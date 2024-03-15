package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class DescriptionDataData extends ChemConnectCompoundBase {

   @Index
   String abstractS;
   @Index
   String titleS;
   @Index
   String createS;
   @Index
   String purposeS;

   public DescriptionDataData() {
   }

   public DescriptionDataData(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getDescriptionAbstract() {
         return abstractS;
   }
   public void setDescriptionAbstract(String abstractS) {
      this.abstractS = abstractS;
   }

   public String getDescriptionTitle() {
         return titleS;
   }
   public void setDescriptionTitle(String titleS) {
      this.titleS = titleS;
   }

   public String getDateCreated() {
         return createS;
   }
   public void setDateCreated(String createS) {
      this.createS = createS;
   }

   public String getPurposeConceptPair() {
         return purposeS;
   }
   public void setPurposeConceptPair(String purposeS) {
      this.purposeS = purposeS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("abstractS", this.getDescriptionAbstract());
      map.put("titleS", this.getDescriptionTitle());
      map.put("createS", this.getDateCreated());
      map.put("purposeS", this.getPurposeConceptPair());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("abstractS");
      if(param0 != null) {
         this.setDescriptionAbstract(param0);
      }
      String param1 = map.get("titleS");
      if(param1 != null) {
         this.setDescriptionTitle(param1);
      }
      String param2 = map.get("createS");
      if(param2 != null) {
         this.setDateCreated(param2);
      }
      String param3 = map.get("purposeS");
      if(param3 != null) {
         this.setPurposeConceptPair(param3);
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
      build.append(prefix + "DescriptionAbstract:  " + this.getDescriptionAbstract() + "\n");
      build.append(prefix + "DescriptionTitle:  " + this.getDescriptionTitle() + "\n");
      build.append(prefix + "DateCreated:  " + this.getDateCreated() + "\n");
      build.append(prefix + "PurposeConceptPair:  " + this.getPurposeConceptPair() + "\n");
      return build.toString();
   }

}
