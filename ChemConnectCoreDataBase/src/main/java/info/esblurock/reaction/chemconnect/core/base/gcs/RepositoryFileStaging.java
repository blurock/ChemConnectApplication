package info.esblurock.reaction.chemconnect.core.base.gcs;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.SimpleCatalogObject;

@SuppressWarnings("serial")
@Entity
public class RepositoryFileStaging extends DatabaseObject {
	@Index 
	String blobFileInformation;
	@Index
	String repositoryFile;
	@Index
	String stagingFilePresent;
	
	public RepositoryFileStaging() {
	}

	public RepositoryFileStaging(DatabaseObject object, 
			String blobFileInformation, String repositoryFile, String stagingFilePresent) {
		super(object);
		this.blobFileInformation = blobFileInformation;
		this.repositoryFile = repositoryFile;
		this.stagingFilePresent = stagingFilePresent;
	}

	public String getBlobFileInformation() {
		return blobFileInformation;
	}

	public void setBlobFileInformation(String blobFileInformation) {
		this.blobFileInformation = blobFileInformation;
	}

	public String getRepositoryFile() {
		return repositoryFile;
	}

	public void setRepositoryFile(String repositoryFile) {
		this.repositoryFile = repositoryFile;
	}
	
	public String getStagingFilePresent() {
		return stagingFilePresent;
	}

	public void setStagingFilePresent(String stagingFilePresent) {
		this.stagingFilePresent = stagingFilePresent;
	}

	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Blob File Information :" + blobFileInformation + "\n");
		build.append(prefix + "Repository File info  :" + repositoryFile + "\n");
		build.append(prefix + "Staged file processed : " + stagingFilePresent + "\n");
		return build.toString();
	}
	

}
