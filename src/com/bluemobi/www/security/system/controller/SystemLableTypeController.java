package com.bluemobi.www.security.system.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.system.SystemLableType;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemLableTypeService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemLableTypeController 
* @Description: 标签类别控制层 
* @author sundq 
* @date 2016-02-24 12:54:01 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class SystemLableTypeController extends BaseController {
	@Resource
	private SystemLableTypeService service;

	@RequestMapping(value = "system/systemLableTypeList")
	public String systemLableTypeList(HttpServletRequest request,
			HttpServletResponse response) {
		return "system/system_lable_type_list";
	}

	@RequestMapping(value = "system/systemLableTypeAjaxPage")
	@ResponseBody
	public PageInfo<SystemLableType> systemLableTypeAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SystemLableType info, Integer page,
			Integer rows) {
		PageInfo<SystemLableType> pageInfo = new PageInfo<SystemLableType>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		if(info.getSort().equals("id")){
			info.setSort("orderList");
			info.setOrder("asc");
		}
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "system/systemLableTypeAjaxAll")
	@ResponseBody
	public List<SystemLableType> systemLableTypeAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemLableType info, Integer page,
			Integer rows) {
		List<SystemLableType> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/systemLableTypeAjaxSave")
	@ResponseBody
	public Map<String,Object> systemLableTypeAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SystemLableType info) {
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

	@RequestMapping(value = "system/systemLableTypeAjaxDelete")
	@ResponseBody
	public Map<String,Object> systemLableTypeAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SystemLableType info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
