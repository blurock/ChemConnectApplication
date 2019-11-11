package info.esblurock.reaction.chemconnect.core.base.client.visualize;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;

public interface VisualizeMediaInterface {

	
	/**
	 * @param info Information to retrieve blob image
	 * @param interpret A class to help interpret blob image, this contents of this class is dependent on the image
	 * @param catid The catalog information
	 * @param titleGiven is the title included in the blob image?
	 * @param visual How to visualize the object.
	 */
	abstract public void getInterpretedBlob(GCSBlobFileInformation info, 
			DatabaseObject interpret, 
			DataCatalogID catid, 
			boolean titleGiven,
			VisualizationOfBlobStorage visual);
	
	
	/**
	 * @param object The object in its hierarchical form
	 * @param title The title of the object
	 * @param visual Contains the working routine to visualize the object.
	 */
	abstract public void insertVisualization(DatabaseObjectHierarchy object, String title, 
			VisualizationOfBlobStorage visual);

}
