package info.esblurock.reaction.chemconnect.core.client.gcs;

import com.google.gwt.user.client.ui.Widget;

import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public interface VisualizationOfBlobStorage {
	public void insertVisualization(Widget panel);
	public void insertCatalogObject(DatabaseObjectHierarchy subs);
}
