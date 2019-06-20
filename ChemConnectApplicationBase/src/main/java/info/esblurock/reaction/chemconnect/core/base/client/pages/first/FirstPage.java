package info.esblurock.reaction.chemconnect.core.base.client.pages.first;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.base.client.authentication.AuthentificationTopPanelInterface;
import info.esblurock.reaction.chemconnect.core.base.client.view.FirstPageView;

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
		Window.alert("FirstPage: asNewUser()");
		CreateNewUserWizard wizard = new CreateNewUserWizard(topPanel,mainPanel,mainCollapsible,modalPanel);
		mainPanel.clear();
		mainPanel.add(wizard);
	}
	public void asExistingUser() {
		Window.alert("FirstPage: asExistingUser()");
		String account = Cookies.getCookie("account_name");
		Cookies.setCookie("user", account);
		
	}


	MaterialPanel getMainPanel() {
		return mainPanel;
	}
	public void setTopPanel(AuthentificationTopPanelInterface topPanel) {
		this.topPanel = topPanel;
	}


}
