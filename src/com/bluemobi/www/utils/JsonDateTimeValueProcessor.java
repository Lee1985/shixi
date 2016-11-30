package com.bluemobi.www.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateTimeValueProcessor implements JsonValueProcessor{
    
    public static final String Default_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /*
     * (non-Javadoc)
     * @see
     * net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang
     * .Object, net.sf.json.JsonConfig)
     */
    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }
    
    /*
     * (non-Javadoc)
     * @see
     * net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang
     * .String, java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    public Object processObjectValue(String key, Object value,JsonConfig jsonConfig) {
        return process(value);
    }
    
    private Object process(Object value) {
    	if(value instanceof Date){    
            SimpleDateFormat sdf = new SimpleDateFormat(Default_DATE_PATTERN);    
            return sdf.format(value);  
        }    
        return value == null ? "" : value.toString();
    }
}
