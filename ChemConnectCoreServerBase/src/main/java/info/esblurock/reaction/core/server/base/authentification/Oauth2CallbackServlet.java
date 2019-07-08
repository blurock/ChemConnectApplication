package info.esblurock.reaction.core.server.base.authentification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import info.esblurock.reaction.chemconnect.core.base.login.ExternalAuthorizationInformation;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.core.server.base.db.DatabaseWriteBase;

@SuppressWarnings("serial")
public class Oauth2CallbackServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(Oauth2CallbackServlet.class.getName());

	private static final Collection<String> SCOPES = Arrays.asList("email", "profile");
	private static final String USERINFO_ENDPOINT = "https://www.googleapis.com/plus/v1/people/me/openIdConnect";
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private GoogleAuthorizationCodeFlow flow;
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		System.out.println("Oauth2CallbackServlet: ");
		
		String state = req.getParameter("state");
		System.out.println("Oauth2CallbackServlet: state='" + state + "'");

		Cookie[] cookies = req.getCookies();
		String expected = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().compareTo("secret") == 0) {
					expected = cookie.getValue();
				}
			}
		} else {
			System.out.println("Oauth2CallbackServlet after sendRedirect  SC_UNAUTHORIZED no cookies");
		}
		System.out.println("Oauth2CallbackServlet: '" + expected + "'");
		
		// Ensure that this is no request forgery going on, and that the user
		// sending us this connect request is the user that was supposed to.
		if (state == null || !(state.compareTo(expected) == 0)) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			System.out.println("Oauth2CallbackServlet after sendRedirect  SC_UNAUTHORIZED");
			resp.sendRedirect(req.getContextPath());
		}
		String userS = "";
		String accountnameS = "";
		String firstname = "";
		String lastname = "";
		String emailaddress = "";
		String levelS = UserAccountKeys.accessTypeQuery;
		String auth_idS = "";
		String authorizationTypeS = "";
		String hasAccountS = "";
		String sessionid = req.getSession().getId();
		String hostname = req.getLocalName();
		String IP = req.getLocalName();
		if (state.startsWith("google")) {
			String client_id = "";
			String client_secret = "";
			req.getSession().removeAttribute("state"); // Remove one-time use state.
			System.out.println("GoogleAuthorizationCodeFlow.Builder");
			flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
					client_id,
					client_secret, SCOPES).build();

			System.out.println("flow.newTokenRequest");
			final TokenResponse tokenResponse = flow.newTokenRequest(req.getParameter("code"))
					.setRedirectUri(getServletContext().getInitParameter("callback")).execute();

			System.out.println("req.getSession().setAttribute");
			req.getSession().setAttribute("token", tokenResponse.toString()); // Keep track of the token.
			System.out.println("");
			final Credential credential = flow.createAndStoreCredential(tokenResponse, null);
			System.out.println("HttpRequestFactory requestFactory");
			final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);

			System.out.println("GenericUrl url = new GenericUrl");
			final GenericUrl url = new GenericUrl(USERINFO_ENDPOINT); // Make an authenticated request.
			final HttpRequest request = requestFactory.buildGetRequest(url);
			request.getHeaders().setContentType("application/json");
			System.out.println("String jsonIdentity = request.execute().parseAsString();");

			final String jsonIdentity = request.execute().parseAsString();
			@SuppressWarnings("unchecked")
			HashMap<String, String> userIdResult = new ObjectMapper().readValue(jsonIdentity, HashMap.class);
			// From this map, extract the relevant profile info and store it in the session.
			req.getSession().setAttribute("userEmail", userIdResult.get("email"));
			req.getSession().setAttribute("userId", userIdResult.get("sub"));
			req.getSession().setAttribute("userImageUrl", userIdResult.get("picture"));

			emailaddress = userIdResult.get("email");
			Cookie emailC = new Cookie("email", emailaddress);
			emailC.setMaxAge(60 * 60);
			resp.addCookie(emailC);

			Cookie picC = new Cookie("userpicture", userIdResult.get("picture"));
			picC.setMaxAge(60 * 60);
			resp.addCookie(picC);

			firstname = userIdResult.get("given_name");
			lastname = userIdResult.get("family_name");
			auth_idS = userIdResult.get("sub");
			authorizationTypeS = "Google";

		} else if (state.startsWith("linkedin")) {
			String code = req.getParameter("code");
			List<String> list = Collections.list(req.getParameterNames());
			for (String name : list) {
				System.out.println(name + ": " + req.getParameter(name));
			}
			String newstate = "tokenlinkedin" + state;
			for (Cookie cookie : cookies) {
				if (cookie.getName().compareTo("secret") == 0) {
					cookie.setValue(newstate);
				}
			}

			System.out.println("Canonical Hostname: '" + req.getLocalAddr() + "'");
			System.out.println("Canonical Hostname: '" + req.getLocalPort() + "'");
			// int serverport = req.getServerPort();
			String servername = req.getServerName();
			String red = "http://blurock-chemconnect.appspot.com/oauth2callback";
			if (servername.compareTo("localhost") == 0) {
				red = "http://localhost:8080/oauth2callback";
			}
			String client_id = "";
			String client_secret = "";
			String response = "https://www.linkedin.com/oauth/v2/accessToken?state=" 
					+ newstate + "&"
					+ "client_id=" + client_id + "&" 
					+ "redirect_uri=" + red + "&" 
					+ "grant_type=authorization_code&"
					+ "client_secret=" + client_secret + "&" 
					+ "code=" + code + "&" 
					+ "format=json";

			log.info("Response: " + response);
			JSONObject jsonobj = getJSONObject(response);
			String accesstoken = (String) jsonobj.get("access_token");

			resp.addHeader("Authorization", "Bearer " + accesstoken);
			String data = "https://api.linkedin.com/v1/people/~?format=json";
			// String data = "https://api.linkedin.com/v2/me?format=json";
			URL url = new URL(data);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.addRequestProperty("Authorization", "Bearer " + accesstoken);
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + conn.getResponseCode() + ": " + conn.getResponseMessage());
			}
			JSONObject json = getJSON(conn.getInputStream());
			firstname = json.getString("firstName");
			lastname = json.getString("lastName");
			auth_idS = json.getString("id");
			authorizationTypeS = "LinkedIn";
		} else if (state.startsWith("facebook")) {
			String access_token = req.getParameter("access_token");
			String CLIENT_ID = "";
			String newstate = "nextfacebook";
			Cookie newstateC = new Cookie("secret", newstate);
			resp.addCookie(newstateC);
			String tokenurl = "https://graph.facebook.com/v3.2/me/accounts?";
			String tokenparameters = "&state=" + newstate + "&client_id=" + CLIENT_ID + "&access_token=" + access_token;

			System.out.println(tokenparameters);
			System.out.println("Size of call: " + tokenparameters.length());
			String url = tokenurl + java.net.URLEncoder.encode(tokenparameters, "UTF-8");
			System.out.println("Size of call: " + url.length());

			JSONObject jsonobj = getJSONObject(url);
			String accesstoken = (String) jsonobj.get("access_token");

			System.out.println(jsonobj);
			System.out.println("Access Token: " + accesstoken);
			authorizationTypeS = "facebook";
		} else if (state.startsWith("nextfacebook")) {
			System.out.println("nextfacebook");
			List<String> list = Collections.list(req.getParameterNames());
			for (String name : list) {
				System.out.println(name + ": " + req.getParameter(name));
			}

		}
		hasAccountS = Boolean.TRUE.toString();
		ExternalAuthorizationInformation authorizationinfo = 
				new ExternalAuthorizationInformation("",firstname, lastname, auth_idS, authorizationTypeS, 
						hasAccountS, emailaddress);

		HttpSession session = req.getSession();
		UserAccount useraccount = null;
		try {
			useraccount = DatabaseWriteBase.userAccountFromAuthorizationName(authorizationinfo.getAuthorizationID());
			hasAccountS = Boolean.TRUE.toString();
			accountnameS = useraccount.getAccountUserName();
			userS = useraccount.getAccountUserName();
			levelS = useraccount.getAccountPrivilege();
			if(useraccount.getAccountUserName().length() == 0) {
				hasAccountS = Boolean.FALSE.toString();
			}
			userS = useraccount.getAccountUserName();
			levelS = useraccount.getAccountPrivilege();
			LoginUtilities.loginWithAuthorization(authorizationinfo, session.getId(), hostname, IP);
		} catch(IOException ex) {
			hasAccountS = Boolean.FALSE.toString();
		}
		Cookie redirectC = new Cookie("redirect", authorizationinfo.getUseraccount());
		redirectC.setMaxAge(60 * 60);
		resp.addCookie(redirectC);
		
		
		LoginUtilities.setupCookiesForUser(resp, userS, 
				lastname, firstname, levelS, 
				emailaddress, 
				auth_idS, authorizationTypeS, hasAccountS, 
				sessionid, hostname, IP);

		
		UserSessionData usession = new UserSessionData(userS,sessionid,IP,hostname,levelS);
		DatabaseWriteBase.writeUserSessionData(usession);
		String servername = req.getServerName();
		System.out.println("servername: " + servername);
		/*
		String redirect = "http://blurock-chemconnect.appspot.com/#FirstPagePlace:First%20Page";
		if (servername.compareTo("localhost") == 0) {
			redirect = "http://localhost:8080/#FirstPagePlace:First%20Page";
		}
		*/
		/*
		String parameters = "?family_name=" + lastname +
				"&given_name=" + firstname + 
				"&account_name=" + accountnameS + 
				"&authorizationType=" + authorizationTypeS +
				"&auth_id=" + auth_idS;
				*/
		String redirect = "/#FirstPagePlace:First%20Page";
		System.out.println("Call redirect: " + redirect);
		String url = resp.encodeRedirectURL(redirect);
		System.out.println("Call redirect: " + redirect);
		resp.sendRedirect(url);
	}

	JSONObject getJSONObject(String response) throws IOException {
		System.out.println(response);
		URL url = new URL(response);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException(
					"Failed : HTTP error code : \n" + conn.getResponseCode() + ": " + conn.getResponseMessage() + "\n");
		}
		return getJSON(conn.getInputStream());
	}

	private JSONObject getJSON(InputStream inputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader((inputStream)));
		String output;
		StringBuilder build = new StringBuilder();
		while ((output = br.readLine()) != null) {
			build.append(output);
			build.append("\n");
		}
		String msg = build.toString();
		System.out.println("Message:\n" + msg);
		JSONObject jsonobj = new JSONObject(msg);
		return jsonobj;
	}

}