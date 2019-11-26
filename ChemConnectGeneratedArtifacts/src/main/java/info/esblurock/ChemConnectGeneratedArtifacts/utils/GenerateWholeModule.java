package info.esblurock.ChemConnectGeneratedArtifacts.utils;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

public class GenerateWholeModule {

	public static GeneratedClassObjects generate(String rootDir, 
			String module, 
			ArrayList<String> topconcepts,
			GeneratedClassObjects alreadygenerated) throws IOException {
		generateModulePom(rootDir,module);
		generateGWTModule(rootDir,module);
		for(String topconcept : topconcepts) {
			GeneratedClassObjects generated = GeneratePackageInformation.generatePackage(topconcept, 
				module, alreadygenerated);
			GenerateObjectHierarchy.generate(topconcept, module, rootDir,alreadygenerated);
			alreadygenerated.mergeCorrespondences(generated);
		}
		return alreadygenerated;
	}
	
	public static void generateModulePom(String rootDir, String module) {
		String gwtS;
		try {
			gwtS = GeneratePackageInformation.generateGWTFile(module);
			String filename = "pom.xml";
			String moduleRoot = WriteModuleFileToSystem.generateModulePath(rootDir, module);
			WriteModuleFileToSystem.writeUsingOutputStream(moduleRoot, filename, gwtS);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void generateGWTModule(String rootDir, String module) {
		String pomS;
		try {
			pomS = GeneratePackageInformation.generateGWTFile(module);
			String filename = ChemConnectCompoundDataStructure.removeNamespace(module) + ".gwt.xml";
			WriteModuleFileToSystem.writeModuleFile(rootDir, module, "", filename, pomS);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
