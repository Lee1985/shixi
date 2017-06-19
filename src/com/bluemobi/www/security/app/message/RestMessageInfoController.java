package com.bluemobi.www.security.app.message;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.message.MessageOfficialNotice;
import com.bluemobi.www.data.model.message.MessagePrivateLetter;
import com.bluemobi.www.data.model.message.MessagePrivateMessageRecent;
import com.bluemobi.www.data.model.message.MessagePushNotification;
import com.bluemobi.www.data.model.recruit.RecruitApplyInfo;
import com.bluemobi.www.data.model.recruit.RecruitInfo;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfo;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberInfoService;
import com.bluemobi.www.security.message.service.MessageOfficialNoticeService;
import com.bluemobi.www.security.message.service.MessagePrivateLetterService;
import com.bluemobi.www.security.message.service.MessagePrivateMessageRecentService;
import com.bluemobi.www.security.message.service.MessagePushNotificationService;
import com.bluemobi.www.security.recruit.service.RecruitApplyInfoService;
import com.bluemobi.www.security.recruit.service.RecruitInfoService;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoService;
import com.bluemobi.www.security.system.service.SystemPictureInfoService;
import com.bluemobi.www.utils.PushUtils;
import com.bluemobi.www.utils.UUIDUtil;
import com.bluemobi.www.utils.compare.DateComparator;

/**
 * 
 * @author sundq
 * 消息模块接口
 */
@Controller
public class RestMessageInfoController extends BaseController{
	
	@Resource
	private MessageOfficialNoticeService noticeService;
	@Resource
	private MessagePrivateLetterService letterService;	
	@Resource
	private MessagePrivateMessageRecentService messagePrivateMessageRecentService;
	@Resource
	private MemberInfoService memberInfoService;
	@Resource
	private RecruitRoleInfoService recruitRoleInfoService;
	@Resource
	private RecruitInfoService recruitInfoService;
	@Resource
	private SystemPictureInfoService systemPictureInfoService;
	@Resource
	private RecruitApplyInfoService recruitApplyInfoService;
	@Resource
	private MessagePushNotificationService messagePushNotificationService;
	
