package info.esblurock.reaction.chemconnect.expdata.client.observations.spreadsheet;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadResult;
import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.core.data.expdata.data.observations.ObservationValueRow;

public class SpreadSheetRowsCallback implements AsyncCallback<ArrayList<ObservationValueRow>>{

	LoadCallback<ObservationValueRow> callback;
	int start;
	int total;
	
	public SpreadSheetRowsCallback(LoadCallback<ObservationValueRow> callback, int start, int total) {
		this.callback = callback;
		this.start = start;
		this.total = total;
		MaterialLoader.loading(true);
		}
	@Override
	public void onFailure(Throwable caught) {
		MaterialLoader.loading(false);
		GWT.log("Getting people async call failed.", caught);
        callback.onFailure(caught);
	}

	@Override
	public void onSuccess(ArrayList<ObservationValueRow> result) {
		MaterialLoader.loading(false);
		callback.onSuccess(new LoadResult<ObservationValueRow>(result, start, total));
	}

}
