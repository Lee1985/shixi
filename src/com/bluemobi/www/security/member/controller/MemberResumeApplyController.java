package com.bluemobi.www.security.member.controller;


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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.constant.Constant;
import com.bluemobi.www.constant.ResumeStatusEnum;
import com.bluemobi.www.data.model.member.MemberResumeApply;
import com.bluemobi.www.data.model.member.MemberResumeApplyPhotos;
import com.bluemobi.www.data.model.member.MemberResumeInfo;
import com.bluemobi.www.data.model.member.MemberResumeInfoPhotos;
import com.bluemobi.www.data.model.member.MemberResumeTemplate;
import com.bluemobi.www.data.model.member.MemberResumeTemplatePhotos;
import com.bluemobi.www.data.model.message.MessagePushNotification;
import com.bluemobi.www.data.model.recruit.RecruitApplyInfo;
import com.bluemobi.www.data.model.system.SystemHotspotCity;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberResumeApplyPhotosService;
import com.bluemobi.www.security.member.service.MemberResumeApplyService;
import com.bluemobi.www.security.member.service.MemberResumeInfoPhotosService;
import com.bluemobi.www.security.member.service.MemberResumeInfoService;
import com.bluemobi.www.security.member.service.MemberResumeTemplatePhotosService;
import com.bluemobi.www.security.member.service.MemberResumeTemplateService;
import com.bluemobi.www.security.message.service.MessagePushNotificationService;
import com.bluemobi.www.security.recruit.service.RecruitApplyInfoService;
import com.bluemobi.www.security.system.service.SystemHotspotCityService;
import com.bluemobi.www.security.system.service.SystemLableInfoService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: MemberResumeApplyController 
* @Description: 简历申请控制层 
* @author sundq 
* @date 2016-02-22 09:46:38 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class MemberResumeApplyController extends BaseController {
	@Resource
	private MemberResumeApplyService service;
	@Resource
	private MemberResumeInfoService infoService;
	@Resource
	private MemberResumeApplyPhotosService photosService;
	@Resource
	private MemberResumeInfoPhotosService infoPhotosService;
	@Resource
	private SystemLableInfoService lableInfoService;
	@Resource
	private SystemHotspotCityService cityService;
	@Resource
	private RecruitApplyInfoService applyInfoService;
	@Resource
	private MessagePushNotificationService notificationService;	
	@Resource
	private MemberResumeTemplateService memberResumeTemplateService;
	@Resource
	private MemberResumeTemplatePhotosService memberResumeTemplatePhotosService;
	
	@RequestMapping(value = "member/memberResumeApplyList")
	public String memberResumeApplyList(HttpServletRequest request,
			HttpServletResponse response) {
		return "member/member_resume_apply_list";
	}

	@RequestMapping(value = "member/memberResumeApplyAjaxPage")
	@ResponseBody
	public PageInfo<MemberResumeApply> memberResumeApplyAjaxPage(HttpServletRequest request,
			HttpServletResponse response, MemberResumeApply info, Integer page,
			Integer rows) {
		PageInfo<MemberResumeApply> pageInfo = new PageInfo<MemberResumeApply>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		if(info.getStatus() == null){
			info.setStatus(0);
		}
		if(info.getSort().equals("id")){
			info.setSort("updateDate");
			info.setOrder("desc");
		}
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "member/memberResumeApplyAjaxAll")
	@ResponseBody
	public List<MemberResumeApply> memberResumeApplyAjaxAll(HttpServletRequest request,
			HttpServletResponse response, MemberResumeApply info, Integer page,
			Integer rows) {
		List<MemberResumeApply> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "member/memberResumeApplyAjaxSave")
	@ResponseBody
	public Map<String,Object> memberResumeApplyAjaxSave(HttpServletRequest request,
			HttpServletResponse response, MemberResumeApply info) {
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

	@RequestMapping(value = "member/memberResumeApplyAjaxDelete")
	@ResponseBody
	public Map<String,Object> memberResumeApplyAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, MemberResumeApply info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
	
	@RequestMapping(value = "member/memberResumeTemplateById")
	public String memberResumeTemplateById(HttpServletRequest request,
			HttpServletResponse response,Model model, String id) {
		MemberResumeTemplate info = new MemberResumeTemplate();
		List<MemberResumeTemplatePhotos> photosList = new ArrayList<MemberResumeTemplatePhotos>();
		if(id != null && !id.equals("")){
			info = memberResumeTemplateService.selectById(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resumeId", id);
			photosList = memberResumeTemplatePhotosService.selectAll(map);	
		}
		getRoleLableTemplateName(info);//赋值角色名称
		getSkillLableTemplateName(info);//赋值技能名称
		getCityTemplateName(info);
		model.addAttribute("entity", info);
		model.addAttribute("photosList", photosList);
		return "member/member_resume_apply_detail";
	}
	
	@RequestMapping(value = "member/memberResumeApplyUpdateStatus")
	@ResponseBody
	public Map<String,Object> messageOfficialNoticeUpdateStatus(HttpServletRequest request,
			HttpServletResponse response, MemberResumeApply info) {
		int result = 0;
		try {
			if (info.getStatus() != null) {
				if(!info.getIds().isEmpty() && !info.getApplyIds().isEmpty()){
					String[] ids = info.getIds().split(",");
					String[] applyIds = info.getApplyIds().split(",");
					for (int i = 0; i < ids.length; i++) {
						if (info.getStatus().equals("1")) {//审核通过 1.复制简历信息 2.修改申请状态
							MemberResumeApply apply = service.selectById(ids[i]);
							MemberResumeInfo resumeInfo = infoService.selectEntityByMemberId(apply.getMemberId());				
							String id = resumeInfo.getId();
							BeanUtils.copyProperties(apply, resumeInfo);
							if(id == null || id.equals("")){
								resumeInfo.setId(UUIDUtil.getUUID());
								resumeInfo.setStatus("1");
								infoService.insert(resumeInfo);
							}else{
								resumeInfo.setId(id);
								resumeInfo.setStatus("1");
								infoService.update(resumeInfo);
							}
							//保存对应剧照信息
							Map<String, Object> conditionInfo = new HashMap<String, Object>();
							conditionInfo.put("resumeId", resumeInfo.getId());
							List<MemberResumeInfoPhotos> infoPhotos = infoPhotosService.selectAll(conditionInfo);
							for (MemberResumeInfoPhotos photos : infoPhotos) {
								infoPhotosService.delete(photos);
							}
							//保存对应剧照信息
							Map<String, Object> conditionApply = new HashMap<String, Object>();
							conditionApply.put("resumeId", apply.getId());
							List<MemberResumeApplyPhotos> applyPhotos = photosService.selectAll(conditionApply);
							for (MemberResumeApplyPhotos photos : applyPhotos) {
								MemberResumeInfoPhotos resumeInfoNew = new MemberResumeInfoPhotos();
								BeanUtils.copyProperties(photos, resumeInfoNew);
								resumeInfoNew.setId(UUIDUtil.getUUID());
								resumeInfoNew.setResumeId(resumeInfo.getId());
								infoPhotosService.insert(resumeInfoNew);
							}
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("resumeId", apply.getId());
							RecruitApplyInfo applyInfo = applyInfoService.selectEntity(map);
							if(applyInfo.getVideoStatus() != null && applyInfo.getVideoStatus().equals("1")){
								applyInfo.setLevel("2");
								applyInfoService.update(applyInfo);
							}
							
						}
//						result = service.update(info);
						
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", applyIds[i]);
						map.put("status", info.getStatus());
						result = applyInfoService.update(map);
						
						RecruitApplyInfo applyInfo = applyInfoService.selectById(applyIds[i]);
						MemberResumeApply apply = service.selectById(ids[i]);
						if (info.getStatus() != null && applyInfo.getStatus().equals("2")) {
							MessagePushNotification notification = new MessagePushNotification();
							notification.setId(UUIDUtil.getUUID());
							notification.setTitle("简历审核");
							notification.setType(Constant.PUSH_EDUCATION);
							notification.setReceiveId(apply.getMemberId());
							notification.setContent(info.getReply());
							notification.setCreateDate(DateUtils.currentStringDate());
							notification.setStatus("1");
							notification.setCid(apply.getMemberInfo().getCid());
							notificationService.insert(notification);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
	
	@RequestMapping(value = "member/memberResumeAudit")
	@ResponseBody
	public Map<String,Object> memberResumeAudit(HttpServletRequest request,
			HttpServletResponse response, String ids,String status,String replyContent) {
		int result = 0;
		try {
			if (status == null) {
				return getJsonResult(result,"操作成功", "操作失败！");
			}
			if(StringUtils.isBlank(ids)){
				return getJsonResult(result,"操作成功", "操作失败！");
			}
			String[] idsArray = ids.split(",");
			for (String id : idsArray) {
				MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectById(id);
				if(memberResumeTemplate == null){
					continue;
				}
				if (status.equals("1")) {
					//审核通过,保存审核通过信息 					
					memberResumeTemplate.setStatus(ResumeStatusEnum.AUDITED.getCode());
					memberResumeTemplate.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					result = memberResumeTemplateService.update(memberResumeTemplate);					
					MemberResumeApply memberResumeApply = service.selectById(id);
					if(memberResumeApply == null){
						memberResumeApply = new MemberResumeApply();
						BeanUtils.copyProperties(memberResumeTemplate,memberResumeApply);
						result = service.insert(memberResumeApply);
					}else{
						BeanUtils.copyProperties(memberResumeTemplate,memberResumeApply);
						result = service.update(memberResumeApply);
					}
					
					//保存剧照信息
					Map<String, Object> conditionInfo = new HashMap<String, Object>();
					conditionInfo.put("resumeId", memberResumeTemplate.getId());
					List<MemberResumeTemplatePhotos> photoList = memberResumeTemplatePhotosService.selectAll(conditionInfo);
					if(photoList == null || photoList.isEmpty()){
						continue;
					}
					List<MemberResumeApplyPhotos> resumeApplyPhotoList = photosService.selectAll(conditionInfo);
					if(resumeApplyPhotoList == null || resumeApplyPhotoList.isEmpty()){
						//拷贝剧照信息
						for(MemberResumeTemplatePhotos resumeTemplatePhotos : photoList){
							MemberResumeApplyPhotos memberResumeApplyPhotos = new MemberResumeApplyPhotos();
							BeanUtils.copyProperties(resumeTemplatePhotos, memberResumeApplyPhotos);
							result = photosService.insert(memberResumeApplyPhotos);
						}
					}else{
						//删除剧照信息
						for(MemberResumeApplyPhotos resumeTemplatePhotos : resumeApplyPhotoList){
							result = photosService.delete(resumeTemplatePhotos);
						}
						//拷贝剧照信息
						for(MemberResumeTemplatePhotos resumeTemplatePhotos : photoList){
							MemberResumeApplyPhotos memberResumeApplyPhotos = new MemberResumeApplyPhotos();
							BeanUtils.copyProperties(resumeTemplatePhotos, memberResumeApplyPhotos);
							result = photosService.insert(memberResumeApplyPhotos);
						}
					}					
				}else if("2".equals(status)){
					//审核拒绝
					memberResumeTemplate.setStatus(ResumeStatusEnum.AUDIT_REFUSED.getCode());
					memberResumeTemplate.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					result = memberResumeTemplateService.update(memberResumeTemplate);
					
					MessagePushNotification notification = new MessagePushNotification();
					notification.setId(UUIDUtil.getUUID());
					notification.setTitle("简历审核");
					notification.setType(Constant.PUSH_EDUCATION);
					notification.setReceiveId(memberResumeTemplate.getMemberId());
					replyContent = replyContent.replaceAll("<br>", "\n").replaceAll("<br/>", "\n");
					//推送<br>换\n
					notification.setContent(replyContent);
					notification.setCreateDate(DateUtils.currentStringDate());
					notification.setStatus("1");
					notification.setCid(memberResumeTemplate.getMemberInfo().getCid());
					notificationService.insert(notification);
				}								
			}					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
	
	/**
	 * 赋值角色
	 * @param applyInfo
	 */
	private void getRoleLableTemplateName(MemberResumeTemplate resumeInfo){			
		String lableName = "";
		String lable = resumeInfo.getRoleLabel();
		if (lable != null) {
			String[] lables = lable.split(",");
			for(String name : lables){
				if(StringUtils.isNotBlank(name)){
					lableName += name + ",";
				}				
			}			
		}
		if (lableName.equals("")) {
			resumeInfo.setRoleLabel("");
		}
		resumeInfo.setRoleLabel(lableName.substring(0, lableName.length() - 1));
	}
	
	/**
	 * 赋值技能
	 * @param applyInfo
	 */
	private void getSkillLableTemplateName(MemberResumeTemplate resumeInfo){
		String lableName = "";
		String lable = resumeInfo.getSkillLabel();
		
		if (lable != null) {
			String[] lables = lable.split(",");
			for(String name : lables){
				if(StringUtils.isNotBlank(name)){
					lableName += name + ",";
				}				
			}			
		}
		if (lableName.equals("")) {
			resumeInfo.setSkillLabel("");
		}
		resumeInfo.setSkillLabel(lableName.substring(0, lableName.length() - 1));				
	}
	
	/**
	 * 常驻城市
	 * @param applyInfo
	 */
	private void getCityTemplateName(MemberResumeTemplate templateInfo){
		String codeName = "";
		String code = templateInfo.getCityCode();
		if(code != null){
			String[] codes = code.split(",");
			for (int j = 0; j < codes.length; j++) {
				if (codes[j]!=null && !codes[j].equals("")) {
					SystemHotspotCity info = cityService.selectById(codes[j]);
					if (info!=null && info.getCityName()!=null) {
						codeName = codeName + info.getCityName() + ",";
					}
				}
			}
			templateInfo.setCityName(codeName.substring(0, codeName.length()-1));
		}
	}
}
