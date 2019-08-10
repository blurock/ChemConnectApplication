package info.esblurock.reaction.core.server.base.queries;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import info.esblurock.reaction.core.ontology.base.authentification.UserQueries;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.base.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.base.query.ListOfQueries;
import info.esblurock.reaction.chemconnect.core.base.query.QueryPropertyValue;
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
	public static ListOfQueries userCanModifyQuery(QuerySetupBase query, UserSessionData usersession) {
		ListOfQueries queries = new ListOfQueries();
		Set<String> accessset = UserQueries.getListOfModifyPriviledges(usersession);
		String username = usersession.getUserName();
		addModifyAccess(queries,username,accessset,query,username);
		List<String> consortiumlist = getListOfUserConsortium(username);
		for(String name : consortiumlist) {
			addModifyAccess(queries,name,accessset,query,username);			
		}
		return queries;
	}
	
	private static void addModifyAccess(ListOfQueries queries, String owner, Set<String> accessset, QuerySetupBase query, String username) {
		SetOfQueryPropertyValues values = query.getQueryvalues();
		SetOfQueryPropertyValues uservalues = new SetOfQueryPropertyValues(values);
		QueryPropertyValue ownervalue = new QueryPropertyValue("owner", owner);
		uservalues.add(ownervalue);
		QuerySetupBase ownerquery = new QuerySetupBase(query.getQueryClass(),uservalues);		
		for (String access : accessset ) {
			if (access.compareTo(UserAccountKeys.accessQueryDataModifyPublic) == 0) {
				QuerySetupBase newquery = ownerquery.produceWithAccess(UserAccountKeys.publicAccess);
				queries.add(newquery);
			} else if (access.compareTo(UserAccountKeys.accessQueryDataModifyUser) == 0) {
				QuerySetupBase newquery = ownerquery.produceWithAccess(username);
				queries.add(newquery);
			} else if (access.compareTo(UserAccountKeys.accessQueryDataModifyConsortium) == 0) {
				List<String> consortiumlist = getListOfUserConsortium(username);
				for (String consortium : consortiumlist) {
					QuerySetupBase newquery = ownerquery.produceWithAccess(consortium);
					queries.add(newquery);
				}
			}
		}
	}
	
	public static List<String> getAccessCreationList(UserSessionData user) {
		System.out.println("getAccessCreationList: UserSessionData\n" + user);
		Set<String> privileges = UserQueries.getListOfInputPriviledges(user);
		System.out.println("getAccessCreationList: privileges:\n" + privileges);
		String consortiumaccess = UserAccountKeys.accessQueryDataInputConsortium;
		String useraccess = UserAccountKeys.accessQueryDataInputUser;
		String publicaccess = UserAccountKeys.accessQueryDataInputPublic;
		ArrayList<String> names = new ArrayList<String>();
		
		if(privileges.contains(useraccess)) {
			System.out.println("getAccessCreationList: user: " + user.getUserName());
			names.add(user.getUserName());
		}
		if(privileges.contains(publicaccess)) {
			System.out.println("getAccessCreationList: Public");
			names.add("Public");
		}
		if(privileges.contains(consortiumaccess)) {
			System.out.println("getAccessCreationList: Consortium");
			List<String> consortium = getListOfUserConsortium(user.getUserName());
			for(String name : consortium) {
				names.add(name);
			}
		}
		return names;
	}
	
	public static List<String> getListOfUserConsortium(String user) {
		ArrayList<String> consortiumlist = new ArrayList<String>();
		return consortiumlist;
	}
}