	/**
	 * 官方通知列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "rest/message/officialNoticeList")
	@ResponseBody
	public Map<String,Object> officialNoticeList(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("status", "1");
		condition.put("sort", "createDate");
		condition.put("order", "desc");
		List<MessageOfficialNotice> list = noticeService.selectAll(condition);
		if(list != null && !list.isEmpty()){
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); 
			for(MessageOfficialNotice notice : list){
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.put("id", notice.getId());
				resultMap.put("title", notice.getTitle());
				resultMap.put("description", notice.getDescription());
				resultMap.put("urlPath", getBasePath(request) + notice.getPictureInfo().getUrlPath());
				resultMap.put("createDate", notice.getCreateDate());
				resultList.add(resultMap);
			}
			map.put("success", "yes");
			map.put("data", resultList);
		}else{
			map.put("success", "no");
			map.put("message", "无结果!");
		}
		return map;
	}
			
	/**
	 * 官方通知详情
	 * @param request
	 * @param response
	 * @param noticeId
	 * @return
	 */
	@RequestMapping(value = "rest/message/officialNoticeById")
	@ResponseBody
	public Map<String,Object> officialNoticeById(HttpServletRequest request,HttpServletResponse response,
			String noticeId){
		Map<String,Object> map = new HashMap<String,Object>();
		MessageOfficialNotice notice = noticeService.selectById(noticeId);
		if(notice != null){
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("id", notice.getId());
			resultMap.put("title", notice.getTitle());
			resultMap.put("description", notice.getDescription());
			resultMap.put("urlPath", getBasePath(request) + notice.getPictureInfo().getUrlPath());
			resultMap.put("createDate", notice.getCreateDate());
			resultMap.put("content", notice.getContent());
			map.put("success", "yes");
			map.put("data", resultMap);
		}else{
			map.put("success", "no");
			map.put("message", "无结果!");
		}
		return map;
	}
	
	
	/**
	 * 推送列表(分页)
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "rest/message/pushNotificationList")
	@ResponseBody
	public Map<String,Object> pushNotificationList(HttpServletRequest request,HttpServletResponse response,
			MessagePushNotification info, Integer page, Integer rows){
		Map<String,Object> map = new HashMap<String,Object>();
		//传分页信息page页数
		PageInfo<MessagePushNotification> pageInfo = new PageInfo<MessagePushNotification>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setStatus("1");
		info.setSort("createDate");
		info.setOrder("desc");
		messagePushNotificationService.selectAll(info, pageInfo);
		map.put("totle", pageInfo.getTotal());
		map.put("page", pageInfo.getPage());
		map.put("pageSize", pageInfo.getPageSize());
		if(pageInfo.getRows() == null || pageInfo.getRows().size()<=0){
			map.put("success", "yes");
			map.put("message", "无结果!");
		}else{
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); 
			for(MessagePushNotification notification : pageInfo.getRows()){
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.put("id", notification.getId());
				resultMap.put("title", decodeParam(notification.getTitle()));
				resultMap.put("content", decodeParam(notification.getContent()));
				resultMap.put("createDate", notification.getCreateDate());
				resultList.add(resultMap);
			}
			map.put("success", "yes");
			map.put("data", resultList);
			
		}
		return map;
	}
	
	/**
	 * 推送列表删除
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "rest/message/deletePushNotificationById")
	@ResponseBody
	public Map<String,Object> deletePushNotificationById(HttpServletRequest request,HttpServletResponse response,
			MessagePushNotification info){
		Map<String,Object> map = new HashMap<String,Object>();
		int result = 0;
		result = messagePushNotificationService.delete(info);
		if (result > 0) {
			map.put("success", "yes");
			map.put("message", "操作成功");
		} else {
			map.put("success", "no");
			map.put("message", "操作失败");
		}
		return map;
	}
	
	
	
	
	/**
	 * 私信最近联系人列表
	 * @param request
	 * @param response
	 * @param memberId
	 * @param type 1-官方 2-私人
	 * @return
	 */
	@RequestMapping(value = "rest/message/privateMessageRecentList")
	@ResponseBody
	public Map<String,Object> privateMessageRecentList(HttpServletRequest request,HttpServletResponse response,
			String memberId,String type,Integer page,Integer rows){
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isBlank(memberId)){
			map.put("success", "no");
			map.put("message", "找不到用户信息!");
			return map;
		}
		Map<String,Object> messageRecentParams = new HashMap<String,Object>();
		messageRecentParams.put("memberId", memberId);
		messageRecentParams.put("identityType", type);
		messageRecentParams.put("order", "desc");
		messageRecentParams.put("sort", "editTime");						
		PageInfo<MessagePrivateMessageRecent> recentPage = new PageInfo<MessagePrivateMessageRecent>();
		recentPage.setPage(page);
		recentPage.setPageSize(rows);
		recentPage = messagePrivateMessageRecentService.selectByType(messageRecentParams,recentPage);
		List<MessagePrivateMessageRecent> letterRecentList = recentPage.getRows();		
		List<Map<String,Object>> contactList = new ArrayList<Map<String,Object>>();
		if(letterRecentList == null || letterRecentList.isEmpty()){
			map.put("success", "yes");
			map.put("message", "找不到最近联系人信息!");
			map.put("recentlyContacts", contactList);
			return map;
		}		
		List<String> contactIdList = new ArrayList<String>();
		for(MessagePrivateMessageRecent messageRecent : letterRecentList){
			contactIdList.add(messageRecent.getRencentContactId());			
			if(memberId.equals(messageRecent.getMemberId())){
				contactIdList.add(messageRecent.getRencentContactId());
			}else if(memberId.equals(messageRecent.getRencentContactId())){
				contactIdList.add(messageRecent.getMemberId());
			}
		}
		//查询最近联系用户列表
		List<MemberInfo> memberList = memberInfoService.selectByIdsWithImage(contactIdList);
		if(memberList == null || memberList.isEmpty()){
			map.put("success", "yes");
			map.put("message", "找不到最近联系人信息!");
			map.put("recentlyContacts", contactList);
			return map;
		}
				
