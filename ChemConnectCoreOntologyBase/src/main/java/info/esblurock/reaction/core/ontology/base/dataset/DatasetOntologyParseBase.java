package info.esblurock.reaction.core.ontology.base.dataset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.vocabulary.ReasonerVocabulary;

import info.esblurock.reaction.chemconnect.core.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.utilities.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.base.transfer.CompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;
import info.esblurock.reaction.core.ontology.base.OntologyBase;
import info.esblurock.reaction.chemconnect.core.base.transfer.ChemConnectRecordInformation;
import info.esblurock.reaction.chemconnect.core.base.transfer.ChemConnectDataElementInformation;

public class DatasetOntologyParseBase {
	/**
	 * @param structure The ID structure object (subclass of ID)
	 * @return The object the ID refers to
	 * 
	 *         ?type: The actual object type ?id: The identifier of the object
	 *         ?super: The superclass of the object
	 * 
	 *         SELECT ?id ?type ?super WHERE { dataset:OrganizationID Concept
	 *         <http://purl.org/dc/terms/references> ?type . ?type
	 *         <http://purl.org/dc/terms/identifier> ?id . ?type rdfs:subClassOf
	 *         ?super . ?super rdfs:subClassOf dcat:Dataset }
	 * @throws IOException
	 */
	static public DataElementInformation getSubElementStructureFromIDObject(String structure) {
		String query = "SELECT ?id ?type ?super ?altl\n" + "	WHERE {\n" + "   " + structure
				+ " <http://purl.org/dc/elements/1.1/type> ?type .\n" + "	" + structure
				+ " <http://purl.org/dc/terms/identifier> ?id .\n" + "	" + structure + " rdfs:subClassOf ?super .\n"
				+ " " + structure + " <http://www.w3.org/2004/02/skos/core#altLabel> ?altl\n" + "  }";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		DataElementInformation info = null;
		if (stringlst.size() > 0) {
			String idS = stringlst.get(0).get("id");
			String typeS = stringlst.get(0).get("type");
			// String superS = stringlst.get(0).get("super");
			String altlabelS = stringlst.get(0).get("altl");
			info = new DataElementInformation(structure, null, true, 1, typeS, idS, altlabelS);
		}
		if (info == null) {
			System.out.println("Structural information not found: " + structure);
		}
		return info;
	}

	public static String getTypeFromCanonicalDataType(String candatatype) {
		// System.out.println("getTypeFromCanonicalDataType: '" + candatatype);
		int pos = candatatype.lastIndexOf('.');
		String datatype = candatatype.substring(pos + 1);
		String type = getTypeFromDataType(datatype);
		// System.out.println("getTypeFromCanonicalDataType: '" + datatype + "' '" +
		// type);
		return type;
	}

	public static String getTypeFromDataType(String datatype) {
		String query = "SELECT ?type\n" 
				+ "	WHERE { ?type <http://purl.org/dc/elements/1.1/type> \"" + datatype
				+ "\"^^xsd:string\n" + "}";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		String type = null;
		if (lst.size() > 0) {
			List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
			Map<String, String> map = stringlst.get(0);
			type = map.get("type");
		}
		return type;
	}

	/*
	 * findClassHierarchy("dataset:ChemConnectPrimitiveDataStructure")
	 * dataset:ChemConnectPrimitiveDataStructure: dataset:DateElement:
	 * dataset:DateIssued: dataset:DateCreated: dataset:OneLine:
	 * dataset:ReferenceTitle: dataset:ReferenceString: dataset:DescriptionTitle:
	 * dataset:ShortStringLabel: dataset:familyName: dataset:givenName:
	 * dataset:OrganizationName: . . . .
	 */
	public static HierarchyNode findClassHierarchy(String topNode) {
		ClassificationInformation info = getIdentificationInformation(topNode);
		String comment = getComment(topNode);
		if (comment == null) {
			comment = topNode;
		}
		String label = getLabel(topNode);
		if (label == null) {
			label = topNode;
		}
		HierarchyNode top = new HierarchyNode(topNode, label, comment, info);
		List<String> structurelst = getSubClasses(topNode);
		for (String subclass : structurelst) {
			HierarchyNode node = findClassHierarchy(subclass);
			top.addSubNode(node);
		}

		return top;
	}

