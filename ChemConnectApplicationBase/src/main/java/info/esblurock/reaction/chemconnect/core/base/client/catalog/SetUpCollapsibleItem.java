package info.esblurock.reaction.chemconnect.core.base.client.catalog;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.contact.StandardDatasetContactHasSiteHeader;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.contact.StandardDatasetContactInfoHeader;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.contact.StandardDatasetContactLocationInformationHeader;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.repository.StandardDatabaseRepositoryDataFile;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.repository.StandardDatabaseRepositoryFileStaging;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gps.PrimitiveGPSLocationRow;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.hierarchy.StandardDatasetCatalogHierarchyHeader;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.info.PrimitiveConceptRow;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.info.StandardDatasetDescriptionDataDataHeader;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.multiple.ChemConnectCompoundMultipleHeader;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.organization.StandardDatasetOrganizationDescriptionHeader;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.organization.StandardDatasetOrganizationHeader;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.person.StandardDatabasePersonalDescriptionHeader;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.person.StandardDatabaseUserAccountHeader;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.person.StandardDatasetIndividualInformation;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.person.StandardDatasetNameOfPersonHeader;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.reference.AuthorInformationHeader;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.reference.StandardDatasetDataSetReferenceHeader;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.client.image.DatasetImageHeader;
import info.esblurock.reaction.chemconnect.core.base.client.image.ImageInformationHeader;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;

public enum SetUpCollapsibleItem {

