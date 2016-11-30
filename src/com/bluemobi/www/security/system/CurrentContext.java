package com.bluemobi.www.security.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
* @ClassName: CurrentContext
* @Description: CurrentContext是HttpServletRequest和HttpServletResponse缓存类.
* @author yangml
* @date 2014-12-3 上午9:52:51
*
*/ 
public final class CurrentContext {

	private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) requestLocal.get();
	}

	public static void setRequest(HttpServletRequest request) {
		requestLocal.set(request);
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) responseLocal.get();
	}

	public static void setResponse(HttpServletResponse response) {
		responseLocal.set(response);
	}

	public static HttpSession getSession() {
		return (HttpSession) ((HttpServletRequest) requestLocal.get())
				.getSession();
	}

	public static String getInterfacePath(HttpServletRequest request) {
		
		if(null != request) {
			return request.getSession().getServletContext().getInitParameter("interfacePath");
		} else {
			return null;
		}
	}
	
}
