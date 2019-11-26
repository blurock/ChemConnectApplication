package info.esblurock.reaction.chemconnect.core.base.activity;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.activity.ActivityRepositoryInformation;

@Entity
@SuppressWarnings("serial")
public class ActivityRepositoryInitialReadInfo extends ActivityRepositoryInformation {

   @Index
   String titleS;
   @Index
   String filesrcidS;
   @Index
   String ftypeS;

   public ActivityRepositoryInitialReadInfo() {
   }

   public ActivityRepositoryInitialReadInfo(ActivityRepositoryInformation structure) {
      super(structure);
   }

   public String getDescriptionTitle() {
         return titleS;
   }
   public void setDescriptionTitle(String titleS) {
      this.titleS = titleS;
   }

   public String getFileSourceIdentifier() {
         return filesrcidS;
   }
   public void setFileSourceIdentifier(String filesrcidS) {
      this.filesrcidS = filesrcidS;
   }

   public String getFileSourceType() {
         return ftypeS;
   }
   public void setFileSourceType(String ftypeS) {
      this.ftypeS = ftypeS;
   }


}
