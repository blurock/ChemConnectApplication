package info.esblurock.reaction.chemconnect.core.base.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


import info.esblurock.reaction.chemconnect.core.base.client.place.RepositoryFileManagerPlace;
import info.esblurock.reaction.chemconnect.core.base.client.view.RepositoryFileManagerView;;

public class RepositoryFileManagerActivity extends AbstractActivity implements RepositoryFileManagerView.Presenter {
	private ClientFactoryBase clientFactory;
	private String name;
	
	public RepositoryFileManagerActivity()  {
	}
	public RepositoryFileManagerActivity(RepositoryFileManagerPlace place, ClientFactoryBase clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		RepositoryFileManagerView repositoryFileManagerView = clientFactory.getRepositoryFileManagerView();
		repositoryFileManagerView.setName(name);
		repositoryFileManagerView.setPresenter(this);
		containerWidget.setWidget(repositoryFileManagerView.asWidget());
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
