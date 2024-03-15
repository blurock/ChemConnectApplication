package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectStructureBase;

@Entity
@SuppressWarnings("serial")
public class DatasetImage extends ChemConnectStructureBase {

   @Index
   String imginfoS;

   public DatasetImage() {
   }

   public DatasetImage(ChemConnectStructureBase structure) {
      super(structure);
   }

   public String getImageInformation() {
         return imginfoS;
   }
   public void setImageInformation(String imginfoS) {
      this.imginfoS = imginfoS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("imginfoS", this.getImageInformation());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("imginfoS");
      if(param0 != null) {
         this.setImageInformation(param0);
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
      build.append(prefix + "ImageInformation:  " + this.getImageInformation() + "\n");
      return build.toString();
   }

}
