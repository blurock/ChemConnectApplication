package info.esblurock.reaction.core.server.expdata.observation.spreadsheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationCorrespondenceSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ObservationSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ParameterSpecification;
import info.esblurock.reaction.chemconnect.core.data.dataset.ValueUnits;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondence;
import info.esblurock.reaction.chemconnect.core.data.observations.matrix.MatrixSpecificationCorrespondenceSet;
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
import info.esblurock.reaction.core.server.base.create.CreateContactObjects;
import info.esblurock.reaction.core.server.base.db.ExtractCatalogInformation;
import info.esblurock.reaction.core.server.base.queries.QueryBase;
import info.esblurock.reaction.core.server.expdata.util.InterpretExpDataData;

public class CreateSpreadSheetObjects {
	public DatabaseObjectHierarchy createObservationBlockFromSpreadSheet(DatabaseObject obj, 
			String blocktype, DataCatalogID datid) {
		DatabaseObjectHierarchy hierarchy = fillObservationBlockFromSpreadSheet(obj,
				blocktype,datid);
		return hierarchy;
	}

	public static DatabaseObjectHierarchy fillObservationBlockFromSpreadSheet(DatabaseObject obj, 
			String blocktype, DataCatalogID datid) {
		String sourceID = QueryBase.getDataSourceIdentification(obj.getOwner());
		obj.setSourceID(sourceID);
		obj.nullKey();
		
		DatabaseObjectHierarchy hierarchy = InterpretExpDataData.ObservationBlockFromSpreadSheet.createEmptyObject(obj);
		ObservationBlockFromSpreadSheet obsblock = (ObservationBlockFromSpreadSheet) hierarchy.getObject();
		
		replaceDataCatalogID(hierarchy,obsblock.getCatalogDataID(),datid);
		
		return hierarchy;
	}
	
	private static void replaceDataCatalogID(DatabaseObjectHierarchy hierarchy, String identifier, DataCatalogID catid) {
		DatabaseObjectHierarchy cathierarchy = hierarchy.getSubObject(identifier);
		DataCatalogID cat = (DataCatalogID) cathierarchy.getObject();
		cat.setDataCatalog(catid.getDataCatalog());
		cat.setSimpleCatalogName(catid.getSimpleCatalogName());
		cat.setPath(catid.getPath());
		cat.setCatalogBaseName(catid.getCatalogBaseName());
	}
	
	public static DatabaseObjectHierarchy fillObservationsFromSpreadSheetFull(DatabaseObject obj, DataCatalogID catid, int numberOfColumns, int numberOfRows) {
		DatabaseObjectHierarchy hierarchy = InterpretExpDataData.ObservationsFromSpreadSheetFull.createEmptyObject(obj);
		ObservationsFromSpreadSheetFull observations = (ObservationsFromSpreadSheetFull) hierarchy.getObject();
		
		DatabaseObjectHierarchy inputhierarchy = hierarchy.getSubObject(observations.getSpreadSheetInputInformation());
		SpreadSheetInputInformation input = (SpreadSheetInputInformation) inputhierarchy.getObject();
		DatabaseObjectHierarchy observehierarchy = hierarchy.getSubObject(observations.getObservationMatrixValues());
		ObservationMatrixValues values = (ObservationMatrixValues) observehierarchy.getObject();
		DatabaseObjectHierarchy valuemulthier = observehierarchy.getSubObject(values.getObservationRowValue());
		ChemConnectCompoundMultiple valuemult = (ChemConnectCompoundMultiple) valuemulthier.getObject();
		replaceDataCatalogID(hierarchy,observations.getCatalogDataID(),catid);
		StringBuilder build = new StringBuilder();
		
		for(int rowcount= 0; rowcount < numberOfRows; rowcount++) {
			DatabaseObjectHierarchy obshier = InterpretExpDataData.ObservationValueRow.createEmptyObject(valuemult);
			ObservationValueRow obs = (ObservationValueRow) obshier.getObject();
			String id = obs.getIdentifier() + rowcount;
			obs.setIdentifier(id);
			valuemulthier.addSubobject(obshier);
			for(int colcount= 0; colcount < numberOfColumns - 1; colcount++) {
				obs.addValue("0");
				build.append("0, ");
			}
			obs.addValue("0");
			obs.setRowNumber(rowcount);
			build.append("0\n");
		}
		valuemult.setNumberOfElements(numberOfRows);
		input.setDelimitor(",");
		input.setDelimitorType(SpreadSheetInputInformation.CSV);
		input.setSource(build.toString());
		input.setSourceType(SpreadSheetInputInformation.STRINGSOURCE);

		return hierarchy;
	}

