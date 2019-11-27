package info.esblurock.ChemConnectGeneratedArtifacts.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GeneratedClassObjects {
	
	Map<String,String> packageNameCorrespondence;
	
	public GeneratedClassObjects() {
		packageNameCorrespondence = new HashMap<String,String>();
	}
	
	public String getPackageName(String className) {
		return packageNameCorrespondence.get(className);
	}
	
	public void mergeCorrespondences(GeneratedClassObjects generated) {
		for(String name : generated.getClassNames()) {
			String packagename = generated.getPackageName(name);
			addClassAndPackage(name,packagename);
		}
	}
	
	public void addClassAndPackage(String className, String packageName) {
		packageNameCorrespondence.put(className,packageName);
	}
	public Set<String> getClassNames() {
		return packageNameCorrespondence.keySet();
	}
	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		for(String name : packageNameCorrespondence.keySet()) {
			build.append(prefix + name + ":\t\t " + packageNameCorrespondence.get(name) + "\n");
		}
		return build.toString();
	}
	
}
