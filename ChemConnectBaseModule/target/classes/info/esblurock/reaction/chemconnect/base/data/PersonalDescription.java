package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class PersonalDescription extends ChemConnectCompoundBase {

   @Index
   String userclassS;
   @Index
   String pnameS;

   public PersonalDescription() {
   }

   public PersonalDescription(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getUserClassification() {
         return userclassS;
   }
   public void setUserClassification(String userclassS) {
      this.userclassS = userclassS;
   }

   public String getNameOfPerson() {
         return pnameS;
   }
   public void setNameOfPerson(String pnameS) {
      this.pnameS = pnameS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("userclassS", this.getUserClassification());
      map.put("pnameS", this.getNameOfPerson());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("userclassS");
      if(param0 != null) {
         this.setUserClassification(param0);
      }
      String param1 = map.get("pnameS");
      if(param1 != null) {
         this.setNameOfPerson(param1);
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
      build.append(prefix + "UserClassification:  " + this.getUserClassification() + "\n");
      build.append(prefix + "NameOfPerson:  " + this.getNameOfPerson() + "\n");
      return build.toString();
   }

}
