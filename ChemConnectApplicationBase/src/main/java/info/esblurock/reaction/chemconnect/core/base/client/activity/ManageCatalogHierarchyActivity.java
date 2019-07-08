package info.esblurock.reaction.chemconnect.core.base.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import info.esblurock.reaction.chemconnect.core.base.client.place.ManageCatalogHierarchyPlace;
import info.esblurock.reaction.chemconnect.core.base.client.view.ManageCatalogHierarchyView;

public class ManageCatalogHierarchyActivity extends AbstractActivity implements ManageCatalogHierarchyView.Presenter {
	private ClientFactoryBase clientFactory;
	private String name;
	
	public ManageCatalogHierarchyActivity() {
		
	}
	public ManageCatalogHierarchyActivity(ManageCatalogHierarchyPlace place, ClientFactoryBase clientFactory) {
		this.name = place.getTitleName();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
		ManageCatalogHierarchyView manageCatalogHierarchyView = clientFactory.getManageCatalogHierarchyView();
		manageCatalogHierarchyView.setName(name);
		manageCatalogHierarchyView.setPresenter(this);
		manageCatalogHierarchyView.setUpHierarchyFromDatabase();
		containerWidget.setWidget(manageCatalogHierarchyView.asWidget());
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
