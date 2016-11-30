package com.bluemobi.www.security.recruit.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.recruit.RecruitInformantType;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.recruit.service.RecruitInformantTypeService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: RecruitInformantTypeController 
* @Description: 招募举报类型控制层 
* @author sundq 
* @date 2016-02-18 10:35:46 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class RecruitInformantTypeController extends BaseController {
	@Resource
	private RecruitInformantTypeService service;

	@RequestMapping(value = "recruit/recruitInformantTypeList")
	public String recruitInformantTypeList(HttpServletRequest request,
			HttpServletResponse response) {
		return "recruit/recruit_informant_type_list";
	}

	@RequestMapping(value = "recruit/recruitInformantTypeAjaxPage")
	@ResponseBody
	public PageInfo<RecruitInformantType> recruitInformantTypeAjaxPage(HttpServletRequest request,
			HttpServletResponse response, RecruitInformantType info, Integer page,
			Integer rows) {
		PageInfo<RecruitInformantType> pageInfo = new PageInfo<RecruitInformantType>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		if(info.getSort().equals("id")){
			info.setSort("orderList");
			info.setOrder("asc");
		}
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "recruit/recruitInformantTypeAjaxAll")
	@ResponseBody
	public List<RecruitInformantType> recruitInformantTypeAjaxAll(HttpServletRequest request,
			HttpServletResponse response, RecruitInformantType info, Integer page,
			Integer rows) {
		List<RecruitInformantType> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "recruit/recruitInformantTypeAjaxSave")
	@ResponseBody
	public Map<String,Object> recruitInformantTypeAjaxSave(HttpServletRequest request,
			HttpServletResponse response, RecruitInformantType info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			info.setStatus("1");
			info.setCreateDate(DateUtils.currentStringDate());
			result = service.insert(info);
			msg = "保存失败！";
		} else {
			result = service.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "recruit/recruitInformantTypeAjaxDelete")
	@ResponseBody
	public Map<String,Object> recruitInformantTypeAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, RecruitInformantType info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
	
	@RequestMapping(value = "recruit/recruitInformantTypeUpdateStatus")
	@ResponseBody
	public Map<String,Object> recruitInformantTypeUpdateStatus(HttpServletRequest request,
			HttpServletResponse response, RecruitInformantType info) {
		int result = 0;
		try {
			result = service.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
}
