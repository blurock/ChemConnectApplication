package info.esblurock.reaction.core.server.base.db;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import com.google.api.gax.paging.Page;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.cloud.storage.Storage.CopyRequest;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Blob.BlobSourceOption;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.GoogleCloudStorageConstants;
import info.esblurock.reaction.core.server.base.queries.QueryBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseData;

public class GCSServiceRoutines {
	public static Storage storage = null;
	static {
		storage = StorageOptions.getDefaultInstance().getService();
	}

	static Long longGeneration = (long) 42.0;

	public static String createUploadPath(String username) {
		String path = GoogleCloudStorageConstants.uploadPathPrefix + "/" + username;
		return path;
	}

	public static GCSBlobFileInformation createInitialUploadInfo(GCSBlobFileInformation source, String path,
			String filename, String contentType, String uploadDescriptionText, String ip, String username) {

		source.fillGCS(path, filename, contentType, uploadDescriptionText);
		return source;
	}

	public static void uploadFileBlob(String id, String bucket, String ip, String username, String path,
			String filename, String contentType, String description, String contentS) throws IOException {

		String sourceID = QueryBase.getDataSourceIdentification(username);
		DatabaseObject obj = new DatabaseObject(id, username, username, sourceID);
		InterpretBaseData interpret = InterpretBaseData.GCSBlobFileInformation;
		DatabaseObjectHierarchy hier = interpret.createEmptyObject(obj);
		GCSBlobFileInformation info = (GCSBlobFileInformation) hier.getObject();
		createInitialUploadInfo(info, path, filename, contentType, description, ip, username);
		System.out.println("uploadFileBlob: \n" + info.toString());
		String url = null;
		GCSBlobContent gcs = new GCSBlobContent(url, info);
		gcs.setBytes(contentS);
		writeBlobContent(gcs);
	}

	public static void writeBlobContent(GCSBlobContent gcs) throws IOException {
		GCSBlobFileInformation info = gcs.getInfo();
		String contentS = gcs.getBytes();
		BlobId blobId = BlobId.of(GCSServiceRoutines.getGCSStorageBucket(), info.getGSFilename());

		byte[] content = contentS.getBytes(StandardCharsets.UTF_8);

		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(info.getFiletype())
				// Modify access list to allow all users with link to read file
				.setAcl(new ArrayList<Acl>(Arrays.asList(Acl.of(User.ofAllUsers(), Acl.Role.READER)
				// ,Acl.of(User.ofAllAuthenticatedUsers(), Acl.Role.OWNER)
				))).build();

		// Acl.of(User.ofAllAuthenticatedUsers(), Acl.Role.OWNER)

		try (WriteChannel writer = storage.writer(blobInfo)) {
			writer.write(ByteBuffer.wrap(content, 0, content.length));
		} catch (Exception ex) {

			System.out.println("writeBlobContent: " + ex.toString());

			throw new IOException(
					"Failure to write blob: " + info.getGSFilename() + " with size " + contentS.length() + "bytes");
		}
		DatabaseWriteBase.writeObjectWithTransaction(gcs.getInfo());
	}
	
	public static void writeBlob(String filename, String contentType, InputStream in) throws IOException {
		
		String bucket = GCSServiceRoutines.getGCSStorageBucket();
		
		BlobInfo info = BlobInfo.newBuilder(bucket, 
				filename)
				.setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
				.setContentType(contentType)
				.build();
		@SuppressWarnings("deprecation")
		BlobInfo blobInfo = storage.create(info, in);
		System.out.println("FileUploadServlet: " + blobInfo.getMediaLink());
		
	}

	public static GCSBlobContent moveBlob(GCSBlobFileInformation target, GCSBlobFileInformation source)
			throws IOException {
		CopyRequest request = CopyRequest.newBuilder()
				.setSource(BlobId.of(GCSServiceRoutines.getGCSStorageBucket(), source.getGSFilename()))
				.setTarget(BlobId.of(GCSServiceRoutines.getGCSStorageBucket(), target.getGSFilename())).build();
		Blob blob = storage.copy(request).getResult();
		blob.createAcl(Acl.of(User.ofAllUsers(), Acl.Role.READER));

		String url = "https://storage.googleapis.com/" + GCSServiceRoutines.getGCSStorageBucket() + "/"
				+ target.getGSFilename();
		GCSBlobContent gcscontent = new GCSBlobContent(url, target);
		String sourceID = QueryBase.getDataSourceIdentification(target.getOwner());
		target.setSourceID(sourceID);
		WriteReadDatabaseObjects.deletePreviousBlobStorageMoves(target);
		DatabaseWriteBase.writeObjectWithTransaction(target);
		return gcscontent;
	}

	public static String getGCSStorageBucket() throws IOException {
		String bucket = "blurock-chemconnect.appspot.com";
		String hostname;
		try {
			hostname = InetAddress.getLocalHost().getHostName();
			if (!hostname.startsWith("blurock-chemconnect.appspot.com")) {
				bucket = "blurock-chemconnect-localhost";
			}
		} catch (UnknownHostException e) {
			throw new IOException("Cannot retrieve hostname for bucket assignment.. using localhost bucket");
		}
		return bucket;
	}

	public static void listStagingFiles(String username) throws IOException {
		String directory = createUploadPath(username);
		String bucketName = getGCSStorageBucket();
		Page<Blob> blobs =
		        storage.list(
		            bucketName, BlobListOption.prefix(directory));
		    for (Blob blob : blobs.iterateAll()) {
		    	System.out.println("listStagingFiles:  " + blob.getName());
		    }
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

	public static void deleteBlob(GCSBlobFileInformation gcsinfo) throws IOException {
		BlobId blobId = BlobId.of(GCSServiceRoutines.getGCSStorageBucket(), gcsinfo.getGSFilename());
		storage.delete(blobId);
	}

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
	public static void deleteBlobFromURL(String url) {
		// "https://storage.googleapis.com/"
		String prefix = "https://storage.googleapis.com/";
		int bucketend = url.indexOf('/', prefix.length());
		String bucket = url.substring(prefix.length(), bucketend);
		String path = url.substring(bucketend + 1);
		
		BlobId blobId = BlobId.of(bucket, path);
		storage.delete(blobId);
	

	}

}
