package info.esblurock.reaction.chemconnect.core.base.client.authentication;

import java.util.Random;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.base.authorization.ClientIDInformation;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;

public class LinkedInAuthentification extends AuthentificationCall {
	
	public LinkedInAuthentification(AuthentificationTopPanelInterface toppanel) {
		super(toppanel);
	}

	@Override
	public void initiateAthentification() {
		initiateAthentification(UserAccountKeys.LinkedInClientKey);
	}
	
	public void authorizeCallback(ClientIDInformation info) {
			String CLIENT_ID = info.getClientID();
			String secretState = UserAccountKeys.LinkedInSecretKey + new Random().nextInt(999_999);
			Cookies.setCookie(UserAccountKeys.SECRET_COOKIE_NAME, secretState);

			String authurl = UserAccountKeys.LinkedInAuthURL + "?";
			String redirect = toppanel.callbackWithServer();
			MaterialToast.fireToast("Redirect: " + redirect);
			String reststr = "response_type=code&"
					+ "client_id=" + CLIENT_ID + "&"
					+ "redirect_uri=" + redirect + "&"
					+ "state=" + secretState + "&"
					+ "scope=r_basicprofile%20r_emailaddress";
			String urlS = authurl + reststr;
			MaterialToast.fireToast("URL: " + redirect);
			toppanel.setLoginVisibility(false);
			Window.open(urlS, "_blank", "");
	}

}
