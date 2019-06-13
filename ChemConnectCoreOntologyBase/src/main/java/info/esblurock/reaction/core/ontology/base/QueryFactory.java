package info.esblurock.reaction.core.ontology.base;

import java.util.ArrayList;
import java.util.List;

import info.esblurock.reaction.core.ontology.base.authentification.UserQueries;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.base.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.base.query.ListOfQueries;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.chemconnect.core.base.query.SetOfQueryPropertyValues;

public class QueryFactory {

	public static ListOfQueries produceQueryList(QuerySetupBase query, UserSessionData user) {
		ListOfQueries queries = new ListOfQueries();
		for (String access : UserQueries.getListOfQueryPriviledges(user)) {
			if (access.compareTo(UserAccountKeys.accessQueryDataQueryPublic) == 0) {
				QuerySetupBase newquery = query.produceWithAccess(UserAccountKeys.publicAccess);
				queries.add(newquery);
			} else if (access.compareTo(UserAccountKeys.accessQueryDataQueryUser) == 0) {
				QuerySetupBase newquery = query.produceWithAccess(user.getUserName());
				queries.add(newquery);
			} else if (access.compareTo(UserAccountKeys.accessQueryDataQueryConsortium) == 0) {
				List<String> consortiumlist = getListOfUserConsortium(user.getUserName());
				for (String consortium : consortiumlist) {
					QuerySetupBase newquery = query.produceWithAccess(consortium);
					queries.add(newquery);
				}
			}
		}
		return queries;
	}

	public static ListOfQueries accessQueryForUser(String classname, String user, SetOfQueryPropertyValues values) {
		ListOfQueries queries = new ListOfQueries();
		if(values == null) {
			values = new SetOfQueryPropertyValues();
		}
		QuerySetupBase ownerquery = new QuerySetupBase(user,classname, values);
		QuerySetupBase publicquery = new QuerySetupBase(UserAccountKeys.publicAccess, classname, values);
		queries.add(publicquery);
		queries.add(ownerquery);
		/*
		 * Add consortium queries
		 */
		return queries;
}
	
	
	public static List<String> getListOfUserConsortium(String user) {
		ArrayList<String> consortiumlist = new ArrayList<String>();
		return consortiumlist;
	}
}