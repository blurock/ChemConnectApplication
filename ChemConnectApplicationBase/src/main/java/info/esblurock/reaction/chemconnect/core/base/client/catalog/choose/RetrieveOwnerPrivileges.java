package info.esblurock.reaction.chemconnect.core.base.client.catalog.choose;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginServiceAsync;

public class RetrieveOwnerPrivileges {
	RetrieveOwnerPrivilegesInterface top;
	
	public RetrieveOwnerPrivileges(RetrieveOwnerPrivilegesInterface top) {
		this.top = top;
	}
	
	public void getPrivileges() {
		LoginServiceAsync async = LoginService.Util.getInstance();
		async.getAccessCreationList(new AsyncCallback<List<String>>() {
			@Override
			public void onFailure(Throwable caught) {
				top.setInOwnerPrivilegesFailure(caught);
			}
			@Override
			public void onSuccess(List<String> result) {
				top.setInOwnerPrivilegesSuccess(result);
			}
		});

	}

}
