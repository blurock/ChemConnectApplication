package info.esblurock.reaction.chemconnect.core.base.client.catalog.choose;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public interface ObjectVisualizationInterface {
	public void createCatalogObject(DatabaseObject obj,DataCatalogID catid);
	public void insertCatalogObject(DatabaseObjectHierarchy subs);
}
