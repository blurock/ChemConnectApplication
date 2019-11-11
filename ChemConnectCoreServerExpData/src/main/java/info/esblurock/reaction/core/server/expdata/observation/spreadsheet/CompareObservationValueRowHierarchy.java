package info.esblurock.reaction.core.server.expdata.observation.spreadsheet;

import java.util.Comparator;

import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.data.expdata.data.observations.ObservationValueRow;

public class CompareObservationValueRowHierarchy implements Comparator<DatabaseObjectHierarchy> {
	
	public CompareObservationValueRowHierarchy() {
	}

	public int compare(DatabaseObjectHierarchy o1, DatabaseObjectHierarchy o2) {
		ObservationValueRow row1 = (ObservationValueRow) o1.getObject();
		ObservationValueRow row2 = (ObservationValueRow) o2.getObject();
		return row1.compareTo(row2);
	}

}
