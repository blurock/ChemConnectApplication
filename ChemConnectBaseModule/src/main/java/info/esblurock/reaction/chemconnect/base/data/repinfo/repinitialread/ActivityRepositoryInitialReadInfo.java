package info.esblurock.reaction.chemconnect.base.data.repinfo.repinitialread;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.repinfo.ActivityRepositoryInformation;

@Entity
@SuppressWarnings("serial")
public class ActivityRepositoryInitialReadInfo extends ActivityRepositoryInformation {

   @Index
   String ftypeS;
   @Index
   String titleS;
   @Index
   String filesrcidS;

   public ActivityRepositoryInitialReadInfo() {
   }

   public ActivityRepositoryInitialReadInfo(ActivityRepositoryInformation structure) {
      super(structure);
   }

   public String getFileSourceType() {
         return ftypeS;
   }
   public void setFileSourceType(String ftypeS) {
      this.ftypeS = ftypeS;
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

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("ftypeS", this.getFileSourceType());
      map.put("titleS", this.getDescriptionTitle());
      map.put("filesrcidS", this.getFileSourceIdentifier());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("ftypeS");
      if(param0 != null) {
         this.setFileSourceType(param0);
      }
      String param1 = map.get("titleS");
      if(param1 != null) {
         this.setDescriptionTitle(param1);
      }
      String param2 = map.get("filesrcidS");
      if(param2 != null) {
         this.setFileSourceIdentifier(param2);
      }
   }
	@Override
	public String toString() {
		return toString("");
	}

   @Override
   public String toString(String prefix) {
      StringBuilder build = new StringBuilder();
      build.append(super.toString(prefix));
      build.append(prefix + "FileSourceType:  " + this.getFileSourceType() + "\n");
      build.append(prefix + "DescriptionTitle:  " + this.getDescriptionTitle() + "\n");
      build.append(prefix + "FileSourceIdentifier:  " + this.getFileSourceIdentifier() + "\n");
      return build.toString();
   }

}
