package info.esblurock.reaction.core.server.base.authentification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.esblurock.reaction.chemconnect.core.base.authorization.ClientIDInformation;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.login.ExternalAuthorizationInformation;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginService;
import info.esblurock.reaction.core.server.base.create.CreateContactObjects;
import info.esblurock.reaction.core.server.base.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.base.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.base.queries.QueryBase;
import info.esblurock.reaction.core.server.base.queries.QueryFactory;
import info.esblurock.reaction.core.server.base.services.ServerBase;
import info.esblurock.reaction.core.server.base.services.util.ContextAndSessionUtilities;


public class LoginServiceImpl extends ServerBase implements LoginService {
	private static final long serialVersionUID = 4456105400553118785L;

	public static int standardMaxTransitions = 1000;

	String from = "edward.blurock@gmail.com";

	public static String login = "Login";

	String guest = "Guest";
	String guestpass = "laguna";
	String admin = "Administration";
	String adminpass = "laguna";
	String guestlevel = UserAccountKeys.accessTypeQuery;
	String adminlevel = UserAccountKeys.accessTypeSuperUser;

	@Override
	public UserSessionData loginGuestServer() throws IOException {
		ContextAndSessionUtilities util = getUtilities();
		String sessionid = getThreadLocalRequest().getSession().getId();
		//String sessionid = util.getId();
		String ip = getThreadLocalRequest().getRemoteAddr();
		String host = getThreadLocalRequest().getRemoteHost();
		System.out.println("loginGuestServer(): " + sessionid);
		UserSessionData sessionuser = LoginUtilities.loginAsCurrentUser(sessionid, host, ip);
		System.out.println("loginGuestServer(): \n" + sessionuser);
		return sessionuser;
	}
	@Override
	public UserSessionData logout() {
		ContextAndSessionUtilities util = getUtilities();
		String sessionid = util.getId();
		String ip = getThreadLocalRequest().getRemoteAddr();
		String host = getThreadLocalRequest().getRemoteHost();
		return LoginUtilities.logout(sessionid,host,ip);
	}



	/**
	 * @param username
	 * @return
	 */
	public String removeUser(String username) {
		QueryBase.deleteUsingPropertyValue(UserAccount.class, "accountUserName", username);
		String ans = "SUCCESS";
		return ans;
	}

	@Override
	public UserAccount getAccount(String key) {
		UserAccount account = WriteReadDatabaseObjects.getAccount(key);
		return account;
	}
	@Override
	public DatabaseObjectHierarchy createNewUser(UserAccount uaccount, NameOfPerson person) throws IOException {
		ContextAndSessionUtilities util = getUtilities();
		String sessionid = util.getId();
		UserSessionData sessionuser = LoginUtilities.isSessionActive(sessionid);
		return CreateContactObjects.createNewUser(sessionuser, uaccount, person);
	}
	
	public UserSessionData loginAsCurrentUser() {
		ContextAndSessionUtilities util = getUtilities();
		String sessionid = util.getId();
		//HttpServletRequest resp = getThreadLocalRequest();
		String ip = getThreadLocalRequest().getRemoteAddr();
		String host = getThreadLocalRequest().getRemoteHost();
		return LoginUtilities.loginAsCurrentUser(sessionid, host, ip);
	}
	
	public UserSessionData loginAfterCreateUser(ExternalAuthorizationInformation authinfo) {
		ContextAndSessionUtilities util = getUtilities();
		String sessionid = util.getId();
		//HttpServletRequest resp = getThreadLocalRequest();
		String ip = getThreadLocalRequest().getRemoteAddr();
		String host = getThreadLocalRequest().getRemoteHost();
		UserSessionData usession = LoginUtilities.loginAfterCreateUser(authinfo, sessionid, host, ip);
		return usession;
	}
	public void initialization() throws IOException {
		AuthorizationIDs.readInAuthorizationIDs(getServletContext());
	}
	
	public ClientIDInformation getClientAuthorization(String clientname) throws IOException {
		return AuthorizationIDs.getClientAuthorizationInfo(clientname);
	}
	public List<String> getAccessCreationList() {
		String sessionid = getThreadLocalRequest().getSession().getId();
		System.out.println("getAccessCreationList(): " + sessionid);
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		System.out.println("getAccessCreationList(): \n"+ usession);
		List<String>  accesslst = new ArrayList<String>();
		if(usession != null) {
			accesslst = QueryFactory.getAccessCreationList(usession);
		}
		return accesslst;
	}
}
