package com.bluemobi.www.security.app.personal;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bluemobi.www.constant.Constant;
import com.bluemobi.www.data.model.activity.ActivityInfo;
import com.bluemobi.www.data.model.activity.ActivityVideoInfo;
import com.bluemobi.www.data.model.member.MemberActionsInfo;
import com.bluemobi.www.data.model.member.MemberFeedback;
import com.bluemobi.www.data.model.member.MemberFollowInfo;
import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.member.MemberResumeApply;
import com.bluemobi.www.data.model.member.MemberResumeTemplate;
import com.bluemobi.www.data.model.recruit.RecruitApplyInfo;
import com.bluemobi.www.data.model.recruit.RecruitCategory;
import com.bluemobi.www.data.model.recruit.RecruitInfo;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfo;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfoTemp;
import com.bluemobi.www.data.model.system.FileInfo;
import com.bluemobi.www.data.model.system.SystemCommonQuestion;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.activity.service.ActivityInfoService;
import com.bluemobi.www.security.activity.service.ActivityVideoInfoService;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberActionsInfoService;
import com.bluemobi.www.security.member.service.MemberFeedbackService;
import com.bluemobi.www.security.member.service.MemberFollowInfoService;
import com.bluemobi.www.security.member.service.MemberInfoService;
import com.bluemobi.www.security.member.service.MemberResumeTemplateService;
import com.bluemobi.www.security.recruit.service.RecruitApplyInfoService;
import com.bluemobi.www.security.recruit.service.RecruitInfoService;
import com.bluemobi.www.security.recruit.service.RecruitInfoTempService;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoService;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoTempService;
import com.bluemobi.www.security.system.service.SystemCommonQuestionService;
import com.bluemobi.www.security.system.service.SystemLableInfoService;
import com.bluemobi.www.security.system.service.SystemPictureInfoService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.FileTool;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
 * @author sundq
 * 个人中心模块接口
 */
