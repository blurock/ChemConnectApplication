package info.esblurock.reaction.core.server.base.db;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import java.util.Set;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Blob.BlobSourceOption;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;

import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.chemconnect.core.base.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.base.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.base.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.base.utilities.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;
import info.esblurock.reaction.core.server.base.create.CreateBaseCatalogObjects;
import info.esblurock.reaction.core.server.base.create.CreateContactObjects;
import info.esblurock.reaction.core.server.base.db.yaml.ReadWriteYamlDatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.base.queries.QueryBase;
import info.esblurock.reaction.core.server.base.services.ServerBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseData;
import info.esblurock.reaction.core.server.base.services.util.ParseUtilities;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.ParsedFilename;
import info.esblurock.reaction.chemconnect.core.base.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.base.image.ImageUploadTransaction;
import info.esblurock.reaction.chemconnect.core.base.image.UploadedImage;

@SuppressWarnings("serial")
public class UserImageServiceImpl extends ServerBase implements UserImageService {

	public static Storage storage = null;
	static {
		storage = StorageOptions.getDefaultInstance().getService();
	}

	/*
	 * private static final GcsService gcsService =
	 * GcsServiceFactory.createGcsService(new RetryParams.Builder()
	 * .initialRetryDelayMillis(10) .retryMaxAttempts(10)
	 * .totalRetryPeriodMillis(15000) .build());
	 */
	// private static final int BUFFER_SIZE = 2 * 1024 * 1024;

	public static String fileCodeParameter = "fileCode";
	public static String userParameter = "user";
	public static String keywordParameter = "identifier";
	public static String blobkeyParameter = "blobKey";
	public static String keyAsStringParameter = "keyAsString";

	@Override
	public ImageServiceInformation getBlobstoreUploadUrl(String keywordName, boolean uploadService) {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		String username = usession.getUserName();
		String outputSourceCode = null;
		if (uploadService) {
			outputSourceCode = QueryBase.getDataSourceIdentification(username);
		}
		UploadOptions options = UploadOptions.Builder.withGoogleStorageBucketName(bucketName);
		String baseurl = uploadRoot + "?" + sourceFileParameter + "=" + outputSourceCode + "&" + keywordNameParameter
				+ "=" + keywordName;
		String uploadUrl = blobstoreService.createUploadUrl(baseurl, options);

		if (uploadService) {
			ImageUploadTransaction imageinfo = new ImageUploadTransaction(username, outputSourceCode, keywordName,
					bucketName, uploadUrl);
			DatabaseWriteBase.writeObjectWithTransaction(imageinfo);
		} else {
			System.out.println("uploadService: no write");
		}
		ImageServiceInformation returninfo = new ImageServiceInformation(username, outputSourceCode, keywordName,
				bucketName, uploadUrl);

		return returninfo;
	}

	@Override
	public ArrayList<UploadedImage> getUploadedImageSet(ImageServiceInformation serviceInfo) throws IOException {
		ArrayList<UploadedImage> imagelst = new ArrayList<UploadedImage>();

		String keyword = serviceInfo.getKeyWord();
		String user = serviceInfo.getUser();

		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		values.add(fileCodeParameter, keyword);
		values.add(userParameter, user);
		String classname = UploadedImage.class.getName();
		QuerySetupBase querybase = new QuerySetupBase(user,classname, values);
		SingleQueryResult result;
		try {
			result = QueryBase.StandardQueryResult(querybase);
			ArrayList<DatabaseObject> objs = result.getResults();
			for (DatabaseObject obj : objs) {
				UploadedImage uploaded = (UploadedImage) obj;
				imagelst.add(uploaded);
			}
		} catch (ClassNotFoundException e) {
			throw new IOException("getUploadedImageSet Class not found: " + classname);
		}
		return imagelst;
	}

