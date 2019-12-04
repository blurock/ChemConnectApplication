package info.esblurock.reaction.chemconnect.server.base.interpret;

import java.io.IOException;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.data.DatabaseObject;
import info.esblurock.reaction.chemconnect.data.dataset.DatabaseObjectHierarchy;

public class InterpretBaseTest {

	@Test
	public void test() {
		DatabaseObject obj = new DatabaseObject();
		obj.setIdentifier("toplevel");
		//String structure = "RepositoryFileStaging";
		//String structure = "ChemConnectStructureRepository";
		//String structure = "ChemConnectStructureBase";
		//String structure = "ChemConnectDataStructure";
		//String structure = "SimpleCatalogObject";
		//String structure = "ChemConnectCompoundBase";
		
		//String structure = "ActivityRepositoryInitialReadLocalFile";
		//String structure = "InitialReadFromUserInterface";
		DatabaseObjectHierarchy hierarchy = null;
		String structure = null;
		try {
			structure = "RepositoryFileStaging";
			hierarchy = GenericCreateEmptyObject.createEmptyObjectFromType(structure , obj);
			System.out.println(hierarchy.toString(structure + ":   "));
			structure = "ActivityRepositoryInitialReadLocalFile";
			hierarchy = GenericCreateEmptyObject.createEmptyObjectFromType(structure , obj);
			System.out.println(hierarchy.toString(structure + ":   "));
			structure = "InitialReadFromUserInterface";
			hierarchy = GenericCreateEmptyObject.createEmptyObjectFromType(structure , obj);
			System.out.println(hierarchy.toString(structure + ":   "));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
