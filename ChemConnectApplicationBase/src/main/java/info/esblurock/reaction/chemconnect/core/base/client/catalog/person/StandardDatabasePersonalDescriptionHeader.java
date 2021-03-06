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
import info.esblurock.reaction.chemconnect.core.base.client.modal.InputLineModal;
import info.esblurock.reaction.chemconnect.core.base.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public class StandardDatabasePersonalDescriptionHeader extends Composite implements SetLineContentInterface {

	private static StandardDatabasePersonalDescriptionHeaderUiBinder uiBinder = GWT
			.create(StandardDatabasePersonalDescriptionHeaderUiBinder.class);

	interface StandardDatabasePersonalDescriptionHeaderUiBinder
			extends UiBinder<Widget, StandardDatabasePersonalDescriptionHeader> {
	}

	public StandardDatabasePersonalDescriptionHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip classtooltip;
	@UiField
	MaterialLink classhead;

	StandardDatasetObjectHierarchyItem item;
	PersonalDescription person;
	NameOfPerson personname;
	
	public StandardDatabasePersonalDescriptionHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		DatabaseObjectHierarchy phierarch = item.getHierarchy();
		person = (PersonalDescription) item.getObject();
		String nameID = person.getNameOfPersonIdentifier();
		DatabaseObjectHierarchy namehier = phierarch.getSubObject(nameID);
		personname = (NameOfPerson) namehier.getObject();
		classhead.setText(person.getUserClassification());
		classtooltip.setText("Classification");
	}

	@UiHandler("classhead")
	void onClickClass(ClickEvent e) {
		InputLineModal modal = new InputLineModal("Input the user classification (Student, Postdoc, Professor, Researcher....",
				"researcher",this);
		item.getModalpanel().clear();
		item.getModalpanel().add(modal);
		modal.openModal();
	}

	@Override
	public void setLineContent(String line) {
		classhead.setText(line);
	}

	public boolean updateData() {
		person.setUserClassification(classhead.getText());
		return false;
	}
}
