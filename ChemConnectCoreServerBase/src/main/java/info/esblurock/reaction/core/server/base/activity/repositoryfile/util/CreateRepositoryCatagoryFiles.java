package info.esblurock.reaction.core.server.base.activity.repositoryfile.util;

import java.io.IOException;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.DescriptionDataData;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobContent;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.InitialStagingRepositoryFile;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryDataFile;
import info.esblurock.reaction.chemconnect.core.base.gcs.RepositoryFileStaging;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;
import info.esblurock.reaction.core.server.base.create.CreateContactObjects;
import info.esblurock.reaction.core.server.base.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.base.db.GCSServiceRoutines;
import info.esblurock.reaction.core.server.base.db.WriteBaseCatalogObjects;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseData;

public class CreateRepositoryCatagoryFiles {
	
	public static DatabaseObjectHierarchy createRepositoryFileStaging(DatabaseObject obj,
			String filename, String filecontext,
			String uploadDescriptionText, String sessionID, String path) {
		
		InterpretBaseData interpret = InterpretBaseData.RepositoryFileStaging;
		DatabaseObjectHierarchy hierarchy = interpret.createEmptyObject(obj);
		RepositoryFileStaging repository = (RepositoryFileStaging) hierarchy.getObject();
		
		DatabaseObjectHierarchy staginghier = hierarchy.getSubObject(repository.getRepositoryFile());
		InitialStagingRepositoryFile staging = 
				(InitialStagingRepositoryFile) staginghier.getObject();
		staging.setFileSourceIdentifier(filename);
		staging.setUploadFileSource(filecontext);
		
		DatabaseObjectHierarchy gcsblobhier = hierarchy.getSubObject(repository.getBlobFileInformation());
		GCSBlobFileInformation source = (GCSBlobFileInformation) gcsblobhier.getObject();
		source.fillGCS(path, filename, filecontext, uploadDescriptionText);
		
		return hierarchy;
	}
	
	
	public static DatabaseObjectHierarchy createRepositoryDataFile(DatabaseObjectHierarchy stagehierarchy,
			DataCatalogID catalogid, UserSessionData sessiondata) throws IOException {
		catalogid.setSourceID(stagehierarchy.getObject().getSourceID());		
		RepositoryFileStaging staging = (RepositoryFileStaging) stagehierarchy.getObject();
		DatabaseObjectHierarchy stagegcshierarchy = stagehierarchy.getSubObject(staging.getBlobFileInformation());
		GCSBlobFileInformation stagegcs = (GCSBlobFileInformation) stagegcshierarchy.getObject();
		
		DatabaseObjectHierarchy repositoryhier = createRepositoryDataFile(stagehierarchy,catalogid);
		RepositoryDataFile repository = (RepositoryDataFile) repositoryhier.getObject();
		InterpretBaseData gcsinterpret = InterpretBaseData.GCSBlobFileInformation;
		DatabaseObjectHierarchy gcshierarchy = gcsinterpret.createEmptyObject(repository);
		GCSBlobFileInformation repgcs = (GCSBlobFileInformation) gcshierarchy.getObject();
		repgcs.setFilename(catalogid.getSimpleCatalogName());
		repgcs.setFiletype(stagegcs.getFiletype());
		String reppath = GCSServiceRoutines.createRepositoryPath(catalogid.getFullPath("/"));
		repgcs.setPath(reppath);
		repgcs.setDescription(stagegcs.getDescription());
		
		
		System.out.println("createRepositoryDataFile: repositoryhier\n" 
		+ repositoryhier.toString("UserImageServiceImpl "));
		
		DatabaseWriteBase.writeObjectWithTransaction(repositoryhier, 
				MetaDataKeywords.transferFileIntoCatagoryHierarchy,
				sessiondata);
		staging.setStagingFilePresent(MetaDataKeywords.stagedFileProcessed);
		WriteBaseCatalogObjects.writeDatabaseObjectHierarchyWithTransaction(stagehierarchy, 
				MetaDataKeywords.updateCatalogObjectEvent);
		
		GCSServiceRoutines.moveBlob(repgcs, stagegcs);
		GCSServiceRoutines.deleteBlob(stagegcs);
		
		return repositoryhier;
	}
	public static DatabaseObjectHierarchy createRepositoryDataFile(DatabaseObjectHierarchy stagehierarchy,
			DataCatalogID catalogid) {
		System.out.println("createRepositoryDataFile  DataCatalogID\n" + catalogid);
		InterpretBaseData interpret = InterpretBaseData.RepositoryDataFile;
		DatabaseObject obj = new DatabaseObject(catalogid);
		String id = catalogid.getFullName();
		obj.setIdentifier(id);
		System.out.println("1 -----------------------------------------------------------------------------");
		System.out.println("createRepositoryDataFile: 1\n" + obj.toString("createRepositoryDataFile: "));
		System.out.println("2 -----------------------------------------------------------------------------");
		DatabaseObjectHierarchy repositoryhier = interpret.createEmptyObject(obj);
		System.out.println("3 -----------------------------------------------------------------------------");
		System.out.println("createRepositoryDataFile  DatabaseObjectHierarchy\n" + repositoryhier);
		RepositoryDataFile repository = (RepositoryDataFile) repositoryhier.getObject();
		System.out.println("4 -----------------------------------------------------------------------------");
		String repcatid = repository.getCatalogDataID();
		DatabaseObjectHierarchy repcathier = repositoryhier.getSubObject(repcatid);
		DataCatalogID cat = (DataCatalogID) repcathier.getObject();
		cat.localFill(catalogid);
		System.out.println(cat.toString("createRepositoryDataFile: cat modified: "));
		System.out.println("5 -----------------------------------------------------------------------------");
		
		System.out.println("createRepositoryDataFile: 2");
		RepositoryFileStaging staging = (RepositoryFileStaging) stagehierarchy.getObject();
		System.out.println("6 -----------------------------------------------------------------------------");
		DatabaseObjectHierarchy gcsdescrhier = stagehierarchy.getSubObject(staging.getBlobFileInformation());
		GCSBlobFileInformation gcs = (GCSBlobFileInformation) gcsdescrhier.getObject();
		String text = gcs.getDescription();
		System.out.println("7 -----------------------------------------------------------------------------");
		
		System.out.println("createRepositoryDataFile: 3");
		DatabaseObjectHierarchy repdescrhier = repositoryhier.getSubObject(repository.getDescriptionDataData());
		DescriptionDataData repdescr = (DescriptionDataData) repdescrhier.getObject();
		repdescr.setDescriptionAbstract(text);
		repdescr.setOnlinedescription(cat.getFullName());

		
		return repositoryhier;
	}
	
	public static DatabaseObjectHierarchy createDatasetImage(DatabaseObject obj,DataCatalogID catid,
			String imageType, GCSBlobFileInformation info) throws IOException {
		String path = catid.getFullPath("/");
		String extension = DatasetOntologyParseBase.getFileExtension(imageType);
		String filename = null;
		if(extension != null) {
			filename = catid.getSimpleCatalogName() + "." + extension;
		} else {
			filename = info.getFilename();
		}
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(obj,null);
		GCSBlobFileInformation target = new GCSBlobFileInformation(structure, path,
				filename, info.getFiletype(), info.getDescription());
		info.setSourceID(target.getSourceID());

		GCSBlobContent content = GCSServiceRoutines.moveBlob(target, info);

		DatabaseObjectHierarchy hierarchy = CreateContactObjects.fillDatasetImage(obj, catid, imageType, content.getUrl());
		return hierarchy;
	}



	
}
