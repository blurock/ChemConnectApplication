package info.esblurock.reaction.chemconnect.core.base.client.activity.mapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import info.esblurock.reaction.chemconnect.core.base.client.activity.AboutSummaryActivity;
import info.esblurock.reaction.chemconnect.core.base.client.activity.ClientFactoryBase;
import info.esblurock.reaction.chemconnect.core.base.client.activity.FirstSiteLandingPageActivity;
import info.esblurock.reaction.chemconnect.core.base.client.place.AboutSummaryPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.FirstSiteLandingPagePlace;


public class AppActivityMapper implements ActivityMapper  {
	private ClientFactoryBase clientFactory;
	
	/**
	 * AppActivityMapper associates each Place with its corresponding
	 * {@link Activity}
	 * 
	 * @param clientFactory
	 *            Factory to be passed to activities
	 */
	public AppActivityMapper(ClientFactoryBase clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof AboutSummaryPlace) {
			return new AboutSummaryActivity((AboutSummaryPlace) place, clientFactory);
		} else if (place instanceof FirstSiteLandingPagePlace) {
			return new FirstSiteLandingPageActivity((FirstSiteLandingPagePlace) place, clientFactory);
		}
		return null;
	}

}
