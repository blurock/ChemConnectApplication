package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.repository;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SaveDatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.util.TextUtilities;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.InitialStagingRepositoryFile;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryFileStaging;

public class StandardDatabaseRepositoryFileStaging extends Composite {

	private static StandardDatabaseRepositoryFileStagingUiBinder uiBinder = GWT
			.create(StandardDatabaseRepositoryFileStagingUiBinder.class);

	interface StandardDatabaseRepositoryFileStagingUiBinder
			extends UiBinder<Widget, StandardDatabaseRepositoryFileStaging> {
	}


	@UiField
	MaterialTooltip filetooltip;
	@UiField
	MaterialLink filehead;
	@UiField
	MaterialTooltip sourceidtooltip;
	@UiField
	MaterialLink sourceid;
	@UiField
	MaterialTooltip uploadsrctooltip;
	@UiField
	MaterialLink uploadsrc;
	@UiField
	MaterialTextArea description;
	@UiField
	MaterialLink save;
	@UiField
	MaterialLink delete;

	StandardDatasetObjectHierarchyItem item;
	DatabaseObjectHierarchy hierarchy;
	RepositoryFileStaging repository;
	InitialStagingRepositoryFile staging;
	GCSBlobFileInformation gcsblob;
	boolean present;
	
	
	public StandardDatabaseRepositoryFileStaging(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		
		hierarchy = item.getHierarchy();
		repository = (RepositoryFileStaging) hierarchy.getObject();
		
		DatabaseObjectHierarchy gcshierarchy = hierarchy.getSubObject(repository.getBlobFileInformation());
		DatabaseObjectHierarchy stagehierarchy = hierarchy.getSubObject(repository.getRepositoryFile());
		
		gcsblob = (GCSBlobFileInformation) gcshierarchy.getObject();
		staging = (InitialStagingRepositoryFile) stagehierarchy.getObject();
		String presentS = repository.getStagingFilePresent();
		if(presentS.compareTo(Boolean.TRUE.toString()) == 0) {
			present = Boolean.TRUE;
		} else {
			present = Boolean.FALSE;
		}
		filetooltip.setText("Filename");
		filehead.setText(gcsblob.getGSFilename());
		sourceidtooltip.setText("Source ID");
		sourceid.setText(TextUtilities.removeNamespace(staging.getFileSourceIdentifier()));
		uploadsrctooltip.setText("Upload Source");
		uploadsrc.setText(TextUtilities.removeNamespace(staging.getUploadFileSource()));
		description.setText(gcsblob.getDescription());
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
