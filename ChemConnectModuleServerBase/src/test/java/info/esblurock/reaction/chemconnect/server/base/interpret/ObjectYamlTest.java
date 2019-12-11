package info.esblurock.reaction.chemconnect.server.base.interpret;

//import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.data.DatabaseObject;
import info.esblurock.reaction.chemconnect.data.dataset.DatabaseObjectHierarchy;

public class ObjectYamlTest {

	@Test
	public void test() {

		DatabaseObjectHierarchy hierarchy = null;
		String structure = null;
		DatabaseObject obj = new DatabaseObject();
		obj.setIdentifier("topclass");
		obj.setOwner("blurock");
		obj.setSourceID("1");
		obj.setAccess("Public");

		try {
			structure = "RepositoryFileStaging";
			//structure = "ChemConnectDataStructure";
			hierarchy = GenericCreateEmptyObject.createEmptyObjectFromType(structure, obj);
			System.out.println(hierarchy.toString(structure + ":   "));
			Map<String, Object> map = CreateYamlFromObject.createYamlFromHierarchy(hierarchy);
			String yamlS = CreateYamlFromObject.yamlMapToString(map);
			System.out.println("=====================================================");
			System.out.println(yamlS);
			System.out.println("=====================================================");
			
			
			obj.setOwner("newblurock");
			obj.setSourceID("2");
			obj.setAccess("newblurock");

			DatabaseObjectHierarchy subhier = CreateYamlFromObject.fillHierarchyFromYamlString(obj,map);
			System.out.println("----------------------------------------------------");
			System.out.println("----------------------------------------------------");
			System.out.println(subhier.toString(structure + ": "));
			System.out.println("----------------------------------------------------");
			System.out.println("----------------------------------------------------");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
