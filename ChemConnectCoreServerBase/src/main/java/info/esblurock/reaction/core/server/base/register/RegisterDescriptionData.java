package info.esblurock.reaction.core.server.base.register;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.base.dataset.AuthorInformation;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectObjectLink;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataSetReference;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.base.dataset.PurposeConceptPair;
import info.esblurock.reaction.chemconnect.core.base.dataset.consortium.Consortium;

public class RegisterDescriptionData {
	public static void register() {
		
		ObjectifyService.register(DataSetReference.class);
		ObjectifyService.register(DescriptionDataData.class);
		ObjectifyService.register(AuthorInformation.class);
		ObjectifyService.register(PurposeConceptPair.class);
		ObjectifyService.register(Consortium.class);
		ObjectifyService.register(ChemConnectObjectLink.class);
		ObjectifyService.register(DataObjectLink.class);
		ObjectifyService.register(ChemConnectObjectLink.class);
		ObjectifyService.register(PurposeConceptPair.class);
		ObjectifyService.register(DataCatalogID.class);
	}
	public static void reset() {
		ResetDatabaseObjects.resetClass(DataSetReference.class);
		ResetDatabaseObjects.resetClass(DescriptionDataData.class);
		ResetDatabaseObjects.resetClass(AuthorInformation.class);
		ResetDatabaseObjects.resetClass(PurposeConceptPair.class);
		ResetDatabaseObjects.resetClass(Consortium.class);
		ResetDatabaseObjects.resetClass(ChemConnectObjectLink.class);
		ResetDatabaseObjects.resetClass(DataObjectLink.class);
		ResetDatabaseObjects.resetClass(ChemConnectObjectLink.class);
		ResetDatabaseObjects.resetClass(PurposeConceptPair.class);
		ResetDatabaseObjects.resetClass(DataCatalogID.class);
	}
}
