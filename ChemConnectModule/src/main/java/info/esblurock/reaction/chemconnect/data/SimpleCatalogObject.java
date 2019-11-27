package info.esblurock.reaction.chemconnect.data;

import com.googlecode.objectify.annotation.Entity;

@Entity
@SuppressWarnings("serial")
public class SimpleCatalogObject extends DatabaseObject {

	public SimpleCatalogObject() {
		super();
	}
	
	public SimpleCatalogObject(SimpleCatalogObject simple) {
		super(simple);
	}
	

}
