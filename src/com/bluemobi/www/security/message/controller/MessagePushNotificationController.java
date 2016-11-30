package com.bluemobi.www.security.message.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.message.MessagePushNotification;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.message.service.MessagePushNotificationService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: MessagePushNotificationController 
* @Description: 推送消息控制层 
* @author sundq 
* @date 2016-02-19 13:54:57 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class MessagePushNotificationController extends BaseController {
	@Resource
	private MessagePushNotificationService service;

	@RequestMapping(value = "message/messagePushNotificationList")
	public String messagePushNotificationList(HttpServletRequest request,
			HttpServletResponse response) {
		return "message/message_push_notification_list";
	}

	@RequestMapping(value = "message/messagePushNotificationAjaxPage")
	@ResponseBody
	public PageInfo<MessagePushNotification> messagePushNotificationAjaxPage(HttpServletRequest request,
			HttpServletResponse response, MessagePushNotification info, Integer page,
			Integer rows) {
		PageInfo<MessagePushNotification> pageInfo = new PageInfo<MessagePushNotification>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setSort("createDate");
		info.setOrder("desc");
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "message/messagePushNotificationAjaxAll")
	@ResponseBody
	public List<MessagePushNotification> messagePushNotificationAjaxAll(HttpServletRequest request,
			HttpServletResponse response, MessagePushNotification info, Integer page,
			Integer rows) {
		List<MessagePushNotification> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "message/messagePushNotificationAjaxSave")
	@ResponseBody
	public Map<String,Object> messagePushNotificationAjaxSave(HttpServletRequest request,
			HttpServletResponse response, MessagePushNotification info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			info.setCreateDate(DateUtils.currentStringDate());
			result = service.insert(info);
			msg = "保存失败！";
		} else {
			result = service.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "message/messagePushNotificationAjaxDelete")
	@ResponseBody
	public Map<String,Object> messagePushNotificationAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, MessagePushNotification info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
