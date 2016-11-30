package com.bluemobi.www.security.message.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.message.MessagePrivateLetter;
import com.bluemobi.www.data.model.message.MessagePrivateMessageRecent;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberInfoService;
import com.bluemobi.www.security.message.service.MessagePrivateLetterService;
import com.bluemobi.www.security.message.service.MessagePrivateMessageRecentService;
import com.bluemobi.www.utils.UUIDUtil;
import com.bluemobi.www.utils.compare.DateComparator;

/**
 * 
* @ClassName: MessagePrivateLetterController 
* @Description: 私信信息控制层 
* @author sundq 
* @date 2016-02-19 09:39:44 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class MessagePrivateLetterController extends BaseController {
	
	@Resource
	private MessagePrivateLetterService service;
	
	@Resource
	private MessagePrivateMessageRecentService messagePrivateMessageRecentService;
	
	@Resource
	private MemberInfoService memberInfoService;
	
	@RequestMapping(value = "message/messagePrivateLetterList")
	public String messagePrivateLetterList(HttpServletRequest request,
			HttpServletResponse response) {
		return "message/message_private_letter_list";
	}
	
	@RequestMapping(value = "message/messagePrivateLetterAjaxPage")
	@ResponseBody
	public PageInfo<MessagePrivateMessageRecent> messagePrivateLetterAjaxPage(HttpServletRequest request,
			HttpServletResponse response, MessagePrivateMessageRecent info, Integer page,
			Integer rows) {
		PageInfo<MessagePrivateMessageRecent> pageInfo = new PageInfo<MessagePrivateMessageRecent>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		messagePrivateMessageRecentService.selectPrivateMessageRecord(info, pageInfo);
		List<MessagePrivateMessageRecent> list = pageInfo.getRows();
		if(list != null && !list.isEmpty()){
			for(MessagePrivateMessageRecent messageInfo : list){
				Date recentContactDate = messageInfo.getRecentContactDate();
				String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(recentContactDate);
				messageInfo.setRecentContactDateStr(dateStr);
			}
		}		
		return pageInfo;
	}
	
	@RequestMapping(value = "message/contactUsers")
	public String contactUsers(HttpServletRequest request,HttpServletResponse response,String memberId) {		
		request.setAttribute("memberInfo", memberInfoService.selectById(memberId));
		return "message/message_private_contact_list";
	}
	
	@RequestMapping(value = "message/messagePrivateContactAjaxPage")
	@ResponseBody
	public PageInfo<MessagePrivateMessageRecent> messagePrivateContactAjaxPage(HttpServletRequest request,
			HttpServletResponse response, MessagePrivateMessageRecent info, Integer page,
			Integer rows) {
		PageInfo<MessagePrivateMessageRecent> pageInfo = new PageInfo<MessagePrivateMessageRecent>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		messagePrivateMessageRecentService.selectRecentContactRecord(info, pageInfo);
		List<MessagePrivateMessageRecent> list = pageInfo.getRows();
		if(list != null && !list.isEmpty()){
			for(MessagePrivateMessageRecent messageInfo : list){
				Date recentContactDate = messageInfo.getRecentContactDate();
				String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(recentContactDate);
				messageInfo.setRecentContactDateStr(dateStr);		
				String content = messageInfo.getRecentContent();
				try {
					messageInfo.setRecentContent(URLDecoder.decode(content,"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}		
		return pageInfo;
	}
	
	@RequestMapping(value = "message/letterRecords")
	public String letterRecords(HttpServletRequest request,HttpServletResponse response,String memberId,String contactMemberId) {
		//查询用户之间的对话记录
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("memberId", memberId);
		params.put("contactId", contactMemberId);
		PageInfo<MessagePrivateLetter> letterPage = new PageInfo<MessagePrivateLetter>();
		letterPage.setPage(1);
		letterPage.setPageSize(1000);
		letterPage = service.selectPrivateRecords(params,letterPage);
		List<MessagePrivateLetter> letterList = letterPage.getRows();
		List<Map<String,Object>> resultLetterList = new ArrayList<Map<String,Object>>();
		String memberImageUrl = "";
		String contactorImageUrl = "";
		if(letterList != null && !letterList.isEmpty()){
			MemberInfo memberInfo = memberInfoService.selectByIdWithImage(memberId);
			if(memberInfo == null){
				map.put("success", "no");
				map.put("message", "找不到用户信息!");
				request.setAttribute("errorMessage", map);
				return "message/message_private_letter_records";				
			}
			memberImageUrl = memberInfo.getHeadImageUrl();
			MemberInfo contactMemberInfo = memberInfoService.selectByIdWithImage(contactMemberId);
			if(contactMemberInfo == null){
				map.put("success", "no");
				map.put("message", "找不到用户信息!");
				request.setAttribute("errorMessage", map);
				return "message/message_private_letter_records";
			}
			contactorImageUrl = contactMemberInfo.getHeadImageUrl();
			Map<String,MemberInfo> memberMap = new HashMap<String,MemberInfo>();
			memberMap.put(memberId, memberInfo);
			memberMap.put(contactMemberId, contactMemberInfo);			
			for(MessagePrivateLetter messagePrivateLetter : letterList){				
				Map<String,Object> messageMap = new HashMap<String,Object>();
				MemberInfo senderInfo = memberMap.get(messagePrivateLetter.getSenderId());
				String senderNickName = senderInfo.getNickname();
				String senderImageUrl = senderInfo.getHeadImageUrl();
				String senderIdentityStatus = senderInfo.getVip();
				String realNameStatus = senderInfo.getRealNameStatus();
				messageMap.put("id", messagePrivateLetter.getId() == null ? "" : messagePrivateLetter.getId());
				messageMap.put("senderNickName", senderNickName == null ? "" : senderNickName);
				messageMap.put("senderImageUrl", senderImageUrl == null ? "" : getBasePath(request) + senderImageUrl);
				messageMap.put("senderIdentityStatus", senderIdentityStatus == null ? "" : senderIdentityStatus);
				messageMap.put("realNameStatus", realNameStatus == null ? "" : realNameStatus);
				try {
					messageMap.put("sendContent", messagePrivateLetter.getSendContent() == null ? "" :  URLDecoder.decode(messagePrivateLetter.getSendContent(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				messageMap.put("sendDate", messagePrivateLetter.getSendDate());	
				if(memberId.equals(messagePrivateLetter.getSenderId())){
					messageMap.put("sender", "1");
				}else{
					messageMap.put("sender", "0");
				}			
				resultLetterList.add(messageMap);
			}					
		}
		Collections.sort(resultLetterList, new DateComparator());
		map.put("memberImageUrl", memberImageUrl);
		map.put("contactorImageUrl", contactorImageUrl);
		map.put("messageRecords",resultLetterList);
		
		
		request.setAttribute("recordResult", map);
		return "message/message_private_letter_records";
	}
	
	@RequestMapping(value = "message/messagePrivateLetterAjaxAll")
	@ResponseBody
	public List<MessagePrivateLetter> messagePrivateLetterAjaxAll(HttpServletRequest request,
			HttpServletResponse response, MessagePrivateLetter info, Integer page,
			Integer rows) {
		List<MessagePrivateLetter> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "message/messagePrivateLetterAjaxSave")
	@ResponseBody
	public Map<String,Object> messagePrivateLetterAjaxSave(HttpServletRequest request,
			HttpServletResponse response, MessagePrivateLetter info) {
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
	
	@RequestMapping(value = "message/messagePrivateLetterAjaxDelete")
	@ResponseBody
	public Map<String,Object> messagePrivateLetterAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, MessagePrivateLetter info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
