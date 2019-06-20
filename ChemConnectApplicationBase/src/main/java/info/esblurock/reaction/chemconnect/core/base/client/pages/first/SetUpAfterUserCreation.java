package info.esblurock.reaction.chemconnect.core.base.client.pages.first;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialPanel;
import info.esblurock.reaction.chemconnect.core.base.client.BaseChemConnectPanel;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginServiceAsync;
import info.esblurock.reaction.core.server.base.authentification.LoginUtilities;

public class SetUpAfterUserCreation implements AsyncCallback<DatabaseObjectHierarchy> {

	MaterialCollapsible panel;
	MaterialPanel modalpanel;
	BaseChemConnectPanel toppanel;
	
	public SetUpAfterUserCreation(BaseChemConnectPanel toppanel, MaterialCollapsible panel,MaterialPanel modalpanel) {
		this.panel = panel;
		this.modalpanel = modalpanel;
		this.toppanel = toppanel;
		MaterialLoader.loading(true);
	}
	
	@Override
	public void onFailure(Throwable arg0) {
		MaterialLoader.loading(false);
		Window.alert("ERROR: Set up after user creation:\n" + arg0.toString());
	}

	@Override
	public void onSuccess(DatabaseObjectHierarchy hierarchy) {
		MaterialLoader.loading(false);
		String account = Cookies.getCookie("account_name");
		Cookies.setCookie("user", account);
		StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,hierarchy,modalpanel);		
		panel.add(item);
		LoginServiceAsync async = LoginService.Util.getInstance();
		async.loginAsCurrentUser(new AsyncCallback<UserSessionData>() {
			
			@Override
			public void onSuccess(UserSessionData result) {
				StandardWindowVisualization.successWindowMessage("Successful login: " + result.getUserName());
				toppanel.loginCallback(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				StandardWindowVisualization.errorWindowMessage("Logging in as new user", caught.toString());
			}
		});
	}

}