	@Override
	public ArrayList<UploadedImage> getUploadedImageSetFromKeywordAndUser(String keyword) throws IOException {
		ArrayList<UploadedImage> imagelst = new ArrayList<UploadedImage>();
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData sessiondata = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);

		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		values.add(keywordParameter, keyword);
		values.add(userParameter, sessiondata.getUserName());
		String classname = UploadedImage.class.getName();
		QuerySetupBase querybase = new QuerySetupBase(classname, values);
		SingleQueryResult result;
		try {
			result = QueryBase.StandardQueryResult(querybase);
			ArrayList<DatabaseObject> objs = result.getResults();
			for (DatabaseObject obj : objs) {
				if (obj instanceof UploadedImage) {
					UploadedImage uploaded = (UploadedImage) obj;
					imagelst.add(uploaded);
				} else {
					System.out.println("getUploadedImageSetFromKeywordAndUser: not a UploadedImage: "
							+ obj.getClass().getCanonicalName());
				}
			}
		} catch (ClassNotFoundException e) {
			throw new IOException("getUploadedImageSetFromKeywordAndUser Class not found: " + classname);
		}
		return imagelst;
	}

	@Override
	public String updateImages(ArrayList<UploadedImage> images) throws IOException {
		String ans = "Successfully updated " + images.size() + " images ";
		DatabaseWriteBase.writeListOfDatabaseObjects(images);
		return ans;
	}

	public GCSBlobContent moveBlobFromUpload(GCSBlobFileInformation target) throws IOException {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		String username = usession.getUserName();

		String path = GCSServiceRoutines.createUploadPath(username);

		String id = target.getIdentifier();
		String access = username;
		String owner = username;
		String sourceID = QueryBase.getDataSourceIdentification(username);
		DatabaseObject obj = new DatabaseObject(id, access, owner, sourceID);

		GCSBlobFileInformation source = new GCSBlobFileInformation(obj, path,
				target.getFilename(), target.getFiletype(), target.getDescription());

		target.setSourceID(source.getSourceID());

		return moveBlob(target, source);
	}

	public GCSBlobContent moveBlob(GCSBlobFileInformation target, GCSBlobFileInformation source) throws IOException {
		return GCSServiceRoutines.moveBlob(target, source);
	}

	public GCSBlobContent getBlobContent(GCSBlobFileInformation gcsinfo) throws IOException {
		return getContent(gcsinfo);
	}
	public void writeBlobContent(GCSBlobContent gcs) throws IOException {
		GCSServiceRoutines.writeBlobContent(gcs);
	}

	public ArrayList<String> getBlobAsLines(GCSBlobContent info) throws IOException {
		GCSBlobContent gcs = getBlobContent(info.getInfo());
		String text = gcs.getBytes();
		ArrayList<String> lines = new ArrayList<String>();
		Scanner tok = new Scanner(text);
		while (tok.hasNextLine()) {
			lines.add(tok.nextLine());
		}
		tok.close();
		return lines;
	}

	public void deleteUploadedFile(GCSBlobFileInformation gcsinfo) throws IOException {
		deleteBlob(gcsinfo);
	}

	public void deleteUploadedFiles(ArrayList<GCSBlobFileInformation> fileset) throws IOException {
		for (GCSBlobFileInformation info : fileset) {
			deleteUploadedFile(info);
		}
	}

	public ArrayList<GCSBlobFileInformation> getUploadedFiles() throws IOException {
		ArrayList<GCSBlobFileInformation> fileset = new ArrayList<GCSBlobFileInformation>();
		SingleQueryResult result = null;
		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		if(usession != null) {
			String username = usession.getUserName();
			values.add("owner", username);
			String path = "upload/" + username;
			values.add("path", path);
		
			QuerySetupBase query = new QuerySetupBase(GCSBlobFileInformation.class.getCanonicalName(), values);
			query.setAccess(username);
			try {
				result = QueryBase.StandardQueryResult(query);
			} catch (ClassNotFoundException e) {
				throw new IOException("Class Not found: " + GCSBlobFileInformation.class.getCanonicalName());
			}
			for (DatabaseObject obj : result.getResults()) {
				fileset.add((GCSBlobFileInformation) obj);
			}
		}
		return fileset;
	}

	public HierarchyNode getUploadedFilesHiearchy(ArrayList<String> fileTypes) throws IOException {
		ArrayList<GCSBlobFileInformation> lst = getUploadedFiles();
		return getBlobHierarchy(lst,fileTypes);
	}
	public HierarchyNode getBlobHierarchy(ArrayList<GCSBlobFileInformation> lst, ArrayList<String> types)  {
		DatabaseObject obj = new DatabaseObject();
		obj.setIdentifier("Blob Files");
		HierarchyNode hierarchy = ParseUtilities.buildBlobHierarchy(obj,lst,types);
		return hierarchy;
	}
	public HierarchyNode getIDsFromConceptLink(String concept) throws IOException {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		String username = usession.getUserName();
		String classType = DatasetOntologyParseBase.findObjectTypeFromLinkConcept(concept);
		Set<String> ids = WriteReadDatabaseObjects.getIDsOfAllDatabaseObjects(username,classType);
		HierarchyNode topnode = ParseUtilities.parseIDsToHierarchyNode(concept,ids,false);
		return topnode;
	}
	
	public HierarchyNode getIDHierarchyFromDataCatalogID(String basecatalog, String catalog) throws IOException {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);

		return WriteReadDatabaseObjects.getIDHierarchyFromDataCatalogID(usession.getUserName(), basecatalog, catalog);
		
	}

	public GCSBlobFileInformation retrieveBlobFromURL(String requestUrl) throws IOException {
		String uploadDescriptionText = "Uploaded File from URL";

		URL urlconnect = new URL(requestUrl);
		URLConnection c = urlconnect.openConnection();
		String contentType = c.getContentType();

		URL urlstream = new URL(requestUrl);
		InputStream in = urlstream.openStream();
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		String username = usession.getUserName();
		String path = GCSServiceRoutines.createUploadPath(username);
		String name = extractNameFromURL(requestUrl);
		GCSBlobFileInformation source = GCSServiceRoutines.createInitialUploadInfo(
				path, name, contentType, uploadDescriptionText,
				usession.getIP(),username);
		retrieveContentFromStream(in, source);

		return source;
	}
	
	public String extractNameFromURL(String url) {
		int pos = url.lastIndexOf("/");
		String name = url;
		if(pos > 0) {
			name = url.substring(pos+1);
		}
		return name;
	}

	public GCSBlobFileInformation retrieveBlobFromContent(String filename, String content) throws IOException {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		String username = usession.getUserName();
		String path = GCSServiceRoutines.createUploadPath(username);
		String contentType = "text/plain";
		String uploadDescriptionText = "Uploaded File from text input";
		GCSBlobFileInformation source = GCSServiceRoutines.createInitialUploadInfo(
				path, filename, contentType, uploadDescriptionText,
				usession.getIP(),username);

		InputStream in = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
		retrieveContentFromStream(in, source);
		return source;
	}
	private void retrieveContentFromStream(InputStream in, GCSBlobFileInformation source) throws IOException {
		BlobInfo info = BlobInfo.newBuilder(GCSServiceRoutines.getGCSStorageBucket(), source.getGSFilename())
				.setAcl(new ArrayList<Acl>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
				.setContentType(source.getFiletype()).build();

		@SuppressWarnings({ "deprecation", "unused" })
		BlobInfo blobInfo = storage.create(info, in);
		DatabaseWriteBase.writeObjectWithTransaction(source);

	}

	public ArrayList<DatabaseObjectHierarchy> getSetOfDatabaseObjectHierarchyForUser(String classType) throws IOException {
		ArrayList<DatabaseObjectHierarchy> objects = null;
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		if(usession != null) {
			objects = WriteReadDatabaseObjects.getAllDatabaseObjectHierarchyForUser(usession.getUserName(), classType);
			/*
			for(DatabaseObjectHierarchy hierarchy : objects) {
				System.out.println(classType + ": -----------------------------------------------------------");
				System.out.println(hierarchy.toString());
				System.out.println(classType + ": -----------------------------------------------------------");
			}
			*/
		} else {
			objects = new ArrayList<DatabaseObjectHierarchy>();
		}
		return objects;
	}
	
	public DatabaseObjectHierarchy getUserDatasetCatalogHierarchy(String username) throws IOException {
		String uid = CreateContactObjects.userCatalogHierarchyID(username);
		DatabaseObjectHierarchy hierarchy = ExtractCatalogInformation.getDatabaseObjectHierarchy(uid);
		return hierarchy;
	}

	public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj, String dataType) {
		DatabaseObjectHierarchy hierarchy = null;
		boolean isSimple = DatasetOntologyParseBase.isAChemConnectPrimitiveDataStructure(dataType);
		if(isSimple) {
			
			hierarchy = new DatabaseObjectHierarchy();
		} else {
			ClassificationInformation info = DatasetOntologyParseBase.getIdentificationInformation(dataType);
			String structureName = info.getDataType();
			InterpretBaseData interpret = InterpretBaseData.valueOf(structureName);
			hierarchy = interpret.createEmptyObject(obj);
		}
		return hierarchy;
	}
	public DatabaseObjectHierarchy createEmptyMultipleObject(ChemConnectCompoundMultiple multiple) {
		return CreateContactObjects.createEmptyMultipleObject(multiple);
	}
	
	public DatabaseObjectHierarchy createOrganization(DatabaseObject obj, String shortname, String organizationname, DataCatalogID catid) {
		DatabaseObjectHierarchy hierarchy = CreateContactObjects.fillOrganization(obj, shortname, organizationname,catid);
		return hierarchy;
	}
	
	public DatabaseObjectHierarchy createDatabasePerson(DatabaseObject obj, String userClassification, NameOfPerson name, DataCatalogID catid) {
		DatabaseObjectHierarchy hierarchy = CreateContactObjects.fillMinimalPersonDescription(obj, 
				UserAccountKeys.accessTypeStandardUser, userClassification, name,catid);
		return hierarchy;
	}
	
	public DatabaseObjectHierarchy createDatasetImage(DatabaseObject obj,DataCatalogID catid,
			String imageType, GCSBlobFileInformation info) throws IOException {
		String path = catid.getFullPath("/");
		String extension = DatasetOntologyParseBase.getFileExtension(imageType);
		String filename = null;
		if(extension != null) {
			filename = catid.getSimpleCatalogName() + "." + extension;
		} else {
			filename = info.getFilename();
		}
		
		GCSBlobFileInformation target = new GCSBlobFileInformation(obj, path,
				filename, info.getFiletype(), info.getDescription());
		info.setSourceID(target.getSourceID());

		GCSBlobContent content = moveBlob(target,info);

		DatabaseObjectHierarchy hierarchy = CreateContactObjects.fillDatasetImage(obj, catid, imageType, content.getUrl());
		return hierarchy;
	}
	
	
	public DatabaseObjectHierarchy getCatalogObject(String id, String dataType) throws IOException {
		DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getCatalogObject(id,dataType);
		if(readhierarchy == null) {
			throw new IOException("Catalog Object Not Found: " + id);
		}
		return readhierarchy;
	}
	
	public DatabaseObjectHierarchy getTopCatalogObject(String id, String dataType) throws IOException {
		DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getTopCatalogObject(id,dataType);
		return readhierarchy;
	}
	
	public DatabaseObjectHierarchy createNewCatalogHierarchy(DatabaseObject obj, String newSimpleName, String id, 
			String onelinedescription, String catagorytype)
			throws IOException {
		
		String sourceID = QueryBase.getDataSourceIdentification(obj.getOwner());
		DatabaseObjectHierarchy subs = ExtractCatalogInformation.createNewCatalogHierarchy(obj,newSimpleName, id, onelinedescription, sourceID, catagorytype);
		return subs;
	}

	public DatabaseObjectHierarchy writeDatabaseObjectHierarchy(DatabaseObjectHierarchy hierarchy) throws IOException {
		DatabaseObjectHierarchy hier = null;
		try {
			WriteReadDatabaseObjects.updateSourceID(hierarchy);
			hier = WriteBaseCatalogObjects.writeDatabaseObjectHierarchyWithTransaction(hierarchy);
		} catch (Exception ex) {
			System.out.println("writeDatabaseObjectHierarchy  error in writing");
			System.out.println(ex.toString());
			ex.printStackTrace();
			throw new IOException("Error in writing objects");
		}
		return hier;
	}
	
	public DatabaseObjectHierarchy  extractLinkObjectFromStructure(DatabaseObjectHierarchy hierarchy, String linktypeid) throws IOException {
		return ExtractLinkObjectFromStructure.extract(hierarchy, linktypeid);
	}

	
	public void writeYamlObjectHierarchy(String id, String simpleclass) throws IOException {
		try {
			System.out.println("writeYamlObjectHierarchy: " + simpleclass + ": " + id);
			String sessionid = getThreadLocalRequest().getSession().getId();
			UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
			String username = usession.getUserName();
			ReadWriteYamlDatabaseObjectHierarchy.writeAsYamlToGCS(id,simpleclass,username,sessionid);
		} catch (Exception ex) {
			System.out.println("writeYamlObjectHierarchy  error in writing");
			System.out.println(ex.toString());
			ex.printStackTrace();
			throw new IOException("Error in writing objects");
		}

	}
	
	
	public ArrayList<NameOfPerson> getIDHierarchyFromFamilyNameAndUser(String familyname) throws IOException {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		String username = usession.getUserName();
		return WriteReadDatabaseObjects.getIDHierarchyFromFamilyNameAndUser(username,familyname);
	}
	
	public HierarchyNode getIDHierarchyFromDataCatalogAndUser(String datacatalog) throws IOException {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		String username = usession.getUserName();
		System.out.println("getIDHierarchyFromDataCatalogAndUser user: " + username);
		return WriteReadDatabaseObjects.getIDHierarchyFromDataCatalogAndUser(username,datacatalog,null);
	}
	
	public HierarchyNode getIDHierarchyFromDataCatalogIDAndClassType(String catalogbasename, String classtype) throws IOException {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		String username = usession.getUserName();
		System.out.println("getIDHierarchyFromDataCatalogIDAndClassType: user           " + username);
		System.out.println("getIDHierarchyFromDataCatalogIDAndClassType: catalogbasename" + catalogbasename);
		System.out.println("getIDHierarchyFromDataCatalogIDAndClassType: classtype      " + classtype);
		return WriteReadDatabaseObjects.getIDHierarchyFromDataCatalogIDAndClassType(username, 
				catalogbasename,classtype);
	}

	public HierarchyNode getFileInterpretionChoices(GCSBlobFileInformation info) throws IOException {
		ParsedFilename parsed = parseFilename(info);
		return ParseUtilities.getFileInterpretionChoices(parsed);
	}
	
	public String getStructureFromFileType(String filetype) {
		return DatasetOntologyParseBase.getStructureFromFileType(filetype);
	}

	public void deleteObject(String id, String type) throws IOException {
		WriteReadDatabaseObjects.deleteObject(id, type);
	}

	
	
	public static ParsedFilename parseFilename(GCSBlobFileInformation info) throws IOException {
		ParsedFilename parsed = ParseUtilities.fillFileInformation(info, info.getFilename(), info.getFiletype());
		return parsed;
	}
