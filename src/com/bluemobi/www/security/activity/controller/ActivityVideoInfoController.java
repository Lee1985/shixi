package com.bluemobi.www.security.activity.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.activity.ActivityVideoInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.activity.service.ActivityVideoInfoService;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: ActivityVideoInfoController 
* @Description: 活动视频控制层 
* @author sundq 
* @date 2016-02-14 14:16:40 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class ActivityVideoInfoController extends BaseController {
	@Resource
	private ActivityVideoInfoService service;

	@RequestMapping(value = "activity/activityVideoInfoList")
	public String activityVideoInfoList(HttpServletRequest request,
			HttpServletResponse response, String activityId) {
		request.setAttribute("activityId", activityId);
		return "activity/activity_video_info_list";
	}

	@RequestMapping(value = "activity/activityVideoInfoCheckedList")
	public String activityVideoInfoCheckedList(HttpServletRequest request,
			HttpServletResponse response) {
		return "activity/activity_video_info_checked_list";
	}
	
	@RequestMapping(value = "activity/activityVideoInfoAjaxPage")
	@ResponseBody
	public PageInfo<ActivityVideoInfo> activityVideoInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ActivityVideoInfo info, Integer page,
			Integer rows) {
		PageInfo<ActivityVideoInfo> pageInfo = new PageInfo<ActivityVideoInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		if(info.getSort().equals("id")){
			info.setSort("createDate");
			info.setOrder("desc");
		}
		if(info.getSort().equals("status")){
			info.setSort("status asc ,createDate desc");
			info.setOrder("");
		}
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "activity/activityVideoInfoAjaxAll")
	@ResponseBody
	public List<ActivityVideoInfo> activityVideoInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, ActivityVideoInfo info, Integer page,
			Integer rows) {
		List<ActivityVideoInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "activity/activityVideoInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> activityVideoInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ActivityVideoInfo info) {
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

	@RequestMapping(value = "activity/activityVideoInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> activityVideoInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ActivityVideoInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
	
	@RequestMapping(value = "activity/activityVideoInfoUpdateStatus")
	@ResponseBody
	public Map<String,Object> activityVideoInfoUpdateStatus(HttpServletRequest request,
			HttpServletResponse response, ActivityVideoInfo info) {
		int result = 0;
		try {
			result = service.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
}
