package info.esblurock.reaction.chemconnect.base.data.baserepository;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.baserepository.ChemConnectStructureRepository;

@Entity
@SuppressWarnings("serial")
public class RepositoryFileStaging extends ChemConnectStructureRepository {

   @Index
   String processedS;
   @Index
   String gcsfileinfoS;
   @Index
   String stagefileS;

   public RepositoryFileStaging() {
   }

   public RepositoryFileStaging(ChemConnectStructureRepository structure) {
      super(structure);
   }

   public String getStagingFilePresent() {
         return processedS;
   }
   public void setStagingFilePresent(String processedS) {
      this.processedS = processedS;
   }

   public String getGCSBlobFileInformation() {
         return gcsfileinfoS;
   }
   public void setGCSBlobFileInformation(String gcsfileinfoS) {
      this.gcsfileinfoS = gcsfileinfoS;
   }

   public String getInitialStagingRepositoryFile() {
         return stagefileS;
   }
   public void setInitialStagingRepositoryFile(String stagefileS) {
      this.stagefileS = stagefileS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("processedS", this.getStagingFilePresent());
      map.put("gcsfileinfoS", this.getGCSBlobFileInformation());
      map.put("stagefileS", this.getInitialStagingRepositoryFile());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("processedS");
      if(param0 != null) {
         this.setStagingFilePresent(param0);
      }
      String param1 = map.get("gcsfileinfoS");
      if(param1 != null) {
         this.setGCSBlobFileInformation(param1);
      }
      String param2 = map.get("stagefileS");
      if(param2 != null) {
         this.setInitialStagingRepositoryFile(param2);
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
      build.append(prefix + "StagingFilePresent:  " + this.getStagingFilePresent() + "\n");
      build.append(prefix + "GCSBlobFileInformation:  " + this.getGCSBlobFileInformation() + "\n");
      build.append(prefix + "InitialStagingRepositoryFile:  " + this.getInitialStagingRepositoryFile() + "\n");
      return build.toString();
   }

}
