package info.esblurock.reaction.chemconnect.core.base.client.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public class SetUpDatabaseObjectHierarchyCallback implements AsyncCallback<DatabaseObjectHierarchy> {

	MaterialCollapsible panel;
	MaterialPanel modalpanel;
	boolean clearPanel;
	public SetUpDatabaseObjectHierarchyCallback(MaterialCollapsible panel,MaterialPanel modalpanel, boolean clearPanel) {
		this.panel = panel;
		this.modalpanel = modalpanel;
		this.clearPanel = clearPanel;
		MaterialLoader.loading(true);
	}
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Database Catagory\n" + arg0.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy transfer) {
		MaterialLoader.loading(false);
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,transfer,modalpanel);		
		if(clearPanel) {
			panel.clear();
		}
		panel.add(item);
	}

}
