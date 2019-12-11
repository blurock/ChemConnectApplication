package info.esblurock.reaction.chemconnect.server.base.interpret;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

import info.esblurock.reaction.chemconnect.data.DatabaseObject;
import info.esblurock.reaction.chemconnect.data.dataset.DataElementInformation;
import info.esblurock.reaction.chemconnect.data.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;

public class CreateYamlFromObject {
	
	/** Create hierarchy
	 * 
	 * @param hierarchy The hierarchy to convert
	 * @return The Map of the yaml object
	 * 
	 * <ul>
	 * <li> The isolate the classname of the top object and find the ontology type
	 * <li> Create the map from the top object
	 * <li> Go through sub objects of the hierarchy and recursive call to this
	 * <ul>
	 * 
	 */
	public static Map<String, Object> createYamlFromHierarchy(DatabaseObjectHierarchy hierarchy) {
		String classname = hierarchy.getObject().getClass().getCanonicalName();
		String structure = DatasetOntologyParseBase.getTypeFromCanonicalDataType(classname);
		Map<String, Object> map = createYamlFromObject(structure, hierarchy.getObject());
		map.put("structure", structure);
		for(DatabaseObjectHierarchy subhierarchy : hierarchy.getSubobjects()) {
			Map<String, Object> submap = createYamlFromHierarchy(subhierarchy);
			submap.put("parentS", hierarchy.getObject().getIdentifier());
			map.put(subhierarchy.getObject().getIdentifier(), submap);
		}
		return map;
	}

	/** Create a map with the elements of the object
	 * 
	 * @param structure The ontology type of the object
	 * @param obj The object to convert
	 * @return The yaml map of the object
	 * 
	 * <ul>
	 * <li> Fill a simple map with the object elements
	 * <li> Create the map and fill with the elements
	 * <ul>
	 * 
	 */
	public static Map<String, Object> createYamlFromObject(String structure, DatabaseObject obj) {
		
		Map<String, Object> objmap = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		
		obj.fillMapOfValues(map);
		Set<String> nameset = map.keySet();
		for(String name : nameset) {
			objmap.put(name, map.get(name));
		}
		
		return objmap;
	}
	
	/** Create the DatabaseObjectHierarchy using the yaml map
	 * 
	 * @param baseinfo The base information for the new object hierarchy
	 * @param yaml The yaml string to convert to hierarchy
	 * @return The total hierarchy
	 * @throws IOException 
	 * 
	 * <ul>
	 * <li> Create the top object with the top level yaml map
	 * <li> Put the object in a hierarchy object
	 * <li> Fill the sub-objects in the hierarchy (after determining the structure)
	 * <ul>
	 */
	public static DatabaseObjectHierarchy fillHierarchyFromYamlString(DatabaseObject baseinfo, 
			Map<String, Object> yaml)
			throws IOException {
		
		DatabaseObject yamlobj = createAndFillFromYamlString(baseinfo,yaml);
		
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(yamlobj);

		String structure = (String) yaml.get("structure");
		fillFromYamlString(hierarchy, structure, yaml);

		return hierarchy;
	}
		
