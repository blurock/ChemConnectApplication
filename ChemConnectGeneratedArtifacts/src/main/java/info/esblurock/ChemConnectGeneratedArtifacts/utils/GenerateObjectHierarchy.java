package info.esblurock.ChemConnectGeneratedArtifacts.utils;

import java.io.IOException;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

public class GenerateObjectHierarchy {

	public static void generate(String concept, String module, String rootDir,
			GeneratedClassObjects generated) throws IOException {
		
		GeneratedClassObjects newgenerated = GeneratePackageInformation.generatePackage(concept,module,generated);
		
		Set<String> classnames = newgenerated.getClassNames();
		newgenerated.mergeCorrespondences(generated);
		
		Set<String> previous = generated.getClassNames();
		
		for(String classname : classnames) {
			if(!previous.contains(classname)) {
				String packageS = newgenerated.getPackageName(classname);
				String classS = GenerateSingleClassDefinition.generation(classname, packageS, newgenerated);
				String structure = ChemConnectCompoundDataStructure.removeNamespace(classname);
				String javaname = structure + ".java";
				WriteModuleFileToSystem.writeModuleFile(rootDir, module, packageS, javaname, classS);
			} else {
				System.out.println("Don't generate: " + classname);
			}
		}
	}
	   
}
