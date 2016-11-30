package com.bluemobi.www.security.activity.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.activity.ActivityVideoComment;
import com.bluemobi.www.data.model.activity.ActivityVideoInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.activity.service.ActivityVideoCommentService;
import com.bluemobi.www.security.activity.service.ActivityVideoInfoService;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: ActivityVideoCommentController 
* @Description: 活动视频评论控制层 
* @author sundq 
* @date 2016-02-14 14:18:20 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class ActivityVideoCommentController extends BaseController {
	@Resource
	private ActivityVideoCommentService service;
	@Resource
	private ActivityVideoInfoService infoService;

	@RequestMapping(value = "activity/activityVideoCommentList")
	public String activityVideoCommentList(HttpServletRequest request,
			HttpServletResponse response, String videoInfoId) {
		request.setAttribute("videoInfoId", videoInfoId);
		return "activity/activity_video_comment_list";
	}

	@RequestMapping(value = "activity/activityVideoCommentAjaxPage")
	@ResponseBody
	public PageInfo<ActivityVideoComment> activityVideoCommentAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ActivityVideoComment info, Integer page,
			Integer rows) {
		PageInfo<ActivityVideoComment> pageInfo = new PageInfo<ActivityVideoComment>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "activity/activityVideoCommentAjaxAll")
	@ResponseBody
	public List<ActivityVideoComment> activityVideoCommentAjaxAll(HttpServletRequest request,
			HttpServletResponse response, ActivityVideoComment info, Integer page,
			Integer rows) {
		List<ActivityVideoComment> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "activity/activityVideoCommentAjaxSave")
	@ResponseBody
	public Map<String,Object> activityVideoCommentAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ActivityVideoComment info) {
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

	@RequestMapping(value = "activity/activityVideoCommentAjaxDelete")
	@ResponseBody
	public Map<String,Object> activityVideoCommentAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ActivityVideoComment info) {
		int result = 0;
		try {
			info = service.selectById(info.getId());
			ActivityVideoInfo videoInfo = info.getActivityVideoInfo();
			videoInfo.setCommentNum(videoInfo.getCommentNum()-1);
			infoService.update(videoInfo);
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