/*
	public static void deleteTransactionFromSourceID(String sourceID) throws IOException {
		TransactionInfo info = (TransactionInfo) QueryBase.getFirstDatabaseObjectsFromSingleProperty(
				TransactionInfo.class.getCanonicalName(), "sourceID", sourceID);
		DatabaseWriteBase.deleteTransactionInfo(info);
	}
*/
	public static void deleteBlob(GCSBlobFileInformation gcsinfo) throws IOException {
		BlobId blobId = BlobId.of(GCSServiceRoutines.getGCSStorageBucket(), gcsinfo.getGSFilename());
		storage.delete(blobId);
	}
	
	
/*
	public static String createUploadPath(ContextAndSessionUtilities util) {
		String username = util.getUserName();
		String path = GoogleCloudStorageConstants.uploadPathPrefix + "/" + username;
		return path;
	}
*/
	/*
	public static GCSBlobFileInformation createInitialUploadInfo(String bucket, String filename, String contentType,
			String uploadDescriptionText, ContextAndSessionUtilities util) {
		UserDTO user = util.getUserInfo();

		String path = GCSServiceRoutines.createUploadPath(util);

		String id = util.getUserInfo().getIP() + ":" + user.getName();
		String access = user.getName();
		String owner = user.getName();
		String sourceID = QueryBase.getDataSourceIdentification(user.getName());
		DatabaseObject obj = new DatabaseObject(id, access, owner, sourceID);
		GCSBlobFileInformation source = new GCSBlobFileInformation(obj, bucket, path,
				filename, contentType, uploadDescriptionText);
		return source;
	}
*/
	public static InputStream getInputStream(GCSBlobFileInformation info) throws IOException {
		System.out.println("InputStream getInputStream");
		//GCSBlobContent content = getContent(info);
		//InputStream inputstream = new ByteArrayInputStream(content.getBytes().getBytes());
		BlobId blobId = BlobId.of(GCSServiceRoutines.getGCSStorageBucket(), info.getGSFilename());
		System.out.println(info.toString("getInputStream: "));
		Blob blob = storage.get(blobId);
		byte[] bytes = blob.getContent(BlobSourceOption.generationMatch());
		InputStream inputstream = new ByteArrayInputStream(bytes);
		return inputstream;
	}

	public static GCSBlobContent getContent(GCSBlobFileInformation gcsinfo) throws IOException {
		BlobId blobId = BlobId.of(GCSServiceRoutines.getGCSStorageBucket(), gcsinfo.getGSFilename());
		Blob blob = storage.get(blobId);
		byte[] bytes = blob.getContent(BlobSourceOption.generationMatch());
		String bytesS = Base64.getEncoder().encodeToString(bytes);
		String urlS = "https://storage.googleapis.com/" + GCSServiceRoutines.getGCSStorageBucket() + "/" + gcsinfo.getGSFilename();
		System.out.println("getContent: '" + urlS + "'");
		GCSBlobContent gcs = new GCSBlobContent(urlS, gcsinfo);
		gcs.setBytes(bytesS);
		return gcs;
	}

	@Override
	public String deleteFromStorage(String blobkey) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}