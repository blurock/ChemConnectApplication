package info.esblurock.reaction.chemconnect.core.base.client.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;

public class HierarchyNodeCallback implements AsyncCallback<HierarchyNode>{
	HierarchyNodeCallbackInterface top;
	public HierarchyNodeCallback(HierarchyNodeCallbackInterface top) {
		this.top = top;
		MaterialLoader.loading(false);	}
	
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		StandardWindowVisualization.errorWindowMessage("ERROR: Hierarchy", arg0.toString());
	}

	@Override
	public void onSuccess(HierarchyNode topnode) {
		MaterialLoader.loading(false);
		top.insertTree(topnode);
	}
}
