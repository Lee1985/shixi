package com.bluemobi.www.security.recruit.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.recruit.RecruitInfoTemp;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfoTemp;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.recruit.service.RecruitInfoTempService;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoTempService;
import com.bluemobi.www.security.system.service.SystemLableInfoService;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: RecruitRoleInfoTempController 
* @Description: 招募角色修改信息控制层 
* @author sundq 
* @date 2016-03-08 13:19:09 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class RecruitRoleInfoTempController extends BaseController {
	@Resource
	private RecruitRoleInfoTempService service;
	@Resource
	private RecruitInfoTempService infoTempService;
	@Resource
	private SystemLableInfoService lableInfoService;

	@RequestMapping(value = "recruit/recruitRoleInfoTempList")
	public String recruitRoleInfoTempList(HttpServletRequest request,
			HttpServletResponse response, String recruitId, String type , String backUrl) {
		RecruitInfoTemp entity = infoTempService.selectById(recruitId);
		request.setAttribute("recruitId", recruitId);
		request.setAttribute("entity", entity);
		request.setAttribute("backUrl", backUrl);
		request.setAttribute("type", type);
		return "recruit/recruit_role_info_temp_list_view";
	}
	
	@RequestMapping(value = "recruit/recruitRoleInfoTempNextList")
	public String recruitRoleInfoTempNextList(HttpServletRequest request,
			HttpServletResponse response, String createDate, String type , String backUrl) {
		RecruitInfoTemp entity = infoTempService.selectNext(type, createDate);
		if(entity == null){
			return "redirect:/recruit/recruitInfoTempList.do";
		}
		request.setAttribute("recruitId", entity.getId());
		request.setAttribute("entity", entity);
		request.setAttribute("backUrl", backUrl);
		request.setAttribute("type", type);
		return "recruit/recruit_role_info_temp_list_view";
	}

	@RequestMapping(value = "recruit/recruitRoleInfoTempAjaxPage")
	@ResponseBody
	public PageInfo<RecruitRoleInfoTemp> recruitRoleInfoTempAjaxPage(HttpServletRequest request,
			HttpServletResponse response, RecruitRoleInfoTemp info, Integer page,
			Integer rows) {
		PageInfo<RecruitRoleInfoTemp> pageInfo = new PageInfo<RecruitRoleInfoTemp>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setIsDelete("0");
		info.setStatus("1");
		service.selectAll(info, pageInfo);
//		getLableNameList(pageInfo.getRows());
		return pageInfo;
	}

	@RequestMapping(value = "recruit/recruitRoleInfoTempAjaxAll")
	@ResponseBody
	public List<RecruitRoleInfoTemp> recruitRoleInfoTempAjaxAll(HttpServletRequest request,
			HttpServletResponse response, RecruitRoleInfoTemp info, Integer page,
			Integer rows) {
		List<RecruitRoleInfoTemp> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "recruit/recruitRoleInfoTempAjaxSave")
	@ResponseBody
	public Map<String,Object> recruitRoleInfoTempAjaxSave(HttpServletRequest request,
			HttpServletResponse response, RecruitRoleInfoTemp info) {
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

	@RequestMapping(value = "recruit/recruitRoleInfoTempAjaxDelete")
	@ResponseBody
	public Map<String,Object> recruitRoleInfoTempAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, RecruitRoleInfoTemp info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
	
	@RequestMapping(value = "recruit/recruitRoleInfoTempById")
	public String recruitRoleInfoById(HttpServletRequest request,
			HttpServletResponse response, RecruitRoleInfoTemp info, String type, String backUrl) {
		try {
			info = service.selectById(info.getId());
			info.setRecruitTempInfo(infoTempService.selectById(info.getRecruitId()));
//			getLableName(info);
		} catch (Exception e) {
			// TODO: handle exception
		}
		request.setAttribute("entity", info);
		request.setAttribute("type", type);
		request.setAttribute("backUrl", backUrl);
		return "recruit/recruit_role_info_temp_detail";
	}
}
