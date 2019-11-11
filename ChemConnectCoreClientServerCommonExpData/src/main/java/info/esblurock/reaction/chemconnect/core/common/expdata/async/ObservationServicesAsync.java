package info.esblurock.reaction.chemconnect.core.common.expdata.async;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public interface ObservationServicesAsync {

	public void createObservationBlockFromSpreadSheet(DatabaseObject obj, 
			String blocktype, DataCatalogID datid, AsyncCallback<DatabaseObjectHierarchy> callback);

}
