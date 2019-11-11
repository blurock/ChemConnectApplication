package info.esblurock.reaction.chemconnect.expdata.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import info.esblurock.reaction.chemconnect.core.base.client.BaseChemConnectPanel;
import info.esblurock.reaction.chemconnect.core.base.client.ClientEnumerateUtilities;
import info.esblurock.reaction.chemconnect.core.base.client.activity.ClientFactoryBase;
import info.esblurock.reaction.chemconnect.core.base.client.activity.mapper.AppPlaceHistoryMapper;
import info.esblurock.reaction.chemconnect.core.base.client.authentication.SetUpUserCookies;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.InterpretUploadedFileBase;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.pages.first.FirstSiteLandingPage;
import info.esblurock.reaction.chemconnect.core.base.client.place.FirstSiteLandingPagePlace;
import info.esblurock.reaction.chemconnect.core.base.client.visualize.VisualizeMediaBase;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.common.expdata.async.ExpDataUtilities;
import info.esblurock.reaction.chemconnect.core.common.expdata.async.ExpDataUtilitiesAsync;
import info.esblurock.reaction.chemconnect.expdata.client.activity.mapper.ActivityMapperExpData;
import info.esblurock.reaction.chemconnect.expdata.client.display.SetUpCollapsibleItemExpDataImpl;

public class ChemConnectApplicationExpDataEntry implements EntryPoint {

	ChemConnectApplicationFrame frame;
	ClientFactoryBase clientFactory;
	private Place defaultPlace = new FirstSiteLandingPagePlace("First");
	ChemConnectApplicationFrame toppanel;

	@Override
	public void onModuleLoad() {
		ClientEnumerateUtilities.setUpCollapsible = new SetUpCollapsibleItemExpDataImpl();
		ClientEnumerateUtilities.interpretUploadedFile = new InterpretUploadedFileBase();
		ClientEnumerateUtilities.visualizeMedia = new VisualizeMediaBase();
		ExpDataUtilitiesAsync async = ExpDataUtilities.Util.getInstance();
		async.initializeBaseSystem(new AsyncCallback<UserSessionData>() {

			@Override
			public void onFailure(Throwable caught) {
				StandardWindowVisualization.errorWindowMessage("Module Initialization: SERIOUS ERROR system abort", 
						caught.toString());
			}

			@Override
			public void onSuccess(UserSessionData user) {
				setUpModuleAfterInitialization(user);
			}
		});
		
	}

	private void setUpModuleAfterInitialization(UserSessionData user) {
		clientFactory = GWT.create(ClientFactoryBase.class);
		toppanel = new ChemConnectApplicationFrame(clientFactory);
		setUpInterface(clientFactory);			
		toppanel.loginCallback(user);
		toppanel.setLoginVisibility(user.getUserName().compareTo("guest")== 0);
		SetUpUserCookies.setup(user);
	}
	public void setUpInterface(ClientFactoryBase clientFactory) {
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
				
		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new ActivityMapperExpData(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(toppanel.getContentPanel());
		
		clientFactory.setTopPanel(toppanel);

		// Start PlaceHistoryHandler with our PlaceHistoryMapper

		AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		
		toppanel.getContentPanel().add(new FirstSiteLandingPage());
		
		RootPanel.get().add(toppanel);
		historyHandler.handleCurrentHistory();
		
	}
}
