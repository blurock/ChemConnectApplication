package info.esblurock.reaction.chemconnect.core.base.client.catalog.consortium;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialTitle;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.consortium.ConsortiumMember;

public class ConsortiumMemberManageModal extends Composite {

	private static ConsortiumMemberManageModalUiBinder uiBinder = GWT.create(ConsortiumMemberManageModalUiBinder.class);

	interface ConsortiumMemberManageModalUiBinder extends UiBinder<Widget, ConsortiumMemberManageModal> {
	}

	@UiField
	MaterialDialog dialog;
	@UiField
	MaterialTitle title;
	
	DatabaseObjectHierarchy memberhier;
	ConsortiumMember member;
	
	public ConsortiumMemberManageModal(DatabaseObjectHierarchy memberhier) {
		initWidget(uiBinder.createAndBindUi(this));
		this.memberhier = memberhier;
		member = (ConsortiumMember) memberhier.getObject();
		title.setTitle("Consortium Member: " + member.getConsortiumMemberName());
	}

	@UiHandler("close")
	void onCloseClick(ClickEvent event) {
		dialog.close();
	}
	
	public void open() {
		dialog.open();
	}
}
