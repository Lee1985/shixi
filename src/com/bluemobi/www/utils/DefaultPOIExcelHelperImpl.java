package com.bluemobi.www.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultPOIExcelHelperImpl implements POIExcelHelper{

	private POIExcelTransformer poiTransformer;
		
	public DefaultPOIExcelHelperImpl(POIExcelTransformer poiTransformer) {
		this.poiTransformer = poiTransformer;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public  Map<String , List> convertExcel2BeanCollection() {
		
		Map<String, List<List<String>>> sheetMap = poiTransformer.readExcel();
		
		Map<String , List> proprietorMap = new HashMap<String , List>();
		if (null == sheetMap || sheetMap.size() <= 0 ) {
			return null;	
		}
		
		for (Map.Entry<String, List<List<String>>> rowEntry : sheetMap.entrySet()) {			
			List<List<String>> rowList = rowEntry.getValue();
			if (null == rowList || rowList.size() <=0 ){
				continue;
			}

			List list = new ArrayList();
			for (List<String> cellList : rowList) {

				if (null == cellList || cellList.isEmpty()) {
					continue;
				}
				
				if (cellList.size() < 8) {
					continue;
				}
			}
			proprietorMap.put(rowEntry.getKey(), list);
		}
		return proprietorMap;
	}

	@Override
	public <T> T convertExcelRow2Bean(int sheetIndex, int rownum) {
		return null;
	}

	@Override
	public <T> T convertExcelRow2Bean(String sheetName, int rownum) {
		return null;
	}
}
