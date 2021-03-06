package info.esblurock.reaction.core.server.expdata.observation.spreadsheet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ibm.icu.util.StringTokenizer;

import info.esblurock.reaction.core.data.expdata.data.observations.ObservationMatrixValues;
import info.esblurock.reaction.core.data.expdata.data.observations.ObservationValueRow;
import info.esblurock.reaction.core.data.expdata.data.observations.ObservationsFromSpreadSheetFull;
import info.esblurock.reaction.core.data.expdata.data.observations.SpreadSheetBlockInformation;
import info.esblurock.reaction.core.data.expdata.data.observations.SpreadSheetInputInformation;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;
import info.esblurock.reaction.core.server.base.db.GCSServiceRoutines;
import info.esblurock.reaction.core.server.base.db.UserImageServiceImpl;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseDataUtilities;
import info.esblurock.reaction.core.server.expdata.metadata.ExpDataStandardDataKeywords;
import info.esblurock.reaction.core.server.expdata.util.InterpretExpDataData;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.transfer.DataElementInformation;

public class InterpretSpreadSheet {

	public static DatabaseObjectHierarchy readSpreadSheetFromGCS(GCSBlobFileInformation gcsinfo,
			SpreadSheetInputInformation input, 
			DataCatalogID catid) throws IOException {
		InputStream stream = GCSServiceRoutines.getInputStream(gcsinfo);
		System.out.println("readSpreadSheetFromGCS: " + gcsinfo.toString("readSpreadSheetFromGCS: "));
		return streamReadSpreadSheet(stream, input, catid);
	}

	public static DatabaseObjectHierarchy readSpreadSheet(SpreadSheetInputInformation input, DataCatalogID catid)
			throws IOException {
		InputStream is = null;
		
		if (input.isSourceType(SpreadSheetInputInformation.URL)) {
			URL oracle;
			oracle = new URL(input.getSource());
			is = oracle.openStream();
		} else if (input.isSourceType(SpreadSheetInputInformation.STRINGSOURCE)) {
			String source = input.getSource();
			is = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		} else if (input.isSourceType(SpreadSheetInputInformation.BLOBSOURCE)) {
			System.out.println("readSpreadSheet:  BLOBSOURCE");
		}
		if (is == null) {
			throw new IOException("Source Error: couldn't open source");
		}
		System.out.println(input.toString("readSpreadSheet: "));
		return streamReadSpreadSheet(is, input,catid);
	}

	public static DatabaseObjectHierarchy streamReadSpreadSheet(InputStream is, 
			SpreadSheetInputInformation spreadinput, DataCatalogID catid) throws IOException {
		ArrayList<ObservationValueRow> set = new ArrayList<ObservationValueRow>();
		DatabaseObject obj = new DatabaseObject(spreadinput);
		obj.nullKey();
		obj.setIdentifier(catid.getFullName());

		DatabaseObjectHierarchy hierarchy = InterpretExpDataData.ObservationsFromSpreadSheetFull.createEmptyObject(obj);
		ObservationsFromSpreadSheetFull observations = (ObservationsFromSpreadSheetFull) hierarchy.getObject();
		
		DatabaseObjectHierarchy inputhierarchy = hierarchy.getSubObject(observations.getSpreadSheetInputInformation());
		SpreadSheetInputInformation input = (SpreadSheetInputInformation) inputhierarchy.getObject();
		DatabaseObjectHierarchy observehierarchy = hierarchy.getSubObject(observations.getObservationMatrixValues());
		ObservationMatrixValues values = (ObservationMatrixValues) observehierarchy.getObject();
		DatabaseObjectHierarchy cathierarchy = hierarchy.getSubObject(observations.getCatalogDataID());
		DataCatalogID cat = (DataCatalogID) cathierarchy.getObject();
				
		DatabaseObjectHierarchy valuemulthier = observehierarchy.getSubObject(values.getObservationRowValue());
		ChemConnectCompoundMultiple valuemult = (ChemConnectCompoundMultiple) valuemulthier.getObject();
		int numberOfColumns = 0;
		if (spreadinput.isType(SpreadSheetInputInformation.XLS)) {
			numberOfColumns = readXLSFile(is, valuemult, set);
		} else if (spreadinput.isType(SpreadSheetInputInformation.XLSX)) {
			numberOfColumns = readXLSXFile(is, valuemult, set);
		} else if (spreadinput.isType(SpreadSheetInputInformation.CSV)) {
			numberOfColumns = readDelimitedFile(is, ",", valuemult, set);
		} else if (spreadinput.isType(SpreadSheetInputInformation.Delimited)) {
			numberOfColumns = readDelimitedFile(is, spreadinput.getDelimitor(), valuemult, set);
		} else if (spreadinput.isType(SpreadSheetInputInformation.SpaceDelimited)) {
			numberOfColumns = readDelimitedFile(is, " ", valuemult, set);
		} else if (spreadinput.isType(SpreadSheetInputInformation.TabDelimited)) {
			numberOfColumns = readDelimitedFile(is, "\t", valuemult, set);
		}
		String numberOfColumnsS = Integer.toString(numberOfColumns);
		values.setNumberOfColumns(numberOfColumnsS);
		valuemult.setNumberOfElements(set.size());
		input.localFill(spreadinput);
		cat.localFill(catid);
		for(int rowcount = 0; rowcount < set.size(); rowcount++) {
			ObservationValueRow obs = set.get(rowcount);
			DatabaseObjectHierarchy obshier = new DatabaseObjectHierarchy(obs);
			DataElementInformation element = DatasetOntologyParseBase
					.getSubElementStructureFromIDObject(ExpDataStandardDataKeywords.observationValueRow);
			String id = InterpretBaseDataUtilities.createSuffix(valuemult, element) + String.valueOf(rowcount);
			obs.setIdentifier(id);
			valuemulthier.addSubobject(obshier);
		}
		return hierarchy;
	}

