package info.esblurock.reaction.chemconnect.data;

import java.util.Map;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@Entity
@SuppressWarnings("serial")
public class SimpleCatalogObject extends DatabaseObject {

	@Index
	String catalogDataID;

	public SimpleCatalogObject() {
		super();
		this.catalogDataID = "";
	}
	
	public SimpleCatalogObject(SimpleCatalogObject simple) {
		super(simple);
		this.catalogDataID = simple.getCatalogDataID();
	}
	public SimpleCatalogObject(DatabaseObject obj) {
		super(obj);
		this.catalogDataID = "";
	}
	public String getCatalogDataID() {
		return catalogDataID;
	}

	public void setCatalogDataID(String catalogDataID) {
		this.catalogDataID = catalogDataID;
	}
	public void fillMapOfValues(Map<String,String> map) {
		super.fillMapOfValues(map);
	      map.put("catidS", this.getCatalogDataID());
	   }
public void retrieveFromMap(Map<String,String> map) {
		super.retrieveFromMap(map);
	      this.setCatalogDataID( map.get("catidS"));
	   }
@Override
public String toString() {
	return toString("");
}
@Override
public String toString(String prefix) {
	StringBuilder builder = new StringBuilder();
	builder.append(super.toString(prefix));
	builder.append(prefix);
	builder.append(prefix + "Catalog ID: ");
	builder.append(catalogDataID);
	builder.append("\n");
	return builder.toString();
}
	

}
