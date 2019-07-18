package info.esblurock.reaction.core.server.base.register;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSInputFileInterpretation;
import info.esblurock.reaction.chemconnect.core.base.gcs.InitialStagingRepositoryFile;
import info.esblurock.reaction.chemconnect.core.base.gcs.ParsedFilename;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryDataFile;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryFileStaging;

public class RegisterGCSClasses {

	public static void register() {
		ObjectifyService.register(GCSBlobFileInformation.class);
		ObjectifyService.register(GCSInputFileInterpretation.class);
		ObjectifyService.register(ParsedFilename.class);
		ObjectifyService.register(RepositoryDataFile.class);
		ObjectifyService.register(RepositoryFileStaging.class);
		ObjectifyService.register(InitialStagingRepositoryFile.class);
	}
	
	public static void reset() {
		ResetDatabaseObjects.resetClass(GCSBlobFileInformation.class);
		ResetDatabaseObjects.resetClass(GCSInputFileInterpretation.class);
		ResetDatabaseObjects.resetClass(ParsedFilename.class);
		ResetDatabaseObjects.resetClass(RepositoryDataFile.class);
		ResetDatabaseObjects.resetClass(RepositoryFileStaging.class);
		ResetDatabaseObjects.resetClass(InitialStagingRepositoryFile.class);
	}
}
