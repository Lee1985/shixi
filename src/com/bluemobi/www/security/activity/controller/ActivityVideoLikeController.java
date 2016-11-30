package com.bluemobi.www.security.activity.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.activity.ActivityVideoLike;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.activity.service.ActivityVideoLikeService;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: ActivityVideoLikeController 
* @Description: 点赞信息控制层 
* @author lip 
* @date 2016-03-15 16:29:37 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class ActivityVideoLikeController extends BaseController {
	@Resource
	private ActivityVideoLikeService service;

	@RequestMapping(value = "system/activityVideoLikeList")
	public String activityVideoLikeList(HttpServletRequest request,
			HttpServletResponse response) {
		return "system/activity_video_like_list";
	}

	@RequestMapping(value = "system/activityVideoLikeAjaxPage")
	@ResponseBody
	public PageInfo<ActivityVideoLike> activityVideoLikeAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ActivityVideoLike info, Integer page,
			Integer rows) {
		PageInfo<ActivityVideoLike> pageInfo = new PageInfo<ActivityVideoLike>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "system/activityVideoLikeAjaxAll")
	@ResponseBody
	public List<ActivityVideoLike> activityVideoLikeAjaxAll(HttpServletRequest request,
			HttpServletResponse response, ActivityVideoLike info, Integer page,
			Integer rows) {
		List<ActivityVideoLike> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/activityVideoLikeAjaxSave")
	@ResponseBody
	public Map<String,Object> activityVideoLikeAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ActivityVideoLike info) {
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

	@RequestMapping(value = "system/activityVideoLikeAjaxDelete")
	@ResponseBody
	public Map<String,Object> activityVideoLikeAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ActivityVideoLike info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
