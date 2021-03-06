package info.esblurock.reaction.chemconnect.core.base.client.catalog.organization;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SaveDatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.modal.DeleteCatalogObject;
import info.esblurock.reaction.chemconnect.core.base.client.modal.SetLineContentInterface;
import info.esblurock.reaction.chemconnect.core.base.contact.Organization;
import info.esblurock.reaction.chemconnect.core.base.contact.OrganizationDescription;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;

public class StandardDatasetOrganizationHeader extends Composite  implements SetLineContentInterface {

	private static StandardDatasetOrganizationHeaderUiBinder uiBinder = GWT
			.create(StandardDatasetOrganizationHeaderUiBinder.class);

	interface StandardDatasetOrganizationHeaderUiBinder extends UiBinder<Widget, StandardDatasetOrganizationHeader> {
	}

	public StandardDatasetOrganizationHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTooltip devicetooltip;
	@UiField
	MaterialLink orghead;
	@UiField
	MaterialLink save;
	@UiField
	MaterialLink delete;
	
	String descrclassname;
	StandardDatasetObjectHierarchyItem item;
	Organization descr;
	OrganizationDescription orgdescr;
	
	public StandardDatasetOrganizationHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		descrclassname = StandardDatasetOrganizationDescriptionHeader.class.getCanonicalName();
		descr = (Organization) item.getObject();
		String orgdescrID = descr.getOrganizationDescriptionID();
		DatabaseObjectHierarchy hierarchy = item.getHierarchy();
		DatabaseObjectHierarchy descrhier = hierarchy.getSubObject(orgdescrID);
		orgdescr = (OrganizationDescription) descrhier.getObject();
		orghead.setText(orgdescr.getOrganizationName());
		devicetooltip.setText(descr.getIdentifier());
	}
	@UiHandler("save")
	public void onClickSave(ClickEvent event) {
		SaveDatasetCatalogHierarchy savemodal = new SaveDatasetCatalogHierarchy(item);
		item.getModalpanel().clear();
		item.getModalpanel().add(savemodal);
		savemodal.openModal();
	}
	@UiHandler("delete")
	public void onClickDelete(ClickEvent event) {
		DeleteCatalogObject delete = new DeleteCatalogObject(descr.getIdentifier(), 
				StandardDataKeywords.organization, item.getModalpanel(), this);
	}

	public void updateData() {
		
	}
	@Override
	public void setLineContent(String line) {
		ArrayList<StandardDatasetObjectHierarchyItem> subitems = item.getSubitems();
		for(StandardDatasetObjectHierarchyItem sub : subitems) {
			Composite header = sub.getHeader();
			String name = header.getClass().getCanonicalName();
			if(name.compareTo(descrclassname) == 0) {
				StandardDatasetOrganizationDescriptionHeader subheader = (StandardDatasetOrganizationDescriptionHeader) header;
				subheader.setOrganizationName(line);
			}
		}
	}
	public void setModifyAllowed(boolean allowed) {
		save.setVisible(allowed);
		delete.setVisible(allowed);
	}

}
