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

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.modal.DeleteCatalogObject;
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

public class StandardDatabaseRepositoryFileStaging extends Composite 
	implements ObjectVisualizationInterface {

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
	MaterialLink delete;
	@UiField
	MaterialLink interpret;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialCollapsible collapsible;
	@UiField
	MaterialLink repositoryfiletitle;
	@UiField
	MaterialLink visualizationtitle;
	@UiField
	MaterialCollapsible repositoryobject;
	@UiField
	MaterialCollapsible visualizationobject;
	//@UiField
	//MaterialPanel choosepanel;
	@UiField
	MaterialPanel choosename;
	//@UiField
	//MaterialTextArea description;

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
		init();
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
		filetooltip.setText(gcsblob.getFiletype());
		filehead.setText(gcsblob.getGSFilename());
		sourceidtooltip.setText("Source ID");
		sourceid.setText(TextUtilities.removeNamespace(staging.getFileSourceIdentifier()));
		uploadsrctooltip.setText("Upload Source");
		uploadsrc.setText(TextUtilities.removeNamespace(staging.getUploadFileSource()));
	}
	private void init() {
		interpret.setText("Interpret Repository File");
		repositoryfiletitle.setText("Repository File Information");
		visualizationtitle.setText("Repository File Interpretation");
		//collapsible.setVisible(false);
		//choosepanel.setVisible(false);
	}

	@UiHandler("interpret")
	public void onClickInterpret(ClickEvent event) {
		//choosepanel.setVisible(true);
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(MetaDataKeywords.dataTypeFileFormat);
		String objectS = StandardDataKeywords.repositoryDataFile;
		ChooseFullNameFromCatagoryRow choose = new ChooseFullNameFromCatagoryRow(this, 
				repository.getOwner(), objectS, choices, modalpanel);
		choosename.add(choose);
/*
		TransformRepositoryFile transform = new TransformRepositoryFile(this, repository.getOwner(),
				gcsblob.getDescription(),
				objectS, choices, modalpanel);
		modalpanel.clear();
		modalpanel.add(transform);
		transform.open();
		*/
	}
	@UiHandler("delete")
	public void onClickDelete(ClickEvent event) {
		
		
		
		MaterialToast.fireToast("Delete not implemented yet");
	}

	public void updateData() {
		
	}
	@Override
	public void createCatalogObject(DatabaseObject obj, DataCatalogID catid) {
		//createRepositoryDataFile(obj,catid,description.getText());
		createRepositoryDataFile(obj,catid,"");
	}

	public void createRepositoryDataFile(DatabaseObject obj, DataCatalogID catid, String description) {
		gcsblob.setDescription(description);
		UserImageServiceAsync async = GWT.create(UserImageService.class);
		async.createRepositoryDataFile(hierarchy, catid, new AsyncCallback<DatabaseObjectHierarchy>() {
			@Override
			public void onSuccess(DatabaseObjectHierarchy repository) {
				MaterialToast.fireToast("Repository File Registered");
				StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(repository);
				repositoryobject.add(item);
				Window.alert("createRepositoryDataFile: repository\n"+ repository.toString());
				async.getBlobDatasetObject(gcsblob, obj, catid, new AsyncCallback<DatabaseObjectHierarchy>() {
					@Override
					public void onSuccess(DatabaseObjectHierarchy datasetimage) {
						MaterialToast.fireToast("Repository File Registered");
						StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(datasetimage);
						visualizationobject.add(item);
						Window.alert("createRepositoryDataFile image\n"+ datasetimage.toString());
					}
					@Override
					public void onFailure(Throwable caught) {
						StandardWindowVisualization.errorWindowMessage(
								"StandardDatabaseRepositoryFileStaging: Image creation", 
								caught.toString());
					}
				});
			}
			
			@Override
			public void onFailure(Throwable caught) {
				StandardWindowVisualization.errorWindowMessage("StandardDatabaseRepositoryFileStaging: createCatalogObject", caught.toString());
			}
		});
	}

	public void insertCatalogObject(DatabaseObjectHierarchy result) {
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(result);
		repositoryobject.add(item);		
	}

	@UiHandler("delete")
	void deleteClick(ClickEvent event) {
		DeleteCatalogObject deleteobject = new DeleteCatalogObject(repository.getIdentifier(), 
				StandardDataKeywords.repositoryFileStaging, modalpanel, this);
		deleteobject.deleteObject();
	}
	
}
