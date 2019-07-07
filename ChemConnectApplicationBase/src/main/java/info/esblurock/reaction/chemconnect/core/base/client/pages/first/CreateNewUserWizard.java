package info.esblurock.reaction.chemconnect.core.base.client.pages.first;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import info.esblurock.reaction.chemconnect.core.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.base.client.authentication.AuthentificationTopPanelInterface;
import info.esblurock.reaction.chemconnect.core.base.client.authentication.SetUpUserCookies;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.modal.OKAnswerInterface;
import info.esblurock.reaction.chemconnect.core.base.client.modal.OKModal;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginServiceAsync;

public class CreateNewUserWizard extends Composite implements OKAnswerInterface {

	private static CreateNewUserWizardUiBinder uiBinder = GWT.create(CreateNewUserWizardUiBinder.class);

	interface CreateNewUserWizardUiBinder extends UiBinder<Widget, CreateNewUserWizard> {
	}

	@UiField
	MaterialTextBox accountname;
	@UiField
	MaterialButton submit;
	@UiField
	MaterialTextBox persontitle;
	@UiField
	MaterialTextBox personfirstname;
	@UiField
	MaterialTextBox personlastname;
	@UiField
	MaterialTitle title;
	@UiField
	MaterialLabel titletext;
	
	String authID;
	String authSource;
	DataCatalogID datid;
	NameOfPerson person;
	UserAccount useraccount;
	UserSessionData sessiondata;
	
	MaterialCollapsible collapsiblePanel;
	MaterialPanel modalpanel;
	MaterialPanel mainPanel;
	AuthentificationTopPanelInterface topPanel;
	
	public CreateNewUserWizard(AuthentificationTopPanelInterface topPanel, MaterialPanel mainPanel, MaterialCollapsible collapsiblePanel,MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.topPanel = topPanel;
		this.collapsiblePanel = collapsiblePanel;
		this.modalpanel = modalpanel;
		this.mainPanel = mainPanel;
		init();
		fromCookies();
	}

	private void init() {
		title.setTitle("New User Wizard");
		accountname.setLabel("Account Name");
		persontitle.setLabel("Title");
		personfirstname.setLabel("First Name");
		personlastname.setLabel("Last Name");
		submit.setText("Create new user");
		
		String titletextS = "Fill out this form for the minimal information for a new user";
		titletext.setText(titletextS);
		
		accountname.setPlaceholder("");
		persontitle.setPlaceholder("");
		personfirstname.setPlaceholder("");
		personlastname.setPlaceholder("");
}
	private void fromCookies() {
		
		String given_nameS = Cookies.getCookie("given_name");
		String family_nameS = Cookies.getCookie("family_name");
		authID = Cookies.getCookie("auth_id");
		String account = Cookies.getCookie("account_name");
		authSource  = Cookies.getCookie("authorizationType");
        /*
		String given_nameS  = Window.Location.getParameter("given_name");
		Window.alert("given_name: " + given_nameS);
		String family_nameS = Window.Location.getParameter("family_name");
		authID              = Window.Location.getParameter("auth_id");
		String account      = Window.Location.getParameter("account_name");
		authSource          = Window.Location.getParameter("authorizationType");
		*/

		personfirstname.setText(given_nameS);
		personlastname.setText(family_nameS);
		accountname.setText(account);

		Cookies.setCookie("user", account);
}
	
	@UiHandler("submit")
	public void submitClick(ClickEvent event) {
		String titleS = persontitle.getValue();
		String givenNameS = personfirstname.getValue();
		String familyNameS = personlastname.getValue();
		String account = accountname.getValue();
		ChemConnectCompoundDataStructure datastructure = new ChemConnectCompoundDataStructure(account,null);
		person = new NameOfPerson(datastructure,titleS,givenNameS,familyNameS);
		String accountPrivilege = UserAccountKeys.accessTypeDataUser;
		ChemConnectDataStructure structure = new ChemConnectDataStructure();
		useraccount = new UserAccount(structure, accountname.getText(), authID, authSource, accountPrivilege);
		String text = "Create user '" + accountname.getText() + "' for " + givenNameS + " " + familyNameS;
		String oktext = "Create New User";
		OKModal okpanel = new OKModal("Create", text, oktext, this);
		modalpanel.clear();
		modalpanel.add(okpanel);
		okpanel.openModal();
	}

	@Override
	public void answeredOK(String answer) {
		collapsiblePanel.clear();
		mainPanel.clear();
		LoginServiceAsync async = LoginService.Util.getInstance();
		async.createNewUser(useraccount, person,
				new AsyncCallback<DatabaseObjectHierarchy>() {
			@Override
			public void onFailure(Throwable arg0) {
				MaterialLoader.loading(false);
				Window.alert("ERROR: Set up after user creation:\n" + arg0.toString());
				topPanel.logout();
			}

			@Override
			public void onSuccess(DatabaseObjectHierarchy hierarchy) {
				
				StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,hierarchy,modalpanel);		
				collapsiblePanel.add(item);
				LoginServiceAsync async = LoginService.Util.getInstance();
				async.loginAsCurrentUser(new AsyncCallback<UserSessionData>() {

					@Override
					public void onFailure(Throwable caught) {
						MaterialLoader.loading(false);
						StandardWindowVisualization.errorWindowMessage("Log in as current new user", caught.toString());
					}

					@Override
					public void onSuccess(UserSessionData result) {
						MaterialLoader.loading(false);
						sessiondata = result;
						StandardWindowVisualization.successWindowMessage("New user successfully logged in" );
						SetUpUserCookies.setup(result);
						Window.alert("CreateNewUserWizard: 1");
						Window.alert("CreateNewUserWizard: 2\n" + result.toString());
						topPanel.loginCallback(result);
						Window.alert("CreateNewUserWizard: 3");
					}
					
				});
			}
			
		});
	}

}
