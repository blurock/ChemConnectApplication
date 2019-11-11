package info.esblurock.reaction.chemconnect.core.common.expdata.async;

import java.io.IOException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.LoginServiceAsync;

@RemoteServiceRelativePath("expdatautilities")
public interface ExpDataUtilities extends RemoteService {
	
	/*
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static ExpDataUtilitiesAsync instance;

		public static ExpDataUtilitiesAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(ExpDataUtilities.class);
			}
			return instance;
		}
	}
	

	public UserSessionData initializeBaseSystem() throws IOException;
}
