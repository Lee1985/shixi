package com.bluemobi.www.security.member.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.member.MemberResumeApplyPhotos;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberResumeApplyPhotosService;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: MemberResumeApplyPhotosController 
* @Description: 简历剧照控制层 
* @author sundq 
* @date 2016-02-26 13:00:23 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class MemberResumeApplyPhotosController extends BaseController {
	@Resource
	private MemberResumeApplyPhotosService service;

	@RequestMapping(value = "member/memberResumeApplyPhotosList")
	public String memberResumeApplyPhotosList(HttpServletRequest request,
			HttpServletResponse response) {
		return "member/member_resume_apply_photos_list";
	}

	@RequestMapping(value = "member/memberResumeApplyPhotosAjaxPage")
	@ResponseBody
	public PageInfo<MemberResumeApplyPhotos> memberResumeApplyPhotosAjaxPage(HttpServletRequest request,
			HttpServletResponse response, MemberResumeApplyPhotos info, Integer page,
			Integer rows) {
		PageInfo<MemberResumeApplyPhotos> pageInfo = new PageInfo<MemberResumeApplyPhotos>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "member/memberResumeApplyPhotosAjaxAll")
	@ResponseBody
	public List<MemberResumeApplyPhotos> memberResumeApplyPhotosAjaxAll(HttpServletRequest request,
			HttpServletResponse response, MemberResumeApplyPhotos info, Integer page,
			Integer rows) {
		List<MemberResumeApplyPhotos> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "member/memberResumeApplyPhotosAjaxSave")
	@ResponseBody
	public Map<String,Object> memberResumeApplyPhotosAjaxSave(HttpServletRequest request,
			HttpServletResponse response, MemberResumeApplyPhotos info) {
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

	@RequestMapping(value = "member/memberResumeApplyPhotosAjaxDelete")
	@ResponseBody
	public Map<String,Object> memberResumeApplyPhotosAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, MemberResumeApplyPhotos info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
