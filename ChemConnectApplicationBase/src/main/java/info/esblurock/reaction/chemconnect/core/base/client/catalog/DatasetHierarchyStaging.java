package info.esblurock.reaction.chemconnect.core.base.client.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gwt.user.client.Window;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.util.TextUtilities;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public class DatasetHierarchyStaging implements Comparator<DatasetHierarchyStaging> {
	
	int priority;
	SetUpCollapsibleItemInterface setup;
	DatabaseObjectHierarchy hierarchy;
	String type;
	DatabaseObject object;
	
	/*
	 * This class is used to order the DatasetHierarchy items using the priority in
	 * SetUpCollapsibleItem
	 * 
	 */
	public static ArrayList<DatasetHierarchyStaging> computeStaging(DatabaseObjectHierarchy hierarchy) {
		ArrayList<DatasetHierarchyStaging> lst = new ArrayList<DatasetHierarchyStaging>();
		for(DatabaseObjectHierarchy  sub : hierarchy.getSubobjects()) {
			DatasetHierarchyStaging substage = new DatasetHierarchyStaging(sub);
			lst.add(substage);
		}
		Collections.sort(lst, new Comparator<DatasetHierarchyStaging> () {
			@Override
			public int compare(DatasetHierarchyStaging o1, DatasetHierarchyStaging o2) {
				return o2.getPriority() - o1.getPriority();
			}
		});
		return lst;
	}
	
	public DatasetHierarchyStaging(DatabaseObjectHierarchy hierarchy) {
		super();
		object = hierarchy.getObject();
		type = TextUtilities.removeNamespace(object.getClass().getSimpleName());
		setup = getSetup(hierarchy.getObject());
		priority = setup.priority();
		this.hierarchy = hierarchy;
	}
	public int getPriority() {
		return priority;
	}
	public SetUpCollapsibleItemInterface getSetup() {
		return setup;
	}

	public DatabaseObjectHierarchy getHierarchy() {
		return hierarchy;
	}

	@Override
	public int compare(DatasetHierarchyStaging o1, DatasetHierarchyStaging o2) {
		return o1.getPriority() - o2.getPriority();
	}
	public static SetUpCollapsibleItemInterface getSetup(DatabaseObject object) {
		String structure = object.getClass().getSimpleName();
		SetUpCollapsibleItemInterface setup = null;
		try {
			setup = SetUpCollapsibleItemBase.valueOf(structure);
		} catch (Exception ex) {
			String message = structure + " has not been not found: " + ex.getClass().getSimpleName() + "\n"
					+ structure + " not found: " + SetUpCollapsibleItemBase.values();
			StandardWindowVisualization.errorWindowMessage("DatasetHierarchyStaging", message);
		}
		return setup;
	}

}
