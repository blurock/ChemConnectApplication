package info.esblurock.reaction.chemconnect.core.base.client.authentication;

import java.util.Random;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;

import info.esblurock.reaction.chemconnect.core.base.authorization.ClientIDInformation;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;

public class GoogleAuthentification extends AuthentificationCall {
	
	public GoogleAuthentification(AuthentificationTopPanelInterface toppanel) {
		super(toppanel);
	}

	@Override
	public void initiateAthentification() {
		initiateAthentification(UserAccountKeys.GoogleClientKey);
	}
	
	@Override
	public void authorizeCallback(ClientIDInformation info) {	
		String CLIENT_ID = info.getClientID();
		//String CLIENT_SECRET = info.getClientSecret();
		//String SCOPE = "https://www.googleapis.com/auth/drive.metadata.readonly";
		//"https://www.googleapis.com/auth/userinfo.email"
		String SCOPE = "email%20profile";
				//"https://www.googleapis.com/auth/userinfo.profile";
		String secretState = UserAccountKeys.GoogleSecretKey + new Random().nextInt(999_999);
		Cookies.setCookie(UserAccountKeys.SECRET_COOKIE_NAME, secretState);
		
		String authurl = UserAccountKeys.GoogleAuthURL + "?";
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