@Controller
public class RestPersonalInfoController extends BaseController{
	@Resource
	private MemberFeedbackService feedbackService;
	@Resource
	private SystemCommonQuestionService questionService;
	@Resource
	private ActivityVideoInfoService videoInfoService;
	@Resource
	private MemberInfoService memberInfoService;
	@Resource
	private SystemPictureInfoService systemPictureInfoService;
	@Resource
	private RecruitApplyInfoService applyInfoService;
	@Resource
	private SystemLableInfoService lableInfoService;
	@Resource
	private MemberActionsInfoService actionsInfoService;
	@Resource
	private MemberFollowInfoService followInfoService;
	@Resource
	private RecruitInfoService recruitInfoService;
	@Resource
	private RecruitRoleInfoService roleInfoService;
	@Resource
	private RecruitRoleInfoTempService infoTempService;
	@Resource
	private RecruitInfoTempService tempService;
	@Resource
	private MemberResumeTemplateService memberResumeTemplateService;
	@Resource
	private ActivityInfoService activityInfoService;
	/**
	 * 保存用户反馈信息
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "rest/personal/saveFeedback")
	@ResponseBody
	public Map<String,Object> saveFeedback(HttpServletRequest request,HttpServletResponse response,
			MemberFeedback info){
		int result = 0;
		String msg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		info.setId(UUIDUtil.getUUID());
		info.setStatus("0");
		info.setCreateDate(DateUtils.currentStringDate());
		info.setContent(encodeParam(info.getContent()));
		result = feedbackService.insert(info);
		msg = "保存失败！";
		if (result > 0) {
			map.put("success", "yes");
			map.put("message", "操作成功");
		} else {
			map.put("success", "no");
			map.put("message", msg);
		}
		return map;
	}
	
	/**
	 * 常见问题(分页)
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "rest/personal/commonQuestionList")
	@ResponseBody
	public Map<String,Object> commonQuestionList(HttpServletRequest request,HttpServletResponse response,
			SystemCommonQuestion info, Integer page, Integer rows){
		Map<String,Object> map = new HashMap<String,Object>();
		//传分页信息page页数
		PageInfo<SystemCommonQuestion> pageInfo = new PageInfo<SystemCommonQuestion>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setStatus("1");
		info.setSort("createDate");
		info.setOrder("desc");
		questionService.selectAll(info, pageInfo);
		map.put("totle", pageInfo.getTotal());
		map.put("page", pageInfo.getPage());
		map.put("pageSize", pageInfo.getPageSize());
		if(pageInfo.getRows() == null || pageInfo.getRows().size()<=0){
			map.put("success", "yes");
			map.put("message", "无结果!");
		}else{
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); 
			for(SystemCommonQuestion question : pageInfo.getRows()){
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.put("id", question.getId());
				resultMap.put("question", question.getQuestion());
				resultMap.put("answer", question.getAnswer());
				resultMap.put("createDate", question.getCreateDate());
				resultList.add(resultMap);
			}
			map.put("success", "yes");
			map.put("data", resultList);
			
		}
		return map;
	}
	
	
	/**
	 * 彪戏视频(分页)
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "rest/personal/videoInfoList")
	@ResponseBody
	public Map<String,Object> videoInfoList(HttpServletRequest request,HttpServletResponse response,
			ActivityVideoInfo info, Integer page, Integer rows){
		Map<String,Object> map = new HashMap<String,Object>();
		if(info.getMemberId() == null){
			map.put("success", "no");
			map.put("message", "用户ID有误");
			return map;
		}
		//传分页信息page页数
		PageInfo<ActivityVideoInfo> pageInfo = new PageInfo<ActivityVideoInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setStatus("1");
		info.setSort("createDate");
		info.setOrder("desc");
		videoInfoService.selectAll(info, pageInfo);
		map.put("totle", pageInfo.getTotal());
		map.put("page", pageInfo.getPage());
		map.put("pageSize", pageInfo.getPageSize());
		if(pageInfo.getRows() == null || pageInfo.getRows().size()<=0){
			map.put("success", "yes");
			map.put("message", "无结果!");
		}else{
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
			for(ActivityVideoInfo videoInfo : pageInfo.getRows()){
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.put("id", videoInfo.getId());
				resultMap.put("title", videoInfo.getActivityInfo().getTitle());
				resultMap.put("urlPath", getBasePath(request) + videoInfo.getFileInfo().getUrlPath());
				resultMap.put("createDate", videoInfo.getCreateDate().substring(0, 16));
				resultMap.put("viewNum", videoInfo.getViewNum());
				resultMap.put("shareNum", videoInfo.getShareNum());
				resultMap.put("likeNum", videoInfo.getLikeNum());
				resultMap.put("commentNum", videoInfo.getCommentNum());
				resultMap.put("isHot", videoInfo.getIsHot());
				resultMap.put("activityId", videoInfo.getActivityId());
				resultMap.put("url", getBasePath(request) + "rest/view/videoInfo.do?videoId="+videoInfo.getId());
				ActivityInfo activityInfo = activityInfoService.selectById(videoInfo.getActivityId());				
				resultMap.put("images", getBasePath(request) + activityInfo.getPictureInfo().getUrlPath());
				resultList.add(resultMap);
			}
			map.put("success", "yes");
			map.put("data", resultList);
			
		}
		return map;
	}
	
	
	/**
	 * 获取实名认证信息
	 * @param request
	 * @param response
	 * @param memberId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "rest/personal/getMemberRealname")
	@ResponseBody
	public Map<String,Object> getMemberRealname(HttpServletRequest request,HttpServletResponse response,
			String memberId){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberInfo info = memberInfoService.selectById(memberId);
		if(info != null){
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("realNameStatus", info.getRealNameStatus());
			if (info.getRealNameStatus() != null && !info.getRealNameStatus().equals("0")) {
				resultMap.put("realName", decodeParam(info.getRealName()));
				resultMap.put("IDcard", info.getIDcard());
				SystemPictureInfo pictureInfoA = info.getIdCardpictureInfoA();
				if(pictureInfoA != null){
					resultMap.put("idCardA", getBasePath(request) + pictureInfoA.getUrlPath());
					resultMap.put("widthA", pictureInfoA.getFwidth());
					resultMap.put("heightA", pictureInfoA.getFheight());
				}else{
					resultMap.put("idCardA", "");
					resultMap.put("widthA", "");
					resultMap.put("heightA", "");
				}
				SystemPictureInfo pictureInfoB = info.getIdCardpictureInfoB();
				if(pictureInfoB != null){
					resultMap.put("idCardB", getBasePath(request) + pictureInfoB.getUrlPath());
					resultMap.put("widthB", pictureInfoB.getFwidth());
					resultMap.put("heightB", pictureInfoB.getFheight());
				}else{
					resultMap.put("idCardB", "");
					resultMap.put("widthB", "");
					resultMap.put("heightB", "");
				}
			}
			map.put("success", "yes");
			map.put("data", resultMap);
		}else{
			map.put("success", "no");
			map.put("message", "memberID有误");
		}
		return map;
	}
	
	/**
	 * 获取学历认证信息
	 * @param request
	 * @param response
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "rest/personal/getEducation")
	@ResponseBody
	public Map<String,Object> getEducation(HttpServletRequest request,HttpServletResponse response,
			String memberId){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberInfo info = memberInfoService.selectById(memberId);
		if(info != null){
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("educationStatus", info.getEducationStatus());
			resultMap.put("school", info.getSchool()==null ? "" : decodeParam(info.getSchool()));
			resultMap.put("realName", info.getRealName());
			if (info.getEducationStatus()!=null && !info.getEducationStatus().equals("0")) {
//				resultMap.put("degreepicture", info.getDegreepictureInfo() != null ? 
//						getBasePath(request) + info.getDegreepictureInfo().getUrlPath() : "");
				SystemPictureInfo pictureInfo = info.getDiplomapictureInfo();
				if(pictureInfo != null){
					resultMap.put("diplomapicture", getBasePath(request) + pictureInfo.getUrlPath());
					resultMap.put("fwidth", pictureInfo.getFwidth());
					resultMap.put("fheight", pictureInfo.getFheight());
				}else{
					resultMap.put("diplomapicture", "");
					resultMap.put("fwidth", "");
					resultMap.put("fheight", "");
				}
			}
			map.put("success", "yes");
			map.put("data", resultMap);
		}else{
			map.put("success", "no");
			map.put("message", "memberID有误");
		}
		return map;
	}
	
	
	/**
	 * 提交认证申请信息
	 * @param request
	 * @param response
	 * @param memberId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "rest/personal/saveIdentification")
	@ResponseBody
	public Map<String,Object> saveIdentification(HttpServletRequest request,HttpServletResponse response,
			MemberInfo info){
		int result = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String root = request.getSession().getServletContext().getRealPath("/");
		if(info.getType() != null){
			if (info.getType().equals("1")) {//实名认证
				// 上传图片
				String idCardUuidA = UUIDUtil.getUUID();
				MultipartFile idCardUuidAImg = multipartRequest.getFile("idCardAImg");
				if (idCardUuidAImg != null && idCardUuidAImg.getSize() > 0) {
					// 删除图片
					deleteImg(info.getIdCardUuidA(), root);
					// 新图片处理
					saveImg(root, idCardUuidA, idCardUuidAImg);
					info.setIdCardUuidA(idCardUuidA);
				}
				String idCardUuidB = UUIDUtil.getUUID();
				MultipartFile idCardUuidBImg = multipartRequest.getFile("idCardBImg");
				if (idCardUuidBImg != null && idCardUuidBImg.getSize() > 0) {
					// 删除图片
					deleteImg(info.getIdCardUuidB(), root);
					// 新图片处理
					saveImg(root, idCardUuidB, idCardUuidBImg);
					info.setIdCardUuidB(idCardUuidB);
				}
				info.setRealNameStatus("3");
				result = memberInfoService.update(info);
				
			}else if(info.getType().equals("2")){
				String degreeImg = UUIDUtil.getUUID();
				MultipartFile degreepicture = multipartRequest.getFile("degreeImg");
				if (degreepicture != null && degreepicture.getSize() > 0) {
					// 删除图片
					deleteImg(info.getDegreeImgUuid(), root);
					// 新图片处理
					saveImg(root, degreeImg, degreepicture);
					info.setDegreeImgUuid(degreeImg);
				}
				String diplomaImg = UUIDUtil.getUUID();
				MultipartFile diplomapicture = multipartRequest.getFile("diplomaImg");
				if (diplomapicture != null && diplomapicture.getSize() > 0) {
					// 删除图片
					deleteImg(info.getDiplomaImgUuid(), root);
					// 新图片处理
					saveImg(root, diplomaImg, diplomapicture);
					info.setDiplomaImgUuid(diplomaImg);
				}
				info.setEducationStatus("3");
				result = memberInfoService.update(info);
				MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(info.getId());
				if(memberResumeTemplate != null){
					memberResumeTemplate.setSchool(info.getSchool());
					memberResumeTemplateService.update(memberResumeTemplate);
				}
			}
		}else{
			map.put("success", "no");
			map.put("message", "类型为空");
		}
		if (result > 0) {
			map.put("success", "yes");
			map.put("message", "操作成功");
		}else{
			map.put("success", "no");
			map.put("message", "操作失败");
		}
		return map;
	}
	
	/**
	 * 角色应聘列表信息(D页入口)
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "rest/personal/getMyApplyInfoByRole")
	@ResponseBody
	public Map<String,Object> getMyApplyInfoByRole(HttpServletRequest request,HttpServletResponse response,
			RecruitApplyInfo info, Integer page, Integer rows, String type, String edit){
		Map<String,Object> map = new HashMap<String,Object>();
		
		if (edit == null || edit.equals("")) {
			RecruitRoleInfo roleInfo = roleInfoService.selectById(info.getRoleId());
			if (roleInfo != null) {								
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("id", roleInfo.getId());//角色ID
				resultMap.put("name", roleInfo.getName());//角色姓名
				resultMap.put("urlPath", getBasePath(request) + roleInfo.getPictureInfo().getUrlPath());//角色图片地址
				resultMap.put("sex", roleInfo.getSex());//性别
				resultMap.put("paidType",roleInfo.getPaidType());//
				resultMap.put("paidMax",roleInfo.getPaidMax());//
				resultMap.put("paidMin",roleInfo.getPaidMin());//
				
				resultMap.put("paid",roleInfo.getPaidType().equals("面议") ? "面议" : 
					roleInfo.getPaidMax() == null || roleInfo.getPaidMax().equals("") ?
					roleInfo.getPaidMin() + roleInfo.getPaidType():
					roleInfo.getPaidMin() + "-" + roleInfo.getPaidMax() + roleInfo.getPaidType());
				resultMap.put("lableName",getLableName(roleInfo.getLableCode()));//类型
				resultMap.put("lableCode",getLableCode(roleInfo.getLableCode()));//类型code
				resultMap.put("requirement", roleInfo.getRequirement());//导演要求
				resultMap.put("dialogue", roleInfo.getDialogue());//试戏台词
				map.put("dataRole", resultMap);
			} else {
				map.put("success", "yes");
				map.put("message", "没有应聘结果!");
				return map;
			}
		}else if(edit != null && edit.equals("1")){
			RecruitRoleInfoTemp roleInfo = infoTempService.selectById(info.getRoleId());
			if (roleInfo != null) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("id", roleInfo.getRoleId());//角色ID
				resultMap.put("name", decodeParam(roleInfo.getName()));//角色姓名
				resultMap.put("urlPath", getBasePath(request) + roleInfo.getPictureInfo().getUrlPath());//角色图片地址
				resultMap.put("sex", roleInfo.getSex());//性别
				resultMap.put("paidType",roleInfo.getPaidType());//
				resultMap.put("paidMax",roleInfo.getPaidMax());//
				resultMap.put("paidMin",roleInfo.getPaidMin());//
				resultMap.put("paid",roleInfo.getPaidType().equals("面议") ? "面议" : 
							roleInfo.getPaidMax() == null || roleInfo.getPaidMax().equals("") ?
							roleInfo.getPaidMin() + roleInfo.getPaidType():
							roleInfo.getPaidMin() + "-" + roleInfo.getPaidMax() + roleInfo.getPaidType());
				resultMap.put("lableName",getLableName(roleInfo.getLableCode()));//类型
				resultMap.put("lableCode",getLableCode(roleInfo.getLableCode()));//类型code
				resultMap.put("requirement", decodeParam(roleInfo.getRequirement()));//导演要求
				resultMap.put("dialogue", decodeParam(roleInfo.getDialogue()));//试戏台词
				map.put("dataRole", resultMap);
				if(roleInfo.getRoleId() != null && !roleInfo.getRoleId().equals("")){
					info.setRoleId(roleInfo.getRoleId());
				}
			} else {
				map.put("success", "no");
				map.put("message", "角色信息错误!");
				return map;
			}
		}
		PageInfo<RecruitApplyInfo> pageInfo = new PageInfo<RecruitApplyInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setSort("createDate");
		info.setOrder("desc");
		//info.setStatus("1");
		info.setVideoStatus("1");
		if (type != null) {
			if (type.equals("1")) {//已看
				info.setIsView("1");
			}
			if (type.equals("2")) {//未看
				info.setIsView("0");
			}
			if (type.equals("3")) {//备选
				info.setOptional("1");
			}
		}
		applyInfoService.selectAll(info, pageInfo);
		if(pageInfo.getRows() != null && pageInfo.getRows().size() > 0){
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); 
			for (RecruitApplyInfo applyInfo: pageInfo.getRows()) {
				Map<String,Object> resultMap = new HashMap<String,Object>();
				MemberResumeApply apply = applyInfo.getResumeApply();
				if(apply == null){
					continue;
				}
				MemberInfo memberInfo = applyInfo.getMemberInfo();
				resultMap.put("urlPath", getBasePath(request) + memberInfo.getPictureInfo().getUrlPath());
				resultMap.put("realName", decodeParam(memberInfo.getRealName()));
				resultMap.put("realNameStatus", memberInfo.getRealNameStatus());
				resultMap.put("vipStatus", memberInfo.getVip());
				resultMap.put("age", Integer.parseInt(DateUtils.toString(new Date(), "yyyy"))-Integer.parseInt(apply.getBirthday().substring(0, 4)));
				resultMap.put("height", apply.getHeight());
				resultMap.put("weight", apply.getWeight());
				resultMap.put("labelName", getLableName(apply.getSkillLabel()));
				resultMap.put("level", applyInfo.getLevel());
				resultMap.put("isView", applyInfo.getIsView());
				resultMap.put("isContact", applyInfo.getIsContact());
				resultMap.put("isVideo", applyInfo.getIsVideo());
				resultMap.put("resumeId", applyInfo.getResumeId());
				resultMap.put("id", applyInfo.getId());
				resultMap.put("optional", applyInfo.getOptional());//备选
				resultMap.put("urlVideo", getBasePath(request) + applyInfo.getFileInfo().getUrlPath());
				resultMap.put("contactMemberId", applyInfo.getMemberId());//投递人memberId
				resultMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
				resultList.add(resultMap);
			}
			map.put("success", "yes");
			map.put("data", resultList);
		}else{
			map.put("success", "yes");
			map.put("message", "没有应聘结果");
		}
		return map;
	}
	
	/**
	 * 角色应聘列表信息
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "rest/personal/getApplyInfoByRole")
	@ResponseBody
	public Map<String,Object> getApplyInfoByRole(HttpServletRequest request,HttpServletResponse response,
			RecruitApplyInfo info, Integer page, Integer rows, String type, String edit){
		Map<String,Object> map = new HashMap<String,Object>();
		
		if (edit == null || edit.equals("")) {
			RecruitRoleInfo roleInfo = roleInfoService.selectById(info.getRoleId());
			if (roleInfo != null) {								
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("id", roleInfo.getId());//角色ID
				resultMap.put("name", decodeParam(roleInfo.getName()));//角色姓名
				resultMap.put("urlPath", getBasePath(request) + roleInfo.getPictureInfo().getUrlPath());//角色图片地址
				resultMap.put("sex", roleInfo.getSex());//性别
				resultMap.put("paidType",roleInfo.getPaidType());//
				resultMap.put("paidMax",roleInfo.getPaidMax());//
				resultMap.put("paidMin",roleInfo.getPaidMin());//
				
				resultMap.put("paid",roleInfo.getPaidType().equals("面议") ? "面议" : 
					roleInfo.getPaidMax() == null || roleInfo.getPaidMax().equals("") ?
					roleInfo.getPaidMin() + roleInfo.getPaidType():
					roleInfo.getPaidMin() + "-" + roleInfo.getPaidMax() + roleInfo.getPaidType());
				resultMap.put("lableName",getLableName(decodeParam(roleInfo.getLableCode())));//类型
				resultMap.put("lableCode",getLableCode(decodeParam(roleInfo.getLableCode())));//类型code
				resultMap.put("requirement", decodeParam(roleInfo.getRequirement()));//导演要求
				resultMap.put("dialogue", decodeParam(roleInfo.getDialogue()));//试戏台词
				map.put("dataRole", resultMap);
			} else {				
				RecruitRoleInfoTemp roleInfoTemp = infoTempService.selectById(info.getRoleId());
				if (roleInfoTemp != null) {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put("id", roleInfoTemp.getRoleId());//角色ID
					resultMap.put("name", decodeParam(roleInfoTemp.getName()));//角色姓名
					resultMap.put("urlPath", getBasePath(request) + roleInfoTemp.getPictureInfo().getUrlPath());//角色图片地址
					resultMap.put("sex", roleInfoTemp.getSex());//性别
					resultMap.put("paidType",roleInfoTemp.getPaidType());//
					resultMap.put("paidMax",roleInfoTemp.getPaidMax());//
					resultMap.put("paidMin",roleInfoTemp.getPaidMin());//
					resultMap.put("paid",roleInfoTemp.getPaidType().equals("面议") ? "面议" : 
								roleInfoTemp.getPaidMax() == null || roleInfoTemp.getPaidMax().equals("") ?
								roleInfoTemp.getPaidMin() + roleInfoTemp.getPaidType():
								roleInfoTemp.getPaidMin() + "-" + roleInfoTemp.getPaidMax() + roleInfoTemp.getPaidType());
					resultMap.put("lableName",getLableName(decodeParam(roleInfoTemp.getLableCode())));//类型
					resultMap.put("lableCode",getLableCode(decodeParam(roleInfoTemp.getLableCode())));//类型code
					resultMap.put("requirement", decodeParam(roleInfoTemp.getRequirement()));//导演要求
					resultMap.put("dialogue", decodeParam(roleInfoTemp.getDialogue()));//试戏台词
					map.put("dataRole", resultMap);
					if(roleInfoTemp.getRoleId() != null && !roleInfoTemp.getRoleId().equals("")){
						info.setRoleId(roleInfoTemp.getRoleId());
					}
				} else {
					map.put("success", "no");
					map.put("message", "角色信息错误!");
					return map;
				}
				
				map.put("success", "yes");
				map.put("message", "没有应聘结果!");
				return map;
			}
		}else if(edit != null && edit.equals("1")){
			RecruitRoleInfoTemp roleInfo = infoTempService.selectById(info.getRoleId());
			if (roleInfo != null) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("id", roleInfo.getRoleId());//角色ID
				resultMap.put("name", decodeParam(roleInfo.getName()));//角色姓名
				resultMap.put("urlPath", getBasePath(request) + roleInfo.getPictureInfo().getUrlPath());//角色图片地址
				resultMap.put("sex", roleInfo.getSex());//性别
				resultMap.put("paidType",roleInfo.getPaidType());//
				resultMap.put("paidMax",roleInfo.getPaidMax());//
				resultMap.put("paidMin",roleInfo.getPaidMin());//
				resultMap.put("paid",roleInfo.getPaidType().equals("面议") ? "面议" : 
							roleInfo.getPaidMax() == null || roleInfo.getPaidMax().equals("") ?
							roleInfo.getPaidMin() + roleInfo.getPaidType():
							roleInfo.getPaidMin() + "-" + roleInfo.getPaidMax() + roleInfo.getPaidType());
				resultMap.put("lableName",getLableName(roleInfo.getLableCode()));//类型
				resultMap.put("lableCode",getLableCode(roleInfo.getLableCode()));//类型code
				resultMap.put("requirement", decodeParam(roleInfo.getRequirement()));//导演要求
				resultMap.put("dialogue", decodeParam(roleInfo.getDialogue()));//试戏台词
				map.put("dataRole", resultMap);
				if(roleInfo.getRoleId() != null && !roleInfo.getRoleId().equals("")){
					info.setRoleId(roleInfo.getRoleId());
				}
			} else {
				map.put("success", "no");
				map.put("message", "角色信息错误!");
				return map;
			}
		}
		PageInfo<RecruitApplyInfo> pageInfo = new PageInfo<RecruitApplyInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setSort("createDate");
		info.setOrder("desc");
		//info.setStatus("1");
		info.setVideoStatus("1");
		if (type != null) {
			if (type.equals("1")) {//已看
				info.setIsView("1");
			}
			if (type.equals("2")) {//未看
				info.setIsView("0");
			}
			if (type.equals("3")) {//备选
				info.setOptional("1");
			}
		}
		applyInfoService.selectAll(info, pageInfo);
		if(pageInfo.getRows() != null && pageInfo.getRows().size() > 0){
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); 
			for (RecruitApplyInfo applyInfo: pageInfo.getRows()) {
				Map<String,Object> resultMap = new HashMap<String,Object>();
				MemberResumeApply apply = applyInfo.getResumeApply();
				if(apply == null){
					continue;
				}
				MemberInfo memberInfo = applyInfo.getMemberInfo();
				resultMap.put("urlPath", getBasePath(request) + memberInfo.getPictureInfo().getUrlPath());
				resultMap.put("realName", decodeParam(memberInfo.getRealName()));
				resultMap.put("realNameStatus", memberInfo.getRealNameStatus());
				resultMap.put("vipStatus", memberInfo.getVip());
				resultMap.put("age", Integer.parseInt(DateUtils.toString(new Date(), "yyyy"))-Integer.parseInt(apply.getBirthday().substring(0, 4)));
				resultMap.put("height", apply.getHeight());
				resultMap.put("weight", apply.getWeight());
				resultMap.put("labelName", getLableName(decodeParam(apply.getSkillLabel())));
				resultMap.put("level", applyInfo.getLevel());
				resultMap.put("isView", applyInfo.getIsView());
				resultMap.put("isContact", applyInfo.getIsContact());
				resultMap.put("isVideo", applyInfo.getIsVideo());
				resultMap.put("resumeId", applyInfo.getResumeId());
				resultMap.put("id", applyInfo.getId());
				resultMap.put("optional", applyInfo.getOptional());//备选
				resultMap.put("urlVideo", getBasePath(request) + applyInfo.getFileInfo().getUrlPath());
				resultMap.put("contactMemberId", applyInfo.getMemberId());//投递人memberId
				resultMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
				resultList.add(resultMap);
			}
			map.put("success", "yes");
			map.put("data", resultList);
		}else{
			map.put("success", "yes");
			map.put("message", "没有应聘结果");
		}
		return map;
	}
	
	
	/**
	 * 试戏记录列表信息
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "rest/personal/getApplyInfoByMemberId")
	@ResponseBody
	public Map<String,Object> getApplyInfoByMemberId(HttpServletRequest request,HttpServletResponse response,
			RecruitApplyInfo info, Integer page, Integer rows){
		Map<String,Object> map = new HashMap<String,Object>();
		PageInfo<RecruitApplyInfo> pageInfo = new PageInfo<RecruitApplyInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setSort("createDate");
		info.setOrder("desc");
		applyInfoService.selectAll(info, pageInfo);
		if(pageInfo.getRows() != null && pageInfo.getRows().size() > 0){
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); 
			for (RecruitApplyInfo applyInfo: pageInfo.getRows()) {
				Map<String,Object> resultMap = new HashMap<String,Object>();
				RecruitRoleInfo roleInfo = applyInfo.getRoleInfo();
				RecruitInfo recruitInfo = roleInfo.getRecruitInfo();
				MemberInfo memberInfo = recruitInfo.getMemberInfo();
				resultMap.put("urlPath", getBasePath(request) + memberInfo.getPictureInfo().getUrlPath());
				resultMap.put("nickName", decodeParam(memberInfo.getNickname()));
				resultMap.put("identityStatus", memberInfo.getIdentityStatus());
				resultMap.put("vipStatus", memberInfo.getVip());
				resultMap.put("title", decodeParam(recruitInfo.getTitle()));
				resultMap.put("roleName", decodeParam(roleInfo.getName()));
				resultMap.put("createDate", roleInfo.getCreateDate());
				resultMap.put("updateDate", applyInfo.getUpdateDate());
				resultMap.put("paidType",roleInfo.getPaidType());//
				resultMap.put("paidMax",roleInfo.getPaidMax());//
				resultMap.put("paidMin",roleInfo.getPaidMin());//
				resultMap.put("paid", roleInfo.getPaidType().equals("面议") ? "面议":
					roleInfo.getPaidMax() == null || roleInfo.getPaidMax().equals("") ?
					roleInfo.getPaidMin() + roleInfo.getPaidType():
					roleInfo.getPaidMin() + "-" + roleInfo.getPaidMax() + roleInfo.getPaidType());
				resultMap.put("roleId", roleInfo.getId());
				resultMap.put("level", applyInfo.getLevel());//1-简历投递成功  3-剧组已查看简历 4-剧组关注了你  5-剧组联系了你
				resultMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
				resultList.add(resultMap);
			}
			map.put("success", "yes");
			map.put("data", resultList);
		}else{
			map.put("success", "yes");
			map.put("message", "没有数据");
		}
		return map;
	}
	
	/**
	 * 浏览记录列表信息
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "rest/personal/getActionsInfoByMemberId")
	@ResponseBody
	public Map<String,Object> getActionsInfoByMemberId(HttpServletRequest request,HttpServletResponse response,
			MemberActionsInfo info, Integer page, Integer rows){
		Map<String,Object> map = new HashMap<String,Object>();
		PageInfo<MemberActionsInfo> pageInfo = new PageInfo<MemberActionsInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setSort("createDate");
		info.setOrder("desc");
		actionsInfoService.selectAll(info, pageInfo);
		if(pageInfo.getRows() != null && pageInfo.getRows().size() > 0){
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); 
			for (MemberActionsInfo actionsInfo: pageInfo.getRows()) {
				Map<String,Object> resultMap = new HashMap<String,Object>();
				MemberInfo memberInfo = actionsInfo.getMemberInfo();
				RecruitRoleInfo roleInfo = actionsInfo.getRoleInfo();
				resultMap.put("nickName", decodeParam(memberInfo.getNickname()));
				resultMap.put("title", decodeParam(roleInfo.getRecruitInfo().getTitle()));
				resultMap.put("roleName", decodeParam(roleInfo.getName()));
				resultMap.put("createDate", roleInfo.getCreateDate());
				resultMap.put("roleId", roleInfo.getId());
				resultMap.put("urlPath", getBasePath(request) + memberInfo.getPictureInfo().getUrlPath());
				resultMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
				resultList.add(resultMap);
			}
			map.put("success", "yes");
			map.put("data", resultList);
		}else{
			map.put("success", "yes");
			map.put("message", "没有数据");
		}
		return map;
	}
	
	
	/**
	 * 设置/取消备选
	 * @param request
	 * @param response
	 * @param info
	 * @param setting
	 * @return
	 */
	@RequestMapping(value = "rest/personal/setupCandidate")
	@ResponseBody
	public Map<String,Object> setupCandidate(HttpServletRequest request,HttpServletResponse response,
			RecruitApplyInfo info , String setting){
		Map<String,Object> map = new HashMap<String,Object>();
		int result = 0;
		RecruitApplyInfo applyInfo = applyInfoService.selectById(info.getId());
		if(setting != null && setting.equals("1")){
			applyInfo.setLevel("4");
			applyInfo.setOptional("1");
		}
		else if(setting != null && setting.equals("0")){
			applyInfo.setLevel("3");
			applyInfo.setOptional("0");
		}
		result = applyInfoService.update(applyInfo);
		if(result > 0){
			map.put("success", "yes");
			map.put("message", "操作成功");
		}else{
			map.put("success", "no");
			map.put("message", "操作失败");
		}
		return map;
	}
	
	
	/**
	 * 我的关注 招募列表信息
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "rest/personal/myFollowRecruit")
	@ResponseBody
	public Map<String,Object> myFollowRecruit(HttpServletRequest request,HttpServletResponse response,
			MemberFollowInfo info, Integer page, Integer rows){
		Map<String,Object> map = new HashMap<String,Object>();
		List<MemberFollowInfo> list = followInfoService.selectAll(info);
		List<String> idList = new ArrayList<String>();
		if(list != null && list.size() > 0){
			for (MemberFollowInfo followInfo: list) {
				idList.add(followInfo.getFollowMemberId());
			}
			Map<String,Object> condition = new HashMap<String,Object>();
			PageInfo<RecruitInfo> pageInfo = new PageInfo<RecruitInfo>();
			pageInfo.setPage(page);
			pageInfo.setPageSize(rows);
			condition.put("list", idList);
//			condition.put("dateNow", DateUtils.currentYourDate("yyyy-MM-dd"));
			condition.put("dateNow", "1");
			condition.put("sort", "");
			condition.put("order", "publishStatus desc , createDate desc");
			recruitInfoService.batchSelectByMemberId(condition, pageInfo);
			map.put("totle", pageInfo.getTotal());
			map.put("page", pageInfo.getPage());
			map.put("pageSize", pageInfo.getPageSize());
			if(pageInfo.getRows() == null || pageInfo.getRows().size()<=0){
				map.put("success", "yes");
				map.put("message", "无结果!");
			}else{
				List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); 
				for(RecruitInfo recruitInfo : pageInfo.getRows()){
					Map<String,Object> resultMap = new HashMap<String,Object>();
					resultMap.put("id", recruitInfo.getId());
					MemberInfo memberInfo = recruitInfo.getMemberInfo();
					resultMap.put("name", decodeParam(memberInfo.getNickname()));
					resultMap.put("urlPath", getBasePath(request) + memberInfo.getPictureInfo().getUrlPath());
					resultMap.put("vipStatus", memberInfo.getVip());
					resultMap.put("title", decodeParam(recruitInfo.getTitle()));//标题
					RecruitCategory category = recruitInfo.getCategory();
					resultMap.put("lableName", category.getName());//类型
					resultMap.put("lableColor", category.getColor());//类型颜色
					resultMap.put("cityCode", recruitInfo.getCityCode());//城市编码
					resultMap.put("cityName", recruitInfo.getCity().getCityName());//城市名称
					resultMap.put("views", recruitInfo.getViewNum());//浏览数
					resultMap.put("publishStatus", recruitInfo.getPublishStatus());//加急状态  1加急 
					resultMap.put("type", recruitInfo.getType());//招募类型 1-官方招募  2-私人招募
					if(recruitInfo.getType() != null && recruitInfo.getType().equals("1")){//官方认证返回关注数
						Map<String, Object> followMap = new HashMap<String, Object>();
						followMap.put("followMemberId", recruitInfo.getMemberId());
						resultMap.put("follows", followInfoService.selectCount(followMap));//关注数
						resultMap.put("identity", memberInfo.getIdentityInfo());//身份
					}
					resultMap.put("remainTime", DateUtils.getDateSubtraction(recruitInfo.getDeadlineDate(), new Date()));//剩余天数
					resultMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
					resultList.add(resultMap);
				}
				map.put("success", "yes");
				map.put("data", resultList);
				
			}
		}else{
			map.put("success", "yes");
			map.put("message", "无关注");
		}
		return map;
	}
	
	
	/**
	 * 我的关注 用户列表信息
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "rest/personal/myFollowMember")
	@ResponseBody
	public Map<String,Object> myFollowMember(HttpServletRequest request,HttpServletResponse response,
			MemberFollowInfo info){
		Map<String,Object> map = new HashMap<String,Object>();
		List<MemberFollowInfo> list = followInfoService.selectAll(info);
		if(list != null && !list.isEmpty()){
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); 
			for (MemberFollowInfo followInfo: list) {
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.put("id", followInfo.getId());
				resultMap.put("recruitMemberId", followInfo.getFollowMemberId());
				MemberInfo memberInfo = followInfo.getFollowMemberInfo();
				resultMap.put("name", decodeParam(memberInfo.getNickname()));
				resultMap.put("urlPath", getBasePath(request) + memberInfo.getPictureInfo().getUrlPath());
				resultMap.put("identityStatus", memberInfo.getIdentityStatus());// 1-官方  0-私人
				resultMap.put("vipStatus", memberInfo.getVip());// 1-v
				resultMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
				resultList.add(resultMap);
			}
			map.put("success", "yes");
			map.put("data", resultList);
		}else{
			map.put("success", "yes");
			map.put("message", "无关注");
		}
		return map;
	}
	
	/**
	 * 修改状态  查看，联系，查看20秒
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "rest/personal/recruitApplyUpdateStatus")
	@ResponseBody
	public Map<String,Object> recruitApplyUpdateStatus(HttpServletRequest request,HttpServletResponse response,
			RecruitApplyInfo info){
		Map<String,Object> map = new HashMap<String,Object>();
		int result = applyInfoService.update(info);
		if(result > 0){
			map.put("success", "yes");
			map.put("message", "成功");
		}else{
			map.put("success", "no");
			map.put("message", "失败");
		}
		return map;
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
	
	/**
	 * 保存图片
	 * @param root
	 * @param meetingImgUuid
	 * @param listfile
	 * @return
	 */
	private int saveImg(String root, String meetingImgUuid,
			MultipartFile listfile) {
		String pathTmp = Constant.UPLOAD_MEMBER_PATH + "/";
		String path = pathTmp + DateUtils.toString(new Date(), "yyyy/MM/dd")
				+ "/";
		String realPath = root + path;
		FileInfo imageInfo = FileTool.saveFile(listfile, realPath, 0, 0);
		String urlPath = path + imageInfo.getRealName();
		SystemPictureInfo pictureInfo = new SystemPictureInfo();
		pictureInfo.setId(UUIDUtil.getUUID());
		pictureInfo.setUuid(meetingImgUuid);
		pictureInfo.setUrlPath(urlPath);
		pictureInfo.setFwidth(imageInfo.getWidth());
		pictureInfo.setFheight(imageInfo.getHeight());
		pictureInfo.setName(imageInfo.getRealName());
		pictureInfo.setSuffix(imageInfo.getSuffix());
		pictureInfo.setCdate(DateUtils.toString(new Date(),
				"yyyy-MM-dd HH:mm:ss"));
		systemPictureInfoService.insert(pictureInfo);
		return 0;
	}

	/**
	 * 添加图片
	 * @param id
	 * @param root
	 * @return
	 */
	private int deleteImg(String uuid, String root) {
		if (uuid != null && !uuid.equals("")) {
			// 删除旧图片
			SystemPictureInfo info = systemPictureInfoService.selectEntityByUuid(uuid);
			if (info != null) {
				String delRealPath = info.getUrlPath();
				if (delRealPath != null) {
					deleteFile(root + delRealPath);
					systemPictureInfoService.delete(info);
				}
			}
		}
		return 0;
	}
	
	/**
	 * 获取标签名称
	 * @param roleInfo
	 */
	private String getLableCode(String lable){
		if(lable != null && lable.length() > 0){
			String first = lable.substring(0, 1);
			if(first.equals(",")){
				return lable.substring(1, lable.length());
			}
		}
		return lable;
	}
}
