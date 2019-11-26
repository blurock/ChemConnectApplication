package info.esblurock.reaction.chemconnect.core.base.activity;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.activity.ActivityRepositoryInitialReadInfo;

@Entity
@SuppressWarnings("serial")
public class ActivityRepositoryInitialReadLocalFile extends ActivityRepositoryInitialReadInfo {


   public ActivityRepositoryInitialReadLocalFile() {
   }

   public ActivityRepositoryInitialReadLocalFile(ActivityRepositoryInitialReadInfo structure) {
      super(structure);
   }


}
