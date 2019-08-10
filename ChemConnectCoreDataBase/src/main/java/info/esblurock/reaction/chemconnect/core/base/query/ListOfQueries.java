package info.esblurock.reaction.chemconnect.core.base.query;

import java.io.Serializable;
import java.util.ArrayList;

public class ListOfQueries extends ArrayList<QuerySetupBase> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public void addAccessToBaseQuery(QuerySetupBase query, String access) {
		QuerySetupBase newquery = query.produceWithAccess(access);
		this.add(newquery);
	}

	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + " ListOfQueries  ------------------------------------------\n");
		for(QuerySetupBase base : this) {
			build.append(base.toString(prefix));
		}
		build.append(prefix + " ListOfQueries  ------------------------------------------\n");
		return build.toString();
	}
}
