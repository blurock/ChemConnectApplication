package info.esblurock.reaction.chemconnect.core.base.client.catalog.consortium;

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
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SetOfObjectsCallbackInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.RetrieveOwnerPrivileges;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.RetrieveOwnerPrivilegesInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.organization.StandardDatasetOrganizationHeader;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.view.ConsortiumManagementView;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;

public class ConsortiumManagement extends Composite 
	implements ConsortiumManagementView, RetrieveOwnerPrivilegesInterface, 
	SetOfObjectsCallbackInterface, ObjectVisualizationInterface {

	private static ConsortiumManagementUiBinder uiBinder = GWT.create(ConsortiumManagementUiBinder.class);

	interface ConsortiumManagementUiBinder extends UiBinder<Widget, ConsortiumManagement> {
	}

	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialPanel topPanel;
	@UiField
	MaterialLink consortiumExisingHeader;
	@UiField
	MaterialLink refresh;
	@UiField
	MaterialCollapsible existingConsortium;
	@UiField
	MaterialLink ownersHeader;
	@UiField
	MaterialPanel fullnamepanel;
	@UiField
	MaterialCollapsible ownersConsortium;
	
	Presenter listener;
	ChooseFullNameFromCatagoryRow choose;
	ArrayList<String> choices;
	List<String> owners;
	
	public ConsortiumManagement() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		owners = null;
		headers();
		choices = new ArrayList<String>();
		choices.add(MetaDataKeywords.consortiumTypeChoice);
		choosePanel();
		RetrieveOwnerPrivileges retrieve = new RetrieveOwnerPrivileges(this);
		retrieve.getPrivileges();
	}

	private void headers() {
		String user = Cookies.getCookie("user");
		if(user != null) {
			ownersHeader.setText("Consortia managed by User: " + user);
			consortiumExisingHeader.setText("Consortia (not managed by " + user + ")");
		} else {
			ownersHeader.setText("Organizations managed by User: refresh to see profiles");
			consortiumExisingHeader.setText("Consortia not managed by user");
		}		
	}
	
	@UiHandler("refresh")
	public void refreshClicked(ClickEvent event) {
		refresh();
	}
	
	public void refresh() {
		RetrieveOwnerPrivileges retrieve = new RetrieveOwnerPrivileges(this);
		retrieve.getPrivileges();
		existingConsortium.clear();
		ownersConsortium.clear();
		fullnamepanel.clear();
		choosePanel();
		headers();
	}
	private void choosePanel() {
		String user = Cookies.getCookie("user");
		choose = new ChooseFullNameFromCatagoryRow(this,user,MetaDataKeywords.consortium,choices,modalpanel);
		fullnamepanel.add(choose);
	}

	@Override
	public void setInOwnerPrivilegesSuccess(List<String> owners) {
		this.owners = owners;
		refreshAccessibleUsers();		
	}
	private void refreshAccessibleUsers() {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		async.getSetOfDatabaseObjectHierarchyForUser(MetaDataKeywords.consortium,
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
	public void setInOwnerPrivilegesFailure(Throwable caught) {
		StandardWindowVisualization.errorWindowMessage("Get Privileges", caught.toString());
	}
	
	@Override
	public void createCatalogObject(DatabaseObject obj, DataCatalogID catid) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		async.createConsortiumCatalogObject(catid, catid.getSimpleCatalogName(), 
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
				ownersConsortium.add(item);
			}
			
		});
	}

	@Override
	public void insertCatalogObject(DatabaseObjectHierarchy subs) {
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,subs,modalpanel);
		existingConsortium.add(item);
	}


	@Override
	public void setInOjbects(ArrayList<DatabaseObjectHierarchy> objects) {
		for(DatabaseObjectHierarchy object : objects) {
			StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,object,modalpanel);
			DatabaseObject obj = item.getHierarchy().getObject();
			if(managed(obj)) {
				ownersConsortium.add(item);
			} else {
				existingConsortium.add(item);
				StandardDatasetOrganizationHeader header = (StandardDatasetOrganizationHeader) item.getHeader();
				header.setModifyAllowed(false);
			}
		}		
	}
	private boolean managed(DatabaseObject obj) {
		String user = Cookies.getCookie("user");
		boolean ans = obj.getOwner().compareTo(user) == 0;
		return ans;
	}

	
	@Override
	public void setName(String helloName) {
		
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}



}
