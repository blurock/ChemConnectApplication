package info.esblurock.reaction.chemconnect.core.base.transaction;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class ActivityInformationRecordIDAndType extends ChemConnectCompoundDataStructure {
	
	@Index
	String activityInformationRecordID;
	@Index
	String activityInformationRecordType;

	public ActivityInformationRecordIDAndType() {
		super();
	}

	
	public ActivityInformationRecordIDAndType(ChemConnectCompoundDataStructure structure,
			String activityInformationRecordID,
			String activityInformationRecordType) {
		super(structure);
		this.activityInformationRecordID = activityInformationRecordID;
		this.activityInformationRecordType = activityInformationRecordType;
	}


	public String getActivityInformationRecordID() {
		return activityInformationRecordID;
	}


	public void setActivityInformationRecordID(String activityInformationRecordID) {
		this.activityInformationRecordID = activityInformationRecordID;
	}


	public String getActivityInformationRecordType() {
		return activityInformationRecordType;
	}


	public void setActivityInformationRecordType(String activityInformationRecordType) {
		this.activityInformationRecordType = activityInformationRecordType;
	}
	
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Activity Info Type:       " + activityInformationRecordType + "\n");
		build.append(prefix + "Activity Info ID  :       " + activityInformationRecordID + "\n");
		return build.toString();
	}
	

}
