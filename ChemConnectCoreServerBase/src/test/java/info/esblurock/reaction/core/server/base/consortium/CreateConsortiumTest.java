package info.esblurock.reaction.core.server.base.consortium;

//import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.base.create.CreateConsortiumCatalogObject;

class CreateConsortiumTest {

	@Test
	void test() {
		DatabaseObject obj = new DatabaseObject("blurock-consortium", "owner", "blurock", "1");
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,"blurock-consortium");
		ArrayList<String> path = new ArrayList<String>();
		path.add("blurock");
		path.add("consortium");
		DataCatalogID catid = new DataCatalogID(structure, "blurock-consortium", 
				"dataset:Consortium", "myconsortia",
				path);
		DatabaseObjectHierarchy hierarchy = CreateConsortiumCatalogObject.createConsortiumCatalogObject(catid,"NewConsortium");
		System.out.println(hierarchy.toString("Create: "));
		
		DatabaseObjectHierarchy memhierarchy = 
				CreateConsortiumCatalogObject.addConsortiumMember(hierarchy, "MyConsortium", "member");
		System.out.println(memhierarchy.toString("New Member: "));
		
		System.out.println(hierarchy.toString("With new member: "));
		
		
		
		
		
	}

}
