package info.esblurock.reaction.core.server.base.db;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public class WriteBaseCatalogObjects {
	
	public static DatabaseObjectHierarchy writeDatabaseObjectHierarchyWithTransaction(DatabaseObjectHierarchy objecthierarchy,
			String event) {
		DatabaseWriteBase.writeTransactionWithoutObjectWrite(objecthierarchy.getObject(),event);
		return writeDatabaseObjectHierarchy(objecthierarchy);
	}
	public static DatabaseObjectHierarchy writeDatabaseObjectHierarchy(DatabaseObjectHierarchy objecthierarchy) {
		writeDatabaseObjectHierarchyRecursive(objecthierarchy);
		return objecthierarchy;
	}
		
	public static void writeDatabaseObjectHierarchyRecursive(DatabaseObjectHierarchy objecthierarchy) {
		DatabaseObject topobject = objecthierarchy.getObject();
		DatabaseWriteBase.writeDatabaseObject(topobject);
		for (DatabaseObjectHierarchy subhierarchy : objecthierarchy.getSubobjects()) {
			writeDatabaseObjectHierarchyRecursive(subhierarchy);
		}
	}

}
