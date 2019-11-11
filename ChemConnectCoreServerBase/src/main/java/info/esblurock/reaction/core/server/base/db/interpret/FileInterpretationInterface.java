package info.esblurock.reaction.core.server.base.db.interpret;

import java.io.IOException;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;

public interface FileInterpretationInterface {
	
	abstract public DatabaseObjectHierarchy interpretBlobFile(GCSBlobFileInformation info, 
			DatabaseObject obj, DataCatalogID catid) throws IOException;

}
