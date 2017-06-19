package com.bluemobi.www.security.base.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.utils.AesUtil;
import com.bluemobi.www.utils.FileTool;
import com.bluemobi.www.utils.PrintWriters;
import com.bluemobi.www.utils.Utils;

@Controller
public class BaseController {
	
	public final static Integer pageSize = 10;
	
	public String getDecryptStr(String data) throws Exception {
		if (Utils.EncodingAesKey.equals("")) {
			Utils utils = new Utils();
			utils.init();
		}
		data = AesUtil.Decrypt(data, Utils.EncodingAesKey);
		return URLDecoder.decode(data, "utf-8");
	}
	
	public String queryLikeParamHandler(String param){
		if(StringUtils.isNotBlank(param)){
			return encodeParam(param).replaceAll("%", Matcher.quoteReplacement("\\%"));
		}
		return param;
	}

	/**
	 * 
	 * @param success
	 *            是否成功
	 * @return
	 */
	protected JSONObject getJsonResult(boolean success) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		return JSONObject.fromObject(map);
	}

	/**
	 * 
	 * @param success
	 *            是否成功
	 * @param msg
	 *            提示信息
	 * @return
	 */
	protected JSONObject getJsonResult(boolean success, String msg,
			String errorMsg) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (success) {
			map.put("success", true);
			map.put("msg", msg);
		} else {
			map.put("success", false);
			map.put("msg", errorMsg);
		}
		return JSONObject.fromObject(map);
	}

	/**
	 * 
	 * @param result
	 *            大于0成功小于0失败
	 * @param msg
	 *            提示信息
	 * @return
	 */
