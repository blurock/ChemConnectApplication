package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.utilities.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.ClientEnumerateUtilities;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.object.UploadedTextObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;

public class UploadedElementCollapsible extends Composite implements ObjectVisualizationInterface,
		ChooseFromConceptHeirarchy, InsertBlobContentInterface {

	private static UploadedElementCollapsibleUiBinder uiBinder = GWT.create(UploadedElementCollapsibleUiBinder.class);

	interface UploadedElementCollapsibleUiBinder extends UiBinder<Widget, UploadedElementCollapsible> {
	}

	public static String imageClassS = "image";
	public static String textClassS = "text";
	@UiField
	MaterialLink type;
	@UiField
	MaterialLink filetype;
	@UiField
	MaterialLink path;
	@UiField
	MaterialLink delete;
	@UiField
	MaterialLink url;
	@UiField
	MaterialTooltip identifiertooltip;
	@UiField
	MaterialTooltip urltooltip;
	@UiField
	MaterialTooltip typetooltip;
	@UiField
	MaterialPanel catidpanel;
	@UiField
	MaterialCollapsible objectpanel;

	GCSBlobFileInformation info;
	Map<String, ClassificationInformation> interpretmap;
	ClassificationInformation interpretinfo;
	String identifierRoot;
	String identifier;
	String typeClass;
	String typeInstance;
	String linkUrl;
	GCSBlobContent content;
	String visualType;
	MaterialImage image;
	UploadedTextObject textobject;
	MaterialPanel modalpanel;

	DataCatalogID catid;
	ChooseFullNameFromCatagoryRow choose;

	public UploadedElementCollapsible(GCSBlobContent content, MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.modalpanel = modalpanel;
		this.content = content;
		init();
		fill(content);
		String object = null;
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(MetaDataKeywords.dataFileInformationStructure);
		String user = Cookies.getCookie("user");
		choose = new ChooseFullNameFromCatagoryRow(this, user, object, choices, modalpanel);
		catidpanel.add(choose);
	}

	void init() {
		typeClass = null;
		typeInstance = null;
		identifier = null;
		// textDescription.setLabel("Description");
		urltooltip.setText("Download File");
	}

	void fill(GCSBlobContent content) {
		this.content = content;
		info = content.getInfo();
		linkUrl = content.getUrl();
		if (linkUrl != null) {
			urltooltip.setText(linkUrl);
		} else {
			UserImageServiceAsync async = UserImageService.Util.getInstance();
			GCSContentCallback callback = new GCSContentCallback(this);
			async.getBlobContent(info, callback);
			urltooltip.setText("");
		}

		if (info != null) {
			parseType(info.getFiletype());
			type.setText(info.getFiletype());
			typetooltip.setText(info.getFiletype());
			identifiertooltip.setText(info.getGSFilename());
			path.setText(info.getFilename());
		}

	}

	void parseType(String type) {
		int pos = type.indexOf("/");
		if (pos >= 0) {
			typeClass = type.substring(0, pos);
			typeInstance = type.substring(pos + 1);
		}
		visualType = null;
		if (typeClass.startsWith(type)) {
			visualType = "Image";
		} else {

		}
	}

	@UiHandler("type")
	void onClickType(ClickEvent e) {
		askForType();
	}

	void askForType() {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		FindFileTypeCallback callback = new FindFileTypeCallback(this);
		async.getFileInterpretionChoices(info, callback);
	}

	@UiHandler("delete")
	void onClickDelete(ClickEvent e) {
		Window.alert("Delete");
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		DeleteObjectCallback callback = new DeleteObjectCallback(path.getText());
		async.deleteObject(info.getSourceID(), MetaDataKeywords.gcsBlobFileInformation, callback);
		this.removeFromParent();
	}

	@UiHandler("url")
	void onClickUrl(ClickEvent e) {
		Window.open(linkUrl, "Download", "");
	}

	public String setIdentifier(String identifierRoot) {
		this.identifierRoot = identifierRoot;
		this.identifier = this.identifierRoot;
		if (info != null) {
			this.identifier = this.identifierRoot + "-" + info.getFilename();
		}
		return this.identifier;
	}

	public String getPath() {
		return path.getText();
	}

	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		visualType = concept;
		interpretinfo = interpretmap.get(concept);
		filetype.setText(concept);
	}

	public void askForInterpretationType(HierarchyNode hierarchy) {
		interpretmap = new HashMap<String, ClassificationInformation>();
		findClassifications(hierarchy, interpretmap);
		ChooseFromConceptHierarchies choose = new ChooseFromConceptHierarchies(this);
		choose.setupTree(hierarchy);
		modalpanel.add(choose);
		choose.open();
	}

	private void findClassifications(HierarchyNode hierarchy, Map<String, ClassificationInformation> interpretmap) {
		String name = hierarchy.getLabel();
		String shortname = name;
		if (name.startsWith("dataset:")) {
			shortname = name.substring(8);
		}
		if (shortname.startsWith("FileType")) {
			shortname = shortname.substring(8);
		}
		hierarchy.setLabel(shortname);
		if (hierarchy.getInfo() != null) {
			interpretmap.put(shortname, hierarchy.getInfo());
		}
		if (hierarchy.getSubNodes() != null) {
			for (HierarchyNode node : hierarchy.getSubNodes()) {
				findClassifications(node, interpretmap);
			}
		}
	}


	@Override
	public void createCatalogObject(DatabaseObject obj, DataCatalogID catid) {
		if (visualType == null) {
			MaterialToast.fireToast("Specify exact file type for interpretation and press Submit again");
			askForType();
		}
		String catalogname = ChemConnectCompoundDataStructure.removeNamespace(catid.getDataCatalog());
		InterpretUploadedFileInterface interpret = 
				ClientEnumerateUtilities.interpretUploadedFile.valueOf(catalogname);
		interpret.interpretStructure(obj, catid, visualType, info, this);
	}

	@Override
	public void insertCatalogObject(DatabaseObjectHierarchy subs) {
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null, subs, modalpanel);
		objectpanel.add(item);
	}

	public MaterialCollapsible getObjectPanel() {
		return objectpanel;
	}

	public MaterialPanel getModalPanel() {
		return modalpanel;
	}

	@Override
	public void insertBlobInformation(GCSBlobContent insert) {
		
	}


}
