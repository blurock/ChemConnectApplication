package info.esblurock.ChemConnectGeneratedArtifacts.utils;

import java.io.IOException;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

public class GenerateObjectHierarchy {

	public static void generate(String concept, String module, String rootDir,
			GeneratedClassObjects generated) throws IOException {
		
		GeneratedClassObjects newgenerated = GeneratePackageInformation.generatePackage(concept,module,generated);

		for(String classname : newgenerated.getClassNames()) {
			String packageS = newgenerated.getPackageName(classname);
			String classS = GenerateSingleClassDefinition.generation(classname, packageS, generated);
			String structure = ChemConnectCompoundDataStructure.removeNamespace(classname);
			String javaname = structure + ".java";
			WriteModuleFileToSystem.writeModuleFile(rootDir, module, packageS, javaname, classS);
		}
	}
	   
}
