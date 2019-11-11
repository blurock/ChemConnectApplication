package info.esblurock.reaction.chemconnect.expdata.client.activity.mapper;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import info.esblurock.reaction.chemconnect.core.base.client.place.AboutSummaryPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.ConsortiumManagementPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.DatabasePersonDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.FirstPagePlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.FirstSiteLandingPagePlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.ManageCatalogHierarchyPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.RepositoryFileManagerPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.UploadFileToBlobStoragePlace;
import info.esblurock.reaction.chemconnect.expdata.client.place.IsolateMatrixBlockPlace;

@WithTokenizers( { 
	FirstSiteLandingPagePlace.Tokenizer.class,
	DatabasePersonDefinitionPlace.Tokenizer.class,
	UploadFileToBlobStoragePlace.Tokenizer.class,
	ManageCatalogHierarchyPlace.Tokenizer.class,
	FirstPagePlace.Tokenizer.class,
	AboutSummaryPlace.Tokenizer.class,
	RepositoryFileManagerPlace.Tokenizer.class,
	ConsortiumManagementPlace.Tokenizer.class,
	IsolateMatrixBlockPlace.Tokenizer.class
})

public interface AppPlaceHistoryMapperExpData extends PlaceHistoryMapper {

}
