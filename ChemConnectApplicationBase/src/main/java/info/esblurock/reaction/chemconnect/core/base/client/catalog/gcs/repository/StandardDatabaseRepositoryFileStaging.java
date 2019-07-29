package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.repository;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SaveDatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
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
		Window.alert("StandardDatabaseRepositoryFileStaging\n" + hierarchy.toString());
		repository = (RepositoryFileStaging) hierarchy.getObject();
		
		DatabaseObjectHierarchy gcshierarchy = hierarchy.getSubObject(repository.getBlobFileInformation());
		Window.alert("StandardDatabaseRepositoryFileStaging GCSBlobFileInformation\n" + gcshierarchy);
		DatabaseObjectHierarchy stagehierarchy = hierarchy.getSubObject(repository.getRepositoryFile());
		Window.alert("StandardDatabaseRepositoryFileStaging stage\n" + stagehierarchy);
		
		gcsblob = (GCSBlobFileInformation) gcshierarchy.getObject();
		Window.alert("StandardDatabaseRepositoryFileStaging GCSBlobFileInformation object\n" + gcsblob);
		staging = (InitialStagingRepositoryFile) stagehierarchy.getObject();
		Window.alert("StandardDatabaseRepositoryFileStaging stage object\n" + staging);
		Window.alert("StandardDatabaseRepositoryFileStaging 1");
		String presentS = repository.getStagingFilePresent();
		if(presentS.compareTo(Boolean.TRUE.toString()) == 0) {
			present = Boolean.TRUE;
		} else {
			present = Boolean.FALSE;
		}
		
		Window.alert("StandardDatabaseRepositoryFileStaging 2 " + present);
		
		filetooltip.setText("Filename");
		filehead.setText(gcsblob.getGSFilename());
		sourceidtooltip.setText("Source ID");
		sourceid.setText(staging.getFileSourceIdentifier());
		uploadsrctooltip.setText("Upload Source");
		uploadsrc.setText(staging.getUploadFileSource());
		description.setText(gcsblob.getDescription());
		Window.alert("StandardDatabaseRepositoryFileStaging 3");
		
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
