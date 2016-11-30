package com.bluemobi.www.security.app.recruit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.bluemobi.www.data.model.activity.ActivityVideoComment;
import com.bluemobi.www.data.model.activity.ActivityVideoInfo;
import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.member.MemberResumeTemplate;
import com.bluemobi.www.data.model.member.MemberResumeTemplatePhotos;
import com.bluemobi.www.data.model.recruit.RecruitInfo;
import com.bluemobi.www.data.model.recruit.RecruitInfoTemp;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfo;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfoTemp;
import com.bluemobi.www.data.model.system.SystemHotspotCity;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.activity.service.ActivityVideoCommentService;
import com.bluemobi.www.security.activity.service.ActivityVideoInfoService;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberFollowInfoService;
import com.bluemobi.www.security.member.service.MemberResumeTemplatePhotosService;
import com.bluemobi.www.security.member.service.MemberResumeTemplateService;
import com.bluemobi.www.security.recruit.service.RecruitInfoService;
import com.bluemobi.www.security.recruit.service.RecruitInfoTempService;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoService;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoTempService;
import com.bluemobi.www.security.system.service.SystemHotspotCityService;
import com.bluemobi.www.security.system.service.SystemLableInfoService;
import com.bluemobi.www.utils.DateUtils;

/**
 * 
 * @author sundq
 * 招募模块webView
 */
@Controller
public class ViewRecruitInfoController extends BaseController{
	@Resource
	private RecruitInfoService service;	
	@Resource
	private RecruitInfoTempService recruitInfoTempService;	
	@Resource
	private RecruitRoleInfoService roleInfoService;
	@Resource
	private RecruitRoleInfoTempService recruitRoleInfoTempService;	
	@Resource
	private SystemLableInfoService lableInfoService;
	@Resource
	private MemberFollowInfoService followService;
	@Resource
	private MemberResumeTemplateService memberResumeTemplateService;
	@Resource
	private MemberResumeTemplatePhotosService memberResumeTemplatePhotosService;
	@Resource
	private SystemHotspotCityService cityService;
	@Resource
	private ActivityVideoInfoService videoInfoService;	
	@Resource
	private ActivityVideoCommentService activityVideoCommentService;
	
	/**
	 * 招募分享
	 * @param request
	 * @param response
	 * @param recruitId
	 * @return
	 */
	@RequestMapping(value = "rest/share/shareRecruitInfo")
	@ResponseBody
	public Map<String,Object> shareRecruitInfo(HttpServletRequest request,HttpServletResponse response,
			String recruitId){
		Map<String,Object> map = new HashMap<String,Object>();
		RecruitInfo recruitInfo = service.selectById(recruitId);
		if(recruitInfo != null){
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("title", decodeParam(recruitInfo.getTitle()));
			result.put("description", "");
			result.put("url", getBasePath(request) + "rest/view/recruitInfo.do?recruitId="+recruitInfo.getId());
			result.put("images", "");
			map.put("success", "yes");
			map.put("data", result);
		}else{
			map.put("success", "no");
			map.put("message", "分享错误");
		}
		
		return map;
	}
	
	/**
	 * 我的招募分享
	 * @param request
	 * @param response
	 * @param recruitId
	 * @return
	 */
	@RequestMapping(value = "rest/share/myShareRecruitInfo")
	@ResponseBody
	public Map<String,Object> myShareRecruitInfo(HttpServletRequest request,HttpServletResponse response,
			String recruitId){
		Map<String,Object> map = new HashMap<String,Object>();
		RecruitInfoTemp recruitInfoTemp = recruitInfoTempService.selectById(recruitId);
		if(recruitInfoTemp != null && StringUtils.isNotBlank(recruitInfoTemp.getRecruitId())){
			RecruitInfo recruitInfo = service.selectById(recruitInfoTemp.getRecruitId());
			if(recruitInfo == null){
				map.put("success", "no");
				map.put("message", "您的招募还没有发布,请发布后再进行此操作。");
				return map;
			}
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("title", decodeParam(recruitInfo.getTitle()));
			result.put("description", "");
			result.put("url", getBasePath(request) + "rest/view/recruitInfo.do?recruitId="+recruitInfo.getId());
			result.put("images", "");
			map.put("success", "yes");
			map.put("shareMap", result);
		}else{
			map.put("success", "no");
			map.put("message", "您的招募还没有发布,请发布后再进行此操作。");
		}		
		return map;
	}
	
