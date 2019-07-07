package info.esblurock.reaction.chemconnect.core.base.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.base.client.place.UploadFileToBlobStoragePlace;
import info.esblurock.reaction.chemconnect.core.base.client.view.UploadFileToBlobStorageView;

public class UploadFileToBlobStorageActivity extends AbstractActivity implements UploadFileToBlobStorageView.Presenter {
	private ClientFactoryBase clientFactory;
	private String name;
	
	public UploadFileToBlobStorageActivity() {
	}
	public UploadFileToBlobStorageActivity(UploadFileToBlobStoragePlace place, ClientFactoryBase clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		UploadFileToBlobStorageView UploadFileToBlobStorageView = clientFactory.getUploadFileToBlobStorageView();
		UploadFileToBlobStorageView.setName(name);
		UploadFileToBlobStorageView.setPresenter(this);
		containerWidget.setWidget(UploadFileToBlobStorageView.asWidget());
		clientFactory.setInUser();
		UploadFileToBlobStorageView.refresh();
	}
	   @Override
	    public String mayStop() {
			return null;
	    }

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}


}
