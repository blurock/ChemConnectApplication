package info.esblurock.reaction.chemconnect.expdata.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.expdata.client.place.IsolateMatrixBlockPlace;
import info.esblurock.reaction.chemconnect.expdata.client.view.IsolateMatrixBlockView;


public class IsolateMatrixBlockActivity extends AbstractActivity implements IsolateMatrixBlockView.Presenter {
	private ClientFactoryExpData clientFactory;
	private String name;
	
	public IsolateMatrixBlockActivity() {
		
	}
	public IsolateMatrixBlockActivity(IsolateMatrixBlockPlace place, ClientFactoryExpData clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		IsolateMatrixBlockView IsolateMatrixBlockView = clientFactory.getIsolateMatrixBlockView();
		IsolateMatrixBlockView.setName(name);
		IsolateMatrixBlockView.setPresenter(this);
		clientFactory.setInUser();
		IsolateMatrixBlockView.refresh();
		containerWidget.setWidget(IsolateMatrixBlockView.asWidget());
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
