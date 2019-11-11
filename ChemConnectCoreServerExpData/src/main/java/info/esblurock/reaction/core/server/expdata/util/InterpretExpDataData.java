package info.esblurock.reaction.core.server.expdata.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import info.esblurock.reaction.chemconnect.core.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.transfer.DataElementInformation;
import info.esblurock.reaction.core.server.base.authentification.InitializationBase;
import info.esblurock.reaction.core.server.base.queries.QueryBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseDataUtilities;
import info.esblurock.reaction.core.server.base.services.util.InterpretDataBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretDataInterface;
import info.esblurock.reaction.core.server.expdata.metadata.ExpDataStandardDataKeywords;
import info.esblurock.reaction.core.data.expdata.data.metadata.ExpDataMetaDataKeywords;
import info.esblurock.reaction.core.data.expdata.data.observations.ObservationBlockFromSpreadSheet;
import info.esblurock.reaction.core.data.expdata.data.observations.ObservationMatrixValues;
import info.esblurock.reaction.core.data.expdata.data.observations.ObservationValueRow;
import info.esblurock.reaction.core.data.expdata.data.observations.ObservationValueRowTitle;
import info.esblurock.reaction.core.data.expdata.data.observations.ObservationsFromSpreadSheet;
import info.esblurock.reaction.core.data.expdata.data.observations.ObservationsFromSpreadSheetFull;
import info.esblurock.reaction.core.data.expdata.data.observations.SingleObservationDataset;
import info.esblurock.reaction.core.data.expdata.data.observations.SpreadSheetBlockIsolation;
import info.esblurock.reaction.core.data.expdata.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.core.data.expdata.data.observations.ValueParameterComponents;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;


public enum InterpretExpDataData implements InterpretDataInterface {
	
