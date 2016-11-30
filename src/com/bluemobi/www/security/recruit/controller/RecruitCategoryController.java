package com.bluemobi.www.security.recruit.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.recruit.RecruitCategory;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.recruit.service.RecruitCategoryService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: RecruitCategoryController 
* @Description: 招募剧目种类控制层 
* @author sundq 
* @date 2016-02-16 11:23:38 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class RecruitCategoryController extends BaseController {
	@Resource
	private RecruitCategoryService service;

	@RequestMapping(value = "recruit/recruitCategoryList")
	public String recruitCategoryList(HttpServletRequest request,
			HttpServletResponse response) {
		return "recruit/recruit_category_list";
	}

	@RequestMapping(value = "recruit/recruitCategoryAjaxPage")
	@ResponseBody
	public PageInfo<RecruitCategory> recruitCategoryAjaxPage(HttpServletRequest request,
			HttpServletResponse response, RecruitCategory info, Integer page,
			Integer rows) {
		PageInfo<RecruitCategory> pageInfo = new PageInfo<RecruitCategory>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		if(info.getSort().equals("id")){
			info.setSort("orderList");
			info.setOrder("asc");
		}
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "recruit/recruitCategoryAjaxAll")
	@ResponseBody
	public List<RecruitCategory> recruitCategoryAjaxAll(HttpServletRequest request,
			HttpServletResponse response, RecruitCategory info, Integer page,
			Integer rows) {
		List<RecruitCategory> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "recruit/recruitCategoryAjaxSave")
	@ResponseBody
	public Map<String,Object> recruitCategoryAjaxSave(HttpServletRequest request,
			HttpServletResponse response, RecruitCategory info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			info.setStatus("0");
			info.setCreateDate(DateUtils.currentStringDate());
			result = service.insert(info);
			msg = "保存失败！";
		} else {
			result = service.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "recruit/recruitCategoryAjaxDelete")
	@ResponseBody
	public Map<String,Object> recruitCategoryAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, RecruitCategory info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
	
	@RequestMapping(value = "recruit/recruitCategoryUpdateStatus")
	@ResponseBody
	public Map<String,Object> systemAdvertisementInfoUpdateStatus(HttpServletRequest request,
			HttpServletResponse response, RecruitCategory info) {
		int result = 0;
		try {
			result = service.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
}
