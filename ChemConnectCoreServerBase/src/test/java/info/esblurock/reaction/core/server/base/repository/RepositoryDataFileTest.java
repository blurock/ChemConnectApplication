package info.esblurock.reaction.core.server.base.repository;


import org.junit.jupiter.api.Test;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.base.activity.repositoryfile.util.CreateRepositoryCatagoryFiles;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseData;

class RepositoryDataFileTest {

	@Test
	void test() {
		String id = "ID";
		String access = "access";
		String owner = "owner";
		String sourceID = "sourceID";
		DatabaseObject obj = new DatabaseObject(id, access, owner, sourceID);
		System.out.println("1---------------------------------------------------");
		System.out.println(obj.toString("RepositoryDataFile  Object: "));
		System.out.println("1---------------------------------------------------");
		
		
		InterpretBaseData interpret = InterpretBaseData.RepositoryFileStaging;
		System.out.println("2---------------------------------------------------");
		System.out.println(interpret.canonicalClassName());
		System.out.println("2---------------------------------------------------");
		DatabaseObjectHierarchy hierarchy = interpret.createEmptyObject(obj);
		System.out.println("3---------------------------------------------------");
		System.out.println(hierarchy.toString("Created: "));
		System.out.println("3---------------------------------------------------");
		
		
		InterpretBaseData interpretcatid = InterpretBaseData.DataCatalogID;
		DatabaseObjectHierarchy cathierarchy = interpretcatid.createEmptyObject(obj);
		DataCatalogID catid = (DataCatalogID) cathierarchy.getObject();
		DatabaseObjectHierarchy rephierarchy = CreateRepositoryCatagoryFiles.createRepositoryDataFile(hierarchy, 
				catid);
		
		System.out.println(hierarchy.toString("RepositoryDataFile Created: "));
		System.out.println("3---------------------------------------------------");
		System.out.println(rephierarchy.toString("RepositoryDataFile: "));
		
		
	}

}
