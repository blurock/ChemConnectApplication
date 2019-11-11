package info.esblurock.reaction.core.ontology.base.generation;

import java.util.Set;

public class GenerateInterpretClassEnumeration {

	public static String generation(String structure, String superclass, String packagename,
			GeneratedClassObjects generated) {
		StringBuilder build = new StringBuilder();
		Set<String> lst = BasicConceptParsing.completeConceptListWithRecordsAndSuperClass(structure);

		String packageline = "package " + packagename + ";\n";
		build.append(packageline);

		String importlines = "import java.io.IOException;\n" + "import java.util.Map;\n" + "\n";
		build.append(importlines);

		for (String name : lst) {
			String classpackagename = generated.getPackageName(name);
			String importclasslines = "   import ";
			if (classpackagename == null) {
				importclasslines += packagename + "." + name + "\n";
			} else {
				importclasslines += classpackagename + "." + name + "\n";
			}
			build.append(importclasslines);
		}
		build.append("\n");

		String classtop = "public enum InterpretBaseData implements InterpretDataInterface {\n";
		build.append(classtop);

		boolean notfirst = false;
		for (String name : lst) {
			String classpackagename = generated.getPackageName(name);
			if (classpackagename == null) {
				if (notfirst) {
					build.append(", ");
				} else {
					notfirst = true;
				}
				StandardInformation info = BasicConceptParsing.findStandardInformation(name);
				StandardInformationGeneration gen = new StandardInformationGeneration(info, generated);
				build.append(gen.generation());
			}
			build.append("\n}\n");
		}

		return build.toString();
	}
}
