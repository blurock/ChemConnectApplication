package info.esblurock.reaction.core.data.expdata.data.observations;

import java.util.Map;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class ObservationSpecification extends ChemConnectCompoundDataStructure {

   @Index
   String ptypeS;
   @Index
   String speclabelS;

   public ObservationSpecification() {
   }

   public ObservationSpecification(ChemConnectCompoundDataStructure structure) {
      super(structure);
   }

   public String getObservationParameterType() {
         return ptypeS;
   }
   public void setObservationParameterType(String ptypeS) {
      this.ptypeS = ptypeS;
   }

   public String getSpecificationLabel() {
         return speclabelS;
   }
   public void setSpecificationLabel(String speclabelS) {
      this.speclabelS = speclabelS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("ptypeS", this.getObservationParameterType());
      map.put("speclabelS", this.getSpecificationLabel());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("ptypeS");
      if(param0 != null) {
         this.setObservationParameterType(param0);
      }
      String param1 = map.get("speclabelS");
      if(param1 != null) {
         this.setSpecificationLabel(param1);
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
      build.append(prefix + "ObservationParameterType:  " + this.getObservationParameterType() + "\n");
      build.append(prefix + "SpecificationLabel:  " + this.getSpecificationLabel() + "\n");
      return build.toString();
   }

}
