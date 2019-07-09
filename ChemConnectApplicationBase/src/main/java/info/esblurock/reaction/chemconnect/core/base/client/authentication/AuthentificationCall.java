package info.esblurock.reaction.chemconnect.core.base.client.authentication;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.base.authorization.ClientIDInformation;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginServiceAsync;

public abstract class AuthentificationCall {
	protected AuthentificationTopPanelInterface toppanel;
	
	public AuthentificationCall(AuthentificationTopPanelInterface toppanel) {
		this.toppanel = toppanel;
	}
	
	public void initiateAthentification(String clientname) {
		LoginServiceAsync async = LoginService.Util.getInstance();
		async.getClientAuthorization(clientname, new AsyncCallback<ClientIDInformation>() {
			
			@Override
			public void onSuccess(ClientIDInformation result) {
				authorizeCallback(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				StandardWindowVisualization.errorWindowMessage("Authorization: " + clientname, 
						caught.toString());
				
			}
		});
	}

	public abstract void initiateAthentification();
	public abstract void authorizeCallback(ClientIDInformation info);
}
