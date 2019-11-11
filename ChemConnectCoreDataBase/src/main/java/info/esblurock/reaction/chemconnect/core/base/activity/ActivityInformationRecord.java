package info.esblurock.reaction.chemconnect.core.base.activity;

import com.googlecode.objectify.annotation.Entity;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class ActivityInformationRecord extends DatabaseObject {

	
	public ActivityInformationRecord() {
	}
	
	public ActivityInformationRecord(DatabaseObject obj) {
		super(obj);
	}
	
	@Override
	public String toString() {
		return toString("");
	}
	
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		return builder.toString();
	}

}
