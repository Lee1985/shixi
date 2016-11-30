package com.bluemobi.www.security.member.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.member.MemberActionsInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberActionsInfoService;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: MemberActionsInfoController 
* @Description: 用户操作信息控制层 
* @author sundq 
* @date 2016-03-10 10:29:21 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class MemberActionsInfoController extends BaseController {
	@Resource
	private MemberActionsInfoService service;

	@RequestMapping(value = "member/memberActionsInfoList")
	public String memberActionsInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		return "member/member_actions_info_list";
	}

	@RequestMapping(value = "member/memberActionsInfoAjaxPage")
	@ResponseBody
	public PageInfo<MemberActionsInfo> memberActionsInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, MemberActionsInfo info, Integer page,
			Integer rows) {
		PageInfo<MemberActionsInfo> pageInfo = new PageInfo<MemberActionsInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setSort("createDate");
		info.setOrder("desc");
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "member/memberActionsInfoAjaxAll")
	@ResponseBody
	public List<MemberActionsInfo> memberActionsInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, MemberActionsInfo info, Integer page,
			Integer rows) {
		List<MemberActionsInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "member/memberActionsInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> memberActionsInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, MemberActionsInfo info) {
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

	@RequestMapping(value = "member/memberActionsInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> memberActionsInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, MemberActionsInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
