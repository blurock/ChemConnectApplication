package info.esblurock.reaction.chemconnect.core.common.base.client.async;

import java.io.IOException;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.base.authorization.ClientIDInformation;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.login.ExternalAuthorizationInformation;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;


@RemoteServiceRelativePath("loginservice")
public interface LoginService extends RemoteService {

	/*
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static LoginServiceAsync instance;

		public static LoginServiceAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(LoginService.class);
			}
			return instance;
		}
	}

	public UserSessionData initializeBaseSystem() throws IOException;
	
	public UserSessionData loginGuestServer() throws IOException;

	public UserSessionData logout();

	public String removeUser(String key);

	public UserAccount getAccount(String key);

	public DatabaseObjectHierarchy createNewUser(UserAccount uaccount, NameOfPerson person) throws IOException;
	
	public UserSessionData loginAsCurrentUser();
	
	public UserSessionData loginAfterCreateUser(ExternalAuthorizationInformation authinfo);
	
	public void initialization() throws IOException;
	
	public ClientIDInformation getClientAuthorization(String clientname) throws IOException;
	
	public List<String> getAccessCreationList();
}
