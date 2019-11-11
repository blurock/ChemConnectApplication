package info.esblurock.reaction.core.server.base.generation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import info.esblurock.reaction.core.ontology.base.generation.GenerateClassDefinitions;
import info.esblurock.reaction.core.ontology.base.generation.GeneratedClassObjects;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseData;

class ArtifactGeneration {

	@Test
	void test() {
		
		GeneratedClassObjects generated = new GeneratedClassObjects();
		InterpretBaseData[] lst = InterpretBaseData.values();
		for(InterpretBaseData interpret : lst) {
			String canonicalname = interpret.canonicalClassName();
			int pos = canonicalname.lastIndexOf(".");
			String packageName = canonicalname.substring(0,pos-1);
			String className = canonicalname.substring(pos+1);
			System.out.println(packageName + "    " + className);
			generated.addClassAndPackage(className, packageName);
		}
		
		String example1 = "dataset:ActivityInformationRecord";
		GenerateClassDefinitions.generation(example1, 
				"/Users/edwardblurock/ChemConnectWorkspace/ChemConnectGeneratedArtifacts/src/main/java",
				"info.esblurock.reaction.chemconnect.core.base.activity",
				generated);

		
		String example2 = "dataset:ObservationMatrixValues";
		GenerateClassDefinitions.generation(example2, 
				"/Users/edwardblurock/ChemConnectWorkspace/ChemConnectGeneratedArtifacts/src/main/java",
				"info.esblurock.reaction.chemconnect.core.base",
				generated);
	}

}
