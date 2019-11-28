package info.esblurock.reaction.chemconnect.data.dataset;

import java.io.Serializable;

import info.esblurock.reaction.chemconnect.data.DatabaseObject;


/*
 * 
 * This has all the annotated information about a concept object
 * 
 * idName:       dataset:ContactLocationInformation: 
 * identifier:   The identifier of the class 
 *                vcard:Location  
 * dataType:     The class with the sub-elements (from Type)
 *               ContactLocationInformation     
 * 
 * :ContactLocationInformation rdfs:isDefinedBy <http://www.w3.org/2006/vcard/ns#Location> ;
                            <http://purl.org/dc/elements/1.1/type> "ContactLocationInformation"^^xsd:string ;
                            rdfs:label "Contact Location Information"^^xsd:string .              
 * 
 * Classification: ID: dataset:ContactLocationInformation(dcat:record): vcard:Location  (ContactLocationInformation)
 * dataType:    ContactLocationInformation
 * idName:      dataset:ContactLocationInformation
 * link:        dcat:record
 * identifier:  vcard:Location
 * 
 * Source of identifier information:
 * ###  http://www.esblurock.info/dataset#ContactLocationInformation
:ContactLocationInformation rdf:type owl:NamedIndividual ;
                            <http://purl.org/dc/terms/identifier> "vcard:Location"^^xsd:string .

 */
public class ClassificationInformation implements Serializable {
	private static final long serialVersionUID = 1L;
	String idName;
	String identifier;
	String dataType;
	String link;
	String label;
	String comment;
	DatabaseObject top;
	
	public ClassificationInformation() {
	}
	public ClassificationInformation(DatabaseObject top, 
			String link, String idName, String identifier, String dataType) {
		super();
		this.idName = idName;
		this.identifier = identifier;
		this.dataType = dataType;
		this.top = top;
		this.link = link;
	}
	public ClassificationInformation(DatabaseObject top, 
			String link, String idName, String identifier, String dataType,
			String label, String comment) {
		super();
		this.idName = idName;
		this.identifier = identifier;
		this.dataType = dataType;
		this.top = top;
		this.link = link;
		this.label = label;
		this.comment = comment;
	}
	public String getIdName() {
		return idName;
	}
	public String getIdentifier() {
		return identifier;
	}
	public String getDataType() {
		return dataType;
	}
	
	public DatabaseObject getTop() {
		return top;
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
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public void setTop(DatabaseObject top) {
		this.top = top;
	}
	public String getLink() {
		return link;
	}
	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		
		build.append(prefix + "ID: ");
		build.append(idName);
		if(link != null) {
			build.append("(");
			build.append(link);
			build.append(")");
		}
		build.append(": ");
		build.append(identifier);
		build.append("  (");
		build.append(dataType);
		build.append(")\n");
		build.append(prefix + "Label: '" + label + "' (" + comment + ")");
		
		return build.toString();
	}
	
}
