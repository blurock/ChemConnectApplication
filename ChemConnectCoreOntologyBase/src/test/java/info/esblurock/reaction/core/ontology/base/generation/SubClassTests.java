package info.esblurock.reaction.core.ontology.base.generation;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class SubClassTests {

	@Test
	public void test() {
		String structure = "dataset:ObservationMatrixValues";
		List<String> concepts = BasicConceptParsing.getAllSubElementsInclusive(structure);
		Set<String> lst = new HashSet<String>();
		System.out.println("BasicConceptParsing.getAllSubElementsInclusive\n" + concepts);
		BasicConceptParsing.completeConceptListWithRecordsAndSuperClass(concepts, lst);
		System.out.println("BasicConceptParsing.getAllSubElementsInclusive\n" + lst);
	}

}
