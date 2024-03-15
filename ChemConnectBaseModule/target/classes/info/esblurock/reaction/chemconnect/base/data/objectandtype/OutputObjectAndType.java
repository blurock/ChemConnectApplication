package info.esblurock.reaction.chemconnect.base.data.objectandtype;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.objectandtype.ObjectIDAndType;

@Entity
@SuppressWarnings("serial")
public class OutputObjectAndType extends ObjectIDAndType {

   @Index
   String catobjtypeS;
   @Index
   String catobjidS;

   public OutputObjectAndType() {
   }

   public OutputObjectAndType(ObjectIDAndType structure) {
      super(structure);
   }

   public String getCatalogObjectType() {
         return catobjtypeS;
   }
   public void setCatalogObjectType(String catobjtypeS) {
      this.catobjtypeS = catobjtypeS;
   }

   public String getCatalogObjectID() {
         return catobjidS;
   }
   public void setCatalogObjectID(String catobjidS) {
      this.catobjidS = catobjidS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("catobjtypeS", this.getCatalogObjectType());
      map.put("catobjidS", this.getCatalogObjectID());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("catobjtypeS");
      if(param0 != null) {
         this.setCatalogObjectType(param0);
      }
      String param1 = map.get("catobjidS");
      if(param1 != null) {
         this.setCatalogObjectID(param1);
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
      build.append(prefix + "CatalogObjectType:  " + this.getCatalogObjectType() + "\n");
      build.append(prefix + "CatalogObjectID:  " + this.getCatalogObjectID() + "\n");
      return build.toString();
   }

}
