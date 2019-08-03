package info.esblurock.reaction.chemconnect.core.base.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class RepositoryFileManagerPlace  extends Place {
	
	private String titleName;
	
	public RepositoryFileManagerPlace(String token) {
		this.titleName = token;
	}

	public String getTitleName() {
		return titleName;
	}

	public static class Tokenizer implements PlaceTokenizer<RepositoryFileManagerPlace> {
		@Override
		public String getToken(RepositoryFileManagerPlace place) {
			return place.getTitleName();
		}
		@Override
		public RepositoryFileManagerPlace  getPlace(String token)  {
			return new RepositoryFileManagerPlace(token);
		}
	}

}
