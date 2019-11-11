package info.esblurock.reaction.core.ontology.base.generation;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

public class GenerateSingleClassDefinition {
	
	public static String generation(String name, String packagename, GeneratedClassObjects generated) {
		StandardInformation standardinfo = BasicConceptParsing.findStandardInformation(name);
		String datastructure = ChemConnectCompoundDataStructure.removeNamespace(standardinfo.getConceptname());
		String supercls = ChemConnectCompoundDataStructure.removeNamespace(standardinfo.getSuperClass());

		StringBuilder subbuild = new StringBuilder();
		String packageS = "package " + packagename + ";\n";
		subbuild.append(packageS);
		
		String import1 = "import com.googlecode.objectify.annotation.Entity;\n" + 
				"import com.googlecode.objectify.annotation.Index;\n" + 
				"\n";
		subbuild.append(import1);

		if (generated.getPackageName(standardinfo.getSuperClass()) == null) {
			subbuild.append("import " + packagename + "." + supercls + ";\n");
		} else {
			subbuild.append("import " + generated.getPackageName(standardinfo.getSuperClass()) + "." + supercls
					+ ";\n");
		}

		subbuild.append("\n");
		String entityS = "@Entity\n" + "@SuppressWarnings(\"serial\")\n";
		subbuild.append(entityS);
		String classDefS = "public class " + datastructure + " extends " + supercls + " {\n" + "\n";
		subbuild.append(classDefS);

		for (StandardGenerationInterface info : standardinfo.getInformation()) {
			subbuild.append("   @Index\n");
			subbuild.append("   String " + info.retrieveName() + ";\n");
		}
		subbuild.append("\n");

		String emptyconstructor = "   public " + datastructure + "() {\n" + "   }\n" + "\n";
		subbuild.append(emptyconstructor);

		String basicconstructor = "   public " + datastructure + "(" + supercls + " structure) {\n"
				+ "      super(structure);\n" + "   }\n" + "\n";
		subbuild.append(basicconstructor);

		for (StandardGenerationInterface info : standardinfo.getInformation()) {
			String getset = "   public String get" + info.getClassName() + "() {\n" + "         return "
					+ info.retrieveName() + ";\n" + "   }\n" + "   public void set" + info.getClassName()
					+ "(String " + info.retrieveName() + ") {\n" + "      this." + info.retrieveName() + " = "
					+ info.retrieveName() + ";\n" + "   }\n" + "\n";
			subbuild.append(getset);
		}

		subbuild.append("\n}\n");
		
		return subbuild.toString();
	}

}
