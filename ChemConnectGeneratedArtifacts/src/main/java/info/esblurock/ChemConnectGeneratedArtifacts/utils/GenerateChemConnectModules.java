package info.esblurock.ChemConnectGeneratedArtifacts.utils;

import java.io.IOException;
import java.util.ArrayList;

public class GenerateChemConnectModules {
	
	public static GeneratedClassObjects generate(String rootDir) throws IOException {
		
		GeneratedClassObjects basePackageNames = new GeneratedClassObjects();
		basePackageNames.addClassAndPackage(
				"dataset:ChemConnectCompoundDataStructure", 
				"info.esblurock.reaction.chemconnect.data");
		basePackageNames.addClassAndPackage("ActivityInformationRecord", "info.esblurock.reaction.chemconnect.data");
		
		ArrayList<String> topconcepts = new ArrayList<String>();
		topconcepts.add("dataset:ActivityInformationRecord");
		topconcepts.add("dataset:ChemConnectCompoundDataStructure");
		topconcepts.add("dataset:SimpleCatalogObject");
		
		String module = "dataset:ChemConnectBaseModule";
		GeneratedClassObjects basegen = GenerateWholeModule.generate(rootDir, module, topconcepts, basePackageNames);
		
		
		module = "dataset:ChemConnectExpDataModule";
		GeneratedClassObjects expgen = GenerateWholeModule.generate(rootDir, module, topconcepts, basegen);
		
		System.out.println(expgen);
		
		return expgen;
	}

}
