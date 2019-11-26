package info.esblurock.ChemConnectGeneratedArtifacts;


import org.junit.Test;

import info.esblurock.ChemConnectGeneratedArtifacts.utils.GeneratePackageInformation;
import info.esblurock.ChemConnectGeneratedArtifacts.utils.GeneratedClassObjects;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;

public class GeneratePackageHierarchy {

	@Test
	public void test() {
		/*
		String concept = "dataset:SimpleCatalogObject";
		GenerateObjectHierarchy.generate(concept);
		concept = "dataset:ChemConnectCompoundDataStructure";
		GenerateObjectHierarchy.generate(concept);
		concept = "dataset:TransactionEvent";
		GenerateObjectHierarchy.generate(concept);
		*/
		
		System.out.println("--------------------------------------------------------------");
		System.out.println("GenerateObjectHierarchy");
		System.out.println("--------------------------------------------------------------");
		
		String concept = "dataset:ChemConnectCompoundDataStructure";
		String module = "dataset:ChemConnectExpDataModule";
		
		HierarchyNode topnode = DatasetOntologyParseBase.findClassHierarchy(concept);
		System.out.println(module + "  Hierarchy --------------------------------------------------------------");
		System.out.println(topnode);
		System.out.println(module + "  Hierarchy --------------------------------------------------------------");
	
		GeneratedClassObjects basePackageNames = new GeneratedClassObjects();
		basePackageNames.addClassAndPackage("dataset:ChemConnectCompoundDataStructure", "info.esblurock.reaction.chemconnect.data");

		GeneratedClassObjects packageNames = GeneratePackageInformation.generatePackage(concept,module,basePackageNames);
		System.out.println(packageNames.toString(module + " "));

	}
}
