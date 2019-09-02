package info.esblurock.reaction.chemconnect.core.base.client.catalog.consortium;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SaveDatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.modal.OKAnswerInterface;
import info.esblurock.reaction.chemconnect.core.base.client.modal.OKModal;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.consortium.Consortium;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;

public class ConsortiumHeader extends Composite implements OKAnswerInterface {

	private static ConsortiumHeaderUiBinder uiBinder = GWT.create(ConsortiumHeaderUiBinder.class);

	interface ConsortiumHeaderUiBinder extends UiBinder<Widget, ConsortiumHeader> {
	}
	
	@UiField
	MaterialTooltip consortiumnametooltip;
	@UiField
	MaterialLink consortiumname;
	@UiField
	MaterialLink delete;
	@UiField
	MaterialLink save;
	@UiField
	MaterialPanel consortiapanel;
	@UiField
	MaterialLink consortiamembers;
	@UiField
	MaterialLink add;
	
	StandardDatasetObjectHierarchyItem item;
	Consortium consortium;
	DatabaseObjectHierarchy hierarchy;
	DatabaseObjectHierarchy membershierarchy;

	public ConsortiumHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		this.hierarchy = item.getHierarchy();
		this.consortium = (Consortium) hierarchy.getObject();
		membershierarchy = hierarchy.getSubObject(consortium.getConsortiumMember());
		setupMembersList();
		
		
		init();
	}
	

	private void init() {
		consortiamembers.setText("Members");
		consortiumnametooltip.setText("Consortium Name");
		consortiumname.setText(consortium.getConsortiumName());
		item.clearSubObjectPanel();
	}

	private void setupMembersList() {
		for(DatabaseObjectHierarchy member : membershierarchy.getSubobjects()) {
			ConsortiaChip chip = new ConsortiaChip(item.getModalpanel(),consortiapanel,member);
			consortiapanel.add(chip);
		}
	}
	
	@UiHandler("add")
	void onCloseClick(ClickEvent event) {
		AddMemberToConsortiumDialog addmember = new AddMemberToConsortiumDialog(consortiapanel,
				item.getModalpanel(), hierarchy);
		item.getModalpanel().clear();
		item.getModalpanel().add(addmember);
		addmember.open();
	}

	@UiHandler("save")
	void onSaveClick(ClickEvent event) {
		SaveDatasetCatalogHierarchy savemodal = new SaveDatasetCatalogHierarchy(item);
		item.getModalpanel().clear();
		item.getModalpanel().add(savemodal);
		savemodal.openModal();
	}
	@UiHandler("delete")
	void deleteClick(ClickEvent event) {
		OKModal askifok = new OKModal("askifOK","Are you sure you want to delete catalog obj","Delete",this);	
		item.getModalpanel().clear();
		item.getModalpanel().add(askifok);
		askifok.openModal();
	}
	@Override
	public void answeredOK(String answer) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		async.deleteObject(item.getObject().getIdentifier(),
				MetaDataKeywords.consortium,
				new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						MaterialToast.fireToast("Deleted Consortium");
						item.removeFromParent();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						StandardWindowVisualization.errorWindowMessage("Delete Consoritum Object", 
								"Deletion Failed");
					}
				});
	}

}
