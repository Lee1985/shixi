package com.bluemobi.www.utils;

import javax.servlet.http.HttpServletRequest;

import com.bluemobi.www.constant.SessionConstants;
import com.bluemobi.www.data.model.system.SystemUserInfo;

public class AuthTokenUtils {
	
	public static SystemUserInfo getAccountInfo(HttpServletRequest request){
		Object sessionObj = request.getSession().getAttribute(SessionConstants.SESSION_BACK_USER);
		SystemUserInfo systemAccountInfo = new SystemUserInfo();
		if(sessionObj != null){
			systemAccountInfo = (SystemUserInfo)sessionObj;
		}
		return systemAccountInfo;
	}	
}
