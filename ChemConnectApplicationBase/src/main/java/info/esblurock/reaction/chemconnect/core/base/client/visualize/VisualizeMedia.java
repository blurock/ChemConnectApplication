package info.esblurock.reaction.chemconnect.core.base.client.visualize;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;

public enum VisualizeMedia implements VisualizeMediaInterface {

	FileTypeImageJPEG {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, DataCatalogID catid,
				boolean titleGiven, VisualizationOfBlobStorage visual) {
			getInterpretedBlobImage(info,interpret,catid,titleGiven,visual);
		}

		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title,
				VisualizationOfBlobStorage visual) {
		}
		
	}, FileTypeImageJPG {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, DataCatalogID catid,
				boolean titleGiven, VisualizationOfBlobStorage visual) {
			getInterpretedBlobImage(info,interpret,catid,titleGiven,visual);
		}

		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title,
				VisualizationOfBlobStorage visual) {
		}
		
	}, FileTypeImageBMP {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, DataCatalogID catid,
				boolean titleGiven, VisualizationOfBlobStorage visual) {
			getInterpretedBlobImage(info,interpret,catid,titleGiven,visual);
		}

		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title,
				VisualizationOfBlobStorage visual) {
		}
		
	}, FileTypeImageGIF {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, DatabaseObject interpret, DataCatalogID catid,
				boolean titleGiven, VisualizationOfBlobStorage visual) {
			getInterpretedBlobImage(info,interpret,catid,titleGiven,visual);
		}

		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title,
				VisualizationOfBlobStorage visual) {
		}
		
	}
	;
	
	abstract public void getInterpretedBlob(GCSBlobFileInformation info, 
			DatabaseObject interpret, 
			DataCatalogID catid, 
			boolean titleGiven,
			VisualizationOfBlobStorage visual);
	abstract public void insertVisualization(DatabaseObjectHierarchy object, String title, 
			VisualizationOfBlobStorage visual);
	
	
	protected void getInterpretedBlobImage(GCSBlobFileInformation info, DatabaseObject interpret, DataCatalogID catid,
			boolean titleGiven, VisualizationOfBlobStorage visual) {
				
		
	}
	
		
	
}
