package info.esblurock.reaction.chemconnect.core.base.client.catalog.person;

import java.util.ArrayList;
import java.util.List;

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
import gwt.material.design.client.ui.MaterialTitle;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SetOfObjectsCallbackInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.RetrieveOwnerPrivileges;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.RetrieveOwnerPrivilegesInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.contact.QueryNameOfPersonInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.contact.QueryNameOfPersonModal;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.view.DatabasePersonDefinitionView;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public class DatabasePersonDefinition extends Composite  
implements ObjectVisualizationInterface, SetOfObjectsCallbackInterface, 
QueryNameOfPersonInterface, DatabasePersonDefinitionView,RetrieveOwnerPrivilegesInterface {

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
	MaterialLink ownersHeader;
	@UiField
	MaterialCollapsible ownersPeople;
	@UiField
	MaterialLink refresh;
	@UiField
	MaterialPanel fullnamepanel;

	Presenter listener;
	ChooseFullNameFromCatagoryRow choose;
	String access;
	NameOfPerson person;
	DatabaseObject obj;
	DataCatalogID datid;
	ArrayList<String> choices;
	List<String> owners;

	public DatabasePersonDefinition() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		
	}

	void init() {
		owners = null;
		person = new NameOfPerson();
		headers();
		String peopleType = MetaDataKeywords.userRoleChoices;;
		choices = new ArrayList<String>();
		choices.add(peopleType);
		choosePanel();
		RetrieveOwnerPrivileges retrieve = new RetrieveOwnerPrivileges(this);
		retrieve.getPrivileges();
	}
	private void choosePanel() {
		String user = Cookies.getCookie("user");
		choose = new ChooseFullNameFromCatagoryRow(this,user,MetaDataKeywords.databasePerson,choices,modalpanel);
		fullnamepanel.add(choose);
		
	}
	private void headers() {
		String user = Cookies.getCookie("user");
		if(user != null) {
			ownersHeader.setText("Profiles managed by User: " + user);
			peopleExisingHeader.setText("Profiles (not managed by " + user + ")");
		} else {
			ownersHeader.setText("Profiles managed by User: refresh to see profiles");
			peopleExisingHeader.setText("Definitions not managed by user");
		}		
	}
	
	@UiHandler("refresh")
	public void refreshClicked(ClickEvent event) {
		refresh();
	}
	
	public void refresh() {
		RetrieveOwnerPrivileges retrieve = new RetrieveOwnerPrivileges(this);
		retrieve.getPrivileges();
		existingPeople.clear();
		ownersPeople.clear();
		fullnamepanel.clear();
		choosePanel();
		headers();
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
						ownersPeople.add(item);
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

	
	@Override
	public void setInOwnerPrivilegesSuccess(List<String> owners) {
		this.owners = owners;
		refreshAccessibleUsers();		
	}

	@Override
	public void setInOwnerPrivilegesFailure(Throwable caught) {
		StandardWindowVisualization.errorWindowMessage("Get Privileges", caught.toString());
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
			DatabaseObject obj = item.getHierarchy().getObject();
			if(managed(obj)) {
				ownersPeople.add(item);
			} else {
				existingPeople.add(item);
				StandardDatasetIndividualInformation header = (StandardDatasetIndividualInformation) item.getHeader();
				header.setModifyAllowed(false);
			}
		}		
	}
	private boolean managed(DatabaseObject obj) {
		boolean ans = false;
		if(owners != null) {
			String owner = obj.getOwner();
			ans = owners.contains(owner);
		} else {
			String user = Cookies.getCookie("user");
			ans = obj.getOwner().compareTo(user) == 0;
		}
		return ans;
	}


}
