package info.esblurock.reaction.core.server.base.create;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import info.esblurock.reaction.chemconnect.core.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.contact.ContactInfoData;
import info.esblurock.reaction.chemconnect.core.base.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.contact.Organization;
import info.esblurock.reaction.chemconnect.core.base.contact.OrganizationDescription;
import info.esblurock.reaction.chemconnect.core.base.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.base.dataset.ContactHasSite;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.base.dataset.PurposeConceptPair;
import info.esblurock.reaction.chemconnect.core.base.image.DatasetImage;
import info.esblurock.reaction.chemconnect.core.base.image.ImageInformation;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.base.metadata.DatabaseKeys;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardTextStatements;
import info.esblurock.reaction.chemconnect.core.base.transfer.ChemConnectDataElementInformation;
import info.esblurock.reaction.chemconnect.core.base.transfer.ChemConnectRecordInformation;
import info.esblurock.reaction.chemconnect.core.base.transfer.CompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.base.utilities.ClassificationInformation;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;
import info.esblurock.reaction.core.server.base.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.base.db.WriteBaseCatalogObjects;
//import info.esblurock.reaction.core.server.base.db.DatabaseWriteBase;
//import info.esblurock.reaction.core.server.base.db.WriteReadDatabaseObjects;
import info.esblurock.reaction.core.server.base.queries.QueryBase;
import info.esblurock.reaction.core.server.base.services.util.ContextAndSessionUtilities;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseData;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseDataUtilities;

public class CreateContactObjects {
	public static DatabaseObjectHierarchy createNewUser(UserAccount uaccount, NameOfPerson person) {
		String account = uaccount.getAccountUserName();
		String accountClassification = uaccount.getAuthorizationType();
		String sourceID = QueryBase.getDataSourceIdentification(account);
		String id = "User";
		DatabaseObject object = new DatabaseObject(id, account, account, sourceID);
		ArrayList<String> path = new ArrayList<String>();
		path.add("User");
		path.add(account);
		String catalogBaseName = "User";
		String dataCatalog = ChemConnectCompoundDataStructure.removeNamespace(DatabaseKeys.conceptUserDataCatagory);
		String simpleCatalogName = "User";
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure();
		DataCatalogID datid = new DataCatalogID(structure, catalogBaseName, dataCatalog, simpleCatalogName, path);

		DatabaseObjectHierarchy user = fillMinimalPersonDescription(object, account, accountClassification, person,
				datid);
		DatabaseObjectHierarchy usercat = fillCataogHierarchyForUser(object, account, user.getObject().getIdentifier(),
				datid);
		WriteBaseCatalogObjects.writeDatabaseObjectHierarchy(usercat);

		connectSubCatagory("PublishedResults", "Catagory for published results",
				MetaDataKeywords.publishedResultsCatagory, usercat);
		DatabaseObjectHierarchy expcat = connectSubCatagory("ExperimentalDataSets",
				"Catagory for experimental data sets", MetaDataKeywords.experimentalSetDataCatagory, usercat);
		connectSubCatagory("ExampleData", "Catagory for example data", MetaDataKeywords.experimentalSetDataCatagory,
				expcat);

		DatabaseObjectHierarchy acchier = InterpretBaseData.UserAccount.createEmptyObject(object);
		UserAccount useraccount = (UserAccount) acchier.getObject();
		useraccount.setAccountUserName(account);
		useraccount.setAuthorizationName(uaccount.getAuthorizationName());
		useraccount.setAuthorizationType(uaccount.getAuthorizationType());
		useraccount.setAccountPrivilege(uaccount.getAccountPrivilege());
		WriteBaseCatalogObjects.writeDatabaseObjectHierarchy(user);
		WriteBaseCatalogObjects.writeDatabaseObjectHierarchy(acchier);
		return user;
	}

