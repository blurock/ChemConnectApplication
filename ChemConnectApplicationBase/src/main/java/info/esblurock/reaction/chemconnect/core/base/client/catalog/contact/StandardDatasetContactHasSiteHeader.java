package info.esblurock.reaction.chemconnect.core.base.client.catalog.contact;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTooltip;
import gwt.material.design.jscore.client.api.media.URL;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.base.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.base.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.base.client.util.TextUtilities;
import info.esblurock.reaction.chemconnect.core.base.dataset.ContactHasSite;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;

public class StandardDatasetContactHasSiteHeader extends Composite 
	implements SetLineContentInterface, ChooseFromConceptHeirarchy {

	private static StandardDatasetContactHasSiteHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetContactHasSiteHeaderUiBinder.class);

	interface StandardDatasetContactHasSiteHeaderUiBinder
			extends UiBinder<Widget, StandardDatasetContactHasSiteHeader> {
	}

	public StandardDatasetContactHasSiteHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip httptooltip;
	@UiField
	MaterialLink httpaddress;
	@UiField
	MaterialTooltip linktypetooltip;
	@UiField
	MaterialLink linktype;

	StandardDatasetObjectHierarchyItem item;
	ContactHasSite  site;
	String httpType;
	InputLineModal line;
	
	public StandardDatasetContactHasSiteHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		this.item = item;
		site = (ContactHasSite ) item.getObject();
		httpType = site.getHttpAddressType();
		httpaddress.setText(site.getHttpAddress());
		linktype.setText(TextUtilities.removeNamespace(site.getHttpAddressType()));
	}
	private void init() {
		httptooltip.setText("Link Address");
		linktypetooltip.setText("Type of link");
	}
	
	@UiHandler("httpaddress")
	public void onHttpAddressClick(ClickEvent event) {
		line = new InputLineModal("HTTP address", "https://", this);
		item.getModalpanel().add(line);
		line.openModal();		
		MaterialPanel modalpanel = item.getModalpanel();
		modalpanel.clear();
		modalpanel.add(line);
	}
	
	@Override
	public void setLineContent(String line) {
		httpaddress.setText(line);
		if(!TextUtilities.isValidUrl(line, false)) {
			Window.alert("URL syntax could not be validated: '" + line + "'");
		}
	}
	
	@UiHandler("linktype")
	public void onLinkTypeClick(ClickEvent event) {
		ArrayList<String> choices = new ArrayList<String>();
		  choices.add(MetaDataKeywords.conceptLinkDataStructures);
		  ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
		  MaterialPanel modalpanel = item.getModalpanel();
		  modalpanel.clear();
		  modalpanel.add(choosedevice);
		  choosedevice.open();    
	}
	
	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		  Window.alert("Choosen:  " + concept);
		linktype.setText(concept);
		site.setHttpAddressType(TextUtilities.removeNamespace(concept));
	}
	
}
