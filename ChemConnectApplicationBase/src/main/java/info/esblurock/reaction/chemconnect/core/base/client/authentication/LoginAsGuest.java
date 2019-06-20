package info.esblurock.reaction.chemconnect.core.base.client.authentication;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginServiceAsync;

public class LoginAsGuest {
	
	AuthentificationTopPanelInterface toppanel;
	
	public LoginAsGuest(AuthentificationTopPanelInterface toppanel) {
		this.toppanel = toppanel;
	}
	
	public void login() {
		LoginServiceAsync async = LoginService.Util.getInstance();
		async.loginGuestServer(new AsyncCallback<UserSessionData>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(UserSessionData account) {
				toppanel.setLoginVisibility(true);
				toppanel.loginCallback(account);
			}
			
		});		

	}

}
