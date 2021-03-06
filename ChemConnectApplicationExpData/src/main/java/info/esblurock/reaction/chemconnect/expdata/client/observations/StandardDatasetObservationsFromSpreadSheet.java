package info.esblurock.reaction.chemconnect.expdata.client.observations;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SaveDatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.data.expdata.data.observations.ObservationValueRowTitle;
import info.esblurock.reaction.core.data.expdata.data.observations.ObservationsFromSpreadSheet;

public class StandardDatasetObservationsFromSpreadSheet extends Composite {

	private static StandardDatasetObservationsFromSpreadSheetUiBinder uiBinder = GWT
			.create(StandardDatasetObservationsFromSpreadSheetUiBinder.class);

	interface StandardDatasetObservationsFromSpreadSheetUiBinder
			extends UiBinder<Widget, StandardDatasetObservationsFromSpreadSheet> {
	}

	public StandardDatasetObservationsFromSpreadSheet() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink title;
	@UiField
	MaterialTooltip titletooltip;
	
	ObservationsFromSpreadSheet observation;
	DatabaseObjectHierarchy hierarchy;
	StandardDatasetObjectHierarchyItem item;
	
	public StandardDatasetObservationsFromSpreadSheet(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.item = item;
		hierarchy = item.getHierarchy();
		observation = (ObservationsFromSpreadSheet) item.getObject();
		
		String id = observation.getCatalogDataID();
		DatabaseObjectHierarchy catidhierarchy = hierarchy.getSubObject(id);
		DataCatalogID catid = (DataCatalogID) catidhierarchy.getObject();
		
		titletooltip.setText(catid.getFullName());
		title.setText(catid.getSimpleCatalogName());	
		
	}
	public void addTitles() {
		String matrixid = observation.getObservationMatrixValues();
		StandardDatasetObjectHierarchyItem matrixitem= item.getItemFromID(matrixid);
		if(matrixitem !=  null) {
			SpreadSheetBlockMatrix header = (SpreadSheetBlockMatrix) matrixitem.getHeader();
			DatabaseObjectHierarchy valueshierarchy = hierarchy.getSubObject(observation.getObservationMatrixValues());
			DatabaseObjectHierarchy titleshierarchy = hierarchy.getSubObject(observation.getObservationValueRowTitle());
			ObservationValueRowTitle titles = (ObservationValueRowTitle) titleshierarchy.getObject();
			header.setupTableFromObservationMatrixValuesWithTitles(valueshierarchy, titles);
		} else {
			StandardWindowVisualization.errorWindowMessage("StandardDatasetObservationsFromSpreadSheet titles not found\n", 
					matrixid);
		}
		
	}
	@UiHandler("save")
	public void clickSave(ClickEvent event) {
		SaveDatasetCatalogHierarchy savemodal = new SaveDatasetCatalogHierarchy(item);
		item.getModalpanel().clear();
		item.getModalpanel().add(savemodal);
		savemodal.openModal();
	}

}
