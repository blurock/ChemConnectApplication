package info.esblurock.reaction.chemconnect.core.base.client.catalog.consortium;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.consortium.ConsortiumMember;

public class ConsortiaChip extends Composite {

	private static ConsortiaChipUiBinder uiBinder = GWT.create(ConsortiaChipUiBinder.class);

	interface ConsortiaChipUiBinder extends UiBinder<Widget, ConsortiaChip> {
	}
	
	@UiField
	MaterialChip consortium;
	
	DatabaseObjectHierarchy memberhier;
	ConsortiumMember member;
	MaterialPanel modalpanel;
	MaterialPanel consortiapanel;

	public ConsortiaChip(MaterialPanel modalpanel, MaterialPanel consortiapanel, DatabaseObjectHierarchy memberhier) {
		initWidget(uiBinder.createAndBindUi(this));
		this.memberhier = memberhier;
		this.consortiapanel = consortiapanel;
		this.member = (ConsortiumMember) memberhier.getObject();
		this.modalpanel = modalpanel;
		consortium.setText(member.getConsortiumMemberName());
	}
	
	@UiHandler("consortium")
	 void onClickChip(ClickEvent e) {
		ConsortiumMemberManageModal modal = new ConsortiumMemberManageModal(memberhier);
		modalpanel.clear();
		modalpanel.add(modal);
		modal.open();
	 }

}
