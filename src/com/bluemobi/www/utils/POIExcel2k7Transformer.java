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

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POIExcel2k7Transformer implements POIExcelTransformer{

	private XSSFWorkbook xssWorkbook;
	
	public POIExcel2k7Transformer(String filePath) throws FileNotFoundException, IOException {
		this.xssWorkbook = new XSSFWorkbook(new FileInputStream(filePath));
	}
	
	public POIExcel2k7Transformer(InputStream input) throws IOException {
		this.xssWorkbook = new XSSFWorkbook(input);
	}
	
	@Override
	public Map<String, List<List<String>>> readExcel() {
		
		int sheetNumber = xssWorkbook.getNumberOfSheets();
		if (sheetNumber < 0) {
			return null;
		}
		List<String> sheets = readSheetName(xssWorkbook);
		Map<String , List<List<String>>> sheetMap = new HashMap<String , List<List<String>>>();
		for (String name : sheets) {
			
			XSSFSheet sheet = xssWorkbook.getSheet(name);
			
			int lastSheetRow = sheet.getLastRowNum();
			if (lastSheetRow <= 0) {
				continue;
			}
			
			List<List<String>> rowList = new ArrayList<List<String>>();
			for (int i=1 ; i<=lastSheetRow ; i++) {
				
				XSSFRow row = sheet.getRow(i);
				List<String> cellList = readCell(row);
				
				if (null == cellList || cellList.size() <=0 ) {
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
	
	private List<String> readSheetName(XSSFWorkbook xssWorkbook) {
		
			int sheetNumber = xssWorkbook.getNumberOfSheets();
			
			List<String> sheets = new ArrayList<String>();
			
			for (int i=0 , length=sheetNumber ; i<length ; i++) {
				XSSFSheet sheet = xssWorkbook.getSheetAt(i);
				sheets.add(sheet.getSheetName());
			}
			return sheets;
	}
	
	private List<String> readCell(XSSFRow row) {
		
		if (null == row) {
			return null;
		}
		int cellNumber = row.getLastCellNum();
		
		if (cellNumber <= 0) {
			return null;
		}
		List<String> cellList = new ArrayList<String>();
		
		for (int i=0 ; i<cellNumber ; i++) {
			XSSFCell cell = row.getCell(i);
			
			if (null == cell) {
				cellList.add("");
				continue;
			}
			
			switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_BLANK : {
					cellList.add(String.valueOf(""));
					break;
				}
				case XSSFCell.CELL_TYPE_BOOLEAN : {
					cellList.add(String.valueOf(cell.getBooleanCellValue()));
					break;
				}
				case XSSFCell.CELL_TYPE_ERROR : {
					cellList.add(String.valueOf(cell.getErrorCellValue()));
					break;
				}
				case XSSFCell.CELL_TYPE_FORMULA : {
					cellList.add(String.valueOf(cell.getCellFormula()));
					break;
				}
				case XSSFCell.CELL_TYPE_NUMERIC :{
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						cellList.add(format.format(cell.getDateCellValue()));
					} else {
//						cellList.add(String.valueOf(cell.getNumericCellValue()));

						BigDecimal bd = new BigDecimal((cell.getNumericCellValue()));
						String num = bd.toPlainString();
						cellList.add(num);
					}
					break;
				}
				default : {
					cellList.add(cell.getStringCellValue());
				}
			}
		}
		return cellList;
	}

}