	static DatabaseObjectHierarchy fillDescriptionDataData(DatabaseObject obj, String onelinedescription,
			String concept, String purpose) {
		DatabaseObjectHierarchy descrhier = InterpretBaseData.DescriptionDataData.createEmptyObject(obj);
		DescriptionDataData descr = (DescriptionDataData) descrhier.getObject();
		descr.setOnlinedescription(onelinedescription);

		setOneLineDescription(descrhier, onelinedescription);
		setPurposeConceptPair(descrhier, concept, purpose);

		return descrhier;
	}

	public static DatabaseObjectHierarchy fillMinimalPersonDescription(DatabaseObject obj, String username,
			String userClassification, NameOfPerson person, DataCatalogID datid) {
		String uid = obj.getIdentifier() + "-" + username;
		obj.setIdentifier(uid);
		System.out.println("fillMinimalPersonDescription: InterpretBaseData.IndividualInformation");
		DatabaseObjectHierarchy infohier = InterpretBaseData.IndividualInformation.createEmptyObject(obj);
		System.out.println(infohier);
		IndividualInformation info = (IndividualInformation) infohier.getObject();
		DatabaseObjectHierarchy personalhier = infohier.getSubObject(info.getPersonalDescriptionID());
		PersonalDescription personal = (PersonalDescription) personalhier.getObject();
		personal.setUserClassification(userClassification);

		insertDataCatalogID(infohier, datid);

		String nameID = personal.getNameOfPersonIdentifier();
		DatabaseObjectHierarchy namehier = personalhier.getSubObject(nameID);
		NameOfPerson name = (NameOfPerson) namehier.getObject();
		name.setTitle(person.getTitle());
		name.setGivenName(person.getGivenName());
		name.setFamilyName(person.getFamilyName());

		String onlinedescription = person.getGivenName() + " " + person.getFamilyName();
		setOneLineDescription(infohier, onlinedescription);
		setDescriptionInHierarchy(infohier, info.getDescriptionDataData(), StandardTextStatements.personDescription,
				username);

		createContactInfoData(infohier, info.getContactInfoData(), MetaDataKeywords.emailContactType,
				"first.last@email.com");
		createContactInfoData(infohier, info.getContactInfoData(), MetaDataKeywords.telephoneContactType,
				"+00-000-000-0000");
		createContactHasSite(infohier, info.getContactHasSite(), MetaDataKeywords.companyHomepage,
				"https://homepage.com");

		String concept = MetaDataKeywords.chemConnectContactUser;
		String purpose = MetaDataKeywords.purposeUser;
		setPurposeConceptPair(infohier, concept, purpose);

		return infohier;
	}

	public static void createContactInfoData(DatabaseObjectHierarchy infohier, String id, String contactType,
			String contactkey) {
		DatabaseObjectHierarchy contactmulthier = infohier.getSubObject(id);

		ChemConnectCompoundMultiple contactmult = (ChemConnectCompoundMultiple) contactmulthier.getObject();
		int numlinks = contactmulthier.getSubobjects().size();
		String numlinkS = Integer.toString(numlinks);

		DatabaseObjectHierarchy contacthier = InterpretBaseData.ContactInfoData.createEmptyObject(contactmult);
		ContactInfoData contact = (ContactInfoData) contacthier.getObject();
		String newid = contact.getIdentifier() + numlinkS;
		contact.setIdentifier(newid);

		contactmulthier.addSubobject(contacthier);

		contact.setContactType(contactType);
		contact.setContact(contactkey);
	}

	public static void createContactHasSite(DatabaseObjectHierarchy infohier, String id, String siteType,
			String sitekey) {
		DatabaseObjectHierarchy contactmulthier = infohier.getSubObject(id);
		ChemConnectCompoundMultiple contactmult = (ChemConnectCompoundMultiple) contactmulthier.getObject();
		int numlinks = contactmulthier.getSubobjects().size();
		String numlinkS = Integer.toString(numlinks);

		DatabaseObjectHierarchy contacthier = InterpretBaseData.ContactHasSite.createEmptyObject(contactmult);
		ContactHasSite site = (ContactHasSite) contacthier.getObject();
		String newid = site.getIdentifier() + numlinkS;
		site.setIdentifier(newid);

		contactmulthier.addSubobject(contacthier);

		site.setHttpAddressType(siteType);
		site.setHttpAddress(sitekey);
	}

