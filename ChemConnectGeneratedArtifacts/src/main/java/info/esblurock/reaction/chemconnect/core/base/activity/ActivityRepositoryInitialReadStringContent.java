package info.esblurock.reaction.chemconnect.core.base.activity;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.activity.ActivityRepositoryInitialReadInfo;

@Entity
@SuppressWarnings("serial")
public class ActivityRepositoryInitialReadStringContent extends ActivityRepositoryInitialReadInfo {


   public ActivityRepositoryInitialReadStringContent() {
   }

   public ActivityRepositoryInitialReadStringContent(ActivityRepositoryInitialReadInfo structure) {
      super(structure);
   }


}
