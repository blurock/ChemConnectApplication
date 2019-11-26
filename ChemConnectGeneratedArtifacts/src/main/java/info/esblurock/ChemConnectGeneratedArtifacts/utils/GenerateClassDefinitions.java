package info.esblurock.ChemConnectGeneratedArtifacts.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;


/** This generates the class definition files of the subclasses of the main structure
 * 
 * generation
 *  <ul>
 *  <li> The set of classes and their records
 *  <li> Write the class definition to a file
 *  </ul>
 *  
 * @author edwardblurock
 *
 */
public class GenerateClassDefinitions {

	/** Write all the classes to the root directory
	 * 
	 * <ul>
	 * <li> The set of classes and their records
	 * <li> Write the class definition to a file
	 * </ul>
	 * 
	 * @param structure The top class of the set of classes to define
	 * @param rootDir The root directory where the classes should be defined
	 * @param packagename The base package name
	 * @param generated The map of classes to  package name
	 */
	public static void generation(String structure,
			String rootDir,
			String packagename,
			GeneratedClassObjects generated) {
		/*
		
		Set<String> lst = BasicConceptParsing.completeConceptListWithRecordsAndSuperClass(structure);		
		for (String name : lst) {
			if (generated.getPackageName(name) == null) {
				String classS = GenerateSingleClassDefinition.generation(name, packagename, generated);
		        String rootnameS = ChemConnectCompoundDataStructure.removeNamespace(name);
		        String filename = rootnameS + ".java";				
				try {
					WriteModuleFileToSystem.writeUsingOutputStream(rootDir, packagename, filename, classS);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		*/
	}
	

}
