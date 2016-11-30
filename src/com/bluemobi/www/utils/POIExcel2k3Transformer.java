package com.bluemobi.www.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class POIExcel2k3Transformer implements POIExcelTransformer {

	private HSSFWorkbook workbook;
	
	public POIExcel2k3Transformer(String filePath) throws FileNotFoundException, IOException {
		this.workbook = new HSSFWorkbook(new FileInputStream(filePath));
	}
	
	public POIExcel2k3Transformer(InputStream input) throws IOException {
		this.workbook = new HSSFWorkbook(input);
	}

	@Override
	public Map<String , List<List<String>>> readExcel() {
		
		List<String> sheetNames = readSheetName(workbook);
		Map<String , List<List<String>>> sheetMap = new HashMap<String , List<List<String>>>();
		
		if (null == sheetNames || sheetNames.size() < 0) {
			return null;
		}

		for (String name : sheetNames) {
			HSSFSheet sheet = workbook.getSheet(name);
			
			int lastRow = sheet.getLastRowNum();
			List<List<String>> rowList = new ArrayList<List<String>>();
			
			if (lastRow <= 0) {
				continue;
			}

			for (int i=1 ; i<=lastRow ; i++) {
				HSSFRow row = sheet.getRow(i);
				List<String> cellList = convertCell2List(row);
				if (null == cellList || cellList.size() <= 0) {
					continue;
				}
				rowList.add(cellList);
			}	
			
			sheetMap.put(name, rowList);
		}
	
		return sheetMap;
	}

	@Override
	public List<List<String>> readExcel(int sheetIndex) {
		return null;
	}

	@Override
	public List<List<String>> readExcel(String sheetName) {
		return null;
	}
	
	private List<String> readSheetName(HSSFWorkbook workbook) {
		int sheetNumber = workbook.getNumberOfSheets();
		
		List<String> sheets = new ArrayList<String>();
		
		for (int i=0 , length=sheetNumber ; i<length ; i++) {
			HSSFSheet sheet = workbook.getSheetAt(i);
			sheets.add(sheet.getSheetName());
		}
		return sheets;
	}
	
	private List<String> convertCell2List(HSSFRow row) {
		
		if (null == row) {
			return null;
		}
		
		int lastCellNumber = row.getLastCellNum();
		
		if (lastCellNumber <= 0) {
			return null;
		}
		
		List<String> cellList = new ArrayList<String>();
		
		for (int i=0 ; i<lastCellNumber ; i++) {
			HSSFCell cell = row.getCell(i);
			
			if(null == cell) {
				cellList.add("");
				continue;
			}
			switch(cell.getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC : {
					
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						cellList.add(format.format(cell.getDateCellValue()));
					} else {
						BigDecimal bd = new BigDecimal((cell.getNumericCellValue()));
						String num = bd.toPlainString();
						cellList.add(num);
					}
					break;
				}
				case HSSFCell.CELL_TYPE_BLANK : { 
					cellList.add(String.valueOf(""));
					break;
				}
				case HSSFCell.CELL_TYPE_BOOLEAN : { 
					cellList.add(String.valueOf(cell.getBooleanCellValue()));
					break;
				}
				case HSSFCell.CELL_TYPE_ERROR : { 
					cellList.add(String.valueOf(cell.getErrorCellValue()));
					break;
				}
				case HSSFCell.CELL_TYPE_FORMULA : {
					cellList.add(String.valueOf(cell.getCellFormula()));
					break;
				}
				case HSSFCell.CELL_TYPE_STRING : {
					cellList.add(cell.getStringCellValue());
					break;
				}
			}
		}
		return cellList;
	}

}