	public static void insertDataCatalogID(DatabaseObjectHierarchy hierarchy, DataCatalogID datid) {
		ChemConnectDataStructure info = (ChemConnectDataStructure) hierarchy.getObject();
		DatabaseObjectHierarchy catidhier = hierarchy.getSubObject(info.getCatalogDataID());
		DataCatalogID catid = (DataCatalogID) catidhier.getObject();
		catid.setCatalogBaseName(datid.getCatalogBaseName());
		catid.setDataCatalog(datid.getDataCatalog());
		catid.setSimpleCatalogName(datid.getSimpleCatalogName());
		catid.setPath(new ArrayList<String>(datid.getPath()));
	}

	public static DatabaseObjectHierarchy fillOrganization(DatabaseObject obj, String shortname,
			String organizationname, DataCatalogID datid) {
		String concept = MetaDataKeywords.conceptContact;
		String purpose = MetaDataKeywords.purposeOrganization;
		String uid = obj.getIdentifier() + "-" + shortname;
		obj.setIdentifier(uid);

		DatabaseObjectHierarchy orghier = InterpretBaseData.Organization.createEmptyObject(obj);
		Organization org = (Organization) orghier.getObject();

		DatabaseObjectHierarchy orgdescrhier = orghier.getSubObject(org.getOrganizationDescriptionID());
		OrganizationDescription orgdescr = (OrganizationDescription) orgdescrhier.getObject();
		orgdescr.setOrganizationName(organizationname);

		insertDataCatalogID(orghier, datid);

		setOneLineDescription(orghier, organizationname);
		setPurposeConceptPair(orghier, concept, purpose);

		return orghier;
	}

	public static DatabaseObjectHierarchy fillDatasetImage(DatabaseObject obj, DataCatalogID catid, String imageType,
			String imageURL) {
		DatabaseObjectHierarchy hierarchy = InterpretBaseData.DatasetImage.createEmptyObject(obj);
		DatasetImage image = (DatasetImage) hierarchy.getObject();
		DatabaseObjectHierarchy infohierarchy = hierarchy.getSubObject(image.getImageInformation());
		ImageInformation information = (ImageInformation) infohierarchy.getObject();
		information.setImageType(imageType);
		information.setImageURL(imageURL);

		insertDataCatalogID(hierarchy, catid);

		return hierarchy;
	}

	public static ArrayList<String> findDataObjectLink(DatabaseObjectHierarchy links, String id) {
		Iterator<DatabaseObjectHierarchy> iter = links.getSubobjects().iterator();
		ArrayList<String> set = new ArrayList<String>();
		while (iter.hasNext()) {
			DatabaseObjectHierarchy linkhier = iter.next();
			DataObjectLink link = (DataObjectLink) linkhier.getObject();
			if (id.compareTo(link.getLinkConcept()) == 0) {
				String datalink = link.getDataStructure();
				set.add(datalink);
			}
		}
		return set;
	}

	public static void setOneLineDescription(DatabaseObjectHierarchy hierarchy, String oneline) {
		ChemConnectDataStructure structure = (ChemConnectDataStructure) hierarchy.getObject();
		DatabaseObjectHierarchy descrhierarchy = hierarchy.getSubObject(structure.getDescriptionDataData());
		DescriptionDataData descr = (DescriptionDataData) descrhierarchy.getObject();
		descr.setOnlinedescription(oneline);
	}

	public static void setAbstract(DatabaseObjectHierarchy hierarchy, String description) {
		ChemConnectDataStructure structure = (ChemConnectDataStructure) hierarchy.getObject();
		DatabaseObjectHierarchy descrhierarchy = hierarchy.getSubObject(structure.getDescriptionDataData());
		DescriptionDataData descr = (DescriptionDataData) descrhierarchy.getObject();
		descr.setDescriptionAbstract(description);
	}

