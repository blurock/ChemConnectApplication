package info.esblurock.ChemConnectGeneratedArtifacts;

import java.io.IOException;

//import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import info.esblurock.ChemConnectGeneratedArtifacts.utils.BasicConceptParsing;
import info.esblurock.ChemConnectGeneratedArtifacts.utils.GeneratePackageInformation;
import info.esblurock.ChemConnectGeneratedArtifacts.utils.GeneratedClassObjects;
import info.esblurock.ChemConnectGeneratedArtifacts.utils.StandardInformation;
import info.esblurock.ChemConnectGeneratedArtifacts.utils.StandardInformationGeneration;
import info.esblurock.reaction.chemconnect.core.base.concepts.StandardConceptAnnotations;

public class BasicTests {

	@Test
	public void test() {
		System.out.println("-----------------------------------------------------------");
		String structure = "dataset:ActivityInformationRecord";
		System.out.println("-----------------------------------------------------------");
		
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
			/*
			StandardInformation info = BasicConceptParsing.findStandardInformation(name);
			StandardInformationGeneration gen = new StandardInformationGeneration(info,packageNames);
			System.out.println(gen.toString(name + "  "));
			*/
		}
				
	}

}
