package info.esblurock.reaction.core.ontology.base.generation;

import java.util.List;

import info.esblurock.reaction.chemconnect.core.base.concepts.StandardConceptAnnotations;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
public class StandardStandardRecord  extends StandardInformation implements StandardGenerationInterface {

	public StandardStandardRecord() {
		super();
	}

	public StandardStandardRecord(StandardConceptAnnotations annotations, String superClass,
			List<StandardGenerationInterface> information) {
		super(annotations, superClass, information);
	}

	public StandardStandardRecord(StandardConceptAnnotations annotations, String superClass) {
		super(annotations, superClass);
	}
	@Override
	public String stringCreateYamlObject() {
		String create = "String "
				+ retrieveName() + " = (String) yaml.get(\""
				+ this.getIdentifier()
				+ "\");";
		
		return create;
	}
	
	@Override
	public String getClassName() {
		return ChemConnectCompoundDataStructure.removeNamespace(getConceptname());
	}

	@Override
	public String stringCreateObject() {
		String datastructure = ChemConnectCompoundDataStructure.removeNamespace(this.getConceptname());
		String create = 
				"     InterpretDataInterface interpret = InitializationBase.interpretDataBase.valueOf(\"" + datastructure + "\");\n" + 
				"     DatabaseObjectHierarchy comphier = interpret.createEmptyObject(obj);\n" + 
				"     " + datastructure + " compound = (" + datastructure + ") comphier.getObject();\n" + 
				"\n";
		return create;
	}

	@Override
	public String stringCreateYamlFromObject() {
		String datastructure = ChemConnectCompoundDataStructure.removeNamespace(this.getConceptname());
		String fillS = "map.put(\""
				+ this.getIdentifier()
				+ "\", "
				+ "structure.get" + datastructure+ "()"
				+ ");";
		return fillS;
	}

	@Override
	public String retrieveName() {
		return this.getAltlabel() + "S";
	}

	@Override
	public String getHierarchyName() {
		return this.getAltlabel() + "hier";
	}

	@Override
	public String stringCreateSetObject(String infoname, boolean yaml) {
		String statement = "      " + infoname + ".set(" + retrieveName();
		if(!yaml) {
			statement += ".getIdentifier()";
		}
		statement += ");";
		return statement;
	}


}
