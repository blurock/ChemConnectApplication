package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class ConsortiumMember extends ChemConnectCompoundBase {

   @Index
   String consortmemberS;
   @Index
   String consortnameS;

   public ConsortiumMember() {
   }

   public ConsortiumMember(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getConsortiumMemberName() {
         return consortmemberS;
   }
   public void setConsortiumMemberName(String consortmemberS) {
      this.consortmemberS = consortmemberS;
   }

   public String getConsortiumName() {
         return consortnameS;
   }
   public void setConsortiumName(String consortnameS) {
      this.consortnameS = consortnameS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("consortmemberS", this.getConsortiumMemberName());
      map.put("consortnameS", this.getConsortiumName());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("consortmemberS");
      if(param0 != null) {
         this.setConsortiumMemberName(param0);
      }
      String param1 = map.get("consortnameS");
      if(param1 != null) {
         this.setConsortiumName(param1);
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
      build.append(prefix + "ConsortiumMemberName:  " + this.getConsortiumMemberName() + "\n");
      build.append(prefix + "ConsortiumName:  " + this.getConsortiumName() + "\n");
      return build.toString();
   }

}
