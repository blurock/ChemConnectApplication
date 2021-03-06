package info.esblurock.reaction.chemconnect.core.client.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public class SubCatagoryHierarchyCallback implements AsyncCallback<DatabaseObjectHierarchy> {

	SubCatagoryHierarchyCallbackInterface top;
	
	public SubCatagoryHierarchyCallback(SubCatagoryHierarchyCallbackInterface top) {
		this.top = top;
		MaterialLoader.loading(true);
	}
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: sub catagory hierarchy\n" + arg0.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy subs) {
		MaterialLoader.loading(false);
		top.setInHierarchy(subs);
	}

}
