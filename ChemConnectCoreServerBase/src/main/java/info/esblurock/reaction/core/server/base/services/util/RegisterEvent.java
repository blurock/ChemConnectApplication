package info.esblurock.reaction.core.server.base.services.util;

import java.io.IOException;
import java.util.logging.Logger;

import info.esblurock.reaction.chemconnect.core.base.session.SessionEvent;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.core.server.base.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.base.queries.QueryBase;

public class RegisterEvent {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(RegisterEvent.class.getName());

	static public int checkLevel0 = 0;
	static public int checkLevel1 = 1;
	static public int checkLevel2 = 2;
	static public int checkLevel3 = 3;

	private static int maxCount = 1000;
	

	static public void register(UserSessionData user, String event, String eventinfo, int checklevel) throws IOException {
		if (user != null) {
			SessionEvent sessionevent = new SessionEvent(user.getUserName(), user.getIP(), event, eventinfo);
			if (checklevel > checkLevel0) {
				int c = QueryBase.getNextEventCount(user.getUserName());
				if (c > maxCount) {
					throw new IOException("Transaction count exceeds maximum: contact administrator");
				}
			}
			DatabaseWriteBase.writeDatabaseObject(sessionevent);
		} else {
			throw new IOException("NO LOGIN: User not logged in");
		}
	}
}
