package info.esblurock.reaction.core.server.base.catalog;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;

import info.esblurock.reaction.chemconnect.core.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.base.metadata.DatabaseKeys;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.chemconnect.core.base.session.DataSourceIdentification;
import info.esblurock.reaction.core.ontology.base.OntologyBase;
import info.esblurock.reaction.core.server.base.create.CreateContactObjects;
import info.esblurock.reaction.core.server.base.db.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.base.db.WriteBaseCatalogObjects;
import info.esblurock.reaction.core.server.base.register.RegisterChemConnectBaseCatalogObjects;
import info.esblurock.reaction.core.server.base.register.RegisterContactData;
import info.esblurock.reaction.core.server.base.register.RegisterDescriptionData;
import info.esblurock.reaction.core.server.base.register.RegisterGCSClasses;
import info.esblurock.reaction.core.server.base.register.RegisterImageInformation;
import info.esblurock.reaction.core.server.base.register.RegisterTransactionData;
import info.esblurock.reaction.core.server.base.register.RegisterUserLoginData;
import info.esblurock.reaction.core.server.base.util.TestBase;

class CreateOrganization extends TestBase {
	protected Closeable session;
	@BeforeAll
	public static void setUpBeforeClass() {
		System.out.println("setUpBeforeClass()");
	}

	@BeforeEach
	public void buildUp() throws Exception {
		ObjectifyService.init(new ObjectifyFactory(this.datastore(), memcache()));
		Closeable rootService = rootService = ObjectifyService.begin();
		RegisterChemConnectBaseCatalogObjects.register();
		RegisterContactData.register();
		RegisterDescriptionData.register();
		RegisterUserLoginData.register();
		RegisterImageInformation.register();
		RegisterGCSClasses.register();
		RegisterTransactionData.register();
	}

	@AfterEach
	public void tearDown() {
	}

	@Test
	public void test() {

		System.out.println("Start Query");
		String username = "guest";
		DataSourceIdentification s = new DataSourceIdentification(username);
		ofy().save().entity(s).now();

		DataSourceIdentification sourceID = ofy().load().type(DataSourceIdentification.class).id(username).now();

		System.out.println("Query Finished");
		System.out.println(sourceID);

		OntologyBase.Util.getDatabaseOntology();

		//ChemConnectDataStructure datastructure = new ChemConnectDataStructure();
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure();
		DatabaseObject obj = new DatabaseObject("blurockorg", "Public", "Blurock", "1");
		String shortname = "BlurockConsultingAB";
		String organizationname = "Blurock Consulting AB";
		DataCatalogID datid = new DataCatalogID(structure, "Guest", 
				DatabaseKeys.conceptOrgDataCatagory, "BlurockConsultingAB", new ArrayList<String>());
		DatabaseObjectHierarchy hierarchy = CreateContactObjects.fillOrganization(obj, shortname, organizationname, datid);

		System.out.println("CreateWriteCatalogObject\n" + hierarchy.toString());
		WriteBaseCatalogObjects.writeDatabaseObjectHierarchy(hierarchy);
		
		try {
			DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getTopCatalogObject(hierarchy.getObject().getIdentifier(),
					StandardDataKeywords.organization);
			System.out.println(readhierarchy);
		
		} catch (IOException e) {
			System.out.println("Failed: " + e.toString());
			e.printStackTrace();
		}
	}

}
