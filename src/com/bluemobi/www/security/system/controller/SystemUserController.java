package com.bluemobi.www.security.system.controller;

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
import com.bluemobi.www.security.system.service.SystemUserService;
import com.bluemobi.www.utils.CipherUtil;
import com.bluemobi.www.utils.UUIDUtil;

@Controller
public class SystemUserController extends BaseController{
	@Resource
	private SystemUserService service;

	@RequestMapping(value = "/system/systemUserList")
	public String systemUserList(HttpServletRequest request,
			HttpServletResponse response) {
		return "system/system_user_list";
	}

	@RequestMapping(value = "/system/systemUserAjaxAll")
	@ResponseBody
	public PageInfo<SystemUserInfo> systemUserAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemUserInfo info, Integer page,
			Integer rows) {
		PageInfo<SystemUserInfo> pageInfo = new PageInfo<SystemUserInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "/system/systemUserAjaxSave")
	@ResponseBody
	public Map<String,Object> systemUserAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SystemUserInfo info) {
		int result = 0;
		String msg = "";
		if (info.getUserPwd()!=null&&!info.getUserPwd().equals("")) {
			info.setUserPwd(CipherUtil.generatePassword(info.getUserPwd()));
		}
		
		if (info.getId() == null || info.getId().equals("")) {
			SystemUserInfo con=new SystemUserInfo();
			con.setUserId(info.getUserId());
			int count=service.selectCount(con);
			if (count>0) {
				msg = "账号重复！";
			} else {
				info.setId(UUIDUtil.getUUID());
				result = service.insert(info);
				msg = "保存失败！";
			}
			
		} else {
			if (info.getStatus()==1) {
				info.setIsDelete(0);
			}
			result = service.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "/system/systemUserAjaxDelete")
	@ResponseBody
	public Map<String,Object> systemUserAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SystemUserInfo info) {
		int result = 0;
		try {
			SystemUserInfo uinfo=new SystemUserInfo();
			uinfo.setId(info.getId());
			uinfo.setStatus(0);
			uinfo.setIsDelete(1);
			result = service.update(uinfo);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
	@RequestMapping(value = "/system/systemUserAjaxUpdate")
	@ResponseBody
	public Map<String,Object> systemUserAjaxUpdate(HttpServletRequest request,
			HttpServletResponse response, SystemUserInfo info) {
		int result = 0;
		try {
			if (info.getStatus()==1) {
				info.setIsDelete(0);
			}
			result = service.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "操作失败！");
	}
}
