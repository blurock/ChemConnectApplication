package info.esblurock.reaction.chemconnect.core.base.client.catalog.person;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SetOfObjectsCallbackInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.contact.QueryNameOfPersonInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.contact.QueryNameOfPersonModal;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.view.DatabasePersonDefinitionView;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public class DatabasePersonDefinition extends Composite  implements ObjectVisualizationInterface, SetOfObjectsCallbackInterface, QueryNameOfPersonInterface, DatabasePersonDefinitionView {

	private static DatabasePersonDefinitionUiBinder uiBinder = GWT.create(DatabasePersonDefinitionUiBinder.class);

	interface DatabasePersonDefinitionUiBinder extends UiBinder<Widget, DatabasePersonDefinition> {
	}

	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialPanel topPanel;
	@UiField
	MaterialLink peopleExisingHeader;
	@UiField
	MaterialCollapsible existingPeople;
	@UiField
	MaterialLink peopleCreatedHeader;
	@UiField
	MaterialCollapsible createdPeople;
	@UiField
	MaterialLink refresh;

	Presenter listener;
	ChooseFullNameFromCatagoryRow choose;
	String access;
	NameOfPerson person;
	DatabaseObject obj;
	DataCatalogID datid;
	ArrayList<String> choices;

	public DatabasePersonDefinition() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		
	}

	void init() {
		String user = Cookies.getCookie("user");
		if(user != null) {
			peopleExisingHeader.setText("Profiles managed by User: " + user);
		} else {
			peopleExisingHeader.setText("Profiles managed by User: refresh to see profiles");
		}
		peopleCreatedHeader.setText("New person definitions");
		
		person = new NameOfPerson();
		
		String peopleType = MetaDataKeywords.userRoleChoices;;
		choices = new ArrayList<String>();
		choices.add(peopleType);
		choose = new ChooseFullNameFromCatagoryRow(this,user,MetaDataKeywords.databasePerson,choices,modalpanel);
		topPanel.add(choose);
	}
	
	@UiHandler("refresh")
	public void refreshClicked(ClickEvent event) {
		refresh();
	}
	
	public void refresh() {
		existingPeople.clear();
		refreshAccessibleUsers();
		topPanel.clear();
		String user = Cookies.getCookie("user");
		choose = new ChooseFullNameFromCatagoryRow(this,user,MetaDataKeywords.databasePerson,choices,modalpanel);
		topPanel.add(choose);
		if(user != null) {
			peopleExisingHeader.setText("Profiles managed by User: " + user);
		} else {
			peopleExisingHeader.setText("Profiles managed by User: refresh to see profiles");
		}
	}

	private void refreshAccessibleUsers() {
		String databasePerson = MetaDataKeywords.databasePerson;
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		async.getSetOfDatabaseObjectHierarchyForUser(databasePerson,
				new AsyncCallback<ArrayList<DatabaseObjectHierarchy>>() {
					@Override
					public void onFailure(Throwable caught) {
						StandardWindowVisualization.errorWindowMessage("Set in objects",  caught.toString());
					}

					@Override
					public void onSuccess(ArrayList<DatabaseObjectHierarchy> objects) {
						setInOjbects(objects);
					}
			
		});

	}
	@Override
	public void setInOjbects(ArrayList<DatabaseObjectHierarchy> objects) {
		for(DatabaseObjectHierarchy object : objects) {
			StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,object,modalpanel);			
			existingPeople.add(item);
		}		
	}

	@Override
	public void createCatalogObject(DatabaseObject obj,DataCatalogID datid) {
		this.datid = datid;
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,null);
		
		
		person = new NameOfPerson(structure,"title","name", "familyname");
		QueryNameOfPersonModal modal = new QueryNameOfPersonModal(person, this);
		modalpanel.clear();
		modalpanel.add(modal);
		modal.open();
	}

	@Override
	public void insertNameOfPerson(NameOfPerson person) {
		String catagory = choose.getCatagory();
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		async.createDatabasePerson(person, catagory, person, 
				UserAccountKeys.accessTypeDataUser,
				datid, 
				new AsyncCallback<DatabaseObjectHierarchy>() {

					@Override
					public void onFailure(Throwable caught) {
						StandardWindowVisualization.errorWindowMessage("Insert name of person", caught.toString());
					}

					@Override
					public void onSuccess(DatabaseObjectHierarchy result) {
						StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,result,modalpanel);		
						createdPeople.add(item);
					}
		});
	}

	@Override
	public void insertCatalogObject(DatabaseObjectHierarchy subs) {
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,subs,modalpanel);
		existingPeople.add(item);
	}
	@Override
	public void setName(String titleName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
