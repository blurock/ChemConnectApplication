package info.esblurock.reaction.chemconnect.core.base.client.modal;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;

public class DeleteCatalogObject implements OKAnswerInterface {
	
	MaterialPanel modal;
	String id;
	String type;
	Widget widget;
	
	public DeleteCatalogObject(String id, String type, MaterialPanel modal, Widget widget) {
		this.modal = modal;
		this.id = id;
		this.type = type;
		this.widget = widget;
	}

	public void deleteObject() {
		OKModal askifok = new OKModal("askifOK","Are you sure you want to delete catalog obj","Delete",this);	
		modal.clear();
		modal.add(askifok);
		askifok.openModal();
	}
	@Override
	public void answeredOK(String answer) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		async.deleteObject(id,
				type,
				new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						MaterialToast.fireToast("Deleted " + ChemConnectCompoundDataStructure.removeNamespace(type));
						widget.removeFromParent();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						StandardWindowVisualization.errorWindowMessage("Delete Consoritum Object", 
								"Deletion Failed");
					}
				});
	}
	
	
}
