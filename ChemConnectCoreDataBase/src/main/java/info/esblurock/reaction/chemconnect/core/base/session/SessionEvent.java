package info.esblurock.reaction.chemconnect.core.base.session;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;


@SuppressWarnings("serial")
@Entity
public class SessionEvent extends DatabaseObject {

    @Index
	String transactionID;
    @Index
	String event;
    @Index
	String sessionIP;
	
	public SessionEvent() {	
	}

	
	/**
	 * @param id The id (username) of the session
	 * @param transactionID transaction ID 
	 * @param event Event tag
	 * @param sessionIP the IP of the session
	 * 
	 * The access and owner are the same as the id.
	 * The sourceID is set to ""
	 */
	public SessionEvent(String id,
			String transactionID, String event, String sessionIP) {
		super(id,id,id,"");
		this.transactionID = transactionID;
		this.event = event;
		this.sessionIP = sessionIP;
	}

	public String getSessionIP() {
		return sessionIP;
	}

	public String getEvent() {
		return event;
	}


	public String getEventInfo() {
		return sessionIP;
	}	
	
}
