package info.esblurock.reaction.chemconnect.core.base.activity.read;

import com.googlecode.objectify.annotation.Entity;

import info.esblurock.reaction.chemconnect.core.base.activity.ActivityRepositoryInformation;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;

@SuppressWarnings("serial")
@Entity
public class ActivityRepositoryInitialReadLocalFile extends ActivityRepositoryInitialReadInfo {
	
	public ActivityRepositoryInitialReadLocalFile(ActivityRepositoryInitialReadInfo repositoryinfo,
   			String descriptionTitle,
   			String fileSourceIdentifier) {
		super(repositoryinfo,descriptionTitle,fileSourceIdentifier,MetaDataKeywords.localFile);
		
	}

}
