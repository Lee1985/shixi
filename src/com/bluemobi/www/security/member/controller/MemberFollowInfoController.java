package com.bluemobi.www.security.member.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.member.MemberFollowInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberFollowInfoService;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: MemberFollowInfoController 
* @Description: 用户关注信息控制层 
* @author sundq 
* @date 2016-03-02 13:15:05 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class MemberFollowInfoController extends BaseController {
	@Resource
	private MemberFollowInfoService service;

	@RequestMapping(value = "member/memberFollowInfoList")
	public String memberFollowInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		return "member/member_follow_info_list";
	}

	@RequestMapping(value = "member/memberFollowInfoAjaxPage")
	@ResponseBody
	public PageInfo<MemberFollowInfo> memberFollowInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, MemberFollowInfo info, Integer page,
			Integer rows) {
		PageInfo<MemberFollowInfo> pageInfo = new PageInfo<MemberFollowInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "member/memberFollowInfoAjaxAll")
	@ResponseBody
	public List<MemberFollowInfo> memberFollowInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, MemberFollowInfo info, Integer page,
			Integer rows) {
		List<MemberFollowInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "member/memberFollowInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> memberFollowInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, MemberFollowInfo info) {
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

	@RequestMapping(value = "member/memberFollowInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> memberFollowInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, MemberFollowInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
