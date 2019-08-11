package info.esblurock.reaction.chemconnect.core.base.client.catalog.choose;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.tree.MaterialTreeItem;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.hierarchy.MaterialTreeItemWithPath;
import info.esblurock.reaction.chemconnect.core.base.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.base.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.base.client.util.TextUtilities;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;

public class ChooseFullNameFromCatagoryRow extends Composite
		implements ChooseCatagoryHierarchyInterface, ChooseFromConceptHeirarchy, SetLineContentInterface,
		ChooseSimpleNameInterface, SubCatagoryHierarchyCallbackInterface,
		RetrieveOwnerPrivilegesInterface {

	private static ChooseFullNameFromCatagoryRowUiBinder uiBinder = GWT
			.create(ChooseFullNameFromCatagoryRowUiBinder.class);

	interface ChooseFullNameFromCatagoryRowUiBinder extends UiBinder<Widget, ChooseFullNameFromCatagoryRow> {
	}

	public ChooseFullNameFromCatagoryRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink catalog;
	@UiField
	MaterialLink objecttype;
	@UiField
	MaterialLink objectname;
	@UiField
	MaterialTooltip catalogtypeid;
	@UiField
	MaterialTooltip objecttypeid;
	@UiField
	MaterialButton submit;
	@UiField
	MaterialButton accessButton;
	@UiField
	MaterialTooltip accesstitle;
	@UiField
	MaterialDropDown dropdown;

	String objecttypeFull;
	String user;
	String objectS;
	boolean catalogSelected;
	boolean typeSelected;
	boolean nameSelected;
	boolean accessSelected;
	MaterialPanel modalpanel;
	ArrayList<String> choices;
	String access;
	InputLineModal line;
	String enterkeyS;
	String keynameS;
	String username;
	ArrayList<String> chosenPath;
	List<String> owners;

	ObjectVisualizationInterface top;

	/**
	 * @param object      The name of the type of object
	 * @param choices     The root ontology choices describing the object type
	 * @param modalpanel: The modal panel for the inquires
	 */
	public ChooseFullNameFromCatagoryRow(ObjectVisualizationInterface top, String user, String objectS,
			ArrayList<String> choices, MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.modalpanel = modalpanel;
		this.choices = choices;
		this.objectS = objectS;
		this.user = user;
		this.top = top;
		chosenPath = null;
		init();
	}

	void init() {
		username = Cookies.getCookie("account_name");
		catalog.setText("Set catalog");
		objecttype.setText("Set objecttype");
		objectname.setText("Set name");
		catalogSelected = false;
		typeSelected = false;
		nameSelected = false;
		accessSelected = false;
		submit.setText("submit");
		keynameS = "ShortDeviceName";
		enterkeyS = "Enter Name of Device: ";
		line = new InputLineModal(enterkeyS, keynameS, this);
		setupAccessDropDown();
	}

	void setupAccessDropDown() {
		accesstitle.setText("Visibility of object");
		MaterialLink publiclink = new MaterialLink(UserAccountKeys.publicAccess);
		MaterialLink userlink = new MaterialLink(username);
		
		dropdown.add(publiclink);
		dropdown.add(userlink);
		access = "Choose Access Level";
		accessButton.setText(access);
		accessSelected = false;
		getAccessCreationList();
	}

	public void getAccessCreationList() {
		RetrieveOwnerPrivileges retrieve = new RetrieveOwnerPrivileges(this);
		retrieve.getPrivileges();
	}

	private void transferToOwners(List<String> result) {
		owners = new ArrayList<String>();
		for(String name : result) {
			if(name.compareTo("Public") != 0) {
				owners.add(name);
			}
		}
	}

	
	@UiHandler("dropdown")
	void onDropdown(SelectionEvent<Widget> callback) {
		MaterialLink chosen = (MaterialLink) callback.getSelectedItem();
		access = chosen.getText();
		accessButton.setText(access);
		accessSelected = true;
	}

	/*
	 * Open up a modal panel with the hierarchy catalog items The catalog items come
	 * from the current user
	 */
	@UiHandler("catalog")
	public void chooseConcept(ClickEvent event) {
		setUpCatagoryChoices();
	}

	private void setUpCatagoryChoices() {
		ChooseCatalogHiearchyModal hierarchymodal = new ChooseCatalogHiearchyModal(username, this);
		modalpanel.add(hierarchymodal);
		hierarchymodal.open();
	}

	@Override
	public void catagoryChosen(String id, MaterialTreeItem item) {
		MaterialTreeItemWithPath withpath = (MaterialTreeItemWithPath) item;
		ArrayList<String> lst = withpath.getPath();
		lst.add(withpath.getIdentifier());
		chosenPath = lst;
		catalogtypeid.setText(id);
		catalog.setText(item.getText());
		catalogSelected = true;
	}

	/*
	 * The 'choices' are the set of ontology items from which to build the class For
	 * example: for devices the choices are dataset:DataTypeDevice
	 * dataset:DataTypeSubSystem dataset:DataTypeComponent
	 */
	@UiHandler("objecttype")
	public void chooseConceptHieararchy(ClickEvent event) {
		setUpConceptChoices();
	}

	private void setUpConceptChoices() {
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices, this);
		modalpanel.add(choosedevice);
		choosedevice.open();
	}

	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		objecttypeFull = concept;
		objecttype.setText(TextUtilities.removeNamespace(concept));
		objecttypeid.setText(concept);
		typeSelected = true;

		if (objectS == null) {
			UserImageServiceAsync async = UserImageService.Util.getInstance();
			SetObjectTypeCallback callback = new SetObjectTypeCallback(this);
			async.getStructureFromFileType(concept, callback);
		}
	}

	public void setObjectType(String objectType) {
		this.objectS = objectType;
	}

	/*
	 * 
	 */
	@UiHandler("objectname")
	public void objectNameKey(ClickEvent event) {
		String sourceID = "";
		String basecatalog = catalogtypeid.getText();
		String catalogname = objecttypeFull;
		DatabaseObject obj = new DatabaseObject("", access, username, sourceID);
		ChooseSimpleNameModal simplename = new ChooseSimpleNameModal(this, obj, basecatalog, catalogname);
		modalpanel.add(simplename);
		simplename.openModal();
	}

	@Override
	public void setLineContent(String line) {
		objectname.setText(line);
		nameSelected = true;
	}

	public String getCatagory() {
		return catalog.getText();
	}

	public String getObjectName() {
		return objectname.getText();
	}

	public String getObjectType() {
		return objecttypeid.getText();
	}

	@UiHandler("submit")
	public void onSubmit(ClickEvent event) {
		DataCatalogID name = retrieveCatalogName();
		DatabaseObject obj = new DatabaseObject(name.getFullName(), accessButton.getText(), username, "");
		obj.nullKey();
		top.createCatalogObject(obj, name);
	}

	public DataCatalogID retrieveCatalogName() {
		DataCatalogID name = null;
		if (!catalogSelected) {
			MaterialToast.fireToast("Select Catagory first");
		} else if (!typeSelected) {
			MaterialToast.fireToast("Select Object Type");
		} else if (!nameSelected) {
			MaterialToast.fireToast("Type in Object Name");
		} else if (!accessSelected) {
			MaterialToast.fireToast("Choose access");
		} else {
			String sourceID = "";
			String basecatalog = catalogtypeid.getText();
			String catalogname = objecttypeFull;
			String simple = objectname.getText().trim();
			DatabaseObject obj = new DatabaseObject("", access, username, sourceID);
			ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj, "");
			name = new DataCatalogID(structure, basecatalog, catalogname, simple, chosenPath);
			String id = name.getFullName();
			name.setIdentifier(id);
			name.setParentLink(id);
		}

		return name;
	}

	@Override
	public void newNameChosen(String newsimplename) {
		objectname.setText(newsimplename);
		nameSelected = true;
	}

	@Override
	public void objectChosen(String id) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		async.getCatalogObject(id, objectS, new AsyncCallback<DatabaseObjectHierarchy>() {

			@Override
			public void onFailure(Throwable caught) {
				StandardWindowVisualization.errorWindowMessage("Catalog Object", caught.toString());
			}

			@Override
			public void onSuccess(DatabaseObjectHierarchy subs) {
				MaterialLoader.loading(false);
				setInHierarchy(subs);
			}

		});
	}

	@Override
	public void setInHierarchy(DatabaseObjectHierarchy subs) {
		top.insertCatalogObject(subs);
	}

	public List<String> getOwners() {
		return owners;
	}

	@Override
	public void setInOwnerPrivilegesFailure(Throwable caught) {
		submit.setEnabled(false);
		accessButton.setEnabled(false);
		StandardWindowVisualization.errorWindowMessage("Access Creation List", caught.toString());		
	}
	
	@Override
	public void setInOwnerPrivilegesSuccess(List<String> owners) {
		transferToOwners(owners);
		if (owners.size() > 0) {
			submit.setEnabled(true);
			accessButton.setEnabled(true);
			dropdown.clear();
			for (String name : owners) {
				MaterialLink userlink = new MaterialLink(name);
				dropdown.add(userlink);
			}
			access = owners.get(0);
		} else {
			submit.setEnabled(false);
			accessButton.setEnabled(false);
		}
	}
}
