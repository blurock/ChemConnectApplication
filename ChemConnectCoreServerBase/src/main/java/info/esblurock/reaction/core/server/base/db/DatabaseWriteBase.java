package info.esblurock.reaction.core.server.base.db;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.image.UploadedImage;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.base.query.QueryPropertyValue;
import info.esblurock.reaction.chemconnect.core.base.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.base.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.base.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.base.transaction.TransactionInfo;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;
import info.esblurock.reaction.core.server.base.queries.QueryBase;
import info.esblurock.reaction.core.server.base.services.util.RegisterEvent;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;
/*
import info.esblurock.reaction.chemconnect.core.data.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactInfoData;
import info.esblurock.reaction.chemconnect.core.data.contact.ContactLocationInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.data.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.data.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.data.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.data.description.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.data.login.UserAccountInformation;
import info.esblurock.reaction.chemconnect.core.data.transfer.DataElementInformation;
import info.esblurock.reaction.io.db.QueryBase;
import info.esblurock.reaction.io.metadata.StandardDatasetMetaData;
import info.esblurock.reaction.ontology.OntologyBase;
import info.esblurock.reaction.ontology.dataset.DatasetOntologyParsing;

*/
import java.util.List;

import com.google.cloud.datastore.QueryResults;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

/**
 * @author edwardblurock
 * 
 *         These routines are the primitives for reading and writing, using
 *         Objectify, to the database.
 * 
 *         Data objects are derived from DatabaseObject. Other objects in the
 *         database are not derived from DatabaseObjects (and they have their
 *         own write routines)
 *         <ul>
 *         <li>UploadedImage: Image management
 *         <li>UserSessionData: This keeps track of the user's logged on session
 *         (created to replace use of session variables)
 *         <li>TransactionInfo: Registers a transaction
 *         <ul>
 * 
 *
 */
public class DatabaseWriteBase {

	/**
	 * Write a DatabaseObject and those objects derived from DatabaseObject
	 * 
	 * @param object a single database objec
	 */
	public static void writeDatabaseObject(DatabaseObject object) {
		ObjectifyService.ofy().save().entity(object).now();
	}

	/**
	 * Write a list of images (UploadedImage not derived from DatabaseObject)
	 * 
	 * @param images a list of images
	 */
	public static void writeListOfDatabaseObjects(ArrayList<UploadedImage> images) {
		ObjectifyService.ofy().save().entities(images);
	}

	public static void writeListOfDatabaseObjects(List<DatabaseObject> lst) {
		ObjectifyService.ofy().save().entities(lst).now();
	}

	public static void writeEntity(Object entity) {
		ObjectifyService.ofy().save().entity(entity).now();
	}

	/**
	 * This deletes all sessions associated with the username within the account
	 * given.
	 * 
	 * @param account The account with the username
	 */
	public static void deleteUserSessionData(UserSessionData account) {
		if (account != null) {
			if (account.getUserName() != null) {
				// System.out.println("deleteUserSessionData: \n" +
				// account.toString("DatabaseWriteBase: "));
				Query<UserSessionData> query = ofy().load().type(UserSessionData.class);
				QueryResults<UserSessionData> result = query.filter("userName", account.getUserName()).iterator();
				while (result.hasNext()) {
					UserSessionData nameaccount = result.next();
					ObjectifyService.ofy().delete().entity(nameaccount);
				}
			} else {
				System.out.println("deleteUserSessionData: account userName is null");
			}
		} else {
			System.out.println("deleteUserSessionData: account is null");
		}
	}

	/**
	 * This should be the only place the UserSessionData should be written. This
	 * write involves deleting all other sessions of the user.
	 * 
	 * @param account The session data to be written
	 */
	public static void writeUserSessionData(UserSessionData account) {
		if (account != null) {
			if (account.getUserName() != null) {
				deleteUserSessionData(account);
				writeEntity(account);
			} else {
				System.out.println("writeUserSessionData: account userName is null");
			}
		} else {
			System.out.println("writeUserSessionData: account is null");
		}
	}

	/**
	 * This is retrieving the current session using the username.
	 * 
	 * @param username The current username
	 * @return The session data
	 * 
	 *         The primary access to session data should be from session id, not the
	 *         username.
	 */
	public static UserSessionData getUserSessionData(String username) {
		UserSessionData account = null;
		if (username != null) {
			Query<UserSessionData> query = ofy().load().type(UserSessionData.class);
			QueryResults<UserSessionData> result = query.filter("userName", username).iterator();
			while (result.hasNext()) {
				account = result.next();
			}
		} else {
			System.out.println("Username is null");
		}
		return account;
	}

