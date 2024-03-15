package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class GCSBlobFileInformation extends ChemConnectCompoundBase {

   @Index
   String abstractS;
   @Index
   String gcspathS;
   @Index
   String ftypeS;
   @Index
   String gcsfilenameS;

   public GCSBlobFileInformation() {
   }

   public GCSBlobFileInformation(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getDescriptionAbstract() {
         return abstractS;
   }
   public void setDescriptionAbstract(String abstractS) {
      this.abstractS = abstractS;
   }

   public String getGCSFilePath() {
         return gcspathS;
   }
   public void setGCSFilePath(String gcspathS) {
      this.gcspathS = gcspathS;
   }

   public String getFileSourceType() {
         return ftypeS;
   }
   public void setFileSourceType(String ftypeS) {
      this.ftypeS = ftypeS;
   }

   public String getGCSFileName() {
         return gcsfilenameS;
   }
   public void setGCSFileName(String gcsfilenameS) {
      this.gcsfilenameS = gcsfilenameS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("abstractS", this.getDescriptionAbstract());
      map.put("gcspathS", this.getGCSFilePath());
      map.put("ftypeS", this.getFileSourceType());
      map.put("gcsfilenameS", this.getGCSFileName());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("abstractS");
      if(param0 != null) {
         this.setDescriptionAbstract(param0);
      }
      String param1 = map.get("gcspathS");
      if(param1 != null) {
         this.setGCSFilePath(param1);
      }
      String param2 = map.get("ftypeS");
      if(param2 != null) {
         this.setFileSourceType(param2);
      }
      String param3 = map.get("gcsfilenameS");
      if(param3 != null) {
         this.setGCSFileName(param3);
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
      build.append(prefix + "DescriptionAbstract:  " + this.getDescriptionAbstract() + "\n");
      build.append(prefix + "GCSFilePath:  " + this.getGCSFilePath() + "\n");
      build.append(prefix + "FileSourceType:  " + this.getFileSourceType() + "\n");
      build.append(prefix + "GCSFileName:  " + this.getGCSFileName() + "\n");
      return build.toString();
   }

}
