package info.esblurock.reaction.chemconnect.core.base.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.base.client.place.ConsortiumManagementPlace;
import info.esblurock.reaction.chemconnect.core.base.client.view.ConsortiumManagementView;


public class ConsortiumManagementActivity extends AbstractActivity implements ConsortiumManagementView.Presenter {
	private ClientFactoryBase clientFactory;
	private String name;
	
	public ConsortiumManagementActivity() {
		
	}
	public ConsortiumManagementActivity(ConsortiumManagementPlace place, ClientFactoryBase clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		ConsortiumManagementView ConsortiumManagementView = clientFactory.getConsortiumManagementView();
		ConsortiumManagementView.setName(name);
		ConsortiumManagementView.setPresenter(this);
		containerWidget.setWidget(ConsortiumManagementView.asWidget());
		ConsortiumManagementView.refresh();
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
