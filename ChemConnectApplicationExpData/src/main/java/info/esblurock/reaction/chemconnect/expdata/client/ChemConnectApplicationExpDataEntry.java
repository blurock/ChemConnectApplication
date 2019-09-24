package info.esblurock.reaction.chemconnect.expdata.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import info.esblurock.reaction.chemconnect.core.base.client.ClientEnumerateUtilities;
import info.esblurock.reaction.chemconnect.core.base.client.activity.ClientFactoryBase;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.InterpretUploadedFileBase;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.visualize.VisualizeMediaBase;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginServiceAsync;

public class ChemConnectApplicationExpDataEntry implements EntryPoint {

	ChemConnectApplicationFrame frame;
	ClientFactoryBase clientFactory;
	
	@Override
	public void onModuleLoad() {
		ClientEnumerateUtilities.interpretUploadedFile = new InterpretUploadedFileBase();
		ClientEnumerateUtilities.visualizeMedia = new VisualizeMediaBase();
		LoginServiceAsync async = LoginService.Util.getInstance();
		async.initializeBaseSystem(new AsyncCallback<UserSessionData>() {

			@Override
			public void onFailure(Throwable caught) {
				StandardWindowVisualization.errorWindowMessage("Module Initialization: SERIOUS ERRIR system abort", 
						caught.toString());
			}

			@Override
			public void onSuccess(UserSessionData user) {
				Window.alert("Session: " + user.toString());
				
				setUpModuleAfterInitialization(user);
			}
		});
		
	}

	private void setUpModuleAfterInitialization(UserSessionData user) {
		clientFactory = GWT.create(ClientFactoryBase.class);

		frame = new ChemConnectApplicationFrame(clientFactory);
		RootPanel.get().add(frame);
		
		
		
		
	}

}
