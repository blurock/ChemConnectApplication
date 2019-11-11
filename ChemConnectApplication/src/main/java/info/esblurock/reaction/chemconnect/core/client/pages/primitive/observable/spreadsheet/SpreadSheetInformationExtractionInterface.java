package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable.spreadsheet;

import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.data.observations.SpreadSheetTitleRowCorrespondence;
import info.esblurock.reaction.core.data.expdata.observations.ObservationValueRow;
import info.esblurock.reaction.core.data.expdata.observations.SpreadSheetBlockIsolation;

public interface SpreadSheetInformationExtractionInterface {

	public void setCorrespondences(ArrayList<SpreadSheetTitleRowCorrespondence> correspondences);

	public void setIsolatedMatrix(ArrayList<ObservationValueRow> matrix);

	public void setMatrixInterpretation(SpreadSheetBlockIsolation interpretation);

}
