package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;

public interface InterpretUploadedFileInterface {
	public abstract void interpretStructure(DatabaseObject obj, DataCatalogID catid, String visualType, 
			GCSBlobFileInformation info, UploadedElementCollapsible uploaded);

}
