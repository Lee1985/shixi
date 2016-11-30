package com.bluemobi.www.security.member.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.member.MemberResumeTemplate;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberResumeTemplateService;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: MemberResumeTemplateController 
* @Description: 用户常用简历控制层 
* @author sundq 
* @date 2016-02-26 12:58:50 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class MemberResumeTemplateController extends BaseController {
	@Resource
	private MemberResumeTemplateService service;

	@RequestMapping(value = "member/memberResumeTemplateList")
	public String memberResumeTemplateList(HttpServletRequest request,
			HttpServletResponse response) {
		return "member/member_resume_template_list";
	}

	@RequestMapping(value = "member/memberResumeTemplateAjaxPage")
	@ResponseBody
	public PageInfo<MemberResumeTemplate> memberResumeTemplateAjaxPage(HttpServletRequest request,
			HttpServletResponse response, MemberResumeTemplate info, Integer page,
			Integer rows) {
		PageInfo<MemberResumeTemplate> pageInfo = new PageInfo<MemberResumeTemplate>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "member/memberResumeTemplateAjaxAll")
	@ResponseBody
	public List<MemberResumeTemplate> memberResumeTemplateAjaxAll(HttpServletRequest request,
			HttpServletResponse response, MemberResumeTemplate info, Integer page,
			Integer rows) {
		List<MemberResumeTemplate> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "member/memberResumeTemplateAjaxSave")
	@ResponseBody
	public Map<String,Object> memberResumeTemplateAjaxSave(HttpServletRequest request,
			HttpServletResponse response, MemberResumeTemplate info) {
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

	@RequestMapping(value = "member/memberResumeTemplateAjaxDelete")
	@ResponseBody
	public Map<String,Object> memberResumeTemplateAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, MemberResumeTemplate info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
