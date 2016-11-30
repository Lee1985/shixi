package com.bluemobi.www.security.recruit.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.constant.Constant;
import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.message.MessagePushNotification;
import com.bluemobi.www.data.model.recruit.RecruitInfo;
import com.bluemobi.www.data.model.recruit.RecruitInfoTemp;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfo;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfoTemp;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberInfoService;
import com.bluemobi.www.security.message.service.MessagePushNotificationService;
import com.bluemobi.www.security.recruit.service.RecruitInfoService;
import com.bluemobi.www.security.recruit.service.RecruitInfoTempService;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoService;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoTempService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: RecruitInfoTempController 
* @Description: 招募修改信息控制层 
* @author sundq 
* @date 2016-03-08 13:17:03 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class RecruitInfoTempController extends BaseController {
	@Resource
	private RecruitInfoTempService service;
	@Resource
	private RecruitInfoService infoService;
	@Resource
	private RecruitRoleInfoTempService infoTempService;
	@Resource
	private RecruitRoleInfoService roleInfoService;
	@Resource
	private MessagePushNotificationService notificationService;
	@Resource
	private MemberInfoService memberInfoService;

	@RequestMapping(value = "recruit/recruitInfoTempList")
	public String recruitInfoTempList(HttpServletRequest request,
			HttpServletResponse response) {
		return "recruit/recruit_info_temp_list";
	}

	@RequestMapping(value = "recruit/recruitInfoTempAjaxPage")
	@ResponseBody
	public PageInfo<RecruitInfoTemp> recruitInfoTempAjaxPage(HttpServletRequest request,
			HttpServletResponse response, RecruitInfoTemp info, Integer page,
			Integer rows) {
		PageInfo<RecruitInfoTemp> pageInfo = new PageInfo<RecruitInfoTemp>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setIsDelete("0");
		if(info.getSort().equals("id")){
			info.setSort("r.createDate");
			info.setOrder("desc");
		}
		service.selectAllByStatus(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "recruit/recruitInfoTempAjaxAll")
	@ResponseBody
	public List<RecruitInfoTemp> recruitInfoTempAjaxAll(HttpServletRequest request,
			HttpServletResponse response, RecruitInfoTemp info, Integer page,
			Integer rows) {
		List<RecruitInfoTemp> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "recruit/recruitInfoTempAjaxSave")
	@ResponseBody
	public Map<String,Object> recruitInfoTempAjaxSave(HttpServletRequest request,
			HttpServletResponse response, RecruitInfoTemp info) {
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

	@RequestMapping(value = "recruit/recruitInfoTempAjaxDelete")
	@ResponseBody
	public Map<String,Object> recruitInfoTempAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, RecruitInfoTemp info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
	
	/**
	 * 审核
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "recruit/recruitInfoTempUpdateStatus")
	@ResponseBody
	public Map<String,Object> recruitInfoTempUpdateStatus(HttpServletRequest request,
			HttpServletResponse response, RecruitInfoTemp info) {
		int result = 0;
		try {
			String[] idss = info.getIds().split(",");
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < idss.length; i++) {
				list.add(idss[i]);
			}
			
			for (int i = 0; i < idss.length; i++) {
				RecruitInfoTemp temp = service.selectById(idss[i]);
				if(temp != null){
					//待审核
					if(info.getStatus().equals("1")){
						Map<String, Object> tempMap = new HashMap<String, Object>();
						tempMap.put("recruitId", temp.getId());
						List<RecruitRoleInfoTemp> infoTemps = infoTempService.selectAll(tempMap);
						RecruitInfo recruitInfo = infoService.selectById(temp.getRecruitId());
						if(recruitInfo != null){
							RecruitInfo recruitInfoNew = new RecruitInfo();
							BeanUtils.copyProperties(temp, recruitInfoNew);
							recruitInfoNew.setId(recruitInfo.getId());
							
							Map<String, Object> infoMap = new HashMap<String, Object>();
							infoMap.put("recruitId", recruitInfo.getId());
							List<RecruitRoleInfo> listRole =  roleInfoService.selectAll(infoMap);
							
							if(listRole != null && !listRole.isEmpty()){								
								for(RecruitRoleInfo roleInfo : listRole){
									roleInfoService.delete(roleInfo);
								}							
							}														
							for (RecruitRoleInfoTemp roleInfoTemp : infoTemps) {
								RecruitRoleInfo roleInfoNew = new RecruitRoleInfo();
								roleInfoTemp.setRecruitTempInfo(null);
								BeanUtils.copyProperties(roleInfoTemp, roleInfoNew);								
								roleInfoNew.setRecruitId(recruitInfo.getId());															
								roleInfoService.insert(roleInfoNew);								
								//infoTempService.delete(roleInfoTemp);
							}
							
							//审核通过
							temp.setStatus("1");
							MemberInfo memberInfo = memberInfoService.selectById(temp.getMemberId());
							if(memberInfo.getIdentityStatus() != null && memberInfo.getIdentityStatus().equals("1")){
								temp.setType("1");
							}
							result = service.update(temp);
							
							recruitInfoNew.setStatus("1");
							recruitInfoNew.setModify("0");
							if(memberInfo.getIdentityStatus() != null && memberInfo.getIdentityStatus().equals("1")){
								recruitInfoNew.setType("1");
							}
							result = infoService.update(recruitInfoNew);																					
							
							MessagePushNotification notification = new MessagePushNotification();
							notification.setId(UUIDUtil.getUUID());
							notification.setTitle(temp.getTitle()+"认证通过");
							notification.setType(Constant.PUSH_RECRUIT);
							notification.setReceiveId(temp.getMemberId());
							notification.setContent("认证通过");
							notification.setCreateDate(DateUtils.currentStringDate());
							notification.setStatus("1");
							notification.setCid(temp.getMemberInfo().getCid());
							if(Constant.ISPASS){
								notificationService.insert(notification);
							}
							//service.delete(temp);
						}else{
							
							//审核通过
							temp.setStatus("1");
							MemberInfo memberInfo = memberInfoService.selectById(temp.getMemberId());
							if(memberInfo.getIdentityStatus() != null && memberInfo.getIdentityStatus().equals("1")){
								temp.setType("1");
							}
							result = service.update(temp);
							
							RecruitInfo recruitInfoNew = new RecruitInfo();
							BeanUtils.copyProperties(temp, recruitInfoNew);
							recruitInfoNew.setId(UUIDUtil.getUUID());
							for (RecruitRoleInfoTemp roleInfoTemp : infoTemps) {
								RecruitRoleInfo roleInfoNew = new RecruitRoleInfo();
								BeanUtils.copyProperties(roleInfoTemp, roleInfoNew);
								roleInfoNew.setId(UUIDUtil.getUUID());
								roleInfoNew.setRecruitId(recruitInfoNew.getId());
								roleInfoService.insert(roleInfoNew);
								//infoTempService.delete(roleInfoTemp);
							}
							if(memberInfo.getIdentityStatus() != null && memberInfo.getIdentityStatus().equals("1")){
								recruitInfoNew.setType("1");
							}
							result = infoService.insert(recruitInfoNew);
							MessagePushNotification notification = new MessagePushNotification();
							notification.setId(UUIDUtil.getUUID());
							notification.setTitle(temp.getTitle()+"认证通过");
							notification.setType(Constant.PUSH_RECRUIT);
							notification.setReceiveId(temp.getMemberId());
							notification.setContent("认证通过");
							notification.setCreateDate(DateUtils.currentStringDate());
							notification.setStatus("1");
							notification.setCid(temp.getMemberInfo().getCid());
							if(Constant.ISPASS){
								notificationService.insert(notification);
							}
							//service.delete(temp);
						}
					}else{
						//修改临时表状态为已拒绝
						temp.setStatus("2");
						temp.setReply(info.getReply());
						result = service.update(temp);
						
						//修改招募表状态为已拒绝
						if(StringUtils.isNotBlank(temp.getRecruitId())){							
							RecruitInfo recruitInfo = infoService.selectById(temp.getRecruitId());
							recruitInfo.setStatus("2");
							infoService.update(recruitInfo);
						}
																		
						MessagePushNotification notification = new MessagePushNotification();
						notification.setId(UUIDUtil.getUUID());
						notification.setTitle(temp.getTitle()+"认证拒绝");
						notification.setType(Constant.PUSH_RECRUIT);
						notification.setReceiveId(temp.getMemberId());
						notification.setContent(info.getReply() != null ? temp.getReply() : "认证拒绝");
						notification.setCreateDate(DateUtils.currentStringDate());
						notification.setStatus("1");
						notification.setCid(temp.getMemberInfo().getCid());
						if(Constant.NOTPASS){
							notificationService.insert(notification);
						}
					}
				}
				//service.delete(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
}
