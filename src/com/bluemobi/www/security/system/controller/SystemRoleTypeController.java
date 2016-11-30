package com.bluemobi.www.security.system.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.system.SystemRoleType;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemRoleTypeService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemRoleTypeController 
* @Description: 角色头像类别控制层 
* @author sundq 
* @date 2016-02-24 14:07:03 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class SystemRoleTypeController extends BaseController {
	@Resource
	private SystemRoleTypeService service;

	@RequestMapping(value = "system/systemRoleTypeList")
	public String systemRoleTypeList(HttpServletRequest request,
			HttpServletResponse response) {
		return "system/system_role_type_list";
	}

	@RequestMapping(value = "system/systemRoleTypeAjaxPage")
	@ResponseBody
	public PageInfo<SystemRoleType> systemRoleTypeAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SystemRoleType info, Integer page,
			Integer rows) {
		PageInfo<SystemRoleType> pageInfo = new PageInfo<SystemRoleType>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setSort("orderlist");
		info.setOrder("asc");
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "system/systemRoleTypeAjaxAll")
	@ResponseBody
	public List<SystemRoleType> systemRoleTypeAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemRoleType info, Integer page,
			Integer rows) {
		List<SystemRoleType> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/systemRoleTypeAjaxSave")
	@ResponseBody
	public Map<String,Object> systemRoleTypeAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SystemRoleType info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			info.setCreateDate(DateUtils.currentStringDate());
			result = service.insert(info);
			msg = "保存失败！";
		} else {
			result = service.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/systemRoleTypeAjaxDelete")
	@ResponseBody
	public Map<String,Object> systemRoleTypeAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SystemRoleType info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
