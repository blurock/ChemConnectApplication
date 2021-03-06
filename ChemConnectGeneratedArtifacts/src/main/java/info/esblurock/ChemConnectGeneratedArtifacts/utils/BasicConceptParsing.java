package info.esblurock.ChemConnectGeneratedArtifacts.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.vocabulary.ReasonerVocabulary;

import info.esblurock.reaction.chemconnect.core.base.concepts.StandardConceptAnnotations;
import info.esblurock.reaction.core.ontology.base.OntologyBase;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;

public class BasicConceptParsing {
	

	
	public static Set<String> completeConceptListWithRecordsAndSuperClass(String concept) {
		Set<String> total = new HashSet<String>();
		List<String> lst = getAllSubElementsInclusive(concept);
		completeConceptListWithRecordsAndSuperClass(lst,total);
		return total;
	}
	/** This lists all the subclasses of the top concept (with an identifier)
	 * 
	 * @param structure: The top level concept
	 * @return a list of subclasses (with an identifer)
	 * 
	 * The requirement that it has an identifier is to differentiate between properties and sub concepts.
	 * All concepts should have an identifer, properties as subclasses do not.
	 * 
	 */
	static public List<String> getAllSubElementsInclusive(String concept) {
		String query = "SELECT ?subclass\n" 
				+ "	WHERE {\n" 
				+ "   ?subclass rdfs:subClassOf " + concept + " ."
						+ " ?subclass <http://purl.org/dc/terms/identifier> ?identifier"
						+ "}\n";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		List<String> sublist = new ArrayList<String>();
		for(Map<String, String> map : stringlst) {
			String subclassS = map.get("subclass");
			sublist.add(subclassS);
		}
		return sublist;
	}
	
	/** Get the direct subclasses of a concept (concept must have an identifier)
	 * 
	 * @param concept The concept
	 * @return The list of subclasses of the concept (that have an identifier)
	 * 
	 * The concept is not listed among the subclasses
	 */
	public static List<String> directSubConceptOfConcept(String concept) {
		String query = "SELECT ?object ?id\n" 
		        + "	WHERE {"
				+ "?object <" + ReasonerVocabulary.directSubClassOf +"> " + concept + " . \n"
						+ "?object <http://purl.org/dc/terms/identifier> ?id"
						+ "}";
		
		System.out.println("directSubConceptOfConcept: \n" + query);
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);

		System.out.println(stringlst);
		
		ArrayList<String> subcls = new ArrayList<String>();
		for (Map<String, String> map : stringlst) {
			String sup = map.get("object");
			if(!sup.matches(concept)) {
			System.out.println("Object: " + sup + "\t    label: '" + map.get("id") + "'");
			subcls.add(sup);
			}
		}
		return subcls;
		
	}
	
	/** This finds the direct super concept of a given concept (with an identifier)
	 * @param concept The concept for which to find super class
	 * @return (one of the) super classes of the concept (having an identifier)
	 * 
	 * This routine implicitely assumes that there is only one superclass.
	 * If there is more than one superclass, then this routine should not be used.
	 * 
	 */
	public static String findSuperClass(String concept) {
		String query = "SELECT ?super\n" 
		        + "	WHERE {"
		        + concept + "  <" + ReasonerVocabulary.directSubClassOf +"> ?super ."
		        		+ "?super <http://purl.org/dc/terms/identifier> ?id"
				+ " }\n";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String superS = "";
		for(Map<String, String> map : stringlst) {
			String sS = map.get("super");
			superS = sS;
		}
		return superS;
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
			System.out.println("StandardConceptAnnotations getAnnotations: " + concept);
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
				List<String> records = DatasetOntologyParseBase.subObjectsOfConcept(concept, "<http://www.w3.org/ns/dcat#record>", false);
				List<String> multrecords = DatasetOntologyParseBase.subObjectsOfConcept(concept, "<http://www.w3.org/ns/dcat#record>", true);
				sublst.addAll(records);
				sublst.addAll(multrecords);
				completeConceptListWithRecordsAndSuperClass(sublst,total);
			}
		}
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
		
		List<String> singlets = DatasetOntologyParseBase.subObjectsOfConcept(concept, "<http://purl.org/dc/terms/hasPart>", false);
		for(String sing : singlets) {
			StandardConceptAnnotations compannotations = getAnnotations(sing);
			StandardStandardHasPartComponent haspart = new StandardStandardHasPartComponent(compannotations, compSuperClass);
			information.addInformation(haspart);
		}
		
		List<String> records = DatasetOntologyParseBase.subObjectsOfConcept(concept,"<http://www.w3.org/ns/dcat#record>",false);
		for(String record : records) {
			StandardConceptAnnotations compannotations = getAnnotations(record);
			StandardStandardRecord recordgen = new StandardStandardRecord(compannotations, compSuperClass);
			information.addInformation(recordgen);
		}
		
		List<String> multrecords = DatasetOntologyParseBase.subObjectsOfConcept(concept,"<http://www.w3.org/ns/dcat#record>",true);
		for(String multrecord : multrecords) {
			StandardConceptAnnotations compannotations = getAnnotations(multrecord);
			StandardStandardRecordMultiple recordgen = new StandardStandardRecordMultiple(compannotations, compSuperClass);
			information.addInformation(recordgen);
		}

		return information;
	}
	
	public static Set<String> findDependentModules(String module) {
		String query = "SELECT ?object\n" + 
				"	WHERE { " + module + " rdfs:subClassOf ?object  .\n" + 
				"	?object rdfs:subClassOf dataset:ChemConnectModule\n" + 
				"}";
		List<String> depsL = OntologyBase.isolateProperty(query, "object");
		Set<String> depsS = new HashSet<String>(depsL);
		depsS.remove(module);
		return depsS;
	}

}
