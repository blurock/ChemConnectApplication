package info.esblurock.ChemConnectGeneratedArtifacts;

import java.io.IOException;

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
		GeneratedClassObjects generated;
		try {
			System.out.println("GenerateChemConnectModules");
			generated = GenerateChemConnectModules.generate(rootDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
