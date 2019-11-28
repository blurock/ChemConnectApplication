package info.esblurock.reaction.chemconnect.data.dataset;

import java.io.Serializable;
import java.util.ArrayList;

public class CompoundDataStructure extends ArrayList<DataElementInformation> implements Serializable {

	private static final long serialVersionUID = 1L;
	String recordType;
	
	public CompoundDataStructure() {
	}
	
	public CompoundDataStructure(String recordType) {
		this.recordType = recordType;
	}
	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(prefix + "ChemConnectCompoundDataStructure: " + recordType + "\n");
		String newprefix = prefix + "\t";
		for(DataElementInformation info : this) {
			build.append(info.toString(newprefix));
			build.append("\n");
		}
		build.append(prefix + "-------------------------------------------------------\n");
		return build.toString();
	}
	public String getRecordType() {
		return recordType;
	}
	@Override
	public boolean add(DataElementInformation element) {
		if(!this.contains(element)) {
			super.add(element);
		}
		return false;
		
	}

}
