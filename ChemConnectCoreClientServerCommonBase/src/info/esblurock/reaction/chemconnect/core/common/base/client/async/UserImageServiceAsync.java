package info.esblurock.reaction.chemconnect.core.common.base.client.async;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

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

public interface UserImageServiceAsync {

	void getUploadedImageSet(ImageServiceInformation serviceInfo, AsyncCallback<ArrayList<UploadedImage>> callback);

	void getBlobstoreUploadUrl(String keywordName, boolean uploadService,
			AsyncCallback<ImageServiceInformation> callback);

	void getUploadedImageSetFromKeywordAndUser(String keyword, AsyncCallback<ArrayList<UploadedImage>> callback);

	void deleteFromStorage(String blobkey, AsyncCallback<String> callback);

	void updateImages(ArrayList<UploadedImage> images, AsyncCallback<String> callback);

	void moveBlobFromUpload(GCSBlobFileInformation fileinfo, AsyncCallback<GCSBlobContent> callback);

	void moveBlob(GCSBlobFileInformation fileinfo, GCSBlobFileInformation source,
			AsyncCallback<GCSBlobContent> callback);

	void getBlobContent(GCSBlobFileInformation gcsinfo, AsyncCallback<GCSBlobContent> callback);

	void getBlobAsLines(GCSBlobContent info, AsyncCallback<ArrayList<String>> callback);

	void getUploadedFiles(AsyncCallback<ArrayList<GCSBlobFileInformation>> callback);

	void deleteUploadedFiles(ArrayList<GCSBlobFileInformation> fileset, AsyncCallback<Void> callback);

	void deleteUploadedFile(GCSBlobFileInformation gcsinfo, AsyncCallback<Void> callback);

	void retrieveBlobFromContent(String filename, String content, AsyncCallback<GCSBlobFileInformation> callback);

	void retrieveBlobFromURL(String requestUrl, AsyncCallback<GCSBlobFileInformation> callback);

	void getFileInterpretionChoices(GCSBlobFileInformation info, AsyncCallback<HierarchyNode> callback);

	void getUserDatasetCatalogHierarchy(String username, AsyncCallback<DatabaseObjectHierarchy> callback);

	void createNewCatalogHierarchy(DatabaseObject obj, String newSimpleName, 
			String id, String onelinedescription, String catagorytype, AsyncCallback<DatabaseObjectHierarchy> callback);

	void writeDatabaseObjectHierarchy(DatabaseObjectHierarchy hierarchy,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void getUploadedFilesHiearchy(ArrayList<String> fileTypes,AsyncCallback<HierarchyNode> callback);

	void getSetOfDatabaseObjectHierarchyForUser(String classType,
			AsyncCallback<ArrayList<DatabaseObjectHierarchy>> callback);

	void createDatabasePerson(DatabaseObject obj, String userClassification, NameOfPerson name, String userlevel,
			DataCatalogID catid, AsyncCallback<DatabaseObjectHierarchy> callback);

	void createOrganization(DatabaseObject obj, String shortname, String organizationname, DataCatalogID catid,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void createEmptyObject(DatabaseObject obj, String dataType, AsyncCallback<DatabaseObjectHierarchy> callback);

	void getIDsFromConceptLink(String concept, AsyncCallback<HierarchyNode> callback);

	void getIDHierarchyFromDataCatalogID(String basecatalog, String catalog, AsyncCallback<HierarchyNode> callback);

	void getCatalogObject(String id, String dataType, AsyncCallback<DatabaseObjectHierarchy> callback);

	void writeYamlObjectHierarchy(String id, String canonicalclass, AsyncCallback<Void> callback);

	void createEmptyMultipleObject(ChemConnectCompoundMultiple multiple,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void getStructureFromFileType(String filetype, AsyncCallback<String> callback);

	void getIDHierarchyFromDataCatalogIDAndClassType(String catalogbasename, String classtype,
			AsyncCallback<HierarchyNode> callback);

	void extractLinkObjectFromStructure(DatabaseObjectHierarchy hierarchy, String linktypeid,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void writeBlobContent(GCSBlobContent gcs, AsyncCallback<Void> callback);


	void createDatasetImage(DatabaseObject obj, DataCatalogID catid, String imageType, GCSBlobFileInformation info,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void getIDHierarchyFromFamilyNameAndUser(String familyname, AsyncCallback<ArrayList<NameOfPerson>> callback);

	void getTopCatalogObject(String id, String dataType, AsyncCallback<DatabaseObjectHierarchy> callback);

	void deleteObject(String id, String type, AsyncCallback<Void> callback);

	void getUploadedStagedFiles(AsyncCallback<ArrayList<DatabaseObjectHierarchy>> callback);

	void createRepositoryDataFile(DatabaseObjectHierarchy stagehierarchy, DataCatalogID catalogid,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void getAllDatabaseObjectHierarchyOwnedUser(String classType,
			AsyncCallback<ArrayList<DatabaseObjectHierarchy>> callback);

	void createConsortiumCatalogObject(DataCatalogID catid, String consortiumName,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void getConsortiumForUser(AsyncCallback<ArrayList<DatabaseObjectHierarchy>> callback);

	void getAvailableDatabaseObjects(String classname, AsyncCallback<List<DatabaseObject>> callback);

	void getAvailableUsernames(AsyncCallback<List<String>> callback);

	void addConsortiumMember(DatabaseObjectHierarchy consortiumhierarchy, String consortiumName, String consortiumMember,
			AsyncCallback<DatabaseObjectHierarchy> callback);

	void getIDHierarchyFromDataCatalogAndUser(String datacatalog, AsyncCallback<HierarchyNode> callback);

	void getBlobDatasetObject(GCSBlobFileInformation info, DatabaseObject obj, DataCatalogID catid,
			AsyncCallback<DatabaseObjectHierarchy> callback);
}
