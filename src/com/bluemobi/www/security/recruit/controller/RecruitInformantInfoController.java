package com.bluemobi.www.security.recruit.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.recruit.RecruitInformantInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.recruit.service.RecruitInformantInfoService;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: RecruitInformantInfoController 
* @Description: 招募举报管理控制层 
* @author sundq 
* @date 2016-02-18 11:02:27 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class RecruitInformantInfoController extends BaseController {
	@Resource
	private RecruitInformantInfoService service;

	@RequestMapping(value = "recruit/recruitInformantInfoList")
	public String recruitInformantInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		return "recruit/recruit_informant_info_list";
	}

	@RequestMapping(value = "recruit/recruitInformantInfoAjaxPage")
	@ResponseBody
	public PageInfo<RecruitInformantInfo> recruitInformantInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, RecruitInformantInfo info, Integer page,
			Integer rows) {
		PageInfo<RecruitInformantInfo> pageInfo = new PageInfo<RecruitInformantInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		if(info.getSort().equals("id")){
			info.setSort("createDate");
			info.setOrder("desc");
		}
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "recruit/recruitInformantInfoAjaxAll")
	@ResponseBody
	public List<RecruitInformantInfo> recruitInformantInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, RecruitInformantInfo info, Integer page,
			Integer rows) {
		List<RecruitInformantInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "recruit/recruitInformantInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> recruitInformantInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, RecruitInformantInfo info) {
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

	@RequestMapping(value = "recruit/recruitInformantInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> recruitInformantInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, RecruitInformantInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
