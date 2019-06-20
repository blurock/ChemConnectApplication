package info.esblurock.reaction.chemconnect.core.base.client.catalog.person;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.contact.QueryNameOfPersonInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.contact.QueryNameOfPersonModal;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;

public class StandardDatasetNameOfPersonHeader extends Composite implements QueryNameOfPersonInterface {

	private static StandardDatasetNameOfPersonHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetNameOfPersonHeaderUiBinder.class);

	interface StandardDatasetNameOfPersonHeaderUiBinder extends UiBinder<Widget, StandardDatasetNameOfPersonHeader> {
	}


	@UiField
	MaterialTooltip nametooltip;
	@UiField
	MaterialLink namehead;

	StandardDatasetObjectHierarchyItem item;
	NameOfPerson person;
	
	public StandardDatasetNameOfPersonHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		this.person = (NameOfPerson) item.getObject();
		namehead.setText(person.nameAsString());
		init();
	}
	
	private void init() {
		
	}

	@UiHandler("namehead")
	public void onHeadClick(ClickEvent event) {
		QueryNameOfPersonModal modal = new QueryNameOfPersonModal(person, this);
		item.getModalpanel().clear();
		item.getModalpanel().add(modal);
		modal.open();
		
	}

	@Override
	public void insertNameOfPerson(NameOfPerson person) {
		this.person = person;
	}
	
}
