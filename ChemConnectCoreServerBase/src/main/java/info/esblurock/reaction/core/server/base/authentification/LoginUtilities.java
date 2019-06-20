package info.esblurock.reaction.core.server.base.authentification;

import java.io.IOException;

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
import info.esblurock.reaction.core.server.base.services.ServerBase;
import info.esblurock.reaction.core.server.base.services.util.ContextAndSessionUtilities;

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
		UserSessionData sessionuser = new UserSessionData(guestName,sessionid,IP,hostname,userLevel);
		DatabaseWriteBase.writeUserSessionData(sessionuser);
		UserAccount guestaccount = WriteReadDatabaseObjects.getAccount(guestName);
		if(guestaccount == null) {
			String authorizationType = "Default";
			String accountPrivilege = UserAccountKeys.accessTypeQuery;
			createANewUser(guestName, guestName, authorizationType, accountPrivilege);
		}
		return sessionuser;
	}
	
	public static UserAccount createANewUser(String username, String authorizationName, 
			String authorizationType, String accountPrivilege) {
		ChemConnectDataStructure datastructure = new ChemConnectDataStructure();
		UserAccount account = new UserAccount(datastructure, username, authorizationName, authorizationType, accountPrivilege);
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure();
		NameOfPerson person = new NameOfPerson(structure, "", "", username);
		DatabaseObjectHierarchy hierarchy = CreateContactObjects.createNewUser(account,person);
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
		if(useraccount == null) {
			String suggestedaccountname = suggestALoginName(info.getGiven_name(), info.getFamily_name());
			String authorizationType = info.getAuthorizationType();
			String accountPrivilege = UserAccountKeys.accessTypeStandardUser;
			useraccount = createANewUser(suggestedaccountname, info.getAuthorizationID(), authorizationType, accountPrivilege);
			
		}
		UserSessionData sessionuser = new UserSessionData(useraccount.getAccountUserName(),sessionid,IP,hostname,
				useraccount.getAccountPrivilege());
		DatabaseWriteBase.writeUserSessionData(sessionuser);
		
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

}
