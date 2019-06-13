package info.esblurock.reaction.core.server.base.catalog;

import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import info.esblurock.reaction.chemconnect.core.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.chemconnect.core.base.session.DataSourceIdentification;
import info.esblurock.reaction.core.ontology.base.OntologyBase;
import info.esblurock.reaction.core.server.base.create.CreateContactObjects;
import info.esblurock.reaction.core.server.base.db.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.base.register.RegisterChemConnectBaseCatalogObjects;
import info.esblurock.reaction.core.server.base.register.RegisterContactData;
import info.esblurock.reaction.core.server.base.register.RegisterDescriptionData;
import info.esblurock.reaction.core.server.base.register.RegisterGCSClasses;
import info.esblurock.reaction.core.server.base.register.RegisterImageInformation;
import info.esblurock.reaction.core.server.base.register.RegisterTransactionData;
import info.esblurock.reaction.core.server.base.register.RegisterUserLoginData;
import info.esblurock.reaction.core.server.base.util.TestBase;

public class CreateWriteCatalogObject extends TestBase {

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

		String accountUserName = "guest";
		String authorizationName = "Guest";
		String authorizationType = "default";
		String accountPrivilege = UserAccountKeys.accessQueryDataQueryUser;
		ChemConnectDataStructure datastructure = new ChemConnectDataStructure();
		UserAccount sessiondata = new UserAccount(datastructure, accountUserName, authorizationName, authorizationType,
				accountPrivilege);
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure();
		NameOfPerson person = new NameOfPerson(structure, "Prof.", "Wiley E.", "Coyote");
		System.out.println(sessiondata.toString("UserAccount: "));
		DatabaseObjectHierarchy hierarchy = CreateContactObjects.createNewUser(sessiondata, person);

		System.out.println("CreateWriteCatalogObject\n" + hierarchy.toString());
		
		try {
			DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getTopCatalogObject(hierarchy.getObject().getIdentifier(),
					StandardDataKeywords.individualInformation);
			System.out.println(readhierarchy);
		} catch (IOException e) {
			System.out.println("Failed: " + e.toString());
			e.printStackTrace();
		}
	}

}
