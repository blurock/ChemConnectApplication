package info.esblurock.reaction.chemconnect.core.base.activity;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.activity.ActivityInformationRecordBase;

@Entity
@SuppressWarnings("serial")
public class ActivityRepositoryInformation extends ActivityInformationRecordBase {


   public ActivityRepositoryInformation() {
   }

   public ActivityRepositoryInformation(ActivityInformationRecordBase structure) {
      super(structure);
   }


}
