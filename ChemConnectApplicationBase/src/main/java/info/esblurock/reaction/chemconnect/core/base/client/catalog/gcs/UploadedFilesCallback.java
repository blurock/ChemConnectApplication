package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryFileStaging;

public class UploadedFilesCallback implements AsyncCallback<ArrayList<DatabaseObjectHierarchy>> {

	UploadedFilesInterface top;
	boolean rows;
	
	public UploadedFilesCallback(UploadedFilesInterface top, boolean rows) {
		this.top = top;
		this.rows = rows;
		MaterialLoader.loading(true);
		}
	@Override
	public void onFailure(Throwable ex) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Uploaded files\n" + ex.toString());
	}

	@Override
	public void onSuccess(ArrayList<DatabaseObjectHierarchy> results) {
		MaterialLoader.loading(false);
		for(DatabaseObjectHierarchy hierarchy: results) {
			if(!rows) {
				top.addCollapsible(hierarchy);
			} else {
				Window.alert("Rows not implemented yet");
			}
		}
	}
}
