package info.esblurock.reaction.chemconnect.data.dataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import info.esblurock.reaction.chemconnect.data.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.data.DatabaseObject;

public class DatabaseObjectHierarchy implements Serializable {

	private static final long serialVersionUID = 1L;
	DatabaseObject object;
	HashMap<String, DatabaseObjectHierarchy> subobjects;

	public DatabaseObjectHierarchy() {
		object = null;
		init();
	}

	public DatabaseObjectHierarchy(DatabaseObject object) {
		this.object = object;
		init();
	}

	private void init() {
		subobjects = new HashMap<String, DatabaseObjectHierarchy>();
	}

	public DatabaseObject getObject() {
		return object;
	}

	public void setObject(DatabaseObject obj) {
		this.object = obj;
	}

	public DatabaseObjectHierarchy getSubObject(String name) {
		return subobjects.get(name);
	}

	public void transferSubObjects(DatabaseObjectHierarchy hierarchy) {
		ArrayList<DatabaseObjectHierarchy> subs = hierarchy.getSubobjects();
		for (DatabaseObjectHierarchy sub : subs) {
			this.addSubobject(sub);
			DatabaseObject obj = sub.getObject();
			if(obj instanceof ChemConnectCompoundDataStructure) {
				ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) obj;
				structure.setParentLink(object.getIdentifier());
			}
		}
	}

	public Set<String> getSubObjectKeys() {
		return subobjects.keySet();
	}

	public void replaceInfo(DatabaseObjectHierarchy newhier) {
		object = newhier.getObject();
		subobjects = new HashMap<String, DatabaseObjectHierarchy>();
		transferSubObjects(newhier);
	}
	
	public ArrayList<DatabaseObjectHierarchy> getSubobjects() {
		ArrayList<DatabaseObjectHierarchy> array = new ArrayList<DatabaseObjectHierarchy>();
		Set<String> names = subobjects.keySet();
		for (String name : names) {
			array.add(subobjects.get(name));
		}
		return array;
	}

	public void addSubobject(DatabaseObjectHierarchy objecthierarchy) {
		DatabaseObject obj = objecthierarchy.getObject();
		subobjects.put(obj.getIdentifier(), objecthierarchy);
	}

	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix);
		builder.append("---------- DatabaseObjectHierarchy ---------- " + subobjects.size() + "\n");
		String objprefix = prefix + " Obj: ";
		if (object != null) {
			builder.append(object.toString(objprefix));
		} else {
			builder.append(objprefix + " No object defined");
		}
		String newprefix = prefix + "\t:  ";
		Set<String> names = subobjects.keySet();
		if (names.size() > 0) {
			builder.append("\n");
			int count = 0;
			for (String name : names) {
				String subprefix = newprefix + count++ + ": ";
				DatabaseObjectHierarchy hierarchy = subobjects.get(name);
				builder.append(hierarchy.toString(subprefix));
			}
		} else {
			builder.append(newprefix + " no sub-objects in hierarchy\n");
		}
		return builder.toString();
	}
}
