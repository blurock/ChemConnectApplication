package info.esblurock.reaction.core.ontology.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.RDFNode;

import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;


/** This reads the ontology to determine the user privileges
 * 
 * 
 * @author edwardblurock
 *
 */
public class UserQueries {


	public static boolean isSuperUser(UserSessionData user) throws IOException {
		boolean ans = false;
		String name = user.getUserName();
		if(name != null) {
			ans = name.compareTo(UserAccountKeys.accessTypeSuperUser) == 0;
		} else {
			throw new IOException("From isSuperUser: username is null\n" + user.toString());
		}
		return ans;
	}
	public static Set<String> getListOfQueryPriviledges(UserSessionData user) {
		return getListOfPriviledges(user,"dataset:AccountPriviledgeDataQuery");
	}
	public static Set<String> getListOfInputPriviledges(UserSessionData user) {
		return getListOfPriviledges(user,"dataset:AccountPriviledgeDataInput");
	}
	public static Set<String> getListOfModifyPriviledges(UserSessionData user) {
		return getListOfPriviledges(user,"dataset:AccountPriviledgeDataModify");
	}
	public static Set<String> getListOfPriviledges(UserSessionData user, String type) {
		HashSet<String> privset = new HashSet<String>();
		if(user != null) {
			String accessLevel = user.getUserLevel();
			String query = 
				"SELECT ?priviledge\n" + 
				"WHERE {" + accessLevel + " rdfs:subClassOf ?object .\n" +
				"	?object owl:onProperty <http://purl.org/dc/terms/accessRights> .\n" + 
				"	?object owl:onClass ?priviledge \n" + 
				"}";
		List<Map<String, RDFNode>> acclst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(acclst);
		List<String> priviledgeset = OntologyBase.isolateProperty("priviledge", stringlst);
		for(String priv : priviledgeset) {
			if(GenericSimpleQueries.isSubClassOf(priv, type, false)) {
				privset.add(priv);
			} else if(GenericSimpleQueries.isSameClass(priv, type)) {
				List<String> privs = GenericSimpleQueries.listOfSubClasses(type, true);
				privset.addAll(privs);
			} else if(GenericSimpleQueries.isSubClassOf(type, priv, true)) {
				List<String> privs = GenericSimpleQueries.listOfSubClasses(type, true);
				privset.addAll(privs);				
			}
		}
		} 
		return privset;
	}
	
	public static boolean allowedTask(String task, String userlevel) {
		String query = "ASK\n" + 
				"	{ " + task + " rdfs:subClassOf ?taskobject .\n" + 
				"		" + userlevel + " rdfs:subClassOf ?userobject .\n" + 
				"		?taskobject owl:onProperty <http://purl.org/dc/terms/requires> .\n" + 
				"       ?taskobject owl:onClass ?priviledge .\n" + 
				"      { ?userobject owl:onProperty <http://purl.org/dc/terms/accessRights> .\n" + 
				"        ?userobject owl:onClass ?priviledge }\n" + 
				
				"      UNION\n" + 
				"      { ?userobject owl:onProperty <http://purl.org/dc/terms/accessRights> .\n" + 
				"        ?userobject owl:onClass ?superpriv .\n" + 
				"        ?priviledge rdfs:subClassOf ?superpriv }\n" +
				//"      ?user rdfs:subClassOf dataset:UserRoleChoices\n" +
				"}";
		
		boolean ans = OntologyBase.datasetASK(query);
		return ans;
	}
}
