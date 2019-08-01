package info.esblurock.reaction.core.server.base.create;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.InitialStagingRepositoryFile;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryDataFile;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryFileStaging;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.core.server.base.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseData;

public class CreateBaseCatalogObjects {
	
	public static DatabaseObjectHierarchy createRepositoryFileStaging(DatabaseObject obj,
			String filename, String filecontext,
			String uploadDescriptionText, String sessionID, String path) {
		
		InterpretBaseData interpret = InterpretBaseData.RepositoryFileStaging;
		DatabaseObjectHierarchy hierarchy = interpret.createEmptyObject(obj);
		RepositoryFileStaging repository = (RepositoryFileStaging) hierarchy.getObject();
		
		DatabaseObjectHierarchy staginghier = hierarchy.getSubObject(repository.getRepositoryFile());
		InitialStagingRepositoryFile staging = 
				(InitialStagingRepositoryFile) staginghier.getObject();
		
		DatabaseObjectHierarchy gcsblobhier = hierarchy.getSubObject(repository.getBlobFileInformation());
		GCSBlobFileInformation source = (GCSBlobFileInformation) gcsblobhier.getObject();
		source.fillGCS(path, filename, filecontext, uploadDescriptionText);
		
		/*
		GCSBlobFileInformation source = GCSServiceRoutines.createInitialUploadInfo(
				path, filename, 
				filecontext, 
				uploadDescriptionText,
				sessionID,obj.getOwner());
		*/
		
		return hierarchy;
	}

	public static DatabaseObjectHierarchy createRepositoryDataFile(DatabaseObjectHierarchy stagehierarchy,
			DataCatalogID catalogid) {
		System.out.println("createRepositoryDataFile  DataCatalogID\n" + catalogid);
		InterpretBaseData interpret = InterpretBaseData.RepositoryDataFile;
		DatabaseObject obj = new DatabaseObject(catalogid);
		String id = catalogid.getFullName();
		obj.setIdentifier(id);
		System.out.println("1 -----------------------------------------------------------------------------");
		System.out.println("createRepositoryDataFile: 1\n" + obj.toString("createRepositoryDataFile: "));
		System.out.println("2 -----------------------------------------------------------------------------");
		DatabaseObjectHierarchy repositoryhier = interpret.createEmptyObject(obj);
		System.out.println("3 -----------------------------------------------------------------------------");
		System.out.println("createRepositoryDataFile  DatabaseObjectHierarchy\n" + repositoryhier);
		RepositoryDataFile repository = (RepositoryDataFile) repositoryhier.getObject();
		System.out.println("4 -----------------------------------------------------------------------------");
		String repcatid = repository.getCatalogDataID();
		DatabaseObjectHierarchy repcathier = repositoryhier.getSubObject(repcatid);
		DataCatalogID cat = (DataCatalogID) repcathier.getObject();
		cat.localFill(catalogid);
		System.out.println(cat.toString("createRepositoryDataFile: cat modified: "));
		System.out.println("5 -----------------------------------------------------------------------------");
		
		System.out.println("createRepositoryDataFile: 2");
		RepositoryFileStaging staging = (RepositoryFileStaging) stagehierarchy.getObject();
		System.out.println("6 -----------------------------------------------------------------------------");
		DatabaseObjectHierarchy gcsdescrhier = stagehierarchy.getSubObject(staging.getBlobFileInformation());
		GCSBlobFileInformation gcs = (GCSBlobFileInformation) gcsdescrhier.getObject();
		String text = gcs.getDescription();
		System.out.println("7 -----------------------------------------------------------------------------");
		
		System.out.println("createRepositoryDataFile: 3");
		DatabaseObjectHierarchy repdescrhier = repositoryhier.getSubObject(repository.getDescriptionDataData());
		DescriptionDataData repdescr = (DescriptionDataData) repdescrhier.getObject();
		repdescr.setDescriptionAbstract(text);
		repdescr.setOnlinedescription(cat.getFullName());

		
		return repositoryhier;
	}
	
}
