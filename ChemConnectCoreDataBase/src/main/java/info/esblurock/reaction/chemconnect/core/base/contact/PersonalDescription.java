package info.esblurock.reaction.chemconnect.core.base.contact;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class PersonalDescription extends ChemConnectCompoundDataStructure {

	@Index
	String UserClassification;
	
	@Index
	String nameOfPersonIdentifier;
	
	public PersonalDescription() {
		UserClassification = "";
		this.nameOfPersonIdentifier = "";
		
	}
	public PersonalDescription(String identifier, String sourceID) {
		super(identifier,sourceID);
		UserClassification = "";
		this.nameOfPersonIdentifier = "";
		
	}
	public PersonalDescription(ChemConnectCompoundDataStructure compound,
			String userClassification, String nameOfPersonIdentifier) {
		this.fill(compound,userClassification,nameOfPersonIdentifier);
	}
	
	public void fill(String identifier, String access, String owner, String sourceID,
			String userClassification, String nameOfPersonIdentifier) {
		super.fill(identifier,access,owner,sourceID);
		UserClassification = userClassification;
		this.nameOfPersonIdentifier = nameOfPersonIdentifier;
	}
	public void fill(ChemConnectCompoundDataStructure compound,
			String userClassification, String  nameOfPersonIdentifier) {
		super.fill(compound);
		UserClassification = userClassification;
		this.nameOfPersonIdentifier = nameOfPersonIdentifier;
	}
	public void fill(DatabaseObject object) {
		super.fill(object);
		PersonalDescription person = (PersonalDescription) object;
		UserClassification = person.getUserClassification();
		this.nameOfPersonIdentifier = person.getNameOfPersonIdentifier();
	}
	
	
	public String getUserClassification() {
		return UserClassification;
	}

	public String getNameOfPersonIdentifier() {
		return nameOfPersonIdentifier;
	}
	
	
	public void setUserClassification(String userClassification) {
		UserClassification = userClassification;
	}
	public void setNameOfPersonIdentifier(String nameOfPersonIdentifier) {
		this.nameOfPersonIdentifier = nameOfPersonIdentifier;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "\tUser Classification    : " + UserClassification + "\n");
		builder.append(prefix + "\tNameOfPerson Identifier: " + nameOfPersonIdentifier + "\n");
		return builder.toString();
	}
	
}
