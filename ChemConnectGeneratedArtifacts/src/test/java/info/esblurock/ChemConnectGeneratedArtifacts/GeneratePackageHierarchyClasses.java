package info.esblurock.ChemConnectGeneratedArtifacts;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import info.esblurock.ChemConnectGeneratedArtifacts.utils.GenerateObjectHierarchy;
import info.esblurock.ChemConnectGeneratedArtifacts.utils.GeneratePackageInformation;
import info.esblurock.ChemConnectGeneratedArtifacts.utils.GeneratedClassObjects;

public class GeneratePackageHierarchyClasses {

	@Test
	public void test() {
		
		String concept = "dataset:ChemConnectCompoundDataStructure";
		String module = "dataset:ChemConnectExpDataModule";
		String rootDir = "/Users/edwardblurock/ChemConnectGenerated";

		GeneratedClassObjects basePackageNames = new GeneratedClassObjects();
		basePackageNames.addClassAndPackage("dataset:ChemConnectCompoundDataStructure", "info.esblurock.reaction.chemconnect.data");

		try {
			GenerateObjectHierarchy.generate(concept, module, rootDir,basePackageNames);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	}

}
