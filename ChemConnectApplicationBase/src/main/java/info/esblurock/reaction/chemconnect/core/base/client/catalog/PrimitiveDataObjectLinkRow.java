package info.esblurock.reaction.chemconnect.core.base.client.catalog;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.base.client.util.TextUtilities;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts.ChooseFromConceptHeirarchy;

public class PrimitiveDataObjectLinkRow extends Composite implements ChooseFromConceptHeirarchy, HierarchyNodeCallbackInterface {

	private static PrimitiveDataObjectLinkRowUiBinder uiBinder = GWT.create(PrimitiveDataObjectLinkRowUiBinder.class);

	interface PrimitiveDataObjectLinkRowUiBinder extends UiBinder<Widget, PrimitiveDataObjectLinkRow> {
	}

	@UiField
	MaterialTooltip concepttip;
	@UiField
	MaterialLink concept;
	@UiField
	MaterialTooltip linktip;
	@UiField
	MaterialLink link;

	String identifier;
	String typeWithNamespace;
	boolean typeSet;
	DataObjectLink linkobject;
	String chosenLinkConcept;
	String chosenObjectID;
	StandardDatasetObjectHierarchyItem item;
	boolean chooseConcept;
	
	public PrimitiveDataObjectLinkRow(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.item = item;
		linkobject = (DataObjectLink) item.getObject();
		setInData(linkobject);
	}

	void init() {
		concepttip.setText("Link concept");
		concept.setText("no concept");
		linktip.setText("Link to another structure");
		link.setText("no link");
		link.setEnabled(false);
	}

	public void setInData(DataObjectLink linkinfo) {
		chosenLinkConcept = linkinfo.getLinkConcept();
		chosenObjectID = linkinfo.getDataStructure();
		concept.setText(TextUtilities.removeNamespace(linkinfo.getLinkConcept()));
		link.setText(linkinfo.getDataStructure());		
	}
	
	public boolean updateObject() {
		linkobject.setLinkConcept(chosenLinkConcept);
		linkobject.setDataStructure(chosenObjectID);
		return false;
	}
	
	@UiHandler("concept")
	void onClickConcept(ClickEvent e) {
		chooseConcept = true;
		ArrayList<String> choices = new ArrayList<String>();
		String topconcept = MetaDataKeywords.dataTypeDataStructure;
		choices.add(topconcept);
		ChooseFromConceptHierarchies chooseconcept = new ChooseFromConceptHierarchies(choices,this);
		item.getModalpanel().clear();
		item.getModalpanel().add(chooseconcept);
		chooseconcept.open();
	}
	@UiHandler("link")
	void onClickLink(ClickEvent e) {
		chooseConcept = false;
		findLinkObject();
	}
	@Override
	public void conceptChosen(String topconcept, String chosen, ArrayList<String> path) {
		if(chooseConcept) {
			chosenLinkConcept = chosen;
			concept.setText(TextUtilities.removeNamespace(chosenLinkConcept));
			link.setEnabled(true);
			findLinkObject();
		} else {
			chosenObjectID = chosen;
			int pos = chosen.lastIndexOf("-");
			link.setText(TextUtilities.extractSimpleNameFromCatalog(chosen));
			linktip.setText(chosen);
		}
	}
	private void findLinkObject() {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		HierarchyNodeCallback callback = new HierarchyNodeCallback(this);
		async.getIDHierarchyFromDataCatalogID(null,chosenLinkConcept,callback);
	}

	@Override
	public void insertTree(HierarchyNode topnode) {
		chooseConcept = false;
		ChooseFromConceptHierarchies chooseconcept = new ChooseFromConceptHierarchies(this);
		chooseconcept.setupTree(topnode);
		item.getModalpanel().clear();
		item.getModalpanel().add(chooseconcept);
		chooseconcept.open();
	}

}
