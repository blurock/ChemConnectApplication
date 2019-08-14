package info.esblurock.reaction.chemconnect.core.base.dataset.consortium;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;


@Entity
@SuppressWarnings("serial")
public class ConsortiumMember extends ChemConnectCompoundDataStructure {

	@Index
	String consortiumName;
	@Index
	String consortiumMemberName;
	
	public ConsortiumMember() {
		this.consortiumName = "ConsortiumName";
		this.consortiumMemberName = "ConsortiumMemberName";		
	}
	public ConsortiumMember(ChemConnectCompoundDataStructure structure, String consortiumName, String consortiumMemberName) {
		super(structure);
		this.consortiumName = consortiumName;
		this.consortiumMemberName = consortiumMemberName;
	}
	public String getConsortiumName() {
		return consortiumName;
	}
	public void setConsortiumName(String consortiumName) {
		this.consortiumName = consortiumName;
	}
	public String getConsortiumMemberName() {
		return consortiumMemberName;
	}
	public void setConsortiumMemberName(String consortiumMemberName) {
		this.consortiumMemberName = consortiumMemberName;
	}
	
	@Override
	public String toString() {
		return toString("");
	}
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "Consortium: " + consortiumName 
				+ ",\tMember: " + consortiumMemberName + ")\n");
		return builder.toString();
	}	
	

}
