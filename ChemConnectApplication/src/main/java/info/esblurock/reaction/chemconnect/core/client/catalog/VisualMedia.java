package info.esblurock.reaction.chemconnect.core.client.catalog;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SpreadSheetInputInformation;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SpreadSheetServicesAsync;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.VisualizationOfBlobStorage;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.VisualizeMediaCallback;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;

public enum VisualMedia {
	FileTypeSpreadSheetTabDelimited {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, 
				DatabaseObject interpret, 
				DataCatalogID catid, 
				boolean titleGiven,
				VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.TabDelimited,
					spread.getSourceType(),spread.getSource());
			interpretSpreadSheetAsMatrix("FileTypeSpreadSheetTabDelimited",catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
				insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, FileTypeSpreadSheetSpaceDelimited {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, 
				DatabaseObject interpret, 
				DataCatalogID catid, 
				boolean titleGiven,
				VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.SpaceDelimited,
					spread.getSourceType(),spread.getSource());
			interpretSpreadSheetAsMatrix("FileTypeSpreadSheetSpaceDelimited",catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, FileTypeSpreadSheetCSV {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, 
				DatabaseObject interpret, 
				DataCatalogID catid, 
				boolean titleGiven,
				VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.CSV,
					spread.getSourceType(),
					spread.getSource());
			interpretSpreadSheetAsMatrix("FileTypeSpreadSheetCSV",catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, FileTypeMSExcel {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, 
				DatabaseObject interpret, 
				DataCatalogID catid, 
				boolean titleGiven,
				VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.XLS,
					spread.getSourceType(),spread.getSource());
			interpretSpreadSheetAsMatrix("FileTypeMSExcel",catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	}, FileTypeExcelOffice {

		@Override
		public void getInterpretedBlob(GCSBlobFileInformation info, 
				DatabaseObject interpret, 
				DataCatalogID catid, 
				boolean titleGiven,
				VisualizationOfBlobStorage visual) {
			SpreadSheetInputInformation spread = (SpreadSheetInputInformation) interpret;
			SpreadSheetInputInformation modified = new SpreadSheetInputInformation(spread,
					SpreadSheetInputInformation.XLSX,
					spread.getSourceType(),spread.getSource());
			interpretSpreadSheetAsMatrix("FileTypeExcelOffice", catid, info,modified,visual);
		}
		@Override
		public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual) {
			insertSpreadSheetVisualization(object,title,visual);
		}
		
	}
	
	abstract public void getInterpretedBlob(GCSBlobFileInformation info, 
			DatabaseObject interpret, 
			DataCatalogID catid, 
			boolean titleGiven,
			VisualizationOfBlobStorage visual);
	
	
	protected void getInterpretedBlobImage(GCSBlobFileInformation info, DatabaseObject interpret, DataCatalogID catid,
			boolean titleGiven, VisualizationOfBlobStorage visual) {
				
		
	}
	abstract public void insertVisualization(DatabaseObjectHierarchy object, String title, VisualizationOfBlobStorage visual);
	
	void interpretSpreadSheetAsMatrix(String type, 
			DataCatalogID catid,
			GCSBlobFileInformation info,
			SpreadSheetInputInformation spread, 
			VisualizationOfBlobStorage visual) {
		VisualizeMediaCallback callback = new VisualizeMediaCallback(type, info.getGSFilename(), visual);
		SpreadSheetServicesAsync async = SpreadSheetServices.Util.getInstance();
		async.interpretSpreadSheetGCS(info, spread, catid, true, callback);		
	}
	
	void insertSpreadSheetVisualization(DatabaseObjectHierarchy hierarchy, String title, VisualizationOfBlobStorage visual) {
		visual.insertCatalogObject(hierarchy);
	}

}
