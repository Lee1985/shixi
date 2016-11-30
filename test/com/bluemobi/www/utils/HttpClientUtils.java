package com.bluemobi.www.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class HttpClientUtils {
  
	
	private final static Log log = LogFactory.getLog(HttpClientUtils.class);
    
    private static String cookieStr = "";
    private static CookieStore cookieStore;
      
    public static String post(String url, Map<String, String> params) {
    	HttpClientContext context = HttpClientContext.create();
    	context.setCookieStore(cookieStore);
    	
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	        
        String body = null;  
          
        log.info("create httppost:" + url);
        
        HttpPost post = postForm(url, params); 
        post.setHeader("Cookie", cookieStr);
          
        body = invoke(httpclient, post, context);  
        
        Header[] headers = post.getAllHeaders();
        for(Header header : headers){
        	System.out.println(header.getName() + ":" + header.getValue());
        }
        
        cookieStore = context.getCookieStore();
        List<Cookie> cookieList = cookieStore.getCookies();
        StringBuffer tmpcookies = new StringBuffer();  
        for (Cookie c : cookieList) {  
        	cookieStr = tmpcookies.append(c.toString() + ";").toString();
        }
          
        try {
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
          
        return body;  
    }  
      
    public static String get(String url) {  
    	
    	HttpClientContext context = HttpClientContext.create();
    	
    	CloseableHttpClient httpclient = HttpClients.createDefault();
        String body = null;  
          
        log.info("create httppost:" + url);  
        HttpGet get = new HttpGet(url);  
        body = invoke(httpclient, get, context);  
          
        try {
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
          
        return body;  
    }  
          
      
    private static String invoke(CloseableHttpClient httpclient,HttpUriRequest httpost,HttpClientContext context) {  
          
    	CloseableHttpResponse response = sendRequest(httpclient, httpost, context);  
        String body = paseResponse(response);          
        return body;  
    }  
  
	private static String paseResponse(CloseableHttpResponse response) {  
		String body = "";
		try{
			 log.info("get response from http server..");  
		        HttpEntity entity = response.getEntity(); 
		        if(entity != null){
		        	InputStream instreams = entity.getContent();    
		            String str = convertStreamToString(instreams);  
		            body = str; 
		        }
		}catch(Exception e){
			e.printStackTrace();
		}		               
        return body;
    }  
  
    private static CloseableHttpResponse sendRequest(CloseableHttpClient httpclient,HttpUriRequest httpost,HttpClientContext context) {
        log.info("execute post...");  
        CloseableHttpResponse response = null;  
          
        try {  
            response = httpclient.execute(httpost,context);
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return response;  
    }  
  
    private static HttpPost postForm(String url, Map<String, String> params){  
          
        HttpPost httpost = new HttpPost(url);  
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
          
        Set<String> keySet = params.keySet();  
        for(String key : keySet) {  
            nvps.add(new BasicNameValuePair(key, params.get(key)));  
        }  
          
        try {  
            log.info("set utf-8 form entity to httppost");  
            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }            
        return httpost;  
    }  
    
    private static String convertStreamToString(InputStream is) {      
        StringBuilder sb1 = new StringBuilder();      
        byte[] bytes = new byte[4096];    
        int size = 0;    
          
        try {      
            while ((size = is.read(bytes)) > 0) {    
                String str = new String(bytes, 0, size, "UTF-8");    
                sb1.append(str);    
            }    
        } catch (IOException e) {      
            e.printStackTrace();      
        } finally {      
            try {      
                is.close();      
            } catch (IOException e) {      
               e.printStackTrace();      
            }      
        } 
        return sb1.toString();      
    }
}