package info.esblurock.reaction.chemconnect.core.base.image;


import com.googlecode.objectify.annotation.Entity;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;

@SuppressWarnings("serial")
@Entity
public class InterpretInputDataObject extends DatabaseObject {

	public InterpretInputDataObject() {
	}
	public InterpretInputDataObject(DatabaseObject object) {
		super(object);
	}
	
	public String getType() {
		return getClass().getSimpleName();
	}
	
}
