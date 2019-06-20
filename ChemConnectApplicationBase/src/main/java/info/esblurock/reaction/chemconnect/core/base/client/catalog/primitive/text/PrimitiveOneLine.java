package info.esblurock.reaction.chemconnect.core.base.client.catalog.primitive.text;

import info.esblurock.reaction.chemconnect.core.base.client.catalog.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.base.transfer.PrimitiveDataStructureInformation;

public class PrimitiveOneLine extends PrimitiveDataStructureBase {
	
	PrimitiveOneLineRow row;
	
	public PrimitiveOneLine() {
		row = new PrimitiveOneLineRow();
		add(row);
	}
	public PrimitiveOneLine(PrimitiveDataStructureInformation info) {
		row = new PrimitiveOneLineRow(info);
		add(row);
	}
	@Override
	public void fill(PrimitiveDataStructureInformation info) {
		row.fill(info);
	}
	
}
