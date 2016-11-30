package com.bluemobi.www.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

public class StringHandler {
	
	public static String encode(String str){
		try {
    		if(StringUtils.isNotBlank(str)){
    			str = URLEncoder.encode(str,"utf-8");
    		}    		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return str;
	}
	
	public static String decode(String str){    	
    	try {
    		if(StringUtils.isNotBlank(str)){
    			str = URLDecoder.decode(str,"utf-8");
    		}    		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return str;
    }

}
