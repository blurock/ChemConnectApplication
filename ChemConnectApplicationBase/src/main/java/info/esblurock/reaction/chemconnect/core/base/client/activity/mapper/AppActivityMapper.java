package info.esblurock.reaction.chemconnect.core.base.client.activity.mapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;



import info.esblurock.reaction.chemconnect.core.base.client.activity.AboutSummaryActivity;
import info.esblurock.reaction.chemconnect.core.base.client.activity.ClientFactoryBase;
import info.esblurock.reaction.chemconnect.core.base.client.activity.ConsortiumManagementActivity;
import info.esblurock.reaction.chemconnect.core.base.client.activity.DatabasePersonDefinitionActivity;
import info.esblurock.reaction.chemconnect.core.base.client.activity.FirstPageActivity;
import info.esblurock.reaction.chemconnect.core.base.client.activity.FirstSiteLandingPageActivity;
import info.esblurock.reaction.chemconnect.core.base.client.activity.ManageCatalogHierarchyActivity;
import info.esblurock.reaction.chemconnect.core.base.client.activity.OrganizationDefinitionActivity;
import info.esblurock.reaction.chemconnect.core.base.client.activity.RepositoryFileManagerActivity;
import info.esblurock.reaction.chemconnect.core.base.client.activity.UploadFileToBlobStorageActivity;

import info.esblurock.reaction.chemconnect.core.base.client.place.AboutSummaryPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.ConsortiumManagementPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.DatabasePersonDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.FirstPagePlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.FirstSiteLandingPagePlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.ManageCatalogHierarchyPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.OrganizationDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.RepositoryFileManagerPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.UploadFileToBlobStoragePlace;


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
		} else if (place instanceof DatabasePersonDefinitionPlace) {
			return new DatabasePersonDefinitionActivity((DatabasePersonDefinitionPlace) place, clientFactory);
		} else if (place instanceof OrganizationDefinitionPlace) {
			return new OrganizationDefinitionActivity((OrganizationDefinitionPlace) place, clientFactory);
		} else if (place instanceof UploadFileToBlobStoragePlace) {
			return new UploadFileToBlobStorageActivity((UploadFileToBlobStoragePlace) place, clientFactory);
		} else if (place instanceof ManageCatalogHierarchyPlace) {
			return new ManageCatalogHierarchyActivity((ManageCatalogHierarchyPlace) place, clientFactory);
		} else if (place instanceof FirstPagePlace) {
			return new FirstPageActivity((FirstPagePlace) place, clientFactory);
		} else if (place instanceof RepositoryFileManagerPlace) {
			return new RepositoryFileManagerActivity((RepositoryFileManagerPlace) place, clientFactory);
		} else if (place instanceof ConsortiumManagementPlace) {
			return new ConsortiumManagementActivity((ConsortiumManagementPlace) place, clientFactory);
		}
		return null;
	}

}
