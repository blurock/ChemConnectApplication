package info.esblurock.reaction.core.server.base.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import info.esblurock.reaction.chemconnect.core.base.query.ListOfQueries;
import info.esblurock.reaction.chemconnect.core.base.query.QueryPropertyValue;
import info.esblurock.reaction.chemconnect.core.base.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.base.query.SetOfQueryResults;
import info.esblurock.reaction.chemconnect.core.base.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.base.utilities.ClassificationInformation;
import info.esblurock.reaction.core.ontology.base.GenericSimpleQueries;
import info.esblurock.reaction.core.ontology.base.QueryFactory;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;
import info.esblurock.reaction.core.server.base.create.CreateContactObjects;
import info.esblurock.reaction.core.server.base.queries.QueryBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseData;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;

public class ExtractCatalogInformation {
	
	/**
	 * @param id  The identifier of the catalog item to 
	 * @param type The (ontology) type of the catalog item (for example dataset:Organization)
	 * @return The hierarchy of 
	 * @throws IOException
	 * 
	 * Uses the type to get the name in InterpretBaseData to read (readElementFromDatabase) the top catalog item.
	 * 
	 * 
	 */
	public static DatabaseObjectHierarchy getTopCatalogObject(String id, String type) throws IOException {
		DataElementInformation element = new DataElementInformation(type, 
				null, true, 0, null, null,null);
		ClassificationInformation classify = DatasetOntologyParseBase.getIdentificationInformation(null, element);
		InterpretBaseData interpret = InterpretBaseData.valueOf(classify.getDataType());
		DatabaseObject subobj = interpret.readElementFromDatabase(id);
		DatabaseObject obj = findTopObject(subobj);
		String dtype = DatasetOntologyParseBase.getTypeFromDataType(obj.getClass().getSimpleName());
		DatabaseObjectHierarchy hierarchy = getObjectHierarchy(obj.getIdentifier(),dtype, obj.getClass().getSimpleName(),true);
		return hierarchy;
	}
	
	/** findTopObject
	 * This finds the top catalog object in the hierarchy, if the current object is not a catalog item (i.e., it is a record)
	 * 
	 * @param subobject  The single catalog object
	 * @return
	 * @throws IOException
	 * 
	 * <ul>
	 * <li>If object is of type ChemConnectCompoundDataStructure, i.e. the object is not a catalog object, then 
	 * the parent ID is retrieved. If it does not have a parent, then the same object is returned.
	 * <li>The name of the class is used to find the parent objects types (DatasetOntologyParseBase.asSubObject)
	 * <li>The database is searched for the class that has such an ID (InterpretBaseData to read database object, if no
	 * exception, then the class was found).
	 * <li> Recursive call to findTopObject
	 * </ul>
	 * 
	 * 
	 */
	public static DatabaseObject findTopObject(DatabaseObject subobject) throws IOException {
		DatabaseObject ans = subobject;
		boolean assignable = info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure.class.isAssignableFrom(subobject.getClass());
		if(assignable) {
			info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure compound 
				= (info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure) subobject;
			String classname = subobject.getClass().getSimpleName();
			ArrayList<String> lst = DatasetOntologyParseBase.asSubObject(classname);
			DatabaseObject parent = null;
			for(String name : lst ) {
				if(parent == null) {
					try {
						InterpretBaseData interpret = InterpretBaseData.valueOf(name);
						if(interpret != null) {
							parent = interpret.readElementFromDatabase(compound.getParentLink());
						}
					} catch(Exception ex) {
						
					}

				}
			}
			ans = findTopObject(parent);
		}
		return ans;
	}
	
	/** getCatalogObject: Find the hierarchy of a catalog item (top level call)
	 * 
	 * @param id  The identifier of the catalog object
	 * @param type The (ontology) type of catalog item
	 * @return The catalog hierarchy
	 */
	public static DatabaseObjectHierarchy getCatalogObject(String id, String type) {
		DataElementInformation element = DatasetOntologyParseBase.getSubElementStructureFromIDObject(type);
		return getDatabaseObjectAndSubElements(id,element,true);
	}
	