		//查询当前用户的未读私信
		Map<String,Object> unreadParams = new HashMap<String,Object>();
		unreadParams.put("receiveId", memberId);
		unreadParams.put("status", "0");
		List<Map<String,Object>> unReadList = letterService.selectUnreadInfo(unreadParams);
		Map<String,String> unReadMap = new HashMap<String,String>();
		if(unReadList != null && !unReadList.isEmpty()){
			for(Map<String,Object> mapInfo : unReadList){
				String recentlyId = (String)mapInfo.get("recentlyId");
				Long unReadCount = (Long)mapInfo.get("unreadCount");
				unReadMap.put(recentlyId, String.valueOf(unReadCount));
			} 
		}						
		boolean noUnread = false;
		if(unReadMap == null || unReadMap.isEmpty()){
			noUnread = true;
		}
		
		Map<String,MemberInfo> memberMap = new HashMap<String,MemberInfo>();
		for(MemberInfo memberInfo : memberList){
			memberMap.put(memberInfo.getId(), memberInfo);
		}		
		for(MessagePrivateMessageRecent messageRecent : letterRecentList){
			Map<String,Object> recentMessageMap = new HashMap<String,Object>();
				
			MemberInfo memberInfo = null;
			if(memberId.equals(messageRecent.getMemberId())){
				memberInfo =  memberMap.get(messageRecent.getRencentContactId());
			}else if(memberId.equals(messageRecent.getRencentContactId())){
				memberInfo =  memberMap.get(messageRecent.getMemberId());
			}
						
			recentMessageMap.put("contactId", memberInfo.getId() == null ? "" : memberInfo.getId());
			recentMessageMap.put("headUrl", memberInfo.getHeadImageUrl() == null ? "" : getBasePath(request) + memberInfo.getHeadImageUrl());
			recentMessageMap.put("nickname", memberInfo.getNickname() == null ? "" : decodeParam(memberInfo.getNickname()));
			recentMessageMap.put("idenityStatus", memberInfo.getStatus() == null ? "" : memberInfo.getStatus());
			recentMessageMap.put("vipStatus", memberInfo.getVip() == null ? "" : memberInfo.getVip());
			recentMessageMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
			recentMessageMap.put("recentlyContent", messageRecent.getRecentContent() == null ? "" : decodeParam(messageRecent.getRecentContent()));			
			recentMessageMap.put("recentRoleId", messageRecent.getRecentRoleId() == null ? "" : messageRecent.getRecentRoleId());
			recentMessageMap.put("applyMemberId", messageRecent.getRencentContactId() == null ? "" : messageRecent.getRencentContactId());
			if(noUnread){								
				recentMessageMap.put("unreadMessagesCount", "0");				
			}else{
				String unreadCount = unReadMap.get(messageRecent.getId());
				if(StringUtils.isBlank(unreadCount)){
					recentMessageMap.put("unreadMessagesCount", "0");
				}else{
					recentMessageMap.put("unreadMessagesCount", unreadCount);
				}
			}
			recentMessageMap.put("editTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(messageRecent.getEditTime()));
			contactList.add(recentMessageMap);
		}
		map.put("success", "yes");
		map.put("recentlyContacts", contactList);
		return map;
	}
	
