package info.esblurock.reaction.chemconnect.base.data.user;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.user.ChemConnectStructureUser;

@Entity
@SuppressWarnings("serial")
public class UserAccount extends ChemConnectStructureUser {

   @Index
   String unameS;
   @Index
   String authnameS;
   @Index
   String authtypeS;

   public UserAccount() {
   }

   public UserAccount(ChemConnectStructureUser structure) {
      super(structure);
   }

   public String getusername() {
         return unameS;
   }
   public void setusername(String unameS) {
      this.unameS = unameS;
   }

   public String getAuthorizationName() {
         return authnameS;
   }
   public void setAuthorizationName(String authnameS) {
      this.authnameS = authnameS;
   }

   public String getAuthorizationType() {
         return authtypeS;
   }
   public void setAuthorizationType(String authtypeS) {
      this.authtypeS = authtypeS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("unameS", this.getusername());
      map.put("authnameS", this.getAuthorizationName());
      map.put("authtypeS", this.getAuthorizationType());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("unameS");
      if(param0 != null) {
         this.setusername(param0);
      }
      String param1 = map.get("authnameS");
      if(param1 != null) {
         this.setAuthorizationName(param1);
      }
      String param2 = map.get("authtypeS");
      if(param2 != null) {
         this.setAuthorizationType(param2);
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
      build.append(prefix + "username:  " + this.getusername() + "\n");
      build.append(prefix + "AuthorizationName:  " + this.getAuthorizationName() + "\n");
      build.append(prefix + "AuthorizationType:  " + this.getAuthorizationType() + "\n");
      return build.toString();
   }

}
