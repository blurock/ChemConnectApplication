package info.esblurock.reaction.chemconnect.core.base.client.authentication;

import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;

public interface AuthentificationTopPanelInterface {
	public String callbackWithServer();
	public void setLoginVisibility(boolean visibility);
	public void setCreateButtonVisibility(boolean visibility);
	public void loginCallback(UserSessionData guestAccount);
	public MaterialPanel getModalPanel();
	public void logout();
}
