package info.esblurock.reaction.chemconnect.core.client.catalog;


import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetGenericHeader;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;

public enum SetUpCollapsibleItemProtocolDevice {
	SubSystemDescription {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetSubSystemHeader header = new StandardDatasetSubSystemHeader(item);
			item.addHeader(header);
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}
		
	}, , ObservationSpecification {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetObservationSpecificationHeader header = new StandardDatasetObservationSpecificationHeader(item);
			item.addHeader(header);
			
		}

		@Override
		public int priority() {
			return 10;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}
		
	}, ObservationCorrespondenceSpecification {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetObservationCorrespondenceSpecificationHeader header = new StandardDatasetObservationCorrespondenceSpecificationHeader(item);
			item.addHeader(header);
			
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetObservationCorrespondenceSpecificationHeader header = (StandardDatasetObservationCorrespondenceSpecificationHeader) item.getHeader();
			header.updateData();
			return true;
		}
		
	}, SpreadSheetInputInformation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			SpreadSheetInputInformationHeader header = new SpreadSheetInputInformationHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
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
			return true;
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
		
	}, MatrixSpecificationCorrespondenceSet {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			MatrixSpecificationCorrespondenceSetHeader header = new MatrixSpecificationCorrespondenceSetHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			MatrixSpecificationCorrespondenceSetHeader header = 
						(MatrixSpecificationCorrespondenceSetHeader) item.getHeader();
			header.updateData();
			return true;
		}

		@Override
		public int priority() {
			// TODO Auto-generated method stub
			return 500;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, MatrixSpecificationCorrespondence {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			MatrixSpecificationCorrespondenceHeader header = new MatrixSpecificationCorrespondenceHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			MatrixSpecificationCorrespondenceHeader header = (MatrixSpecificationCorrespondenceHeader)
					item.getHeader();
			header.updateData();
			return false;
		}

		@Override
		public int priority() {
			return 500;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, DimensionParameterValue {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			ParameterValueHeader header = new ParameterValueHeader(item.getHierarchy());
			item.addHeader(header);
			
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

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			ParameterValueHeader header = (ParameterValueHeader) item.getHeader();
			return header.updateObject();
		}
		
	}, MeasurementParameterValue {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			ParameterValueHeader header = new ParameterValueHeader(item.getHierarchy());
			item.addHeader(header);
			
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
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			PrimitiveParameterValueRow header = (PrimitiveParameterValueRow) item.getHeader();
			return header.updateObject();
		}
		
	}, ParameterSpecification {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetParameterSpecificationHeader header = new StandardDatasetParameterSpecificationHeader(item, true);
			item.addHeader(header);
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetParameterSpecificationHeader header = (StandardDatasetParameterSpecificationHeader) item.getHeader();
			return header.updateData();
		}
		
	}, MeasureParameterSpecification {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetParameterSpecificationHeader header = new StandardDatasetParameterSpecificationHeader(item,true);
			item.addHeader(header);
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetParameterSpecificationHeader header = (StandardDatasetParameterSpecificationHeader) item.getHeader();
			return header.updateData();
		}
		
	}, DimensionParameterSpecification {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetParameterSpecificationHeader header = new StandardDatasetParameterSpecificationHeader(item,false);
			item.addHeader(header);
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetParameterSpecificationHeader header = (StandardDatasetParameterSpecificationHeader) item.getHeader();
			return header.updateData();
		}
		
	}, ValueUnits {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetValueUnitsHeader header = new StandardDatasetValueUnitsHeader(item.getObject());
			item.addHeader(header);
			
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetValueUnitsHeader header = (StandardDatasetValueUnitsHeader) item.getHeader();
			header.updateValues();
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
		
	}, ChemConnectProtocol {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetProtocolHeader header = new StandardDatasetProtocolHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetProtocolHeader header = (StandardDatasetProtocolHeader) item.getHeader();
			return header.updateProtocol();
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
		
	}, ParameterValue {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			ParameterValueHeader header = new ParameterValueHeader(item.getHierarchy());
			item.addHeader(header);
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

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			PrimitiveParameterValueRow header = (PrimitiveParameterValueRow) item.getHeader();
			return header.updateObject();
		}
		
	};
	
	public abstract void addInformation(StandardDatasetObjectHierarchyItem item);
	public abstract boolean update(StandardDatasetObjectHierarchyItem item);
	public abstract int priority();
	public abstract boolean isInformation();
	public abstract boolean addSubitems();
	public static void addGenericInformation(DatabaseObject object,
			StandardDatasetObjectHierarchyItem item) {
		StandardDatasetGenericHeader header = new StandardDatasetGenericHeader(object.getClass().getSimpleName());
		item.addHeader(header);
		
	}

}
