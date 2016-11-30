package com.bluemobi.www.security.recruit.controller;

import java.util.ArrayList;
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

import com.bluemobi.www.constant.Constant;
import com.bluemobi.www.data.model.member.MemberResumeApply;
import com.bluemobi.www.data.model.member.MemberResumeTemplate;
import com.bluemobi.www.data.model.message.MessagePushNotification;
import com.bluemobi.www.data.model.recruit.RecruitApplyInfo;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberResumeApplyService;
import com.bluemobi.www.security.member.service.MemberResumeTemplateService;
import com.bluemobi.www.security.message.service.MessagePushNotificationService;
import com.bluemobi.www.security.recruit.service.RecruitApplyInfoService;
import com.bluemobi.www.security.system.service.SystemLableInfoService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
 * @ClassName: RecruitApplyInfoController
 * @Description: 投递招募信息控制层
 * @author sundq
 * @date 2016-02-23 09:01:13
 * @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class RecruitApplyInfoController extends BaseController {
	
	@Resource
	private RecruitApplyInfoService service;
	@Resource
	private SystemLableInfoService lableInfoService;
	@Resource
	private MessagePushNotificationService notificationService;
	@Resource
	private MemberResumeTemplateService memberResumeTemplateService;
	@Resource
	private MemberResumeApplyService memberResumeApplyService;
	
	@RequestMapping(value = "recruit/recruitApplyInfoList")
	public String recruitApplyInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		return "recruit/recruit_apply_info_list";
	}
	
	@RequestMapping(value = "recruit/recruitApplyInfoResumeList")
	public String recruitApplyInfoResumeList(HttpServletRequest request,
			HttpServletResponse response) {
		return "recruit/recruit_apply_info_resume_list";
	}
	
	@RequestMapping(value = "recruit/recruitApplyInfoAjaxPage")
	@ResponseBody
	public PageInfo<RecruitApplyInfo> recruitApplyInfoAjaxPage(
			HttpServletRequest request, HttpServletResponse response,
			RecruitApplyInfo info, Integer page, Integer rows) {
		
		PageInfo<RecruitApplyInfo> pageInfo = new PageInfo<RecruitApplyInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		if (info.getSort().equals("id")) {
			info.setSort("createDate");
			info.setOrder("desc");
		}
		service.selectAll(info, pageInfo);
		return pageInfo;
	}
	
	/**
	 * 试戏戏频审核
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "recruit/recruitVideoAuditAjaxPage")
	@ResponseBody
	public PageInfo<RecruitApplyInfo> recruitVideoAuditAjaxPage(
			HttpServletRequest request, HttpServletResponse response,
			RecruitApplyInfo info, Integer page, Integer rows){
		
		//查询审核通过的简历
		List<MemberResumeApply> applyList = memberResumeApplyService.selectAll();
		List<String> resumeIdList = new ArrayList<String>();
		if(applyList != null && !applyList.isEmpty()){
			for(MemberResumeApply memberResumeApply : applyList){
				resumeIdList.add(memberResumeApply.getId());
			}
		}
		PageInfo<RecruitApplyInfo> pageInfo = new PageInfo<RecruitApplyInfo>();
		if(!resumeIdList.isEmpty()){
			info.setResumeIdList(resumeIdList);
			pageInfo.setPage(page);
			pageInfo.setPageSize(rows);
			if (info.getSort().equals("id")) {
				info.setSort("createDate");
				info.setOrder("desc");
			}
			service.selectAll(info, pageInfo);
		}		
		return pageInfo;
	}
	
	@RequestMapping(value = "recruit/resumeTemplateInfoAjaxPage")
	@ResponseBody
	public PageInfo<MemberResumeTemplate> resumeApplyInfoAjaxPage(
			HttpServletRequest request, HttpServletResponse response,
			MemberResumeTemplate info, Integer page, Integer rows) {
		
		PageInfo<MemberResumeTemplate> pageInfo = new PageInfo<MemberResumeTemplate>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		if (info.getSort().equals("id")) {
			info.setSort("createDate");
			info.setOrder("desc");			
		}
		info.setShowStatus("true");
		memberResumeTemplateService.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "recruit/recruitApplyInfoAjaxAll")
	@ResponseBody
	public List<RecruitApplyInfo> recruitApplyInfoAjaxAll(
			HttpServletRequest request, HttpServletResponse response,
			RecruitApplyInfo info, Integer page, Integer rows) {
		List<RecruitApplyInfo> results = service.selectAll(info);
		return results;
	}

	@RequestMapping(value = "recruit/recruitApplyInfoAjaxSave")
	@ResponseBody
	public Map<String, Object> recruitApplyInfoAjaxSave(
			HttpServletRequest request, HttpServletResponse response,
			RecruitApplyInfo info) {
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
		return getJsonResult(result, "操作成功", msg);
	}
	
	@RequestMapping(value = "recruit/recruitApplyInfoAjaxDelete")
	@ResponseBody
	public Map<String, Object> recruitApplyInfoAjaxDelete(
			HttpServletRequest request, HttpServletResponse response,
			RecruitApplyInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result, "操作成功", "删除失败！");
	}
	
	@RequestMapping(value = "recruit/recruitApplyInfoUpdateStatus")
	@ResponseBody
	public Map<String, Object> recruitApplyInfoUpdateStatus(
			HttpServletRequest request, HttpServletResponse response,
			RecruitApplyInfo info) {
		int result = 0;
		String msg = "";
			if(!info.getIds().isEmpty()){
				String[] ids = info.getIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					Map<String, Object> infomap = new HashMap<String, Object>();
					infomap.put("id", ids[i]);
					infomap.put("videoStatus", info.getVideoStatus());
					result = service.update(infomap);
					
					RecruitApplyInfo applyInfo = service.selectById(ids[i]);
					if(info.getVideoStatus() != null && info.getVideoStatus().equals("1")){
						if(applyInfo.getStatus().equals("1")){
							applyInfo.setLevel("2");
							service.update(applyInfo);
						}
					}
					
					
					if (info.getVideoStatus() != null && !info.getVideoStatus().equals("2")) {
						MessagePushNotification notification = new MessagePushNotification();
						notification.setId(UUIDUtil.getUUID());
						notification.setTitle("简历审核");
						notification.setType(Constant.PUSH_EDUCATION);
						notification.setReceiveId(applyInfo.getMemberId());
						notification.setContent(info.getReply());
						notification.setCreateDate(DateUtils.currentStringDate());
						notification.setStatus("1");
						notification.setCid(applyInfo.getMemberInfo().getCid());
						if(info.getVideoStatus().equals("1") && Constant.ISPASS){
							notificationService.insert(notification);
						}
						if(info.getVideoStatus().equals("2") && Constant.NOTPASS){
							notificationService.insert(notification);
						}
					}
				}
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功", "操作失败");
	}

	@RequestMapping(value = "recruit/recruitApplyInfoById")
	public String recruitApplyInfoById(HttpServletRequest request,
			HttpServletResponse response, RecruitApplyInfo info) {
		try {
			info = service.selectById(info.getId());
			getLableName(info.getRoleInfo());
			request.setAttribute("info", info);
			request.setAttribute("entity", info.getRoleInfo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "recruit/recruit_apply_info_detail";
	}
	
	@RequestMapping(value = "recruit/recruitApplyInfoViewById")
	public String recruitApplyInfoViewById(HttpServletRequest request,
			HttpServletResponse response, RecruitApplyInfo info) {
		try {
			info = service.selectById(info.getId());
			getLableName(info.getRoleInfo());
			request.setAttribute("info", info);
			request.setAttribute("entity", info.getRoleInfo());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "recruit/recruit_apply_info_view";
	}
	
	@RequestMapping(value = "recruit/recruitApplyInfoNext")
	public String recruitApplyInfoNext(HttpServletRequest request,
			HttpServletResponse response, RecruitApplyInfo info) {
		try {
			info = service.selectNext(info.getId());
			if(info == null){
				return "redirect:/recruit/recruitApplyInfoList.do";
			}
			getLableName(info.getRoleInfo());
			request.setAttribute("info", info);
			request.setAttribute("entity", info.getRoleInfo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "recruit/recruit_apply_info_detail";
	}

	@RequestMapping(value = "recruit/recruitVideoInfoList")
	public String recruitVideoInfoList(HttpServletRequest request,
			HttpServletResponse response, String memberId) {
		request.setAttribute("memberId", memberId);
		return "recruit/recruit_video_info_list";
	}
	
	@RequestMapping(value = "recruit/recruitVideoInfoAjaxPage")
	@ResponseBody
	public PageInfo<RecruitApplyInfo> recruitVideoInfoAjaxPage(
			HttpServletRequest request, HttpServletResponse response,
			RecruitApplyInfo info, Integer page, Integer rows) {
		PageInfo<RecruitApplyInfo> pageInfo = new PageInfo<RecruitApplyInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	private void getLableName(RecruitRoleInfo roleInfo) {
		String lableName = "";
		String lable = roleInfo.getLableCode();
		if (lable != null) {
			String[] lables = lable.split(",");
			for(String name : lables){
				if(StringUtils.isNotBlank(name)){
					lableName += name + ",";
				}				
			}
		}
		if (lableName.equals("")) {
			roleInfo.setLableNames("");
		}
		roleInfo.setLableNames(lableName.substring(0, lableName.length() - 1));
	}
}
