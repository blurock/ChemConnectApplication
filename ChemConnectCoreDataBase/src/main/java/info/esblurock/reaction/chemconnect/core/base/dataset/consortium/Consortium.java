package info.esblurock.reaction.chemconnect.core.base.dataset.consortium;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.ChemConnectDataStructure;

@Entity
@SuppressWarnings("serial")
public class Consortium extends ChemConnectDataStructure {
	
	@Index
	String consortiumName;
	@Index
	String consortiumMember;
	
	
	public Consortium() {
		super();
	}

	public Consortium(ChemConnectDataStructure structure, String consortiumName, String consortiumMember) {
		super(structure);
		this.consortiumMember = consortiumMember;
		this.consortiumName = consortiumName;
	}

	
	
	public String getConsortiumName() {
		return consortiumName;
	}

	public void setConsortiumName(String consortiumName) {
		this.consortiumName = consortiumName;
	}

	public String getConsortiumMember() {
		return consortiumMember;
	}

	public void setConsortiumMember(String consortiumMember) {
		this.consortiumMember = consortiumMember;
	}

	@Override
	public String toString() {
		return toString("");
	}
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "Consortium Name   : " + consortiumName + "\n");
		builder.append(prefix + "Consortium Members: " + consortiumMember + "\n");
		return builder.toString();
	}	
}
