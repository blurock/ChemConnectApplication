package info.esblurock.reaction.chemconnect.core.base.gcs;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class InitialStagingRepositoryFile extends ChemConnectCompoundDataStructure {
	
	@Index
	String fileSourceIdentifier;
	@Index
	String uploadFileSource;

	public InitialStagingRepositoryFile() {
	}

	public InitialStagingRepositoryFile(ChemConnectCompoundDataStructure structure,
			String fileSourceIdentifier, String uploadFileSource) {
		super(structure);
		this.fileSourceIdentifier = fileSourceIdentifier;
		this.uploadFileSource = uploadFileSource;
	}

	public String getFileSourceIdentifier() {
		return fileSourceIdentifier;
	}

	public void setFileSourceIdentifier(String fileSourceIdentifier) {
		this.fileSourceIdentifier = fileSourceIdentifier;
	}

	public String getUploadFileSource() {
		return uploadFileSource;
	}

	public void setUploadFileSource(String uploadFileSource) {
		this.uploadFileSource = uploadFileSource;
	}
	
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		
		build.append(super.toString(prefix));
		build.append(prefix + "FileSourceIdentifier: " + fileSourceIdentifier + "\n"); 
		build.append(prefix + "UploadFileSource    : " + uploadFileSource + "\n"); 
		return build.toString();
	}
	
	
}