	/**
	 * This is retrieving the current session using the session ID. This should be
	 * the primary method of accessing the UserSessionData.
	 * 
	 * @param sessionID The session id (from the server)
	 * @return The session information
	 */
	public static UserSessionData getUserSessionDataFromSessionID(String sessionID) {
		UserSessionData account = null;
		if (sessionID != null) {
			Query<UserSessionData> query = ofy().load().type(UserSessionData.class);
			QueryResults<UserSessionData> result = query.filter("sessionID", sessionID).iterator();
			while (result.hasNext()) {
				account = result.next();
			}
		} else {
			System.out.println("getUserSessionDataFromSessionID:  Session ID is null");
		}
		return account;
	}

	public static UserAccount userAccountFromAuthorizationName(String auth_id) throws IOException {
		UserAccount useraccount = (UserAccount) QueryBase.getFirstDatabaseObjectsFromSingleProperty(
				UserAccount.class.getCanonicalName(), "authorizationName", auth_id);
		return useraccount;
	}

	public static void deleteTransactionInfo(TransactionInfo info, String datatype) throws IOException {
		String ID = info.getIdentifier();
		DeleteBaseCatalogStructures.deleteObject(datatype, ID);
		DatabaseObjectHierarchy hierarchy = ExtractCatalogInformation.getCatalogObject(info.getIdentifier(), datatype);
		WriteReadDatabaseObjects.deleteHierarchy(hierarchy);
		ObjectifyService.ofy().delete().entity(info);
	}

	/**
	 * This writes the transaction object and the TransactionInfo.
	 * 
	 * @param id       The keyword id of the transaction
	 * @param access   access of the objects in the transaction
	 * @param owner    the owner of the transaction
	 * @param sourceID the sourceID of the transaction
	 * @param object   The associated information object associated with the
	 *                 transaction.
	 * 
	 *                 This writes the transaction object and the TransactionInfo.
	 *                 The TransactionInfo uses the base parameters (id,access,owner
	 *                 and sourceID) of the object.
	 * 
	 */
	static public void writeTransactionWithoutObjectWrite(DatabaseObject object, String event) {
		String classname = object.getClass().getName();
		TransactionInfo transaction = new TransactionInfo(object, event);
		transaction.setStoredObjectKey(object.getKey());
		writeDatabaseObject(transaction);
	}

	/**
	 * This writes the transaction object and the TransactionInfo.
	 * 
	 * @param id       The keyword id of the transaction
	 * @param access   access of the objects in the transaction
	 * @param owner    the owner of the transaction
	 * @param sourceID the sourceID of the transaction
	 * @param object   The associated information object associated with the
	 *                 transaction.
	 * 
	 *                 This writes the transaction object and the TransactionInfo.
	 *                 The TransactionInfo uses the base parameters (id,access,owner
	 *                 and sourceID) of the object.
	 * @throws IOException
	 * 
	 */
	static public void writeObjectWithTransaction(DatabaseObject object, String event) {
		String classname = object.getClass().getName();

		String transclass = TransactionInfo.class.getCanonicalName();
		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		QueryPropertyValue value1 = new QueryPropertyValue("identifier", object.getIdentifier());
		values.add(value1);
		QueryPropertyValue value2 = new QueryPropertyValue("transactionObjectType", classname);
		values.add(value2);
		QuerySetupBase query = new QuerySetupBase(object.getOwner(), transclass, values);
		SingleQueryResult result;
		try {
			result = QueryBase.StandardQueryResult(query);
			List<DatabaseObject> objs = result.getResults();
			for (DatabaseObject obj : objs) {
				TransactionInfo info = (TransactionInfo) obj;
				String datatype = info.getTransactionObjectType();
				int pos = datatype.lastIndexOf('.');
				if (pos > 0) {
					String type = DatasetOntologyParseBase.getTypeFromCanonicalDataType(datatype);
					deleteTransactionInfo(info, type);
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("writeObjectWithTransaction: ClassNotFoundException ");
		} catch (IOException ex) {
			System.out.println("writeObjectWithTransaction: IOException ");
		}
		TransactionInfo transaction = new TransactionInfo(object, event);
		writeDatabaseObject(object);
		transaction.setStoredObjectKey(object.getKey());
		writeDatabaseObject(transaction);
	}

	static public void writeObjectWithTransaction(DatabaseObjectHierarchy hierarchy, String event,
			UserSessionData session) throws IOException {
		WriteBaseCatalogObjects.writeDatabaseObjectHierarchy(hierarchy);
		TransactionInfo transaction = new TransactionInfo(hierarchy.getObject(), event);
		RegisterEvent.register(session, transaction, RegisterEvent.checkLevel3);
	}

}
