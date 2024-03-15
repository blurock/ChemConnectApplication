package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class OrganizationDescription extends ChemConnectCompoundBase {

   @Index
   String orgnameS;
   @Index
   String suborgS;
   @Index
   String orgclassS;
   @Index
   String orgunitS;

   public OrganizationDescription() {
   }

   public OrganizationDescription(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getOrganizationName() {
         return orgnameS;
   }
   public void setOrganizationName(String orgnameS) {
      this.orgnameS = orgnameS;
   }

   public String getSubOrganizationOf() {
         return suborgS;
   }
   public void setSubOrganizationOf(String suborgS) {
      this.suborgS = suborgS;
   }

   public String getOrganizationClassification() {
         return orgclassS;
   }
   public void setOrganizationClassification(String orgclassS) {
      this.orgclassS = orgclassS;
   }

   public String getOrganizationalUnit() {
         return orgunitS;
   }
   public void setOrganizationalUnit(String orgunitS) {
      this.orgunitS = orgunitS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("orgnameS", this.getOrganizationName());
      map.put("suborgS", this.getSubOrganizationOf());
      map.put("orgclassS", this.getOrganizationClassification());
      map.put("orgunitS", this.getOrganizationalUnit());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("orgnameS");
      if(param0 != null) {
         this.setOrganizationName(param0);
      }
      String param1 = map.get("suborgS");
      if(param1 != null) {
         this.setSubOrganizationOf(param1);
      }
      String param2 = map.get("orgclassS");
      if(param2 != null) {
         this.setOrganizationClassification(param2);
      }
      String param3 = map.get("orgunitS");
      if(param3 != null) {
         this.setOrganizationalUnit(param3);
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
      build.append(prefix + "OrganizationName:  " + this.getOrganizationName() + "\n");
      build.append(prefix + "SubOrganizationOf:  " + this.getSubOrganizationOf() + "\n");
      build.append(prefix + "OrganizationClassification:  " + this.getOrganizationClassification() + "\n");
      build.append(prefix + "OrganizationalUnit:  " + this.getOrganizationalUnit() + "\n");
      return build.toString();
   }

}
