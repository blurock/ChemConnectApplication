package info.esblurock.reaction.core.server.base.authentification;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import info.esblurock.reaction.chemconnect.core.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.login.ExternalAuthorizationInformation;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.core.server.base.create.CreateContactObjects;
import info.esblurock.reaction.core.server.base.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.base.db.WriteReadDatabaseObjects;

/*
 * These routines manage the user under the current session and logging
 * 
 * UserSessionData: This is the session data associated with the user. This should be eliminated when the session ends
 * or the user logs out.
 * 
 * 
 * 
 * 
 */


/**
 * @author edwardblurock
 *
 */
public class LoginUtilities {

	
	
	/** isSessionActive
	 * This determines or finds the current session.
	 * 
	 * @param sessionid The current session id
	 * @return The UserSessionData if the session is active (the sessionid matches) or null (none found).
	 * 
	 * If a UserSessionData is found, then the session is still active and the user can continue using the session.
	 * 
	 */
	public static UserSessionData isSessionActive(String sessionid) {
		return DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
	}
	/** loginAsCurrentUser
	 * 
	 * This is the top level routine when the user opens the browser and starts the module.
	 * There are basically two modes:
	 * <ul>
	 * 	  <li> The user is already logged in, this means the current session matches a UserAccount in the database
	 *    <li> No UserSessionData is found, start a new session as guest.
	 * </ul>
	 * 
	 * @param sessionid The current session ID
	 * @param hostname  The current hostname
	 * @param IP        The current IP
	 * @return The user session data of the current user.
	 */
	public static UserSessionData loginAsCurrentUser(String sessionid, String hostname, String IP) {
		UserSessionData sessionuser = isSessionActive(sessionid);
		if(sessionuser == null) {
			sessionuser = newLoginAsGuest(sessionid,hostname,IP);
		} else {
			continueWithCurrentSession(sessionuser);
		}
		return sessionuser;
	}
	/** newLoginAsGuest 
	 * 
	 * This starts up a new session with Guest as the user.
	 * 
	 * @param sessionid The current session ID
	 * @param hostname  The current hostname
	 * @param IP        The current IP
	 * @return The current user session data
	 * 
	 * A new UserSessionData is created for the guest user.
	 * If the guest UserAccount does not exist, this creates it
	 * 
	 */
	public static UserSessionData newLoginAsGuest(String sessionid, String hostname, String IP) {
		String guestName = "guest";
		String userLevel = UserAccountKeys.accessTypeQuery;
		
		System.out.println("loginWithAuthorization   guestName:    " + guestName);
		System.out.println("loginWithAuthorization   sessionid:    " + sessionid);
		System.out.println("loginWithAuthorization   IP:           " + IP);
		System.out.println("loginWithAuthorization   hostname:     " + hostname);
		System.out.println("loginWithAuthorization   userLevel   : " + userLevel);


		
		UserSessionData sessionuser = new UserSessionData(guestName,sessionid,IP,hostname,userLevel);
		DatabaseWriteBase.writeUserSessionData(sessionuser);
		UserAccount guestaccount = WriteReadDatabaseObjects.getAccount(guestName);
		if(guestaccount == null) {
			String authorizationType = "Default";
			String accountPrivilege = UserAccountKeys.accessTypeQuery;
			createANewUser(sessionuser, guestName, guestName, authorizationType, accountPrivilege);
		}
		return sessionuser;
	}
	
	public static UserAccount createANewUser(UserSessionData usession, String username, String authorizationName, 
			String authorizationType, String accountPrivilege) {
		ChemConnectDataStructure datastructure = new ChemConnectDataStructure();
		UserAccount account = new UserAccount(datastructure, username, authorizationName, authorizationType, accountPrivilege);
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure();
		NameOfPerson person = new NameOfPerson(structure, "", "", username);
		DatabaseObjectHierarchy hierarchy = CreateContactObjects.createNewUser(usession,account,person);
		System.out.println(hierarchy.toString("createANewUser"));
		return (UserAccount) hierarchy.getObject();
	}
	
	
	/** continueWithCurrentSession
	 * 
	 * This does everything that is needed to continue a session.
	 * 
	 * @param sessionuser The user session data
	 * 
	 * 
	 */
	public static void continueWithCurrentSession(UserSessionData sessionuser) {
		
	}
	
