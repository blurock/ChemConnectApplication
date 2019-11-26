package info.esblurock.ChemConnectGeneratedArtifacts;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import info.esblurock.ChemConnectGeneratedArtifacts.utils.GenerateObjectHierarchy;
import info.esblurock.ChemConnectGeneratedArtifacts.utils.GeneratePackageInformation;
import info.esblurock.ChemConnectGeneratedArtifacts.utils.GeneratedClassObjects;
import info.esblurock.reaction.chemconnect.core.base.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.base.utilities.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;

public class GeneratePackageHierrchy {

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
