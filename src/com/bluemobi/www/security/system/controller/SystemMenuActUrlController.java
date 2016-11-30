package com.bluemobi.www.security.system.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.system.SystemMenuActUrl;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemMenuActUrlService;
import com.bluemobi.www.utils.UUIDUtil;
/**
 * 
* @ClassName: SystemMenuActUrlController 
* @Description: 系统菜单动作资源控制层 
* @author 郭冲 
* @date 2015-03-30 09:22:49 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class SystemMenuActUrlController extends BaseController {
	@Resource
	private SystemMenuActUrlService service;

	@RequestMapping(value = "/system/systemMenuActUrlList")
	public String systemMenuActUrlList(HttpServletRequest request,
			HttpServletResponse response) {
		return "system/system_menu_act_url_list";
	}

	@RequestMapping(value = "/system/systemMenuActUrlAjaxPage")
	@ResponseBody
	public PageInfo<SystemMenuActUrl> systemMenuActUrlAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SystemMenuActUrl info, Integer page,
			Integer rows) {
		PageInfo<SystemMenuActUrl> pageInfo = new PageInfo<SystemMenuActUrl>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "/system/systemMenuActUrlAjaxAll")
	@ResponseBody
	public List<SystemMenuActUrl> systemMenuActUrlAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemMenuActUrl info, Integer page,
			Integer rows) {
		List<SystemMenuActUrl> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "/system/systemMenuActUrlAjaxSave")
	@ResponseBody
	public Map<String,Object> systemMenuActUrlAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SystemMenuActUrl info) {
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

	@RequestMapping(value = "/system/systemMenuActUrlAjaxDelete")
	@ResponseBody
	public Map<String,Object> systemMenuActUrlAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SystemMenuActUrl info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
