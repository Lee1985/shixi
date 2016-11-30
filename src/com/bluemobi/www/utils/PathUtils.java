package com.bluemobi.www.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 项目路径 操作辅助类
 * 
 * @author maew
 * 
 */
public class PathUtils {

	public static String getRemotePath() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra)
				.getRequest();
		String scheme = request.getScheme();
		String host = request.getServerName();
		int port = request.getServerPort();
		String context = request.getContextPath();
		String absPath = scheme + "://" + host + (port != 80 ? ":" + port : "")
				+ context;
		return absPath;
	}

	public static String getLocalPath() {
		return System.getProperty("cms_root");
	}
}
