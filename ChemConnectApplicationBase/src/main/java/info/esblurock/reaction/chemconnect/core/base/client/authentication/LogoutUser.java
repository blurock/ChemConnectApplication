package info.esblurock.reaction.chemconnect.core.base.client.authentication;

import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginServiceAsync;

public class LogoutUser {
	
	AuthentificationTopPanelInterface toppanel;
	
	public LogoutUser(AuthentificationTopPanelInterface toppanel) {
		this.toppanel = toppanel;
	}

	public void logout() {
		MaterialToast.fireToast("Logout");
		LoginServiceAsync async = LoginService.Util.getInstance();
		async.logout(new AsyncCallback<UserSessionData>() {

			@Override
			public void onFailure(Throwable caught) {
				SetUpUserCookies.zeroAllCookies();
				SetUpUserCookies.setupDefaultGuestUserCookies();
				toppanel.setLoginVisibility(true);
				StandardWindowVisualization.errorWindowMessage("Problem with logging out", caught.toString());
			}

			@Override
			public void onSuccess(UserSessionData result) {
				SetUpUserCookies.zeroAllCookies();
				SetUpUserCookies.setupDefaultGuestUserCookies();
				toppanel.setLoginVisibility(true);
				toppanel.loginCallback(result);
			}
			
		});
	}
}
