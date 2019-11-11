package info.esblurock.reaction.core.server.expdata.metadata;

public class ExpDataStandardDataKeywords {
	
	public static String chemConnectDataObject = "dataset:ChemConnectDataObject";

	
	// ObservationBlockFromSpreadSheet
	public static String observationBlockFromSpreadSheet = "dataset:ObservationBlockFromSpreadSheet";
	public static String observationBlockFromSpreadSheetID = "dataset:SpreadSheetBlock";

	
	// SpreadSheetBlockIsolation
	public static String spreadSheetBlockIsolation = "dataset:SpreadSheetBlockIsolation";
	public static String spreadSheetInformationID = "dataset:spreadsheet";

	
	public static String lastColumnInMatrix = "dataset:lastColumnInMatrix";
	public static String startColumnInMatrix = "dataset:startColumnInMatrix";
	public static String lastRowInMatrix = "dataset:lastRowInMatrix";
	public static String startRowInMatrix = "dataset:startRowInMatrix";
	public static String includeBlockTitle = "dataset:matrixBlockTitle";
	public static String spreadSheetBlockIsolationType = "dataset:SpreadSheetBlockIsolationType";
	
	// SingleObservationDataset
	public static String singleObservationDataset = "dataset:SingleObservationDataset";
	public static String valueParameterComponentsID = "dataset:ValueParameterComponents";
	public static String observationValueRowID = "qb:Slice";

	// ValueParameterComponents
	public static String valueParameterComponents = "dataset:ValueParameterComponents";
	public static String parameterposition = "dataset:position";
	public static String parameterLabelS = "skos:prefLabel";
	public static String unitsOfValueS = "qudt:QuantityKind";
	public static String includesUncertaintyParameter = "dataset:includesUncertaintyParameter";

	
	// SpreadSheetInformation
	public static String spreadSheetSourceType = "dataset:SpreadSheetSourceType";
	public static String spreadSheetDelimitor = "dataset:SpreadSheetDelimitor";
	public static String fileSourceIdentifier = "dataset:FileSourceIdentifier";
	public static String fileSourceType = "dataset:FileSourceType";
	public static String spreadSheetInformation = "dataset:SpreadSheetInputInformation";
	public static String spreadSheetBlockIsolationID = "dataset:SpreadSheetBlockIsolation";
	public static String listOfUnits = "dataset:ListOfUnits";

	// ObservationMatrixValues
	public static String observationMatrixValuesID = "dataset:matrix";
	public static String observationMatrixValues = "dataset:ObservationMatrixValues";
	public static String numberOfColumns = "dataset:NumberOfColumns";
	
	// ObservationValueRowTitle
	public static String observationValueRowTitle = "dataset:ObservationValueRowTitle";
	public static String listOfTitles = "dataset:ListOfTitles";
	
	// ObservationValueRow
	public static String observationValueRow = "dataset:ObservationValueRow";
	public static String position = "qb:order";
	public static String listOfValuesAsString = "dataset:ListOfValuesAsString";
	
	// ValueParameterComponents
	


	public static String matrixColumn = "dataset:matrixColumn";
	public static String observationValueRowTitleID = "qb:SliceKey";
	public static String observationRowUnitsID = "dataset:ObservationRowUnits";
	public static String observationRowUnits = "dataset:ObservationRowUnits";

	
	// Transactions: SpreadSheet
	public static String interpretSpreadSheetTransaction = "dataset:InterpretSpreadSheetTransaction";
	public static String interpretSpreadSheetFromBlobTransaction = "dataset:InterpretSpreadSheetFromBlobTransaction";
	public static String isolateSubMatrixTransaction = "dataset:IsolateSubMatrixTransaction";

}
