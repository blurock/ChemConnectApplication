package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class InitialStagingRepositoryFile extends ChemConnectCompoundBase {

   @Index
   String UploadFileSourceS;
   @Index
   String filesrcidS;

   public InitialStagingRepositoryFile() {
   }

   public InitialStagingRepositoryFile(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getUploadFileSource() {
         return UploadFileSourceS;
   }
   public void setUploadFileSource(String UploadFileSourceS) {
      this.UploadFileSourceS = UploadFileSourceS;
   }

   public String getFileSourceIdentifier() {
         return filesrcidS;
   }
   public void setFileSourceIdentifier(String filesrcidS) {
      this.filesrcidS = filesrcidS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("UploadFileSourceS", this.getUploadFileSource());
      map.put("filesrcidS", this.getFileSourceIdentifier());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("UploadFileSourceS");
      if(param0 != null) {
         this.setUploadFileSource(param0);
      }
      String param1 = map.get("filesrcidS");
      if(param1 != null) {
         this.setFileSourceIdentifier(param1);
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
      build.append(prefix + "UploadFileSource:  " + this.getUploadFileSource() + "\n");
      build.append(prefix + "FileSourceIdentifier:  " + this.getFileSourceIdentifier() + "\n");
      return build.toString();
   }

}
