package info.esblurock.reaction.chemconnect.core.common.expdata.async;


import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;

public interface ExpDataUtilitiesAsync {

	public void initializeBaseSystem(AsyncCallback<UserSessionData> callback);

}