	@RequestMapping(value = "rest/message/privateLetters")
	@ResponseBody
	public Map<String,Object> privateLetters(HttpServletRequest request,HttpServletResponse response,
			String memberId,String contactMemberId,String roleId,Integer page,Integer rows){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> roleMap = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(roleId)){
			RecruitRoleInfo recruitRoleInfo = recruitRoleInfoService.selectById(roleId);			
			if(recruitRoleInfo == null){
				map.put("success", "no");
				map.put("message", "找不到角色信息!");
				return map;
			}									
			SystemPictureInfo picInfo = systemPictureInfoService.selectByUuid(recruitRoleInfo.getImgUuid());
			if(picInfo != null){
				String urlPath = picInfo.getUrlPath();
				roleMap.put("roleImageUrl", getBasePath(request) + urlPath);
			}else{
				roleMap.put("roleImageUrl", "");
			}	
			RecruitInfo recruitInfo = recruitInfoService.selectById(recruitRoleInfo.getRecruitId());
			if(recruitInfo == null){
				map.put("success", "no");
				map.put("message", "找不到招募信息!");
				return map;
			}
			//roleMap.put("publisherId", recruitInfo.getMemberId() == null ? "" : recruitInfo.getMemberId());
			roleMap.put("roleName", recruitRoleInfo.getName() == null ? "" : decodeParam(recruitRoleInfo.getName()));
			String paidMin = recruitRoleInfo.getPaidMin() == null ? "?" : recruitRoleInfo.getPaidMin();
			String paidMax = recruitRoleInfo.getPaidMax() == null ? "?" : recruitRoleInfo.getPaidMax();
			String paidType = recruitRoleInfo.getPaidType() == null ? "?" : recruitRoleInfo.getPaidType();
			roleMap.put("paidType",recruitRoleInfo.getPaidType());//
			roleMap.put("paidMax",recruitRoleInfo.getPaidMax());//
			roleMap.put("paidMin",recruitRoleInfo.getPaidMin());//
			roleMap.put("paid", paidMin + "-" + paidMax + "" + paidType);
			roleMap.put("crewName", recruitInfo.getTitle() == null ? "" : decodeParam(recruitInfo.getTitle()));
			roleMap.put("publishMember",recruitInfo.getMemberId());//
			roleMap.put("roleStatus",recruitRoleInfo.getIsDelete());//
			roleMap.put("recruitStatus",recruitInfo.getIsDelete());//
		}
		if(!roleMap.isEmpty()){
			map.put("roleInfo", roleMap);
		}
		map.putAll(reloadLetters(request, memberId, contactMemberId, page, rows));
		return map;
	}
	
	@RequestMapping(value = "rest/message/reloadNewLetters")
	@ResponseBody
	public Map<String,Object> reloadNewLetters(HttpServletRequest request,HttpServletResponse response,
			String memberId,String contactMemberId,Integer page,Integer rows){
		return reloadLetters(request,memberId,contactMemberId,page,rows);		
	}
	
	private Map<String,Object> reloadLetters(HttpServletRequest request,
			String memberId,String contactMemberId,Integer page,Integer rows){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("memberId", memberId);
		params.put("contactId", contactMemberId);
		params.put("sort", "sendDate");
		params.put("order", "desc");
		PageInfo<MessagePrivateLetter> letterPage = new PageInfo<MessagePrivateLetter>();
		letterPage.setPage(page);
		letterPage.setPageSize(rows);
		letterPage = letterService.selectPrivateRecords(params,letterPage);
		List<MessagePrivateLetter> letterList = letterPage.getRows();
		List<Map<String,Object>> resultLetterList = new ArrayList<Map<String,Object>>();
		String recentlyId = "";
		if(letterList != null && !letterList.isEmpty()){
			recentlyId = letterList.get(0).getRecentlyId();
			MemberInfo memberInfo = memberInfoService.selectByIdWithImage(memberId);
			if(memberInfo == null){
				map.put("success", "no");
				map.put("message", "找不到用户信息!");
				return map;
			}
			MemberInfo contactMemberInfo = memberInfoService.selectByIdWithImage(contactMemberId);
			if(contactMemberInfo == null){
				map.put("success", "no");
				map.put("message", "找不到用户信息!");
				return map;
			}
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
				messageMap.put("senderMemberId", senderNickName == null ? "" : senderNickName);
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
			
			//查询未读消息
			Map<String,Object> unreadParams = new HashMap<String,Object>();
			unreadParams.put("receiveId", memberId);
			unreadParams.put("senderId", contactMemberId);
			unreadParams.put("status", "0");
			List<MessagePrivateLetter> updateList = letterService.selectAll(unreadParams);
			if(updateList != null && !updateList.isEmpty()){
				for(MessagePrivateLetter messagePrivateLetter : updateList){
					messagePrivateLetter.setStatus("1");
					letterService.update(messagePrivateLetter);
				}
			}
		}
		Collections.sort(resultLetterList, new DateComparator());
		map.put("success", "yes");
		map.put("recentlyId", recentlyId);
		map.put("messageRecords",resultLetterList);
		return map;		
	}
	
	@RequestMapping(value = "rest/message/sendLetter")
	@ResponseBody
	public Map<String,Object> sendLetter(HttpServletRequest request,HttpServletResponse response,
			String memberId,String contactId,String recentlyId,String roleId,String sendContent,String type) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isBlank(memberId)){
			map.put("success", "no");
			map.put("message", "找不到用户信息!");
			return map;
		}
		MemberInfo memberInfo = memberInfoService.selectById(memberId);
		if(memberInfo == null){
			map.put("success", "no");
			map.put("message", "找不到用户信息!");
			return map;
		}
		if(StringUtils.isBlank(sendContent)){
			map.put("success", "no");
			map.put("message", "不能发送空消息!");
			return map;
		}
		sendContent = URLEncoder.encode(sendContent,"utf-8");
		int result = 0;
		MessagePrivateMessageRecent messagePrivateMessageRecent = null;
		if(StringUtils.isBlank(recentlyId)){
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("memberId", memberId);
			condition.put("rencentContactId", contactId);
			MessagePrivateMessageRecent recent = messagePrivateMessageRecentService.selectEntity(condition);
			if(recent != null){
				recentlyId = recent.getId();
			}else{
				condition.put("memberId", contactId);
				condition.put("rencentContactId", memberId);
				recent = messagePrivateMessageRecentService.selectEntity(condition);
				if(recent != null){
					recentlyId = recent.getId();
				}
			}
		}
		if(StringUtils.isBlank(recentlyId)){
			//如果不存在recentlyId,则招募者简历为入口										
			messagePrivateMessageRecent = new MessagePrivateMessageRecent();
			messagePrivateMessageRecent.setId(UUIDUtil.getUUID());
			messagePrivateMessageRecent.setMemberId(memberId);
			messagePrivateMessageRecent.setRencentContactId(contactId);
			messagePrivateMessageRecent.setRecentContent(sendContent);
			messagePrivateMessageRecent.setReadStatus("0");			
			messagePrivateMessageRecent.setUnreadMessageCount("0");			
			messagePrivateMessageRecent.setEditTime(new Date());
			messagePrivateMessageRecent.setRecentRoleId(roleId);
			String identity = memberInfo.getIdentityStatus();
			if("1".equals(identity)){
				type = "1";
			}else{
				type = "2";
			}
			messagePrivateMessageRecent.setType(type);
			result = messagePrivateMessageRecentService.insert(messagePrivateMessageRecent);						
		}else{
			messagePrivateMessageRecent = messagePrivateMessageRecentService.selectById(recentlyId);
			if(messagePrivateMessageRecent == null){
				messagePrivateMessageRecent = new MessagePrivateMessageRecent();
				messagePrivateMessageRecent.setId(recentlyId);
				messagePrivateMessageRecent.setMemberId(memberId);
				messagePrivateMessageRecent.setRencentContactId(contactId);
				messagePrivateMessageRecent.setRecentContent(sendContent);
				messagePrivateMessageRecent.setReadStatus("0");			
				messagePrivateMessageRecent.setUnreadMessageCount("0");			
				messagePrivateMessageRecent.setEditTime(new Date());
				messagePrivateMessageRecent.setType(type);
				messagePrivateMessageRecent.setRecentRoleId(roleId);
				result = messagePrivateMessageRecentService.insert(messagePrivateMessageRecent);
			}else{
				messagePrivateMessageRecent.setRecentContent(sendContent);
				messagePrivateMessageRecent.setReadStatus("0");
				int count = Integer.parseInt(messagePrivateMessageRecent.getUnreadMessageCount());
				messagePrivateMessageRecent.setUnreadMessageCount(String.valueOf(count+1));
				messagePrivateMessageRecent.setEditTime(new Date());
				messagePrivateMessageRecent.setRecentRoleId(roleId);
				result = messagePrivateMessageRecentService.update(messagePrivateMessageRecent);
			}
		}
		if(result > 0){
			MessagePrivateLetter messagePrivateLetter = new MessagePrivateLetter();
			messagePrivateLetter.setId(UUIDUtil.getUUID());
			messagePrivateLetter.setSenderId(memberId);
			messagePrivateLetter.setReceiveId(contactId);
			messagePrivateLetter.setType(messagePrivateMessageRecent.getType());
			messagePrivateLetter.setSendContent(sendContent);
			messagePrivateLetter.setSendDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			messagePrivateLetter.setStatus("0");
			messagePrivateLetter.setRecentlyId(messagePrivateMessageRecent.getId());
			messagePrivateLetter.setRoleId(roleId);
			result = letterService.insert(messagePrivateLetter);
		}
		
		//查询最近联系人,如果有该条记录,说明该剧组已经联系了他,修改时间轴为"剧组联系了你"
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("memberId", contactId);
		params.put("roleId", roleId);
		RecruitApplyInfo applyInfo = recruitApplyInfoService.selectEntity(params);
		if(applyInfo != null && !"5".equals(applyInfo.getLevel())){
			applyInfo.setLevel("5");
			recruitApplyInfoService.update(applyInfo);
		}
		
		if(result > 0){
			//给接收人发送推送消息
			MemberInfo contactMember = memberInfoService.selectById(contactId);
			PushUtils.pushToSingle(contactMember.getCid(), decodeParam(memberInfo.getNickname()) + "给您发送了一条私信",decodeParam(memberInfo.getNickname()) + ":" + decodeParam(sendContent), "type=1");
			map.put("success", "yes");
			map.put("message", "发送私信成功!");
		}else{
			map.put("success", "no");
			map.put("message", "发送私信失败!");
		}		
		return map;
	}
	
	@RequestMapping(value = "rest/message/listCount")
	@ResponseBody
	public Map<String,Object> listCount(HttpServletRequest request,HttpServletResponse response,
			String memberId) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isBlank(memberId)){
			map.put("success", "no");
			map.put("message", "找不到用户信息!");
			return map;
		}
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("receiveId", memberId);
		condition.put("status", "0");
		condition.put("type", "1");
		int officialCount = letterService.selectCount(condition);
		data.put("officialCount", officialCount);
		condition.put("type", "2");
		int privateCount = letterService.selectCount(condition);
		data.put("privateCount", privateCount);
		map.put("success", "yes");
		map.put("message", "未读数量");
		map.put("data", data);
		return map;
	}
	
	@RequestMapping(value = "rest/message/unReadCount")
	@ResponseBody
	public Map<String,Object> unReadCount(HttpServletRequest request,HttpServletResponse response,
			String memberId) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> unreadParams = new HashMap<String,Object>();
		unreadParams.put("receiveId", memberId);
		unreadParams.put("status", "0");
		int unReadCount = letterService.selectCount(unreadParams);
		map.put("unReadCount", unReadCount);
		map.put("success", "yes");
		map.put("message", "未读数量");
		return map; 
	}
}