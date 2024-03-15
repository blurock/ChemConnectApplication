package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class PurposeConceptPair extends ChemConnectCompoundBase {

   @Index
   String dataconceptS;
   @Index
   String purposeS;

   public PurposeConceptPair() {
   }

   public PurposeConceptPair(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getDataTypeConcept() {
         return dataconceptS;
   }
   public void setDataTypeConcept(String dataconceptS) {
      this.dataconceptS = dataconceptS;
   }

   public String getPurposeKeyword() {
         return purposeS;
   }
   public void setPurposeKeyword(String purposeS) {
      this.purposeS = purposeS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("dataconceptS", this.getDataTypeConcept());
      map.put("purposeS", this.getPurposeKeyword());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("dataconceptS");
      if(param0 != null) {
         this.setDataTypeConcept(param0);
      }
      String param1 = map.get("purposeS");
      if(param1 != null) {
         this.setPurposeKeyword(param1);
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
      build.append(prefix + "DataTypeConcept:  " + this.getDataTypeConcept() + "\n");
      build.append(prefix + "PurposeKeyword:  " + this.getPurposeKeyword() + "\n");
      return build.toString();
   }

}
