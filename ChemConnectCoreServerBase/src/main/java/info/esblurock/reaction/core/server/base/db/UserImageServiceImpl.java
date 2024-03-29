package info.esblurock.reaction.core.server.base.db;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;

import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.base.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.base.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.base.utilities.ClassificationInformation;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;
import info.esblurock.reaction.core.server.base.activity.repositoryfile.util.CreateRepositoryCatagoryFiles;
import info.esblurock.reaction.core.server.base.activity.repositoryfile.util.InitialStagingUtilities;
import info.esblurock.reaction.core.server.base.activity.repositoryfile.util.RetrieveRepositoryFileInfo;
import info.esblurock.reaction.core.server.base.authentification.InitializationBase;
//import info.esblurock.reaction.core.server.base.create.CreateBaseCatalogObjects;
import info.esblurock.reaction.core.server.base.create.CreateConsortiumCatalogObject;
import info.esblurock.reaction.core.server.base.create.CreateContactObjects;
import info.esblurock.reaction.core.server.base.db.consortium.ReadInConsortium;
import info.esblurock.reaction.core.server.base.db.interpret.FileInterpretationBase;
import info.esblurock.reaction.core.server.base.db.interpret.FileInterpretationInterface;
import info.esblurock.reaction.core.server.base.db.yaml.ReadWriteYamlDatabaseObjectHierarchy;
import info.esblurock.reaction.core.server.base.queries.QueryBase;
import info.esblurock.reaction.core.server.base.services.ServerBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseData;
import info.esblurock.reaction.core.server.base.services.util.InterpretDataBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretDataInterface;
import info.esblurock.reaction.core.server.base.services.util.ParseUtilities;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.activity.read.ActivityRepositoryInitialReadInfo;
import info.esblurock.reaction.chemconnect.core.base.activity.read.ActivityRepositoryInitialReadURL;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.InitialStagingRepositoryFile;
import info.esblurock.reaction.chemconnect.core.base.gcs.ParsedFilename;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryDataFile;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryFileStaging;
import info.esblurock.reaction.chemconnect.core.base.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.base.image.ImageUploadTransaction;
import info.esblurock.reaction.chemconnect.core.base.image.UploadedImage;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;

@SuppressWarnings("serial")
public class UserImageServiceImpl extends ServerBase implements UserImageService {


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
	
	/*
	@Override
	public DatabaseObjectHierarchy processActivity() {
		return null;
	}
	*/
	@Override
	public DatabaseObjectHierarchy getBlobDatasetObject(GCSBlobFileInformation info, 
			DatabaseObject obj, DataCatalogID catid) throws IOException {
		String type = ChemConnectCompoundDataStructure.removeNamespace(catid.getDataCatalog());
		
		FileInterpretationInterface interpret = InitializationBase.fileInterpretationBase.valueOf(type);
		DatabaseObjectHierarchy hierarchy = interpret.interpretBlobFile(info, obj, catid);
		return hierarchy;
	}
	
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
			DatabaseWriteBase.writeObjectWithTransaction(imageinfo, MetaDataKeywords.InitialReadFromWebLocation);
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

	public DatabaseObjectHierarchy createRepositoryDataFile(DatabaseObjectHierarchy stagehierarchy,
			DataCatalogID catalogid) throws IOException {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData sessiondata = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		return CreateRepositoryCatagoryFiles.createRepositoryDataFile(stagehierarchy, catalogid, sessiondata);
		/*
		catalogid.setSourceID(stagehierarchy.getObject().getSourceID());
		CreateRepositoryCatagoryFiles.createRepositoryDataFile(stagehierarchy, catalogid, sessiondata);
		RepositoryFileStaging staging = (RepositoryFileStaging) stagehierarchy.getObject();
		DatabaseObjectHierarchy stagegcshierarchy = stagehierarchy.getSubObject(staging.getBlobFileInformation());
		GCSBlobFileInformation stagegcs = (GCSBlobFileInformation) stagegcshierarchy.getObject();

		DatabaseObjectHierarchy repositoryhier = 
				CreateBaseCatalogObjects.createRepositoryDataFile(stagehierarchy,catalogid);
		RepositoryDataFile repository = (RepositoryDataFile) repositoryhier.getObject();
		InterpretBaseData gcsinterpret = InterpretBaseData.GCSBlobFileInformation;
		DatabaseObjectHierarchy gcshierarchy = gcsinterpret.createEmptyObject(repository);
		GCSBlobFileInformation repgcs = (GCSBlobFileInformation) gcshierarchy.getObject();
		repgcs.setFilename(catalogid.getSimpleCatalogName());
		repgcs.setFiletype(stagegcs.getFiletype());
		String reppath = GCSServiceRoutines.createRepositoryPath(catalogid.getFullPath("/"));
		repgcs.setPath(reppath);
		repgcs.setDescription(stagegcs.getDescription());
		
		
		System.out.println("createRepositoryDataFile: repositoryhier\n" 
		+ repositoryhier.toString("UserImageServiceImpl "));
		
		DatabaseWriteBase.writeObjectWithTransaction(repositoryhier, 
				MetaDataKeywords.transferFileIntoCatagoryHierarchy,
				sessiondata);
		staging.setStagingFilePresent(MetaDataKeywords.stagedFileProcessed);
		WriteBaseCatalogObjects.writeDatabaseObjectHierarchyWithTransaction(stagehierarchy, 
				MetaDataKeywords.updateCatalogObjectEvent);
		
		GCSServiceRoutines.moveBlob(repgcs, stagegcs);
		GCSServiceRoutines.deleteBlob(stagegcs);
		
		return repositoryhier;
		*/
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
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,null);
		GCSBlobFileInformation source = new GCSBlobFileInformation(structure, path,
				target.getFilename(), target.getFiletype(), target.getDescription());

