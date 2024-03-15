package info.esblurock.reaction.chemconnect.base.data.transaction.transinpobj;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.transaction.ChemConnectTransactionEvent;

@Entity
@SuppressWarnings("serial")
public class TransactionEventInputObject extends ChemConnectTransactionEvent {

   @Index
   String activityinfoS;
   @Index
   String requiredtransitionidS;

   public TransactionEventInputObject() {
   }

   public TransactionEventInputObject(ChemConnectTransactionEvent structure) {
      super(structure);
   }

   public String getActivityInformationRecord() {
         return activityinfoS;
   }
   public void setActivityInformationRecord(String activityinfoS) {
      this.activityinfoS = activityinfoS;
   }

   public String getRequiredTransactionIDAndType() {
         return requiredtransitionidS;
   }
   public void setRequiredTransactionIDAndType(String requiredtransitionidS) {
      this.requiredtransitionidS = requiredtransitionidS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("activityinfoS", this.getActivityInformationRecord());
      map.put("requiredtransitionidS", this.getRequiredTransactionIDAndType());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("activityinfoS");
      if(param0 != null) {
         this.setActivityInformationRecord(param0);
      }
      String param1 = map.get("requiredtransitionidS");
      if(param1 != null) {
         this.setRequiredTransactionIDAndType(param1);
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
      build.append(prefix + "ActivityInformationRecord:  " + this.getActivityInformationRecord() + "\n");
      build.append(prefix + "RequiredTransactionIDAndType:  " + this.getRequiredTransactionIDAndType() + "\n");
      return build.toString();
   }

}
