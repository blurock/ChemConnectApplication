package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs;

import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public interface UploadedFilesInterface {

	void addCollapsible(DatabaseObjectHierarchy coll); 
	MaterialPanel getModalPanel();
}