		target.setSourceID(source.getSourceID());

		return moveBlob(target, source);
	}

	public GCSBlobContent moveBlob(GCSBlobFileInformation target, GCSBlobFileInformation source) throws IOException {
		return GCSServiceRoutines.moveBlob(target, source);
	}

	public GCSBlobContent getBlobContent(GCSBlobFileInformation gcsinfo) throws IOException {
		return GCSServiceRoutines.getContent(gcsinfo);
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
		GCSServiceRoutines.deleteBlob(gcsinfo);
	}

	public void deleteUploadedFiles(ArrayList<GCSBlobFileInformation> fileset) throws IOException {
		for (GCSBlobFileInformation info : fileset) {
			deleteUploadedFile(info);
		}
	}

	public ArrayList<DatabaseObjectHierarchy> getUploadedStagedFiles() throws IOException {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		return RetrieveRepositoryFileInfo.getUploadedStagedFiles(usession);
		/*
		ArrayList<DatabaseObjectHierarchy> fileset = new ArrayList<DatabaseObjectHierarchy>();
		SingleQueryResult result = null;
		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
		if(usession != null) {
			String username = usession.getUserName();
			values.add("owner", username);
			String staged = MetaDataKeywords.stagedFileNotProcessed;
			values.add("stagingFilePresent", staged);
			QuerySetupBase query = new QuerySetupBase(RepositoryFileStaging.class.getCanonicalName(), values);
			query.setAccess(username);
			try {
				result = QueryBase.StandardQueryResult(query);
			} catch (ClassNotFoundException e) {
				throw new IOException("Class Not found: " + RepositoryFileStaging.class.getCanonicalName());
			}
			for (DatabaseObject obj : result.getResults()) {
				RepositoryFileStaging repstage = (RepositoryFileStaging) obj;
				String id = repstage.getIdentifier();
				String classType = StandardDataKeywords.repositoryFileStaging;
				DatabaseObjectHierarchy readhierarchy = ExtractCatalogInformation.getCatalogObject(id, classType);
				fileset.add(readhierarchy);
			}
		} else {
			//System.out.println("getUploadedStagedFiles: no user session connected");
		}
		return fileset;
		*/
	}


	
	public ArrayList<GCSBlobFileInformation> getUploadedFiles() throws IOException {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		return RetrieveRepositoryFileInfo.getUploadedFiles(usession);
		/*
		ArrayList<GCSBlobFileInformation> fileset = new ArrayList<GCSBlobFileInformation>();
		SingleQueryResult result = null;
		SetOfQueryPropertyValues values = new SetOfQueryPropertyValues();
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
		*/
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
		String classType = DatasetOntologyParseBase.findObjectTypeFromLinkConcept(concept);
		Set<String> ids = WriteReadDatabaseObjects.getIDsOfAllDatabaseObjects(usession,classType);
		HierarchyNode topnode = ParseUtilities.parseIDsToHierarchyNode(concept,ids,false);
		return topnode;
	}
	/**
	 *
	 */
	public HierarchyNode getIDHierarchyFromDataCatalogID(String basecatalog, String catalog) throws IOException {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		HierarchyNode nodehierarchy = null;
		System.out.println("getIDHierarchyFromDataCatalogID: basecatalog: " + basecatalog);
		System.out.println("getIDHierarchyFromDataCatalogID: catalog: " + catalog);
		if(usession.getUserName() != null) {
			System.out.println("getIDHierarchyFromDataCatalogID: usession\n: " + usession.toString("session: "));
			nodehierarchy =  WriteReadDatabaseObjects.getIDHierarchyFromDataCatalogID(usession, 
					basecatalog, catalog);
		} else {
			throw new IOException("No user logged in, not even guest");
		}
		return nodehierarchy;
	}

	public GCSBlobFileInformation retrieveBlobFromURL(String requestUrl) throws IOException {
		
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		/*
		String sourceID = "";
		
		
		ActivityRepositoryInitialReadInfo actread = 
				ActivityUtilities.generateActivityRepositoryInitialReadInfo(obj, 
						"", 
						requestUrl, 
						fileSourceType);
		ActivityRepositoryInitialReadURL urlact = new ActivityRepositoryInitialReadURL(actread);
		return InitialStagingUtilities.retrieveBlobFromURL(actread, usession, sourceID);
		*/
		
		String uploadDescriptionText = "Uploaded File from URL";

		URL urlconnect = new URL(requestUrl);
		URLConnection c = urlconnect.openConnection();
		String contentType = c.getContentType();

		URL urlstream = new URL(requestUrl);
		InputStream in = urlstream.openStream();
		String username = usession.getUserName();
		String path = GCSServiceRoutines.createUploadPath(username);
		String name = extractNameFromURL(requestUrl);
		
		
		String sourceID = QueryBase.getDataSourceIdentification(username);
		String id = "upload-" + username + "-" + name;
		DatabaseObject obj = new DatabaseObject(id, username, username, sourceID);
		
		DatabaseObjectHierarchy hierarchy = CreateRepositoryCatagoryFiles.createRepositoryFileStaging(obj, 
				name, contentType,
				uploadDescriptionText, sessionid,path);
		
		RepositoryFileStaging staging = (RepositoryFileStaging) hierarchy.getObject();
		DatabaseObjectHierarchy hier = hierarchy.getSubObject(staging.getBlobFileInformation());
		GCSBlobFileInformation source = (GCSBlobFileInformation) hier.getObject();
		DatabaseObjectHierarchy filehier = hierarchy.getSubObject(staging.getRepositoryFile());
		InitialStagingRepositoryFile staginginfo = (InitialStagingRepositoryFile) filehier.getObject();
		staginginfo.setUploadFileSource(MetaDataKeywords.initialReadInLocalStorageSystem);
		staginginfo.setFileSourceIdentifier(MetaDataKeywords.urlsource);
		System.out.println(hierarchy.toString("FileUploadServlet: "));
		
		DatabaseWriteBase.writeObjectWithTransaction(hierarchy, 
				MetaDataKeywords.InitialReadFromWebLocation,usession);		

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
		//return InitialStagingUtilities.retrieveBlobFromContent(filename, content, usession);
		
		
		String username = usession.getUserName();
		String path = GCSServiceRoutines.createUploadPath(username);
		String contentType = "text/plain";
		String uploadDescriptionText = "Uploaded File from text input";
		
		String sourceID = QueryBase.getDataSourceIdentification(username);
		String id = "uploadtext-" + username + "-" + filename;
		DatabaseObject obj = new DatabaseObject(id, username, username, sourceID);
		DatabaseObjectHierarchy hierarchy = CreateRepositoryCatagoryFiles.createRepositoryFileStaging(obj, 
				filename, contentType,
				uploadDescriptionText, sessionid,path);
		
		RepositoryFileStaging staging = (RepositoryFileStaging) hierarchy.getObject();
		DatabaseObjectHierarchy hier = hierarchy.getSubObject(staging.getBlobFileInformation());
		GCSBlobFileInformation source = (GCSBlobFileInformation) hier.getObject();
		DatabaseObjectHierarchy filehier = hierarchy.getSubObject(staging.getRepositoryFile());
		InitialStagingRepositoryFile staginginfo = (InitialStagingRepositoryFile) filehier.getObject();
		staginginfo.setUploadFileSource(MetaDataKeywords.initialReadFromUserInterface);
		staginginfo.setFileSourceIdentifier(MetaDataKeywords.stringSource);
		System.out.println(hierarchy.toString("FileUploadServlet: "));
		
		InputStream in = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
		retrieveContentFromStream(in, source);
		DatabaseWriteBase.writeObjectWithTransaction(hierarchy, 
				MetaDataKeywords.initialReadFromUserInterface,usession);
		
		return source;
		
	}
	
	private void retrieveContentFromStream(InputStream in, GCSBlobFileInformation source) throws IOException {
		GCSServiceRoutines.writeBlob(source.getGSFilename(), source.getFiletype(), in);
	}

	public ArrayList<DatabaseObjectHierarchy> getAllDatabaseObjectHierarchyOwnedUser(String classType) throws IOException {
		ArrayList<DatabaseObjectHierarchy> objects = null;
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		if(usession != null) {
			objects = WriteReadDatabaseObjects.getAllDatabaseObjectHierarchyOwnedUser(usession, classType);
		} else {
			objects = new ArrayList<DatabaseObjectHierarchy>();
		}
		return objects;
		
	}
	public ArrayList<DatabaseObjectHierarchy> getSetOfDatabaseObjectHierarchyForUser(String classType) throws IOException {
		ArrayList<DatabaseObjectHierarchy> objects = null;
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		if(usession != null) {
			objects = WriteReadDatabaseObjects.getAllDatabaseObjectHierarchyForUser(usession, classType);
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
			InterpretDataInterface interpret = InitializationBase.getInterpret().valueOf(structureName);
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
	
	public DatabaseObjectHierarchy createDatabasePerson(DatabaseObject obj, 
			String userClassification, 
			NameOfPerson name, 
			String userLevel,
			DataCatalogID catid) {
		DataCatalogID userdatid = new DataCatalogID(catid, 
				catid.getCatalogBaseName(), 
				MetaDataKeywords.dataTypeIndividual, 
				name.getAlphabeticName(), catid.getPath());
		DatabaseObjectHierarchy hierarchy = CreateContactObjects.fillMinimalPersonDescription(obj, 
				userLevel, userClassification, name,userdatid);
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
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,null);
		GCSBlobFileInformation target = new GCSBlobFileInformation(structure, path,
				filename, info.getFiletype(), info.getDescription());
		info.setSourceID(target.getSourceID());

		GCSBlobContent content = moveBlob(target,info);

		DatabaseObjectHierarchy hierarchy = CreateContactObjects.fillDatasetImage(obj, catid, imageType, content.getUrl());
		return hierarchy;
	}
	
	public DatabaseObjectHierarchy createConsortiumCatalogObject(DataCatalogID catid, String consortiumName) throws IOException {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		String username = usession.getUserName();
		DatabaseObjectHierarchy hierarchy = null;
		if(username.compareTo(catid.getOwner()) == 0) {
			String sourceID = QueryBase.getDataSourceIdentification(username);
			catid.setSourceID(sourceID);
			hierarchy = CreateConsortiumCatalogObject.createConsortiumCatalogObject(catid, consortiumName);
		} else {
			throw new IOException("Owner mismatch: client: " + catid.getOwner() + "  server: " + username);
		}
		return hierarchy;
	}
	
	public DatabaseObjectHierarchy addConsortiumMember(DatabaseObjectHierarchy consortiumhier, 
			String consortiumName, String consortiumMember) throws IOException {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		String username = usession.getUserName();
		DatabaseObjectHierarchy hierarchy = null;
		if(username.compareTo(consortiumhier.getObject().getOwner()) == 0) {
			String sourceID = QueryBase.getDataSourceIdentification(username);
			consortiumhier.getObject().setSourceID(sourceID);
			hierarchy = CreateConsortiumCatalogObject.addConsortiumMember(consortiumhier, 
					consortiumName, consortiumMember);
		} else {
			throw new IOException("Owner mismatch: client: " + consortiumhier.getObject().getOwner() + "  server: " + username);
		}
		return hierarchy;
	}
	
	
	
	public ArrayList<DatabaseObjectHierarchy> getConsortiumForUser() {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		return ReadInConsortium.getConsortiumForUser(usession);
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
			hier = WriteBaseCatalogObjects.writeDatabaseObjectHierarchyWithTransaction(hierarchy, 
					MetaDataKeywords.updateCatalogObjectEvent);
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
	
	public List<DatabaseObject> getAvailableDatabaseObjects(String classname) {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		return WriteReadDatabaseObjects.getAvailableDatabaseObject(classname, usession);
	}
	
	public List<String> getAvailableUsernames() {
		String sessionid = getThreadLocalRequest().getSession().getId();
		UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(sessionid);
		String classname = UserAccount.class.getCanonicalName();
		List<DatabaseObject> objs = WriteReadDatabaseObjects.getAvailableDatabaseObject(classname, usession);
		List<String> usernames = new ArrayList<String>();
		for(DatabaseObject obj : objs) {
			UserAccount account = (UserAccount) obj;
			usernames.add(account.getAccountUserName());
		}
		return usernames;
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
		return WriteReadDatabaseObjects.getIDHierarchyFromDataCatalogIDAndClassType(usession, 
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

	@Override
	public String deleteFromStorage(String blobkey) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}


}