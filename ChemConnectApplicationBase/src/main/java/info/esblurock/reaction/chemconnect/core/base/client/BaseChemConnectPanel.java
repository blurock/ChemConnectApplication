package info.esblurock.reaction.chemconnect.core.base.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.client.activity.ClientFactoryBase;
import info.esblurock.reaction.chemconnect.core.base.client.place.AboutSummaryPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.ConsortiumManagementPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.DatabasePersonDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.FirstSiteLandingPagePlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.ManageCatalogHierarchyPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.OrganizationDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.RepositoryFileManagerPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.UploadFileToBlobStoragePlace;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.base.client.authentication.AuthentificationTopPanelInterface;
import info.esblurock.reaction.chemconnect.core.base.client.authentication.FacebookAuthentification;
import info.esblurock.reaction.chemconnect.core.base.client.authentication.LinkedInAuthentification;
import info.esblurock.reaction.chemconnect.core.base.client.authentication.LoginAsGuest;
import info.esblurock.reaction.chemconnect.core.base.client.authentication.LogoutUser;
import info.esblurock.reaction.chemconnect.core.base.client.authentication.GoogleAuthentification;

public class BaseChemConnectPanel extends Composite implements AuthentificationTopPanelInterface {

	ClientFactoryBase clientFactory;
	UserSessionData userSessionAccount;

	private static BaseChemConnectPanelUiBinder uiBinder = GWT.create(BaseChemConnectPanelUiBinder.class);

	interface BaseChemConnectPanelUiBinder extends UiBinder<Widget, BaseChemConnectPanel> {
	}

	@UiField
	MaterialLink username;
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
	MaterialLink createbutton;
	@UiField
	MaterialLink catalog;
	@UiField
	MaterialLink upload;
	@UiField
	MaterialLink repository;
	@UiField
	MaterialLink isolate;
	@UiField
	MaterialLink people;
	@UiField
	MaterialLink organizations;
	@UiField
	MaterialLink consortia;
	@UiField
	MaterialLink logout;
	@UiField
	MaterialTooltip logouttooltip;
	@UiField
	MaterialTooltip logintooltip;
	@UiField
	MaterialPanel modalpanel;

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
		consortia.setText("Consortia");
		repository.setText("manage repository");
		logout.setText("Logout");
		logouttooltip.setText("Log out current user (to Guest");
		logintooltip.setText("Choose method of login");
		userSessionAccount = null;
	}

	@UiHandler("guestLogin")
	void onClickGuest(ClickEvent e) {
		LoginAsGuest glogin = new LoginAsGuest(this);
		glogin.login();
	}

	@UiHandler("googleLogin")
	void onClickGoogle(ClickEvent e) {
		GoogleAuthentification google = new GoogleAuthentification(this);
		google.initiateAthentification();

	}

	@UiHandler("linkedinLogin")
	void onClickLinkedIn(ClickEvent e) {
		LinkedInAuthentification linkedIn = new LinkedInAuthentification(this);
		linkedIn.initiateAthentification();
	}
	@UiHandler("facebookLogin")
	void onClickFacebook(ClickEvent e) {
		FacebookAuthentification facebook = new FacebookAuthentification(this);
		facebook.initiateAthentification();
	}
	

	@Override
	public void loginCallback(UserSessionData account) {
		setLoginVisibility(false);
		userSessionAccount = account;
		username.setText(account.getUserName());
	}

	public void setLoginVisibility(boolean loginvisible) {
		logout.setVisible(!loginvisible);
		loginchoice.setVisible(loginvisible);
	}

	public void setCreateButtonVisibility(boolean visibility) {
		createbutton.setVisible(visibility);
	}

	public String callbackWithServer() {
		String redirect = "http://blurock-chemconnect.appspot.com/oauth2callback";
		if (Window.Location.getHostName().compareTo("localhost") == 0) {
			redirect = "http://localhost:8080/oauth2callback";
		}
		return redirect;
	}

	@UiHandler("logout")
	public void onLogout(ClickEvent event) {
		LogoutUser ulogout = new LogoutUser(this);
		ulogout.logout();
		subtitle.setText("ChemConnect: The Intelligent Repository");
		goTo(new FirstSiteLandingPagePlace("Home"));
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

	@UiHandler("catalog")
	public void onCatalogClick(ClickEvent event) {
		setSubTitle("Manage Catalog Structure");
		goTo(new ManageCatalogHierarchyPlace("Manage Catalog Structure"));
	}

	@UiHandler("upload")
	public void onUploadClick(ClickEvent event) {
		setSubTitle("File staging");
		goTo(new UploadFileToBlobStoragePlace("File staging"));
	}


	@UiHandler("people")
	public void onPeopleClick(ClickEvent event) {
		setSubTitle("Manage Contact Info");
		goTo(new DatabasePersonDefinitionPlace("Manage Contact Info"));
	}

	@UiHandler("organizations")
	public void onOrganizationsClick(ClickEvent event) {
		setSubTitle("Manage Organization Info");
		goTo(new OrganizationDefinitionPlace("Manage Organization Info"));
	}

	@UiHandler("consortia")
	public void onConsortiaClick(ClickEvent event) {
		setSubTitle("Manage Consortium Info");
		goTo(new ConsortiumManagementPlace("Manage Consortium Info"));
	}
	@UiHandler("repository")
	public void onRepositoryClick(ClickEvent event) {
		setSubTitle("Repository Manager");
		goTo(new RepositoryFileManagerPlace("Manage Repository"));
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

	@Override
	public MaterialPanel getModalPanel() {
		return null;
	}

	@Override
	public void logout() {
		setLoginVisibility(true);
		username.setText(Cookies.getCookie("user"));
	}

}
