package info.esblurock.reaction.core.server.base.services;

import java.io.IOException;
import java.util.Map;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.base.transfer.ChemConnectRecordInformation;
import info.esblurock.reaction.chemconnect.core.base.transfer.CompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.BaseCatalogAccess;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;
import info.esblurock.reaction.core.server.base.authentification.InitializationBase;
import info.esblurock.reaction.core.server.base.gps.GeocodingLatituteAndLongitude;
import info.esblurock.reaction.core.server.base.services.util.InterpretDataBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretDataInterface;

public class BaseCatalogAccessImpl extends ServerBase implements BaseCatalogAccess {
	private static final long serialVersionUID = 1L;

	public GPSLocation getGPSLocation(DatabaseObject obj, String city, String country) throws IOException {
		GeocodingLatituteAndLongitude geo = new GeocodingLatituteAndLongitude();
		geo.coordinates(city, country);
		GPSLocation gps = new GPSLocation(obj, geo.getLatitude(), geo.getLongitude());
		return gps;
	}
public ChemConnectRecordInformation getChemConnectRecordInformation(DatabaseObject obj) throws IOException {
		
		String structureS = DatasetOntologyParseBase.dataTypeOfStructure(obj);
		CompoundDataStructure structure = getChemConnectCompoundDataStructure(structureS);
		String objecttype = obj.getClass().getSimpleName();
		InterpretDataInterface interpret = InitializationBase.getInterpret().valueOf(objecttype);
		Map<String,Object> mapping = interpret.createYamlFromObject(obj);
		ChemConnectRecordInformation info = new ChemConnectRecordInformation(obj,structureS,structure,mapping);
		
		return info;
}
public CompoundDataStructure getChemConnectCompoundDataStructure(String dataElementName) {
	CompoundDataStructure substructures = null;
	substructures = DatasetOntologyParseBase.subElementsOfStructure(dataElementName);
	return substructures;
}

public HierarchyNode hierarchyOfConcepts(String topnode) {
	HierarchyNode hierarchy = DatasetOntologyParseBase.conceptHierarchy(topnode);
	return hierarchy;
}
public HierarchyNode hierarchyFromPrimitiveStructure(String structure) {
	String topconcept = DatasetOntologyParseBase.definitionFromStructure(structure);
	return hierarchyOfConcepts(topconcept);
}

public HierarchyNode hierarchyOfConceptsWithLevelLimit(String topnode, int maxlevel) {
	HierarchyNode hierarchy = DatasetOntologyParseBase.conceptHierarchy(topnode, maxlevel);
	return hierarchy;
}

}