	public static ClassificationInformation getIdentificationInformation(String dataElementName) {
		DataElementInformation dataelement = new DataElementInformation(dataElementName, null, true, 0, null, null,
				null);
		return getIdentificationInformation(null, dataelement);
	}

	/**
	 * @param id
	 * @return
	 */
	public static ClassificationInformation getIdentificationInformation(DatabaseObject top,
			DataElementInformation element) {

		String id = element.getDataElementName();
		String query = "SELECT ?identifier ?datatype\n" + "	WHERE {\n" + id
				+ " <http://purl.org/dc/terms/identifier> ?identifier .\n" + "	{  " + id
				+ " <http://purl.org/dc/elements/1.1/type>  ?datatype } \n" + "UNION\n" + "	{ " + id + " <"
				+ ReasonerVocabulary.directSubClassOf + "> ?subclass .\n"
				+ "	   ?subclass <http://purl.org/dc/elements/1.1/type>  ?datatype\n" + "	}" + "  }";

		ClassificationInformation classification = null;
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		if (lst.size() > 0) {
			List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
			Map<String, String> map = stringlst.get(0);
			String identifier = map.get("identifier");
			String datatype = map.get("datatype");
			classification = new ClassificationInformation(top, element.getLink(), id, identifier, datatype);
		}
		return classification;
	}

	public static ArrayList<String> asSubObject(String object) {

		String query = "SELECT ?object ?sub ?type\n" + "	WHERE { ?sub <http://purl.org/dc/elements/1.1/type> \""
				+ object + "\"^^xsd:string .\n" + "        ?object <http://purl.org/dc/elements/1.1/type> ?type .\n"
				+ "		?object rdfs:subClassOf ?subobject .\n" + "		{ \n"
				+ "		  {?subobject  owl:onProperty <http://purl.org/dc/terms/hasPart> }\n"
				+ "                                            UNION \n"
				+ "		  {?subobject  owl:onProperty dcat:record }\n" + "		} .\n" + "		{\n"
				+ "		  { ?subobject  owl:onClass ?sub }\n" + "                                             UNION\n"
				+ "		  { ?subobject  owl:someValuesFrom ?sub }\n" + "                                   }\n" + "}";

		// System.out.println(query);

		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);