	public static void setOneLineDescriptionAndAbstract(DatabaseObjectHierarchy hierarchy, String oneline,
			String description) {
		ChemConnectDataStructure structure = (ChemConnectDataStructure) hierarchy.getObject();
		DatabaseObjectHierarchy descrhierarchy = hierarchy.getSubObject(structure.getDescriptionDataData());
		DescriptionDataData descr = (DescriptionDataData) descrhierarchy.getObject();
		descr.setOnlinedescription(oneline);
		descr.setDescriptionAbstract(description);
	}

	public static void setPurposeConceptPair(DatabaseObjectHierarchy hierarchy, String concept, String purpose) {
		ChemConnectDataStructure structure = (ChemConnectDataStructure) hierarchy.getObject();
		DatabaseObjectHierarchy descrhierarchy = hierarchy.getSubObject(structure.getDescriptionDataData());
		DescriptionDataData descr = (DescriptionDataData) descrhierarchy.getObject();

		String pairID = descr.getSourceConcept();
		DatabaseObjectHierarchy pairhierarchy = descrhierarchy.getSubObject(pairID);
		PurposeConceptPair pair = (PurposeConceptPair) pairhierarchy.getObject();
		pair.setConcept(concept);
		pair.setPurpose(purpose);
	}

	/*
	 * 1. Create link ID (aobj.getIdentifier()) 2. Set up new link (cathierarchy ->
	 * catalog) 3. Set in one line description (setOneLineDescription) 4. Read in
	 * top catagory link list (toplinkstructure from
	 * topcatalog.getChemConnectObjectLink()) 5. Determine number of links already
	 * in list (linknum) 6. Create an object link (linkhier -> lnk)
	 */
	public static DatabaseObjectHierarchy fillDatasetCatalogHierarchy(DatasetCatalogHierarchy topcatalog,
			String simpleName, DatabaseObject obj, String onelinedescription, String catagorytype) throws IOException {
		DatabaseObject aobj = new DatabaseObject(obj);
		String id = topcatalog.getIdentifier();
		String aid = DatasetCatalogHierarchy.createFullCatalogName(id, simpleName);
		aobj.setIdentifier(aid);
		aobj.nullKey();

		DatabaseObjectHierarchy cathierarchy = InterpretBaseData.DatasetCatalogHierarchy.createEmptyObject(aobj);
		DatasetCatalogHierarchy catalog = (DatasetCatalogHierarchy) cathierarchy.getObject();
		setOneLineDescription(cathierarchy, onelinedescription);

		DatabaseObjectHierarchy idhier = cathierarchy.getSubObject(catalog.getCatalogDataID());
		DataCatalogID catid = (DataCatalogID) idhier.getObject();
		ArrayList<String> catPath = new ArrayList<String>();
		catPath.add(obj.getOwner());
		catPath.add(ChemConnectCompoundDataStructure.removeNamespace(StandardDataKeywords.datasetCatalogHierarchy));
		catid.setCatalogBaseName(obj.getIdentifier());
		catid.setDataCatalog(catagorytype);
		catid.setSimpleCatalogName(simpleName);
		catid.setPath(catPath);
		setPurposeConceptPair(cathierarchy, catagorytype, DatabaseKeys.purposeDefineSubCatagory);

		connectInCatalogHierarchy(topcatalog, catalog);
		WriteBaseCatalogObjects.writeDatabaseObjectHierarchy(cathierarchy);
		return cathierarchy;
	}

	public static String userCatalogHierarchyID(String username) {
		String uidcat = "User-" + username;
		DatabaseObject catobj = new DatabaseObject(uidcat, username, username, "");
		DataElementInformation element = DatasetOntologyParseBase
				.getSubElementStructureFromIDObject(StandardDataKeywords.datasetCatalogHierarchy);
		String catid = InterpretBaseDataUtilities.createSuffix(catobj, element);
		return catid;
	}

	public static DatabaseObjectHierarchy fillDataCatalogID(DatabaseObject obj, String parentLink, String catalogbase,
			String catalog, String simple, ArrayList<String> path) {
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj, parentLink);

