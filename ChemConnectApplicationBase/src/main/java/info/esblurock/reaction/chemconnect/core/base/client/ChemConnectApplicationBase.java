package info.esblurock.reaction.chemconnect.core.base.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import info.esblurock.reaction.chemconnect.core.base.client.activity.ClientFactoryBase;
import info.esblurock.reaction.chemconnect.core.base.client.activity.mapper.AppActivityMapper;
import info.esblurock.reaction.chemconnect.core.base.client.activity.mapper.AppPlaceHistoryMapper;
import info.esblurock.reaction.chemconnect.core.base.client.authentication.SetUpUserCookies;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SetUpCollapsibleItemBase;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.InterpretUploadedFileBase;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.pages.first.FirstSiteLandingPage;
import info.esblurock.reaction.chemconnect.core.base.client.place.FirstSiteLandingPagePlace;
import info.esblurock.reaction.chemconnect.core.base.client.visualize.VisualizeMediaBase;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginServiceAsync;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ChemConnectApplicationBase implements EntryPoint {
	private Place defaultPlace = new FirstSiteLandingPagePlace("First");
	BaseChemConnectPanel toppanel;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		ClientEnumerateUtilities.setUpCollapsible = new SetUpCollapsibleItemBase();
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
				setUpModuleAfterInitialization(user);
			}
		});
		
	}
	private void setUpModuleAfterInitialization(UserSessionData user) {
		//String redirect = Cookies.getCookie("redirect");
		//String account_name = Cookies.getCookie("user");
		Cookies.removeCookie("redirect");
		
		ClientFactoryBase clientFactory = GWT.create(ClientFactoryBase.class);
		toppanel = new BaseChemConnectPanel(clientFactory);
		setUpInterface(clientFactory);			
		toppanel.loginCallback(user);
		toppanel.setLoginVisibility(user.getUserName().compareTo("guest")== 0);
		SetUpUserCookies.setup(user);
		/*
		boolean firsttime = true;
		if(redirect == null || account_name == null) {
			firsttime = true;
		}  else if(redirect.compareTo(account_name) == 0) {
			firsttime = false;
		}
		
		if(firsttime) {
			toppanel.loginCallback(user);
			toppanel.setLoginVisibility(true);
			SetUpUserCookies.setup(user);

			//LoginAsGuest glogin = new LoginAsGuest(toppanel);
			glogin.login();
			LoginServiceAsync async = LoginService.Util.getInstance();
			async.initialization(new AsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
					//StandardWindowVisualization.successWindowMessage("Initialization Complete");
				}
				@Override
				public void onFailure(Throwable caught) {
					StandardWindowVisualization.errorWindowMessage("", caught.toString());
				}
			});
			
		} else {
			
		}
		*/
	}
	
	
	@SuppressWarnings("deprecation")
	public void setUpInterface(ClientFactoryBase clientFactory) {
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
				
		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
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
