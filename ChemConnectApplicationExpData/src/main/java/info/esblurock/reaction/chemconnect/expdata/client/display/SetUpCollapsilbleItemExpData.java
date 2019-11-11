package info.esblurock.reaction.chemconnect.expdata.client.display;

import info.esblurock.reaction.chemconnect.core.base.client.catalog.SetUpCollapsibleItemInterface;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.expdata.client.observations.ObservationBlockFromSpreadSheetHeader;
import info.esblurock.reaction.chemconnect.expdata.client.observations.SpreadSheetBlockMatrix;
import info.esblurock.reaction.chemconnect.expdata.client.observations.SpreadSheetMatrixBlockIsolateHeader;
import info.esblurock.reaction.chemconnect.expdata.client.observations.StandardDatasetObservationValueRowTitle;
import info.esblurock.reaction.chemconnect.expdata.client.observations.StandardDatasetObservationsFromSpreadSheet;
import info.esblurock.reaction.chemconnect.expdata.client.observations.StandardDatasetObservationsFromSpreadSheetFull;

public enum SetUpCollapsilbleItemExpData implements SetUpCollapsibleItemInterface {
	
	ObservationBlockFromSpreadSheet {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			ObservationBlockFromSpreadSheetHeader header = new ObservationBlockFromSpreadSheetHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			ObservationBlockFromSpreadSheetHeader header = (ObservationBlockFromSpreadSheetHeader) item.getHeader();
			return header.updateData();
		}

		@Override
		public int priority() {
			return 500;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}
		
	}, SpreadSheetBlockIsolation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			SpreadSheetMatrixBlockIsolateHeader header = new SpreadSheetMatrixBlockIsolateHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return false;
		}

		@Override
		public int priority() {
			return 400;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, ObservationsFromSpreadSheet  {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetObservationsFromSpreadSheet header = new StandardDatasetObservationsFromSpreadSheet(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}

		@Override
		public int priority() {
			return 800;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}
		
	}, ObservationsFromSpreadSheetFull  {
		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetObservationsFromSpreadSheetFull header = new StandardDatasetObservationsFromSpreadSheetFull(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}

		@Override
		public int priority() {
			return 800;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}
		
	}, ObservationValueRowTitle {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetObservationValueRowTitle title = new StandardDatasetObservationValueRowTitle(item);
			item.addHeader(title);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return false;
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, ObservationMatrixValues {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			SpreadSheetBlockMatrix header = new SpreadSheetBlockMatrix(item);	
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return false;
		}

		@Override
		public int priority() {
			return 200;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	};
}
