package info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts;

import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;

public class ConceptHierarchyCallback implements AsyncCallback<HierarchyNode> {

	ChooseFromConceptHierarchies top;
	
	public ConceptHierarchyCallback(ChooseFromConceptHierarchies top) {
		super();
		this.top = top;
		MaterialLoader.loading(true);
		}

	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		System.out.println("Error: concept hierarchy\n" + arg0);
	}

	@Override
	public void onSuccess(HierarchyNode hierarchy) {
		MaterialLoader.loading(false);
		top.setupTree(hierarchy);
	}

}
