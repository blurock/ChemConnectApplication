package info.esblurock.reaction.chemconnect.core.base.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import info.esblurock.reaction.chemconnect.core.base.client.about.AboutSummary;
import info.esblurock.reaction.chemconnect.core.base.client.activity.ClientFactoryBase;
import info.esblurock.reaction.chemconnect.core.base.client.activity.mapper.AppActivityMapper;
import info.esblurock.reaction.chemconnect.core.base.client.activity.mapper.AppPlaceHistoryMapper;
import info.esblurock.reaction.chemconnect.core.base.client.pages.first.FirstSiteLandingPage;
import info.esblurock.reaction.chemconnect.core.base.client.place.AboutSummaryPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.FirstSiteLandingPagePlace;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ChemConnectApplicationBase implements EntryPoint {
	private Place defaultPlace = new FirstSiteLandingPagePlace("First");

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		ClientFactoryBase clientFactory = GWT.create(ClientFactoryBase.class);
		setUpInterface(clientFactory);
	}
	
	
	@SuppressWarnings("deprecation")
	public void setUpInterface(ClientFactoryBase clientFactory) {
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
				
		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		BaseChemConnectPanel toppanel = new BaseChemConnectPanel(clientFactory);
		activityManager.setDisplay(toppanel.getContentPanel());

		// Start PlaceHistoryHandler with our PlaceHistoryMapper

		AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		
		toppanel.getContentPanel().add(new FirstSiteLandingPage());
		
		RootPanel.get().add(toppanel);
		historyHandler.handleCurrentHistory();
		
	}
}
