package info.esblurock.reaction.chemconnect.core.client.catalog;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SetOfObjectsCallbackInterface;

public class SetOfObjectsCallback implements AsyncCallback<ArrayList<DatabaseObjectHierarchy>> {

	SetOfObjectsCallbackInterface calling;
	
	public SetOfObjectsCallback(SetOfObjectsCallbackInterface calling) {
		this.calling = calling;
		MaterialLoader.loading(true);
		}
	
	@Override
	public void onFailure(Throwable ex) {
		MaterialLoader.loading(false);
		Window.alert("Set of catalog elements:\n" + ex.toString());
	}

	@Override
	public void onSuccess(ArrayList<DatabaseObjectHierarchy> objects) {
		MaterialLoader.loading(false);
		calling.setInOjbects(objects);
	}

}
