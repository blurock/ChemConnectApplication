package info.esblurock.reaction.core.server.base.db.interpret;

import java.io.IOException;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.core.server.base.create.CreateContactObjects;
import info.esblurock.reaction.core.server.base.db.GCSServiceRoutines;

public enum FileInterpretationImpl implements FileInterpretationInterface {

	FileTypeImageJPEG {

		@Override
		public DatabaseObjectHierarchy interpretBlobFile(GCSBlobFileInformation info, DatabaseObject obj,
				DataCatalogID catid) throws IOException {
			String imageType = "dataset:FileTypeImageJPEG";
			return interpretDatasetImage(imageType,info,obj,catid);
		}
		
	}, FileTypeImageJPG {

		@Override
		public DatabaseObjectHierarchy interpretBlobFile(GCSBlobFileInformation info, DatabaseObject obj,
				DataCatalogID catid) throws IOException {
			String imageType = "dataset:FileTypeImageJPG";
			return interpretDatasetImage(imageType,info,obj,catid);
		}
		
	}, FileTypeImageBMP {

		@Override
		public DatabaseObjectHierarchy interpretBlobFile(GCSBlobFileInformation info, DatabaseObject obj,
				DataCatalogID catid) throws IOException {
			String imageType = "dataset:FileTypeImageBMP";
			return interpretDatasetImage(imageType,info,obj,catid);
		}
		
	}, FileTypeImageGIF {

		@Override
		public DatabaseObjectHierarchy interpretBlobFile(GCSBlobFileInformation info, DatabaseObject obj,
				DataCatalogID catid) throws IOException {
			String imageType = "dataset:FileTypeImageGIF";
			return interpretDatasetImage(imageType,info,obj,catid);
		}
		
	};
	
	DatabaseObjectHierarchy interpretDatasetImage(String type, GCSBlobFileInformation info, DatabaseObject obj,
				DataCatalogID catid) throws IOException {
		System.out.println("-------------------   interpretDatasetImage -------------------");
		String url = "https://storage.googleapis.com/" + GCSServiceRoutines.getGCSStorageBucket() + "/"
				+ info.getGSFilename();
		DatabaseObjectHierarchy  hierarchy = CreateContactObjects.fillDatasetImage(obj, catid, type, url);
		System.out.println("-------------------" + hierarchy.toString("interpretDatasetImage: "));
		return hierarchy;
		
	}
	
}
