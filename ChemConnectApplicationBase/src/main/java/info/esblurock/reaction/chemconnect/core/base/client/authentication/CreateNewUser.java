package info.esblurock.reaction.chemconnect.core.base.client.authentication;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginServiceAsync;

public class CreateNewUser {

	AuthentificationTopPanelInterface topPanel;
	
	public CreateNewUser(AuthentificationTopPanelInterface topPanel) {
		this.topPanel = topPanel;
	}
	
	public void create(UserAccount uaccount, NameOfPerson person) {
		LoginServiceAsync async = LoginService.Util.getInstance();
		async.createNewUser(uaccount, person, new AsyncCallback<DatabaseObjectHierarchy>() {

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(DatabaseObjectHierarchy hierarchy) {
				StandardDatasetObjectHierarchyItem item = new StandardDatasetObjectHierarchyItem(null,hierarchy,
						topPanel.getModalPanel());
			}
			
		});
	}
}
