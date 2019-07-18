package info.esblurock.reaction.core.server.base.create;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.InitialStagingRepositoryFile;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryFileStaging;
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

}
