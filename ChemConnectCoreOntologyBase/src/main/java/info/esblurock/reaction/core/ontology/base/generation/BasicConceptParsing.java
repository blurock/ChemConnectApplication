package info.esblurock.reaction.core.ontology.base.generation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.vocabulary.ReasonerVocabulary;

import info.esblurock.reaction.chemconnect.core.base.concepts.StandardConceptAnnotations;
import info.esblurock.reaction.core.ontology.base.OntologyBase;

public class BasicConceptParsing {
	
	public static Set<String> completeConceptListWithRecordsAndSuperClass(String concept) {
		Set<String> total = new HashSet<String>();
		List<String> lst = getAllSubElementsInclusive(concept);
		completeConceptListWithRecordsAndSuperClass(lst,total);
		return total;
	}
	/** This lists all the subclasses of the top concept
	 * 
	 * @param structure: The top level concept
	 * @return a list of subclasses
	 * 
	 */
	static public List<String> getAllSubElementsInclusive(String concept) {
		String query = "SELECT ?subclass\n" 
				+ "	WHERE {\n" 
				+ "   ?subclass rdfs:subClassOf " + concept + "}\n";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		List<String> sublist = new ArrayList<String>();
		for(Map<String, String> map : stringlst) {
			String subclassS = map.get("subclass");
			sublist.add(subclassS);
		}
		return sublist;
	}
	
	/** This retrieves the standard set of annotations
	 * @param concept: The concept
	 * @return The annotations, if null, some or all of annotations were not in the concept
	 * 
	 */
	static public StandardConceptAnnotations getAnnotations(String concept) {
		String query = "SELECT ?type ?identifier ?comment ?label ?altlabel\n" 
				+ "	WHERE {\n"
				+ "              " + concept + " <http://purl.org/dc/elements/1.1/type> ?type .\n" + 
				"                " + concept + " <http://purl.org/dc/terms/identifier> ?identifier .\n" + 
				"                " + concept + " rdfs:comment ?comment .\n" + 
				"                " + concept + " rdfs:label ?label .\n" + 
				"                " + concept + " <http://www.w3.org/2004/02/skos/core#altLabel> ?altlabel\n"
				+ "}";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		StandardConceptAnnotations annotations = null;
		if(stringlst.isEmpty()) {
			
		} else {
		for(Map<String, String> map : stringlst) {
			String typeS = map.get("type");
			String identifierS = map.get("identifier");
			String commentS = map.get("comment");
			String labelS = map.get("label");
			String altlabelS = map.get("altlabel");
			annotations = new StandardConceptAnnotations(concept, labelS,commentS,altlabelS,typeS,identifierS);
		}
		}
		return annotations;
	}

	/** Find the subobjects with the qualifications of link type and whether singlet or multiple
	 * 
	 * @param concept: The concept to find sub objects  
	 * @param link  The type of link to sub object
	 * @param multiple true: find multiple links, otherwise single links
	 * @return A list of subobjects having the criteria
	 * 
	 * The sub objects found are those defined within the concept. Those that are inheireted are not given.
	 * 
	 */
	public static List<String> subObjectsOfConcept(String concept, String link, boolean multiple) {

		String query = "SELECT ?sub\n" 
		        + "	WHERE {"
				+ "     " + concept + "<" + ReasonerVocabulary.directSubClassOf +"> ?subobject . \n"
				+ "		?subobject  owl:onProperty " + link + " .\n";
		String app = "     ?subobject  owl:onClass ?sub";
		if(multiple) {
			app = "     ?subobject  owl:someValuesFrom ?sub";
		}
		query += app + "\n}\n";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);

		ArrayList<String> supers = new ArrayList<String>();
		for (Map<String, String> map : stringlst) {
			String sup = map.get("sub");
			supers.add(sup);
		}
		return supers;
	}
	
	public static void completeConceptListWithRecordsAndSuperClass(List<String> objs, Set<String> total) {
		int origsize = total.size();
		total.addAll(objs);
		if(total.size() > origsize) {
			for(String concept : objs) {
				completeConcept(concept,total);
			}
		}
	}
	
	public static void completeConcept(String concept, Set<String> total) {
		if(concept != null) {
			if(concept.length() != 0) {
				String superclass = findSuperClass(concept);
				if(superclass.length() > 0) {
					total.add(superclass);
				}
				if(concept.length() > 0) {
					total.add(concept);
				}
				ArrayList<String> sublst = new ArrayList<String>();
				sublst.add(concept);
				List<String> records = subObjectsOfConcept(concept, "<http://www.w3.org/ns/dcat#record>", false);
				List<String> multrecords = subObjectsOfConcept(concept, "<http://www.w3.org/ns/dcat#record>", true);
				sublst.addAll(records);
				sublst.addAll(multrecords);
				completeConceptListWithRecordsAndSuperClass(sublst,total);
			}
		}
	}
	
	public static String findSuperClass(String concept) {

		String query = "SELECT ?super\n" 
		        + "	WHERE {"
		        + concept + "  <" + ReasonerVocabulary.directSubClassOf +"> ?super \n"
				+ " }\n";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String superS = "";
		for(Map<String, String> map : stringlst) {
			String sS = map.get("super");
			if(sS.compareTo(concept) != 0) {
				if(sS.startsWith("dataset:")) {
					superS = sS;
				}
			}
		}
		return superS;
	}

	public static StandardInformation findStandardInformation(String concept) {
		String superClass = findSuperClass(concept);
		if(superClass.length() == 0) {
			superClass = "DatabaseObject";
		}
		StandardConceptAnnotations annotations = getAnnotations(concept);
		StandardInformation information = new StandardInformation(annotations, superClass);
		
		String compSuperClass = findSuperClass(concept);
		if(compSuperClass.length() == 0) {
			compSuperClass = "DatabaseObject";
		}
		
		List<String> singlets = subObjectsOfConcept(concept, "<http://purl.org/dc/terms/hasPart>", false);
		for(String sing : singlets) {
			StandardConceptAnnotations compannotations = getAnnotations(sing);
			StandardStandardHasPartComponent haspart = new StandardStandardHasPartComponent(compannotations, compSuperClass);
			information.addInformation(haspart);
		}
		
		List<String> records = subObjectsOfConcept(concept,"<http://www.w3.org/ns/dcat#record>",false);
		for(String record : records) {
			StandardConceptAnnotations compannotations = getAnnotations(record);
			StandardStandardRecord recordgen = new StandardStandardRecord(compannotations, compSuperClass);
			information.addInformation(recordgen);
		}
		
		List<String> multrecords = subObjectsOfConcept(concept,"<http://www.w3.org/ns/dcat#record>",true);
		for(String multrecord : multrecords) {
			StandardConceptAnnotations compannotations = getAnnotations(multrecord);
			StandardStandardRecordMultiple recordgen = new StandardStandardRecordMultiple(compannotations, compSuperClass);
			information.addInformation(recordgen);
		}

		return information;
	}
	

}
