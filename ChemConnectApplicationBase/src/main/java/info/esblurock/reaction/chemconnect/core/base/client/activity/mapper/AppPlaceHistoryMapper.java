package info.esblurock.reaction.chemconnect.core.base.client.activity.mapper;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import info.esblurock.reaction.chemconnect.core.base.client.place.AboutSummaryPlace;


@WithTokenizers( { 
	AboutSummaryPlace.Tokenizer.class
})

public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
