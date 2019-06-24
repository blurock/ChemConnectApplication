package info.esblurock.reaction.chemconnect.core.base.client.image;

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
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.image.DatasetImage;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.modal.OKAnswerInterface;
import info.esblurock.reaction.chemconnect.core.base.client.modal.OKModal;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SaveDatasetCatalogHierarchy;

public class DatasetImageHeader extends Composite implements OKAnswerInterface {

	private static DatasetImageHeaderUiBinder uiBinder = GWT.create(DatasetImageHeaderUiBinder.class);

	interface DatasetImageHeaderUiBinder extends UiBinder<Widget, DatasetImageHeader> {
	}


	@UiField
	MaterialTooltip headertooltip;
	@UiField
	MaterialLink header;
	@UiField
	MaterialLink delete;
	@UiField
	MaterialLink save;
	
	StandardDatasetObjectHierarchyItem item;
	DatabaseObjectHierarchy hierarchy;
	DatasetImage image;
	DataCatalogID catid;
	
	public DatasetImageHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public DatasetImageHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		hierarchy = item.getHierarchy();
		init();
		image = (DatasetImage) hierarchy.getObject();
		DatabaseObjectHierarchy catidhierarchy = hierarchy.getSubObject(image.getCatalogDataID());
		catid = (DataCatalogID) catidhierarchy.getObject();
		header.setText(catid.getSimpleCatalogName());
	}
	
	void init() {
		headertooltip.setText("Simple Name");
		header.setText("Simple");
	}

	@UiHandler("save")
	void onClickSave(ClickEvent e) {
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
		StandardWindowVisualization.successWindowMessage("code not activated yet");
		async.deleteObject(image.getIdentifier(),
				StandardDataKeywords.datasetImage,
				new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						StandardWindowVisualization.successWindowMessage("Specification deletion successful");
					}
					
					@Override
					public void onFailure(Throwable caught) {
						StandardWindowVisualization.errorWindowMessage("Specification deletion", caught.toString());
					}
				});
	}
	@UiHandler("header")
	void onClickHeader(ClickEvent e) {
		Window.alert("Header!");
	}

}
