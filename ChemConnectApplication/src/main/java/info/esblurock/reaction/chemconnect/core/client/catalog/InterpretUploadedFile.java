package info.esblurock.reaction.chemconnect.core.client.catalog;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.UploadedElementCollapsible;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.VisualizeMedia;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;

public enum InterpretUploadedFile {
	DataFileMatrixStructure {

		@Override
		public void interpretStructure(DatabaseObject obj, DataCatalogID catid, String visualType, 
				GCSBlobFileInformation info, UploadedElementCollapsible uploaded) {
			VisualizeMedia visual = VisualizeMedia.valueOf(ChemConnectCompoundDataStructure.removeNamespace(visualType));

			String sourceType = SpreadSheetInputInformation.BLOBSOURCE;
			String source = info.getGSFilename();
			obj.setIdentifier(catid.getFullName());
			ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(catid,catid.getIdentifier());
			SpreadSheetInputInformation spread = new SpreadSheetInputInformation(structure," ",sourceType,source);
			if(visual != null) {
				visual.getInterpretedBlob(info, spread, catid,true,uploaded);
			}
		}
	}
	public abstract void interpretStructure(DatabaseObject obj, DataCatalogID catid, String visualType, 
			GCSBlobFileInformation info, UploadedElementCollapsible uploaded);

}
