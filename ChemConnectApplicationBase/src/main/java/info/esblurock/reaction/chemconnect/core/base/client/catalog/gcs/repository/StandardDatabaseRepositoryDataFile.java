package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.repository;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryDataFile;

public class StandardDatabaseRepositoryDataFile extends Composite {

	private static StandardDatabaseRepositoryDataFileUiBinder uiBinder = GWT
			.create(StandardDatabaseRepositoryDataFileUiBinder.class);

	interface StandardDatabaseRepositoryDataFileUiBinder extends UiBinder<Widget, StandardDatabaseRepositoryDataFile> {
	}

	@UiField
	MaterialLink filename;
	@UiField
	MaterialLink save;
	@UiField
	MaterialLink delete;
	
	DatabaseObjectHierarchy hierarchy;		
	RepositoryDataFile repository;
	
	public StandardDatabaseRepositoryDataFile(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		
		hierarchy = item.getHierarchy();
		repository = (RepositoryDataFile) hierarchy.getObject();
		
		DatabaseObjectHierarchy catidhier = hierarchy.getSubObject(repository.getCatalogDataID());
		DataCatalogID catid = (DataCatalogID) catidhier.getObject();
		
		filename.setText(catid.getFullName());
	}

}
