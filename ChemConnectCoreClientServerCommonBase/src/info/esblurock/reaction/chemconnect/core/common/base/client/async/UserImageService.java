package info.esblurock.reaction.chemconnect.core.common.base.client.async;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryFileStaging;
import info.esblurock.reaction.chemconnect.core.base.image.ImageServiceInformation;
import info.esblurock.reaction.chemconnect.core.base.image.UploadedImage;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;




@RemoteServiceRelativePath("images")
public interface UserImageService  extends RemoteService  {
	static public String uploadName = "image";
	static public String bucketName = "images";
	static public String uploadRoot = "/upload";
	static public String sourceFileParameter = "source";
	static public String keywordNameParameter = "keywordName";

	
	   public static class Util {
	       private static UserImageServiceAsync instance;

	       public static UserImageServiceAsync getInstance() {
	           if (instance == null) {
	               instance = GWT.create(UserImageService.class);
	           }
	           return instance;
	       }
	   }

	
	ImageServiceInformation getBlobstoreUploadUrl(String keywordName, boolean uploadService);

	ArrayList<UploadedImage> getUploadedImageSet(ImageServiceInformation serviceInfo) throws IOException;

	ArrayList<UploadedImage> getUploadedImageSetFromKeywordAndUser(String keyword) throws IOException;

	String deleteFromStorage(String blobkey) throws IOException;

	String updateImages(ArrayList<UploadedImage> images) throws IOException;
	
	GCSBlobContent moveBlobFromUpload(GCSBlobFileInformation fileinfo)  throws IOException;
	
	GCSBlobContent moveBlob(GCSBlobFileInformation fileinfo, GCSBlobFileInformation source) throws IOException;
	
	GCSBlobContent getBlobContent(GCSBlobFileInformation gcsinfo) throws IOException;
	
	ArrayList<String> getBlobAsLines(GCSBlobContent info) throws IOException;
	
	void deleteUploadedFile(GCSBlobFileInformation gcsinfo) throws IOException;
	
	void deleteUploadedFiles(ArrayList<GCSBlobFileInformation> fileset) throws IOException;
	
	ArrayList<DatabaseObjectHierarchy> getUploadedStagedFiles() throws IOException;
	
	ArrayList<GCSBlobFileInformation> getUploadedFiles() throws IOException;
	
	GCSBlobFileInformation retrieveBlobFromContent(String filename, String content) throws IOException;
	
	GCSBlobFileInformation retrieveBlobFromURL(String requestUrl) throws IOException;
	
	HierarchyNode getFileInterpretionChoices(GCSBlobFileInformation info) throws IOException;
	
	DatabaseObjectHierarchy getUserDatasetCatalogHierarchy(String username) throws IOException;
	
	DatabaseObjectHierarchy createNewCatalogHierarchy(DatabaseObject obj, String newSimpleName, String id, 
			String onelinedescription, String catagorytype) throws IOException;

	DatabaseObjectHierarchy writeDatabaseObjectHierarchy(DatabaseObjectHierarchy hierarchy) throws IOException;
	
	HierarchyNode getUploadedFilesHiearchy(ArrayList<String> fileTypes) throws IOException;
	
	ArrayList<DatabaseObjectHierarchy> getAllDatabaseObjectHierarchyOwnedUser(String classType) throws IOException;
	
	ArrayList<DatabaseObjectHierarchy> getSetOfDatabaseObjectHierarchyForUser(String classType) throws IOException;
	
	DatabaseObjectHierarchy createDatabasePerson(DatabaseObject obj, String userClassification, 
			NameOfPerson name, String userlevel, DataCatalogID catid);
	
	DatabaseObjectHierarchy createOrganization(DatabaseObject obj, String shortname, String organizationname, DataCatalogID catid);
	
	DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj, String dataType);
	
	HierarchyNode getIDsFromConceptLink(String concept) throws IOException;
	
	HierarchyNode getIDHierarchyFromDataCatalogID(String basecatalog, String catalog) throws IOException;
	
	DatabaseObjectHierarchy getCatalogObject(String id, String dataType) throws IOException;
	
	void writeYamlObjectHierarchy(String id, String canonicalclass) throws IOException;
	
	DatabaseObjectHierarchy createEmptyMultipleObject(ChemConnectCompoundMultiple multiple);
	
	String getStructureFromFileType(String filetype);
	
	HierarchyNode getIDHierarchyFromDataCatalogIDAndClassType(String catalogbasename, String classtype) throws IOException;
		
	
	DatabaseObjectHierarchy  extractLinkObjectFromStructure(DatabaseObjectHierarchy hierarchy, String linktypeid) throws IOException;
	
	public void writeBlobContent(GCSBlobContent gcs) throws IOException;
	
	public DatabaseObjectHierarchy createDatasetImage(DatabaseObject obj,DataCatalogID catid,
			String imageType, GCSBlobFileInformation info) throws IOException;
	
	public ArrayList<NameOfPerson> getIDHierarchyFromFamilyNameAndUser(String familyname) throws IOException;
	
	public DatabaseObjectHierarchy getTopCatalogObject(String id, String dataType) throws IOException;
	
	void deleteObject(String id, String type) throws IOException;
	
	DatabaseObjectHierarchy createRepositoryDataFile(DatabaseObjectHierarchy stagehierarchy,
			DataCatalogID catalogid) throws IOException;
	
	public DatabaseObjectHierarchy createConsortiumCatalogObject(DataCatalogID catid, String consortiumName) throws IOException;

	public DatabaseObjectHierarchy addConsortiumMember(DatabaseObjectHierarchy consortiumhierarchy, 
			String consortiumName, String consortiumMember) throws IOException;
	public ArrayList<DatabaseObjectHierarchy> getConsortiumForUser();
	
	List<DatabaseObject> getAvailableDatabaseObjects(String classname);
	
	public List<String> getAvailableUsernames();

}
