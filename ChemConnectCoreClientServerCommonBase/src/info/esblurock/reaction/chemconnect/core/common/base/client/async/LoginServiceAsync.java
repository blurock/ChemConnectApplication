package info.esblurock.reaction.chemconnect.core.common.base.client.async;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;


public interface LoginServiceAsync {
	void logout(AsyncCallback<UserSessionData> callback);

	void removeUser(String key, AsyncCallback<String> callback);

	void getAccount(String key, AsyncCallback<UserAccount> callback);

	void createNewUser(UserAccount uaccount, NameOfPerson person, AsyncCallback<DatabaseObjectHierarchy> callback);

	void loginGuestServer(AsyncCallback<UserSessionData> callback);

	void loginAsCurrentUser(AsyncCallback<UserSessionData> callback);

}

