package info.esblurock.reaction.core.server.base.repository;

//import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.base.db.yaml.ReadWriteYamlDatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseData;

class RepositoryClassTest {

	@Test
	void testRepositoryFileStaging() {
		String id = "ID";
		String access = "access";
		String owner = "owner";
		String sourceID = "sourceID";
		DatabaseObject obj = new DatabaseObject(id, access, owner, sourceID);
		System.out.println("1---------------------------------------------------");
		System.out.println(obj.toString("RepositoryClassTest  Object: "));
		System.out.println("1---------------------------------------------------");
		
		
		InterpretBaseData interpret = InterpretBaseData.RepositoryFileStaging;
		System.out.println("2---------------------------------------------------");
		System.out.println(interpret.canonicalClassName());
		System.out.println("2---------------------------------------------------");
		DatabaseObjectHierarchy hierarchy = interpret.createEmptyObject(obj);
		System.out.println("3---------------------------------------------------");
		System.out.println(hierarchy.toString("Created: "));
		System.out.println("3---------------------------------------------------");
		
		try {
			String yaml = ReadWriteYamlDatabaseObjectHierarchy.yamlStringFromDatabaseObjectHierarchyNoUpdate(hierarchy);
			System.out.println("4---------------------------------------------------");
			System.out.println("RepositoryClassTest yaml from hierarchy\n");
			System.out.println(yaml);
			System.out.println("4---------------------------------------------------");
			Map<String, Object> map = ReadWriteYamlDatabaseObjectHierarchy.stringToYamlMap(yaml);
			
			System.out.println("5---------------------------------------------------");
			System.out.println("RepositoryClassTest\n");
			System.out.println(map);
			System.out.println("5---------------------------------------------------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testRepository() {
		String id = "ID";
		String access = "access";
		String owner = "owner";
		String sourceID = "sourceID";
		DatabaseObject obj = new DatabaseObject(id, access, owner, sourceID);
		System.out.println("1---------------------------------------------------");
		System.out.println(obj.toString("RepositoryDataFile  Object: "));
		System.out.println("1---------------------------------------------------");
		
		
		InterpretBaseData interpret = InterpretBaseData.RepositoryDataFile;
		System.out.println("2---------------------------------------------------");
		System.out.println(interpret.canonicalClassName());
		System.out.println("2---------------------------------------------------");
		DatabaseObjectHierarchy hierarchy = interpret.createEmptyObject(obj);
		System.out.println("3---------------------------------------------------");
		System.out.println(hierarchy.toString("RepositoryDataFile Created: "));
		System.out.println("3---------------------------------------------------");
		
		try {
			String yaml = ReadWriteYamlDatabaseObjectHierarchy.yamlStringFromDatabaseObjectHierarchyNoUpdate(hierarchy);
			System.out.println("4---------------------------------------------------");
			System.out.println("RepositoryDataFile yaml from hierarchy\n");
			System.out.println(yaml);
			System.out.println("4---------------------------------------------------");
			Map<String, Object> map = ReadWriteYamlDatabaseObjectHierarchy.stringToYamlMap(yaml);
			
			System.out.println("5---------------------------------------------------");
			System.out.println("RepositoryDataFile\n");
			System.out.println(map);
			System.out.println("5---------------------------------------------------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
