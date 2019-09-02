package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs;

import com.google.gwt.user.client.Window;

import gwt.material.design.client.ui.MaterialCollapsible;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SetUpDatabaseObjectHierarchyCallback;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
public enum InterpretUploadedFile implements InterpretUploadedFileInterface {

	DataFileImageStructure {
		@Override
		public void interpretStructure(DatabaseObject obj, DataCatalogID catid, String visualType, 
				GCSBlobFileInformation info, UploadedElementCollapsible uploaded) {
			String source = info.getGSFilename();
			Window.alert("InterpretUploadedFile: DataFileImageStructure source: " + source);
			MaterialCollapsible collapsible = new MaterialCollapsible();
			uploaded.getObjectPanel().add(collapsible);
			SetUpDatabaseObjectHierarchyCallback createcallback = 
					new SetUpDatabaseObjectHierarchyCallback(collapsible, uploaded.getModalPanel(),false);
			UserImageServiceAsync createasync = UserImageService.Util.getInstance();
			createasync.createDatasetImage(obj,catid,visualType, info,createcallback);			
		}
		
	};
	public abstract void interpretStructure(DatabaseObject obj, DataCatalogID catid, String visualType, 
			GCSBlobFileInformation info, UploadedElementCollapsible uploaded);
}
