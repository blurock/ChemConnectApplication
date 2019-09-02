package info.esblurock.reaction.chemconnect.core.base.client.catalog.consortium;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.consortium.ConsortiumMember;

public class ConsortiumMemberHeader extends Composite {

	private static ConsortiumMemberHeaderUiBinder uiBinder = GWT.create(ConsortiumMemberHeaderUiBinder.class);

	interface ConsortiumMemberHeaderUiBinder extends UiBinder<Widget, ConsortiumMemberHeader> {
	}
	
	@UiField
	MaterialTooltip consortiumNametooltip;
	@UiField
	MaterialLink consortiumname;
	@UiField
	MaterialTooltip membernametooltip;
	@UiField
	MaterialLink membername;
	@UiField
	MaterialLink delete;
	
	StandardDatasetObjectHierarchyItem item;
	ConsortiumMember consortiumMember;
	DatabaseObjectHierarchy hierarchy;

	
	public ConsortiumMemberHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		hierarchy = item.getHierarchy();
		consortiumMember = (ConsortiumMember) hierarchy.getObject();
		init();
	}

	private void init() {
		consortiumNametooltip.setText("Consortium Name");
		membernametooltip.setText("Consortium Member Name");
		consortiumname.setText(consortiumMember.getConsortiumName());
		membername.setText(consortiumMember.getConsortiumMemberName());
	}
	
	
}