	/** getDatabaseObjectAndSubElements: Find the hierarchy below the current object from DataElementInformation
	 * 
	 * @param id  The catalog ID
	 * @param dataelement The class information about the hierarchy item
	 * @param asSinglet true if a singlet
	 * @return The hierarchy below the current object
	 * 
	 * Service routine for getObjectHierarchy
	 */
	public static DatabaseObjectHierarchy getDatabaseObjectAndSubElements(String id, 
			DataElementInformation dataelement, boolean asSinglet) {
		return getObjectHierarchy(id,dataelement.getDataElementName(),dataelement.getChemconnectStructure(),asSinglet);
	}
	/**
	 * @param id The identifier
	 * @param t The name of the class
	 * @param type The class type (to be used with InterpretBaseData
	 * @param asSinglet true if a singlet.
	 * @return The hierarchy from the element
	 * 
	 * If singlet, call readSingletInformation
	 * If multiple:
	 * <ul>
	 * <li> Get the ChemConnectCompoundMultiple database item with ID
	 * <li> Start a new hierarchy (DatabaseObjectHierarchy)
	 * <li> Get the parent ID from the read in ChemConnectCompoundMultiple
	 * <li> Get the class information of the subitems of multiple items (for recursive call) List<DataElementInformation>
	 * <li> Get all the database items having the parent ID of the original item (the parent ID of ChemConnectCompoundMultiple)
	 * <li> Loop through the database items with readSingletInformation and add their subhierarchies to this one.
	 * </ul>
	 * 
	 */
	public static DatabaseObjectHierarchy getObjectHierarchy(String id, String t, String type, boolean asSinglet) {
		List<DataElementInformation> substructures = DatasetOntologyParseBase.subElementsOfStructure(t);
		DatabaseObjectHierarchy hierarchy = null;
		try {
			InterpretBaseData interpret = InterpretBaseData.valueOf(type);
			if(asSinglet) {
				DatabaseObject obj = interpret.readElementFromDatabase(id);
				hierarchy = readSingletInformation(obj, interpret, substructures);
			} else {
				InterpretBaseData multiinterpret = InterpretBaseData.valueOf("ChemConnectCompoundMultiple");
				ChemConnectCompoundMultiple multi = (ChemConnectCompoundMultiple) multiinterpret.readElementFromDatabase(id);
				hierarchy = new DatabaseObjectHierarchy(multi);
				String parentid = multi.getIdentifier();
				ClassificationInformation classification = DatasetOntologyParseBase.getIdentificationInformation(multi.getType());
				List<DataElementInformation> mulitsubstructures = DatasetOntologyParseBase.subElementsOfStructure(t);
				InterpretBaseData clsinterpret = InterpretBaseData.valueOf(classification.getDataType());
				String classtype = clsinterpret.canonicalClassName();
				SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
				QueryPropertyValue value1 = new QueryPropertyValue("parentLink",parentid);
				values.add(value1);
				ListOfQueries queries = QueryFactory.accessQueryForUser(classtype, multi.getOwner(), values);
				SetOfQueryResults results;
				try {
					results = QueryBase.StandardSetOfQueries(queries);
					List<DatabaseObject> objs = results.retrieveAndClear();
					for(DatabaseObject obj: objs) {
						DatabaseObjectHierarchy subhier = readSingletInformation(obj, interpret, mulitsubstructures);
						hierarchy.addSubobject(subhier);
					}
				} catch (ClassNotFoundException e) {
					throw new IOException("getDatabaseObjectAndSubElements Class not found: " + classtype);
				}
			}
		} catch(IllegalArgumentException ex) {
			//System.out.println("No interpret: " + classify.getDataType());
			//System.out.println(ex.getClass().getSimpleName());
		} catch(IOException ex) {
			System.out.println("IOException: '" + type + "' with ID: '" + id + "' singlet(" + asSinglet + ")");
			System.out.println(ex.toString());
		}
		return hierarchy;
	}

