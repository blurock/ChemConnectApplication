package info.esblurock.reaction.chemconnect.core.base.client.catalog.reference;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.base.transfer.PrimitiveDataStructureInformation;
import info.esblurock.reaction.chemconnect.core.base.transfer.PrimitiveInterpretedInformation;

public class PrimitiveReference extends PrimitiveDataStructureBase {
	PrimitiveReferenceRow row;
	
	public PrimitiveReference() {
		super();
		row = new PrimitiveReferenceRow();
		add(row);
	}
	
	public PrimitiveReference(PrimitiveDataStructureInformation primitiveinfo) {
		super();
		PrimitiveInterpretedInformation info = (PrimitiveInterpretedInformation) primitiveinfo;
		row = new PrimitiveReferenceRow(info);
		add(row);
	}
	@Override
	public void fill(PrimitiveDataStructureInformation primitiveinfo) {
		PrimitiveInterpretedInformation info = (PrimitiveInterpretedInformation) primitiveinfo;
		row.fill(info);
		add(row);
	}

	public String getIdentifier() {
		return row.getIdentifier();
	}
	@Override
	public void setIdentifier(DatabaseObject obj) {
		super.setIdentifier(obj);
		row.setIdentifier(obj);
	}

}
