package info.esblurock.reaction.core.server.base.db.consortium;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.consortium.Consortium;
import info.esblurock.reaction.chemconnect.core.base.dataset.consortium.ConsortiumMember;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.base.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.base.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.core.server.base.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.base.queries.QueryBase;

public class ReadInConsortium {

	public static ArrayList<DatabaseObjectHierarchy> getConsortiumForUser(UserSessionData usession) {
		ArrayList<DatabaseObjectHierarchy> conlist = new ArrayList<DatabaseObjectHierarchy>();
		Set<String> names = getConsortiumNamesForUser(usession);
		Set<String> ids = new HashSet<String>();
		for (String consortium : names) {
			SetOfQueryPropertyValues queryvalues = new SetOfQueryPropertyValues();
			queryvalues.add("consortiumName", consortium);
			String classname = Consortium.class.getCanonicalName();
			QuerySetupBase query = new QuerySetupBase(classname, queryvalues);
			try {
				SingleQueryResult result = QueryBase.StandardQueryResult(query);
				ArrayList<DatabaseObject> objs = result.getResults();
				for (DatabaseObject obj : objs) {
					ids.add(obj.getIdentifier());
				}
			} catch (ClassNotFoundException ex) {
				System.out.println("SYSTEM ERROR: class not found: " + classname);
			}
		}

		return WriteReadDatabaseObjects.getDatabaseObjectHierarchyFromIDs(StandardDataKeywords.consortium, ids);
	}

	public static Set<String> getConsortiumNamesForUser(UserSessionData usession) {
		SetOfQueryPropertyValues queryvalues = new SetOfQueryPropertyValues();
		queryvalues.add("consortiumMemberName", usession.getUserName());
		QuerySetupBase query = new QuerySetupBase(ConsortiumMember.class.getCanonicalName(), queryvalues);
		HashSet<String> names = new HashSet<String>();
		try {
			SingleQueryResult result = QueryBase.StandardQueryResult(query);
			ArrayList<DatabaseObject> objs = result.getResults();
			for (DatabaseObject obj : objs) {
				ConsortiumMember member = (ConsortiumMember) obj;
				names.add(member.getConsortiumName());
			}
		} catch (ClassNotFoundException ex) {
			System.out.println("SYSTEM ERROR: class not found: " + StandardDataKeywords.consortiumMember);
		}
		return names;
	}

}
