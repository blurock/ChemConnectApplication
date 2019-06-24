package info.esblurock.reaction.core.server.base.register;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;

public class RegisterChemConnectBaseCatalogObjects  implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ObjectifyService.init();
		RegisterContactData.register();
		RegisterDescriptionData.register();
		RegisterUserLoginData.register();
		RegisterImageInformation.register();
		RegisterGCSClasses.register();
		RegisterTransactionData.register();
		
		ObjectifyService.register(DatabaseObject.class);
		ObjectifyService.register(ChemConnectCompoundMultiple.class);
		ObjectifyService.register(ChemConnectDataStructure.class);
		ObjectifyService.register(ChemConnectCompoundDataStructure.class);
		ObjectifyService.register(DatasetCatalogHierarchy.class);
		ObjectifyService.register(UserSessionData.class);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
}