	public static DatabaseObjectHierarchy fillSingleObservationDataset(DatabaseObject obj, 
			String observationS,
			String correspondenceSpecification,
			String observationMatrix,
			DataCatalogID catid) throws IOException {
		DatabaseObjectHierarchy hierarchy = InterpretExpDataData.SingleObservationDataset.createEmptyObject(obj);
		SingleObservationDataset single = (SingleObservationDataset) hierarchy.getObject();
		replaceDataCatalogID(hierarchy, single.getCatalogDataID(),catid);
		DatabaseObjectHierarchy multihierarchy = hierarchy.getSubObject(single.getChemConnectObjectLink());
		ChemConnectCompoundMultiple multilnk = (ChemConnectCompoundMultiple) multihierarchy.getObject();
		int numlinks = multilnk.getNumberOfElements();
		numlinks = CreateContactObjects.linkStructure(obj,multihierarchy,numlinks,
				MetaDataKeywords.conceptLinkCorrespondenceSpecification,
				correspondenceSpecification);
		numlinks = CreateContactObjects.linkStructure(obj,multihierarchy,numlinks,
				MetaDataKeywords.conceptLinkObservationsFromSpreadSheetFull,
				observationMatrix);
		multilnk.setNumberOfElements(numlinks+1);
		
		DatabaseObjectHierarchy corrshierarchy = ExtractCatalogInformation.getCatalogObject(correspondenceSpecification, 
				MetaDataKeywords.observationCorrespondenceSpecification );
		ObservationCorrespondenceSpecification corrspec = (ObservationCorrespondenceSpecification) corrshierarchy.getObject();

		DatabaseObjectHierarchy observationhierarchy = ExtractCatalogInformation.getCatalogObject(observationMatrix,
				MetaDataKeywords.observationsFromSpreadSheetFull);
		
		ObservationCorrespondenceSpecification obsspec = (ObservationCorrespondenceSpecification) corrshierarchy.getObject();
		String links = obsspec.getChemConnectObjectLink();
		DatabaseObjectHierarchy speclinks = corrshierarchy.getSubObject(links);
		ArrayList<String> isolateset = CreateContactObjects.findDataObjectLink(speclinks,MetaDataKeywords.conceptLinkBlockIsolation);
		String isolateID = isolateset.get(0);
		DatabaseObjectHierarchy isolatehierarchy = ExtractCatalogInformation.getCatalogObject(isolateID,
				MetaDataKeywords.spreadSheetBlockIsolation);
		SpreadSheetBlockIsolation blockisolate = (SpreadSheetBlockIsolation) isolatehierarchy.getObject();		
		DatabaseObjectHierarchy obs = IsolateBlockFromMatrix.isolateFromMatrix(catid, observationhierarchy, blockisolate);
		ObservationsFromSpreadSheetFull matrix = (ObservationsFromSpreadSheetFull) obs.getObject();
		DatabaseObjectHierarchy isolatedvalues = obs.getSubObject(matrix.getObservationMatrixValues());
		ObservationMatrixValues obsmatrix = (ObservationMatrixValues) isolatedvalues.getObject();
		DatabaseObjectHierarchy matrixhier = isolatedvalues.getSubObject(obsmatrix.getObservationRowValue());
		
		DatabaseObjectHierarchy singlevalueshier = hierarchy.getSubObject(single.getObservationValueRows());
		ChemConnectCompoundMultiple singlevaluesmultiple = (ChemConnectCompoundMultiple) singlevalueshier.getObject();
		
		
		ArrayList<DatabaseObjectHierarchy> valueset = matrixhier.getSubobjects();
		singlevaluesmultiple.setNumberOfElements(valueset.size());
		int count = 0;
		for(DatabaseObjectHierarchy valuehier : valueset) {
			ObservationValueRow value = (ObservationValueRow) valuehier.getObject();
			DatabaseObjectHierarchy copyhier = InterpretExpDataData.ObservationValueRow.createEmptyObject(singlevaluesmultiple);
			ObservationValueRow copyvalue = (ObservationValueRow) copyhier.getObject();
			String id = copyvalue.getIdentifier();
			copyvalue.setIdentifier(id + "-" + count);
			count++;
			ArrayList<String> vset = new ArrayList<String>(value.getRow());
			copyvalue.setRow(vset);
			singlevalueshier.addSubobject(copyhier);
		}
		
		DatabaseObjectHierarchy specsethier = corrshierarchy.getSubObject(corrspec.getMatrixSpecificationCorrespondenceSet());
		MatrixSpecificationCorrespondenceSet specset = (MatrixSpecificationCorrespondenceSet) specsethier.getObject();
		
		DatabaseObjectHierarchy multiplespec = specsethier.getSubObject(specset.getMatrixSpecificationCorrespondence());
		ArrayList<DatabaseObjectHierarchy> spechierset = multiplespec.getSubobjects();
		ArrayList<String> titles = new ArrayList<String>();
		ArrayList<String> uncertain = new ArrayList<String>();
		
		spechierset.sort(new Comparator<DatabaseObjectHierarchy>() {

			public int compare(DatabaseObjectHierarchy o1, DatabaseObjectHierarchy o2) {
				MatrixSpecificationCorrespondence spec1 = (MatrixSpecificationCorrespondence) o1.getObject();
				MatrixSpecificationCorrespondence spec2 = (MatrixSpecificationCorrespondence) o2.getObject();
				return spec1.getColumnNumber() - spec2.getColumnNumber();
			}
		});
		for(DatabaseObjectHierarchy spechier : spechierset) {
			MatrixSpecificationCorrespondence spec = (MatrixSpecificationCorrespondence) spechier.getObject();
			titles.add(spec.getSpecificationLabel());
			uncertain.add(String.valueOf(spec.isIncludesUncertaintyParameter()));
		}
		
		DatabaseObjectHierarchy obsspechier = corrshierarchy.getSubObject(corrspec.getObservationSpecification());
		ObservationSpecification spec = (ObservationSpecification) obsspechier.getObject();
		DatabaseObjectHierarchy dimensionspec = obsspechier.getSubObject(spec.getDimensionSpecifications());
		DatabaseObjectHierarchy measurespec = obsspechier.getSubObject(spec.getMeasureSpecifications());
		
		DatabaseObjectHierarchy compmulthier = hierarchy.getSubObject(single.getParameterValueComponents());
		ChemConnectCompoundMultiple compmult = (ChemConnectCompoundMultiple) compmulthier.getObject();
		
		setInParameterValueComponents(dimensionspec,titles,uncertain,compmult,compmulthier);
		setInParameterValueComponents(measurespec,titles,uncertain,compmult,compmulthier);
		compmult.setNumberOfElements(titles.size());
		
		return hierarchy;
	}

