package info.esblurock.reaction.core.ontology.base;

//import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.data.dataset.ClassificationInformation;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;

public class ClassInformationFromType {

	@Test
	public void test() {
		String type = "SubSystemDescription";
		String structure = DatasetOntologyParseBase.getDataTypeFromType(type);
		System.out.println(type + "  Structure: " + structure);
		
		
		ClassificationInformation info = DatasetOntologyParseBase.getClassificationInformationFromType(type);
		System.out.println("From " + type + "\n" + info.toString());
	}

}
