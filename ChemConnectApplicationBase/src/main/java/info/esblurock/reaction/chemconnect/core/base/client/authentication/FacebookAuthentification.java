package info.esblurock.reaction.chemconnect.core.base.client.authentication;

import java.util.Random;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

public class FacebookAuthentification {
	
	AuthentificationTopPanelInterface toppanel; 
	
	public FacebookAuthentification(AuthentificationTopPanelInterface toppanel) {
		this.toppanel = toppanel;
	}

	public void login() {
		String CLIENT_ID = "";
		String CLIENT_SECRET = "";
		String secretState = "facebook" + new Random().nextInt(999_999);
		Cookies.setCookie("secret", secretState);

		String redirect = toppanel.callbackWithServer();
		String authurl = "https://graph.facebook.com/oauth/access_token?";
		String reststr = "response_type=code&"
				+ "client_id=" + CLIENT_ID + "&"
				+ "redirect_uri=" + redirect + "&"
				+ "state=" + secretState + "&"
				+ "client_secret=" + CLIENT_SECRET + "&"
				+ "&grant_type=client_credentials";
		/*
		String authurl = "https://www.facebook.com/v3.2/dialog/oauth?";
		String reststr = "response_type=code&"
				+ "client_id=" + CLIENT_ID + "&"
				+ "redirect_uri=" + redirect + "&"
				+ "state=" + secretState + "&"
				+ "client_secret=";
		*/
		String urlS = authurl + reststr;
		Window.alert(urlS);

		Window.open(urlS, "_blank", "");
		
	}
}
