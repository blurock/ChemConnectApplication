package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.visualize;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.VisualizationOfBlobStorage;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;

public interface VisualizeMediaInterface {

	abstract public void getInterpretedBlob(GCSBlobFileInformation info, 
			DatabaseObject interpret, 
			DataCatalogID catid, 
			boolean titleGiven,
			VisualizationOfBlobStorage visual);
	abstract public void insertVisualization(DatabaseObjectHierarchy object, String title, 
			VisualizationOfBlobStorage visual);

}
