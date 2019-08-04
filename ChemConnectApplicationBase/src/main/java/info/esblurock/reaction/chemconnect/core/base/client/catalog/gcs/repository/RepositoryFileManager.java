package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.repository;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.tree.MaterialTree;
import gwt.material.design.addins.client.tree.MaterialTreeItem;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.HierarchyNodeCallback;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.HierarchyNodeCallbackInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.hierarchy.ConvertToMaterialTree;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.util.TextUtilities;
import info.esblurock.reaction.chemconnect.core.base.client.view.RepositoryFileManagerView;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.base.client.view.ManageCatalogHierarchyView.Presenter;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public class RepositoryFileManager extends Composite 
implements RepositoryFileManagerView,ChooseFromConceptHeirarchy,HierarchyNodeCallbackInterface {

	private static RepositoryFileManagerUiBinder uiBinder = GWT.create(RepositoryFileManagerUiBinder.class);

	interface RepositoryFileManagerUiBinder extends UiBinder<Widget, RepositoryFileManager> {
	}

	Presenter listener;
	String titlename;
	
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialTooltip identifiertip;
	@UiField
	MaterialLink filetype;
	@UiField
	MaterialTree tree;
	@UiField
	MaterialCollapsible objectpanel;

	
	ArrayList<String> choices;
	boolean filetypechosen;

	public RepositoryFileManager() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	
	private void init() {
		identifiertip.setText("Choose Repository File type");
		filetype.setText("Repository File Type");
		choices = new ArrayList<String>();
		choices.add(MetaDataKeywords.dataTypeFileFormat);
		filetypechosen = false;
	}
	
	@UiHandler("filetype")
	public void filetypeClick(ClickEvent event) {
		setUpConceptChoices();
	}
	
	private void  setUpConceptChoices() {
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
		modalpanel.clear();
		modalpanel.add(choosedevice);
		choosedevice.open();				
	}
	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		filetype.setText(TextUtilities.removeNamespace(concept));
		StringBuilder build = new StringBuilder();
		for(String element : path) {
			build.append(TextUtilities.removeNamespace(element) + " ");
		}
		identifiertip.setText(build.toString());
		filetypechosen = true;
		setupIDHierarchy(concept);
	}
	
	private void setupIDHierarchy(String catalogtype) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		HierarchyNodeCallback callback = new HierarchyNodeCallback(this);
		async.getIDHierarchyFromDataCatalogID(null,catalogtype,callback);
	}
	@Override
	public void insertTree(HierarchyNode hierarchy) {
		tree.clear();
		ConvertToMaterialTree.addHierarchyTop(hierarchy, tree);
		tree.collapse();		
	}
	@UiHandler("tree")
	public void onClose(CloseEvent<MaterialTreeItem> event) {
		MaterialTreeItem item = (MaterialTreeItem) event.getTarget();
		selected(item);
	}

	@UiHandler("tree")
	public void onOpen(OpenEvent<MaterialTreeItem> event) {
		MaterialTreeItem item = (MaterialTreeItem) event.getTarget();
		selected(item);
	}

	@UiHandler("tree")
	public void onSelected(SelectionEvent<MaterialTreeItem> event) {
		MaterialTreeItem item = (MaterialTreeItem) event.getSelectedItem();
		selected(item);
	}
	/*
	 * Grab selected information from tree and call 'catagoryChosen' in the 
	 * class that called this modal class
	 */
	private void selected(MaterialTreeItem item) {
		if (item.getTreeItems().size() == 0) {
			String id = item.getText();
			loadCatalogObjectFromID(id);
		} else {
			item.expand();
		}
		
	}

	private void loadCatalogObjectFromID(String id) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		async.getCatalogObject(id,StandardDataKeywords.repositoryDataFile,
				new AsyncCallback<DatabaseObjectHierarchy>() {

					@Override
					public void onFailure(Throwable caught) {
						MaterialLoader.loading(false);
						StandardWindowVisualization.errorWindowMessage("Catalog Object", caught.toString());
					}

					@Override
					public void onSuccess(DatabaseObjectHierarchy hierarchy) {
						MaterialLoader.loading(false);
						insertCatalogObject(hierarchy);
					}
		});
	}
	
	public void insertCatalogObject(DatabaseObjectHierarchy subs) {
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null, subs, modalpanel);
		objectpanel.add(item);
	}
	
	@Override
	public void setName(String titlename) {
		this.titlename = titlename;
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
		
	}



}
