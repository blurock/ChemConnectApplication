package info.esblurock.reaction.chemconnect.server.base.interpret;

import info.esblurock.reaction.chemconnect.data.DatabaseObject;
import info.esblurock.reaction.chemconnect.data.dataset.DataElementInformation;
import info.esblurock.reaction.chemconnect.data.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;

public class GenericCreateEmptyObject {
	
	
	
	public static DatabaseObjectHierarchy createEmptyObject(String structure, DatabaseObject obj) {
		DatabaseObject compobj = new DatabaseObject(obj);
		compobj.nullKey();
		DataElementInformation element = DatasetOntologyParseBase
				.getSubElementStructureFromIDObject("dataset:ChemConnectDataStructure");
		String compid = InterpretBaseDataUtilities.createSuffix(obj, element);
		compobj.setIdentifier(compid);
		
		
		
		return null;
	}

}
