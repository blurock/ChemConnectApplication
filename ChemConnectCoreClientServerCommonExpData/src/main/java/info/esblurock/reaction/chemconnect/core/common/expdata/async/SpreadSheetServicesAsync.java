package info.esblurock.reaction.chemconnect.core.common.expdata.async;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.core.data.expdata.data.observations.ObservationValueRow;
import info.esblurock.reaction.core.data.expdata.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.core.data.expdata.data.observations.SpreadSheetInputInformation;

public interface SpreadSheetServicesAsync {
	void getSpreadSheetRows(String parent, int start, int limit,
			AsyncCallback<ArrayList<ObservationValueRow>> callback);

	void interpretSpreadSheet(SpreadSheetInputInformation input, DataCatalogID catid, boolean writeObjects,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void interpretSpreadSheetGCS(GCSBlobFileInformation gcsinfo, SpreadSheetInputInformation input, DataCatalogID catid,
			boolean writeObject, AsyncCallback<DatabaseObjectHierarchy> callback);

	void deleteSpreadSheetTransaction(String filename, AsyncCallback<Void> callback);

	void isolateFromMatrix(DataCatalogID catid, DatabaseObjectHierarchy matrixhier,
			SpreadSheetBlockIsolation blockisolate, AsyncCallback<DatabaseObjectHierarchy> callback);
}
