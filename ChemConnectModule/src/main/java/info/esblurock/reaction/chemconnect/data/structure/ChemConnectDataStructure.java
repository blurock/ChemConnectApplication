package info.esblurock.reaction.chemconnect.data.structure;

import java.util.Map;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.data.DatabaseObject;
import info.esblurock.reaction.chemconnect.data.SimpleCatalogObject;

@SuppressWarnings("serial")
@Entity
public class ChemConnectDataStructure extends SimpleCatalogObject {
	@Index
	String dataSetReference;
	@Index
	String descriptionDataData;
	@Index
	String chemConnectObjectLink;
	@Index
	String contactHasSite;
	
	public ChemConnectDataStructure() {
		super();
		this.descriptionDataData = "";
		this.dataSetReference = "";
		this.chemConnectObjectLink = "";
		this.contactHasSite = "";
	}
	
	public ChemConnectDataStructure(ChemConnectDataStructure datastructure) {
		super(datastructure);
		this.dataSetReference = datastructure.getDataSetReference();
		this.descriptionDataData = datastructure.getDescriptionDataData();
		this.chemConnectObjectLink = datastructure.getChemConnectObjectLink();
		this.contactHasSite = datastructure.getContactHasSite();
	}

	public ChemConnectDataStructure(DatabaseObject obj) {
		super(obj);
		this.descriptionDataData = "";
		this.dataSetReference = "";
		this.chemConnectObjectLink = "";
		this.contactHasSite = "";
	}
	
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		ChemConnectDataStructure datastructure = (ChemConnectDataStructure) object;
		this.dataSetReference = datastructure.getDataSetReference();
		this.descriptionDataData = datastructure.getDescriptionDataData();
		this.chemConnectObjectLink = datastructure.getChemConnectObjectLink();
		this.contactHasSite = datastructure.getContactHasSite();
	}
	
	public void fillMapOfValues(Map<String,String> map) {
		super.fillMapOfValues(map);
	      map.put("linkS", this.getChemConnectObjectLink());
	      map.put("httpcontactS", this.getContactHasSite());
	      map.put("descrS", this.getDescriptionDataData());
	      map.put("refS", this.getDataSetReference());
	   }
public void retrieveFromMap(Map<String,String> map) {
	super.retrieveFromMap(map);
	String param = map.get("linkS");
	if(param != null) {
		this.setChemConnectObjectLink(param);
	}
	param = map.get("httpcontactS");
	if(param != null) {
		this.setContactHasSite(param);
	}
	param = map.get("descrS");
	if(param != null) {
		this.setDescriptionDataData(param);
	}
	param = map.get("refS");
	if(param != null) {
		this.setDataSetReference(param);
	}
	
	   }
	
	
	
	public String getDataSetReference() {
		return dataSetReference;
	}

	public String getDescriptionDataData() {
		return descriptionDataData;
	}

	public String getChemConnectObjectLink() {
		return chemConnectObjectLink;
	}
	
	public String getContactHasSite() {
		return contactHasSite;
	}

	public void setDataSetReference(String dataSetReference) {
		this.dataSetReference = dataSetReference;
	}

	public void setDescriptionDataData(String descriptionDataData) {
		this.descriptionDataData = descriptionDataData;
	}

	public void setChemConnectObjectLink(String chemConnectObjectLink) {
		this.chemConnectObjectLink = chemConnectObjectLink;
	}

	public void setContactHasSite(String contactHasSite) {
		this.contactHasSite = contactHasSite;
	}

	@Override
	public String toString() {
		return toString("");
	}
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix);
		builder.append("Descr: ");
		builder.append(descriptionDataData);
		builder.append("\n");
		builder.append(prefix);
		builder.append("Reference: ");
		builder.append(dataSetReference);
		builder.append("\n");
		builder.append(prefix + "ObjectLink: ");
		builder.append(chemConnectObjectLink);
		builder.append("\n");
		builder.append(prefix + "ContactHasSite: ");
		builder.append(contactHasSite);
		builder.append("\n");
		return builder.toString();
	}
}
