package com.bluemobi.www.security.system.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.system.SystemRoleAuthority;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemRoleAuthorityService;
/**
 * 
* @ClassName: SystemRoleAuthorityController 
* @Description: 系统角色权限控制层 
* @author 郭冲 
* @date 2015-03-30 13:30:06 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class SystemRoleAuthorityController extends BaseController {
	@Resource
	private SystemRoleAuthorityService service;

	@RequestMapping(value = "/system/systemRoleAuthorityList")
	public String systemRoleAuthorityList(HttpServletRequest request,
			HttpServletResponse response) {
		return "system/system_role_authority_list";
	}

	@RequestMapping(value = "/system/systemRoleAuthorityAjaxPage")
	@ResponseBody
	public PageInfo<SystemRoleAuthority> systemRoleAuthorityAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SystemRoleAuthority info, Integer page,
			Integer rows) {
		PageInfo<SystemRoleAuthority> pageInfo = new PageInfo<SystemRoleAuthority>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "/system/systemRoleAuthorityAjaxAll")
	@ResponseBody
	public List<SystemRoleAuthority> systemRoleAuthorityAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemRoleAuthority info, Integer page,
			Integer rows) {
		List<SystemRoleAuthority> results= service.selectAll(info);
		return results;
	}
	
	@RequestMapping(value = "/system/systemRoleAuthorityAjaxSave")
	@ResponseBody
	public Map<String,Object> systemRoleAuthorityAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SystemRoleAuthority info) {
		int result = 0;
		String msg = "授权失败！";
		try {
			result = service.saveAuthority(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "/system/systemRoleAuthorityAjaxDelete")
	@ResponseBody
	public Map<String,Object> systemRoleAuthorityAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SystemRoleAuthority info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
