package com.bluemobi.www.security.system.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.system.SystemLableInfo;
import com.bluemobi.www.data.model.system.SystemLableType;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemLableInfoService;
import com.bluemobi.www.security.system.service.SystemLableTypeService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemLableInfoController 
* @Description: 标签管理控制层 
* @author sundq 
* @date 2016-02-02 16:42:04 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class SystemLableInfoController extends BaseController {
	@Resource
	private SystemLableInfoService service;
	@Resource
	private SystemLableTypeService typeService;

	@RequestMapping(value = "system/systemLableInfoList")
	public String systemLableInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		return "system/system_lable_info_list";
	}

	@RequestMapping(value = "system/systemLableInfoAjaxPage")
	@ResponseBody
	public PageInfo<SystemLableInfo> systemLableInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SystemLableInfo info, Integer page,
			Integer rows) {
		PageInfo<SystemLableInfo> pageInfo = new PageInfo<SystemLableInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setSort("createDate");
		info.setOrder("asc");
		info.setMemberId("0");
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "system/systemLableInfoAjaxAll")
	@ResponseBody
	public List<SystemLableInfo> systemLableInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemLableInfo info, Integer page,
			Integer rows) {
		List<SystemLableInfo> results= new ArrayList<SystemLableInfo>();
		List<SystemLableType> lableTypes = typeService.selectAll();
		Map<String, Object> condition = new HashMap<String, Object>();
		List<SystemLableInfo> infos =  new ArrayList<SystemLableInfo>();
		condition.put("order", "asc");
		condition.put("sort", "createDate");
		for (int i = 0; i < lableTypes.size(); i++) {
			condition.put("typeId", lableTypes.get(i).getId());
			infos = service.selectAll(condition);
			for (int j = 0; j < infos.size(); j++) {
				infos.get(j).setGroup(lableTypes.get(i).getTypeName());
			}
			results.addAll(infos);
		}
		service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/systemLableInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> systemLableInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SystemLableInfo info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			info.setCreateDate(DateUtils.currentStringDate());
			info.setMemberId("0");
			result = service.insert(info);
			msg = "保存失败！";
		} else {
			result = service.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/systemLableInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> systemLableInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SystemLableInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