	public static DatabaseObjectHierarchy fillObservationsFromSpreadSheet(DatabaseObject obj, 
			DataCatalogID catid, SpreadSheetInputInformation spreadinfo,
			int numberOfColumns, int numberOfRows) {
		DatabaseObjectHierarchy hierarchy = InterpretExpDataData.ObservationsFromSpreadSheet.createEmptyObject(obj);
		ObservationsFromSpreadSheet observations = (ObservationsFromSpreadSheet) hierarchy.getObject();
		
		DatabaseObjectHierarchy titlehierarchy = hierarchy.getSubObject(observations.getObservationValueRowTitle());
		ObservationValueRowTitle title = (ObservationValueRowTitle) titlehierarchy.getObject();
		DatabaseObjectHierarchy observehierarchy = hierarchy.getSubObject(observations.getObservationMatrixValues());
		ObservationMatrixValues values = (ObservationMatrixValues) observehierarchy.getObject();
		DatabaseObjectHierarchy valuemulthier = observehierarchy.getSubObject(values.getObservationRowValue());
		ChemConnectCompoundMultiple valuemult = (ChemConnectCompoundMultiple) valuemulthier.getObject();
		replaceDataCatalogID(hierarchy,observations.getCatalogDataID(),catid);
		StringBuilder build = new StringBuilder();
		
		DatabaseObjectHierarchy infohier = hierarchy.getSubObject(observations.getSpreadSheetInputInformation());
		SpreadSheetInputInformation info = (SpreadSheetInputInformation) infohier.getObject();
		info.setSource(spreadinfo.getSource());
		info.setDelimitor(spreadinfo.getDelimitor());
		info.setType(spreadinfo.getType());
		info.setSourceType(spreadinfo.getSourceType());
		
		ArrayList<String> defaulttitles = new ArrayList<String>();
		for(int colcount= 0; colcount < numberOfColumns - 1; colcount++) {
			String coltitle = "col:" + colcount;
			defaulttitles.add(coltitle);
		}
		title.setParameterLabel(defaulttitles);
		
		for(int rowcount= 0; rowcount < numberOfRows; rowcount++) {
			DatabaseObjectHierarchy obshier = InterpretExpDataData.ObservationValueRow.createEmptyObject(valuemult);
			ObservationValueRow obs = (ObservationValueRow) obshier.getObject();
			String id = obs.getIdentifier() + rowcount;
			obs.setIdentifier(id);
			valuemulthier.addSubobject(obshier);
			
			for(int colcount= 0; colcount < numberOfColumns - 1; colcount++) {
				obs.addValue("0");
				build.append("0, ");
			}
			obs.addValue("0");
			obs.setRowNumber(rowcount);
			build.append("0\n");
		}
		valuemult.setNumberOfElements(numberOfRows);
		return hierarchy;
	}
	public static void setInParameterValueComponents(
			DatabaseObjectHierarchy spec,
			ArrayList<String> titles, ArrayList<String> uncertain,
			ChemConnectCompoundMultiple compmult, DatabaseObjectHierarchy compmulthier) {
		int count = 0;
		Iterator<String> iter = uncertain.iterator();
		for(String col : titles) {
			
			DatabaseObjectHierarchy pspechier = findParameterSpecification(spec,col);
			String uncertainS = iter.next();
			if(pspechier != null) {
				ParameterSpecification pspec = (ParameterSpecification) pspechier.getObject();
				DatabaseObjectHierarchy comphier = InterpretExpDataData.ValueParameterComponents.createEmptyObject(compmult);
				ValueParameterComponents components = (ValueParameterComponents) comphier.getObject();
				String id = components.getIdentifier();
				String compid = id + "-" + ChemConnectCompoundDataStructure.removeNamespace(col)+"-"+count;
				DatabaseObjectHierarchy unithier = pspechier.getSubObject(pspec.getUnits());
				ValueUnits units = (ValueUnits) unithier.getObject();
				components.setIdentifier(compid);
				components.setParameterLabel(col);
				components.setUnitsOfValue(units.getUnitsOfValue());
				components.setPostion(count);
				boolean uncertainB = Boolean.valueOf(uncertainS);
				components.setUncertaintyValue(uncertainB);
				compmulthier.addSubobject(comphier);
			}
			count++;
		}

	}
	public static DatabaseObjectHierarchy findParameterSpecification(DatabaseObjectHierarchy specs, String id) {
		DatabaseObjectHierarchy parameterspechier = null;
		Iterator<DatabaseObjectHierarchy> iter = specs.getSubobjects().iterator();
		while(iter.hasNext() && parameterspechier == null) {
			DatabaseObjectHierarchy spechier = iter.next();
			ParameterSpecification spec = (ParameterSpecification) spechier.getObject();
			if(id.compareTo(spec.getParameterLabel()) == 0) {
				parameterspechier = spechier;
			}
		}
		return parameterspechier;
		
	}

}
