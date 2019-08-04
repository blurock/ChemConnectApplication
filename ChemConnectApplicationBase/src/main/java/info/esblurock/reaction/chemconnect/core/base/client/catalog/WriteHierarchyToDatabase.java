package info.esblurock.reaction.chemconnect.core.base.client.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;

public class WriteHierarchyToDatabase {
	
	public static void writeDatabaseObjectHierarchy(DatabaseObjectHierarchy hierarchy) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		//String simpleclass = hierarchy.getObject().getClass().getSimpleName();
		//String id = hierarchy.getObject().getIdentifier();
		async.writeDatabaseObjectHierarchy(hierarchy, new AsyncCallback<DatabaseObjectHierarchy>() {

			@Override
			public void onFailure(Throwable caught) {
				StandardWindowVisualization.errorWindowMessage("Write Database",caught.toString());
			}

			@Override
			public void onSuccess(DatabaseObjectHierarchy result) {
				StandardWindowVisualization.successWindowMessage("Successfully wrote object");
			}
			
		});
	}

	public static void writeYamlObjectHierarchy(DatabaseObjectHierarchy hierarchy) {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		String simpleclass = hierarchy.getObject().getClass().getSimpleName();
		String id = hierarchy.getObject().getIdentifier();
		async.writeYamlObjectHierarchy(id, simpleclass,  new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				StandardWindowVisualization.errorWindowMessage("YAML save",caught.toString());
			}

			@Override
			public void onSuccess(Void result) {
				StandardWindowVisualization.successWindowMessage("Successful YAML save");
			}
			
		});
		
	}
 }
