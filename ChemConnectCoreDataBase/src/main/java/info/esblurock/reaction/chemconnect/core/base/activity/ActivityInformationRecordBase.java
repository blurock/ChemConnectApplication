package info.esblurock.reaction.chemconnect.core.base.activity;

import com.googlecode.objectify.annotation.Entity;

@SuppressWarnings("serial")
@Entity
public class ActivityInformationRecordBase extends ActivityInformationRecord {

	public ActivityInformationRecordBase(ActivityInformationRecord record) {
		super(record);
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
