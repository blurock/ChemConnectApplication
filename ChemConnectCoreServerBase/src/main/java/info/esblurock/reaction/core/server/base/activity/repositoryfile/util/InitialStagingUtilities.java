package info.esblurock.reaction.core.server.base.activity.repositoryfile.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.apache.commons.fileupload.FileItemStream;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.activity.ActivityInformationRecord;
import info.esblurock.reaction.chemconnect.core.base.activity.read.ActivityRepositoryInitialReadInfo;
import info.esblurock.reaction.chemconnect.core.base.activity.read.ActivityRepositoryInitialReadLocalFile;
import info.esblurock.reaction.chemconnect.core.base.activity.read.ActivityRepositoryInitialReadStringContent;
import info.esblurock.reaction.chemconnect.core.base.activity.read.ActivityRepositoryInitialReadURL;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.InitialStagingRepositoryFile;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryFileStaging;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.core.server.base.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.base.db.GCSServiceRoutines;

public class InitialStagingUtilities {
	
	/**
	 * @param requestUrl The URL to retrieve the file 
	 * @param usession Session information
	 * @Param sourceID The source ID of the transaction
	 * @return The hierarchy of the RepositoryFileStaging object
	 * @throws IOException
	 * 
	 */
	public static DatabaseObjectHierarchy retrieveBlobFromURL(ActivityInformationRecord baseinfo,
			UserSessionData usession, String sourceID) throws IOException {
		
		ActivityRepositoryInitialReadURL info = (ActivityRepositoryInitialReadURL) baseinfo;
		String filename = info.getDescriptionTitle();
		String requestUrl= info.getFileSourceIdentifier();
		String uploadDescriptionText = "Uploaded File from URL";

		//  Set up input stream for file read
		URL urlconnect = new URL(requestUrl);
		URLConnection c = urlconnect.openConnection();
		URL urlstream = new URL(requestUrl);
		InputStream in = urlstream.openStream();
		String contentType = c.getContentType();
		
		DatabaseObject obj = createUploadFileDataObject(usession, sourceID, filename);
		
		DatabaseObjectHierarchy hierarchy = createAndFillRepositoryFileStaging(obj,usession, sourceID, filename, 
				contentType, uploadDescriptionText, 
				info);
		
		DatabaseWriteBase.writeObjectWithTransaction(hierarchy, 
				MetaDataKeywords.InitialReadFromWebLocation,usession);		

		retrieveContentFromStream(in, hierarchy);

		return hierarchy;
	}
	
	/**
	 * @param fileItem The fileitem set up by the FileUploadServlet
	 * @param usession The user session information
	 * @param sourceID The source ID
	 * @return The hierarchy of the RepositoryFileStaging object
	 * @throws IOException
	 * 
	 * 
	 */
	public static DatabaseObjectHierarchy  localFileUpload(ActivityRepositoryInitialReadLocalFile info,
			FileItemStream fileItem,
			UserSessionData usession, String sourceID) throws IOException {
		String uploadDescriptionText = "Uploaded File from local file system";
		
		InputStream in = fileItem.openStream();

		
		DatabaseObject obj = createUploadFileDataObject(usession, sourceID, fileItem.getName());
		
		DatabaseObjectHierarchy hierarchy = createAndFillRepositoryFileStaging(obj, 
				usession, sourceID, 
				fileItem.getName(), fileItem.getContentType(), uploadDescriptionText, 
				info);
			
		retrieveContentFromStream(in, hierarchy);

		DatabaseWriteBase.writeObjectWithTransaction(hierarchy,MetaDataKeywords.initialReadInLocalStorageSystem,
				usession);
		
		return hierarchy;
	}

