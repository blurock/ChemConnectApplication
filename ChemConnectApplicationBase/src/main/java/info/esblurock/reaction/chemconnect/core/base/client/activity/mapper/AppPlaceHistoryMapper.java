package info.esblurock.reaction.chemconnect.core.base.client.activity.mapper;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import info.esblurock.reaction.chemconnect.core.base.client.place.AboutSummaryPlace;
import info.esblurock.reaction.chemconnect.core.base.client.place.FirstSiteLandingPagePlace;


@WithTokenizers( { 
	FirstSiteLandingPagePlace.Tokenizer.class,
	AboutSummaryPlace.Tokenizer.class
})

public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
