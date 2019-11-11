package info.esblurock.reaction.core.server.base.activity.repositoryfile.util;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryFileStaging;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.query.QuerySetupBase;
import info.esblurock.reaction.chemconnect.core.base.query.SetOfQueryPropertyValues;
import info.esblurock.reaction.chemconnect.core.base.query.SingleQueryResult;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.core.server.base.db.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.base.queries.QueryBase;

public class RetrieveRepositoryFileInfo {
	
	public static ArrayList<DatabaseObjectHierarchy> getUploadedStagedFiles(UserSessionData usession) throws IOException {
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
	}
	public static ArrayList<GCSBlobFileInformation> getUploadedFiles(UserSessionData usession) throws IOException {
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
	}

}
