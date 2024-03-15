package info.esblurock.reaction.chemconnect.base.data.user;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.user.ChemConnectStructureUser;

@Entity
@SuppressWarnings("serial")
public class DatabasePerson extends ChemConnectStructureUser {

   @Index
   String locationS;
   @Index
   String personS;
   @Index
   String contactS;

   public DatabasePerson() {
   }

   public DatabasePerson(ChemConnectStructureUser structure) {
      super(structure);
   }

   public String getContactLocationInformation() {
         return locationS;
   }
   public void setContactLocationInformation(String locationS) {
      this.locationS = locationS;
   }

   public String getPersonalDescription() {
         return personS;
   }
   public void setPersonalDescription(String personS) {
      this.personS = personS;
   }

   public String getContactInfoData() {
         return contactS;
   }
   public void setContactInfoData(String contactS) {
      this.contactS = contactS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("locationS", this.getContactLocationInformation());
      map.put("personS", this.getPersonalDescription());
      map.put("contactS", this.getContactInfoData());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("locationS");
      if(param0 != null) {
         this.setContactLocationInformation(param0);
      }
      String param1 = map.get("personS");
      if(param1 != null) {
         this.setPersonalDescription(param1);
      }
      String param2 = map.get("contactS");
      if(param2 != null) {
         this.setContactInfoData(param2);
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
      build.append(prefix + "ContactLocationInformation:  " + this.getContactLocationInformation() + "\n");
      build.append(prefix + "PersonalDescription:  " + this.getPersonalDescription() + "\n");
      build.append(prefix + "ContactInfoData:  " + this.getContactInfoData() + "\n");
      return build.toString();
   }

}
