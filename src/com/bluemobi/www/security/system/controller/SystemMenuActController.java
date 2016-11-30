package com.bluemobi.www.security.system.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.system.SystemMenuAct;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemMenuActService;
import com.bluemobi.www.utils.UUIDUtil;

@Controller
public class SystemMenuActController extends BaseController{
	@Resource
	private SystemMenuActService service;

	@RequestMapping(value = "/system/systemMenuActAjaxAll")
	@ResponseBody
	public List<SystemMenuAct> systemMenuActAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemMenuAct info) {
		info.setOrder("asc");
		info.setSort("orderList");
		List<SystemMenuAct> list=service.selectAll(info);
		return list;
	}

	@RequestMapping(value = "/system/systemMenuActAjaxSave")
	@ResponseBody
	public Map<String,Object> systemMenuActAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SystemMenuAct info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			result = service.insert(info);
			msg = "保存失败！";
		} else {
			result = service.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "/system/systemMenuActAjaxDelete")
	@ResponseBody
	public Map<String,Object> systemMenuActAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SystemMenuAct info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
