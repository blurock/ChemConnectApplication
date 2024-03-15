package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class DataSetReference extends ChemConnectCompoundBase {

   @Index
   String doiS;
   @Index
   String refstrS;
   @Index
   String reftitleS;

   public DataSetReference() {
   }

   public DataSetReference(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getDOI() {
         return doiS;
   }
   public void setDOI(String doiS) {
      this.doiS = doiS;
   }

   public String getReferenceString() {
         return refstrS;
   }
   public void setReferenceString(String refstrS) {
      this.refstrS = refstrS;
   }

   public String getReferenceTitle() {
         return reftitleS;
   }
   public void setReferenceTitle(String reftitleS) {
      this.reftitleS = reftitleS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("doiS", this.getDOI());
      map.put("refstrS", this.getReferenceString());
      map.put("reftitleS", this.getReferenceTitle());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("doiS");
      if(param0 != null) {
         this.setDOI(param0);
      }
      String param1 = map.get("refstrS");
      if(param1 != null) {
         this.setReferenceString(param1);
      }
      String param2 = map.get("reftitleS");
      if(param2 != null) {
         this.setReferenceTitle(param2);
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
      build.append(prefix + "DOI:  " + this.getDOI() + "\n");
      build.append(prefix + "ReferenceString:  " + this.getReferenceString() + "\n");
      build.append(prefix + "ReferenceTitle:  " + this.getReferenceTitle() + "\n");
      return build.toString();
   }

}
