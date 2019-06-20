package info.esblurock.reaction.chemconnect.core.common.base.client.async;

import java.io.IOException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.base.transfer.ChemConnectRecordInformation;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;

@RemoteServiceRelativePath("catalogaccess")
public interface BaseCatalogAccess extends RemoteService {
	/*
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static BaseCatalogAccessAsync instance;

		public static BaseCatalogAccessAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(BaseCatalogAccess.class);
			}
			return instance;
		}
	}
	
	GPSLocation getGPSLocation(DatabaseObject obj, String city, String country) throws IOException;
	ChemConnectRecordInformation getChemConnectRecordInformation(DatabaseObject obj) throws IOException;
	HierarchyNode hierarchyOfConcepts(String topnode);
	HierarchyNode hierarchyOfConceptsWithLevelLimit(String topnode, int maxlevel);
	HierarchyNode hierarchyFromPrimitiveStructure(String structure);
}
