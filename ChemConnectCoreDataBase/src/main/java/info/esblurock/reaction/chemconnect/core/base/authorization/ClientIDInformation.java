package info.esblurock.reaction.chemconnect.core.base.authorization;

import java.io.Serializable;

public class ClientIDInformation implements Serializable{

	private static final long serialVersionUID = 1L;
	
	String clientName;
	String clientID;
	String clientSecret;

	public ClientIDInformation() {
	}
	
	public ClientIDInformation(String clientName, String clientID, String clientSecret) {
		super();
		this.clientName = clientName;
		this.clientID = clientID;
		this.clientSecret = clientSecret;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		
		build.append(prefix + "clientName    : " + clientName + "\n");
		build.append(prefix + "clientID      : " + clientID + "\n");
		build.append(prefix + "clientSecret  : " + clientSecret + "\n");
		
		return build.toString();
	}
	
}
