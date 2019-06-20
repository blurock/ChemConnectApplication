package info.esblurock.reaction.chemconnect.core.base.client.catalog.multiple;

import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public interface CreateMultipleItemCallback {
	public StandardDatasetObjectHierarchyItem addMultipleObject(DatabaseObjectHierarchy obj);
}
