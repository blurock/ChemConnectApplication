package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class UserAccountInformation extends ChemConnectCompoundBase {

   @Index
   String emailS;
   @Index
   String uroleS;

   public UserAccountInformation() {
   }

   public UserAccountInformation(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getContactEmail() {
         return emailS;
   }
   public void setContactEmail(String emailS) {
      this.emailS = emailS;
   }

   public String getUserAccountRole() {
         return uroleS;
   }
   public void setUserAccountRole(String uroleS) {
      this.uroleS = uroleS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("emailS", this.getContactEmail());
      map.put("uroleS", this.getUserAccountRole());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("emailS");
      if(param0 != null) {
         this.setContactEmail(param0);
      }
      String param1 = map.get("uroleS");
      if(param1 != null) {
         this.setUserAccountRole(param1);
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
      build.append(prefix + "ContactEmail:  " + this.getContactEmail() + "\n");
      build.append(prefix + "UserAccountRole:  " + this.getUserAccountRole() + "\n");
      return build.toString();
   }

}
