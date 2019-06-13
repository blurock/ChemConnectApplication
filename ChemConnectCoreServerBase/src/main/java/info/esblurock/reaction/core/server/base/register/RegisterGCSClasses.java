package info.esblurock.reaction.core.server.base.register;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSInputFileInterpretation;
import info.esblurock.reaction.chemconnect.core.base.gcs.ParsedFilename;

public class RegisterGCSClasses {

	public static void register() {
		ObjectifyService.register(GCSBlobFileInformation.class);
		ObjectifyService.register(GCSInputFileInterpretation.class);
		ObjectifyService.register(ParsedFilename.class);
	}
	
	public static void reset() {
		ResetDatabaseObjects.resetClass(GCSBlobFileInformation.class);
		ResetDatabaseObjects.resetClass(GCSInputFileInterpretation.class);
		ResetDatabaseObjects.resetClass(ParsedFilename.class);
	}
}
