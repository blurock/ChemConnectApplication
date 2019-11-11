package info.esblurock.reaction.core.server.expdata.observation;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.common.expdata.async.ObservationServices;
import info.esblurock.reaction.core.server.base.services.ServerBase;
import info.esblurock.reaction.core.server.expdata.observation.spreadsheet.CreateSpreadSheetObjects;

@SuppressWarnings("serial")
public class ObservationServicesImpl extends ServerBase implements ObservationServices {
	
	public DatabaseObjectHierarchy createObservationBlockFromSpreadSheet(DatabaseObject obj, 
			String blocktype, DataCatalogID datid) {
		DatabaseObjectHierarchy hierarchy = CreateSpreadSheetObjects.fillObservationBlockFromSpreadSheet(obj,
				blocktype,datid);
		return hierarchy;
	}

}
