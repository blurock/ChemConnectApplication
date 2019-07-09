package info.esblurock.reaction.chemconnect.core.base.authorization;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class AuthorizationIDMap implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, ClientIDInformation> authmap;
	
	public AuthorizationIDMap() {
		authmap = new HashMap<String, ClientIDInformation>();
	}
	
	public void insert(ClientIDInformation ids) {
		authmap.put(ids.getClientName(), ids);
	}
	
	public ClientIDInformation getClientInfo(String clientname) {
		return authmap.get(clientname);
	}

	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + " AuthorizationIDMap -------------------");
		Set<String> clients = authmap.keySet();
		for(String client :  clients) {
			ClientIDInformation info = authmap.get(client);
			build.append(info.toString(prefix));
		}
		
		
		return build.toString();
	}
}
