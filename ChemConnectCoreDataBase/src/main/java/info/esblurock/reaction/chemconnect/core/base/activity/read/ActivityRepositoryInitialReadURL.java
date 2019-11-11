package info.esblurock.reaction.chemconnect.core.base.activity.read;

import com.googlecode.objectify.annotation.Entity;

import info.esblurock.reaction.chemconnect.core.base.activity.ActivityRepositoryInformation;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;

@SuppressWarnings("serial")
@Entity
public class ActivityRepositoryInitialReadURL extends ActivityRepositoryInitialReadInfo {

	public ActivityRepositoryInitialReadURL(ActivityRepositoryInitialReadInfo repositoryinfo,
   			String descriptionTitle,
   			String fileSourceIdentifier) {
		super(repositoryinfo,descriptionTitle,fileSourceIdentifier,MetaDataKeywords.urlsource);
		
	}

}
