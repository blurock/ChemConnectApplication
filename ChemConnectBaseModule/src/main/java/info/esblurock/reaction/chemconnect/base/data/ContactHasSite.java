package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class ContactHasSite extends ChemConnectCompoundBase {

   @Index
   String httptypeS;
   @Index
   String httpS;

   public ContactHasSite() {
   }

   public ContactHasSite(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getHttpAddressType() {
         return httptypeS;
   }
   public void setHttpAddressType(String httptypeS) {
      this.httptypeS = httptypeS;
   }

   public String getHTTPAddress() {
         return httpS;
   }
   public void setHTTPAddress(String httpS) {
      this.httpS = httpS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("httptypeS", this.getHttpAddressType());
      map.put("httpS", this.getHTTPAddress());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("httptypeS");
      if(param0 != null) {
         this.setHttpAddressType(param0);
      }
      String param1 = map.get("httpS");
      if(param1 != null) {
         this.setHTTPAddress(param1);
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
      build.append(prefix + "HttpAddressType:  " + this.getHttpAddressType() + "\n");
      build.append(prefix + "HTTPAddress:  " + this.getHTTPAddress() + "\n");
      return build.toString();
   }

}
