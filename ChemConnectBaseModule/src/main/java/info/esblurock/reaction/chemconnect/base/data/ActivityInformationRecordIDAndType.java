package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class ActivityInformationRecordIDAndType extends ChemConnectCompoundBase {

   @Index
   String activityinfotypeS;
   @Index
   String activityinfoidS;

   public ActivityInformationRecordIDAndType() {
   }

   public ActivityInformationRecordIDAndType(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getActivityInformationRecordType() {
         return activityinfotypeS;
   }
   public void setActivityInformationRecordType(String activityinfotypeS) {
      this.activityinfotypeS = activityinfotypeS;
   }

   public String getActivityInformationRecordID() {
         return activityinfoidS;
   }
   public void setActivityInformationRecordID(String activityinfoidS) {
      this.activityinfoidS = activityinfoidS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("activityinfotypeS", this.getActivityInformationRecordType());
      map.put("activityinfoidS", this.getActivityInformationRecordID());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("activityinfotypeS");
      if(param0 != null) {
         this.setActivityInformationRecordType(param0);
      }
      String param1 = map.get("activityinfoidS");
      if(param1 != null) {
         this.setActivityInformationRecordID(param1);
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
      build.append(prefix + "ActivityInformationRecordType:  " + this.getActivityInformationRecordType() + "\n");
      build.append(prefix + "ActivityInformationRecordID:  " + this.getActivityInformationRecordID() + "\n");
      return build.toString();
   }

}
