package info.esblurock.reaction.chemconnect.expdata.client.activity.mapper;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

import info.esblurock.reaction.chemconnect.core.base.client.activity.ClientFactoryBase;
import info.esblurock.reaction.chemconnect.core.base.client.activity.mapper.AppActivityMapper;
import info.esblurock.reaction.chemconnect.expdata.client.activity.ClientFactoryExpData;
import info.esblurock.reaction.chemconnect.expdata.client.activity.IsolateMatrixBlockActivity;
import info.esblurock.reaction.chemconnect.expdata.client.place.IsolateMatrixBlockPlace;

public class ActivityMapperExpData extends AppActivityMapper {

	public ActivityMapperExpData(ClientFactoryBase clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity getActivity(Place place) {
		Activity activity = super.getActivity(place);
		if (activity == null) {
			ClientFactoryExpData clientFactoryExpData = (ClientFactoryExpData) clientFactory;
			if (place instanceof IsolateMatrixBlockPlace) {
				activity = new IsolateMatrixBlockActivity((IsolateMatrixBlockPlace) place, clientFactoryExpData);
			}
		}
		return activity;
	}

}
