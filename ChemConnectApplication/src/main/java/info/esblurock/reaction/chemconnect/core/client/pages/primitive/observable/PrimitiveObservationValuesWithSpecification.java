package info.esblurock.reaction.chemconnect.core.client.pages.primitive.observable;

import info.esblurock.reaction.chemconnect.core.data.transfer.ObservationsAndSpecificationsInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.SetOfObservationsInformation;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.base.transfer.PrimitiveDataStructureInformation;

public class PrimitiveObservationValuesWithSpecification extends PrimitiveDataStructureBase {

	PrimitiveObservationVauesWithSpecificationRow row;
	
	public PrimitiveObservationValuesWithSpecification() {
		super();
		row = new PrimitiveObservationVauesWithSpecificationRow();
		add(row);
	}
	
	public PrimitiveObservationValuesWithSpecification(PrimitiveDataStructureInformation info) {
		super();
		SetOfObservationsInformation primitiveinfo = (SetOfObservationsInformation) info;
		row = new PrimitiveObservationVauesWithSpecificationRow(primitiveinfo);
		add(row);
	}

	@Override
	public void fill(PrimitiveDataStructureInformation primitiveinfo) {
		ObservationsAndSpecificationsInformation info = (ObservationsAndSpecificationsInformation) primitiveinfo;
		row.fill(info);
		add(row);
	}

	public String getIdentifier() {
		return row.getIdentifier();
	}
	public void setIdentifier(DatabaseObject obj) {
		super.setIdentifier(obj);
		row.setIdentifier(obj);
	}

}
