package info.esblurock.reaction.core.server.base.services.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;

import info.esblurock.reaction.chemconnect.core.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.AuthorInformation;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.ContactHasSite;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataSetReference;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatasetCatalogHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.PurposeConceptPair;
import info.esblurock.reaction.chemconnect.core.base.dataset.consortium.Consortium;
import info.esblurock.reaction.chemconnect.core.base.dataset.consortium.ConsortiumMember;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.chemconnect.core.base.transfer.DataElementInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.image.DatasetImage;
import info.esblurock.reaction.chemconnect.core.base.image.ImageInformation;
import info.esblurock.reaction.chemconnect.core.base.image.ConvertInputDataBase;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.base.contact.ContactInfoData;
import info.esblurock.reaction.chemconnect.core.base.contact.ContactLocationInformation;
import info.esblurock.reaction.chemconnect.core.base.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.base.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.contact.Organization;
import info.esblurock.reaction.chemconnect.core.base.contact.OrganizationDescription;
import info.esblurock.reaction.chemconnect.core.base.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccountInformation;
import info.esblurock.reaction.core.server.base.queries.QueryBase;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryFileStaging;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryDataFile;
import info.esblurock.reaction.chemconnect.core.base.gcs.InitialStagingRepositoryFile;

public enum InterpretBaseData {

