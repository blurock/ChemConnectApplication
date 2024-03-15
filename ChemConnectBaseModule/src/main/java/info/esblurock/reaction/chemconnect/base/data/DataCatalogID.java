package info.esblurock.reaction.chemconnect.base.data;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.ChemConnectCompoundBase;

@Entity
@SuppressWarnings("serial")
public class DataCatalogID extends ChemConnectCompoundBase {

   @Index
   String catalogS;
   @Index
   String simpleS;
   @Index
   String baseS;

   public DataCatalogID() {
   }

   public DataCatalogID(ChemConnectCompoundBase structure) {
      super(structure);
   }

   public String getDataCatalog() {
         return catalogS;
   }
   public void setDataCatalog(String catalogS) {
      this.catalogS = catalogS;
   }

   public String getSimpleCatalogName() {
         return simpleS;
   }
   public void setSimpleCatalogName(String simpleS) {
      this.simpleS = simpleS;
   }

   public String getCatalogBaseName() {
         return baseS;
   }
   public void setCatalogBaseName(String baseS) {
      this.baseS = baseS;
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
      map.put("catalogS", this.getDataCatalog());
      map.put("simpleS", this.getSimpleCatalogName());
      map.put("baseS", this.getCatalogBaseName());
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
      String param0 = map.get("catalogS");
      if(param0 != null) {
         this.setDataCatalog(param0);
      }
      String param1 = map.get("simpleS");
      if(param1 != null) {
         this.setSimpleCatalogName(param1);
      }
      String param2 = map.get("baseS");
      if(param2 != null) {
         this.setCatalogBaseName(param2);
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
      build.append(prefix + "DataCatalog:  " + this.getDataCatalog() + "\n");
      build.append(prefix + "SimpleCatalogName:  " + this.getSimpleCatalogName() + "\n");
      build.append(prefix + "CatalogBaseName:  " + this.getCatalogBaseName() + "\n");
      return build.toString();
   }

}
