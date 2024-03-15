package info.esblurock.reaction.chemconnect.base.data.user;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.user.ChemConnectStructureUser;

@Entity
@SuppressWarnings("serial")
public class Consortium extends ChemConnectStructureUser {

   @Index
   String consortnameS;
   @Index
   String consortmemS;

   public Consortium() {
   }

   public Consortium(ChemConnectStructureUser structure) {
      super(structure);
   }

   public String getConsortiumName() {
         return consortnameS;
   }
   public void setConsortiumName(String consortnameS) {
      this.consortnameS = consortnameS;
   }

   public String getConsortiumMember() {
         return consortmemS;
   }
   public void setConsortiumMember(String consortmemS) {
      this.consortmemS = consortmemS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("consortnameS", this.getConsortiumName());
      map.put("consortmemS", this.getConsortiumMember());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("consortnameS");
      if(param0 != null) {
         this.setConsortiumName(param0);
      }
      String param1 = map.get("consortmemS");
      if(param1 != null) {
         this.setConsortiumMember(param1);
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
      build.append(prefix + "ConsortiumName:  " + this.getConsortiumName() + "\n");
      build.append(prefix + "ConsortiumMember:  " + this.getConsortiumMember() + "\n");
      return build.toString();
   }

}