		DataCatalogID id = new DataCatalogID(structure, catalogbase, catalog, simple, path);
		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(id);
		return hierarchy;
	}

	public static void connectInCatalogHierarchy(DatasetCatalogHierarchy parentcatalog,
			DatasetCatalogHierarchy childcatalog) throws IOException {
		String linkid = parentcatalog.getChemConnectObjectLink();

		ChemConnectCompoundMultiple multi = (ChemConnectCompoundMultiple) InterpretBaseData.ChemConnectCompoundMultiple
				.readElementFromDatabase(linkid);

		String sourceID = childcatalog.getSourceID();
		DatabaseObjectHierarchy subcatalog = addConnectionToMultiple(multi, sourceID, childcatalog.getIdentifier());
		WriteBaseCatalogObjects.writeDatabaseObjectHierarchy(subcatalog);
		DatabaseWriteBase.writeDatabaseObject(multi);
	}

	public static DatabaseObjectHierarchy fillCataogHierarchyForUser(DatabaseObject obj, String username, String userid,
			DataCatalogID usercatid) {
		DatabaseObject dobj = new DatabaseObject(obj);
		String uid = dobj.getIdentifier();
		dobj.setIdentifier(uid);
		dobj.nullKey();
		String onelinedescription = "Catalog of '" + username + "'";
		DatabaseObjectHierarchy userhierarchy = InterpretBaseData.DatasetCatalogHierarchy.createEmptyObject(dobj);
		DatasetCatalogHierarchy usercatalog = (DatasetCatalogHierarchy) userhierarchy.getObject();
		setOneLineDescription(userhierarchy, onelinedescription);

		DatabaseObjectHierarchy idhier = userhierarchy.getSubObject(usercatalog.getCatalogDataID());
		DataCatalogID catid = (DataCatalogID) idhier.getObject();
		catid.setCatalogBaseName(obj.getIdentifier());
		catid.setDataCatalog(DatabaseKeys.conceptUserDataCatagory);
		catid.setSimpleCatalogName(username);
		ArrayList<String> userPath = new ArrayList<String>();
		userPath.add(username);
		userPath.add(ChemConnectCompoundDataStructure.removeNamespace(StandardDataKeywords.datasetCatalogHierarchy));
		catid.setPath(userPath);

		setDescriptionInHierarchy(userhierarchy, usercatalog.getDescriptionDataData(),
				StandardTextStatements.userdescription, username);

		setPurposeConceptPair(userhierarchy, DatabaseKeys.purposeDefineSubCatagory,
				DatabaseKeys.conceptUserDataCatagory);

		DatabaseObjectHierarchy multilnkhier = userhierarchy.getSubObject(usercatalog.getChemConnectObjectLink());
		ChemConnectCompoundMultiple multilnk = (ChemConnectCompoundMultiple) multilnkhier.getObject();
		DatabaseObjectHierarchy userlink = fillDataObjectLink(multilnk, "0", MetaDataKeywords.linkUser, userid);
		multilnkhier.addSubobject(userlink);
		multilnk.setNumberOfElements(1);
		return userhierarchy;
	}

	static DatabaseObjectHierarchy connectSubCatagory(String simpleName, String oneLineDescription, String catagorytype,
			DatabaseObjectHierarchy supercat) {
		DatabaseObjectHierarchy cat = null;
		try {
			DatabaseObject obj = new DatabaseObject(supercat.getObject());
			DatasetCatalogHierarchy orgcathier = (DatasetCatalogHierarchy) supercat.getObject();
			cat = fillDatasetCatalogHierarchy(orgcathier, simpleName, obj, oneLineDescription, catagorytype);
			setAbstract(cat, oneLineDescription);

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return cat;
	}

	public static void setDescriptionInHierarchy(DatabaseObjectHierarchy hierarchy, String descrid, String resource,
			String name) {
		DatabaseObjectHierarchy descrhier = hierarchy.getSubObject(descrid);
		DescriptionDataData descr = (DescriptionDataData) descrhier.getObject();

		try {
			String descrabstract = IOUtils
					.toString(CreateContactObjects.class.getClassLoader().getResourceAsStream(resource), "UTF-8");
			if (name != null) {
				descrabstract = descrabstract.replace("#####", name);
			}
			descr.setDescriptionAbstract(descrabstract);
		} catch (IOException e) {
		}

	}

	public static DatabaseObjectHierarchy addConnectionToMultiple(ChemConnectCompoundMultiple multilnk, String sourceID,
			String childid) {
		int numlinks = multilnk.getNumberOfElements();
		String numlinkS = Integer.toString(numlinks);
		multilnk.setNumberOfElements(numlinks + 1);
		DatabaseObject obj = new DatabaseObject(multilnk);
		obj.setSourceID(sourceID);

		DatabaseObjectHierarchy subcatalog = fillDataObjectLink(multilnk, numlinkS, MetaDataKeywords.linkSubCatalog,
				childid);

		return subcatalog;
	}

	static DatabaseObjectHierarchy fillDataObjectLink(DatabaseObject obj, String linknumber, String concept,
			String linkedobj) {
		DatabaseObjectHierarchy linkhier = InterpretBaseData.DataObjectLink.createEmptyObject(obj);
		DataObjectLink userlink = (DataObjectLink) linkhier.getObject();

		userlink.setLinkConcept(concept);
		userlink.setDataStructure(linkedobj);
		String id = userlink.getIdentifier() + linknumber;
		userlink.setIdentifier(id);

		DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(userlink);

		return hierarchy;

	}
	public static DatabaseObjectHierarchy createEmptyMultipleObject(ChemConnectCompoundMultiple multiple) {
		String dataType = multiple.getType();
		String numS = String.valueOf(multiple.getNumberOfElements());
		DatabaseObject obj = new DatabaseObject(multiple);
		obj.nullKey();
		ClassificationInformation info = DatasetOntologyParseBase.getIdentificationInformation(dataType);
		String structureName = info.getDataType();
		InterpretBaseData interpret = InterpretBaseData.valueOf(structureName);
		DatabaseObjectHierarchy hierarchy = interpret.createEmptyObject(obj);
		String uid = hierarchy.getObject().getIdentifier() + numS;
		hierarchy.getObject().setIdentifier(uid);
		return hierarchy;
	}
	public ChemConnectRecordInformation getChemConnectRecordInformation(DatabaseObject obj) throws IOException {
		
		String structureS = DatasetOntologyParseBase.dataTypeOfStructure(obj);
		CompoundDataStructure structure = getChemConnectCompoundDataStructure(structureS);
		String objecttype = obj.getClass().getSimpleName();
		InterpretBaseData interpret = InterpretBaseData.valueOf(objecttype);
		Map<String,Object> mapping = interpret.createYamlFromObject(obj);
		ChemConnectRecordInformation info = new ChemConnectRecordInformation(obj,structureS,structure,mapping);
		
		return info;
	}
	/*
	public ChemConnectDataElementInformation getChemConnectDataStructure(String identifier, String structureS) {
		ChemConnectDataElementInformation structure = DatasetOntologyParseBase.getChemConnectDataStructure(identifier, structureS);
		if(structure.getIdentifier() == null) {
			DatabaseObject obj = getBaseUserDatabaseObject();
			structure.setIdentifier(obj);
		}
		return structure;
	}
	public DatabaseObject getBaseUserDatabaseObject() {
		ContextAndSessionUtilities context = getUtilities();
		String username = context.getUserName();
		String sourceID = QueryBase.getDataSourceIdentification(username);
		DatabaseObject obj = new DatabaseObject();
		obj.setOwner(username);
		obj.setSourceID(sourceID);
		return obj;
	}
	*/
	public CompoundDataStructure getChemConnectCompoundDataStructure(String dataElementName) {
		CompoundDataStructure substructures = null;
		substructures = DatasetOntologyParseBase.subElementsOfStructure(dataElementName);
		return substructures;
	}

}
