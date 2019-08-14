package info.esblurock.reaction.chemconnect.core.base.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ConsortiumManagementPlace extends Place {
	private String titleName;

	public ConsortiumManagementPlace(String token) {
		this.titleName = token;
	}
	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<ConsortiumManagementPlace> {
		@Override
		public String getToken(ConsortiumManagementPlace place) {
			return place.getTitleName();
		}
		@Override
		public ConsortiumManagementPlace  getPlace(String token)  {
			return new ConsortiumManagementPlace(token);
		}
	}

}
