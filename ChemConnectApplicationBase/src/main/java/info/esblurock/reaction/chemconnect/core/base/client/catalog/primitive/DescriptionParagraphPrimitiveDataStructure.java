package info.esblurock.reaction.chemconnect.core.base.client.catalog.primitive;

import gwt.material.design.client.ui.MaterialTextArea;
import info.esblurock.reaction.chemconnect.core.base.transfer.PrimitiveDataStructureInformation;

public class DescriptionParagraphPrimitiveDataStructure extends PrimitiveDataStructureBase {

	public DescriptionParagraphPrimitiveDataStructure(PrimitiveDataStructureInformation primitiveinfo) {
		super(primitiveinfo);
		MaterialTextArea paragraph = new MaterialTextArea();
		paragraph.setText(primitiveinfo.getValue());
		
	}
	public DescriptionParagraphPrimitiveDataStructure() {
		super();
		MaterialTextArea paragraph = new MaterialTextArea();
		paragraph.setText("");
		
	}
}
