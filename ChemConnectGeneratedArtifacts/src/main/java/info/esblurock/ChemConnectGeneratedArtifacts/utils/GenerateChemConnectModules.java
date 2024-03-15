package info.esblurock.ChemConnectGeneratedArtifacts.utils;

import java.io.IOException;
import java.util.ArrayList;

public class GenerateChemConnectModules {
	
	public static GeneratedClassObjects generate(String rootDir) throws IOException {
		
		GeneratedClassObjects basePackageNames = new GeneratedClassObjects();
		basePackageNames.addClassAndPackage(
				"dataset:ChemConnectCompoundDataStructure", 
				"info.esblurock.reaction.chemconnect.data");
		basePackageNames.addClassAndPackage(
				"dataset:ActivityInformationRecord", 
				"info.esblurock.reaction.chemconnect.data");
		basePackageNames.addClassAndPackage(
				"dataset:SimpleCatalogObject", 
				"info.esblurock.reaction.chemconnect.data");
		basePackageNames.addClassAndPackage(
				"dataset:ChemConnectDataStructure", 
				"info.esblurock.reaction.chemconnect.data.structure");
		basePackageNames.addClassAndPackage(
				"dataset:TransactionEvent", 
				"info.esblurock.reaction.chemconnect.data");
		basePackageNames.addClassAndPackage(
				"dataset:ChemConnectCompoundMultiple", 
				"info.esblurock.reaction.chemconnect.data");
		
		ArrayList<String> topconcepts = new ArrayList<String>();
		topconcepts.add("dataset:ActivityInformationRecord");
		topconcepts.add("dataset:ChemConnectCompoundDataStructure");
		topconcepts.add("dataset:SimpleCatalogObject");
		topconcepts.add("dataset:TransactionEvent");
		
		
		String module = "dataset:ChemConnectBaseModule";
		System.out.println("GeneratedClassObjects generate:" + module);
		GeneratedClassObjects basegen = GenerateWholeModule.generate(rootDir, module, topconcepts, basePackageNames);
		System.out.println(basegen);
		/*
		module = "dataset:ChemConnectExpDataModule";
		System.out.println("GeneratedClassObjects generate:" + module);
		GeneratedClassObjects expgen = GenerateWholeModule.generate(rootDir, module, topconcepts, basegen);
		System.out.println(expgen);
	*/
		
		
		return basegen;
		//return basegen;
	}

}
