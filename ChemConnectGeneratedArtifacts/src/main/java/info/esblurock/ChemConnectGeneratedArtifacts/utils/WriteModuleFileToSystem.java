package info.esblurock.ChemConnectGeneratedArtifacts.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

/** This class are the base routines for writing a file into a module
 * 
 * @author edwardblurock
 *
 */
public class WriteModuleFileToSystem {
	
	/**
	 * @param rootDir The root directory to write the module file
	 * @param module The name of the module
	 * @param packagename The package name within the module
	 * @param filename The filename to write
	 * @param data The contents of the file
	 * @throws IOException Problems writing the file
	 */
	public static void writeModuleFile(String rootDir, String module, String packagename, String filename, String data) throws IOException {
		String moduleRootDirS = generateModulePath(rootDir,module);
		String packageRootS = generateAbsolutePackageRoot(moduleRootDirS);
	  	String packageS = convertPackageToDirectory(packagename);
	  	File rootDirF = new File(packageRootS, packageS);
	  	String rootDirS = rootDirF.toString();
		writeUsingOutputStream(rootDirS, filename, data);
	}

    /**
     * @param rootDir 
     * @param packageS
     * @param structure
     * @param data
     * @throws IOException
     */
    public static void writeUsingOutputStream(String rootDir, String filename, String data) throws IOException {

        Path pathP = Paths.get(rootDir);
        
        System.out.println("writeUsingOutputStream\n" + pathP.toString());
        
        if (!Files.exists(pathP)) {
            System.out.println("Directory to be created");
            Files.createDirectories(pathP);
            System.out.println("Directory created");
        } else {
        	System.out.println("Directory exists");
        }

        System.out.println("writeUsingOutputStream\n" + pathP.toString());
                
        String fileS =  rootDir + "/" + filename;
        File fileF = new File(fileS);
        System.out.println(fileF.toString());
        
        OutputStream os = null;
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
	   public static String generateModulePath(String rootDir, String module) {
			File moduleRootDirF = new File(rootDir,ChemConnectCompoundDataStructure.removeNamespace(module));
			String moduleRootDirS = moduleRootDirF.toString();
		   return moduleRootDirS;
	   }
	   
	   public static String generateAbsolutePackageRoot(String moduleRootDirS) {
		   File packageRootDirF = new File(moduleRootDirS,"src/main/java");
		   return packageRootDirF.toString();
	   }
	   
	   public static String convertPackageToDirectory(String packageS) {
		   String dirS = "";
		   int index = packageS.indexOf(".");
		   int beginIndex = 0;
		   while(index >= 0) {
			   String subdirS = packageS.substring(beginIndex,index);
			   File fulldirF = new File(dirS,subdirS);
			   dirS = fulldirF.toString();
			   packageS = packageS.substring(index+1);
			   index = packageS.indexOf(".");
		   }
		   File fulldirF = new File(dirS,packageS);
		   dirS = fulldirF.toString();
		   
		   return dirS;
	   }

}
