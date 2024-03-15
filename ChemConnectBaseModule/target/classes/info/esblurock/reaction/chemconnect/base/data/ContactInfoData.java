package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class ContactInfoData extends ChemConnectCompoundBase {

   @Index
   String contactS;
   @Index
   String contacttypeS;

   public ContactInfoData() {
   }

   public ContactInfoData(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getContactKey() {
         return contactS;
   }
   public void setContactKey(String contactS) {
      this.contactS = contactS;
   }

   public String getContactType() {
         return contacttypeS;
   }
   public void setContactType(String contacttypeS) {
      this.contacttypeS = contacttypeS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("contactS", this.getContactKey());
      map.put("contacttypeS", this.getContactType());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("contactS");
      if(param0 != null) {
         this.setContactKey(param0);
      }
      String param1 = map.get("contacttypeS");
      if(param1 != null) {
         this.setContactType(param1);
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
      build.append(prefix + "ContactKey:  " + this.getContactKey() + "\n");
      build.append(prefix + "ContactType:  " + this.getContactType() + "\n");
      return build.toString();
   }

}
