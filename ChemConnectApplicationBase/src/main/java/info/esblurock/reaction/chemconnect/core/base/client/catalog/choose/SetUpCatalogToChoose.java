package info.esblurock.reaction.chemconnect.core.base.client.catalog.choose;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public class SetUpCatalogToChoose implements AsyncCallback<DatabaseObjectHierarchy> {

	
	ChooseCatalogHiearchyModal modal;
	
	public SetUpCatalogToChoose(ChooseCatalogHiearchyModal modal) {
		this.modal = modal;
		MaterialLoader.loading(true);
	}
	@Override
	public void onFailure(Throwable arg0) {
		StandardWindowVisualization.errorWindowMessage("ERROR: In catalog chosen\n", arg0.toString());
		MaterialLoader.loading(false);
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy hierarchy) {
		modal.setInHierarchy(hierarchy);
		MaterialLoader.loading(false);
	}

}
