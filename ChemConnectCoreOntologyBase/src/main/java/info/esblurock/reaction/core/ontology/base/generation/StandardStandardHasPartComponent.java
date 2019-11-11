package info.esblurock.reaction.core.ontology.base.generation;

import java.util.List;

import info.esblurock.reaction.chemconnect.core.base.concepts.StandardConceptAnnotations;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
public class StandardStandardHasPartComponent extends StandardInformation implements StandardGenerationInterface {

	public StandardStandardHasPartComponent() {
		super();
	}

	public StandardStandardHasPartComponent(StandardConceptAnnotations annotations, String superClass,
			List<StandardGenerationInterface> information) {
		super(annotations, superClass, information);
	}

	public StandardStandardHasPartComponent(StandardConceptAnnotations annotations, String superClass) {
		super(annotations, superClass);
	}
	@Override
	public String stringCreateObject() {
		return null;
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
	public String stringCreateSetObject(String infoname,boolean yaml) {
		String statement = "      " + infoname + ".set" 
					+ ChemConnectCompoundDataStructure.removeNamespace(getConceptname())
					+"(" + retrieveName() + ");";
		return statement;
	}
	
	@Override
	public String getClassName() {
		return ChemConnectCompoundDataStructure.removeNamespace(getConceptname());
	}
	

}