	private static ObservationValueRow createSpreadSheetRow(DatabaseObject obj, int count, ArrayList<String> lst) {
		DatabaseObject rowobj = new DatabaseObject(obj);
		String id = obj.getIdentifier() + "-R" + count;
		rowobj.setIdentifier(id);
		
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(rowobj,obj.getIdentifier());
		ObservationValueRow row = new ObservationValueRow(structure, count, lst);
		return row;
	}

	public static int readDelimitedFile(InputStream is, String delimiter, DatabaseObject obj,
			ArrayList<ObservationValueRow> rowset) throws IOException {
		BufferedInputStream reader = new BufferedInputStream(is);
		BufferedReader r = new BufferedReader(new InputStreamReader(reader, StandardCharsets.UTF_8));
		boolean notdone = true;
		int count = 0;
		int numberOfColumns = 0;
		while (notdone) {
			String line = r.readLine();
			ArrayList<String> lst = new ArrayList<String>();
			if (line != null) {
				StringTokenizer tok = new StringTokenizer(line, delimiter,true);
				while (tok.hasMoreTokens()) {
					String cell = tok.nextToken();
					if(cell.compareTo(delimiter) == 0) {
						lst.add("");
					} else {
						lst.add(cell);
						if(tok.hasMoreTokens()) {
							tok.nextToken();
						}
					}
				}
				if (lst.size() > numberOfColumns) {
					numberOfColumns = lst.size();
				}
				ObservationValueRow row = createSpreadSheetRow(obj, count++, lst);
				rowset.add(row);
			} else {
				notdone = false;
			}
		}
		return numberOfColumns;
	}

