package info.esblurock.reaction.chemconnect.base.data.user;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.user.ChemConnectStructureUser;

@Entity
@SuppressWarnings("serial")
public class Organization extends ChemConnectStructureUser {

   @Index
   String orginfoS;
   @Index
   String locationS;
   @Index
   String contactS;

   public Organization() {
   }

   public Organization(ChemConnectStructureUser structure) {
      super(structure);
   }

   public String getOrganizationDescription() {
         return orginfoS;
   }
   public void setOrganizationDescription(String orginfoS) {
      this.orginfoS = orginfoS;
   }

   public String getContactLocationInformation() {
         return locationS;
   }
   public void setContactLocationInformation(String locationS) {
      this.locationS = locationS;
   }

   public String getContactInfoData() {
         return contactS;
   }
   public void setContactInfoData(String contactS) {
      this.contactS = contactS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("orginfoS", this.getOrganizationDescription());
      map.put("locationS", this.getContactLocationInformation());
      map.put("contactS", this.getContactInfoData());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("orginfoS");
      if(param0 != null) {
         this.setOrganizationDescription(param0);
      }
      String param1 = map.get("locationS");
      if(param1 != null) {
         this.setContactLocationInformation(param1);
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
      build.append(prefix + "OrganizationDescription:  " + this.getOrganizationDescription() + "\n");
      build.append(prefix + "ContactLocationInformation:  " + this.getContactLocationInformation() + "\n");
      build.append(prefix + "ContactInfoData:  " + this.getContactInfoData() + "\n");
      return build.toString();
   }

}
