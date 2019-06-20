package info.esblurock.reaction.chemconnect.core.base.client.catalog.organization;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
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
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.view.OrganizationDefinitionView;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public class OrganizationDefinition extends Composite implements ObjectVisualizationInterface, SetOfObjectsCallbackInterface, OrganizationDefinitionView {

	private static OrganizationDefinitionUiBinder uiBinder = GWT.create(OrganizationDefinitionUiBinder.class);

	interface OrganizationDefinitionUiBinder extends UiBinder<Widget, OrganizationDefinition> {
	}


	String defaultCatagory;
	String enterkeyS;
	String keynameS;
	@UiField
	MaterialCollapsible contentcollapsible;
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialPanel topPanel;
	@UiField
	MaterialLink organizationExisingHeader;
	@UiField
	MaterialCollapsible existingOrgs;
	@UiField
	MaterialLink organizationCreatedHeader;
	@UiField
	MaterialCollapsible createdOrgs;
	
	Presenter listener;
	ChooseFullNameFromCatagoryRow choose;
	String access;

	public OrganizationDefinition() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		String orgType = MetaDataKeywords.organizationClassifications;
		
		organizationExisingHeader.setText("Existing Organization Definitions");
		organizationCreatedHeader.setText("New Organizations");
		
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(orgType);
		String user = Cookies.getCookie("user");
		String organization = MetaDataKeywords.organization;
		choose = new ChooseFullNameFromCatagoryRow(this,user,organization,choices,modalpanel);
		topPanel.add(choose);
		
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		async.getSetOfDatabaseObjectHierarchyForUser(organization,
				new AsyncCallback<ArrayList<DatabaseObjectHierarchy>>() {
			@Override
			public void onFailure(Throwable ex) {
				MaterialLoader.loading(false);
				StandardWindowVisualization.errorWindowMessage("Set of catalog elements",ex.toString());
			}

			@Override
			public void onSuccess(ArrayList<DatabaseObjectHierarchy> objects) {
				MaterialLoader.loading(false);
				setInOjbects(objects);
			}			
		});
		
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
				createdOrgs.add(item);
			}
			
		});
	}

	@Override
	public void setInOjbects(ArrayList<DatabaseObjectHierarchy> objects) {
		for(DatabaseObjectHierarchy object : objects) {
			StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,object,modalpanel);			
			existingOrgs.add(item);
		}
		
	}

	@Override
	public void insertCatalogObject(DatabaseObjectHierarchy subs) {
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,subs,modalpanel);
		contentcollapsible.add(item);
	}
	@Override
	public void setName(String titleName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
