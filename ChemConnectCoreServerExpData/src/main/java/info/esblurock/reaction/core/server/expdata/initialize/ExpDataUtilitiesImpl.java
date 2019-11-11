package info.esblurock.reaction.core.server.expdata.initialize;

import java.io.IOException;

import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.common.expdata.async.ExpDataUtilities;
import info.esblurock.reaction.core.server.base.authentification.AuthorizationIDs;
import info.esblurock.reaction.core.server.base.authentification.InitializationBase;
import info.esblurock.reaction.core.server.base.authentification.LoginUtilities;
import info.esblurock.reaction.core.server.base.services.ServerBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretDataBase;
import info.esblurock.reaction.core.server.expdata.util.InterpretDataExpData;

public class ExpDataUtilitiesImpl extends ServerBase implements ExpDataUtilities {

	private static final long serialVersionUID = 1L;
	
	public UserSessionData initializeBaseSystem() throws IOException {
		InitializationBase.interpretDataBase = new InterpretDataExpData();
		AuthorizationIDs.readInAuthorizationIDs(getServletContext());
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData user = LoginUtilities.isSessionActive(sessionid);
		if(user == null) {
			user = loginGuestServer();
		}
		System.out.println("initializeBaseSystem():" +sessionid + "\n" + user.toString("Current: "));
		return user;
	}
	
	public UserSessionData loginGuestServer() throws IOException {
		String sessionid = getThreadLocalRequest().getSession().getId();
		String ip = getThreadLocalRequest().getRemoteAddr();
		String host = getThreadLocalRequest().getRemoteHost();
		UserSessionData sessionuser = LoginUtilities.loginAsCurrentUser(sessionid, host, ip);
		return sessionuser;
	}


}
