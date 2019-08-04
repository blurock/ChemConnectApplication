package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.repository;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SaveDatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryDataFile;

public class StandardDatabaseRepositoryDataFile extends Composite {

	private static StandardDatabaseRepositoryDataFileUiBinder uiBinder = GWT
			.create(StandardDatabaseRepositoryDataFileUiBinder.class);

	interface StandardDatabaseRepositoryDataFileUiBinder extends UiBinder<Widget, StandardDatabaseRepositoryDataFile> {
	}
	@UiField
	MaterialTooltip fullname;
	@UiField
	MaterialLink filename;
	@UiField
	MaterialLink save;
	@UiField
	MaterialLink delete;
	
	DatabaseObjectHierarchy hierarchy;		
	RepositoryDataFile repository;
	StandardDatasetObjectHierarchyItem item;
	
	public StandardDatabaseRepositoryDataFile(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.item = item;
		hierarchy = item.getHierarchy();
		repository = (RepositoryDataFile) hierarchy.getObject();
		
		DatabaseObjectHierarchy catidhier = hierarchy.getSubObject(repository.getCatalogDataID());
		DataCatalogID catid = (DataCatalogID) catidhier.getObject();
		
		fullname.setText(catid.getFullName());
		filename.setText(catid.getSimpleCatalogName());
	}
	@UiHandler("save")
	public void onClickSave(ClickEvent event) {
		updateData();
		SaveDatasetCatalogHierarchy savemodal = new SaveDatasetCatalogHierarchy(item);
		item.getModalpanel().clear();
		item.getModalpanel().add(savemodal);
		savemodal.openModal();
	}
	
	@UiHandler("delete")
	public void onClickDelete(ClickEvent event) {
		MaterialToast.fireToast("Delete not implemented yet");
	}

	public void updateData() {
		
	}
}
