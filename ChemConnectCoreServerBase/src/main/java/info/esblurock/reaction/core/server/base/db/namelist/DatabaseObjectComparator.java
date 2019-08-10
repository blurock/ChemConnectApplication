package info.esblurock.reaction.core.server.base.db.namelist;

import java.util.Comparator;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;

public abstract class DatabaseObjectComparator implements Comparator<DatabaseObject>{
	String classType;

	public DatabaseObjectComparator(String classType) {
		super();
		this.classType = classType;
	}

	public String getClassType() {
		return classType;
	}
	
	public abstract String generateLabel(DatabaseObject obj);

}
