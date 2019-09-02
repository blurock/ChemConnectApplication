package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.repository;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SaveDatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.util.TextUtilities;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.InitialStagingRepositoryFile;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryFileStaging;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;

public class StandardDatabaseRepositoryFileStaging extends Composite implements ObjectVisualizationInterface {

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
	@UiField
	MaterialPanel choosename;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialCollapsible collapsible;

	StandardDatasetObjectHierarchyItem item;
	DatabaseObjectHierarchy hierarchy;
	RepositoryFileStaging repository;
	InitialStagingRepositoryFile staging;
	GCSBlobFileInformation gcsblob;
	boolean present;
	ChooseFullNameFromCatagoryRow choose;
	
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
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(MetaDataKeywords.dataTypeFileFormat);
		String object = StandardDataKeywords.repositoryDataFile;
		choose = new ChooseFullNameFromCatagoryRow(this, repository.getOwner(), 
				object, choices, modalpanel);
		choosename.add(choose);
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
		gcsblob.setDescription(description.getText());
	}

	@Override
	public void createCatalogObject(DatabaseObject obj, DataCatalogID catid) {
		updateData();
		UserImageServiceAsync async = GWT.create(UserImageService.class);
		async.createRepositoryDataFile(hierarchy, catid, new AsyncCallback<DatabaseObjectHierarchy>() {
			
			@Override
			public void onSuccess(DatabaseObjectHierarchy result) {
				choose.setVisible(false);
				insertCatalogObject(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				StandardWindowVisualization.errorWindowMessage("StandardDatabaseRepositoryFileStaging: createCatalogObject", caught.toString());
			}
		});
	}

	@Override
	public void insertCatalogObject(DatabaseObjectHierarchy result) {
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(result);
		collapsible.add(item);		
	}

}
