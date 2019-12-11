package info.esblurock.reaction.chemconnect.server.base.interpret;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.esblurock.reaction.chemconnect.data.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.data.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.data.DatabaseObject;
import info.esblurock.reaction.chemconnect.data.dataset.DataElementInformation;
import info.esblurock.reaction.chemconnect.data.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;

/** 
 * 
 * @author edwardblurock
 * 
 * The static methods for generating an empty hierarchy of objects
 * 
 * <ul>
 * <li> createEmptyObjectFromType:  Create a hierarchy from the class type
 * <li> createEmptyObject: Create a hierarchy from the ontology type
 * <ul>
 * 
 *
 */
public class GenericCreateEmptyObject {
	
	/** Create a hierarchy from the class type
	 * 
	 * @param structuretype The class type (the ontology type is derived from a classname)
	 * @param obj The base  DatabaseObject
	 * @return Create a object hierarchy based on the class type
	 * @throws IOException If there is a problem to produce a class within the hierarchy
	 * 
	 * This sets up calling createEmptyObject
	 * 
	 */
	public static DatabaseObjectHierarchy createEmptyObjectFromType(String structuretype, DatabaseObject obj) throws IOException {
		String structure = DatasetOntologyParseBase.getDataTypeFromType(structuretype);
		return createEmptyObject(structure,obj);
	}
	
	/** Create a hierarchy from the ontology type
	 * 
	 * @param structure The ontology structure type of an object 
	 * @param obj The base  DatabaseObject
	 * @return Create a object hierarchy based on the ontology type
	 * @throws IOException IOException If there is a problem to produce a class within the hierarchy
	 * 
	 * <ul>
	 * <li> Determine DataElementInformation from type
	 * <li> Create an instance of the top class
	 * <li> Determine the ID based on type (add the suffix) and set in new DatabaseObject
	 * <li> Find map of elements from base object
	 * <li> Determine the list of singlet records (generateSingleRecordObjects), add id to map, add class to hierarchy
	 * <li> Determine the list of multiple records (generateMultipleRecordObjects), add id to map, add class to hierarchy
	 * <li> Fill in sub classes in the hierarchy (fillInSubClassObjects)
	 * <li> Fill in the topclass from map
	 * <ul>
	 */
	public static DatabaseObjectHierarchy createEmptyObject(String structure, DatabaseObject obj) throws IOException {
		DataElementInformation info = DatasetOntologyParseBase.getSubElementStructureFromIDObject(structure);
		
		DatabaseObject topclass = getObjectClassFromCanonicalName(structure);
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(topclass);
		
		DatabaseObject compobj = determineDatabaseObjectWithID(obj, info);
		Map<String,String> map = new HashMap<String,String>();
		compobj.fillMapOfValues(map);
		topclass.setIdentifier(compobj.getIdentifier());
		
		DatabaseObjectHierarchy subclasshierarchy = fillInSubClassObjects(structure,compobj,map);
		if(subclasshierarchy != null) {
			hierarchy.transferSubObjects(subclasshierarchy);
		}
		
		generateSingleRecordObjects(structure, compobj, hierarchy, map);
		generateMultipleRecordObjects(structure, compobj, hierarchy, map);
				
		topclass.retrieveFromMap(map);
		topclass.setIdentifier(compobj.getIdentifier());

		return hierarchy;
	}
		
	/**
	 * @param structure The ontology data type
	 * @return 
	 * @throws IOException If the generation of the class has a problem (mainly associated with not finding class)
	 * 
	 * Find the canonical name and then create the class with the empty constructor
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static DatabaseObject getObjectClassFromCanonicalName(String structure) throws IOException {
		String classname = DatasetOntologyParseBase.getCanonicalClassName(structure);
		Class myClass;
		DatabaseObject topclass = null;
		try {
			myClass = Class.forName(classname);
			Constructor constructor = myClass.getConstructor();
			//Object[] parameters = new Object[0];
			topclass = (DatabaseObject) constructor.newInstance();
		} catch (ClassNotFoundException e) {
			throw new IOException("Did not find the class: (" + structure + "): " + classname);
		} catch (NoSuchMethodException e) {
			throw new IOException("No such method: " + structure);
		} catch (SecurityException e) {
			throw new IOException("Security exception: "+ structure);
		} catch (InstantiationException e) {
			throw new IOException("Instantiation Exception: (" + structure + "): " + classname);
		} catch (IllegalAccessException e) {
			throw new IOException("Illegal Access Exception: "+ structure);
		} catch (IllegalArgumentException e) {
			throw new IOException("Illegal Argument Exception: "+ structure);
		} catch (InvocationTargetException e) {
			throw new IOException("Invocation Targe tException: "+ structure);
		}
		return topclass;
	}
	
	/** Generate the id of the generated object
	 * 
	 * @param obj The base object information of the class
	 * @param info The ontology information about the class
	 * @return The object with the proper id
	 */
	public static DatabaseObject determineDatabaseObjectWithID(DatabaseObject obj, DataElementInformation info) {
		DatabaseObject compobj = new DatabaseObject(obj);
		compobj.nullKey();
		String compid = InterpretBaseDataUtilities.createSuffix(obj, info);
		
		compobj.setIdentifier(compid);
		return compobj;
	}
	