	DatasetCatalogHierarchy {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetCatalogHierarchyHeader header=new StandardDatasetCatalogHierarchyHeader(item);item.addHeader(header);
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			UserImageServiceAsync async=UserImageService.Util.getInstance();
			async.writeDatabaseObjectHierarchy(item.getHierarchy(),
					new AsyncCallback<DatabaseObjectHierarchy>() {

						@Override
						public void onFailure(Throwable caught) {
							StandardWindowVisualization.errorWindowMessage("Update", caught.toString());
						}

						@Override
						public void onSuccess(DatabaseObjectHierarchy hierarchy) {
							item.updateHierarchy(hierarchy);
						}
				
			});
			return true;
		}
	},
	Organization {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetOrganizationHeader header=new StandardDatasetOrganizationHeader(item);item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetOrganizationHeader header=(StandardDatasetOrganizationHeader)item.getHeader();header.updateData();return true;
		}

		@Override
		public int priority() {
			return 610;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

	},
	OrganizationDescription {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetOrganizationDescriptionHeader header=new StandardDatasetOrganizationDescriptionHeader(item);item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetOrganizationDescriptionHeader header=(StandardDatasetOrganizationDescriptionHeader)item.getHeader();header.updateInfo();return false;
		}

		@Override
		public int priority() {
			return 609;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

	}, IndividualInformation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetIndividualInformation header=new StandardDatasetIndividualInformation(item);item.addHeader(header);

		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}

		@Override
		public int priority() {
			return 610;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

	}, UserAccount {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatabaseUserAccountHeader header=new StandardDatabaseUserAccountHeader(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}

		@Override
		public int priority() {
			return 610;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

	}, PersonalDescription {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatabasePersonalDescriptionHeader header=new StandardDatabasePersonalDescriptionHeader(item);item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatabasePersonalDescriptionHeader header=(StandardDatabasePersonalDescriptionHeader)item.getHeader();return header.updateData();
		}

		@Override
		public int priority() {
			return 609;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
	}, RepositoryFileStaging {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatabaseRepositoryFileStaging header = new StandardDatabaseRepositoryFileStaging(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}

		@Override
		public int priority() {
			return 600;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
	}, RepositoryDataFile {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatabaseRepositoryDataFile header = new StandardDatabaseRepositoryDataFile(item);
			item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return false;
		}

		@Override
		public int priority() {
			return 600;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}
		
	},
	
	DatasetImage {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			DatasetImageHeader header=new DatasetImageHeader(item);item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return true;
		}

		@Override
		public int priority() {
			return 0;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

	},
	ImageInformation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			ImageInformationHeader header=new ImageInformationHeader(item);item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			ImageInformationHeader image=(ImageInformationHeader)item.getHeader();image.updateData();return false;
		}

		@Override
		public int priority() {
			return 700;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

	},
	ChemConnectCompoundMultiple {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			ChemConnectCompoundMultipleHeader header=new ChemConnectCompoundMultipleHeader(item);item.addHeader(header);
		}

		@Override
		public int priority() {
			return 1;
		}

		@Override
		public boolean isInformation() {
			return false;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			ChemConnectCompoundMultipleHeader header=(ChemConnectCompoundMultipleHeader)item.getHeader();header.updateObject();return true;
		}

	},
	DataObjectLink {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			PrimitiveDataObjectLinkRow row=new PrimitiveDataObjectLinkRow(item);item.addHeader(row);
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			PrimitiveDataObjectLinkRow header=(PrimitiveDataObjectLinkRow)item.getHeader();return header.updateObject();
		}

	},
	PurposeConceptPair {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			PrimitiveConceptRow concept=new PrimitiveConceptRow(item.getObject());item.addHeader(concept);
		}

		@Override
		public int priority() {
			return 90;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			PrimitiveConceptRow row=(PrimitiveConceptRow)item.getHeader();return row.updateData();
		}

	},
	DataCatalogID {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			DatasetStandardDataCatalogIDHeader header=new DatasetStandardDataCatalogIDHeader(item);item.addHeader(header);

		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			DatasetStandardDataCatalogIDHeader header=(DatasetStandardDataCatalogIDHeader)item.getHeader();header.updateData();return false;
		}

		@Override
		public int priority() {
			return 1000;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

	},
	GPSLocation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			PrimitiveGPSLocationRow row=new PrimitiveGPSLocationRow(item.getObject());item.addHeader(row);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			PrimitiveGPSLocationRow header=(PrimitiveGPSLocationRow)item.getHeader();header.update();return false;
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

	},
	NameOfPerson {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetNameOfPersonHeader header=new StandardDatasetNameOfPersonHeader(item);item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return false;
		}

		@Override
		public int priority() {
			return 400;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

	},
	DescriptionDataData {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			DescriptionDataData description=(DescriptionDataData)item.getObject();String purposeconceptID=description.getSourceConcept();DatabaseObjectHierarchy hierarchy=item.getHierarchy();DatabaseObjectHierarchy phierarchy=hierarchy.getSubObject(purposeconceptID);StandardDatasetObjectHierarchyItem subitem=new StandardDatasetObjectHierarchyItem(item,phierarchy,item.getModalpanel());SetUpCollapsibleItem setup=SetUpCollapsibleItem.valueOf("PurposeConceptPair");setup.addInformation(subitem);PrimitiveConceptRow conceptrow=(PrimitiveConceptRow)subitem.getHeader();StandardDatasetDescriptionDataDataHeader header=new StandardDatasetDescriptionDataDataHeader(description,conceptrow);item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetDescriptionDataDataHeader header=(StandardDatasetDescriptionDataDataHeader)item.getHeader();return header.updateData();
		}

		@Override
		public int priority() {
			return 900;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

	},
	ContactInfoData {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetContactInfoHeader header=new StandardDatasetContactInfoHeader(item);item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetContactInfoHeader header=(StandardDatasetContactInfoHeader)item.getHeader();return header.updateData();
		}

		@Override
		public int priority() {
			return 500;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}
	},
	AuthorInformation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			AuthorInformationHeader header=new AuthorInformationHeader(item);item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return false;
		}

		@Override
		public int priority() {
			return 0;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

	},
	ContactLocationInformation {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetContactLocationInformationHeader header=new StandardDatasetContactLocationInformationHeader(item);item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetContactLocationInformationHeader header=(StandardDatasetContactLocationInformationHeader)item.getHeader();return header.updateData();
		}

		@Override
		public int priority() {
			return 500;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return true;
		}

	},
	ContactHasSite {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetContactHasSiteHeader header=new StandardDatasetContactHasSiteHeader(item);item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			return false;
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

	},
	DataSetReference {

		@Override
		public void addInformation(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetDataSetReferenceHeader header=new StandardDatasetDataSetReferenceHeader(item);item.addHeader(header);
		}

		@Override
		public boolean update(StandardDatasetObjectHierarchyItem item) {
			StandardDatasetDataSetReferenceHeader header=(StandardDatasetDataSetReferenceHeader)item.getHeader();header.updateReference();return true;
		}

		@Override
		public int priority() {
			return 100;
		}

		@Override
		public boolean isInformation() {
			return true;
		}

		@Override
		public boolean addSubitems() {
			return false;
		}

	};

	public abstract void addInformation(StandardDatasetObjectHierarchyItem item);

	public abstract boolean update(StandardDatasetObjectHierarchyItem item);

	public abstract int priority();

	public abstract boolean isInformation();

	public abstract boolean addSubitems();

	public static void addGenericInformation(DatabaseObject object,
			StandardDatasetObjectHierarchyItem item) {
		StandardDatasetGenericHeader header = new StandardDatasetGenericHeader(object.getClass().getSimpleName());
		item.addHeader(header);
		
	}
}
