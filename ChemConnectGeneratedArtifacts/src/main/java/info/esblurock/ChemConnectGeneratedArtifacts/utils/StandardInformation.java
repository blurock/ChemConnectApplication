package info.esblurock.ChemConnectGeneratedArtifacts.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import info.esblurock.reaction.chemconnect.core.base.concepts.StandardConceptAnnotations;

public class StandardInformation extends StandardConceptAnnotations implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String superClass;
	List<StandardGenerationInterface> information;

	public StandardInformation() {
		super();
		information = new ArrayList<StandardGenerationInterface>();
	}
	
	public StandardInformation(StandardConceptAnnotations annotations, String superClass) {
		super(annotations);
		this.superClass = superClass;
		information = new ArrayList<StandardGenerationInterface>();
	}
	
	public StandardInformation(StandardConceptAnnotations annotations,
			String superClass, List<StandardGenerationInterface> information) {
		super(annotations);
		this.superClass = superClass;
		this.information = information;
	}
	
	public StandardInformation(StandardInformation info) {
		super(info);
		this.superClass = info.getSuperClass();
		this.information = info.getInformation();
	}
	
	public void addInformation(StandardGenerationInterface info) {
		information.add(info);
	}
	
	public String getSuperClass() {
		return superClass;
	}

	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}

	public List<StandardGenerationInterface> getInformation() {
		return information;
	}

	public void setInformation(List<StandardGenerationInterface> information) {
		this.information = information;
	}

	@Override
	public String toString() {
		return toString("");
	}
	
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append("Super class:   " + superClass + "\n");
		for(StandardGenerationInterface annotations : information) {
			builder.append(annotations.toString(prefix) + "\n");
		}
		return builder.toString();
	}
}
