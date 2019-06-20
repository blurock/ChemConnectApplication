package info.esblurock.reaction.chemconnect.core.base.client.authentication;

import java.util.Random;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

import gwt.material.design.client.ui.MaterialToast;

public class LinkedInAuthentification extends AuthentificationCall {
	
	public LinkedInAuthentification(AuthentificationTopPanelInterface toppanel) {
		super(toppanel);
	}

	@Override
	public void initiateAthentification() {
			String CLIENT_ID = "77lvn5zzefwzq0";
			String secretState = "linkedin" + new Random().nextInt(999_999);
			Cookies.setCookie("secret", secretState);

			String authurl = "https://www.linkedin.com/oauth/v2/authorization?";
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
