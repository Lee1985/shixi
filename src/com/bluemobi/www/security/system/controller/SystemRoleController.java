package com.bluemobi.www.security.system.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.system.SystemRole;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemRoleService;
import com.bluemobi.www.utils.UUIDUtil;
/**
 * 
* @ClassName: SystemRoleController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author guoc 
* @date 2015-3-30 上午9:08:15 
* @Copyright：上海科匠信息科技有限公司
 */
@Controller
public class SystemRoleController extends BaseController {
	@Resource
	private SystemRoleService service;

	@RequestMapping(value = "/system/systemRoleList")
	public String systemRoleList(HttpServletRequest request,
			HttpServletResponse response) {
		return "system/system_role_list";
	}

	@RequestMapping(value = "/system/systemRoleAjaxPage")
	@ResponseBody
	public PageInfo<SystemRole> systemRoleAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SystemRole systemRole, Integer page,
			Integer rows) {
		PageInfo<SystemRole> pageInfo = new PageInfo<SystemRole>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(systemRole, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "/system/systemRoleAjaxAll")
	@ResponseBody
	public List<SystemRole> systemRoleAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemRole systemRole, Integer page,
			Integer rows) {
		List<SystemRole> roles= service.selectAll(systemRole);
		return roles; 
	}
	
	@RequestMapping(value = "/system/systemRoleAjaxSave")
	@ResponseBody
	public Map<String,Object> systemRoleAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SystemRole systemRole) {
		int result = 0;
		String msg = "";
		if (systemRole.getId() == null || systemRole.getId().equals("")) {
			systemRole.setId(UUIDUtil.getUUID());
			result = service.insert(systemRole);
			msg = "保存失败！";
		} else {
			result = service.update(systemRole);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "/system/systemRoleAjaxDelete")
	@ResponseBody
	public Map<String,Object> systemRoleAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SystemRole info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
