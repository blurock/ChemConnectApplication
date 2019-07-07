package info.esblurock.reaction.chemconnect.core.base.client.activity.mapper;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import info.esblurock.reaction.chemconnect.core.base.client.place.AboutSummaryPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.DatabasePersonDefinitionPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.FirstPagePlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.FirstSiteLandingPagePlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.ManageCatalogHierarchyPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.UploadFileToBlobStoragePlace;


@WithTokenizers( { 
	FirstSiteLandingPagePlace.Tokenizer.class,
	DatabasePersonDefinitionPlace.Tokenizer.class,
	UploadFileToBlobStoragePlace.Tokenizer.class,
	ManageCatalogHierarchyPlace.Tokenizer.class,
	FirstPagePlace.Tokenizer.class,
	AboutSummaryPlace.Tokenizer.class
})

public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
