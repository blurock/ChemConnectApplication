package info.esblurock.reaction.chemconnect.core.base.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.base.client.place.AboutSummaryPlace;
import info.esblurock.reaction.chemconnect.core.base.client.view.AboutSummaryView;


public class AboutSummaryActivity extends AbstractActivity implements AboutSummaryView.Presenter {
	private ClientFactoryBase clientFactory;
	private String name;
	
	public AboutSummaryActivity()  {
	}
	public AboutSummaryActivity(AboutSummaryPlace place, ClientFactoryBase clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		AboutSummaryView aboutSummaryView = clientFactory.getAboutSummaryView();
		aboutSummaryView.setName(name);
		aboutSummaryView.setPresenter(this);
		containerWidget.setWidget(aboutSummaryView.asWidget());
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
