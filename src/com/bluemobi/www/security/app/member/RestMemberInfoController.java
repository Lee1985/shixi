package com.bluemobi.www.security.app.member;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberInfoService;

/**
 * 
* @ClassName: RestMemberInfoController 
* @Description: 用户管理控制层 
* @author sundq 
* @date 2016-02-02 16:42:04 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class RestMemberInfoController extends BaseController {
	
	@Resource
	private MemberInfoService service;

	/**
	 * 获取用户的发布招募权限
	 * @param request
	 * @param response
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "rest/member/getMemberPublishStatus")
	@ResponseBody
	public Map<String,Object> lableInfoList(HttpServletRequest request,HttpServletResponse response, String memberId){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberInfo memberInfo = service.selectById(memberId);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		map.put("identityStatus", memberInfo.getIdentityStatus());
		map.put("realNameStatus", memberInfo.getRealNameStatus());
		if(memberInfo != null){
			if(memberInfo.getIdentityStatus() != null && memberInfo.getIdentityStatus().equals("1")){
				resultMap.put("type", "1");
				map.put("success", "yes");
				map.put("message", "可以发布官方招募");
			}else if(memberInfo.getRealNameStatus() != null && memberInfo.getRealNameStatus().equals("1")){
				resultMap.put("type", "2");
				map.put("success", "yes");
				map.put("message", "用户不正确");
				map.put("message", "可以发布私人招募");
			}else if(memberInfo.getRealNameStatus() != null && memberInfo.getRealNameStatus().equals("3")){
				resultMap.put("type", "3");
				map.put("success", "yes");
				map.put("message", "目前正处于实名认证审核中");
			}else{
				resultMap.put("type", "0");
				map.put("success", "no");
				map.put("message", "实名认证后可发布私人招募");
			}
			map.put("data", resultMap);
		}else{
			map.put("success", "no");
			map.put("message", "用户不正确");
		}
		return map;
	}
	
	
	/**
	 * 我的认证信息
	 * @param request
	 * @param response
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "rest/member/myIdentification")
	@ResponseBody
	public Map<String,Object> myIdentification(HttpServletRequest request,HttpServletResponse response,
			String memberId){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberInfo memberInfo = service.selectById(memberId);
		if(memberInfo != null){
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("realNameStatus", memberInfo.getRealNameStatus());
			resultMap.put("educationStatus", memberInfo.getEducationStatus());
			map.put("success", "yes");
			map.put("data", resultMap);
		}else{
			map.put("success", "no");
			map.put("message", "用户不正确");
		}
		return map;
	}
}
