package info.esblurock.reaction.chemconnect.core.common.expdata.async;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;



@RemoteServiceRelativePath("observationservices")
public interface ObservationServices  extends RemoteService {

	   public static class Util
	   {
	       private static ObservationServicesAsync instance;

	       public static ObservationServicesAsync getInstance()
	       {
	           if (instance == null)
	           {
	               instance = GWT.create(ObservationServices.class);
	           }
	           return instance;
	       }
	   }
	   
		public DatabaseObjectHierarchy createObservationBlockFromSpreadSheet(DatabaseObject obj, 
				String blocktype, DataCatalogID datid);
	   

}