	ObservationBlockFromSpreadSheet {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			System.out.println("ObservationBlockFromSpreadSheet: " + obj);
			DatabaseObject matobj = new DatabaseObject(obj);
			matobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(ExpDataStandardDataKeywords.observationBlockFromSpreadSheet);
			String matid = InterpretBaseDataUtilities.createSuffix(obj, element);
			matobj.setIdentifier(matid);
			
			DatabaseObjectHierarchy interpretpechier = InterpretExpDataData.SpreadSheetBlockIsolation.createEmptyObject(matobj);
			InterpretDataBase interpretbase = InitializationBase.getInterpret();
			DatabaseObjectHierarchy comphier = interpretbase.valueOf("ChemConnectDataStructure").createEmptyObject(matobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) comphier.getObject();
			ObservationBlockFromSpreadSheet obsmat = new ObservationBlockFromSpreadSheet(structure,
					interpretpechier.getObject().getIdentifier());
			obsmat.setIdentifier(matid);
			DatabaseObjectHierarchy hier = new DatabaseObjectHierarchy(obsmat);
			hier.addSubobject(interpretpechier);
			hier.transferSubObjects(comphier);
			
			return hier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ChemConnectDataStructure structure = (ChemConnectDataStructure) 
					InitializationBase.valueOf("ChemConnectDataStructure").fillFromYamlString(top, yaml, sourceID);
			String interpret = (String) yaml.get(ExpDataStandardDataKeywords.spreadSheetBlockIsolationID);
			ObservationBlockFromSpreadSheet obs = new ObservationBlockFromSpreadSheet(structure,interpret);
			return obs;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ObservationBlockFromSpreadSheet values = (ObservationBlockFromSpreadSheet) object;

			InterpretDataInterface interpret = InitializationBase.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(ExpDataStandardDataKeywords.spreadSheetBlockIsolationID, values.getSpreadBlockIsolation());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ObservationBlockFromSpreadSheet.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ObservationBlockFromSpreadSheet.class.getCanonicalName();
		}
		
	}, SpreadSheetBlockIsolation {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject spreadobj = new DatabaseObject(obj);
			spreadobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(ExpDataStandardDataKeywords.spreadSheetBlockIsolation);
			String catid = InterpretBaseDataUtilities.createSuffix(obj, element);
			spreadobj.setIdentifier(catid);
			
			DatabaseObjectHierarchy obspechier = InitializationBase.interpretDataBase.valueOf("ChemConnectCompoundDataStructure").createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) obspechier.getObject();

			String startRowType = ExpDataMetaDataKeywords.beginMatrixTopOfSpreadSheet;
			String endRowType = ExpDataMetaDataKeywords.matrixBlockEndAtFileEndOrBlankLine;
			String startColumnType = ExpDataMetaDataKeywords.matrixBlockColumnBeginLeft;
			String endColumnType = ExpDataMetaDataKeywords.matrixBlockColumnEndMaximum;
			String spreadSheetType = ExpDataMetaDataKeywords.chemConnectIsolateBlockEntireMatrix;
			String includeTitle = ExpDataMetaDataKeywords.matrixBlockTitleFirstLine;
			SpreadSheetBlockIsolation interpret = new SpreadSheetBlockIsolation(structure,
					spreadSheetType,
					startRowType,endRowType,startColumnType,endColumnType,
					includeTitle);
			interpret.setIdentifier(catid);
			DatabaseObjectHierarchy interprethier = new DatabaseObjectHierarchy(interpret);
			
			return interprethier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			SpreadSheetBlockIsolation set = null;
			InterpretDataInterface interpret = InitializationBase.interpretDataBase.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) 
					interpret.fillFromYamlString(top, yaml, sourceID);
			
			String beginRow = (String) yaml.get(ExpDataStandardDataKeywords.startRowInMatrix);			
			String endRow = (String) yaml.get(ExpDataStandardDataKeywords.lastRowInMatrix);			
			String beginColumn = (String) yaml.get(ExpDataStandardDataKeywords.startColumnInMatrix);			
			String endColumn = (String) yaml.get(ExpDataStandardDataKeywords.lastColumnInMatrix);			
			String spreadSheetType = (String) yaml.get(ExpDataStandardDataKeywords.spreadSheetBlockIsolationType);			
			String includeTitle = (String) yaml.get(ExpDataStandardDataKeywords.includeBlockTitle);			
			set = new SpreadSheetBlockIsolation(objdata, 
					beginRow, endRow, beginColumn, endColumn,
					spreadSheetType, includeTitle);
			return set;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			SpreadSheetBlockIsolation datastructure = (SpreadSheetBlockIsolation) object;
			InterpretDataInterface interpret = InitializationBase.interpretDataBase.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(ExpDataStandardDataKeywords.startRowInMatrix,String.valueOf(datastructure.getStartRowType()));
			map.put(ExpDataStandardDataKeywords.lastRowInMatrix, String.valueOf(datastructure.getEndRowType()));
			map.put(ExpDataStandardDataKeywords.startColumnInMatrix, String.valueOf(datastructure.getStartColumnType()));
			map.put(ExpDataStandardDataKeywords.lastColumnInMatrix, String.valueOf(datastructure.getEndColumnType()));
			map.put(ExpDataStandardDataKeywords.spreadSheetBlockIsolationType, String.valueOf(datastructure.getSpreadSheetBlockIsolationType()));
			map.put(ExpDataStandardDataKeywords.includeBlockTitle, String.valueOf(datastructure.getTitleIncluded()));
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(SpreadSheetBlockIsolation.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return SpreadSheetBlockIsolation.class.getCanonicalName();
		}
		
	}, ObservationsFromSpreadSheetFull {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			DatabaseObject spreadobj = new DatabaseObject(obj);
			spreadobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(MetaDataKeywords.observationsFromSpreadSheetFull);
			String catid = InterpretBaseDataUtilities.createSuffix(obj, element);
			spreadobj.setIdentifier(catid);
			
			DatabaseObjectHierarchy inputhier = InterpretExpDataData.SpreadSheetInputInformation.createEmptyObject(spreadobj);
			DatabaseObjectHierarchy matrixhier = InterpretExpDataData.ObservationMatrixValues.createEmptyObject(spreadobj);
			
			DatabaseObjectHierarchy structurehier = InitializationBase.interpretDataBase.valueOf("ChemConnectDataStructure")
					.createEmptyObject(spreadobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) structurehier.getObject();
			ObservationsFromSpreadSheetFull set = new ObservationsFromSpreadSheetFull(structure,
					matrixhier.getObject().getIdentifier(),
					inputhier.getObject().getIdentifier());
			set.setIdentifier(catid);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(set);
			hierarchy.addSubobject(inputhier);
			hierarchy.addSubobject(matrixhier);
			hierarchy.transferSubObjects(structurehier);
			return hierarchy;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ObservationsFromSpreadSheetFull set = null;
			InterpretDataInterface interpret = InitializationBase.interpretDataBase.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure objdata = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
					
			String observationMatrixValuesID = (String) yaml.get(ExpDataStandardDataKeywords.observationMatrixValuesID);			
			String spreadSheetInformationID = (String) yaml.get(ExpDataStandardDataKeywords.spreadSheetInformationID);			

			set = new ObservationsFromSpreadSheetFull(objdata, 
					observationMatrixValuesID, 
					spreadSheetInformationID);
			return set;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ObservationsFromSpreadSheetFull datastructure = (ObservationsFromSpreadSheetFull) object;
			InterpretDataInterface interpret = InitializationBase.interpretDataBase.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);
			map.put(ExpDataStandardDataKeywords.observationMatrixValuesID, datastructure.getObservationMatrixValues());
			map.put(ExpDataStandardDataKeywords.spreadSheetInformationID, datastructure.getSpreadSheetInputInformation());
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ObservationsFromSpreadSheetFull.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ObservationsFromSpreadSheetFull.class.getCanonicalName();
		}
	},
	ObservationsFromSpreadSheet {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			DatabaseObject spreadobj = new DatabaseObject(obj);
			spreadobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(MetaDataKeywords.observationsFromSpreadSheet);
			String catid = InterpretBaseDataUtilities.createSuffix(obj, element);
			spreadobj.setIdentifier(catid);
			
			DatabaseObjectHierarchy titleshier = InterpretExpDataData.ObservationValueRowTitle.createEmptyObject(spreadobj);
			
			DatabaseObjectHierarchy structurehier = InterpretExpDataData.ObservationsFromSpreadSheetFull.createEmptyObject(spreadobj);
			ObservationsFromSpreadSheetFull structure = (ObservationsFromSpreadSheetFull) structurehier.getObject();
			ObservationsFromSpreadSheet set = new ObservationsFromSpreadSheet(structure,
					titleshier.getObject().getIdentifier());
			set.setIdentifier(catid);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(set);
			hierarchy.addSubobject(titleshier);
			hierarchy.transferSubObjects(structurehier);
			return hierarchy;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ObservationsFromSpreadSheet set = null;
			InterpretDataInterface interpret = InitializationBase.interpretDataBase.valueOf("ObservationsFromSpreadSheetFull");
			ObservationsFromSpreadSheetFull objdata = (ObservationsFromSpreadSheetFull) interpret.fillFromYamlString(top, yaml, sourceID);
					
			String observationValueRowTitleID = (String) yaml.get(ExpDataStandardDataKeywords.observationValueRowTitleID);			

			set = new ObservationsFromSpreadSheet(objdata, 
					observationValueRowTitleID);
			return set;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ObservationsFromSpreadSheet datastructure = (ObservationsFromSpreadSheet) object;
			InterpretDataInterface interpret = InitializationBase.interpretDataBase.valueOf("ObservationsFromSpreadSheetFull");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(ExpDataStandardDataKeywords.observationValueRowTitleID, datastructure.getObservationValueRowTitle());
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ObservationsFromSpreadSheet.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return ObservationsFromSpreadSheet.class.getCanonicalName();
		}
		
	},  SpreadSheetInputInformation {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject spreadobj = new DatabaseObject(obj);
			spreadobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(ExpDataStandardDataKeywords.spreadSheetInformation);
			String catid = InterpretBaseDataUtilities.createSuffix(obj, element);
			spreadobj.setIdentifier(catid);
			
			DatabaseObjectHierarchy obspechier = InitializationBase.interpretDataBase.valueOf("ChemConnectCompoundDataStructure").createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) obspechier.getObject();
			String type = "dataset:CSV";
			String sourceType = "dataset:StringSource";
			String source = "0,0,0,0,0\n0,0,0,0,0\n";
			SpreadSheetInputInformation input = new SpreadSheetInputInformation(structure,type,sourceType,source);
			input.setIdentifier(catid);
			DatabaseObjectHierarchy inputhier = new DatabaseObjectHierarchy(input);
			return inputhier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			SpreadSheetInputInformation set = null;
			InterpretDataInterface interpret = InitializationBase.interpretDataBase.valueOf("ChemConnectDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
					
			String type = (String) yaml.get(ExpDataStandardDataKeywords.spreadSheetSourceType);			
			String source    = (String) yaml.get(ExpDataStandardDataKeywords.fileSourceIdentifier);
			String sourceType    = (String) yaml.get(ExpDataStandardDataKeywords.fileSourceType);

			set = new SpreadSheetInputInformation(objdata, type, sourceType, source);
			return set;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				DatabaseObject object) throws IOException {
			SpreadSheetInputInformation datastructure = (SpreadSheetInputInformation) object;
			InterpretDataInterface interpret = InitializationBase.interpretDataBase.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(ExpDataStandardDataKeywords.spreadSheetSourceType, datastructure.getType());
			map.put(ExpDataStandardDataKeywords.fileSourceIdentifier, datastructure.getSourceID());
			map.put(ExpDataStandardDataKeywords.fileSourceType, datastructure.getSourceType());
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(SpreadSheetInputInformation.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return SpreadSheetInputInformation.class.getCanonicalName();
		}
		
	}, ObservationMatrixValues {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			DatabaseObject matobj = new DatabaseObject(obj);
			matobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(ExpDataStandardDataKeywords.observationMatrixValues);
			String matid = InterpretBaseDataUtilities.createSuffix(obj, element);
			matobj.setIdentifier(matid);
			
			DatabaseObjectHierarchy comphier =  
					InitializationBase.interpretDataBase.valueOf("ChemConnectCompoundDataStructure").createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) comphier.getObject();
			
			DatabaseObjectHierarchy matspechier = InitializationBase.interpretDataBase.valueOf("ChemConnectCompoundMultiple").createEmptyObject(matobj);
			InterpretBaseDataUtilities.setChemConnectCompoundMultipleType(matspechier,ExpDataStandardDataKeywords.observationValueRow);
			
			ObservationMatrixValues obsmat = new ObservationMatrixValues(structure,
					matspechier.getObject().getIdentifier(),"0");
			obsmat.setIdentifier(matid);
			DatabaseObjectHierarchy hier = new DatabaseObjectHierarchy(obsmat);
			hier.addSubobject(matspechier);
			hier.transferSubObjects(comphier);
			
			return hier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) 
					InitializationBase.interpretDataBase.valueOf("ChemConnectCompoundDataStructure").fillFromYamlString(top, yaml, sourceID);
			String values = (String) yaml.get(ExpDataStandardDataKeywords.observationValueRowID);
			String numberOfColumns = (String) yaml.get(ExpDataStandardDataKeywords.numberOfColumns);
			ObservationMatrixValues corr = new ObservationMatrixValues(structure,values,numberOfColumns);
			return corr;
		}

		@Override
		public Map<String, Object> createYamlFromObject(
				DatabaseObject object) throws IOException {
			ObservationMatrixValues values = (ObservationMatrixValues) object;

			InterpretDataInterface interpret = InitializationBase.interpretDataBase.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(ExpDataStandardDataKeywords.observationValueRowID, values.getObservationRowValue());
			map.put(ExpDataStandardDataKeywords.numberOfColumns, values.getNumberOfColumns());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(
				String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ObservationMatrixValues.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ObservationMatrixValues.class.getCanonicalName();
		}
		
	}, ObservationValueRowTitle {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(
				DatabaseObject obj) {
			DatabaseObject rowobj = new DatabaseObject(obj);
			rowobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(ExpDataStandardDataKeywords.observationValueRowTitle);
			String rowid = InterpretBaseDataUtilities.createSuffix(obj, element);
			rowobj.setIdentifier(rowid);
			
			DatabaseObjectHierarchy compoundhier = InitializationBase.interpretDataBase.valueOf("ChemConnectCompoundDataStructure").createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) compoundhier.getObject();
			ObservationValueRowTitle titlerow = new ObservationValueRowTitle(structure,new ArrayList<String>());
			titlerow.setIdentifier(rowid);
			DatabaseObjectHierarchy hier = new DatabaseObjectHierarchy(titlerow);
			return hier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			InterpretDataInterface interpret = InitializationBase.interpretDataBase.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
			ArrayList<String> titles = InterpretBaseDataUtilities.interpretMultipleYamlList(ExpDataStandardDataKeywords.listOfTitles,yaml);
			ObservationValueRowTitle row = new ObservationValueRowTitle(objdata, titles);
			return row;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ObservationValueRowTitle rowtitles = (ObservationValueRowTitle) object;

			InterpretDataInterface interpret = InitializationBase.interpretDataBase.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			InterpretBaseDataUtilities.putMultipleInYamlList(ExpDataStandardDataKeywords.listOfTitles,map,rowtitles.getParameterLabel());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ObservationValueRowTitle.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ObservationValueRowTitle.class.getCanonicalName();
		}
		
	}, ObservationValueRow {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject rowobj = new DatabaseObject(obj);
			rowobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(ExpDataStandardDataKeywords.observationValueRow);
			String rowid = InterpretBaseDataUtilities.createSuffix(obj, element);
			rowobj.setIdentifier(rowid);
			
			DatabaseObjectHierarchy compoundhier = InitializationBase.valueOf("ChemConnectCompoundDataStructure").createEmptyObject(obj);
			ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) compoundhier.getObject();
			ObservationValueRow valuerow = new ObservationValueRow(structure,0,new ArrayList<String>());
			DatabaseObjectHierarchy hier = new DatabaseObjectHierarchy(valuerow);
			
			return hier;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			InterpretDataInterface interpret = InitializationBase.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure objdata = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);

			String position = (String) yaml.get(ExpDataStandardDataKeywords.position);
			int pos = Integer.valueOf(position).intValue();
			ArrayList<String> values = InterpretBaseDataUtilities.interpretMultipleYamlList(ExpDataStandardDataKeywords.listOfValuesAsString,yaml);
			
			ObservationValueRow row = new ObservationValueRow(objdata, pos,values);
			return row;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ObservationValueRow row = (ObservationValueRow) object;

			InterpretDataInterface interpret = InitializationBase.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			int pos = row.getRowNumber();
			map.put(ExpDataStandardDataKeywords.position, String.valueOf(pos));
			InterpretBaseDataUtilities.putMultipleInYamlList(ExpDataStandardDataKeywords.listOfValuesAsString,map,row.getRow());

			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ObservationValueRow.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ObservationValueRow.class.getCanonicalName();
		}
	}, SingleObservationDataset {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
			DatabaseObject obsobj = new DatabaseObject(obj);
			obsobj.nullKey();
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(ExpDataStandardDataKeywords.singleObservationDataset);
			String obsid = InterpretBaseDataUtilities.createSuffix(obj, element);
			obsobj.setIdentifier(obsid);

			SingleObservationDataset single = null;
			DatabaseObjectHierarchy comphier = InitializationBase.valueOf("ChemConnectCompoundMultiple").createEmptyObject(obsobj);
			InterpretBaseDataUtilities.setChemConnectCompoundMultipleType(comphier,ExpDataStandardDataKeywords.valueParameterComponents);
			DatabaseObjectHierarchy valueshier = InitializationBase.valueOf("ChemConnectCompoundMultiple").createEmptyObject(obsobj);
			InterpretBaseDataUtilities.setChemConnectCompoundMultipleType(valueshier,ExpDataStandardDataKeywords.observationValueRow);
			DatabaseObjectHierarchy structurehier = InitializationBase.valueOf("ChemConnectDataStructure").createEmptyObject(obsobj);
			ChemConnectDataStructure structure = (ChemConnectDataStructure) structurehier.getObject();
			
			single = new SingleObservationDataset(structure,
					comphier.getObject().getIdentifier(),
					valueshier.getObject().getIdentifier()
					);
			DatabaseObjectHierarchy hierarchy = new DatabaseObjectHierarchy(single);
			hierarchy.addSubobject(comphier);
			hierarchy.addSubobject(valueshier);
			hierarchy.transferSubObjects(structurehier);
			return hierarchy;
		}

		@Override
		public DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			SingleObservationDataset set = null;
			InterpretDataInterface interpret = InitializationBase.valueOf("ChemConnectDataStructure");
			ChemConnectDataStructure objdata = (ChemConnectDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
					
			String valueParameterComponentsID = (String) yaml.get(ExpDataStandardDataKeywords.valueParameterComponentsID);			
			String observationValueRowID = (String) yaml.get(ExpDataStandardDataKeywords.observationValueRowID);			

			set = new SingleObservationDataset(objdata, valueParameterComponentsID,observationValueRowID);
			return set;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			SingleObservationDataset datastructure = (SingleObservationDataset) object;
			InterpretDataInterface interpret = InitializationBase.valueOf("ChemConnectDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			map.put(ExpDataStandardDataKeywords.valueParameterComponentsID, datastructure.getParameterValueComponents());
			map.put(ExpDataStandardDataKeywords.observationValueRowID, datastructure.getObservationValueRows());
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(SingleObservationDataset.class.getCanonicalName(),
					identifier);
		}

		@Override
		public String canonicalClassName() {
			return SingleObservationDataset.class.getCanonicalName();
		}
		
	}, ValueParameterComponents {

		@Override
		public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {
				DatabaseObject valcomp = new DatabaseObject(obj);
				valcomp.nullKey();
				DataElementInformation element = DatasetOntologyParseBase
						.getSubElementStructureFromIDObject(ExpDataStandardDataKeywords.valueParameterComponents);
				String valid = InterpretBaseDataUtilities.createSuffix(obj, element);
				valcomp.setIdentifier(valid);
				String parameterLabel = "label";
				String unitOfValue = "unit";
				boolean isUncertaintyValue = false;
				DatabaseObjectHierarchy comphier =  
						InitializationBase.valueOf("ChemConnectCompoundDataStructure").createEmptyObject(obj);
				ChemConnectCompoundDataStructure structure = (ChemConnectCompoundDataStructure) comphier.getObject();
				ValueParameterComponents components = new ValueParameterComponents(structure,
						0,parameterLabel, unitOfValue, isUncertaintyValue);
				components.setIdentifier(valid);
				DatabaseObjectHierarchy hier = new DatabaseObjectHierarchy(components);
				hier.transferSubObjects(comphier);				
				return hier;
		}

		@Override
		public DatabaseObject fillFromYamlString(
				DatabaseObject top, Map<String, Object> yaml,
				String sourceID) throws IOException {
			InterpretDataInterface interpret = InitializationBase.valueOf("ChemConnectCompoundDataStructure");
			ChemConnectCompoundDataStructure compound = (ChemConnectCompoundDataStructure) interpret.fillFromYamlString(top, yaml, sourceID);
			String positionS = (String) yaml.get(ExpDataStandardDataKeywords.parameterposition);
			int position = Integer.valueOf(positionS).intValue();
			String parameterLabel = (String) yaml.get(ExpDataStandardDataKeywords.parameterLabelS);
			String unitsOfValue = (String) yaml.get(ExpDataStandardDataKeywords.unitsOfValueS);
			String isUncertainty = (String) yaml.get(ExpDataStandardDataKeywords.includesUncertaintyParameter);
			boolean isUncertaintyB = Boolean.valueOf(isUncertainty);
			ValueParameterComponents components = new ValueParameterComponents(compound,position, parameterLabel, unitsOfValue,isUncertaintyB);
			return components;
		}

		@Override
		public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {
			ValueParameterComponents components = (ValueParameterComponents) object;

			InterpretDataInterface interpret = InitializationBase.valueOf("ChemConnectCompoundDataStructure");
			Map<String, Object> map = interpret.createYamlFromObject(object);

			String positionS = String.valueOf(components.getPosition());
			map.put(ExpDataStandardDataKeywords.parameterposition, positionS);
			map.put(ExpDataStandardDataKeywords.parameterLabelS, components.getParameterLabel());
			map.put(ExpDataStandardDataKeywords.unitsOfValueS, components.getUnitsOfValue());
			boolean isUncertainty = components.isUncertaintyValue();
			map.put(ExpDataStandardDataKeywords.includesUncertaintyParameter, String.valueOf(isUncertainty));
			
			return map;
		}

		@Override
		public DatabaseObject readElementFromDatabase(String identifier) throws IOException {
			return QueryBase.getDatabaseObjectFromIdentifier(ValueParameterComponents.class.getCanonicalName(), identifier);
		}

		@Override
		public String canonicalClassName() {
			return ValueParameterComponents.class.getCanonicalName();
		}
		
	};
	
	
	
	
	public abstract DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj);
	
	public abstract DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
			throws IOException;

	public abstract Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException;

	public abstract DatabaseObject readElementFromDatabase(String identifier) throws IOException;

	public abstract String canonicalClassName();

}