	/** Generate all single record objects of the top ontology class
	 * 
	 * @param structure The ontology data structure
	 * @param compobj The DatabaseObject from the super class 
	 * @param hierarchy The hierarchy of objects from this class
	 * @param map The map to put the subclass identifier in
	 * @throws IOException
	 * 
	 * Find the set of single records, for each record
	 * <ul>
	 * <li> Create the empty record object
	 * <li> Put in the hierarchy
	 * <li> Add the element id to the map
	 * <ul>
	 * 
	 */
	static void generateSingleRecordObjects(String structure, 
			DatabaseObject compobj, 
			DatabaseObjectHierarchy hierarchy, 
			Map<String,String> map) throws IOException {
		List<String> records = DatasetOntologyParseBase.subObjectsOfConcept(structure,"<http://www.w3.org/ns/dcat#record>",false);
		for(String record : records) {
			DatabaseObjectHierarchy rechier = createEmptyObject(record,compobj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) rechier.getObject();
			compound.setParentLink(hierarchy.getObject().getIdentifier());
			DataElementInformation recinfo = DatasetOntologyParseBase.getSubElementStructureFromIDObject(record);
			hierarchy.addSubobject(rechier);
			String id = rechier.getObject().getIdentifier();
			String elementS = recinfo.getSuffix() + "S";
			map.put(elementS,id);
		}
	}
	/** Generate all the multiple sub-class objects of the top ontology class
	 * 
	 * @param structure The ontology data structure
	 * @param compobj The DatabaseObject from the super class 
	 * @param hierarchy The hierarchy of objects from this class
	 * @param map  The map to put the subclass identifier in
	 * @throws IOException If there is a problem class generation
	 * 
	 * Find the set of multiple records, for each record:
	 * <ul>
	 * <li> Find the object information (DataElementInformation)
	 * <li> Create the ChemConnectCompoundMultiple and put in a new hierarchy
	 * <li> Set the type information within the multiple
	 * <li> Add the element id to the map
	 * <ul>
	 * 
	 */
	static void generateMultipleRecordObjects(String structure, 
			DatabaseObject compobj, 
			DatabaseObjectHierarchy hierarchy, 
			Map<String,String> map) throws IOException {
		List<String> multrecords = DatasetOntologyParseBase.subObjectsOfConcept(structure,"<http://www.w3.org/ns/dcat#record>",true);
		for(String multrecord : multrecords) {
			DataElementInformation recinfo = DatasetOntologyParseBase.getSubElementStructureFromIDObject(multrecord);
			ChemConnectCompoundDataStructure compound = new ChemConnectCompoundDataStructure(compobj,
					hierarchy.getObject().getIdentifier());
			ChemConnectCompoundMultiple multiple = new ChemConnectCompoundMultiple(compound,multrecord);
			DatabaseObjectHierarchy multhierarchy = new DatabaseObjectHierarchy(multiple);
			setChemConnectCompoundMultipleType(multhierarchy,multrecord);
			hierarchy.addSubobject(multhierarchy);
			String id = multhierarchy.getObject().getIdentifier();
			String elementS = recinfo.getSuffix() + "S";
			map.put(elementS,id);
			
		}
		
	}
	/** This fills in the hierarchy elements of the subclasses 
	 * 
	 * @param structure The ontology data structure
	 * @param obj The DatabaseObject from the super class 
	 * @param map The map to put the subclass identifier in
	 * @return The hierarchy of the object
	 * @throws IOException
	 */
	static DatabaseObjectHierarchy fillInSubClassObjects(String structure, DatabaseObject obj, Map<String,String> map) throws IOException {
		String superclass = DatasetOntologyParseBase.getDirectSuperClass(structure);
		
		DatabaseObjectHierarchy hierarchy = null;
		if(superclass != null) {
			hierarchy = createEmptyObject(superclass,obj);
			DatabaseObject superobj = hierarchy.getObject();
			superobj.fillMapOfValues(map);
		}
		return hierarchy;
	}
	/** Insert the id and datatype 
	 * 
	 * @param hierarchy The hierarchy of the mulitiple object
	 * @param dataType The datatype of the multiple object
	 * 
	 */
	public static void setChemConnectCompoundMultipleType(DatabaseObjectHierarchy hierarchy, String dataType) {
		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) hierarchy.getObject();
		multiple.setType(dataType);
		DataElementInformation refelement = DatasetOntologyParseBase.getSubElementStructureFromIDObject(dataType);
		String refid = InterpretBaseDataUtilities.createSuffix(multiple, refelement);
		multiple.setIdentifier(refid);
	}

}