/*	protected JSONObject getJsonResult(Integer result, String msg,
			String errorMsg) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (result > 0) {
			map.put("success", true);
			map.put("message", msg);
		} else {
			map.put("success", false);
			map.put("message", errorMsg);
		}
		System.out.println(JSONObject.fromObject(map).toString());
		return JSONObject.fromObject(map);
	}*/
	
	protected Map<String,Object> getJsonResult(Integer result, String msg,String errorMsg) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (result > 0) {
			map.put("success", true);
			map.put("msg", msg);
		} else {
			map.put("success", false);
			map.put("msg", errorMsg);
		}
		return map;
	}
	
	/**
	 * 
	 * @param result
	 *            大于0成功小于0失败
	 * @param msg
	 *            提示信息
	 * @return
	 */
	protected JSONObject getJsonResultYesorNo(Integer result, String msg,
			String errorMsg) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (result > 0) {
			map.put("success", "yes");
			map.put("msg", msg);
		} else {
			map.put("success", "no");
			map.put("msg", errorMsg);
		}
		return JSONObject.fromObject(map);
	}
	
	/**
	 * 
	 * @param result
	 *            大于0成功小于0失败
	 * @param msg
	 *            提示信息
	 * @return
	 */
	protected JSONObject getJsonResultWithMessage(Integer result, String msg,
			String errorMsg) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (result > 0) {
			map.put("success", "yes");
			map.put("message", msg);
		} else {
			map.put("success", "no");
			map.put("message", errorMsg);
		}
		return JSONObject.fromObject(map);
	}
	
	protected boolean deleteBrandLogoFile(String path) {
		boolean flag = false;
		FileTool.deleteFile(path);
		path = path.substring(0, path.lastIndexOf("/"));
		File dirFile = new File(path);
		// 删除日文件夹
		if (dirFile.listFiles() != null && dirFile.listFiles().length == 0) {
			flag = FileTool.deleteDirectory(path);
		}
		// 删除月文件夹
		if (flag) {
			path = path.substring(0, path.lastIndexOf("/"));
			dirFile = new File(path);
			if (dirFile.listFiles().length == 0) {
				flag = FileTool.deleteDirectory(path);
			}
		}
		// 删除年文件夹
		if (flag) {
			path = path.substring(0, path.lastIndexOf("/"));
			dirFile = new File(path);
			if (dirFile.listFiles().length == 0) {
				flag = FileTool.deleteDirectory(path);
			}
		}
		return flag;
	}
	
	public void writeJsonObject(HttpServletResponse response, int result,
			String msg, JSONObject data) {
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("result", result);
		jsonobject.put("msg", msg);
		if (null != data) {
			jsonobject.put("data", data.toString());
		}
		try {
			String content = jsonobject.toString();
			PrintWriters.write(response,
					getDesMw(URLEncoder.encode(content, "utf-8")));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public String getDesMw(String content) {
		if (Utils.EncodingAesKey.equals("")) {
			Utils utils = new Utils();
			utils.init();
		}
		try {
			content = AesUtil.Encrypt(content, Utils.EncodingAesKey);
		} catch (Exception e) {

		}
		return content;
	}
	
	public void writeError(HttpServletResponse response) {
		try {
			writeJsonObject(response, -100, "异常!", null);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void writeJsonPage(HttpServletResponse response, int result,String msg, PageInfo pageInfo) {
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("result", result);
		jsonobject.put("msg", msg);
		if (null != pageInfo) {
			jsonobject.put("data", pageInfo);
		}
		try {
			String content = jsonobject.toString();
			System.out.println(content);
			PrintWriters.write(response,
					getDesMw(URLEncoder.encode(content, "utf-8")));
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void writeResult(HttpServletResponse response, int result) {
		try {
			if (result > 0) {
				writeJsonObject(response, result, "操作成功!", null);
			} else {
				result = -1;
				writeJsonObject(response, result, "操作失败!", null);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public JSONObject handleException(Exception ex,HttpServletRequest request) {	
		String errorMessage = StringUtils.EMPTY;
		if (ex instanceof MaxUploadSizeExceededException){
			errorMessage = "文件应不大于 " + getFileMB(((MaxUploadSizeExceededException)ex).getMaxUploadSize());
		} else{
			ex.printStackTrace();
			errorMessage = "未知错误: " + ex.getMessage();
		}  
		return getJsonResultWithMessage(-1,"",errorMessage);
    }
	
    private String getFileMB(long byteFile){  
        if(byteFile==0)  
           return "0MB";  
        long mb=1024*1024;  
        return ""+byteFile/mb+"MB";  
    }
    
    protected boolean deleteFile(String path) {
		boolean flag = false;
		FileTool.deleteFile(path);
		path = path.substring(0, path.lastIndexOf("/"));
		File dirFile = new File(path);
		// 删除日文件夹
		if (dirFile.listFiles() != null && dirFile.listFiles().length == 0) {
			flag = FileTool.deleteDirectory(path);
		}
		// 删除月文件夹
		if (flag) {
			path = path.substring(0, path.lastIndexOf("/"));
			dirFile = new File(path);
			if (dirFile.listFiles().length == 0) {
				flag = FileTool.deleteDirectory(path);
			}
		}
		// 删除年文件夹
		if (flag) {
			path = path.substring(0, path.lastIndexOf("/"));
			dirFile = new File(path);
			if (dirFile.listFiles().length == 0) {
				flag = FileTool.deleteDirectory(path);
			}
		}
		return flag;
	}
    
    public String getBasePath(HttpServletRequest request){
    	if(request.getServerPort() == 80){
    		return request.getScheme() + "://shixinet.com" + request.getContextPath() + "/";
    	}else{
    		return request.getScheme() + "://shixinet.com:" + request.getServerPort() + request.getContextPath() + "/";
    	}    	
    }
    
    public String encodeParam(String param){    	
    	try {
    		if(StringUtils.isNotBlank(param)){
    			param = URLEncoder.encode(param,"utf-8");
    		}    		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return param;
    }
    
    public String decodeParam(String param){    	
    	try {
    		if(StringUtils.isNotBlank(param)){
    			param = URLDecoder.decode(param,"utf-8");
    		}    		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return param;
    }
}
