package info.esblurock.reaction.chemconnect.core.base.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

import info.esblurock.reaction.chemconnect.core.base.client.about.AboutSummary;
import info.esblurock.reaction.chemconnect.core.base.client.authentication.AuthentificationTopPanelInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.organization.OrganizationDefinition;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.person.DatabasePersonDefinition;
import info.esblurock.reaction.chemconnect.core.base.client.pages.first.FirstPage;
import info.esblurock.reaction.chemconnect.core.base.client.pages.first.FirstSiteLandingPage;
import info.esblurock.reaction.chemconnect.core.base.client.view.AboutSummaryView;
import info.esblurock.reaction.chemconnect.core.base.client.view.DatabasePersonDefinitionView;
import info.esblurock.reaction.chemconnect.core.base.client.view.FirstPageView;
import info.esblurock.reaction.chemconnect.core.base.client.view.FirstSiteLandingPageView;
import info.esblurock.reaction.chemconnect.core.base.client.view.OrganizationDefinitionView;

public class ClientFactoryBaseImpl implements ClientFactoryBase {
	private final SimpleEventBus eventBus = new SimpleEventBus();
	@SuppressWarnings("unused")
	private final PlaceController placeController = new PlaceController(eventBus);
	AuthentificationTopPanelInterface toppanel;
	
	private final AboutSummaryView aboutSummaryView = new AboutSummary();
	private final FirstSiteLandingPageView firstLandingSitePageView = new FirstSiteLandingPage();
	private final FirstPageView firstPageView = new FirstPage();
	private final DatabasePersonDefinitionView databasePersonDefinitionView = new DatabasePersonDefinition();
	private final OrganizationDefinitionView organizationDefinitionView = new OrganizationDefinition();

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
	
	@Override
	public FirstSiteLandingPageView getFirstSiteLandingPageView() {
		return firstLandingSitePageView;
	}
	
	@Override
	public FirstPageView getFirstPageView() {
		return firstPageView;
	}
	@Override
	public DatabasePersonDefinitionView getDatabasePersonDefinitionView() {
		return databasePersonDefinitionView;
	}
	@Override
	public OrganizationDefinitionView getOrganizationDefinitionView() {
		return organizationDefinitionView;
	}

	@Override
	public AuthentificationTopPanelInterface getTopPanel() {
		return toppanel;
	}

}
