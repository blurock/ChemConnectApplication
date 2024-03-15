package info.esblurock.ChemConnectGeneratedArtifacts.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.utilities.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;


/** The static classes within this class are used to generate the mapping between the concept and the package name
 * 
 * The main routine is generatePackage
 * 
 * @author edwardblurock
 *
 */
public class GeneratePackageInformation {
	
	// The 
	static String pomtemplateR = "info/esblurock/ChemConnectGeneratedArtifacts/resources/pom.xml";
	static String gwttemplateR = "info/esblurock/ChemConnectGeneratedArtifacts/resources/gwt.xml";

	
	/**
	 * @param topconcept The top concept of a hierarchy of concepts
	 * @return The mapping of the concept and the package
	 * 
	 * The hierarchy of concepts is used to determine the package of the concept. 
	 * The package hierarchy matches the concept hierarchy
	 * 
	 * First the hierarchy of concepts is found (HierarchyNode). In each node the ClassificationInformation
	 * is filled in.
	 * 
	 * The package hierarchy is created with the skos:altlabel of the concept within the hierarchy
	 * If a module is (ChemConnectModule) defined within the concept, the package name within the concept
	 * is used as the base of the packages below it.
	 * 
	 */
	public static GeneratedClassObjects generatePackage(String topconcept, String module,
			GeneratedClassObjects alreadygenerated) {
		GeneratedClassObjects packageNames = new GeneratedClassObjects();
		ArrayList<ClassificationInformation> clslst = new ArrayList<ClassificationInformation>();
		
		HierarchyNode topnode = DatasetOntologyParseBase.findClassHierarchy(topconcept);
		packageHierarchy(topnode,packageNames,clslst,module,alreadygenerated);
		
		return packageNames;
	}
	
	/** This adds the package name and concept mapping from the hierarchy 
	 * 
	 * @param node The current node
	 * @param packageNames The current set of concept to package name mappings
	 * @param clslst The list of concepts from the topnode to the current node
	 * 
	 * The sub concepts are 
	 * 
	 */
	static void packageHierarchy(HierarchyNode node, 
			GeneratedClassObjects packageNames, 
			ArrayList<ClassificationInformation> clslst,
			String module,
			GeneratedClassObjects alreadygenerated) {
		ClassificationInformation info = node.getInfo();
		if(info != null) {
			String concept = info.getIdName();
			ArrayList<ClassificationInformation> subclslst = new ArrayList<ClassificationInformation>(clslst);
			if(node.getSubNodes().size() > 0) {
				subclslst.add(info);
			}
			String toppackage = topPackageFromModuleFromConcept(subclslst);
			String currentmodule = DatasetOntologyParseBase.getModuleMembershipFromConcept(concept);
			if(alreadygenerated.getPackageName(concept) == null) {
				if(currentmodule.matches(module)) {
					packageNames.addClassAndPackage(concept,toppackage);
				} else {
					System.out.println("packageHierarchy: modules: " + module + " =/= " + currentmodule);
				}
			}
			for(HierarchyNode subnode : node.getSubNodes()) {
				packageHierarchy(subnode,packageNames,subclslst,module,alreadygenerated);
			}
		} else {
			System.out.println("info null: " + node.getIdentifier());
		}
	}
	
	
	/** Determine package name from the path of concepts from Hierarchy 
	 * 
	 * @param clslst The list of concepts (with ClassificationInformation) from the top concept to the current
	 * @return The generated package name.
	 * 
	 * Given the path from the top concept within the hierarchy to the current concept, 
	 * the package name is created. If a module is defined within the concept, 
	 * the base package defined with the module is the base of the set of packages names 
	 * of the sub concepts. The package name of the sub concepts is appended 
	 * with the skos:altlabel of the concepts.
	 * 
	 */
	public static String topPackageFromModuleFromConcept(ArrayList<ClassificationInformation> clslst) {
		String packageName = "";
		for(ClassificationInformation cls : clslst) {
			String concept = cls.getIdName();
			String module = DatasetOntologyParseBase.getModuleDirectFromConcept(concept);
			if(module == null) {
				packageName += "." + cls.getLink();
			} else {
				packageName = DatasetOntologyParseBase.getDomainFromModule(module);
				packageName += ".data";
			}
		}
		return packageName;
	}

	/** Generate the pom file as string
	 * 
	 * @param module The module of the pom.xml file
	 * @return The generated pom file as string
	 * @throws IOException If a problem to reading the pom template
	 * 
	 * <ul>
	 * <li> Determine the dependent modules of the pom module
	 * <li> Read in the pom.xml template
	 * <li> Substitute in the module and dependent module information
	 * </ul>
	 * 
	 */
	public static String generatePom(String module) throws IOException {
		Set<String> dependences = GeneratePackageInformation.dependentModules(module);
		InputStream str = GeneratePackageInformation.class.getClassLoader().getResourceAsStream(pomtemplateR);
		StringBuilder build = new StringBuilder();
		String pom = IOUtils.toString(str, StandardCharsets.UTF_8);
		StringTokenizer tok = new StringTokenizer(pom,"\n");
		String line = (String) tok.nextElement();
		while(tok.hasMoreTokens()){
			if(line.contains("XXXXXX")) {
				String simpmodule = ChemConnectCompoundDataStructure.removeNamespace(module);
				line = "      <artifactId>" + simpmodule + "</artifactId>";
				build.append(line).append("\n");
			} else 	if(line.contains("YYYYY")) {
				String simpmodule = ChemConnectCompoundDataStructure.removeNamespace(module);
				line = "      <name>" + simpmodule + "</name>";
				build.append(line).append("\n");
			} else if(line.contains("DDDDDD")){
				for(String dep : dependences) {
					String simpdep = ChemConnectCompoundDataStructure.removeNamespace(dep);
					String depS = "      <dependency>\n" + 
							"          <groupId>info.esblurock</groupId>\n" + 
							"          <artifactId>" + simpdep + "</artifactId>\n" + 
							"          <version>1.0</version>\n" + 
							"      </dependency>\n";
					build.append(depS);
				}
			} else {
				build.append(line).append("\n");
			}
			line = (String) tok.nextElement();
        }
		build.append(line).append("\n");
		return build.toString();
	}
	
	/** Determine the set of dependent modules
	 * @param module The name of the module
	 * @return The set of dependent modules
	 */
	public static Set<String> dependentModules(String module) {
		return BasicConceptParsing.findDependentModules(module);
	}
	
	/** Generates the gwt.xml file
	 * 
	 * @param module The module for the gwt.xml file
	 * @return The string with the gwt.xml file
	 * @throws IOException 
	 * 
	 * Generates the gwt.xml file with the module name inserted
	 * 
	 */
	public static String generateGWTFile(String module) throws IOException {
		InputStream str = GeneratePackageInformation.class.getClassLoader().getResourceAsStream(gwttemplateR);
		StringBuilder build = new StringBuilder();
		String pom = IOUtils.toString(str, StandardCharsets.UTF_8);
		StringTokenizer tok = new StringTokenizer(pom,"\n");
		String line = (String) tok.nextElement();
		while(tok.hasMoreTokens()){
			if(line.contains("MMMMM")) {
				String lowmodule = module.toLowerCase();
				line = "  <module rename-to=\'" + lowmodule + "\'>";
			}
			build.append(line).append("\n");
			line = (String) tok.nextElement();
        }
		build.append(line).append("\n");
		return build.toString();
	}
	
}
