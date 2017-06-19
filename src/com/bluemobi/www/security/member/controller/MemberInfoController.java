package com.bluemobi.www.security.member.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.constant.Constant;
import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.message.MessagePushNotification;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberInfoService;
import com.bluemobi.www.security.message.service.MessagePushNotificationService;
import com.bluemobi.www.security.recruit.service.RecruitInfoService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: MemberInfoController 
* @Description: 用户管理控制层 
* @author sundq 
* @date 2016-02-04 16:47:31 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class MemberInfoController extends BaseController {
	
	@Resource
	private MemberInfoService service;
	@Resource
	private MessagePushNotificationService notificationService;
	@Resource
	private RecruitInfoService infoService;
	
	
	@RequestMapping(value = "member/memberInfoList")
	public String memberInfoList(HttpServletRequest request,
			HttpServletResponse response, String type) {
		if(type != null){
			if(type.equals("1")){
				return "member/member_info_list_sf";
			}else if(type.equals("2")){
				return "member/member_info_list_xm";
			}else if(type.equals("3")){
				return "member/member_info_list_xl";
			}else if(type.equals("4")){
				return "member/member_info_list_hy";
			}
		}
		return "member/member_info_list";
	}

	@RequestMapping(value = "member/memberInfoAjaxPage")
	@ResponseBody
	public PageInfo<MemberInfo> memberInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, MemberInfo info, Integer page,
			Integer rows) {
		PageInfo<MemberInfo> pageInfo = new PageInfo<MemberInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		if(StringUtils.isNotBlank(info.getNickname())){
			info.setNickname(queryLikeParamHandler(info.getNickname()));
		}
		if(StringUtils.isNotBlank(info.getRealName())){
			info.setRealName(queryLikeParamHandler(info.getRealName()));
		}
		service.selectAll(info, pageInfo);
		List<MemberInfo> memberList = pageInfo.getRows();
		if(memberList != null){
			for(MemberInfo memberInfo : memberList){
				memberInfo.setNickname(decodeParam(memberInfo.getNickname()));
				memberInfo.setRealName(decodeParam(memberInfo.getRealName()));
			}
		}
		return pageInfo;
	}

	@RequestMapping(value = "member/memberInfoAjaxAll")
	@ResponseBody
	public List<MemberInfo> memberInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, MemberInfo info, Integer page,
			Integer rows) {
		List<MemberInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "member/memberInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> memberInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, MemberInfo info) {
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

	@RequestMapping(value = "member/memberInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> memberInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, MemberInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}

	@RequestMapping(value = "member/memberInfoUpdateIdentity")
	@ResponseBody
	public Map<String,Object> memberInfoUpdateIdentity(HttpServletRequest request,
			HttpServletResponse response, MemberInfo info) {
		int result = 0;
		try {
			if(!info.getIds().isEmpty()){
				String[] ids = info.getIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					info.setId(ids[i]);
					result = service.update(info);
					MemberInfo memberInfo = service.selectById(ids[i]);
					Map<String, Object> condition = new HashMap<String, Object>();
					condition.put("memberId", info.getId());
					if(info.getIdentityStatus().equals("1")){
						condition.put("type", "1");
					}else{
						condition.put("type", "2");
					}
					infoService.batchUpdateByMemberId(condition);
					MessagePushNotification notification = new MessagePushNotification();
					notification.setId(UUIDUtil.getUUID());
					notification.setTitle("身份认证");
					notification.setType(Constant.PUSH_IDENTITY);
					notification.setReceiveId(memberInfo.getId());
					notification.setContent(info.getReply());
					notification.setCreateDate(DateUtils.currentStringDate());
					notification.setStatus("1");
					notification.setCid(memberInfo.getCid());
					if(memberInfo.getIdentityStatus().equals("1") && Constant.ISPASS){
						notificationService.insert(notification);
					}
					if(memberInfo.getIdentityStatus().equals("2") && Constant.NOTPASS){
						notificationService.insert(notification);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
	
	@RequestMapping(value = "member/memberInfoUpdateRealName")
	@ResponseBody
	public Map<String,Object> memberInfoUpdateRealName(HttpServletRequest request,
			HttpServletResponse response, MemberInfo info) {
		int result = 0;
		try {
			if(!info.getIds().isEmpty()){
				String[] ids = info.getIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					info.setId(ids[i]);
					result = service.update(info);
					MemberInfo memberInfo = service.selectById(ids[i]);
					MessagePushNotification notification = new MessagePushNotification();
					notification.setId(UUIDUtil.getUUID());
					notification.setTitle("实名认证");
					notification.setType(Constant.PUSH_REALNAME);
					notification.setReceiveId(memberInfo.getId());
					notification.setContent(info.getReply());
					notification.setCreateDate(DateUtils.currentStringDate());
					notification.setStatus("1");
					notification.setCid(memberInfo.getCid());
					if(memberInfo.getRealNameStatus().equals("1") && Constant.ISPASS){
						notificationService.insert(notification);
					}
					if(memberInfo.getRealNameStatus().equals("2") && Constant.NOTPASS){
						notificationService.insert(notification);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
	
	@RequestMapping(value = "member/memberInfoUpdateEducation")
	@ResponseBody
	public Map<String,Object> memberInfoUpdateEducation(HttpServletRequest request,
			HttpServletResponse response, MemberInfo info) {
		int result = 0;
		try {
			if(!info.getIds().isEmpty()){
				String[] ids = info.getIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					info.setId(ids[i]);
					result = service.update(info);
					MemberInfo memberInfo = service.selectById(info.getId());
					MessagePushNotification notification = new MessagePushNotification();
					notification.setId(UUIDUtil.getUUID());
					notification.setTitle("学历认证");
					notification.setType(Constant.PUSH_EDUCATION);
					notification.setReceiveId(memberInfo.getId());
					notification.setContent(info.getReply());
					notification.setCreateDate(DateUtils.currentStringDate());
					notification.setStatus("1");
					notification.setCid(memberInfo.getCid());
					if(memberInfo.getEducationStatus().equals("1") && Constant.ISPASS){
						notificationService.insert(notification);
					}
					if(memberInfo.getEducationStatus().equals("2") && Constant.NOTPASS){
						notificationService.insert(notification);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
	
	
	@RequestMapping(value = "member/memberInfoUpdateVip")
	@ResponseBody
	public Map<String,Object> memberInfoUpdateVip(HttpServletRequest request,
			HttpServletResponse response, MemberInfo info) {
		int result = 0;
		try {
			if(!info.getIds().isEmpty()){
				String[] ids = info.getIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					info.setId(ids[i]);
					result = service.update(info);
					MemberInfo memberInfo = service.selectById(info.getId());
					MessagePushNotification notification = new MessagePushNotification();
					notification.setId(UUIDUtil.getUUID());
					notification.setTitle("会员认证");
					notification.setType(Constant.PUSH_VIP);
					notification.setReceiveId(memberInfo.getId());
					notification.setContent(info.getReply());
					notification.setCreateDate(DateUtils.currentStringDate());
					notification.setStatus("1");
					notification.setCid(memberInfo.getCid());
					if(memberInfo.getVip().equals("1") && Constant.ISPASS){
						notificationService.insert(notification);
					}
					if(memberInfo.getVip().equals("2") && Constant.NOTPASS){
						notificationService.insert(notification);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
	
	@RequestMapping(value = "member/memberInfoById")
	public String memberInfoById(HttpServletRequest request,
			HttpServletResponse response,Model model, String id) {
		MemberInfo info = new MemberInfo();
		if(id != null && !id.equals("")){
			info = service.selectById(id);
			info.setNickname(decodeParam(info.getNickname()));
			info.setRealName(decodeParam(info.getRealName()));
		}
		model.addAttribute("entity", info);
		return "member/member_info_detail";
	}
	
	
	
	@RequestMapping(value = "member/memberChooseList")
	public String memberChooseList(HttpServletRequest request,
			HttpServletResponse response) {
		return "member/member_choose_list";
	}

	@RequestMapping(value = "member/memberChooseAjaxPage")
	@ResponseBody
	public PageInfo<MemberInfo> memberChooseAjaxPage(HttpServletRequest request,
			HttpServletResponse response, MemberInfo info, Integer page,
			Integer rows) {
		PageInfo<MemberInfo> pageInfo = new PageInfo<MemberInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}
	
	
	@RequestMapping(value = "member/doUpdateStatus")
	@ResponseBody
	public Map<String,Object> doUpdateStatus(HttpServletRequest request,
			HttpServletResponse response, MemberInfo info) {
		int result = 0;
		try {
			result = service.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
}
