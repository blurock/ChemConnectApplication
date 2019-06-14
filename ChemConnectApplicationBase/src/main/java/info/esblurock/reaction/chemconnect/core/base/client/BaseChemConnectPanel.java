package info.esblurock.reaction.chemconnect.core.base.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;

public class BaseChemConnectPanel extends Composite {

	private static BaseChemConnectPanelUiBinder uiBinder = GWT.create(BaseChemConnectPanelUiBinder.class);

	interface BaseChemConnectPanelUiBinder extends UiBinder<Widget, BaseChemConnectPanel> {
	}
	@UiField
	MaterialLink loginchoice;
	@UiField
	MaterialLink catalog;
	@UiField
	MaterialLink upload;
	@UiField
	MaterialLink people;
	@UiField
	MaterialLink organizations;
	@UiField
	MaterialLink logout;
	@UiField
	MaterialTooltip logouttooltip;
	@UiField
	MaterialTooltip logintooltip;

	public BaseChemConnectPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		catalog.setText("Manage Data Catalog Structure");
		upload.setText("file staging and interpretation");
		people.setText("researchers in database");
		organizations.setText("organizations in database");
		logout.setText("Logout");
		logouttooltip.setText("Log out current user (to Guest");
		logintooltip.setText("Choose method of login");
		
	}
	void setLoginVisibility(boolean loginvisible) {
		logout.setVisible(!loginvisible);
		loginchoice.setVisible(loginvisible);
	}
	

}
