package info.esblurock.reaction.chemconnect.core.client.pages.primitive;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.primitive.ClassificationPrimitiveDataStructure;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.primitive.PrimitiveObservationValuesWithSpecification;
import info.esblurock.reaction.chemconnect.core.base.transfer.PrimitiveDataStructureInformation;

public enum CreatePrimitiveStructureProtocolDevice {
	
	ObservationValuesWithSpecification {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveObservationValuesWithSpecification spec = new PrimitiveObservationValuesWithSpecification(info);
			return spec;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveObservationValuesWithSpecification spec = new PrimitiveObservationValuesWithSpecification();
			return spec;
		}

		@Override
		public String getStructureName() {
			return "ObservationVauesWithSpecification";
		}

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
		}
		
	}, datasetClassification {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			ClassificationPrimitiveDataStructure base = new ClassificationPrimitiveDataStructure(info);
			return base;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			ClassificationPrimitiveDataStructure base = new ClassificationPrimitiveDataStructure();
			return base;
		}

		@Override
		public String getStructureName() {
			return "datasetClassification";
		}

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
		}
	};
	
	/*	
	ParameterSpecification {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			PrimitiveParameterSpecification spec = new PrimitiveParameterSpecification(info);
			return spec;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			PrimitiveParameterSpecificationInformation info = new PrimitiveParameterSpecificationInformation();
			PrimitiveParameterSpecification spec = new PrimitiveParameterSpecification(info);
			return spec;
		}

		@Override
		public String getStructureName() {
			return "ParameterSpecification";
		}

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
		}


	}, SetOfObservationsSpecification {

		@Override
		public PrimitiveDataStructureBase createStructure(PrimitiveDataStructureInformation info) {
			SetOfObservationsInformation obs = (SetOfObservationsInformation) info;
			SetOfObservationsField set = new SetOfObservationsField(obs);
			return set;
		}

		@Override
		public PrimitiveDataStructureBase createEmptyStructure() {
			SetOfObservationsInformation obs = new SetOfObservationsInformation();
			SetOfObservationsField set = new SetOfObservationsField(obs);
			return set;
		}

		@Override
		public String getStructureName() {
			return "SetOfObservationsSpecification";
		}

		@Override
		public void updateStructure(DatabaseObject object, PrimitiveDataStructureBase base) {
			// TODO Auto-generated method stub
			
		}
*/
}
