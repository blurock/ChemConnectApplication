package info.esblurock.reaction.chemconnect.core.base.activity.read;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.activity.ActivityRepositoryInformation;

@SuppressWarnings("serial")
@Entity
public class ActivityRepositoryInitialReadInfo extends ActivityRepositoryInformation {
	
   	@Index
   	String descriptionTitle;
    @Index
    String fileSourceIdentifier;
    @Index
    String fileSourceType;

   	public ActivityRepositoryInitialReadInfo(ActivityRepositoryInformation repositoryinfo) {
   		super(repositoryinfo);
   	}
   	public ActivityRepositoryInitialReadInfo(ActivityRepositoryInformation repositoryinfo,
   			String descriptionTitle,
   			String fileSourceIdentifier,
   			String fileSourceType) {
   		super(repositoryinfo);
   		
   		this.descriptionTitle = descriptionTitle;
   		this.fileSourceIdentifier = fileSourceIdentifier;
   		this.fileSourceType = fileSourceType;
   	}

	public String getDescriptionTitle() {
		return descriptionTitle;
	}
	public void setDescriptionTitle(String descriptionTitle) {
		this.descriptionTitle = descriptionTitle;
	}

	public String getFileSourceIdentifier() {
		return fileSourceIdentifier;
	}
	public void setFileSourceIdentifier(String fileSourceIdentifier) {
		this.fileSourceIdentifier = fileSourceIdentifier;
	}

	public String getFileSourceType() {
		return fileSourceType;
	}

	public void setFileSourceType(String fileSourceType) {
		this.fileSourceType = fileSourceType;
	}

	@Override
	public String toString() {
		return toString("");
	}
	
	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		
		builder.append("ActivityRepositoryInitialReadInfo\n");
		builder.append("Title: " + descriptionTitle + "\n");
		builder.append("SourceType: " + descriptionTitle + " Identifier: " + fileSourceIdentifier + "\n");
		return builder.toString();
	}
   	
}
