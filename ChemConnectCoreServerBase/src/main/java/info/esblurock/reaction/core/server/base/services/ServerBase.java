package info.esblurock.reaction.core.server.base.services;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.core.server.base.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.base.services.util.ContextAndSessionUtilities;
import info.esblurock.reaction.core.server.base.services.util.RegisterEvent;
import info.esblurock.reaction.core.server.base.services.util.VerifyServerTransaction;


public class ServerBase  extends RemoteServiceServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String googleAPIKey = "AIzaSyAM533K4aia4ObLQLsix476xrQ1YBqpdVo";
	
	protected ContextAndSessionUtilities getUtilities() {
		HttpSession session = getThreadLocalRequest().getSession();
		ContextAndSessionUtilities util 
			= new ContextAndSessionUtilities(getServletContext(), session);
		return util;
	}
	
	public static String getAPIKey() {
		return googleAPIKey;
	}
	
	protected void register(String event, String eventinfo, int checklevel) throws IOException {
		ContextAndSessionUtilities util = getUtilities();
		String sessionid = util.getId();
		UserSessionData sessiondata = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		RegisterEvent.register(sessiondata,event,eventinfo,checklevel);
	}
	
	protected void verify(String task, String tasktype) throws IOException {
		ContextAndSessionUtilities util = getUtilities();
		String sessionid = util.getId();
		String ip = this.getThreadLocalRequest().getLocalAddr();
		UserSessionData sessiondata = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		if(sessiondata == null) {
			throw new IOException("Session must be activated");
		} else {
			VerifyServerTransaction.verify(sessiondata, task, ip, sessionid, tasktype);
		}
	}


	public String fileAsInput(String className, String sourceType, String textName, String text, String user,
			String keyword) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
