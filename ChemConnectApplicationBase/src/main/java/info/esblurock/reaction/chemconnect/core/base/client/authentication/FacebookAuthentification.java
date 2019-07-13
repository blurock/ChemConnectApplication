package info.esblurock.reaction.chemconnect.core.base.client.authentication;

import java.util.Random;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

import info.esblurock.reaction.chemconnect.core.base.authorization.ClientIDInformation;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;

public class FacebookAuthentification extends AuthentificationCall {
	
	public FacebookAuthentification(AuthentificationTopPanelInterface toppanel) {
		super(toppanel);
	}

	public void initiateAthentification() {
		initiateAthentification(UserAccountKeys.FacebookClientKey);
	}
	@Override
	public void authorizeCallback(ClientIDInformation info) {	
		String CLIENT_ID = info.getClientID();
		String CLIENT_SECRET = info.getClientSecret();
		String secretState = UserAccountKeys.FacebookSecretKey + new Random().nextInt(999_999);
		Cookies.setCookie(UserAccountKeys.SECRET_COOKIE_NAME, secretState);

		String redirect = toppanel.callbackWithServer();
		String authurl = UserAccountKeys.FacebookAuthURL + "?";
		String reststr = "client_id=" + CLIENT_ID + "&"
				+ "client_secret=" + CLIENT_SECRET + "&"
				+ "redirect_uri=" + redirect + "&"
				+ "response_type=code&"
				+ "state=" + secretState + "&"
				+ "scope=email&"
				+ "grant_type=client_credentials";
				
		/*
		String reststr = "response_type=code&"
				+ "client_id=" + CLIENT_ID + "&"
				+ "redirect_uri=" + redirect + "&"
				+ "state=" + secretState + "&"
				+ "client_secret=" + CLIENT_SECRET + "&"
				+ "&grant_type=client_credentials";
				*/
		String urlS = authurl + reststr;
		Window.alert(urlS);

		Window.open(urlS, "_blank", "");
		
	}
}
