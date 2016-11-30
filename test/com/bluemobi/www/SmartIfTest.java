package com.bluemobi.www;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.bluemobi.www.utils.HttpClientUtils;


public class SmartIfTest {
	
	private final static Log log = LogFactory.getLog(SmartIfTest.class);
	
	private static String domainAddr = "http://localhost:8080/smart";
	
	/**
	 * 移动终端登录接口测试方法
	 */
	@Test
	public void testLoginIf(){
		String url =  domainAddr +"/login_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("loginName", "admin");
		parameters.put("password", "root");
		parameters.put("getuiId", "");
		parameters.put("osType", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
	
	/**
	 * 移动终端修改用户密码接口测试方法
	 */
	@Test
	public void testModifyPassIf(){
		String url =  domainAddr +"/modify_passwd_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("userId", "");
		parameters.put("roleId", "");
		parameters.put("oldPassword", "");
		parameters.put("newPassword", "");
		parameters.put("confirmPassword", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
	
	/**
	 * 移动终端获取个人用户信息接口测试方法
	 */
	@Test
	public void testMyInfoIf(){
		String url =  domainAddr +"/myinfo_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("userId", "");
		parameters.put("roleId", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
	
	/**
	 * 移动终端修改个人用户头像测试方法
	 */
	@Test
	public void testChangeHeadImgIf(){
		String url =  domainAddr +"/change_headimg_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("userId", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
	
	/**
	 * 移动终端验证用户手机是否注册接口
	 */
	@Test
	public void testCheckMobileIf(){
		String url =  domainAddr +"/check_mobile_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("userMoblie", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
	
	/**
	 * 移动终端忘记密码接口
	 */
	@Test
	public void testForgetPasswdIf(){
		String url =  domainAddr +"/forget_passwd_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("userId", "");
		parameters.put("roleType", "");
		parameters.put("newPassword", "");
		parameters.put("confirmPassword", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
	
	/**
	 * 移动终端获取用户场景列表接口
	 */
	@Test
	public void testSceneListIf(){
		String url =  domainAddr +"/scenelist_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("userId", "");
		parameters.put("isCommonUse", "");
		parameters.put("page", "");
		parameters.put("rows", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
	
	/**
	 * 移动终端获取电器模板列表接口
	 */
	@Test
	public void testElecTemplateListIf(){
		String url =  domainAddr +"/elec_templatelist_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("userId", "");
		parameters.put("page", "");
		parameters.put("rows", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
	
	/**
	 * 移动终端获取回路接口列表
	 */
	@Test
	public void testLoopListIf(){
		String url =  domainAddr +"/looplist_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("userId", "");
		parameters.put("elecTemplateId", "");
		parameters.put("page", "");
		parameters.put("rows", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
	
	/**
	 * 移动终端获取页面接口列表
	 */
	@Test
	public void testPageListIf(){
		String url =  domainAddr +"/pagelist_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("userId", "");
		parameters.put("crowdType", "");
		parameters.put("page", "");
		parameters.put("rows", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
	
	/**
	 * 移动终端留言反馈接口
	 */
	@Test
	public void testFeedbackIf(){
		String url =  domainAddr +"/feedback_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("userId", "");
		parameters.put("feedbackContent", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
	
	/**
	 * 移动终端问题上报接口
	 */
	@Test
	public void testReportIf(){
		String url =  domainAddr +"/report_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("userId", "");
		parameters.put("reportTitle", "");
		parameters.put("reportParentCate", "");
		parameters.put("reportChildCate", "");
		parameters.put("reportContent", "");
		parameters.put("contactTel", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
	
	/**
	 * 移动终端获取用户定时任务列表接口
	 */
	@Test
	public void testTimeTaskListIf(){
		String url =  domainAddr +"/timetask_list_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("userId", "");
		parameters.put("page", "");
		parameters.put("rows", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
	
	/**
	 * 移动终端改变定时任务状态接口
	 */
	@Test
	public void testChangeTimeTaskStatusIf(){
		String url =  domainAddr +"/change_task_status_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("userId", "");
		parameters.put("taskId", "");
		parameters.put("status", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
	
	/**
	 * 移动终端改变定时任务状态接口
	 */
	@Test
	public void testTimetaskDetailIf(){
		String url =  domainAddr +"/timetask_detail_if.do";
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("userId", "");
		parameters.put("taskId", "");
		String responseText = HttpClientUtils.post(url, parameters);
		log.debug(responseText);
	}
}
