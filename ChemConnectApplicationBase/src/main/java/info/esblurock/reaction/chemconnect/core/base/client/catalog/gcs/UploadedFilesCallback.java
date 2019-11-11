package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

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
		StandardWindowVisualization.errorWindowMessage("ERROR: Uploaded files", ex.toString());
	}

	@Override
	public void onSuccess(ArrayList<DatabaseObjectHierarchy> results) {
		MaterialLoader.loading(false);
		for(DatabaseObjectHierarchy hierarchy: results) {
			if(!rows) {
				top.addCollapsible(hierarchy);
			} else {
				StandardWindowVisualization.errorWindowMessage("ERROR: Uploaded files", "no rows");
			}
		}
	}
}
