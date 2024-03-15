package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class GPSLocation extends ChemConnectCompoundBase {

   @Index
   String longS;
   @Index
   String latS;

   public GPSLocation() {
   }

   public GPSLocation(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getGPSLongitude() {
         return longS;
   }
   public void setGPSLongitude(String longS) {
      this.longS = longS;
   }

   public String getGPSLatitude() {
         return latS;
   }
   public void setGPSLatitude(String latS) {
      this.latS = latS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("longS", this.getGPSLongitude());
      map.put("latS", this.getGPSLatitude());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("longS");
      if(param0 != null) {
         this.setGPSLongitude(param0);
      }
      String param1 = map.get("latS");
      if(param1 != null) {
         this.setGPSLatitude(param1);
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
      build.append(prefix + "GPSLongitude:  " + this.getGPSLongitude() + "\n");
      build.append(prefix + "GPSLatitude:  " + this.getGPSLatitude() + "\n");
      return build.toString();
   }

}