	DatabaseObject {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			String identifierS = (String) yaml.get(StandardDataKeywords.identifierKeyS);
			String ownerS = (String) yaml.get(StandardDataKeywords.ownerKeyS);
			String accessS = (String) yaml.get(StandardDataKeywords.accessKeyS);
			String sourceIDS = (String) yaml.get(StandardDataKeywords.sourceIDS);
			
			if (top != null) {
				if (identifierS == null) {
					identifierS = top.getIdentifier();
				}
				if (ownerS == null) {
					ownerS = top.getOwner();
				}
				if (accessS == null) {
					accessS = top.getAccess();
				}
			}
			if(sourceID == null) {
				if(sourceIDS == null) {
					sourceID = top.getSourceID();
				} else {
					sourceID = sourceIDS;
				}
			}

			
			DatabaseObject obj = new DatabaseObject(identifierS, ownerS, accessS, sourceID);

			return obj;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {

			Map<String, Object> map = new HashMap<String, Object>();

			map.put(StandardDataKeywords.identifierKeyS, object.getIdentifier());
			map.put(StandardDataKeywords.ownerKeyS, object.getOwner());
			map.put(StandardDataKeywords.accessKeyS, object.getAccess());
			map.put(StandardDataKeywords.sourceIDS, object.getSourceID());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DatabaseObject.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return DatabaseObject.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject objcpy = new DatabaseObject(obj);
			objcpy.nullKey();
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(objcpy);
			return hierarchy;
		}



	}, ChemConnectDataStructure {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top,
				Map<String, Object> yaml, String sourceID) throws IOException {
			ChemConnectDataStructure datastructure = null;
			InterpretBaseData interpret = InterpretBaseData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String descriptionDataDataS = (String) yaml.get(StandardDataKeywords.descriptionDataDataS);			
			String dataSetReferenceS    = (String) yaml.get(StandardDataKeywords.dataSetReferenceS);
			String dataObjectLinkS      = (String) yaml.get(StandardDataKeywords.parameterObjectLinkS);
			String catalogDataIDS      = (String) yaml.get(StandardDataKeywords.DataCatalogIDID);
			String contactHasSiteS      = (String) yaml.get(StandardDataKeywords.ContactHasSiteID);
			
			datastructure = new ChemConnectDataStructure(objdata, 
					descriptionDataDataS, dataSetReferenceS,dataObjectLinkS, 
					catalogDataIDS, contactHasSiteS);
			
			
			return datastructure;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) object;
			InterpretBaseData interpret = InterpretBaseData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDataKeywords.descriptionDataDataS, datastructure.getDescriptionDataData());
			map.put(StandardDataKeywords.dataSetReferenceS, datastructure.getDataSetReference());
			map.put(StandardDataKeywords.parameterObjectLinkS, datastructure.getChemConnectObjectLink());
			map.put(StandardDataKeywords.DataCatalogIDID, datastructure.getCatalogDataID());
			map.put(StandardDataKeywords.ContactHasSiteID, datastructure.getContactHasSite());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ChemConnectDataStructure.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ChemConnectDataStructure.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject compobj = new DatabaseObject(obj);
			compobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.chemConnectDataStructure);
			String compid = InterpretBaseDataUtilities.createSuffix(obj, element);
			compobj.setIdentifier(compid);

			DatabaseObjectHierarchy descrhier = InterpretBaseData.DescriptionDataData.createEmptyObject(obj);
			DescriptionDataData descr = (DescriptionDataData) descrhier.getObject();
			
			DatabaseObjectHierarchy sitehier = InterpretBaseData.ChemConnectCompoundMultiple.createEmptyObject(compobj);
			InterpretBaseDataUtilities.setChemConnectCompoundMultipleType(sitehier,StandardDataKeywords.contactHasSite);
			DatabaseObjectHierarchy refhier = InterpretBaseData.ChemConnectCompoundMultiple.createEmptyObject(compobj);
			InterpretBaseDataUtilities.setChemConnectCompoundMultipleType(refhier,StandardDataKeywords.dataSetReference);
			DatabaseObjectHierarchy lnkhier = InterpretBaseData.ChemConnectCompoundMultiple.createEmptyObject(compobj);
			InterpretBaseDataUtilities.setChemConnectCompoundMultipleType(lnkhier,StandardDataKeywords.dataObjectLink);
			DatabaseObjectHierarchy cathier = InterpretBaseData.DataCatalogID.createEmptyObject(obj);
			ChemConnectDataStructure compound = new ChemConnectDataStructure(compobj, 
					descr.getIdentifier(), 
					refhier.getObject().getIdentifier(),
					lnkhier.getObject().getIdentifier(),
					cathier.getObject().getIdentifier(),
					sitehier.getObject().getIdentifier());

			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(compound);
			hierarchy.addSubobject(descrhier);
			hierarchy.addSubobject(refhier);
			hierarchy.addSubobject(lnkhier);
			hierarchy.addSubobject(cathier);
			hierarchy.addSubobject(sitehier);

			return hierarchy;
		}


	}, ChemConnectCompoundDataStructure {

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ChemConnectCompoundDataStructure datastructure = null;
			InterpretBaseData interpret = InterpretBaseData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);					
			
			String parentCatalogS = (String) yaml.get(StandardDataKeywords.parentCatalogS);
			datastructure = new ChemConnectCompoundDataStructure(objdata, parentCatalogS);
			
			return datastructure;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ChemConnectCompoundDataStructure datastructure = (ChemConnectCompoundDataStructure) object;
			InterpretBaseData interpret = InterpretBaseData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDataKeywords.parentCatalogS, datastructure.getParentLink());
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ChemConnectCompoundDataStructure.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ChemConnectCompoundDataStructure.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject compobj = new DatabaseObject(obj);
			compobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.chemConnectCompoundDataStructure);
			String compid = InterpretBaseDataUtilities.createSuffix(obj, element);
			compobj.setIdentifier(compid);

			ChemConnectCompoundDataStructure compound = new ChemConnectCompoundDataStructure(compobj, "no parent");
			compound.setParentLink(obj.getIdentifier());
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(compound);

			return hierarchy;
		}
	}, DataCatalogID {
		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject refobj = new DatabaseObject(obj);
			refobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.datacatalogid);
			String catid = InterpretBaseDataUtilities.createSuffix(obj, element);
			refobj.setIdentifier(catid);
			
			StringTokenizer tok = new StringTokenizer(obj.getIdentifier(),"-");
			ArrayList<String> names = new ArrayList<String>();
			while(tok.hasMoreElements()) {
				names.add(tok.nextToken());
			}
			String CatalogBaseName = "no catalog base name";
			String DataCatalog = "no catalog";
			String SimpleCatalogName = "no simple name";
			int sze = names.size();
			if(sze >= 3) {
				DataCatalog = names.get(sze-2);
				SimpleCatalogName = names.get(sze-1);
				CatalogBaseName = "";
				for(int i=0;i<sze-3;i++) {
					CatalogBaseName += names.get(i) + "-";
				}
				CatalogBaseName += names.get(sze-3);
			}
			String parentLink = obj.getIdentifier();
			
			ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(refobj,parentLink);
			ArrayList<String> path = new ArrayList<String>();
			DataCatalogID id = new DataCatalogID(structure,
					CatalogBaseName,DataCatalog,SimpleCatalogName,path);
			DatabaseObjectHierarchy refhier = new DatabaseObjectHierarchy(id);
			return refhier;
		}
		
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			DataCatalogID datastructure = null;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);					
			
			String CatalogBaseNameS = (String) yaml.get(StandardDataKeywords.CatalogBaseName);
			String DataCatalogS = (String) yaml.get(StandardDataKeywords.DataCatalog);
			String SimpleCatalogNameS = (String) yaml.get(StandardDataKeywords.SimpleCatalogName);
			ArrayList<String> path = InterpretBaseDataUtilities.interpretMultipleYamlList(StandardDataKeywords.catalogPath,yaml);
			datastructure = new DataCatalogID(objdata, CatalogBaseNameS, DataCatalogS, SimpleCatalogNameS,path);
			return datastructure;
		}
		
		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			DataCatalogID datastructure = (DataCatalogID) object;
			InterpretBaseData interpret = InterpretBaseData.ChemConnectCompoundDataStructure;
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDataKeywords.CatalogBaseName, datastructure.getCatalogBaseName());
			map.put(StandardDataKeywords.DataCatalog, datastructure.getDataCatalog());
			map.put(StandardDataKeywords.SimpleCatalogName, datastructure.getSimpleCatalogName());
			InterpretBaseDataUtilities.putMultipleInYamlList(StandardDataKeywords.catalogPath,map,datastructure.getPath());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DataCatalogID.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return DataCatalogID.class.getCanonicalName();
		}
		
	}, ChemConnectCompoundMultiple {

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ChemConnectCompoundMultiple multi = null;
			InterpretBaseData interpret = InterpretBaseData.DatabaseObject;
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);					
			String typeS = (String) yaml.get(StandardDataKeywords.elementType);
			multi = new ChemConnectCompoundMultiple(objdata, typeS);
			
			return multi;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				DatabaseObject object) throws IOException {
			ChemConnectCompoundMultiple multi = (ChemConnectCompoundMultiple) object;
			InterpretBaseData interpret = InterpretBaseData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);
			map.put(StandardDataKeywords.elementType, multi.getType());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ChemConnectCompoundMultiple.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ChemConnectCompoundMultiple.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject refobj = new DatabaseObject(obj);
			refobj.nullKey();
			String dataType = "noType";
			ChemConnectCompoundMultiple refmult = new ChemConnectCompoundMultiple(refobj,dataType);
			DatabaseObjectHierarchy refhier = new DatabaseObjectHierarchy(refmult);
			return refhier;
		}
		
	}, RepositoryDataFile {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject refobj = new DatabaseObject(obj);
			refobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.repositoryDataFile);
			String catid = InterpretBaseDataUtilities.createSuffix(obj, element);
			refobj.setIdentifier(catid);
			refobj.nullKey();
			
			InterpretBaseData interpret = InterpretBaseData.ChemConnectDataStructure;
			DatabaseObjectHierarchy structurehier = interpret.createEmptyObject(refobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) structurehier.getObject();
			
			RepositoryDataFile repositoryinfo = new RepositoryDataFile(structure);
			repositoryinfo.setIdentifier(refobj.getIdentifier());
			DatabaseObjectHierarchy repositoryinfohier = new DatabaseObjectHierarchy(repositoryinfo);
			repositoryinfohier.transferSubObjects(structurehier);
			
			return repositoryinfohier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			RepositoryDataFile repository = null;
			InterpretBaseData interpret = InterpretBaseData.ChemConnectDataStructure;
			ChemConnectDataStructure structure = (ChemConnectDataStructure) 
					interpret.fillFromYamlString(top, yaml, sourceID);
			repository = new RepositoryDataFile(structure);
			return repository;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InterpretBaseData interpret = InterpretBaseData.ChemConnectDataStructure;
			Map<String, Object> map = interpret.createYamlFromObject(object);
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier)
				throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(RepositoryDataFile.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return RepositoryDataFile.class.getCanonicalName();
		}
		
	}, RepositoryFileStaging {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject refobj = new DatabaseObject(obj);
			refobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.repositoryFileStaging);
			String catid = InterpretBaseDataUtilities.createSuffix(obj, element);
			refobj.setIdentifier(catid);
			
			InterpretBaseData blobinterpret = InterpretBaseData.GCSBlobFileInformation;
			DatabaseObjectHierarchy blobhier = blobinterpret.createEmptyObject(refobj);
			
			InterpretBaseData fileinterpret = InterpretBaseData.InitialStagingRepositoryFile;
			DatabaseObjectHierarchy filehier = fileinterpret.createEmptyObject(refobj);
			
			refobj.nullKey();
			RepositoryFileStaging stageinfo = new RepositoryFileStaging(refobj, 
					blobhier.getObject().getIdentifier(),
					filehier.getObject().getIdentifier(),
					MetaDataKeywords.stagedFileNotProcessed);
			
			DatabaseObjectHierarchy stagehier = new DatabaseObjectHierarchy(stageinfo);
			stagehier.addSubobject(blobhier);
			stagehier.addSubobject(filehier);
			return stagehier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			RepositoryFileStaging image = null;
			InterpretBaseData interpret = InterpretBaseData.DatabaseObject;
			interpret.fillFromYamlString(top, yaml, sourceID);
			String blobFileInformation = (String) yaml.get(StandardDataKeywords.gcsBlobFileInformation);			
			String repositoryFile = (String) yaml.get(StandardDataKeywords.repositoryFile);			
			String stagedFileProcessed = (String) yaml.get(StandardDataKeywords.stagedFileProcessed);			
			image = new RepositoryFileStaging(top,blobFileInformation, repositoryFile,stagedFileProcessed);
			return image;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			RepositoryFileStaging repository = (RepositoryFileStaging) object;
			InterpretBaseData interpret = InterpretBaseData.DatabaseObject;
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDataKeywords.gcsBlobFileInformation, repository.getBlobFileInformation());
			map.put(StandardDataKeywords.repositoryFile, repository.getRepositoryFile());
			map.put(StandardDataKeywords.stagedFileProcessed, repository.getStagingFilePresent());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier)
				throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(RepositoryFileStaging.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return RepositoryFileStaging.class.getCanonicalName();
		}
		
	}, InitialStagingRepositoryFile {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject refobj = new DatabaseObject(obj);
			refobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.initialStagingRepositoryFile);
			String catid = InterpretBaseDataUtilities.createSuffix(obj, element);
			refobj.setIdentifier(catid);
			
			InterpretBaseData interpret = InterpretBaseData.ChemConnectCompoundDataStructure;
			DatabaseObjectHierarchy refhier = interpret.createEmptyObject(refobj);
			ChemConnectCompoundDataStructure structuredata = 
					(ChemConnectCompoundDataStructure) refhier.getObject();
			structuredata.setParentLink(obj.getIdentifier());
			structuredata.nullKey();
			
			String uploadFileIdentifer = "UploadFileIdentifer-ID";
			String uploadFileSource = "UploadFileSource-ID";
			
			refobj.nullKey();
			InitialStagingRepositoryFile stageinfo = new InitialStagingRepositoryFile(structuredata, 
					uploadFileIdentifer,uploadFileSource);
			stageinfo.setIdentifier(refobj.getIdentifier());
			DatabaseObjectHierarchy stagehier = new DatabaseObjectHierarchy(stageinfo);
			return stagehier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			InitialStagingRepositoryFile repinfo = null;
			InterpretBaseData interpret = InterpretBaseData.ChemConnectCompoundDataStructure;
			ChemConnectCompoundDataStructure objdata = 
					(ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);					
			String uploadFileIdentifer = (String) yaml.get(StandardDataKeywords.fileSourceIdentifier);
			String uploadFileSource = (String) yaml.get(StandardDataKeywords.uploadFileSource);
			repinfo = new InitialStagingRepositoryFile(objdata, 
					uploadFileIdentifer, uploadFileSource);
			
			return repinfo;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InitialStagingRepositoryFile repinfo = (InitialStagingRepositoryFile) object;
			InterpretBaseData interpret = InterpretBaseData.ChemConnectCompoundDataStructure;
			Map<String, Object> map = interpret.createYamlFromObject(object);
			map.put(StandardDataKeywords.fileSourceIdentifier, repinfo.getFileSourceIdentifier());
			map.put(StandardDataKeywords.uploadFileSource, repinfo.getUploadFileSource());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier)
				throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(InitialStagingRepositoryFile.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return InitialStagingRepositoryFile.canonicalClassName();
		}
		
	}, GCSBlobFileInformation {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject gcsobj = new DatabaseObject(obj);
			gcsobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.gcsBlobFileInformation);
			String catid = InterpretBaseDataUtilities.createSuffix(obj, element);
			gcsobj.setIdentifier(catid);
			
			
			InterpretBaseData baseinterpret = InterpretBaseData.ChemConnectCompoundDataStructure;
			DatabaseObjectHierarchy basehier = baseinterpret.createEmptyObject(gcsobj);
			ChemConnectCompoundDataStructure refobj = (ChemConnectCompoundDataStructure) basehier.getObject();
			refobj.setParentLink(obj.getIdentifier());
			refobj.nullKey();
			
			System.out.println(refobj.toString("GCSBlobFileInformation refobj: "));
			
			
			String path = obj.getOwner();
			String filename = "filename.txt";
			String filetype = "text";
			String description = "default";
			GCSBlobFileInformation gcsinfo = new GCSBlobFileInformation(refobj, 
					path, filename, filetype,description);
			System.out.println(gcsinfo.toString("GCSBlobFileInformation gcsinfo: "));
			
			gcsinfo.setIdentifier(gcsobj.getIdentifier());
			DatabaseObjectHierarchy refhier = new DatabaseObjectHierarchy(gcsinfo);
			return refhier;
			
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			GCSBlobFileInformation gcsinfo = null;
			InterpretBaseData interpret = InterpretBaseData.ChemConnectCompoundDataStructure;
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);					
			String path = (String) yaml.get(StandardDataKeywords.filepath);
			String filename = (String) yaml.get(StandardDataKeywords.filename);
			String filetype = (String) yaml.get(StandardDataKeywords.fileTypeS);
			String description = (String) yaml.get(StandardDataKeywords.descriptionKeyS);
			gcsinfo = new GCSBlobFileInformation(objdata, 
					path, filename, filetype,description);
			
			return gcsinfo;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			GCSBlobFileInformation gcsinfo = (GCSBlobFileInformation) object;
			InterpretBaseData interpret = InterpretBaseData.ChemConnectCompoundDataStructure;
			Map<String, Object> map = interpret.createYamlFromObject(object);
			map.put(StandardDataKeywords.filepath, gcsinfo.getPath());
			map.put(StandardDataKeywords.filename, gcsinfo.getFilename());
			map.put(StandardDataKeywords.fileTypeS, gcsinfo.getFiletype());
			map.put(StandardDataKeywords.descriptionKeyS, gcsinfo.getDescription());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(GCSBlobFileInformation.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return GCSBlobFileInformation.class.getCanonicalName();
		}
		
	},	ImageInformation {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject imageobj = new DatabaseObject(obj);
			imageobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.imageInformation);
			String catid = InterpretBaseDataUtilities.createSuffix(obj, element);
			imageobj.setIdentifier(catid);

			InterpretBaseData interpret = InterpretBaseData.ChemConnectCompoundDataStructure;
			DatabaseObjectHierarchy structurehierarchy = interpret.createEmptyObject(imageobj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) structurehierarchy.getObject();
			ImageInformation image = new ImageInformation(structure);
			image.setIdentifier(catid);
			
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(image);
			return hierarchy;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ImageInformation image = null;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
			String imageType = (String) yaml.get(StandardDataKeywords.imageType);
			String imageURL = (String) yaml.get(StandardDataKeywords.imageURL);
			image = new ImageInformation(structure,imageType,imageURL);
			return image;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ImageInformation imageinformation = (ImageInformation) object;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDataKeywords.imageType, imageinformation.getImageType());
			map.put(StandardDataKeywords.imageURL, imageinformation.getImageURL());
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ImageInformation.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ImageInformation.class.getCanonicalName();
		}
		
	}, DatasetImage {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject imageobj = new DatabaseObject(obj);
			imageobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(MetaDataKeywords.datasetImage);
			String catid = InterpretBaseDataUtilities.createSuffix(obj, element);
			imageobj.setIdentifier(catid);
			
			InterpretBaseData interpret = InterpretBaseData.ChemConnectDataStructure;
			DatabaseObjectHierarchy structurehierarchy = interpret.createEmptyObject(imageobj);
			
			InterpretBaseData infointerpret = InterpretBaseData.ImageInformation;
			DatabaseObjectHierarchy infohierarchy = infointerpret.createEmptyObject(imageobj);
			
			ChemConnectDataStructure structure = (ChemConnectDataStructure) structurehierarchy.getObject();
			DatasetImage image = new DatasetImage(structure, infohierarchy.getObject().getIdentifier());
			image.setIdentifier(catid);
			
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(image);
			hierarchy.addSubobject(infohierarchy);
			hierarchy.transferSubObjects(structurehierarchy);
			
		return hierarchy;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			DatasetImage image = null;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure structure = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
			String imageInformationID = (String) yaml.get(MetaDataKeywords.imageInformationID);			
			image = new DatasetImage(structure,imageInformationID);
			return image;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			DatasetImage datasetImage = (DatasetImage) object;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);
			map.put(MetaDataKeywords.imageInformationID, datasetImage.getImageInformation());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DatasetImage.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return DatasetImage.class.getCanonicalName();
		}
		
	}, DatasetCatalogHierarchy {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			DatasetCatalogHierarchy datastructure = null;
			InterpretBaseData interpret = InterpretBaseData.ChemConnectDataStructure;
			ChemConnectDataStructure objdata = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);					
			datastructure = new DatasetCatalogHierarchy(objdata);
			
			return datastructure;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				DatabaseObject object) throws IOException {
			InterpretBaseData interpret = InterpretBaseData.ChemConnectDataStructure;
			Map<String, Object> map = interpret.createYamlFromObject(object);
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DatasetCatalogHierarchy.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return DatasetCatalogHierarchy.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject catobj = new DatabaseObject(obj);
			catobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.datasetCatalogHierarchy);
			String catid = InterpretBaseDataUtilities.createSuffix(obj, element);
			catobj.setIdentifier(catid);

			DatabaseObjectHierarchy structhier = InterpretBaseData.ChemConnectDataStructure.createEmptyObject(catobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) structhier.getObject();

			DatasetCatalogHierarchy catalog = new DatasetCatalogHierarchy(structure);
			catalog.setIdentifier(catid);

			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(catalog);
			hierarchy.transferSubObjects(structhier);
			return hierarchy;
		}
		
	}, PurposeConceptPair {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			PurposeConceptPair datastructure = null;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);					
			
			String conceptS = (String) yaml.get(StandardDataKeywords.datacubeConcept);
			String purposeS = (String) yaml.get(StandardDataKeywords.purposeS);
			datastructure = new PurposeConceptPair(objdata, purposeS, conceptS);
			
			return datastructure;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				DatabaseObject object) throws IOException {
			PurposeConceptPair datastructure = (PurposeConceptPair) object;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDataKeywords.datacubeConcept, datastructure.getConcept());
			map.put(StandardDataKeywords.purposeS, datastructure.getPurpose());
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(PurposeConceptPair.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return PurposeConceptPair.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.purposeConceptPair);
			DatabaseObject conceptobj = new DatabaseObject(obj);
			conceptobj.nullKey();
			String conceptid = InterpretBaseDataUtilities.createSuffix(obj, element);
			conceptobj.setIdentifier(conceptid);
			ChemConnectCompoundDataStructure conceptcompound = new ChemConnectCompoundDataStructure(conceptobj,
					obj.getIdentifier());
			PurposeConceptPair pair = new PurposeConceptPair(conceptcompound, 
					StandardDataKeywords.noPurposeS, StandardDataKeywords.noConceptS);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(pair);
			return hierarchy;
		}
		
	}, DescriptionDataData {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			DescriptionDataData descdata = null;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String titleS = (String) yaml.get(StandardDataKeywords.titleKeyS);
			String descriptionS = (String) yaml.get(StandardDataKeywords.descriptionKeyS);
			String purposeS = (String) yaml.get(StandardDataKeywords.parameterPurposeConceptPairS);
			String datatypeS = (String) yaml.get(StandardDataKeywords.dataTypeKeyS);
			String sourceDateS = (String) yaml.get(StandardDataKeywords.sourceDateKeyS);
			String keywordsS = (String) yaml.get(StandardDataKeywords.keywordKeyS);

			Date dateD = null;
			if (sourceDateS != null) {
				dateD = InterpretBaseDataUtilities.parseDate(sourceDateS);
			} else {
				dateD = new Date();
			}

			descdata = new DescriptionDataData(objdata,
					titleS, descriptionS, 
					purposeS, dateD, datatypeS, 
					keywordsS);

			return descdata;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			DescriptionDataData description = (DescriptionDataData) object;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			String sourceDateS = InterpretBaseDataUtilities.dateToString(description.getSourceDate());

			map.put(StandardDataKeywords.titleKeyS, description.getOnlinedescription());
			map.put(StandardDataKeywords.descriptionKeyS, description.getFulldescription());
			map.put(StandardDataKeywords.parameterPurposeConceptPairS, description.getSourceConcept());
			map.put(StandardDataKeywords.dataTypeKeyS, description.getDataType());
			map.put(StandardDataKeywords.sourceDateKeyS, sourceDateS);
			map.put(StandardDataKeywords.keywordKeyS, description.getKeywords());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DescriptionDataData.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return DescriptionDataData.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject descrobj = new DatabaseObject(obj);
			descrobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.descriptionDataData);
			String descrid = InterpretBaseDataUtilities.createSuffix(obj, element);
			descrobj.setIdentifier(descrid);

			DatabaseObjectHierarchy comphier = InterpretBaseData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) comphier.getObject();

			DatabaseObjectHierarchy pairhier = InterpretBaseData.PurposeConceptPair.createEmptyObject(descrobj);
			PurposeConceptPair pair = (PurposeConceptPair) pairhier.getObject();

			DatabaseObjectHierarchy keyshier = InterpretBaseData.ChemConnectCompoundMultiple.createEmptyObject(descrobj);
			InterpretBaseDataUtilities.setChemConnectCompoundMultipleType(keyshier,StandardDataKeywords.keyWord);
			ChemConnectCompoundMultiple keywords = (ChemConnectCompoundMultiple) keyshier.getObject();

			DescriptionDataData descr = new DescriptionDataData(compound, "one line", "full description",
					pair.getIdentifier(), new Date(),StandardDataKeywords.descriptionDataData , keywords.getIdentifier());
			descr.setIdentifier(descrid);
			DatabaseObjectHierarchy descrhier = new DatabaseObjectHierarchy(descr);
			descrhier.addSubobject(pairhier);
			descrhier.addSubobject(keyshier);

			return descrhier;
		}

	}, ContactHasSite {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject rowobj = new DatabaseObject(obj);
			rowobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.contactHasSite);
			String rowid = InterpretBaseDataUtilities.createSuffix(obj, element);
			rowobj.setIdentifier(rowid);
			
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			DatabaseObjectHierarchy compoundhier = interpret.createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) compoundhier.getObject();
			String httpaddress = "https://homepage.com";
			String httpaddressType = "dataset:PersonalHomepage";
			ContactHasSite hassite = new ContactHasSite(structure,httpaddressType,httpaddress);
			hassite.setIdentifier(rowid);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(hassite);
			return hierarchy;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
			String httpaddress = (String) yaml.get(StandardDataKeywords.siteOfS);
			String httpaddressType = (String) yaml.get(StandardDataKeywords.siteTypeS);
			ContactHasSite hassite = new ContactHasSite(compound,httpaddressType, httpaddress);
			return hassite;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ContactHasSite sites = (ContactHasSite) object;

			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDataKeywords.siteOfS, sites.getHttpAddress());
			map.put(StandardDataKeywords.siteTypeS, sites.getHttpAddressType());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ContactHasSite.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ContactHasSite.class.getCanonicalName();
		}
	
	}, ContactInfoData {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			InterpretBaseData interpret = InterpretBaseData.ChemConnectCompoundDataStructure;
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String contactkey = (String) yaml.get(StandardDataKeywords.contactKeyS);
			String contacttype = (String) yaml.get(StandardDataKeywords.contactTypeS);

			ContactInfoData contact = new ContactInfoData(objdata, contacttype, contactkey);
			return contact;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ContactInfoData contact = (ContactInfoData) object;

			InterpretBaseData interpret = InterpretBaseData.ChemConnectCompoundDataStructure;
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDataKeywords.contactKeyS, contact.getContactType());
			map.put(StandardDataKeywords.contactTypeS, contact.getContact());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ContactInfoData.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ContactInfoData.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			DatabaseObject contactobj = new DatabaseObject(obj);
			contactobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.contactInfoData);
			String contactid = InterpretBaseDataUtilities.createSuffix(obj, element);
			contactobj.setIdentifier(contactid);

			DatabaseObjectHierarchy compoundhier = InterpretBaseData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();

			ContactInfoData contact = new ContactInfoData(compound, "contactType","contactKey");
			contact.setIdentifier(contactid);
			DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(contact);
			return top;
		}

	}, ContactLocationInformation {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String streetaddress = (String) yaml.get(StandardDataKeywords.streetaddressKeyS);
			String locality = (String) yaml.get(StandardDataKeywords.localityKeyS);
			String postalcode = (String) yaml.get(StandardDataKeywords.postalcodeKeyS);
			String country = (String) yaml.get(StandardDataKeywords.countryKeyS);
			String gspLocationID = (String) yaml.get(StandardDataKeywords.gpsCoordinatesID);

			ContactLocationInformation location = new ContactLocationInformation(compound, 
					streetaddress, locality, country, postalcode,gspLocationID);
			
			return location;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ContactLocationInformation location = (ContactLocationInformation) object;

			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDataKeywords.streetaddressKeyS, location.getAddressAddress());
			map.put(StandardDataKeywords.localityKeyS, location.getCity());
			map.put(StandardDataKeywords.postalcodeKeyS, location.getPostcode());
			map.put(StandardDataKeywords.countryKeyS, location.getCountry());
			map.put(StandardDataKeywords.gpsCoordinatesID, location.getGpsLocationID());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ContactLocationInformation.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ContactLocationInformation.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			DatabaseObject contactobj = new DatabaseObject(obj);
			contactobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.contactLocationInformation);
			String contactid = InterpretBaseDataUtilities.createSuffix(obj, element);
			contactobj.setIdentifier(contactid);

			DatabaseObjectHierarchy compoundhier = InterpretBaseData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();

			DatabaseObjectHierarchy gpshier = InterpretBaseData.GPSLocation.createEmptyObject(contactobj);
			GPSLocation gps = (GPSLocation) gpshier.getObject();

			ContactLocationInformation location = new ContactLocationInformation(compound, gps.getIdentifier());
			location.setIdentifier(contactid);
			DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(location);
			top.addSubobject(gpshier);

			return top;
		}

	}, GPSLocation {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			InterpretBaseData interpret = InterpretBaseData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);

			String GPSLongitude = (String) yaml.get(StandardDataKeywords.longitudeKeyS);
			String GPSLatitude = (String) yaml.get(StandardDataKeywords.latitudeKeyS);

			GPSLocation location = new GPSLocation(objdata.getIdentifier(), objdata.getAccess(), objdata.getOwner(),
					sourceID, GPSLongitude, GPSLatitude);
			return location;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			GPSLocation location = (GPSLocation) object;

			InterpretBaseData interpret = InterpretBaseData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDataKeywords.longitudeKeyS, location.getGPSLatitude());
			map.put(StandardDataKeywords.latitudeKeyS, location.getGPSLongitude());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(GPSLocation.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return GPSLocation.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			DatabaseObject gpsobj = new DatabaseObject(obj);
			gpsobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.gPSLocation);
			String gpsid = InterpretBaseDataUtilities.createSuffix(obj, element);
			gpsobj.setIdentifier(gpsid);

			GPSLocation gps = new GPSLocation(gpsobj);
			DatabaseObjectHierarchy gpshier = new DatabaseObjectHierarchy(gps);

			return gpshier;
		}

	}, OrganizationDescription {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			OrganizationDescription descdata = null;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
			
			String organizationalUnitS = (String) yaml.get(StandardDataKeywords.organizationUnit);
			String organizationClassificationS = (String) yaml.get(StandardDataKeywords.organizationClassification);
			String organizationNameS = (String) yaml.get(StandardDataKeywords.organizationName);
			String subOrganizationOfS = (String) yaml.get(StandardDataKeywords.subOrganizationOf);

			descdata = new OrganizationDescription(compound,
					organizationalUnitS, organizationClassificationS, organizationNameS, subOrganizationOfS);
			return descdata;
		}

		@Override
		public Map<String, Object> createYamlFromObject(

				DatabaseObject object) throws IOException {

			OrganizationDescription org = (OrganizationDescription) object;

			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDataKeywords.organizationClassification, org.getOrganizationClassification());
			map.put(StandardDataKeywords.organizationName, org.getOrganizationName());
			map.put(StandardDataKeywords.organizationUnit, org.getOrganizationUnit());
			map.put(StandardDataKeywords.subOrganizationOf, org.getSubOrganizationOf());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(OrganizationDescription.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return OrganizationDescription.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			DatabaseObject orgobj = new DatabaseObject(obj);
			orgobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.organizationDescription);
			String orgid = InterpretBaseDataUtilities.createSuffix(obj, element);
			orgobj.setIdentifier(orgid);

			DatabaseObjectHierarchy compoundhier = InterpretBaseData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();

			OrganizationDescription descr = new OrganizationDescription(compound, "organization unit", "organization class",
					"organization", "suborganization");
			descr.setIdentifier(orgid);

			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(descr);

			return hierarchy;
		}

	}, DataSetReference {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String referenceDOIS = (String) yaml.get(StandardDataKeywords.referenceDOI);
			String referenceTitleS = (String) yaml.get(StandardDataKeywords.referenceTitle);
			String referenceBibliographicStringS = (String) yaml
					.get(StandardDataKeywords.referenceBibliographicString);
			String authors = (String) yaml.get(StandardDataKeywords.referenceAuthors);
			DataSetReference refset = new DataSetReference(compound, referenceDOIS, referenceTitleS, referenceBibliographicStringS,
					authors);

			return refset;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			DataSetReference ref = (DataSetReference) object;

			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDataKeywords.referenceDOI, ref.getDOI());
			map.put(StandardDataKeywords.referenceTitle, ref.getTitle());
			map.put(StandardDataKeywords.referenceBibliographicString, ref.getReferenceString());
			map.put(StandardDataKeywords.referenceAuthors,ref.getAuthors());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DataSetReference.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return DataSetReference.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			DatabaseObject refobj = new DatabaseObject(obj);
			refobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.dataSetReference);
			String refid = InterpretBaseDataUtilities.createSuffix(obj, element);
			refobj.setIdentifier(refid);
			
			DatabaseObjectHierarchy compoundhier = InterpretBaseData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();
			
			DatabaseObjectHierarchy authormult = InterpretBaseData.ChemConnectCompoundMultiple.createEmptyObject(refobj);
			InterpretBaseDataUtilities.setChemConnectCompoundMultipleType(authormult,StandardDataKeywords.authorInformation);
			
			DataSetReference reference = new DataSetReference(compound,
					"DOI","Article Title","Reference String",authormult.getObject().getIdentifier());
			reference.setIdentifier(refobj.getIdentifier());
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(reference);
			hierarchy.addSubobject(authormult);
			return hierarchy;
		}

	}, DataObjectLink {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);


			String dataStructureIdentifierS = (String) yaml.get(StandardDataKeywords.dataStructureIdentifierS);
			String linkConceptTypeS = (String) yaml.get(StandardDataKeywords.datacubeConcept);
			DataObjectLink refset = new DataObjectLink(compound, 
					linkConceptTypeS,dataStructureIdentifierS);

			return refset;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			DataObjectLink ref = (DataObjectLink) object;

			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDataKeywords.dataStructureIdentifierS, ref.getDataStructure());
			map.put(StandardDataKeywords.datacubeConcept, ref.getLinkConcept());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(DataObjectLink.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return DataObjectLink.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			DatabaseObject linkobj = new DatabaseObject(obj);
			linkobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.dataObjectLink);
			String linkid = InterpretBaseDataUtilities.createSuffix(obj, element);
			linkobj.setIdentifier(linkid);

			DatabaseObjectHierarchy compoundhier = InterpretBaseData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();

			DataObjectLink userlink = new DataObjectLink(compound, "link concept", "linked object");
			userlink.setIdentifier(linkobj.getIdentifier());
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(userlink);
			return hierarchy;
		}

	}, PersonalDescription {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String userClassification = (String) yaml.get(StandardDataKeywords.userClassification);
			String userNameID = (String) yaml.get(StandardDataKeywords.userNameID);

			PersonalDescription person = new PersonalDescription(compound, userClassification, userNameID);

			return person;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			PersonalDescription person = (PersonalDescription) object;

			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(StandardDataKeywords.userClassification, person.getUserClassification());
			map.put(StandardDataKeywords.userNameID, person.getNameOfPersonIdentifier());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(PersonalDescription.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return PersonalDescription.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			DatabaseObject personobj = new DatabaseObject(obj);
			personobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.personalDescription);
			String personid = InterpretBaseDataUtilities.createSuffix(obj, element);
			personobj.setIdentifier(personid);

			DatabaseObjectHierarchy compoundhier = InterpretBaseData.ChemConnectCompoundDataStructure.createEmptyObject(obj);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) compoundhier.getObject();

			DatabaseObjectHierarchy namehier = InterpretBaseData.NameOfPerson.createEmptyObject(personobj);
			NameOfPerson person = (NameOfPerson) namehier.getObject();

			PersonalDescription description = new PersonalDescription(compound, "user class", person.getIdentifier());
			description.setIdentifier(personid);

			DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(description);
			top.addSubobject(namehier);

			return top;
		}

	}, NameOfPerson {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			NameOfPerson person = null;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String nameTitleS = (String) yaml.get(StandardDataKeywords.titleName);
			String givenNameS = (String) yaml.get(StandardDataKeywords.givenName);
			String familyNameS = (String) yaml.get(StandardDataKeywords.familyName);

			person = new NameOfPerson(structure, nameTitleS, givenNameS, familyNameS);
			return person;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			NameOfPerson name = (NameOfPerson) object;

			map.put(StandardDataKeywords.titleName, name.getTitle());
			map.put(StandardDataKeywords.givenName, name.getGivenName());
			map.put(StandardDataKeywords.familyName, name.getFamilyName());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(NameOfPerson.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return NameOfPerson.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject personobj = new DatabaseObject(obj);
			personobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.nameOfPerson);
			String personid = InterpretBaseDataUtilities.createSuffix(obj, element);
			personobj.setIdentifier(personid);

			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			DatabaseObjectHierarchy structurehier = interpret.createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) structurehier.getObject();
			NameOfPerson person = new NameOfPerson(structure, "title", "firstname", "lastname");
			person.setIdentifier(personid);
			DatabaseObjectHierarchy personhier = new DatabaseObjectHierarchy(person);

			return personhier;
		}

	}, AuthorInformation {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			AuthorInformation author = null;
			InterpretBaseData interpret = InterpretBaseData.NameOfPerson;
			NameOfPerson person = (NameOfPerson) interpret.fillFromYamlString(top, yaml, sourceID);
			String linkS = (String) yaml.get(StandardDataKeywords.linkToContact);
			author = new AuthorInformation(person, linkS);
			return author;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InterpretBaseData interpret = InterpretBaseData.NameOfPerson;
			Map<String, Object> map = interpret.createYamlFromObject(object);
			AuthorInformation author = (AuthorInformation) object;
			map.put(StandardDataKeywords.linkToContact, author.getLinkToContact());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(AuthorInformation.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return AuthorInformation.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject personobj = new DatabaseObject(obj);
			personobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.authorInformation);
			String authorid = InterpretBaseDataUtilities.createSuffix(obj, element);
			personobj.setIdentifier(authorid);

			InterpretBaseData interpret = InterpretBaseData.valueOf("NameOfPerson");
			DatabaseObjectHierarchy personhier = interpret.createEmptyObject(obj);
			NameOfPerson person = (NameOfPerson) personhier.getObject();
			AuthorInformation author = new AuthorInformation(person, "no link");
			person.setIdentifier(authorid);
			DatabaseObjectHierarchy authorhier = new DatabaseObjectHierarchy(author);

			return authorhier;
		}
		
	}, IndividualInformation {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			IndividualInformation org = null;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String contactLocationInformationID = (String) yaml.get(StandardDataKeywords.locationKeyS);
			String personalDescriptionID = (String) yaml.get(StandardDataKeywords.personS);
			String contactinfodataID = (String) yaml.get(StandardDataKeywords.contactInfoData);

			org = new IndividualInformation(datastructure,
					contactLocationInformationID, 
					personalDescriptionID,
					contactinfodataID);
			
			return org;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {

			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			IndividualInformation individual = (IndividualInformation) object;
			map.put(StandardDataKeywords.locationKeyS, individual.getContactLocationInformationID());
			map.put(StandardDataKeywords.personS, individual.getPersonalDescriptionID());
			map.put(StandardDataKeywords.contactInfoData, individual.getContactInfoData());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(IndividualInformation.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return IndividualInformation.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject indobj = new DatabaseObject(obj);
			indobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.individualInformation);
			String indid = InterpretBaseDataUtilities.createSuffix(obj, element);
			indobj.setIdentifier(indid);

			DatabaseObjectHierarchy compoundhier = InterpretBaseData.ChemConnectDataStructure.createEmptyObject(indobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) compoundhier.getObject();

			DatabaseObjectHierarchy contacthier = InterpretBaseData.ChemConnectCompoundMultiple.createEmptyObject(indobj);
			InterpretBaseDataUtilities.setChemConnectCompoundMultipleType(contacthier,StandardDataKeywords.contactInfoData);

			DatabaseObjectHierarchy location = InterpretBaseData.ContactLocationInformation.createEmptyObject(indobj);
			DatabaseObjectHierarchy personalhier = InterpretBaseData.PersonalDescription.createEmptyObject(indobj);
			IndividualInformation info = new IndividualInformation(structure, 
					location.getObject().getIdentifier(), 
					personalhier.getObject().getIdentifier(),
					contacthier.getObject().getIdentifier());
			info.setIdentifier(indid);
			DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(info);
			top.addSubobject(location);
			top.addSubobject(personalhier);
			top.addSubobject(contacthier);
			top.transferSubObjects(compoundhier);

			return top;
		}

	}, Organization {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			Organization org = null;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml,
					sourceID);

			String contactInfoData = (String) 
					yaml.get(StandardDataKeywords.contactInfoData);
			String contactLocationInformationID = (String) 
					yaml.get(StandardDataKeywords.locationKeyS);
			String organizationDescriptionID = (String) 
					yaml.get(StandardDataKeywords.originfoKeyS);
		
			org = new Organization(datastructure,
					contactLocationInformationID, 
					organizationDescriptionID,
					contactInfoData
					);

			return org;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {

			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			Organization org = (Organization) object;
			map.put(StandardDataKeywords.contactInfoData, org.getContactInfoData());
			map.put(StandardDataKeywords.locationKeyS, org.getContactLocationInformationID());
			map.put(StandardDataKeywords.originfoKeyS, org.getOrganizationDescriptionID());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(Organization.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return Organization.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			DatabaseObject compobj = new DatabaseObject(obj);
			compobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.organization);
			String compid = InterpretBaseDataUtilities.createSuffix(obj, element);
			compobj.setIdentifier(compid);
			DatabaseObjectHierarchy location = InterpretBaseData.ContactLocationInformation.createEmptyObject(compobj);
			DatabaseObjectHierarchy orgdescr = InterpretBaseData.OrganizationDescription.createEmptyObject(compobj);
			DatabaseObjectHierarchy contacthier = InterpretBaseData.ChemConnectCompoundMultiple.createEmptyObject(compobj);
			InterpretBaseDataUtilities.setChemConnectCompoundMultipleType(contacthier,StandardDataKeywords.contactInfoData);

			DatabaseObjectHierarchy compoundhier = InterpretBaseData.ChemConnectDataStructure.createEmptyObject(compobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) compoundhier.getObject();
			Organization org = new Organization(structure,
					location.getObject().getIdentifier(), 
					orgdescr.getObject().getIdentifier(),
					contacthier.getObject().getIdentifier());
			org.setIdentifier(compid);
			DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(org);
			top.addSubobject(location);
			top.addSubobject(orgdescr);
			top.addSubobject(contacthier);
			top.transferSubObjects(compoundhier);

			return top;
		}

	}, UserAccountInformation {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			UserAccountInformation account = null;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) objdata;
			String emailS = (String) yaml.get(StandardDataKeywords.emailKeyS);
			String userrole = (String) yaml.get(StandardDataKeywords.userrole);

			account = new UserAccountInformation(compound, 
					emailS, userrole);
			return account;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			UserAccountInformation account = (UserAccountInformation) object;

			map.put(StandardDataKeywords.emailKeyS, account.getEmail());
			map.put(StandardDataKeywords.userrole, account.getUserrole());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			DatabaseObject obj = QueryBase
					.getDatabaseObjectFromIdentifier(UserAccountInformation.class.getCanonicalName(), identifier);
			return obj;
		}

		@Override
		public String canonicalClassName() {
			return UserAccountInformation.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			DatabaseObject userobj = new DatabaseObject(obj);
			userobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.useraccount);
			String userid = InterpretBaseDataUtilities.createSuffix(obj, element);
			userobj.setIdentifier(userid);
			
			DatabaseObjectHierarchy compoundhier = InterpretBaseData.ChemConnectCompoundDataStructure.createEmptyObject(userobj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) compoundhier.getObject();
			UserAccountInformation useraccount = new UserAccountInformation(structure,
					"email@comp.com",UserAccountKeys.accessTypeQuery);
			DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(useraccount);
			return top;
		}

	}, UserAccount {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {
			UserAccount account = null;
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml,
					sourceID);

			String accountUserName = (String) yaml.get(StandardDataKeywords.accountUserName);
			String authorizationName = (String) yaml.get(StandardDataKeywords.authorizationName);
			String authorizationType = (String) yaml.get(StandardDataKeywords.authorizationType);
			String accountPrivilege = (String) yaml.get(StandardDataKeywords.accountPrivilege);

			account = new UserAccount(datastructure,
					accountUserName,authorizationName,authorizationType,accountPrivilege);
			return account;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InterpretBaseData interpret = InterpretBaseData.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			UserAccount account = (UserAccount) object;

			map.put(StandardDataKeywords.accountUserName, account.getAccountUserName());
			map.put(StandardDataKeywords.authorizationName, account.getAuthorizationName());
			map.put(StandardDataKeywords.authorizationType, account.getAuthorizationType());
			map.put(StandardDataKeywords.accountPrivilege, account.getAccountPrivilege());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(UserAccount.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return UserAccount.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			DatabaseObject userobj = new DatabaseObject(obj);
			userobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.useraccount);
			String userid = InterpretBaseDataUtilities.createSuffix(obj, element);
			userobj.setIdentifier(userid);
			
			DatabaseObjectHierarchy compoundhier = InterpretBaseData.ChemConnectDataStructure.createEmptyObject(userobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) compoundhier.getObject();
			UserAccount useraccount = new UserAccount(structure,"Prof:","Phineas","Fogg",UserAccountKeys.accessTypeStandardUser);
			DatabaseObjectHierarchy userhier = new DatabaseObjectHierarchy(useraccount);
			userhier.transferSubObjects(compoundhier);
			return userhier;
		}
	}, Consortium {

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			Consortium consortium = null;
			InterpretBaseData interpret = InterpretBaseData.ChemConnectDataStructure;
			ChemConnectDataStructure datastructure = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml,
					sourceID);
			String consortiumName = (String) yaml.get(StandardDataKeywords.consortiumName);
			String consortiumMemberName = (String) yaml.get(StandardDataKeywords.consortiumMemberName);

			consortium = new Consortium(datastructure, 
					consortiumName, consortiumMemberName);

			return consortium;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			InterpretBaseData interpret = InterpretBaseData.ChemConnectDataStructure;
			Map<String, Object> map = interpret.createYamlFromObject(object);

			Consortium consortium = (Consortium) object;
			map.put(StandardDataKeywords.consortiumName, consortium.getConsortiumName());
			map.put(StandardDataKeywords.consortiumMemberName, consortium.getConsortiumMember());
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(Consortium.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return Consortium.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject consortiumobj = new DatabaseObject(obj);
			consortiumobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.consortium);
			String conid = InterpretBaseDataUtilities.createSuffix(obj, element);
			consortiumobj.setIdentifier(conid);
			
			InterpretBaseData interpret = InterpretBaseData.ChemConnectDataStructure;
			DatabaseObjectHierarchy structurehierarchy = interpret.createEmptyObject(consortiumobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) structurehierarchy.getObject();
			

			DatabaseObjectHierarchy memhier = InterpretBaseData.ChemConnectCompoundMultiple.createEmptyObject(consortiumobj);
			InterpretBaseDataUtilities.setChemConnectCompoundMultipleType(memhier,StandardDataKeywords.consortiumMember);

			Consortium consortium = new Consortium(structure, 
					"ConsortiumName",
					memhier.getObject().getIdentifier());
			consortium.setIdentifier(conid);
			DatabaseObjectHierarchy consorthier = new DatabaseObjectHierarchy(consortium);
			consorthier.transferSubObjects(structurehierarchy);
			consorthier.addSubobject(memhier);
			
			return consorthier;
		}

	}, ConsortiumMember {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject memobj = new DatabaseObject(obj);
			memobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.consortiumMember);
			String memid = InterpretBaseDataUtilities.createSuffix(obj, element);
			memobj.setIdentifier(memid);
			
			DatabaseObjectHierarchy compoundhier = InterpretBaseData.ChemConnectCompoundDataStructure.createEmptyObject(memobj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) compoundhier.getObject();
			ConsortiumMember member = new ConsortiumMember(structure,
					"ConsortiumName", "ConsortiumMemberName");
			member.setIdentifier(memid);
			DatabaseObjectHierarchy top = new DatabaseObjectHierarchy(member);
			return top;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ConsortiumMember member = null;
			InterpretBaseData interpret = InterpretBaseData.ChemConnectCompoundDataStructure;
			ChemConnectCompoundDataStructure datastructure = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml,
					sourceID);

			String consortiumName = (String) yaml.get(StandardDataKeywords.consortiumName);
			String consortiumMemberName = (String) yaml.get(StandardDataKeywords.consortiumMemberName);

			member = new ConsortiumMember(datastructure,
					consortiumName,consortiumMemberName);
			return member;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				info.esblurock.reaction.chemconnect.core.base.DatabaseObject object) throws IOException {
			InterpretBaseData interpret = InterpretBaseData.ChemConnectDataStructure;
			Map<String, Object> map = interpret.createYamlFromObject(object);

			ConsortiumMember member = (ConsortiumMember) object;

			map.put(StandardDataKeywords.consortiumName, member.getConsortiumName());
			map.put(StandardDataKeywords.consortiumMemberName, member.getConsortiumMemberName());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier)
				throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ConsortiumMember.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ConsortiumMember.class.getCanonicalName();
		}
		
	},
	
	ConvertInputDataBase {
		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
				throws IOException {

			InterpretBaseData interpret = InterpretBaseData.valueOf("DatabaseObject");
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);
			
			ConvertInputDataBase convert = null;
			String inputType = (String) yaml.get(StandardDataKeywords.inputTypeS);
			String outputType = (String) yaml.get(StandardDataKeywords.outputTypeS);

			convert = new ConvertInputDataBase(objdata,inputType,outputType);
			return convert;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {

			InterpretBaseData interpret = InterpretBaseData.valueOf("DatabaseObject");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			ConvertInputDataBase base = (ConvertInputDataBase) object;
			map.put(StandardDataKeywords.inputTypeS, base.getInputType());
			map.put(StandardDataKeywords.outputTypeS, base.getOutputType());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ConvertInputDataBase.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ConvertInputDataBase.class.getCanonicalName();
		}

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
	
	public abstract DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj);
	
	public abstract DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
			throws IOException;

	public abstract Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException;

	public abstract DatabaseObject readElementFromDatabase(String identifier) throws IOException;

	public abstract String canonicalClassName();

}
