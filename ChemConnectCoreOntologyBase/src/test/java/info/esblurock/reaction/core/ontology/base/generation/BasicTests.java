package info.esblurock.reaction.core.ontology.base.generation;

//import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.core.base.concepts.StandardConceptAnnotations;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

public class BasicTests {

	@Test
	public void test() {
		System.out.println("-----------------------------------------------------------");
		String structure = "dataset:ActivityInformationRecord";
		
		/*
		List<String> lst = BasicConceptParsing.getAllSubElementsInclusive(structure);
		for(String name : lst) {
			String superclass = BasicConceptParsing.findSuperClass(name);
			System.out.println("Super class of " + name + " is " + superclass);			
		}
		
		StringBuilder missing = new StringBuilder();
		for(String name : lst) {
			StandardConceptAnnotations annotations = BasicConceptParsing.getAnnotations(name);
			if(annotations == null) {
				missing.append(name + "\n");
			} else {
				System.out.println(name);
				System.out.println(annotations.toString());
			}
			
			List<String> singleHasPart = BasicConceptParsing.subObjectsOfConcept(name, "<http://purl.org/dc/terms/hasPart>", false);
			List<String> multipleHasPart = BasicConceptParsing.subObjectsOfConcept(name, "<http://purl.org/dc/terms/hasPart>", true);
			List<String> singleRecord = BasicConceptParsing.subObjectsOfConcept(name, "<http://www.w3.org/ns/dcat#record>", false);
			List<String> multipleRecord = BasicConceptParsing.subObjectsOfConcept(name, "<http://www.w3.org/ns/dcat#record>", true);
			
			System.out.println("Single HasPart      : " + singleHasPart + "\n");
			System.out.println("Multiple HasPart    : " + multipleHasPart + "\n");
			System.out.println("Single Record       : " + singleRecord + "\n");
			System.out.println("Multiple Record     : " + multipleRecord + "\n");
			*/
/*
			System.out.println("-----------------------------------------------------------");
			StandardInformation info = BasicConceptParsing.findStandardInformation(name);
			StandardInformationGeneration gen = new StandardInformationGeneration(info);
			System.out.println(gen.generation());
			System.out.println("-----------------------------------------------------------");
			
		}
		*/
		//System.out.println("Missing: \n" + missing.toString());

		GeneratedClassObjects generated = new GeneratedClassObjects();
		generated.addClassAndPackage("ChemConnectCompoundDataStructure", 
				"info.esblurock.reaction.chemconnect.core.base.dataset");
		generated.addClassAndPackage("DatabaseObject", 
				"info.esblurock.reaction.chemconnect.core.base");
		System.out.println(GenerateInterpretClassEnumeration.generation(structure, 
				"BaseDataObject", 
				"info.esblurock.reaction.chemconnect.core.base",
				generated));
		
		StringBuilder missing = new StringBuilder();
		List<String> lst = BasicConceptParsing.getAllSubElementsInclusive(structure);
		for(String name : lst) {
			StandardConceptAnnotations annotations = BasicConceptParsing.getAnnotations(name);
			if(annotations == null) {
				missing.append(name + "\n");
			} else {
				System.out.println(name);
				System.out.println(annotations.toString());
			}
		}
		System.out.println("Missing: \n" + missing.toString());
		

		
		/*
		generated = new GeneratedClassObjects();
		generated.addClassAndPackage("ChemConnectCompoundDataStructure", 
				"info.esblurock.reaction.chemconnect.core.base.dataset");
		
		
		System.out.println(GenerateInterpretClassEnumeration.generation(example1, 
				"ChemConnectCompoundDataStructure", 
				"info.esblurock.reaction.chemconnect.core.base",
				generated));
				
		generated = new GeneratedClassObjects();
		generated.addClassAndPackage("dataset:ChemConnectCompoundDataStructure", 
				"info.esblurock.reaction.chemconnect.core.base.dataset");
		*/
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