	public static UserSessionData loginWithAuthorization(ExternalAuthorizationInformation info, String sessionid, String hostname, String IP) {
		logout(sessionid, hostname, IP);
		UserAccount useraccount = WriteReadDatabaseObjects.getAccountWithAuthorization(info.getAuthorizationID());
		UserSessionData sessionuser = null;
		if(useraccount == null) {
			String suggestedaccountname = suggestALoginName(info.getGiven_name(), info.getFamily_name());
			String authorizationType = info.getAuthorizationType();
			String accountPrivilege = UserAccountKeys.accessTypeStandardUser;
			sessionuser = new UserSessionData(suggestedaccountname,sessionid,IP,hostname,
					accountPrivilege);
			DatabaseWriteBase.writeUserSessionData(sessionuser);
			useraccount = createANewUser(sessionuser, suggestedaccountname, info.getAuthorizationID(), authorizationType, accountPrivilege);
			System.out.println(useraccount.toString("loginWithAuthorization"));
		} else {
			sessionuser = new UserSessionData(useraccount.getAccountUserName(),sessionid,IP,hostname,
					useraccount.getAccountPrivilege());
			DatabaseWriteBase.writeUserSessionData(sessionuser);
		}
		System.out.println("loginWithAuthorization   useraccount.getAccountUserName(): " + useraccount.getAccountUserName());
		System.out.println("loginWithAuthorization   sessionid:                         " + sessionid);
		System.out.println("loginWithAuthorization   IP:                                " + IP);
		System.out.println("loginWithAuthorization   hostname:                          " + hostname);
		System.out.println("loginWithAuthorization   useraccount.getAccountPrivilege(): " + useraccount.getAccountPrivilege());
		System.out.println(useraccount.toString("loginWithAuthorization"));
		
	return sessionuser;	
	}

	/** logout
	 * 
	 * This log out the current user and sign in again as guest.
	 * 
	 * @param sessionid The current session ID
	 * @param hostname  The current hostname
	 * @param IP        The current IP
	 */
	public static UserSessionData logout(String sessionid, String hostname, String IP) {
		UserSessionData sessionuser = isSessionActive(sessionid);
		DatabaseWriteBase.deleteUserSessionData(sessionuser);
		return newLoginAsGuest(sessionid, hostname, IP);
	}
	
	/** suggestALoginName
	 * @param firstname The user's first name
	 * @param lastname  The user's last name
	 * @return A suggestion for a login
	 * 
	 * This tries a combination of first and last name (with a count) for a suggestion for a login name
	 */
	private static String suggestALoginName(String firstname, String lastname) {
		String suggestion = lastname;
		if (WriteReadDatabaseObjects.getAccount(suggestion) != null) {
			int count = 0;
			suggestion = firstname + "_" + lastname;
			while (WriteReadDatabaseObjects.getAccount(suggestion) != null) {
				suggestion = firstname + "_" + lastname + "_" + Integer.toString(count++);
			}
		}
		return suggestion;
	}
	
	public static UserSessionData loginAfterCreateUser(ExternalAuthorizationInformation authinfo,
			String sessionid, String hostname, String IP) {
		return loginWithAuthorization(authinfo, sessionid, hostname, IP);
	}
	

	public static void setupCookiesForUser(HttpServletResponse resp,
			String userS, 
			String familyS, String givenS, String levelS,
			String emailaddress,
			String auth_idS, String authorizationTypeS, String hasAccountS,
			String sessionid, String hostname, String IP) {
		
		Cookie userC = new Cookie("account_name", userS);
		userC.setMaxAge(60 * 60);
		resp.addCookie(userC);

		Cookie familyC = new Cookie("family_name", familyS);
		userC.setMaxAge(60 * 60);
		resp.addCookie(familyC);

		Cookie givenC = new Cookie("given_name", givenS);
		userC.setMaxAge(60 * 60);
		resp.addCookie(givenC);


		Cookie levelC = new Cookie("level", levelS);
		levelC.setMaxAge(60 * 60);
		resp.addCookie(levelC);

		Cookie auth_idC = new Cookie("auth_id", auth_idS);
		auth_idC.setMaxAge(60 * 60);
		resp.addCookie(auth_idC);

		Cookie authorizationTypeC = new Cookie("authorizationType", authorizationTypeS);
		authorizationTypeC.setMaxAge(60 * 60);
		resp.addCookie(authorizationTypeC);

		Cookie hasAccountC = new Cookie("hasAccount", hasAccountS);
		hasAccountC.setMaxAge(60 * 60);
		resp.addCookie(hasAccountC);

		Cookie sidC = new Cookie("sid", sessionid);
		sidC.setMaxAge(60 * 60);
		resp.addCookie(sidC);

		Cookie hostnameC = new Cookie("hostname", hostname);
		hostnameC.setMaxAge(60 * 60);
		resp.addCookie(hostnameC);

		Cookie ipC = new Cookie("sid", IP);
		ipC.setMaxAge(60 * 60);
		resp.addCookie(ipC);

		Cookie emailC = new Cookie("email", emailaddress);
		emailC.setMaxAge(60 * 60);
		resp.addCookie(emailC);

	}
}
