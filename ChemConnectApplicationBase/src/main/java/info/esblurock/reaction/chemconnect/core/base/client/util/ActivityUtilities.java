package info.esblurock.reaction.chemconnect.core.base.client.util;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.activity.ActivityInformationRecord;
import info.esblurock.reaction.chemconnect.core.base.activity.ActivityInformationRecordBase;
import info.esblurock.reaction.chemconnect.core.base.activity.ActivityRepositoryInformation;
import info.esblurock.reaction.chemconnect.core.base.activity.read.ActivityRepositoryInitialReadInfo;

public class ActivityUtilities {
	
	public static ActivityInformationRecord generateActivityRepositoryInitialReadInfo(
			DatabaseObject obj,
			String descriptionTitle,
   			String fileSourceIdentifier,
   			String fileSourceType) {

		ActivityInformationRecord record = new ActivityInformationRecord(obj);
		ActivityInformationRecordBase recordbase = new  ActivityInformationRecordBase(record);
		ActivityRepositoryInformation repinfo = new ActivityRepositoryInformation(recordbase);
		ActivityRepositoryInitialReadInfo readinfo = new ActivityRepositoryInitialReadInfo(repinfo);
		readinfo.setDescriptionTitle(descriptionTitle);
		readinfo.setFileSourceType(fileSourceType);
		readinfo.setFileSourceIdentifier(fileSourceIdentifier);
		return readinfo;
	}
	
	
	
}
