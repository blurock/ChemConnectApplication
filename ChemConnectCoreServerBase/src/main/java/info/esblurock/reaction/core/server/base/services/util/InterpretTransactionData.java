package info.esblurock.reaction.core.server.base.services.util;

import java.io.IOException;
import java.util.Map;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.transfer.DataElementInformation;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;
import info.esblurock.reaction.core.server.base.queries.QueryBase;
import info.esblurock.reaction.chemconnect.core.base.activity.ActivityRepositoryInformation;
import info.esblurock.reaction.chemconnect.core.base.activity.ActivityInformationRecord;
import info.esblurock.reaction.chemconnect.core.base.activity.ActivityInformationRecordBase;
import info.esblurock.reaction.chemconnect.core.base.activity.read.ActivityRepositoryInitialReadInfo;
import info.esblurock.reaction.chemconnect.core.base.activity.read.ActivityRepositoryInitialReadLocalFile;
import info.esblurock.reaction.chemconnect.core.base.activity.read.ActivityRepositoryInitialReadStringContent;
import info.esblurock.reaction.chemconnect.core.base.activity.read.ActivityRepositoryInitialReadURL;


public enum InterpretTransactionData  implements InterpretDataInterface  {
	
	ActivityInformationRecord {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject compobj = new DatabaseObject(obj);
			compobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.activityInformationRecord);
			String compid = InterpretBaseDataUtilities.createSuffix(obj, element);
			compobj.setIdentifier(compid);
			
			ActivityInformationRecord activity = new ActivityInformationRecord(compobj);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(activity);
			return hierarchy;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			//ActivityInformationRecord datastructure = (ActivityInformationRecord) object;
			InterpretBaseData interpret = InterpretBaseData.DatabaseObject;
			Map<String, Object> map = interpret.createYamlFromObject(object);

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ActivityInformationRecord.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ActivityInformationRecord.class.getCanonicalName();
		}

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ActivityInformationRecord activity = null;
			InterpretBaseData interpret = InterpretBaseData.DatabaseObject;
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);					
			activity = new ActivityInformationRecord(objdata);
			
			return activity;
		}

		
	}, ActivityInformationRecordBase {
		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject compobj = new DatabaseObject(obj);
			compobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.activityInformationRecordBase);
			String compid = InterpretBaseDataUtilities.createSuffix(obj, element);
			compobj.setIdentifier(compid);
			
			DatabaseObjectHierarchy basehier = InterpretTransactionData.ActivityInformationRecord.createEmptyObject(compobj);
			ActivityInformationRecord base = (ActivityInformationRecord) basehier.getObject();
			ActivityInformationRecordBase activity = new ActivityInformationRecordBase(base);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(activity);
			return hierarchy;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			//ActivityInformationRecord datastructure = (ActivityInformationRecord) object;
			InterpretTransactionData interpret = InterpretTransactionData.ActivityInformationRecord;
			Map<String, Object> map = interpret.createYamlFromObject(object);

			return map;
		}

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ActivityInformationRecord activity = null;
			InterpretTransactionData interpret = InterpretTransactionData.ActivityInformationRecord;
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);					
			activity = new ActivityInformationRecord(objdata);
			
			return activity;
		}
		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ActivityInformationRecordBase.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ActivityInformationRecordBase.class.getCanonicalName();
		}

		
	}, ActivityRepositoryInformation {
		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject compobj = new DatabaseObject(obj);
			compobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.activityRepositoryInformation);
			String compid = InterpretBaseDataUtilities.createSuffix(obj, element);
			compobj.setIdentifier(compid);
			
			DatabaseObjectHierarchy basehier = InterpretTransactionData.ActivityInformationRecordBase.createEmptyObject(compobj);
			ActivityInformationRecordBase base = (ActivityInformationRecordBase) basehier.getObject();
			ActivityRepositoryInformation activity = new ActivityRepositoryInformation(base);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(activity);
			return hierarchy;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			//ActivityInformationRecord datastructure = (ActivityInformationRecord) object;
			InterpretTransactionData interpret = InterpretTransactionData.ActivityRepositoryInformation;
			Map<String, Object> map = interpret.createYamlFromObject(object);

			return map;
		}

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ActivityInformationRecord activity = null;
			InterpretTransactionData interpret = InterpretTransactionData.ActivityRepositoryInformation;
			DatabaseObject objdata = interpret.fillFromYamlString(top, yaml, sourceID);					
			activity = new ActivityInformationRecord(objdata);
			
			return activity;
		}
		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ActivityRepositoryInformation.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ActivityRepositoryInformation.class.getCanonicalName();
		}
		
	}, ActivityRepositoryInitialReadInfo {
		
		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject compobj = new DatabaseObject(obj);
			compobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(StandardDataKeywords.activityRepositoryInitialReadInfo);
			String compid = InterpretBaseDataUtilities.createSuffix(obj, element);
			compobj.setIdentifier(compid);
			
			DatabaseObjectHierarchy basehier = InterpretTransactionData.ActivityRepositoryInformation.createEmptyObject(compobj);
			ActivityRepositoryInformation base = (ActivityRepositoryInformation) basehier.getObject();
			ActivityRepositoryInitialReadInfo activity = new ActivityRepositoryInitialReadInfo(base,
					"Empty read info","","");
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(activity);
			return hierarchy;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ActivityRepositoryInitialReadInfo datastructure = (ActivityRepositoryInitialReadInfo) object;
			InterpretTransactionData interpret = InterpretTransactionData.ActivityRepositoryInformation;
			Map<String, Object> map = interpret.createYamlFromObject(object);
			map.put(StandardDataKeywords.fileSourceIdentifier, datastructure.getFileSourceIdentifier());
			map.put(StandardDataKeywords.fileTypeS, datastructure.getFileSourceType());
			map.put(StandardDataKeywords.titleKeyS, datastructure.getDescriptionTitle());

			return map;
		}

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ActivityInformationRecord activity = null;
			InterpretTransactionData interpret = InterpretTransactionData.ActivityRepositoryInformation;
			ActivityRepositoryInformation objdata = (ActivityRepositoryInformation) 
					interpret.fillFromYamlString(top, yaml, sourceID);					
			String titleS = (String) yaml.get(StandardDataKeywords.descriptionKeyS);
			String identifierS = (String) yaml.get(StandardDataKeywords.fileSourceIdentifier);
			String typeS = (String) yaml.get(StandardDataKeywords.fileTypeS);
			activity = new ActivityRepositoryInitialReadInfo(objdata,titleS,identifierS,typeS);
			
			return activity;
		}
		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ActivityRepositoryInitialReadInfo.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ActivityRepositoryInitialReadInfo.class.getCanonicalName();
		}
		
	};



}
