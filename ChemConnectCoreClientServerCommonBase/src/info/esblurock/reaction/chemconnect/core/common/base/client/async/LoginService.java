package info.esblurock.reaction.chemconnect.core.common.base.client.async;

import java.io.IOException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
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

	public UserSessionData loginGuestServer() throws IOException;

	public void logout();

	public String removeUser(String key);

	public UserAccount getAccount(String key);

	public DatabaseObjectHierarchy createNewUser(UserAccount uaccount, NameOfPerson person) throws IOException;
}
