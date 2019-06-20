package info.esblurock.reaction.chemconnect.core.common.base.client.async;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.base.transfer.ChemConnectRecordInformation;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;

public interface BaseCatalogAccessAsync {

	void getGPSLocation(DatabaseObject obj, String city, String country, AsyncCallback<GPSLocation> callback);

	void getChemConnectRecordInformation(DatabaseObject obj, AsyncCallback<ChemConnectRecordInformation> callback);

	void hierarchyOfConcepts(String topnode, AsyncCallback<HierarchyNode> callback);

	void hierarchyFromPrimitiveStructure(String structure, AsyncCallback<HierarchyNode> callback);

	void hierarchyOfConceptsWithLevelLimit(String topnode, int maxlevel, AsyncCallback<HierarchyNode> callback);
}
