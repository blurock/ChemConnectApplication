package info.esblurock.reaction.chemconnect.base.data.transaction.transinpobj;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.transaction.transinpobj.TransactionEventInputObject;

@Entity
@SuppressWarnings("serial")
public class TransactionEventObject extends TransactionEventInputObject {

   @Index
   String outputtransactionidS;

   public TransactionEventObject() {
   }

   public TransactionEventObject(TransactionEventInputObject structure) {
      super(structure);
   }

   public String getOutputTransactionIDAndType() {
         return outputtransactionidS;
   }
   public void setOutputTransactionIDAndType(String outputtransactionidS) {
      this.outputtransactionidS = outputtransactionidS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("outputtransactionidS", this.getOutputTransactionIDAndType());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("outputtransactionidS");
      if(param0 != null) {
         this.setOutputTransactionIDAndType(param0);
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
      build.append(prefix + "OutputTransactionIDAndType:  " + this.getOutputTransactionIDAndType() + "\n");
      return build.toString();
   }

}