	/**
	 * @param filename The given filename of the file
	 * @param content The actual file information
	 * @param usession The user session information
	 * @param sourceID The source ID
	 * @return The hierarchy of the RepositoryFileStaging object
	 * @throws IOException
	 */
	public static DatabaseObjectHierarchy retrieveBlobFromContent(ActivityInformationRecord baseinfo,
			UserSessionData usession,String sourceID) throws IOException {
		
		ActivityRepositoryInitialReadStringContent info = (ActivityRepositoryInitialReadStringContent) baseinfo;
		String filename = info.getDescriptionTitle();
		String content =  info.getFileSourceIdentifier();
		String uploadDescriptionText = "Uploaded File from text input";
		String contentType = "text/plain";

		InputStream in = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
		
		DatabaseObject obj = createUploadFileDataObject(usession, sourceID,filename);
	
		DatabaseObjectHierarchy hierarchy = createAndFillRepositoryFileStaging(obj, 
				usession, sourceID, filename, contentType, uploadDescriptionText,
				info);
		
		retrieveContentFromStream(in, hierarchy);
		DatabaseWriteBase.writeObjectWithTransaction(hierarchy, 
				MetaDataKeywords.initialReadFromUserInterface,usession);
		
		return hierarchy;
	}
	
	
	public static String generateUniqueUploadFileName(String filename, String sourceID) {
		return filename + "-" + sourceID;
	}
	
	/**
	 * @param usession The user session data
	 * @param sourceID The source ID 
	 * @param filename The base filename of the upload file
	 * @return A standard DatabaseObject for the uploaded file
	 */
	public static DatabaseObject createUploadFileDataObject(UserSessionData usession, String sourceID, String filename) {
		String username = usession.getUserName();
		String id = "upload-" + username + generateUniqueUploadFileName(filename, sourceID);
		DatabaseObject obj = new DatabaseObject(id, username, username, sourceID);
		return obj;
	}
	
	public static DatabaseObjectHierarchy createAndFillRepositoryFileStaging(DatabaseObject obj,
			UserSessionData usession, String sourceID, String filename,
			String contentType, String uploadDescriptionText,
			ActivityRepositoryInitialReadInfo info) {
		// Create and fill in the RepositoryFileStaging information
		DatabaseObjectHierarchy hierarchy = CreateRepositoryCatagoryFiles.createRepositoryFileStaging(obj, 
				generateUniqueUploadFileName(sourceID,filename), contentType,
				uploadDescriptionText, usession.getSessionID(),
				GCSServiceRoutines.createUploadPath(usession.getUserName()));
		
		RepositoryFileStaging staging = (RepositoryFileStaging) hierarchy.getObject();
		DatabaseObjectHierarchy filehier = hierarchy.getSubObject(staging.getRepositoryFile());
		InitialStagingRepositoryFile staginginfo = (InitialStagingRepositoryFile) filehier.getObject();
		staginginfo.setUploadFileSource(info.getClass().getSimpleName());
		staginginfo.setFileSourceIdentifier(info.getFileSourceIdentifier());
		System.out.println(hierarchy.toString("FileUploadServlet: "));
		return hierarchy;
	}
	
	
	

	/**
	 * @param in The input stream of the file information
	 * @param hierarchy The hierarchy of the RepositoryFileStaging object
	 * @throws IOException
	 */
	private static void retrieveContentFromStream(InputStream in, DatabaseObjectHierarchy hierarchy) throws IOException {
		RepositoryFileStaging staging = (RepositoryFileStaging) hierarchy.getObject();
		DatabaseObjectHierarchy hier = hierarchy.getSubObject(staging.getBlobFileInformation());
		GCSBlobFileInformation source = (GCSBlobFileInformation) hier.getObject();
		GCSServiceRoutines.writeBlob(source.getGSFilename(), source.getFiletype(), in);
	}
	
	
	/**
	 * @param url The full url name of the file source
	 * @return The filename isolated from the URL
	 */
	public static String extractNameFromURL(String url) {
		int pos = url.lastIndexOf("/");
		String name = url;
		if(pos > 0) {
			name = url.substring(pos+1);
		}
		return name;
	}

}
