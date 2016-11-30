package com.bluemobi.www.security.member.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.member.MemberResumeInfoPhotos;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberResumeInfoPhotosService;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: MemberResumeInfoPhotosController 
* @Description: 简历剧照控制层 
* @author sundq 
* @date 2016-02-26 13:00:23 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class MemberResumeInfoPhotosController extends BaseController {
	@Resource
	private MemberResumeInfoPhotosService service;

	@RequestMapping(value = "member/memberResumeInfoPhotosList")
	public String memberResumeInfoPhotosList(HttpServletRequest request,
			HttpServletResponse response) {
		return "member/member_resume_info_photos_list";
	}

	@RequestMapping(value = "member/memberResumeInfoPhotosAjaxPage")
	@ResponseBody
	public PageInfo<MemberResumeInfoPhotos> memberResumeInfoPhotosAjaxPage(HttpServletRequest request,
			HttpServletResponse response, MemberResumeInfoPhotos info, Integer page,
			Integer rows) {
		PageInfo<MemberResumeInfoPhotos> pageInfo = new PageInfo<MemberResumeInfoPhotos>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "member/memberResumeInfoPhotosAjaxAll")
	@ResponseBody
	public List<MemberResumeInfoPhotos> memberResumeInfoPhotosAjaxAll(HttpServletRequest request,
			HttpServletResponse response, MemberResumeInfoPhotos info, Integer page,
			Integer rows) {
		List<MemberResumeInfoPhotos> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "member/memberResumeInfoPhotosAjaxSave")
	@ResponseBody
	public Map<String,Object> memberResumeInfoPhotosAjaxSave(HttpServletRequest request,
			HttpServletResponse response, MemberResumeInfoPhotos info) {
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

	@RequestMapping(value = "member/memberResumeInfoPhotosAjaxDelete")
	@ResponseBody
	public Map<String,Object> memberResumeInfoPhotosAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, MemberResumeInfoPhotos info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
