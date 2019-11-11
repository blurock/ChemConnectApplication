package info.esblurock.reaction.chemconnect.core.base.concepts;

import java.io.Serializable;

public class StandardConceptAnnotations implements Serializable {

	private static final long serialVersionUID = 1L;
	
	String conceptname;
	String label;
	String comment;
	String altlabel;
	String type;
	String identifier;
	
	
	
	public StandardConceptAnnotations() {
	}
	
	public StandardConceptAnnotations(String conceptname, String label, String comment, String altlabel, String type, String identifier) {
		super();
		this.conceptname = conceptname;
		this.label = label;
		this.comment = comment;
		this.altlabel = altlabel;
		this.type = type;
		this.identifier = identifier;
	}
	
	public StandardConceptAnnotations(StandardConceptAnnotations annotations) {
		super();
		this.conceptname = annotations.getConceptname();
		this.label = annotations.getLabel();
		this.comment = annotations.getComment();
		this.altlabel = annotations.getAltlabel();
		this.type = annotations.getType();
		this.identifier = annotations.getIdentifier();
	}

	
	public String getConceptname() {
		return conceptname;
	}

	public void setConceptname(String conceptname) {
		this.conceptname = conceptname;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAltlabel() {
		return altlabel;
	}

	public void setAltlabel(String altlabel) {
		this.altlabel = altlabel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	@Override
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + conceptname + "\n");	
		build.append(prefix + "label:      " + label + "\n");
		build.append(prefix + "altlabel:   " + altlabel + "\n");
		build.append(prefix + "comment:    " + comment + "\n");
		build.append(prefix + "type:       " + type + "\n");
		build.append(prefix + "identifier: " + identifier + "\n");
		return build.toString();
	}
	

}
