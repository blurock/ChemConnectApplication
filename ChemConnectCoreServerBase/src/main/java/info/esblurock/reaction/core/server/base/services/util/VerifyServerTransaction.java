package info.esblurock.reaction.core.server.base.services.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.core.ontology.base.UserQueries;



public class VerifyServerTransaction {

	private static final Logger log = Logger.getLogger(VerifyServerTransaction.class.getName());

	public static void verify(UserSessionData sessiondata, String event, String ip,
			String sessionid, String tasktype) throws IOException {
		
		boolean verified = UserQueries.allowedTask(event, sessiondata.getUserLevel());
		
		if (verified) {
			log.info("Login verify: ip=" + ip + "   \tUserIP= " + sessiondata.getIP()
			+"\nsessionid=" + sessionid + " \tUser session=" + sessiondata.getSessionID());
			/*
			if (ip.equals(user.getIP())) {
				System.out.println("Login verify 3:" + ip);
				if (user.getSessionId().equals(sessionid)) {
					System.out.println("Login verify 4:" + user.getSessionId());
					SessionEvent sessionevent = new SessionEvent(
							user.getName(), user.getIP(), event);
					System.out.println("Login verify 5:" + event);
					pm.makePersistent(sessionevent);
					System.out.println("Login verify 6: session stored");
				} else {
					String message = "Session id mismatch "
							+ user.getSessionId() + ", " + sessionid;
					System.out.println(message);
				}
			} else {
				String message = "Session IP (" + ip + ") and User IP ("
						+ user.getIP() + ") do not match";
				System.out.println(message);
				throw new Exception(message);
			}
			*/
		} else {
			String message = "Task (" + tasktype
					+ ") Authorization Failure for " + sessiondata.getUserName() + "("
					+ sessiondata.getUserLevel() + ")";
			log.log(Level.WARNING,message);
			throw new IOException(message);
		}
	}
}
