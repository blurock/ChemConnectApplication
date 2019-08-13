package info.esblurock.reaction.chemconnect.core.base.client.catalog.organization;

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
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SetOfObjectsCallbackInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.RetrieveOwnerPrivileges;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.RetrieveOwnerPrivilegesInterface;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.view.OrganizationDefinitionView;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public class OrganizationDefinition extends Composite 
	implements ObjectVisualizationInterface, SetOfObjectsCallbackInterface, 
	OrganizationDefinitionView,RetrieveOwnerPrivilegesInterface {

	private static OrganizationDefinitionUiBinder uiBinder = GWT.create(OrganizationDefinitionUiBinder.class);

	interface OrganizationDefinitionUiBinder extends UiBinder<Widget, OrganizationDefinition> {
	}


	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialPanel topPanel;
	@UiField
	MaterialLink orgExisingHeader;
	@UiField
	MaterialCollapsible existingOrganizations;
	@UiField
	MaterialLink ownersHeader;
	@UiField
	MaterialCollapsible ownersOrganization;
	@UiField
	MaterialLink refresh;
	@UiField
	MaterialPanel fullnamepanel;
	
	Presenter listener;
	ChooseFullNameFromCatagoryRow choose;
	String access;
	String defaultCatagory;
	String enterkeyS;
	String keynameS;
	ArrayList<String> choices;
	List<String> owners;

	public OrganizationDefinition() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		owners = null;
		headers();
		String orgType = MetaDataKeywords.organizationClassifications;
		choices = new ArrayList<String>();
		choices.add(orgType);
		choosePanel();
		RetrieveOwnerPrivileges retrieve = new RetrieveOwnerPrivileges(this);
		retrieve.getPrivileges();
		orgExisingHeader.setText("Existing Organization Definitions");
	}
	private void choosePanel() {
		String user = Cookies.getCookie("user");
		choose = new ChooseFullNameFromCatagoryRow(this,user,MetaDataKeywords.organization,choices,modalpanel);
		fullnamepanel.add(choose);
	}
	private void headers() {
		String user = Cookies.getCookie("user");
		if(user != null) {
			ownersHeader.setText("Organizations managed by User: " + user);
			orgExisingHeader.setText("Organizations (not managed by " + user + ")");
		} else {
			ownersHeader.setText("Organizations managed by User: refresh to see profiles");
			orgExisingHeader.setText("Organizations not managed by user");
		}		
	}
	@UiHandler("refresh")
	public void refreshClicked(ClickEvent event) {
		refresh();
	}
	
	public void refresh() {
		RetrieveOwnerPrivileges retrieve = new RetrieveOwnerPrivileges(this);
		retrieve.getPrivileges();
		existingOrganizations.clear();
		ownersOrganization.clear();
		fullnamepanel.clear();
		choosePanel();
		headers();
	}
	@Override
	public void createCatalogObject(DatabaseObject obj,DataCatalogID datid) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		String title = choose.getObjectName();
		String shortname = title;
		async.createOrganization(obj,shortname, title,datid,
				new AsyncCallback<DatabaseObjectHierarchy>() {
			@Override
			public void onFailure(Throwable arg0) {
				MaterialLoader.loading(false);
				StandardWindowVisualization.errorWindowMessage("Database Catagory" ,arg0.toString());
			}

			@Override
			public void onSuccess(DatabaseObjectHierarchy transfer) {
				MaterialLoader.loading(false);
				StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,transfer,modalpanel);		
				ownersOrganization.add(item);
			}
			
		});
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
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		async.getSetOfDatabaseObjectHierarchyForUser(MetaDataKeywords.organization,
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
				ownersOrganization.add(item);
			} else {
				existingOrganizations.add(item);
				StandardDatasetOrganizationHeader header = (StandardDatasetOrganizationHeader) item.getHeader();
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
	@Override
	public void setName(String titleName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@Override
	public void insertCatalogObject(DatabaseObjectHierarchy subs) {
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,subs,modalpanel);
		existingOrganizations.add(item);
	}


}
