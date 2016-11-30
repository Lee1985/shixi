package com.bluemobi.www.utils;

import java.util.List;
import java.util.Map;

public interface POIExcelTransformer {
	
	public Map<String , List<List<String>>> readExcel();
	
	public List<List<String>> readExcel(int sheetIndex);
	
	public List<List<String>> readExcel(String sheetName);
	
}
