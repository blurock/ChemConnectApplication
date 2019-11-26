package info.esblurock.ChemConnectGeneratedArtifacts;

import info.esblurock.ChemConnectGeneratedArtifacts.utils.GenerateChemConnectModules;
import info.esblurock.ChemConnectGeneratedArtifacts.utils.GeneratedClassObjects;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		String rootDir = "/Users/edwardblurock/ChemConnectGenerated";
		GeneratedClassObjects generated = GenerateChemConnectModules.generate(rootDir);
		
		System.out.println(generated);
    }
}
