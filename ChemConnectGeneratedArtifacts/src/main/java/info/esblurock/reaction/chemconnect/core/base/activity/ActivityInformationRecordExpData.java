package info.esblurock.reaction.chemconnect.core.base.activity;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.activity.ActivityInformationRecord;

@Entity
@SuppressWarnings("serial")
public class ActivityInformationRecordExpData extends ActivityInformationRecord {


   public ActivityInformationRecordExpData() {
   }

   public ActivityInformationRecordExpData(ActivityInformationRecord structure) {
      super(structure);
   }


}
