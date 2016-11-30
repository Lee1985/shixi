package com.bluemobi.www.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface POIExcelHelper {
	
	public <T extends Serializable> Map<String , List<T>> convertExcel2BeanCollection();
	
	public <T> T convertExcelRow2Bean(int sheetIndex , int rownum);
	
	public <T> T convertExcelRow2Bean(String sheetName , int rownum);

}
