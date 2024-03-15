package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class ImageInformation extends ChemConnectCompoundBase {

   @Index
   String imgurlS;
   @Index
   String imgtypeS;

   public ImageInformation() {
   }

   public ImageInformation(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getImageURL() {
         return imgurlS;
   }
   public void setImageURL(String imgurlS) {
      this.imgurlS = imgurlS;
   }

   public String getImageType() {
         return imgtypeS;
   }
   public void setImageType(String imgtypeS) {
      this.imgtypeS = imgtypeS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("imgurlS", this.getImageURL());
      map.put("imgtypeS", this.getImageType());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("imgurlS");
      if(param0 != null) {
         this.setImageURL(param0);
      }
      String param1 = map.get("imgtypeS");
      if(param1 != null) {
         this.setImageType(param1);
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
      build.append(prefix + "ImageURL:  " + this.getImageURL() + "\n");
      build.append(prefix + "ImageType:  " + this.getImageType() + "\n");
      return build.toString();
   }

}
