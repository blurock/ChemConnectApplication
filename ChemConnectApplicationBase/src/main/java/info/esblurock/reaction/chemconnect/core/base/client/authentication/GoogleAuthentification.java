package info.esblurock.reaction.chemconnect.core.base.client.authentication;

import java.util.Random;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

public class GoogleAuthentification extends AuthentificationCall {
	
	public GoogleAuthentification(AuthentificationTopPanelInterface toppanel) {
		super(toppanel);
	}

	@Override
	public void initiateAthentification() {
		String CLIENT_ID = "571384264595-am69s6l6nuu1hg4o2vmlcmaj63pscd3d.apps.googleusercontent.com";
		String SCOPE = "https://www.googleapis.com/auth/drive.metadata.readonly";
		
		String secretState = "google" + new Random().nextInt(999_999);
		Cookies.setCookie("secret", secretState);
		
		String authurl = "https://accounts.google.com/o/oauth2/v2/auth?";
		String redirect = toppanel.callbackWithServer();
		
		String reststr =
				"scope=" + SCOPE + "&" + 
				"access_type=offline&" + 
				"include_granted_scopes=true&" + 
				"state=" + secretState + "&" + 
				"redirect_uri=" + redirect + "&" + 
				"response_type=code&" + 
				"client_id=" + CLIENT_ID;
		String urlS = authurl + reststr;
		toppanel.setLoginVisibility(false);

		Window.open(urlS, "_blank", "");
	}

}
