package info.esblurock.reaction.chemconnect.core.base.client.catalog.primitive.text;

import info.esblurock.reaction.chemconnect.core.base.client.catalog.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.base.transfer.PrimitiveDataStructureInformation;

public class PrimitiveShortString extends PrimitiveDataStructureBase {

	PrimitiveShortStringRow row;
	
	public PrimitiveShortString() {
		row = new PrimitiveShortStringRow();
		add(row);
	}
	public PrimitiveShortString(PrimitiveDataStructureInformation info) {
		row = new PrimitiveShortStringRow(info);
		add(row);
	}
	
	@Override
	public void fill(PrimitiveDataStructureInformation info) {
		row.fill(info);
	}

}