	public static int readXLSFile(InputStream is, DatabaseObject obj, ArrayList<ObservationValueRow> rowset)
			throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook(is);
		Sheet sheet = wb.getSheetAt(0);
		int numberOfColumns = readSpreadSheet(sheet,obj,rowset);
		wb.close();
		return numberOfColumns;
	}
	public static int readSpreadSheet(Sheet sheet, DatabaseObject obj, ArrayList<ObservationValueRow> rowset) {
		
		
		Row row;
		Cell cell;

		int numberOfColumns = 0;
		Iterator<Row> rows = sheet.rowIterator();
		int count = 0;
		while (rows.hasNext()) {
			row = (Row) rows.next();
			Iterator<Cell> cells = row.cellIterator();
			ArrayList<String> array = new ArrayList<String>();
			int columnnumber = 0;
			while (cells.hasNext()) {
				cell = (Cell) cells.next();
				while(columnnumber < cell.getAddress().getColumn()) {
					array.add("");
					columnnumber++;
				}
								
				if (cell.getCellTypeEnum() == CellType.STRING) {
					String element = cell.getStringCellValue();
					array.add(element);
				} else if (cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
					double dbl = cell.getNumericCellValue();
					Double elementD = new Double(dbl);
					String elementS = elementD.toString();
					array.add(elementS);
				} else if (cell.getCellTypeEnum() == CellType.BLANK) {
					array.add("");
				} else if (cell.getCellTypeEnum() == CellType.ERROR) {
					array.add("error");
				} else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
					boolean ans = cell.getBooleanCellValue();
					if(ans) {
						array.add(Boolean.TRUE.toString());
					} else {
						array.add(Boolean.FALSE.toString());
					}
				} else if (cell.getCellTypeEnum() == CellType._NONE) {
					array.add("");
				}
				columnnumber++;
			}
			if (array.size() > numberOfColumns) {
				numberOfColumns = array.size();
			}
			ObservationValueRow arrayrow = createSpreadSheetRow(obj, count++, array);
			rowset.add(arrayrow);
		}
		return numberOfColumns;
	}
	
	public static int readXLSXFile(InputStream is, DatabaseObject obj, ArrayList<ObservationValueRow> rowset)
			throws IOException {
		XSSFWorkbook wb = new XSSFWorkbook(is);

		Sheet sheet = wb.getSheetAt(0);
		int numberOfColumns = readSpreadSheet(sheet,obj,rowset);
		wb.close();
		return numberOfColumns;
		/*
		Row row;
		Cell cell;

		int numberOfColumns = 0;
		Iterator<Row> rows = sheet.rowIterator();
		int count = 0;
		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
			Iterator<Cell> cells = row.cellIterator();
			ArrayList<String> array = new ArrayList<String>();
			while (cells.hasNext()) {
				cell = (XSSFCell) cells.next();
				if (cell.getCellTypeEnum() == CellType.STRING) {
					String element = cell.getStringCellValue();
					array.add(element);
				} else if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
					double dbl = cell.getNumericCellValue();
					Double elementD = new Double(dbl);
					String elementS = elementD.toString();
					array.add(elementS);
				} else if (cell.getCellType() == CellType.BLANK) {
					array.add("");
				} else {
					System.out.println("not STRING or NUMERIC: " + cell.getCellTypeEnum());
					System.out.println("not STRING or NUMERIC: " + cell.getNumericCellValue());
				}
			}
			if (array.size() > numberOfColumns) {
				numberOfColumns = array.size();
			}
			ObservationValueRow arrayrow = createSpreadSheetRow(obj, count++, array);
			rowset.add(arrayrow);
		}
		wb.close();
		*/
	}
	
/*
	public static SpreadSheetBlockInformation findBlocks(ObservationsFromSpreadSheet obs, ArrayList<ObservationValueRow> rows) {
		Iterator<ObservationValueRow> iter = rows.iterator();
		int linecount = 0;
		boolean morerows = iter.hasNext();
		ObservationValueRow row = iter.next();
		while (morerows) {
			SpreadSheetBlockInformation block = new SpreadSheetBlockInformation(linecount);
			block.setFirstLine(linecount);
			if (row.size() == 1) {
				row = isolateTitleAndComments(row, iter, block);
			} else if (row.size() > 1) {
				row = isolateBlockElements(row, iter, block);
			}
			if (row != null) {
				if (row.size() == 0) {
					boolean notdone = iter.hasNext();
					while (notdone) {
						row = nextRow(iter, block);
						notdone = iter.hasNext();
						if (row.size() > 0) {
							notdone = false;
						}
					}
				}
			}
			block.finalize();
			morerows = iter.hasNext();
			linecount = block.getTotalLineCount();
		}
	}
*/
	public static ObservationValueRow isolateTitleAndComments(ObservationValueRow row, Iterator<ObservationValueRow> iter,
			SpreadSheetBlockInformation block) {
		if (row.size() == 1) {
			block.setTitle(row.get(0));
			block.setMaxNumberOfColumns(1);
			block.setMinNumberOfColumns(1);
			block.setNumberOfLines(1);
			block.setNumberOfColumnsEqual(true);
			row = nextRow(iter, block);
			if (row.size() == 1) {
				block.incrementLineCount();
				while (row.size() == 1) {
					String comment = row.get(0);
					block.addComment(comment);
					row = nextRow(iter, block);
					block.incrementLineCount();
				}
				if (row.size() > 0) {
					isolateBlockElements(row, iter, block);
					block.setTitlePlusBlock(true);
				}
			} else {
				block.setJustTitle(true);
			}
		}
		return row;
	}

	public static ObservationValueRow isolateBlockElements(ObservationValueRow row, Iterator<ObservationValueRow> iter,
			SpreadSheetBlockInformation block) {
		block.setMinNumberOfColumns(1000000);
		while (row.size() > 1) {
			if (block.getMaxNumberOfColumns() < row.size()) {
				block.setMaxNumberOfColumns(row.size());
			}
			if (block.getMinNumberOfColumns() > row.size()) {
				block.setMinNumberOfColumns(row.size());
			}
			block.addRow(row);
			row = nextRow(iter, block);
			block.incrementLineCount();
		}
		block.decrementLineCount();
		return row;
	}

	public static ObservationValueRow nextRow(Iterator<ObservationValueRow> iter, SpreadSheetBlockInformation block) {
		ObservationValueRow row = iter.next();
		block.incrementTotalLineCount();
		return row;
	}

}
