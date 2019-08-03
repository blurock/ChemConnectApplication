package info.esblurock.reaction.chemconnect.core.base.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

import info.esblurock.reaction.chemconnect.core.base.client.authentication.AuthentificationTopPanelInterface;
import info.esblurock.reaction.chemconnect.core.base.client.view.AboutSummaryView;
import info.esblurock.reaction.chemconnect.core.base.client.view.DatabasePersonDefinitionView;
import info.esblurock.reaction.chemconnect.core.base.client.view.FirstPageView;
import info.esblurock.reaction.chemconnect.core.base.client.view.FirstSiteLandingPageView;
import info.esblurock.reaction.chemconnect.core.base.client.view.ManageCatalogHierarchyView;
import info.esblurock.reaction.chemconnect.core.base.client.view.OrganizationDefinitionView;
import info.esblurock.reaction.chemconnect.core.base.client.view.RepositoryFileManagerView;
import info.esblurock.reaction.chemconnect.core.base.client.view.UploadFileToBlobStorageView;


public interface ClientFactoryBase {
	EventBus getEventBus();
	PlaceController getPlaceController();
	
	void setInUser();

	AboutSummaryView getAboutSummaryView();
	FirstSiteLandingPageView getFirstSiteLandingPageView();
	FirstPageView getFirstPageView();
	DatabasePersonDefinitionView getDatabasePersonDefinitionView();
	ManageCatalogHierarchyView getManageCatalogHierarchyView();
	UploadFileToBlobStorageView getUploadFileToBlobStorageView();

	AuthentificationTopPanelInterface getTopPanel();
	OrganizationDefinitionView getOrganizationDefinitionView();
	RepositoryFileManagerView getRepositoryFileManagerView();
	
	void setTopPanel(AuthentificationTopPanelInterface toppanel);
	}