	/** This creates the empty object and fill in the base information and the immediate fields from yaml
	 * 
	 * @param top The base object (to get the base information)
	 * @param yaml The yaml information
	 * @param sourceID The new sourceID
	 * @return The object
	 * @throws IOException
	 * 
	 * <ul> 
	 * <li> Get the ontology name from "structure" of the current yaml hierarchy
	 * <li> Create the empty object from the canonical name (getObjectClassFromOntologyStructureType)
	 * <li> Retrieve the part and structure information of this object (not subclass info)
	 * <li> Fill in base information
	 * <ul>
	 */
	public static DatabaseObject createAndFillFromYamlString(DatabaseObject top, 
			Map<String, Object> yaml)
			throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		String structure = (String) yaml.get("structure");
		DatabaseObject topclass = GenericCreateEmptyObject.getObjectClassFromOntologyStructureType(structure);
		top.fillMapOfValues(map);
		topclass.retrieveFromMap(map);
		fillBaseDatabaseObject(top,topclass);		
		return topclass;
	}
	
	
	/** Top level of creating yaml map from hierarchy
	 * 
	 * @param hierarchy The current hierarchy
	 * @param topobj The top object of the hierarchy
	 * @param structure The ontology data type
	 * @param yaml The current yaml string
	 * @param sourceID The new source ID
	 * @throws IOException
	 * 
	 * <ul> 
	 * <li> Fill in the immediate fields from yaml (retrieveFromMap) into a map
	 * <li> Put the values of the map into the object (retrieveFromMap) 
	 * <li> Get the superclass type from the structure type
	 * <li> If there is a superclass exists recursive call to fill in superclass information
	 * <li> Fill in the record (sub-object) information
	 * <ul>
	 * 
	 */
	public static void fillFromYamlString(DatabaseObjectHierarchy hierarchy, 
			String structure, Map<String, Object> yaml) throws IOException {
		
		DatabaseObject topobj = hierarchy.getObject();
		
		Map<String, String> map = new HashMap<String, String>();
		retrieveFromMap(structure,map,yaml);
		topobj.retrieveFromMap(map);
		
		String superclass = DatasetOntologyParseBase.getDirectSuperClass(structure);
		if(superclass != null) {
			fillFromYamlString(hierarchy,superclass,yaml);
		}
		
		fillInRecordsToHierarchy(hierarchy,structure,yaml);
	}
	
	/** Loop through records and set in hierarchy
	 * 
	 * @param hierarchy The current hierarchy
	 * @param structure The ontology structure type
	 * @param yaml The current yaml map 
	 * @throws IOException
	 * 
	 * Determine the records (single and multiple) for the structure and for each record:
	 * <ul>
	 * <li> Determine parameter (yaml identifier) from record type
	 * <li> Get the record identifier for the subobject
	 * <li> Get annotation information from record
	 * <li> Create an object from the annotation information
	 * <li> Retrieve the map record information (using the id)
	 * <li> Set the identifier (from the yaml map) in the object
	 * <li> Fill in the sub-records (fillHierarchyFromYamlString)
	 * <ul>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void fillInRecordsToHierarchy(DatabaseObjectHierarchy hierarchy,
			String structure,
			Map<String, Object> yaml) throws IOException {
		
		DatabaseObject top = hierarchy.getObject();
		
		List<String> records = DatasetOntologyParseBase.subObjectsOfConcept(structure,
				"<http://www.w3.org/ns/dcat#record>",false);
		List<String> multrecords = DatasetOntologyParseBase.subObjectsOfConcept(structure,
				"<http://www.w3.org/ns/dcat#record>",true);
		records.addAll(multrecords);
		for(String name : records) {
			String paramname = getIDFromRecord(name);
			String id = (String) yaml.get(paramname);
			
			DataElementInformation info = DatasetOntologyParseBase.getSubElementStructureFromIDObject(name);
			DatabaseObject subobj = GenericCreateEmptyObject.determineDatabaseObjectWithID(top, info);

			Map<String, Object> subyaml = (Map<String, Object>) yaml.get(id);
			
			String objid = (String) subyaml.get("identifier");
			subobj.setIdentifier(objid);
			
			if(subyaml != null) {
				DatabaseObjectHierarchy subhier = fillHierarchyFromYamlString(subobj,subyaml);
				hierarchy.addSubobject(subhier);
			}
		}

	}
	
	/** Reads the annotations of the ontology object and retrieves the yaml identifier
	 * 
	 * @param structure The ontology element name
	 * @return The yaml identifier
	 * 
	 * The identifier consists of the suffix with "S" appended.
	 * 
	 */
	static String getIDFromRecord(String structure) {
		DataElementInformation recinfo = DatasetOntologyParseBase.getSubElementStructureFromIDObject(structure);
		//System.out.println("fillHierarchyFromYamlString subhierarchy: " + structure);
		String paramname = recinfo.getSuffix()+ "S";
		return paramname;
	}

	
	/** Fill in immediate hasPart and record information with fields from yaml
	 * 
	 * @param structure The ontology name of the structure
	 * @param map The map to fill in
	 * @param yaml The yaml file information
	 * 
	 * <ul>
	 * <li> get parameters (hasPart), records (record) ontology names
	 * <li> Loop through all elements.
	 * <li> Translate ontology name to yaml IDs and fill in map
	 * <ul>
	 * 
	 * getIDFromRecord translates the ontology name to the yaml identifier
	 * 
	 * 
	 */
	public static void retrieveFromMap(String structure, Map<String, String> map, Map<String, Object> yaml) {
		Set<String> nameset = new HashSet<String>();
		List<String> params = DatasetOntologyParseBase.subObjectsOfConcept(structure,
				" <http://purl.org/dc/terms/hasPart>",false);
		List<String> records = DatasetOntologyParseBase.subObjectsOfConcept(structure,
				"<http://www.w3.org/ns/dcat#record>",false);
		List<String> multrecords = DatasetOntologyParseBase.subObjectsOfConcept(structure,
				"<http://www.w3.org/ns/dcat#record>",true);
		nameset.addAll(params);
		nameset.addAll(records);
		nameset.addAll(multrecords);
		//Set<String> nameset = yaml.keySet();
		for(String name : nameset) {
			String paramname = getIDFromRecord(name);
			Object obj = yaml.get(paramname);
			if(obj != null) {
			if(obj.getClass().getCanonicalName().matches("java.lang.String")) {
				String param = (String) obj;
				map.put(paramname,param);
			}
			}
		}
	}
	
	/** Fills in the base information from the top element
	 * 
	 * @param top The source of the base information
	 * @param yamlobj put information in this object
	 */
	public static void fillBaseDatabaseObject(DatabaseObject top, DatabaseObject yamlobj) {
		yamlobj.setIdentifier(top.getIdentifier());
		yamlobj.setAccess(top.getAccess());
		yamlobj.setSourceID(top.getSourceID());
		yamlobj.setCreationDate(top.getCreationDate());
	}
	
	/** To pretty print of the map as a yaml
	 * 
	 * @param map The map
	 * @return The string with the map (as a yaml string)
	 * @throws YamlException
	 */
	public static String yamlMapToString(Map<String,Object> map) throws YamlException {
		StringWriter wS = new StringWriter(1000000);
		YamlWriter writer = new YamlWriter(wS);
		writer.write(map);
		writer.close();
		String yaml = wS.toString();
		return yaml;
	}
	
	/** Convert the yaml string to a map
	 * 
	 * @param yaml The yaml string
	 * @return The map of the string
	 * @throws YamlException
	 */
	@SuppressWarnings("deprecation")
	public static Map<String, Object> stringToYamlMap(String yaml) throws YamlException {
		InputStream modin = IOUtils.toInputStream(yaml);
		return stringToYamlMap(modin);
	}
		
	/** Convert the yaml string to a map
	 * 
	 * @param modin The yaml string as a input stream
	 * @return The map of the string
	 * @throws YamlException
	 */
	public static Map<String, Object> stringToYamlMap(InputStream modin) throws YamlException {
		Reader targetReader = new InputStreamReader(modin);
		YamlReader reader = new YamlReader(targetReader);
		Object object = reader.read();
		@SuppressWarnings("unchecked")
		Map<String, Object> mapping = (Map<String, Object>) object;
		return mapping;
	}

	
}
