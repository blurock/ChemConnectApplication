package info.esblurock.reaction.chemconnect.core.base.client.activity;


import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.base.client.place.FirstSiteLandingPagePlace;
import info.esblurock.reaction.chemconnect.core.base.client.view.FirstSiteLandingPageView;


public class FirstSiteLandingPageActivity extends AbstractActivity implements FirstSiteLandingPageView.Presenter {
	private ClientFactoryBase clientFactory;
	private String name;
	
	public FirstSiteLandingPageActivity() {
		
	}
	public FirstSiteLandingPageActivity(FirstSiteLandingPagePlace place, ClientFactoryBase clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		FirstSiteLandingPageView firstSiteLandingPageView = clientFactory.getFirstSiteLandingPageView();
		firstSiteLandingPageView.setName(name);
		firstSiteLandingPageView.setPresenter(this);
		containerWidget.setWidget(firstSiteLandingPageView.asWidget());
	}
	   @Override
	    public String mayStop() {
			return null;
	    }

	   
	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
