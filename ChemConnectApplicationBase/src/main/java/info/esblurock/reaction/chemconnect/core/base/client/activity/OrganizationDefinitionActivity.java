package info.esblurock.reaction.chemconnect.core.base.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.base.client.place.OrganizationDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.base.client.view.OrganizationDefinitionView;

public class OrganizationDefinitionActivity extends AbstractActivity implements OrganizationDefinitionView.Presenter {
	private ClientFactoryBase clientFactory;
	private String name;
	
	public OrganizationDefinitionActivity() {
		
	}
	public OrganizationDefinitionActivity(OrganizationDefinitionPlace place, ClientFactoryBase clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		OrganizationDefinitionView OrganizationDefinitionView = clientFactory.getOrganizationDefinitionView();
		OrganizationDefinitionView.setName(name);
		OrganizationDefinitionView.setPresenter(this);
		containerWidget.setWidget(OrganizationDefinitionView.asWidget());
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
