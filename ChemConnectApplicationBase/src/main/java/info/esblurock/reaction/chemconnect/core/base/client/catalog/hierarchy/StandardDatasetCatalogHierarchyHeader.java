package info.esblurock.reaction.chemconnect.core.base.client.catalog.hierarchy;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.base.client.modal.OKAnswerInterface;
import info.esblurock.reaction.chemconnect.core.base.client.modal.OKModal;
import info.esblurock.reaction.chemconnect.core.base.client.modal.cards.CardModal;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.SubCatagoryHierarchyCallbackInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;


public class StandardDatasetCatalogHierarchyHeader extends Composite 
	implements SubCatagoryHierarchyCallbackInterface, ChooseFromConceptHeirarchy,OKAnswerInterface {

	private static StandardDatasetCatalogHierarchyHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetCatalogHierarchyHeaderUiBinder.class);

	interface StandardDatasetCatalogHierarchyHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetCatalogHierarchyHeader> {
	}

	public StandardDatasetCatalogHierarchyHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	String catagorychoice = MetaDataKeywords.catagoryTypeChoices;

	@UiField
	MaterialLink cataloghead;
	@UiField
	MaterialLink delete;
	@UiField
	MaterialLink add;

	StandardDatasetObjectHierarchyItem item;
	NewSubCatalogWizard wizard;
	MaterialPanel modal;
	CardModal cardmodal;
	DataCatalogID dataid;
	String newSimpleName;
	
	ChooseFullNameFromCatagoryRow catagory;
	ArrayList<String> choices;
	String newChosenCatalogConcept;
	ArrayList<String> newChosenPath;
	ArrayList<String> subcatagories;
	
	public StandardDatasetCatalogHierarchyHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		this.modal = item.getModalpanel();
		DatasetCatalogHierarchy hierarchy = (DatasetCatalogHierarchy) item.getObject();
		DatabaseObjectHierarchy hier = item.getHierarchy();
		String id = hierarchy.getCatalogDataID();
		DatabaseObjectHierarchy cathier = hier.getSubObject(id);
		dataid = (DataCatalogID) cathier.getObject();
		cataloghead.setText(dataid.getSimpleCatalogName());
		init();
	}

	void init() {
		cardmodal = new CardModal();
		choices = new ArrayList<String>();
		choices.add(catagorychoice);
		subcatagories = new ArrayList<String>();
	}
	@UiHandler("delete")
	public void onDeleteClick(ClickEvent event) {
		
		if (item.getSubitems().size() > 0) {
			MaterialToast.fireToast("Delete subcatagories first");
		} else {
			OKModal askifok = new OKModal("askifOK","Are you sure you want to delete catalog obj","Delete",this);	
			item.getModalpanel().clear();
			item.getModalpanel().add(askifok);
			askifok.openModal();
		}
		
	}
	
	@Override
	public void answeredOK(String answer) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		StandardWindowVisualization.successWindowMessage("Code not activated yet");
		async.deleteObject(item.getObject().getIdentifier(),
				MetaDataKeywords.datasetCatalogHierarchy,
				new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						StandardWindowVisualization.errorWindowMessage("Deletion", caught.toString());
					}

					@Override
					public void onSuccess(Void result) {
						StandardWindowVisualization.successWindowMessage("Specification deletion successful");
					}
			
		});
	}

	@UiHandler("add")
	public void onAddClick(ClickEvent event) {
		addCatagory();
	}

	private void addCatagory() {
		wizard = new NewSubCatalogWizard(this);
		cardmodal.setContent(wizard, true);
		modal.clear();
		modal.add(cardmodal);
		cardmodal.open();
	}
	public void insertInitialSubCatagoryInformation() {
		cardmodal.close();
		setUpConceptChoices();
	}
	private void  setUpConceptChoices() {
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
		modal.clear();
		modal.add(choosedevice);
		choosedevice.open();				
	}

	private void addSubCatagory() {
		newSimpleName = wizard.getSimpleName();
		String oneline = wizard.getOneLineDescription();
		
		boolean addsub = true;
		for(String simple : subcatagories) {
			if(simple.compareTo(newSimpleName) == 0) {
				addsub = false;
			}
		}
		if(addsub) {
			DatabaseObject subobj = new DatabaseObject(item.getObject());
			UserImageServiceAsync async = UserImageService.Util.getInstance();
			async.createNewCatalogHierarchy(subobj,newSimpleName,subobj.getIdentifier(),oneline,newChosenCatalogConcept,
					new AsyncCallback<DatabaseObjectHierarchy>() {

						@Override
						public void onFailure(Throwable caught) {
							StandardWindowVisualization.errorWindowMessage("sub catagory hierarchy", caught.toString());
						}

						@Override
						public void onSuccess(DatabaseObjectHierarchy subs) {
							MaterialLoader.loading(false);
							setInHierarchy(subs);
						}
				
			});	
		} else {
			MaterialToast.fireToast("Name already being used in another sub-catagory");
			addCatagory();
		}
	}
	public void setInHierarchy(DatabaseObjectHierarchy subs) {
		StandardDatasetObjectHierarchyItem subhiearchy = new StandardDatasetObjectHierarchyItem(item,subs,modal);
		item.addSubItem(subhiearchy);
		DatasetCatalogHierarchy catalog = (DatasetCatalogHierarchy) subs.getObject();
		String catalogidID = catalog.getCatalogDataID();
		DatabaseObjectHierarchy catalogidhier = subs.getSubObject(catalogidID);
		DataCatalogID catalogID = (DataCatalogID) catalogidhier.getObject();
		subcatagories.add(catalogID.getSimpleCatalogName());
	}

	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		newChosenCatalogConcept = concept;
		newChosenPath = path;
		addSubCatagory();
	}


}
