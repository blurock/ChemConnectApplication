package info.esblurock.reaction.chemconnect.expdata.client.observations.spreadsheet;

import gwt.material.design.client.data.DataSource;
import gwt.material.design.client.data.loader.LoadCallback;
import gwt.material.design.client.data.loader.LoadConfig;
import info.esblurock.reaction.chemconnect.core.common.expdata.async.SpreadSheetServices;
import info.esblurock.reaction.chemconnect.core.common.expdata.async.SpreadSheetServicesAsync;
import info.esblurock.reaction.core.data.expdata.data.observations.ObservationValueRow;

public class SpreadSheetDataSource implements DataSource<ObservationValueRow> {

	String parent;
	int total;
	
	public SpreadSheetDataSource(String parent, int total, SpreadSheetServicesAsync async) {
		this.parent = parent;
		this.total = total;
	}
	
	@Override
	public void load(LoadConfig<ObservationValueRow> loadConfig, LoadCallback<ObservationValueRow> callback) {
		SpreadSheetServicesAsync async = SpreadSheetServices.Util.getInstance();
		SpreadSheetRowsCallback asyncallback = new SpreadSheetRowsCallback(callback,loadConfig.getOffset(),total);
		async.getSpreadSheetRows(parent,loadConfig.getOffset(), loadConfig.getLimit(),
				asyncallback);
	}

	@Override
	public boolean useRemoteSort() {
		return false;
	}

}
