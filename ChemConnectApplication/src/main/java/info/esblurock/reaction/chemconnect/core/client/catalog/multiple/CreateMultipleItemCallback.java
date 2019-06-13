package info.esblurock.reaction.chemconnect.core.client.catalog.multiple;

import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.client.catalog.StandardDatasetObjectHierarchyItem;

public interface CreateMultipleItemCallback {
	public StandardDatasetObjectHierarchyItem addMultipleObject(DatabaseObjectHierarchy obj);
}
