package com.bluemobi.www.security.member.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.constant.Constant;
import com.bluemobi.www.data.model.member.MemberFeedback;
import com.bluemobi.www.data.model.message.MessagePushNotification;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberFeedbackService;
import com.bluemobi.www.security.message.service.MessagePushNotificationService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: MemberFeedbackController 
* @Description: 用户反馈控制层 
* @author sundq 
* @date 2016-02-14 10:27:14 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class MemberFeedbackController extends BaseController {
	@Resource
	private MemberFeedbackService service;
	@Resource
	private MessagePushNotificationService notificationService;

	@RequestMapping(value = "member/memberFeedbackList")
	public String memberFeedbackList(HttpServletRequest request,
			HttpServletResponse response) {
		return "member/member_feedback_list";
	}

	@RequestMapping(value = "member/memberFeedbackAjaxPage")
	@ResponseBody
	public PageInfo<MemberFeedback> memberFeedbackAjaxPage(HttpServletRequest request,
			HttpServletResponse response, MemberFeedback info, Integer page,
			Integer rows) {
		PageInfo<MemberFeedback> pageInfo = new PageInfo<MemberFeedback>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		if(StringUtils.isNotBlank(info.getMemberName())){
			info.setMemberName(queryLikeParamHandler(info.getMemberName()));
		}
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "member/memberFeedbackAjaxAll")
	@ResponseBody
	public List<MemberFeedback> memberFeedbackAjaxAll(HttpServletRequest request,
			HttpServletResponse response, MemberFeedback info, Integer page,
			Integer rows) {
		List<MemberFeedback> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "member/memberFeedbackAjaxSave")
	@ResponseBody
	public Map<String,Object> memberFeedbackAjaxSave(HttpServletRequest request,
			HttpServletResponse response, MemberFeedback info) {
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

	@RequestMapping(value = "member/memberFeedbackAjaxDelete")
	@ResponseBody
	public Map<String,Object> memberFeedbackAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, MemberFeedback info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
	
	@RequestMapping(value = "member/memberFeedbackReply")
	@ResponseBody
	public Map<String,Object> memberFeedbackReply(HttpServletRequest request,
			HttpServletResponse response, MemberFeedback info) {
		int result = 0;
		try {
			String reply = info.getReply();
			info = service.selectById(info.getId());
			info.setStatus("1");
			result = service.update(info);
			//添加推送
			MessagePushNotification notification = new MessagePushNotification();
			notification.setId(UUIDUtil.getUUID());
			notification.setType(Constant.PUSH_REPLY);
			notification.setTitle(Constant.PUSH_REPLY_TITLE);
			notification.setReceiveId(info.getMemberId());
			notification.setContent(reply);
			notification.setCreateDate(DateUtils.currentStringDate());
			notification.setStatus("1");
			notification.setCid(info.getMemberInfo().getCid());
			notificationService.insert(notification);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