	/** readSingletInformation: Process item from multiple list
	 * @param obj The DatabaseObject
	 * @param interpret How to process the object
	 * @param substructures The set of sub items of this object
	 * @return The hierarchy under this item
	 * @throws IOException
	 * 
	 * <ul>
	 * <li> Convert the current object to yaml (to find correspondence with DataElementInformation)
	 * <ll> Loop through DataElementInformation and interpret with yaml information 
	 * 		<ul> Get identifier from DataElementInformation
	 * 		<li> Get yaml object with identifier
	 * 		<li> If String: The object is an identifier and call getDatabaseObjectAndSubElements
	 * 		<li> Otherwise, nothing (actually an error.
	 * 		<ul>
	 * <ul>
	 * 
	 */
	private static DatabaseObjectHierarchy readSingletInformation(DatabaseObject obj, 
			InterpretBaseData interpret,
			List<DataElementInformation> substructures) throws IOException {
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(obj);
		Map<String,Object> mapping = interpret.createYamlFromObject(obj);
		
		for(DataElementInformation element : substructures) {
			String identifier = element.getIdentifier();
			Object elementobj = mapping.get(identifier);
			if(elementobj != null) {
				if(elementobj.getClass().getCanonicalName().compareTo(String.class.getCanonicalName()) == 0) {
					String newid = (String) elementobj;
					DatabaseObjectHierarchy sub = getDatabaseObjectAndSubElements(newid,element,element.isSinglet());
					if(sub != null) {
						hierarchy.addSubobject(sub);
					}
				} else if(GenericSimpleQueries.isAArrayListDataObject(element.getDataElementName())) {
					System.out.println("getDatabaseObjectAndSubElements: ArrayList: " + element.getDataElementName());
				} else {
					System.out.println("getDatabaseObjectAndSubElements: obj\n" + obj.toString());
					System.out.println("getDatabaseObjectAndSubElements: element\n" + element.toString());
					System.out.println("getDatabaseObjectAndSubElements: elementobj\n" 
							+ elementobj.getClass().getCanonicalName());
					System.out.println("getDatabaseObjectAndSubElements: elementobj\n" 
							+ elementobj.toString());
				}
			} else {
				System.out.println("--------------------------------------");
				System.out.println("Couldn't find Identifier: \n" + element.toString());
				System.out.println("Couldn't find Identifier: " + interpret.canonicalClassName());
				System.out.println("Couldn't find Identifier: " + identifier);
				System.out.println("Couldn't find Identifier: " + mapping.keySet());
				System.out.println("--------------------------------------");
			}
		}
		return hierarchy;
	}
	public static DatabaseObjectHierarchy getDatabaseObjectHierarchy(String catid) throws IOException {
		DatabaseObjectHierarchy hierarchy = ExtractCatalogInformation.getCatalogObject(catid, 
				MetaDataKeywords.datasetCatalogHierarchy);
		if(hierarchy != null) {
		InterpretBaseData interpret = InterpretBaseData.DatasetCatalogHierarchy;
		//String classname = interpret.canonicalClassName();
		Map<String,Object> mapping = interpret.createYamlFromObject(hierarchy.getObject());
		//Set<String> keys = mapping.keySet();
		String objlinkid = (String) mapping.get(StandardDataKeywords.parameterObjectLinkS);
		DatabaseObjectHierarchy multihier = hierarchy.getSubObject(objlinkid);
		for(DatabaseObjectHierarchy subhier : multihier.getSubobjects()) {
				DataObjectLink lnk = (DataObjectLink) subhier.getObject();
				String type = lnk.getLinkConcept();
				if(type.compareTo(MetaDataKeywords.linkSubCatalog) == 0) {
					String subid = lnk.getDataStructure();
					DatabaseObjectHierarchy subhierarchy = getDatabaseObjectHierarchy(subid);
					hierarchy.addSubobject(subhierarchy);
				}
		}
		} else {
			throw new IOException("DatasetCatalogHierarchy not found: " + catid);
		}
		return hierarchy;
	}
	
	/*
	 * id: The id of the super catagory hierarchy
	 * obj: The base class, used to get new id
	 * catagorytype: The type of catalog hierarchy
	 * sourceID: The new source ID (overrides sourceID of obj)
	 * onelinedescription: Goes into the title.
	 */
	public static DatabaseObjectHierarchy createNewCatalogHierarchy(DatabaseObject obj,
			String simpleName,
			String id, String onelinedescription,String sourceID, String catagorytype)
			throws IOException {
		DatabaseObject newobj = new DatabaseObject(obj);
		newobj.setSourceID(sourceID);
		String classname = DatasetCatalogHierarchy.class.getCanonicalName();
		DatasetCatalogHierarchy catalog = (DatasetCatalogHierarchy) QueryBase.getDatabaseObjectFromIdentifier(classname,
				id);
		DatabaseObjectHierarchy subs = CreateContactObjects.fillDatasetCatalogHierarchy(catalog, simpleName, newobj,
				onelinedescription,catagorytype);
		return subs;
	}
}
