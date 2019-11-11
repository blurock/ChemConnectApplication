package info.esblurock.reaction.core.ontology.base.generation;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Set;

import com.google.gwt.dev.Link.LinkOptions;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;


public class GenerateClassDefinitions {

	public static void generation(String structure,
			String rootDir,
			String packagename,
			GeneratedClassObjects generated) {
		
		Set<String> lst = BasicConceptParsing.completeConceptListWithRecordsAndSuperClass(structure);		
		for (String name : lst) {
			if (generated.getPackageName(name) == null) {
				String classS = GenerateSingleClassDefinition.generation(name, packagename, generated);
				
				try {
					writeUsingOutputStream(rootDir, packagename, name, classS);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				System.out.println("---------------------------------------------");
				System.out.println(classS);
				System.out.println("---------------------------------------------");
			}
		}
	}
	
    private static void writeUsingOutputStream(String rootDir, String packageS, String structure, String data) throws IOException {
        OutputStream os = null;
        
        String dirS = packageS.replace(".", "/");
        String pathS = rootDir + "/" + dirS;
        Path pathP = Paths.get(pathS);
        
        System.out.println("writeUsingOutputStream\n" + pathP.toString());
        
        if (!Files.exists(pathP)) {
            System.out.println("Directory to be created");
            Files.createDirectories(pathP);
            System.out.println("Directory created");
        } else {
        	System.out.println("Directory exists");
        }

        System.out.println("writeUsingOutputStream\n" + pathP.toString());
        
        String name = ChemConnectCompoundDataStructure.removeNamespace(structure);
        String fileS =  pathS + "/" + name + ".java";
        File fileF = new File(fileS);
        
        System.out.println(fileF.toString());
        
        try {
            os = new FileOutputStream(fileF);
            os.write(data.getBytes(), 0, data.length());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
