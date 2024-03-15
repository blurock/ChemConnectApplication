package info.esblurock.reaction.chemconnect.base.data.objectandtype.requiredtransid;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.objectandtype.ObjectIDAndType;

@Entity
@SuppressWarnings("serial")
public class TransactionIDAndType extends ObjectIDAndType {

   @Index
   String transidS;
   @Index
   String transtypeS;

   public TransactionIDAndType() {
   }

   public TransactionIDAndType(ObjectIDAndType structure) {
      super(structure);
   }

   public String getDatabaseIDTransaction() {
         return transidS;
   }
   public void setDatabaseIDTransaction(String transidS) {
      this.transidS = transidS;
   }

   public String getTransactionInfoType() {
         return transtypeS;
   }
   public void setTransactionInfoType(String transtypeS) {
      this.transtypeS = transtypeS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("transidS", this.getDatabaseIDTransaction());
      map.put("transtypeS", this.getTransactionInfoType());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("transidS");
      if(param0 != null) {
         this.setDatabaseIDTransaction(param0);
      }
      String param1 = map.get("transtypeS");
      if(param1 != null) {
         this.setTransactionInfoType(param1);
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
      build.append(prefix + "DatabaseIDTransaction:  " + this.getDatabaseIDTransaction() + "\n");
      build.append(prefix + "TransactionInfoType:  " + this.getTransactionInfoType() + "\n");
      return build.toString();
   }

}
