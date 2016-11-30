package com.bluemobi.www.utils.compare;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class IntegerComparator implements Comparator<Map<String, Object>>{
	
	@Override
	public int compare(Map<String, Object> map1, Map<String, Object> map2) {
		int o1 = 0;
		int o2 = 0;
		for (Entry<String, Object> entry : map1.entrySet()) {
            String key1 = entry.getKey();
            if("orderNo".equals(key1)){
            	o1 = (Integer)entry.getValue();            	
            }
        }
		for (Entry<String, Object> entry : map2.entrySet()) {
            String key2 = entry.getKey();
            if("orderNo".equals(key2)){
            	o2 = (Integer)entry.getValue();            	
            }
        }
		if(o1 < o2){
			return 1;
		}else if(o1 > o2){
			return -1;
		}else{
			return 0;
		}				
	}

}
