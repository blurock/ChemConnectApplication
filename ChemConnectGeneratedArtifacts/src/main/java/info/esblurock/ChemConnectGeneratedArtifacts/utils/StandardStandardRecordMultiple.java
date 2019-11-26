package info.esblurock.ChemConnectGeneratedArtifacts.utils;

import java.util.List;

import info.esblurock.reaction.chemconnect.core.base.concepts.StandardConceptAnnotations;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
public class StandardStandardRecordMultiple extends StandardInformation implements StandardGenerationInterface {


	public StandardStandardRecordMultiple() {
		super();
	}

	public StandardStandardRecordMultiple(StandardConceptAnnotations annotations, String superClass,
			List<StandardGenerationInterface> information) {
		super(annotations, superClass, information);
	}

	public StandardStandardRecordMultiple(StandardConceptAnnotations annotations, String superClass) {
		super(annotations, superClass);
	}

	@Override
	public String stringCreateYamlObject() {
		String create = "String "
				+ retrieveName() + "S = (String) yaml.get(\""
				+ this.getIdentifier()
				+ "\");";
		
		return create;
	}

	@Override
	public String stringCreateObject() {
		String datastructure = ChemConnectCompoundDataStructure.removeNamespace(this.getConceptname());
		String create = 
				"      InterpretDataInterface interpret = "
				+                "InitializationBase.interpretDataBase.valueOf(\"ChemConnectCompoundMultiple\");\n" +
				"      DatabaseObjectHierarchy " + getHierarchyName() + " = interpret.createEmptyObject(refobj);\n" + 
				"      InterpretBaseDataUtilities.setChemConnectCompoundMultipleType(" + getHierarchyName() + ",\"" 
				     + getConceptname() + "\" );\n" + 
				"      " + datastructure + " " + this.getAltlabel() + "S  = (" + datastructure + ") " + getHierarchyName() + ".getObject();\n" + 
				"\n";
		
		return create;
	}

	@Override
	public String stringCreateYamlFromObject() {
		String datastructure = ChemConnectCompoundDataStructure.removeNamespace(this.getConceptname());
		String fillS = "map.put(\""
				+ this.getIdentifier()
				+ "\", "
				+ "structure.get" + datastructure + "()"
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
		String datastructure = ChemConnectCompoundDataStructure.removeNamespace(this.getConceptname());
		String statement = "      " + infoname + ".set" + datastructure + "(" + retrieveName();
		if(!yaml) {
			statement += ".getIdentifier()";
		}
		statement += ");";
		return statement;
	}

	@Override
	public String getClassName() {
		return ChemConnectCompoundDataStructure.removeNamespace(getConceptname());
	}
	

}
