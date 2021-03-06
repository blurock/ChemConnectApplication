package info.esblurock.reaction.chemconnect.core.client.firstpage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.base.client.pages.first.CreateNewUserWizard;
import info.esblurock.reaction.chemconnect.core.base.client.view.FirstPageView;
import info.esblurock.reaction.chemconnect.core.client.SimpleLoginCallback;
import info.esblurock.reaction.chemconnect.core.client.TopChemConnectPanel;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginServiceAsync;

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
	
	
	TopChemConnectPanel toppanel;
	
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
		Window.alert("FirstPage: asNewUser()");
		CreateNewUserWizard wizard = new CreateNewUserWizard(toppanel,mainPanel,mainCollapsible,modalPanel);
		mainPanel.clear();
		mainPanel.add(wizard);
	}
	public void asExistingUser() {
		Window.alert("FirstPage: asExistingUser()");
		String account = Cookies.getCookie("account_name");
		Cookies.setCookie("user", account);
		LoginServiceAsync async = LoginService.Util.getInstance();
		SimpleLoginCallback callback = new SimpleLoginCallback(null,toppanel.getClientFactory());
		async.getUserInfo(callback);
		toppanel.setInUser();
	}


	MaterialPanel getMainPanel() {
		return mainPanel;
	}
	public void setTopPanel(TopChemConnectPanel toppanel) {
		this.toppanel = toppanel;
	}


}
