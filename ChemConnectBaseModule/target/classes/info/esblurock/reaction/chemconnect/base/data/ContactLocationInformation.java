package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class ContactLocationInformation extends ChemConnectCompoundBase {

   @Index
   String postcodeS;
   @Index
   String cityS;
   @Index
   String streetS;
   @Index
   String gpslocS;
   @Index
   String countryS;

   public ContactLocationInformation() {
   }

   public ContactLocationInformation(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getLocationPostcode() {
         return postcodeS;
   }
   public void setLocationPostcode(String postcodeS) {
      this.postcodeS = postcodeS;
   }

   public String getLocationCity() {
         return cityS;
   }
   public void setLocationCity(String cityS) {
      this.cityS = cityS;
   }

   public String getLocationAddress() {
         return streetS;
   }
   public void setLocationAddress(String streetS) {
      this.streetS = streetS;
   }

   public String getGPSLocation() {
         return gpslocS;
   }
   public void setGPSLocation(String gpslocS) {
      this.gpslocS = gpslocS;
   }

   public String getLocationCountry() {
         return countryS;
   }
   public void setLocationCountry(String countryS) {
      this.countryS = countryS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("postcodeS", this.getLocationPostcode());
      map.put("cityS", this.getLocationCity());
      map.put("streetS", this.getLocationAddress());
      map.put("gpslocS", this.getGPSLocation());
      map.put("countryS", this.getLocationCountry());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("postcodeS");
      if(param0 != null) {
         this.setLocationPostcode(param0);
      }
      String param1 = map.get("cityS");
      if(param1 != null) {
         this.setLocationCity(param1);
      }
      String param2 = map.get("streetS");
      if(param2 != null) {
         this.setLocationAddress(param2);
      }
      String param3 = map.get("gpslocS");
      if(param3 != null) {
         this.setGPSLocation(param3);
      }
      String param4 = map.get("countryS");
      if(param4 != null) {
         this.setLocationCountry(param4);
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
      build.append(prefix + "LocationPostcode:  " + this.getLocationPostcode() + "\n");
      build.append(prefix + "LocationCity:  " + this.getLocationCity() + "\n");
      build.append(prefix + "LocationAddress:  " + this.getLocationAddress() + "\n");
      build.append(prefix + "GPSLocation:  " + this.getGPSLocation() + "\n");
      build.append(prefix + "LocationCountry:  " + this.getLocationCountry() + "\n");
      return build.toString();
   }

}