	/**
	 * 角色分享
	 * @param request
	 * @param response
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "rest/share/shareRecruitRoleInfo")
	@ResponseBody
	public Map<String,Object> shareRecruitRoleInfo(HttpServletRequest request,HttpServletResponse response,
			String roleId){
		Map<String,Object> map = new HashMap<String,Object>();
		RecruitRoleInfo roleInfo = roleInfoService.selectById(roleId);
		if(roleInfo != null){
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("title", decodeParam(roleInfo.getName()));
			result.put("description", decodeParam(roleInfo.getRequirement()));
			result.put("url", getBasePath(request) + "rest/view/recruitRoleInfo.do?roleId=" + roleInfo.getId());
			result.put("images", "");
			map.put("success", "yes");
			map.put("data", result);
		}else{
			map.put("success", "no");
			map.put("message", "分享错误");
		}
		
		return map;
	}
	
	/**
	 * 角色分享
	 * @param request
	 * @param response
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "rest/share/myShareRecruitRoleInfo")
	@ResponseBody
	public Map<String,Object> myShareRecruitRoleInfo(HttpServletRequest request,HttpServletResponse response,
			String roleId){
		Map<String,Object> map = new HashMap<String,Object>();
		RecruitRoleInfoTemp roleInfoTemp = recruitRoleInfoTempService.selectById(roleId);
		if(roleInfoTemp != null){
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("title", decodeParam(roleInfoTemp.getName()));
			result.put("description", decodeParam(roleInfoTemp.getRequirement()));
			result.put("url", getBasePath(request) + "rest/view/myRecruitRoleInfo.do?roleId=" + roleInfoTemp.getId());
			result.put("images", "");
			map.put("success", "yes");
			map.put("data", result);
		}else{
			map.put("success", "no");
			map.put("message", "分享错误");
		}
		
		return map;
	}
	
	
	/**
	 * 获取招募详细信息
	 * @param request
	 * @param response
	 * @param recruitId
	 * @return
	 */
	@RequestMapping(value = "rest/view/recruitInfo")
	public String recruitInfo(HttpServletRequest request,HttpServletResponse response,
			String recruitId){
		RecruitInfo recruitInfo = service.selectById(recruitId);
		MemberInfo memberInfo = recruitInfo.getMemberInfo();
		memberInfo.setNickname(decodeParam(memberInfo.getNickname()));
		
		recruitInfo.setTitle(decodeParam(recruitInfo.getTitle()));
		recruitInfo.setDirector(decodeParam(recruitInfo.getDirector()));
		recruitInfo.setScreenwriter(decodeParam(recruitInfo.getScreenwriter()));
		recruitInfo.setScriptOutline(decodeParam(recruitInfo.getScriptOutline()));
		recruitInfo.setRemark(decodeParam(recruitInfo.getRemark()));
		
		String startDate = recruitInfo.getStartDate();
		String endDate = recruitInfo.getEndDate();
		String deadlineDate = recruitInfo.getDeadline();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfNew = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			recruitInfo.setStartDate(sdfNew.format(sdf.parse(startDate)));
			recruitInfo.setEndDate(sdfNew.format(sdf.parse(endDate)));
			recruitInfo.setDeadline(sdfNew.format(sdf.parse(deadlineDate)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		request.setAttribute("recruitInfo", recruitInfo);
		Map<String, Object> followMap = new HashMap<String, Object>();
		followMap.put("followMemberId", recruitInfo.getMemberId());
		request.setAttribute("follows", followService.selectCount(followMap));//关注数
		request.setAttribute("remainTime", DateUtils.getDateSubtraction(recruitInfo.getDeadlineDate(), new Date()));//剩余天数
		
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); 
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("recruitId", recruitId);
		condition.put("status", "1");
		condition.put("isDelete", "0");
		condition.put("sort", "createDate");
		condition.put("order", "asc");
		List<RecruitRoleInfo> list = roleInfoService.selectAll(condition);
		for(RecruitRoleInfo roleInfo : list){
			Map<String,Object> resultRoleMap = new HashMap<String,Object>();
			resultRoleMap.put("id", roleInfo.getId());//角色ID
			resultRoleMap.put("urlPath", roleInfo.getPictureInfo().getUrlPath());//角色头像
			resultRoleMap.put("name", decodeParam(roleInfo.getName()));//角色名称
			resultRoleMap.put("sex", roleInfo.getSex());//角色性别 1 男  2 女
			resultRoleMap.put("paid", roleInfo.getPaidType().equals("面议") ? "面议":roleInfo.getPaidMin()+"-"+roleInfo.getPaidMax()+roleInfo.getPaidType());
			resultRoleMap.put("lableCode", decodeParam(roleInfo.getLableCode()));//标签code
			String lableNames = getLableName(decodeParam(roleInfo.getLableCode()));
			String[] lables = lableNames.split(",");
			List<String> nameList = new ArrayList<String>();
			for (int i = 0; i < lables.length; i++) {
				nameList.add(lables[i]);
			}
			resultRoleMap.put("nameList", nameList);//标签名称
			resultList.add(resultRoleMap);
		}
		request.setAttribute("resultList", resultList);
		return "webview/recruit_info";
	}
	
	/**
	 * 获取我的招募详细信息
	 * @param request
	 * @param response
	 * @param recruitId
	 * @return
	 */
	@RequestMapping(value = "rest/view/myRecruitInfo")
	public String myRecruitInfo(HttpServletRequest request,HttpServletResponse response,
			String recruitId){
		RecruitInfoTemp recruitInfoTemp = recruitInfoTempService.selectById(recruitId);
		MemberInfo memberInfo = recruitInfoTemp.getMemberInfo();
		memberInfo.setNickname(decodeParam(memberInfo.getNickname()));
		
		recruitInfoTemp.setTitle(decodeParam(recruitInfoTemp.getTitle()));
		recruitInfoTemp.setDirector(decodeParam(recruitInfoTemp.getDirector()));
		recruitInfoTemp.setScreenwriter(decodeParam(recruitInfoTemp.getScreenwriter()));
		recruitInfoTemp.setScriptOutline(decodeParam(recruitInfoTemp.getScriptOutline()));
		recruitInfoTemp.setRemark(decodeParam(recruitInfoTemp.getRemark()));
		
		String startDate = recruitInfoTemp.getStartDate();
		String endDate = recruitInfoTemp.getEndDate();
		String deadlineDate = recruitInfoTemp.getDeadline();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfNew = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			recruitInfoTemp.setStartDate(sdfNew.format(sdf.parse(startDate)));
			recruitInfoTemp.setEndDate(sdfNew.format(sdf.parse(endDate)));
			recruitInfoTemp.setDeadline(sdfNew.format(sdf.parse(deadlineDate)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		request.setAttribute("recruitInfo", recruitInfoTemp);
		Map<String, Object> followMap = new HashMap<String, Object>();
		followMap.put("followMemberId", recruitInfoTemp.getMemberId());
		request.setAttribute("follows", followService.selectCount(followMap));//关注数
		request.setAttribute("remainTime", DateUtils.getDateSubtraction(recruitInfoTemp.getDeadlineDate(), new Date()));//剩余天数
		
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); 
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("recruitId", recruitId);
		condition.put("status", "1");
		condition.put("isDelete", "0");
		condition.put("sort", "createDate");
		condition.put("order", "asc");
		List<RecruitRoleInfo> list = roleInfoService.selectAll(condition);
		for(RecruitRoleInfo roleInfo : list){
			Map<String,Object> resultRoleMap = new HashMap<String,Object>();
			resultRoleMap.put("id", roleInfo.getId());//角色ID
			resultRoleMap.put("urlPath", roleInfo.getPictureInfo().getUrlPath());//角色头像
			resultRoleMap.put("name", decodeParam(roleInfo.getName()));//角色名称
			resultRoleMap.put("sex", roleInfo.getSex());//角色性别 1 男  2 女
			resultRoleMap.put("paid", roleInfo.getPaidType().equals("面议") ? "面议":roleInfo.getPaidMin()+"-"+roleInfo.getPaidMax()+roleInfo.getPaidType());
			resultRoleMap.put("lableCode", decodeParam(roleInfo.getLableCode()));//标签code
			String lableNames = getLableName(decodeParam(roleInfo.getLableCode()));
			String[] lables = lableNames.split(",");
			List<String> nameList = new ArrayList<String>();
			for (int i = 0; i < lables.length; i++) {
				nameList.add(lables[i]);
			}
			resultRoleMap.put("nameList", nameList);//标签名称
			resultList.add(resultRoleMap);
		}
		request.setAttribute("resultList", resultList);
		return "webview/my_recruit_info";
	}
	
	
	/**
	 * 获取角色详细信息
	 * @param request
	 * @param response
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "rest/view/recruitRoleInfo")
	public String selectRecruitRoleById(HttpServletRequest request,HttpServletResponse response,
			String roleId){
		RecruitRoleInfo roleInfo = roleInfoService.selectById(roleId);
		roleInfo.setName(decodeParam(roleInfo.getName()));
		roleInfo.setRequirement(decodeParam(roleInfo.getRequirement()));
		roleInfo.setDialogue(decodeParam(roleInfo.getDialogue()));
		
		request.setAttribute("roleInfo", roleInfo);
		String lableNames = getLableName(decodeParam(roleInfo.getLableCode()));
		String[] lables = lableNames.split(",");
		List<String> nameList = new ArrayList<String>();
		for (int i = 0; i < lables.length; i++) {
			nameList.add(lables[i]);
		}
		request.setAttribute("nameList", nameList);//类型
		return "webview/recruit_role_info";
	}
	
	/**
	 * 获取角色详细信息
	 * @param request
	 * @param response
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "rest/view/myRecruitRoleInfo")
	public String selectMyRecruitRoleById(HttpServletRequest request,HttpServletResponse response,
			String roleId){
		RecruitRoleInfoTemp roleInfoTemp = recruitRoleInfoTempService.selectById(roleId);
		roleInfoTemp.setName(decodeParam(roleInfoTemp.getName()));
		roleInfoTemp.setRequirement(decodeParam(roleInfoTemp.getRequirement()));
		roleInfoTemp.setDialogue(decodeParam(roleInfoTemp.getDialogue()));
		
		request.setAttribute("roleInfo", roleInfoTemp);
		String lableNames = getLableName(decodeParam(roleInfoTemp.getLableCode()));
		String[] lables = lableNames.split(",");
		List<String> nameList = new ArrayList<String>();
		for (int i = 0; i < lables.length; i++) {
			nameList.add(lables[i]);
		}
		request.setAttribute("nameList", nameList);//类型
		return "webview/recruit_role_info";
	}
	
	/**
	 * 简历分享
	 * @param request
	 * @param response
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "rest/share/shareResumeInfo")
	@ResponseBody
	public Map<String,Object> shareResumeInfo(HttpServletRequest request,HttpServletResponse response,
			String memberId){
		Map<String,Object> map = new HashMap<String,Object>();
		
		MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(memberId);
		if(memberResumeTemplate != null){
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("title", decodeParam(memberResumeTemplate.getRealName())+"个人简历");
			result.put("description", "");
			result.put("url", getBasePath(request) + "rest/view/ResumeInfo.do?memberId="+memberId);
			result.put("images", getBasePath(request) + memberResumeTemplate.getPictureInfo().getUrlPath());
			map.put("success", "yes");
			map.put("data", result);
		}else{
			map.put("success", "no");
			map.put("message", "分享错误");
		}
		
		return map;
	}
	
	/**
	 * 获取角色详细信息
	 * @param request
	 * @param response
	 * @param roleId
	 * @return
	 * @throws Exception 
	 * @throws ParseException 
	 */
	@RequestMapping(value = "rest/view/ResumeInfo")
	public String ResumeInfo(HttpServletRequest request,HttpServletResponse response,
			String memberId) throws ParseException, Exception{
		MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(memberId);
		
		memberResumeTemplate.setRealName(decodeParam(memberResumeTemplate.getRealName()));
		memberResumeTemplate.setSchool(decodeParam(memberResumeTemplate.getSchool()));
		memberResumeTemplate.setSchool(decodeParam(memberResumeTemplate.getSchool()));
		
		SystemHotspotCity city = cityService.selectById(memberResumeTemplate.getCityCode());
		memberResumeTemplate.setCityCode(city!=null ?city.getCityName() :"");
		request.setAttribute("memberResumeTemplate", memberResumeTemplate);
		request.setAttribute("memberInfo", memberResumeTemplate.getMemberInfo());
		if(StringUtils.isNotBlank(memberResumeTemplate.getBirthday())){
			request.setAttribute("age", DateUtils.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(memberResumeTemplate.getBirthday())));
		}
		String lableNames = getLableName(decodeParam(memberResumeTemplate.getRoleLabel()));
		String[] lables = lableNames.split(",");
		List<String> roleList = new ArrayList<String>();
		for (int i = 0; i < lables.length; i++) {
			roleList.add(lables[i]);
		}
		request.setAttribute("roleList", roleList);//角色类型
		
		String skillLabels = getLableName(decodeParam(memberResumeTemplate.getSkillLabel()));
		String[] skills = skillLabels.split(",");
		List<String> skillList = new ArrayList<String>();
		for (int i = 0; i < skills.length; i++) {
			skillList.add(skills[i]);
		}
		request.setAttribute("skillList", skillList);//角色类型
		
		Map<String,Object> photosParams = new HashMap<String,Object>();
		photosParams.put("resumeId", memberResumeTemplate.getId());
		List<MemberResumeTemplatePhotos> photoList = memberResumeTemplatePhotosService.selectAll(photosParams);
		if(photoList != null){
			for(MemberResumeTemplatePhotos memberResumeTemplatePhoto : photoList){
				memberResumeTemplatePhoto.setTitle(decodeParam(memberResumeTemplatePhoto.getTitle()));
			}
		}
		request.setAttribute("photoList", photoList);
		return "webview/resume_info";
	}
	
	
	
	/**
	 * 获取活动视频
	 * @param request
	 * @param response
	 * @param videoId
	 * @return
	 */
	@RequestMapping(value = "rest/view/videoInfo")
	public String videoInfo(HttpServletRequest request,HttpServletResponse response,
			String videoId){
		ActivityVideoInfo videoInfo = videoInfoService.selectById(videoId);
		Map<String,Object> commentParams = new HashMap<String,Object>();
		commentParams.put("videoInfoId", videoId);
		commentParams.put("order", "asc");
		commentParams.put("sort", "createDate");
		PageInfo<ActivityVideoComment> pageInfo = new PageInfo<ActivityVideoComment>();
		pageInfo.setPage(1);
		pageInfo.setPageSize(10);
		PageInfo<ActivityVideoComment> commentPage =  activityVideoCommentService.selectAll(commentParams,pageInfo);
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		
		for (int i = 0; i < commentPage.getRows().size(); i++) {
			ActivityVideoComment comment = commentPage.getRows().get(i);
			MemberInfo info = comment.getMemberInfo();
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("urlPath", info.getPictureInfo().getUrlPath());
			map.put("nickname", decodeParam(info.getNickname()));
			map.put("createDate", comment.getCreateDate());
			map.put("content", decodeParam(comment.getContent()));
			map.put("realNameStatus", info.getRealNameStatus());
			listMap.add(map);
		}
		request.setAttribute("videoInfo", videoInfo);
		request.setAttribute("activityInfo", videoInfo.getActivityInfo());
		MemberInfo memberInfo = videoInfo.getMemberInfo();
		memberInfo.setNickname(decodeParam(memberInfo.getNickname()));
		request.setAttribute("memberInfo", memberInfo);
		request.setAttribute("commentPage", listMap);//评论
		return "webview/video";
	}
	
	
	/**
	 * 获取标签名称
	 * @param roleInfo
	 */
	private String getLableName(String lable){
		String lableName = "";
		if (lable != null) {
			String[] lables = lable.split(",");
			for(String name : lables){
				if(StringUtils.isNotBlank(name)){
					lableName += name + ",";
				}				
			}			
		}
		if (lableName.equals("")) {
			return "";
		}
		return lableName.substring(0, lableName.length() - 1);
	}
}
