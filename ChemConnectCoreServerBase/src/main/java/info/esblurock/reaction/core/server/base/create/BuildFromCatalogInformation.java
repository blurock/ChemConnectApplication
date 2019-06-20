package info.esblurock.reaction.core.server.base.create;

import java.util.Map;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.transfer.ChemConnectDataElementInformation;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;
import info.esblurock.reaction.core.server.base.db.ReadWriteDatabaseObjectsWithSubobjects;

public class BuildFromCatalogInformation {
	
	
	public static ChemConnectDataElementInformation getChemConnectDataStructure(String identifier, String structureS) {
		ChemConnectDataElementInformation structure = DatasetOntologyParseBase
				.getChemConnectDataStructure(structureS);
		Map<String,DatabaseObject> map = getElementsOfCatalogObject(identifier,structureS);
		DatabaseObject obj = map.get(identifier);
		if(obj != null) {
			structure.setIdentifier(obj);
		}
		structure.setObjectMap(map);
		return structure;
	}
	
	
	public static Map<String,DatabaseObject> getElementsOfCatalogObject(String identifier, String dataElementName) {
		return ReadWriteDatabaseObjectsWithSubobjects.readCatalogObjectWithElements(dataElementName, identifier);
	}

}
