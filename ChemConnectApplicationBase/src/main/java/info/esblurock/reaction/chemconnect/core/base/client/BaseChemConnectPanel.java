package info.esblurock.reaction.chemconnect.core.base.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.client.activity.ClientFactoryBase;
import info.esblurock.reaction.chemconnect.core.base.client.place.AboutSummaryPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.FirstSiteLandingPagePlace;

public class BaseChemConnectPanel extends Composite {
	
	ClientFactoryBase clientFactory;


	private static BaseChemConnectPanelUiBinder uiBinder = GWT.create(BaseChemConnectPanelUiBinder.class);

	interface BaseChemConnectPanelUiBinder extends UiBinder<Widget, BaseChemConnectPanel> {
	}
	@UiField
	MaterialLabel title;
	@UiField
	MaterialLabel subtitle;
	@UiField
	SimplePanel contentPanel;
	@UiField
	SimplePanel footerpanel;
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

	public BaseChemConnectPanel(ClientFactoryBase clientFactory) {
		initWidget(uiBinder.createAndBindUi(this));
		this.clientFactory = clientFactory;
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
	
	public SimplePanel getContentPanel() {
		return contentPanel;
	}
	
	@UiHandler("home")
	public void onHome(ClickEvent event) {
		subtitle.setText("ChemConnect: The Intelligent Repository");
		goTo(new FirstSiteLandingPagePlace("Home"));
	}

	@UiHandler("about")
	public void onAboutClick(ClickEvent event) {
		setSubTitle("About ChemConnect");
		goTo(new AboutSummaryPlace("About ChemConnect"));
	}
	
	private void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
	public ClientFactoryBase getClientFactory() {
		return clientFactory;
	}
	public void setSubTitle(String subtitletext) {
		subtitle.setText(subtitletext);
	}


}
