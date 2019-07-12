package info.esblurock.reaction.core.server.base.authentification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.Level;

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
import com.google.appengine.api.log.LogService.LogLevel;


import info.esblurock.reaction.chemconnect.core.base.authorization.ClientIDInformation;
import info.esblurock.reaction.chemconnect.core.base.login.ExternalAuthorizationInformation;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.core.server.base.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.base.services.util.JSONUtilities;
import info.esblurock.reaction.core.server.base.services.util.ManageServerCookies;

@SuppressWarnings("serial")
public class Oauth2CallbackServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(Oauth2CallbackServlet.class.getName());

	private static final Collection<String> GOOGLE_SCOPES = Arrays.asList("email", "profile");
	private static final String USERINFO_ENDPOINT = "https://www.googleapis.com/plus/v1/people/me/openIdConnect";
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private GoogleAuthorizationCodeFlow flow;
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	
	String fullCallbackURL(HttpServletRequest req) {
		String servername = req.getServerName();
		String callbackService = getServletContext().getInitParameter(UserAccountKeys.AuthCallbackParameterKey);
		String fullcallback = getServletContext().getInitParameter(UserAccountKeys.AuthRemoteHostParameterKey) 
				+ callbackService;
		if (servername.compareTo("localhost") == 0) {
			fullcallback = getServletContext().getInitParameter(UserAccountKeys.AuthLocalhostParameterKey) 
					+ callbackService;
		}
		return fullcallback;
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		System.out.println("Oauth2CallbackServlet: ");
		
		String state = req.getParameter(UserAccountKeys.AuthStateParameterKey);
		String code = req.getParameter(UserAccountKeys.AuthCodeParameterKey);
		String access_token = req.getParameter("access_token");
		
		System.out.println("Oauth2CallbackServlet: state='" + state + "'");
		System.out.println("Canonical Hostname: '" + req.getLocalAddr() + "'");
		System.out.println("Canonical Hostname: '" + req.getLocalPort() + "'");

		String expected = ManageServerCookies.findCookie(req, UserAccountKeys.SECRET_COOKIE_NAME);
		System.out.println("Oauth2CallbackServlet: expected='" + expected + "'  state='" + state + "'");
		
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
		
		String fullcallback = fullCallbackURL(req);
		
		if (state.startsWith(UserAccountKeys.GoogleSecretKey)) {
			ClientIDInformation idinfo = AuthorizationIDs.getClientAuthorizationInfo(UserAccountKeys.GoogleClientKey);
			String client_id = idinfo.getClientID();
			String client_secret = idinfo.getClientSecret();
			req.getSession().removeAttribute(UserAccountKeys.AuthStateParameterKey); // Remove one-time use state.
			System.out.println("GoogleAuthorizationCodeFlow.Builder");
			flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
					client_id,
					client_secret, GOOGLE_SCOPES).build();

			System.out.println("flow.newTokenRequest");
			final TokenResponse tokenResponse = flow.newTokenRequest(code)
					.setRedirectUri(fullcallback).execute();

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
			ManageServerCookies.setCookie(resp, "email", emailaddress);
			ManageServerCookies.setCookie(resp, "userpicture", userIdResult.get("picture"));

			firstname = userIdResult.get("given_name");
			lastname = userIdResult.get("family_name");
			auth_idS = userIdResult.get("sub");
			authorizationTypeS = UserAccountKeys.GoogleClientKey;

		} else if (state.startsWith(UserAccountKeys.LinkedInSecretKey)) {
			ClientIDInformation idinfo = AuthorizationIDs.getClientAuthorizationInfo(UserAccountKeys.LinkedInClientKey);
			String client_id = idinfo.getClientID();
			String client_secret = idinfo.getClientSecret();
			String newstate = "tokenlinkedin" + state;
			ManageServerCookies.replaceCookieValue(req,UserAccountKeys.SECRET_COOKIE_NAME,newstate);

			// int serverport = req.getServerPort();
			String callback = "http://localhost:8080/oauth2callback";
			String linkedin = "https://www.linkedin.com/oauth/v2/accessToken?";
			String params = 
					"client_id=" + client_id + "&" 
					+ "redirect_uri=" + callback + "&" 
					+ "grant_type=authorization_code&"
					+ "client_secret=" + client_secret + "&" 
					+ "code=" + code + "&" 
					+ "format=json";
			String response = linkedin + params;
			URL url = new URL(response);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "x-www-form-urlencoded");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : \n" + conn.getResponseCode() + ": " + conn.getResponseMessage() + "\n");
			}
			JSONObject jsonobj = JSONUtilities.getJSON(conn.getInputStream());
			String accesstoken = (String) jsonobj.get("access_token");
			String data = "https://api.linkedin.com/v2/me";
			url = new URL(data);
			conn = (HttpURLConnection) url.openConnection();
		    conn.setRequestMethod("GET");
		    conn.setRequestProperty("Authorization", "Bearer " + accesstoken);
		    conn.setRequestProperty("cache-control", "no-cache");
		    conn.setRequestProperty("X-Restli-Protocol-Version", "2.0.0");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + conn.getResponseCode() + ": " + conn.getResponseMessage());
			}
		    JSONObject json = JSONUtilities.getJSON(conn.getInputStream());
			
			firstname = json.getString("localizedFirstName");
			lastname = json.getString("localizedLastName");
			auth_idS = json.getString("id");
			authorizationTypeS = UserAccountKeys.LinkedInClientKey;
			log.log(Level.INFO, "LinkedIn Authorization: " + auth_idS);
		} else if (state.startsWith(UserAccountKeys.FacebookSecretKey)) {
			ClientIDInformation idinfo = AuthorizationIDs.getClientAuthorizationInfo(UserAccountKeys.LinkedInClientKey);
			String client_id = idinfo.getClientID();
			String client_secret = idinfo.getClientSecret();
			String newstate = "nextfacebook";
			ManageServerCookies.setCookie(resp, "secret", newstate);
			String tokenurl = "https://graph.facebook.com/v3.2/me/accounts?";
			String tokenparameters = "&state=" + newstate 
					+ "&client_id=" + client_id
					+ "&access_token=" + access_token;

			System.out.println(tokenparameters);
			System.out.println("Size of call: " + tokenparameters.length());
			String url = tokenurl + java.net.URLEncoder.encode(tokenparameters, "UTF-8");
			System.out.println("Size of call: " + url.length());

			JSONObject jsonobj = JSONUtilities.getJSONObject(url);
			String accesstoken = (String) jsonobj.get("access_token");

			System.out.println(jsonobj);
			System.out.println("Access Token: " + accesstoken);
			authorizationTypeS = UserAccountKeys.FacebookClientKey;
		} else if (state.startsWith("nextfacebook")) {
			System.out.println("nextfacebook");
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
		ManageServerCookies.setCookie(resp, "redirect", authorizationinfo.getUseraccount());
		
		
		LoginUtilities.setupCookiesForUser(resp, userS, 
				lastname, firstname, levelS, 
				emailaddress, 
				auth_idS, authorizationTypeS, hasAccountS, 
				sessionid, hostname, IP);

		
		UserSessionData usession = new UserSessionData(userS,sessionid,IP,hostname,levelS);
		DatabaseWriteBase.writeUserSessionData(usession);
		String redirect = "/#FirstPagePlace:First%20Page";
		System.out.println("Call redirect: " + redirect);
		String url = resp.encodeRedirectURL(redirect);
		System.out.println("Call redirect: " + redirect);
		resp.sendRedirect(url);
	}


}