		ArrayList<String> supers = new ArrayList<String>();
		for (Map<String, String> map : stringlst) {
			String sup = map.get("type");
			supers.add(sup);
		}
		return supers;
	}

	/**
	 * From the structure/substructure name, return the list of
	 * DataElementInformation
	 * 
	 * @param structure This is the data structure or substructure.
	 * @return A list of DataElementInformation, one for each sub-item within the
	 *         structure.
	 * 
	 *         ?sub: The general type of the object ?card The number of times the
	 *         object can be repeated ?substructure The sub element type ?id The
	 *         identifier key for the subelement ?superclass
	 * 
	 *         SELECT ?sub ?pred ?card ?superclass ?id ?substructure WHERE {
	 *         dataset:ContactInfoData rdfs:subClassOf ?sub . { { ?sub owl:onClass
	 *         ?substructure . ?sub owl:qualifiedCardinality ?card } UNION { ?sub
	 *         owl:someValuesFrom|owl:allValuesFrom ?substructure} } . ?sub ?pred
	 *         ?substructure . ?substructure <http://purl.org/dc/terms/identifier>
	 *         ?id . }
	 * 
	 *         :ContactLocationInformation rdfs:isDefinedBy
	 *         <http://www.w3.org/2006/vcard/ns#Location> ;
	 *         <http://purl.org/dc/elements/1.1/type>
	 *         "ContactLocationInformation"^^xsd:string ; rdfs:label "Contact
	 *         Location Information"^^xsd:string .
	 * 
	 * 
	 *         :ContactLocationInformation rdf:type owl:Class ; rdfs:subClassOf
	 *         :ChemConnectCompoundDataStructure , :ChemConnectDataUnit , [ rdf:type
	 *         owl:Restriction ; owl:onProperty <http://purl.org/dc/terms/hasPart> ;
	 *         owl:qualifiedCardinality "1"^^xsd:nonNegativeInteger ; owl:onClass
	 *         :LocationAddress ] ,
	 * 
	 * 
	 *         ### http://www.esblurock.info/dataset#LocationAddress
	 *         :LocationAddress rdf:type owl:NamedIndividual ;
	 *         <http://purl.org/dc/terms/identifier>
	 *         "vcard:street-address"^^xsd:string .
	 * 
	 */
	public static CompoundDataStructure subElementsOfStructure(String structure) {
		String query = "SELECT ?sub  ?pred ?card ?link ?id ?substructure ?subtype ?subsubtype ?altl\n" + "	WHERE {\n"
				+ "      " + structure + " rdfs:subClassOf ?sub .\n" + "		{\n"
				+ "        {  ?sub owl:onClass ?substructure  . \n"
				+ "           ?sub owl:qualifiedCardinality ?card }\n" + "		      UNION\n"
				+ "		   {   ?sub owl:someValuesFrom|owl:allValuesFrom ?substructure}" + "      } .\n"
				+ "		   ?sub ?pred ?substructure .\n" + "        ?sub owl:onProperty ?link .\n"
				+ "        ?substructure" + " <http://purl.org/dc/terms/identifier> ?id .\n"
				+ "        ?substructure <http://www.w3.org/2004/02/skos/core#altLabel> ?altl \n" + "       }";
		// System.out.println(query);
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		CompoundDataStructure info = new CompoundDataStructure(structure);
		for (Map<String, String> map : stringlst) {
			String substructure = map.get("substructure");
			String pred = map.get("pred");
			String link = map.get("link");
			String altl = map.get("altl");
			boolean singlet = true;
			int numberOfElements = 1;
			String numS = map.get("card");
			if (numS != null)
				numberOfElements = Integer.parseInt(numS);
			else
				numberOfElements = 0;
			if (pred.compareTo("owl:someValuesFrom") == 0) {
				singlet = false;
			} else if (pred.compareTo("owl:onClass") == 0) {
				singlet = false;
				if (numberOfElements == 1)
					singlet = true;
			}
			String identifier = (String) map.get("id");
			String chemconnect = getChemConnectDirectTypeHierarchy(substructure);
			DataElementInformation element = new DataElementInformation(substructure, link, singlet, numberOfElements,
					chemconnect, identifier, altl);
			info.add(element);
		}
		return info;
	}

	/**
	 * @param name The element name
	 * @return The ChemConnect type
	 * 
	 *         dataset:ContactSiteOf ---> HTTPAddress
	 * 
	 */
	public static String getChemConnectDirectTypeHierarchy(String name) {
		String direct = "SELECT ?type\n" + "	WHERE {\n" + name + " <http://purl.org/dc/elements/1.1/type> ?type .\n"
				+ "	}";
		String objname = getHierarchString(direct);
		if (objname == null) {

			String sub = "SELECT ?object ?type\n" + "	WHERE {\n" + "     " + name + "  rdfs:subClassOf ?object .\n"
					+ "		?object <http://purl.org/dc/elements/1.1/type> ?type\n" + "	}";
			objname = getHierarchString(sub);
		}
		return objname;
	}

	/**
	 * @param query query with the object
	 * @return The corresponding ChemConnect structure of the general type.
	 * 
	 *         ?object: The general type of object ?type: The corresponding
	 *         ChemConnect structure of the general type
	 * 
	 *         SELECT ?object ?type WHERE { dataset:ContactHasSite rdfs:subClassOf
	 *         ?object . ?object <http://purl.org/dc/elements/1.1/type> ?type }
	 * 
	 *         ### http://www.esblurock.info/dataset#ContactHasSite :ContactHasSite
	 *         rdf:type owl:Class ; rdfs:subClassOf :HTTPAddress .
	 * 
	 *         ### http://www.esblurock.info/dataset#HTTPAddress :HTTPAddress
	 *         rdf:type owl:Class ; rdfs:subClassOf
	 *         :ChemConnectPrimitiveDataStructure ;
	 *         <http://purl.org/dc/elements/1.1/type> "HTTPAddress"^^xsd:string ;
	 *         rdfs:isDefinedBy <http://purl.org/spar/fabio/hasURL> ; rdfs:label
	 *         "HTTP address data"^^xsd:string .
	 * 
	 */
	public static String getHierarchString(String query) {
		List<Map<String, RDFNode>> maplst = OntologyBase.resultSetToMap(query);
		String str = null;
		if (maplst.size() > 0) {
			Map<String, RDFNode> nodemap = maplst.get(0);
			RDFNode node = nodemap.get("type");
			Literal literal = node.asLiteral();
			str = (String) literal.getValue();
		}
		return str;
	}

	public static String getComment(String concept) {
		String ans = ChemConnectCompoundDataStructure.removeNamespace(concept);
		String query = "SELECT ?comment\n" + "WHERE {\n" + concept + " rdfs:comment ?comment\n" + "}";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		for (Map<String, String> map : stringlst) {
			String subcomment = map.get("comment");
			if (subcomment != null) {
				ans = subcomment;
			}
		}
		return ans;
	}

	public static String getLabel(String concept) {
		String ans = ChemConnectCompoundDataStructure.removeNamespace(concept);
		String query = "SELECT ?label\n" + "WHERE {\n" + concept + " rdfs:label ?label\n" + "}";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		for (Map<String, String> map : stringlst) {
			String sublabel = map.get("label");
			if (sublabel != null) {
				ans = sublabel;
			}
		}
		return ans;
	}

	/*
	 * This returns the direct set of subclasses. First all subclasses are found,
	 * then for each element all their subclasses are eliminated based on the fact
	 * that rdfs:subClassOf returns all subclasses
	 * 
	 * getSubClasses("dataset:ChemConnectPrimitiveDataStructure");
	 * [dataset:DateElement, dataset:OneLine, dataset:ShortStringLabel,
	 * dataset:Email, dataset:Classification, dataset:HTTPAddress, dataset:GPSData,
	 * dataset:Paragraph, dataset:DatabaseID, dataset:Keyword,
	 * dataset:PasswordElement]
	 * 
	 */
	public static List<String> getSubClasses(String topclassS) {
		List<String> structurelst = getAllSubClasses(topclassS);
		structurelst.remove(topclassS);
		ArrayList<String> toplist = new ArrayList<String>(structurelst);
		for (String name : structurelst) {
			List<String> subs = getAllSubClasses(name);
			toplist.removeAll(subs);
		}
		return toplist;
	}

	/*
	 * Finds all subclasses: based on that rdfs:subClassOf returns all subclasses
	 * 
	 * getAllSubClasses("dataset:Classification"); [dataset:UnitClass,
	 * dataset:LocationCountry, dataset:LocationCity, dataset:UnitsOfValue,
	 * dataset:DataCatalog, dataset:DataAccess, dataset:UserAccountRole,
	 * dataset:DataOwner, dataset:OrganizationClassification,
	 * dataset:UserClassification, dataset:SourceKey, dataset:UserTitle,
	 * dataset:DataCreator]
	 * 
	 */
	public static List<String> getAllSubClasses(String topclassS) {
		String query = "SELECT ?directSub\n" + " WHERE { ?directSub rdfs:subClassOf " + topclassS + " .\n" + " }";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		List<String> structurelst = OntologyBase.isolateProperty("directSub", stringlst);
		structurelst.remove(topclassS);
		return structurelst;
	}

	public static ArrayList<String> typesWithExtension(String extension) {
		ArrayList<String> typelst = new ArrayList<String>();
		String query = "SELECT ?type \n" + "	WHERE {?type dataset:fileextension  \"" + extension
				+ "\"^^xsd:string }";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		for (Map<String, String> maptype : stringlst) {
			String exttype = maptype.get("type");
			if (exttype != null) {
				typelst.add(exttype);
			}
		}
		return typelst;
	}

	public static String findObjectTypeFromLinkConcept(String linkconcept) {
		String query = "SELECT ?objecttype \n" + "	WHERE { " + linkconcept + " rdfs:subClassOf ?object .\n"
				+ "		?object owl:onProperty <http://www.w3.org/2004/02/skos/core#related> .\n"
				+ "		?object owl:onClass ?objecttype }";

		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String structuretype = null;
		if (stringlst.size() > 0) {
			Map<String, String> result = stringlst.get(0);
			structuretype = result.get("objecttype");
		}
		return structuretype;
	}

	public static boolean isAChemConnectPrimitiveDataStructure(String unit) {
		String query = "ASK {" + "	" + unit + " rdfs:subClassOf dataset:ChemConnectPrimitiveDataStructure }";
		boolean result = OntologyBase.datasetASK(query);
		return result;
	}

	public static String getFileExtension(String fileType) {
		String query = "SELECT ?type\n" + "	WHERE {\n" + "   " + fileType + " dataset:fileextension ?type }";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String type = null;
		if (stringlst.size() > 0) {
			Map<String, String> result = stringlst.get(0);
			type = result.get("type");
		}
		return type;
	}

	public static String getStructureFromFileType(String filetype) {
		String query = "SELECT ?filetype\n" + "WHERE {\n" + filetype + " rdfs:subClassOf* ?subclass .\n"
				+ "  ?subclass owl:onProperty <http://purl.org/linked-data/cube#structure> .\n"
				+ "  ?subclass owl:onClass ?filetype\n" + "}";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String type = null;
		if (stringlst.size() > 0) {
			Map<String, String> result = stringlst.get(0);
			type = result.get("filetype");
		}
		return type;

	}

	/**
	 * @param objid
	 * @return public static String objectFromIdentifier(String objid) { String
	 *         query = "SELECT ?ref\n" + " WHERE {\n" + " ?ref
	 *         <http://purl.org/dc/terms/identifier> " + objid + " }";
	 * 
	 *         List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
	 *         List<Map<String, String>> stringlst =
	 *         OntologyBase.resultmapToStrings(lst); String id = null; if
	 *         (stringlst.size() > 0) { Map<String, String> map = stringlst.get(0);
	 *         id = (String) map.get("ref"); } return id;
	 * 
	 *         }
	 * 
	 */

	public static String getStructureFromDataStructure(String object) {
		String query = "SELECT ?ref\n" + "	WHERE {\n" + "?ref <http://purl.org/dc/elements/1.1/type> \"" + object
				+ "\"^^xsd:string   \n" + "  }";

		String structure = null;
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		if (stringlst.size() > 0) {
			structure = stringlst.get(0).get("ref");
		}
		return structure;
	}

	public static String getContentType(String yamlFileType) {
		String query = "SELECT ?type\n" + "	WHERE {\n"
				+ "   dataset:FileTypeYaml <http://purl.org/dc/elements/1.1/type> ?type }";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String type = null;
		if (stringlst.size() > 0) {
			Map<String, String> result = stringlst.get(0);
			type = result.get("type");
		}
		return type;
	}

	/*
	 * DataType: dataset:ChemConnectDataStructure Records
	 * dataset:DescriptionDataData: dcat:record (singlet) dataset:DataSetReference:
	 * dcat:record (some) LinkedTo dataset:Consortium: org:linkedTo (some) Other
	 * 
	 * MapToChemConnectCompoundDataStructure ChemConnectCompoundDataStructure:
	 * dataset:DataSetReference dataset:ReferenceTitle(dcterms:hasPart):
	 * dcterms:title (OneLine): single dataset:NameOfPerson(dcterms:hasPart):
	 * foaf:name (NameOfPerson): multiple dataset:ReferenceString(dcterms:hasPart):
	 * dcterms:description (OneLine): single dataset:DOI(dcterms:hasPart):
	 * datacite:PrimaryResourceIdentifier (ShortStringLabel): single
	 * -------------------------------------------------------
	 * 
	 * ChemConnectCompoundDataStructure: dataset:DescriptionDataData
	 * dataset:DateCreated(dcterms:hasPart): dcterms:created (DateObject): single
	 * dataset:SourceKey(dcterms:hasPart): dcat:dataset (Classification): single
	 * dataset:DataSpecification(dcterms:hasPart): dataset:DataSpecification
	 * (DataSpecification): single dataset:DescriptionTitle(dcterms:hasPart):
	 * dcterms:title (OneLine): single dataset:DescriptionAbstract(dcterms:hasPart):
	 * dcterms:description (Paragraph): single
	 * dataset:DescriptionKeyword(dcterms:hasPart): dcat:keyword (Keyword): multiple
	 * -------------------------------------------------------
	 * 
	 * ChemConnectCompoundDataStructure: dataset:NameOfPerson
	 * dataset:givenName(dcterms:hasPart): foaf:givenName (ShortStringLabel): single
	 * dataset:familyName(dcterms:hasPart): foaf:familyName (ShortStringLabel):
	 * single dataset:UserTitle(dcterms:hasPart): foaf:title (Classification):
	 * single -------------------------------------------------------
	 * 
	 * 
	 */
	public static ChemConnectDataElementInformation getChemConnectDataStructure(String elementStructure) {
		ClassificationInformation classification = getIdentificationInformation(elementStructure);
		ChemConnectDataElementInformation set = new ChemConnectDataElementInformation(classification);
		getSetOfChemConnectDataStructureElements(elementStructure, set);
		return set;
	}

	/*
	 * SELECT ?record ?property ?cardinality WHERE {
	 * dataset:ChemConnectDataStructure rdfs:subClassOf ?sub . ?sub owl:onProperty
	 * ?property . {?sub owl:someValuesFrom ?record } UNION {?sub owl:onClass
	 * ?record . ?sub owl:qualifiedCardinality ?cardinality} }
	 * [{record=dataset:DescriptionDataData, property=dcat:record, cardinality=1},
	 * {record=dataset:Consortium, property=org:linkedTo},
	 * {record=dataset:DataSetReference, property=dcat:record}]
	 * 
	 */
	public static void getSetOfChemConnectDataStructureElements(String element, ChemConnectDataElementInformation set) {
		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+ "PREFIX dataset: <http://www.esblurock.info/dataset#>\n"
				+ "PREFIX core: <http://www.w3.org/2004/02/skos/core#>\n"
				+ "SELECT ?record ?property ?cardinality ?identifier ?datatype ?altl\n" + " WHERE { " + element
				+ "  rdfs:subClassOf  ?sub .\n" + "	?sub owl:onProperty ?property .\n"
				+ "		{?sub owl:someValuesFrom ?record   }\n" + "	UNION\n"
				+ "		{?sub owl:onClass ?record . ?sub owl:qualifiedCardinality ?cardinality}\n"
				+ " ?record <http://purl.org/dc/terms/identifier> ?identifier .\n"
				+ "   ?record <http://purl.org/dc/elements/1.1/type>  ?datatype .\n" + "   ?record core:altLabel ?altl"
				+ "  }";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		for (Map<String, String> map : stringlst) {
			String linktype = map.get("property");
			String elementType = map.get("record");
			String cardinalityS = map.get("cardinality");
			String identifierS = map.get("identifier");
			String datatypeS = map.get("datatype");
			String altlabelS = map.get("altl");
			boolean singlet = true;
			int cardinality = 0;
			if (cardinalityS != null) {
				cardinality = Integer.parseInt(cardinalityS);
				if (cardinality > 1) {
					singlet = false;
				}
			} else {
				singlet = false;
			}
			DataElementInformation e = new DataElementInformation(elementType, linktype, singlet, cardinality,
					datatypeS, identifierS, altlabelS);
			CompoundDataStructure record = null;
			if (linktype.compareTo("dcat:record") == 0) {
				record = subElementsOfStructure(e.getDataElementName());
				subElements(record, set);
			}
			set.addElement(e, record);

		}
	}

	public static void subElements(CompoundDataStructure record, ChemConnectDataElementInformation set) {
		for (DataElementInformation recordelement : record) {
			if (isChemConnectPrimitiveDataStructure(recordelement.getDataElementName()) == null) {
				if (recordelement.getLink().compareTo("dcterms:hasPart") == 0) {
					CompoundDataStructure subrecord = subElementsOfStructure(recordelement.getDataElementName());
					set.addToMapping(subrecord);
					subElements(subrecord, set);
				}
			} else {
			}
		}
	}

	/*
	 * The key to the search is that the element is a compound element
	 * (ChemConnectPrimitiveCompound) if not a compound element, then the search
	 * returns no values. The class name returned should return just the direct
	 * super class.
	 * 
	 * SELECT DISTINCT ?super WHERE { dataset:NameOfPerson rdfs:subClassOf*
	 * dataset:ChemConnectPrimitiveDataStructure . dataset:NameOfPerson
	 * rdfs:subClassOf ?super . ?super rdf:type owl:Class } [{super=dataset:Name},
	 * {super=dataset:NameOfPerson}, {super=dataset:ChemConnectPrimitiveCompound},
	 * {super=dataset:Component}]
	 * 
	 * The string return is the first in this array (note that in Protege´this
	 * returns just the first element)
	 * 
	 */
	public static String isChemConnectPrimitiveDataStructure(String element) {
		String query = "SELECT DISTINCT ?super\n" + "	WHERE {\n" + element
				+ "  rdfs:subClassOf* dataset:ChemConnectPrimitiveDataStructure .\n" + element
				+ "  rdfs:subClassOf ?super .\n" + "     ?super rdf:type owl:Class\n" + "	}";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String superclass = null;
		if (lst.size() > 0) {
			superclass = stringlst.get(1).get("super");
		}
		return superclass;
	}

	public static String dataTypeOfStructure(DatabaseObject obj) {
		String structureType = obj.getClass().getSimpleName();
		String query = "SELECT ?datatype\n" + "	WHERE { ?datatype <http://purl.org/dc/elements/1.1/type>  \""
				+ structureType + "\"^^xsd:string  \n" + "}";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String datatype = null;
		if (stringlst.size() > 0) {
			Map<String, String> map = stringlst.get(0);
			datatype = map.get("datatype");
		}
		return datatype;
	}
	public static HierarchyNode conceptHierarchy(String topnode) {
		return conceptHierarchy(topnode, -1);
	}

	public static HierarchyNode conceptHierarchy(String topnode, int maxlevel) {
		String comment = getComment(topnode);
		String label = getLabel(topnode);
		HierarchyNode node = new HierarchyNode(topnode,label,comment);
		
		String query = "SELECT ?subsystem { ?subsystem <" + ReasonerVocabulary.directSubClassOf + "> " + topnode
				+ " .\n" + "FILTER (?subsystem != " + topnode + ") .\n" + "}";
		
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		if (maxlevel > 0 || maxlevel < 0) {
			--maxlevel;
			for (Map<String, String> map : stringlst) {
				String subsystem = map.get("subsystem");
				HierarchyNode subset = conceptHierarchy(subsystem, maxlevel);
				node.addSubNode(subset);
			}
		}
		return node;
	}
	public static String definitionFromStructure(String structure) {
		String query = "SELECT ?type\n" + "	WHERE {\n" + "    " + structure
				+ "  <http://www.w3.org/2004/02/skos/core#definition>  ?type\n" + "	}";
		List<Map<String, RDFNode>> lst = OntologyBase.resultSetToMap(query);
		List<Map<String, String>> stringlst = OntologyBase.resultmapToStrings(lst);
		String type = null;
		for (Map<String, String> map : stringlst) {
			type = map.get("type");
		}
		return type;

	}

}
