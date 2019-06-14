package info.esblurock.reaction.chemconnect.core.base.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

import info.esblurock.reaction.chemconnect.core.base.client.about.AboutSummary;
import info.esblurock.reaction.chemconnect.core.base.client.view.AboutSummaryView;

public class ClientFactoryBaseImpl implements ClientFactoryBase {
	private final SimpleEventBus eventBus = new SimpleEventBus();
	@SuppressWarnings("unused")
	private final PlaceController placeController = new PlaceController(eventBus);

	private final AboutSummaryView aboutSummaryView = new AboutSummary();

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public void setInUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AboutSummaryView getAboutSummaryView() {
		return aboutSummaryView;
	}

}
