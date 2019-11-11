package info.esblurock.reaction.core.ontology.base.generation;

import java.util.HashMap;
import java.util.Map;

public class GeneratedClassObjects {
	
	Map<String,String> packageNameCorrespondence;
	
	public GeneratedClassObjects() {
		packageNameCorrespondence = new HashMap<String,String>();
	}
	
	public String getPackageName(String className) {
		return packageNameCorrespondence.get(className);
	}
	
	public void addClassAndPackage(String className, String packageName) {
		packageNameCorrespondence.put(className,packageName);
	}
}
