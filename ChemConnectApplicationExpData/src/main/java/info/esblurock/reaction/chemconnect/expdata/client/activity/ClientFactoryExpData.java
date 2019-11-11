package info.esblurock.reaction.chemconnect.expdata.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

import info.esblurock.reaction.chemconnect.expdata.client.view.IsolateMatrixBlockView;

public interface ClientFactoryExpData {
	
	
	IsolateMatrixBlockView getIsolateMatrixBlockView();

	EventBus getEventBus();

	PlaceController getPlaceController();

	void setInUser();

}
