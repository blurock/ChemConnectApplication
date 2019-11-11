package info.esblurock.reaction.chemconnect.core.base.dataset;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class SimpleCatalogObject extends DatabaseObject {
	@Index
	String catalogDataID;
	
	public SimpleCatalogObject() {
		super();
	}

	public SimpleCatalogObject(SimpleCatalogObject simple) {
		super(simple);
		this.catalogDataID = simple.getCatalogDataID();
	}

	public SimpleCatalogObject(DatabaseObject obj, String catalogDataID) {
		super(obj);
		this.catalogDataID = catalogDataID;
	}

	public String getCatalogDataID(SimpleCatalogObject simple) {
		return catalogDataID;
	}

	public String getCatalogDataID() {
		return catalogDataID;
	}

	public void setCatalogDataID(String catalogDataID) {
		this.catalogDataID = catalogDataID;
	}

	@Override
	public String toString() {
		return toString("");
	}
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "Catalog ID: ");
		builder.append(catalogDataID);
		builder.append("\n");
		return builder.toString();
	}	
}
