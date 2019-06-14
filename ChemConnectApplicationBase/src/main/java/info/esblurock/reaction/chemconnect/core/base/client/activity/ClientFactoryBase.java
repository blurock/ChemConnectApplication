package info.esblurock.reaction.chemconnect.core.base.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

import info.esblurock.reaction.chemconnect.core.base.client.view.AboutSummaryView;
import info.esblurock.reaction.chemconnect.core.base.client.view.FirstSiteLandingPageView;


public interface ClientFactoryBase {
	EventBus getEventBus();
	PlaceController getPlaceController();
	
	void setInUser();

	AboutSummaryView getAboutSummaryView();
	FirstSiteLandingPageView getFirstSiteLandingPageView();
}
