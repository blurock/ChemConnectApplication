package info.esblurock.reaction.chemconnect.core.common.base.client.async;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.base.authorization.ClientIDInformation;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.login.ExternalAuthorizationInformation;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;


public interface LoginServiceAsync {
	void logout(AsyncCallback<UserSessionData> callback);

	void removeUser(String key, AsyncCallback<String> callback);

	void getAccount(String key, AsyncCallback<UserAccount> callback);

	void createNewUser(UserAccount uaccount, NameOfPerson person, AsyncCallback<DatabaseObjectHierarchy> callback);

	void loginGuestServer(AsyncCallback<UserSessionData> callback);

	void loginAsCurrentUser(AsyncCallback<UserSessionData> callback);

	void loginAfterCreateUser(ExternalAuthorizationInformation authinfo, AsyncCallback<UserSessionData> callback);

	void initialization(AsyncCallback<Void> callback);

	void getClientAuthorization(String clientname, AsyncCallback<ClientIDInformation> callback);

	void getAccessCreationList(AsyncCallback<List<String>> callback);

	void initializeBaseSystem(AsyncCallback<UserSessionData> callback);

}

