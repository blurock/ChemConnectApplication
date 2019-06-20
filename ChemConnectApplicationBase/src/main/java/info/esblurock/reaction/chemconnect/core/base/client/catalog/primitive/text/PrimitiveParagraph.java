package info.esblurock.reaction.chemconnect.core.base.client.catalog.primitive.text;

import info.esblurock.reaction.chemconnect.core.base.client.catalog.primitive.PrimitiveDataStructureBase;
import info.esblurock.reaction.chemconnect.core.base.transfer.PrimitiveDataStructureInformation;

public class PrimitiveParagraph  extends PrimitiveDataStructureBase {
	PrimitiveParagraphRow row;
	
	public PrimitiveParagraph() {
		row = new PrimitiveParagraphRow();
		add(row);
	}
	public PrimitiveParagraph(PrimitiveDataStructureInformation info) {
		row = new PrimitiveParagraphRow(info);
		add(row);
	}
	
	@Override
	public void fill(PrimitiveDataStructureInformation info) {
		row.fill(info);
	}

}
