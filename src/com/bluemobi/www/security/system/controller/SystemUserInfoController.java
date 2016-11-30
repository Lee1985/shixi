package com.bluemobi.www.security.system.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.system.SystemUserInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemUserInfoService;
import com.bluemobi.www.utils.UUIDUtil;
/**
 * 
* @ClassName: SystemUserInfoController 
* @Description: 后台管理控制层 
* @author lipeng 
* @date 2016-02-02 09:16:54 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class SystemUserInfoController extends BaseController {
	
	@Resource
	private SystemUserInfoService systemUserInfoService;
	
	@RequestMapping(value = "system/systemUserInfoList")
	public String systemUserInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		return "system/system_user_info_list";
	}
	
	@RequestMapping(value = "system/systemUserInfoAjaxPage")
	@ResponseBody
	public PageInfo<SystemUserInfo> systemUserInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SystemUserInfo info, Integer page,
			Integer rows) {
		PageInfo<SystemUserInfo> pageInfo = new PageInfo<SystemUserInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		systemUserInfoService.selectAll(info, pageInfo);
		return pageInfo;
	}
	
	@RequestMapping(value = "system/systemUserInfoAjaxAll")
	@ResponseBody
	public List<SystemUserInfo> systemUserInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemUserInfo info, Integer page,
			Integer rows) {
		List<SystemUserInfo> results= systemUserInfoService.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/systemUserInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> systemUserInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SystemUserInfo info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = systemUserInfoService.insert(info);
			msg = "保存失败！";
		} else {
			result = systemUserInfoService.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}
	
	@RequestMapping(value = "system/systemUserInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> systemUserInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SystemUserInfo info) {
		int result = 0;
		try {
			result = systemUserInfoService.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
