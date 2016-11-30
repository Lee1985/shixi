package com.bluemobi.www.security.system.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bluemobi.www.constant.SessionConstants;
import com.bluemobi.www.data.model.system.SystemUserInfo;
import com.bluemobi.www.utils.Utils;

public class CommonInterceptor implements HandlerInterceptor {
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
//		if (!Utils.isPass) {
//			redirect(request,response);
//		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		Object obj = request.getSession().getAttribute(SessionConstants.SESSION_PERMISSURL);
		Object objectu = request.getSession().getAttribute(SessionConstants.SESSION_BACK_USER);
		SystemUserInfo user = objectu == null ? null : (SystemUserInfo) objectu;
		if (user!=null) {
			if (user.getRoleId().equals("root")) {
				return true;
			}
		}
		List<Map<String, Object>> urls = (List<Map<String, Object>>) obj;
		String requestUri=request.getRequestURI();
		requestUri=requestUri.substring(requestUri.indexOf("/",2)+1,requestUri.length());
		Utils.isPass=false;
		if (urls!=null) {
			for (Map<String, Object> map : urls) {
				if (map.get("url").equals(requestUri)) {
					Utils.isPass=true;
					break;
				}
			}
		}
		System.out.println(requestUri+"/"+Utils.isPass);
		if (Utils.isPass) {
			return true;
		} else {
			return true;
		} 
	}	
}
