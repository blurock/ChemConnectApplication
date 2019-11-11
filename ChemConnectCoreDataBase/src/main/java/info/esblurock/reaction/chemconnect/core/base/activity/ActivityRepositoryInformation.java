package info.esblurock.reaction.chemconnect.core.base.activity;

import com.googlecode.objectify.annotation.Entity;

@SuppressWarnings("serial")
@Entity
public class ActivityRepositoryInformation extends ActivityInformationRecordBase {

	public ActivityRepositoryInformation(ActivityInformationRecordBase recordbase) {
		super(recordbase);
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
