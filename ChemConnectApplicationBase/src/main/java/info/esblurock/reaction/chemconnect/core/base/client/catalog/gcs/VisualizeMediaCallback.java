package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.base.client.ClientEnumerateUtilities;
import info.esblurock.reaction.chemconnect.core.base.client.visualize.VisualizationOfBlobStorage;
import info.esblurock.reaction.chemconnect.core.base.client.visualize.VisualizeMediaBase;
import info.esblurock.reaction.chemconnect.core.base.client.visualize.VisualizeMediaInterface;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public class VisualizeMediaCallback implements AsyncCallback<DatabaseObjectHierarchy> {

	String type;
	String title;
	VisualizationOfBlobStorage visual;
	
	public VisualizeMediaCallback(String type, String title, VisualizationOfBlobStorage visual) {
		this.type = type;
		this.visual = visual;
		this.title = title;
		MaterialLoader.loading(true);
	}
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("Error in reading and interpreting blob\n" + arg0.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy obj) {
		VisualizeMediaInterface visualization = ClientEnumerateUtilities.visualizeMedia.valueOf(type);
		visualization.insertVisualization(obj, title, visual);
		MaterialLoader.loading(false);
	}

}
