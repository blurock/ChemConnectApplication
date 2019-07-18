package info.esblurock.reaction.core.server.base.db;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.gwt.user.client.Cookies;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.InitialStagingRepositoryFile;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryFileStaging;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.core.server.base.create.CreateBaseCatalogObjects;
import info.esblurock.reaction.core.server.base.queries.QueryBase;
import info.esblurock.reaction.core.server.base.services.util.ContextAndSessionUtilities;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseData;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!ServletFileUpload.isMultipartContent(request)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Not a multipart request");
			return;
		}
		ServletFileUpload upload = new ServletFileUpload(); // from Commons
		try {
			FileItemIterator iter = upload.getItemIterator(request);
			if (iter.hasNext()) {
				FileItemStream fileItem = iter.next();
				
				InputStream in = fileItem.openStream();
				Storage storage = StorageOptions.getDefaultInstance().getService();
				ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), request.getSession());
				util.printOutSessionAttributes();
				
				
				String ip = request.getSession().getId();
				UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(ip);
				String username = usession.getUserName();
				String path = GCSServiceRoutines.createUploadPath(username);
				
				String uploadDescriptionText = "Uploaded File from FileUploadServlet";
				String sessionID = util.getId();
				
				String sourceID = QueryBase.getDataSourceIdentification(username);
				String id = "upload-" + username + "-" + fileItem.getName();
				DatabaseObject obj = new DatabaseObject(id, username, username, sourceID);
				
				DatabaseObjectHierarchy hierarchy = CreateBaseCatalogObjects.createRepositoryFileStaging(obj, 
						fileItem.getName(), fileItem.getContentType(),
						uploadDescriptionText, sessionID,path);
				
				RepositoryFileStaging staging = (RepositoryFileStaging) hierarchy.getObject();
				DatabaseObjectHierarchy hier = hierarchy.getSubObject(staging.getBlobFileInformation());
				GCSBlobFileInformation source = (GCSBlobFileInformation) hier.getObject();
				DatabaseObjectHierarchy filehier = hierarchy.getSubObject(staging.getRepositoryFile());
				InitialStagingRepositoryFile staginginfo = (InitialStagingRepositoryFile) filehier.getObject();
				staginginfo.setUploadFileSource(MetaDataKeywords.initialReadInLocalStorageSystem);
				System.out.println(hierarchy.toString("FileUploadServlet: "));
		
				BlobInfo info = BlobInfo.newBuilder(GCSServiceRoutines.getGCSStorageBucket(), source.getGSFilename())
						.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
						.setContentType(fileItem.getContentType())
						.build();
				@SuppressWarnings("deprecation")
				BlobInfo blobInfo = storage.create(info, in);
				System.out.println("FileUploadServlet: " + blobInfo.getMediaLink());
				
				DatabaseWriteBase.writeObjectWithTransaction(hierarchy, 
						MetaDataKeywords.initialReadInLocalStorageSystem);
				
				//DatabaseWriteBase.writeObjectWithTransaction(source);
			}
		} catch (Exception caught) {
			throw new RuntimeException(caught);
		}
	}
}