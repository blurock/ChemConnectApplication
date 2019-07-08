package info.esblurock.reaction.chemconnect.core.base.client.pages.first;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.base.client.authentication.AuthentificationTopPanelInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.view.FirstPageView;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;

public class FirstPage extends Composite implements FirstPageView {

	private static FirstPageUiBinder uiBinder = GWT.create(FirstPageUiBinder.class);

	interface FirstPageUiBinder extends UiBinder<Widget, FirstPage> {
	}
	
	Presenter listener;

	@UiField
	MaterialPanel modalPanel;
	@UiField
	MaterialPanel footerpanel;
	@UiField
	MaterialPanel mainPanel;
	@UiField
	MaterialCollapsible mainCollapsible;
	
	AuthentificationTopPanelInterface topPanel;
	
	
	public FirstPage() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	
	void init() {
	}

	@Override
	public void setName(String helloName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}
	
	public void asNewUser() {
		CreateNewUserWizard wizard = new CreateNewUserWizard(topPanel,mainPanel,mainCollapsible,modalPanel);
		mainPanel.clear();
		mainPanel.add(wizard);
	}
	public void asExistingUser() {
		String account = Cookies.getCookie("account_name");
		String userName =Cookies.getCookie("account_name");;
		String sessionID = Cookies.getCookie("account_name");;
		String IP = Cookies.getCookie("account_name");;
		String hostname = Cookies.getCookie("account_name");;
		String userLevel = Cookies.getCookie("account_name");;
		UserSessionData usession = new UserSessionData(userName,sessionID,IP,hostname,userLevel);
		Cookies.setCookie("user", account);
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		String id = StandardDataKeywords.individualInformationPrefix + "-" + 
				usession.getUserName() + 
				"-" + StandardDataKeywords.individualInformationSuffix;
		String type = MetaDataKeywords.databasePerson;
		async.getTopCatalogObject(id,type, new AsyncCallback<DatabaseObjectHierarchy>() {

			@Override
			public void onFailure(Throwable caught) {
				StandardWindowVisualization.errorWindowMessage("Read in current user", caught.toString());
			}

			@Override
			public void onSuccess(DatabaseObjectHierarchy hierarchy) {
				StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,hierarchy,modalPanel);
				mainCollapsible.add(item);
			}
			
		});
	}

	MaterialPanel getMainPanel() {
		return mainPanel;
	}
	public void setTopPanel(AuthentificationTopPanelInterface topPanel) {
		this.topPanel = topPanel;
	}


}
