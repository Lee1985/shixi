package com.bluemobi.www.utils.compare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

public class DateComparator implements Comparator<Map<String, Object>>{
	
	@Override
	public int compare(Map<String, Object> map1, Map<String, Object> map2) {
		Date d1 = null;
		Date d2 = null;
		String d1Str = "";
		String d2Str = "";
		for (Entry<String, Object> entry : map1.entrySet()) {
            String key1 = entry.getKey();
            if("sendDate".equals(key1)){
            	d1Str = (String)entry.getValue();
            	try {
					d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d1Str);
				} catch (ParseException e) {
					e.printStackTrace();
				}
            }
        }
		for (Entry<String, Object> entry : map2.entrySet()) {
            String key2 = entry.getKey();
            if("sendDate".equals(key2)){
            	d2Str = (String)entry.getValue();
            	try {
					d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d2Str);
				} catch (ParseException e) {
					e.printStackTrace();
				}
            }
        }
		if(d1.after(d2)){
			return 1;
		}else if(d1.before(d2)){
			return -1;
		}else{
			return 0;
		}				
	}

}
