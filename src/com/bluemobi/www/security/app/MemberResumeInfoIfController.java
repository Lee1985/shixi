package com.bluemobi.www.security.app;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.bluemobi.www.constant.Constant;
import com.bluemobi.www.constant.ResumeStatusEnum;
import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.member.MemberResumeTemplate;
import com.bluemobi.www.data.model.member.MemberResumeTemplatePhotos;
import com.bluemobi.www.data.model.system.SystemFileInfo;
import com.bluemobi.www.data.model.system.SystemHotspotCity;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberInfoService;
import com.bluemobi.www.security.member.service.MemberResumeTemplatePhotosService;
import com.bluemobi.www.security.member.service.MemberResumeTemplateService;
import com.bluemobi.www.security.system.service.SystemFileInfoService;
import com.bluemobi.www.security.system.service.SystemHotspotCityService;
import com.bluemobi.www.security.system.service.SystemLableInfoService;
import com.bluemobi.www.security.system.service.SystemPictureInfoService;
import com.bluemobi.www.utils.CustomPropertiesUtils;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 个人简历相关信息接口类
 * @author lip
 *
 */
@Controller
public class MemberResumeInfoIfController extends BaseController{
	
	@Resource
	private MemberResumeTemplateService memberResumeTemplateService;
	
	@Resource
	private MemberInfoService memberInfoService;
	
	@Resource
	private SystemHotspotCityService systemHotspotCityService; 
	
	@Resource
	private SystemPictureInfoService systemPictureInfoService;
	
	@Resource
	private SystemLableInfoService systemLableInfoService;
	
	@Resource
	private SystemFileInfoService systemFileInfoService;
	
	@Resource
	private MemberResumeTemplatePhotosService memberResumeTemplatePhotosService;
		
	/**
	 * 查询简历基本信息 
	 * @param request
	 * @param response
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/resume_basic_info_if")
	@ResponseBody
	public Map<String,Object> resume_basic_info_if(HttpServletRequest request,HttpServletResponse response,
			String memberId) throws Exception{		
		Map<String,Object> map = new HashMap<String,Object>();
		//查询简历
		MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(memberId);
		MemberInfo memberInfo = memberInfoService.selectById(memberId);
		if(memberInfo == null){
			map.put("success", "no");
			map.put("message", "获取用户信息失败!");
			return map;
		}
		Map<String,Object> basicMap = new HashMap<String,Object>();
		if(memberResumeTemplate == null){
			basicMap.put("realName", memberInfo.getRealName() == null ? "" : decodeParam(memberInfo.getRealName()));
			basicMap.put("sex", memberInfo.getSex() == null ? "" : memberInfo.getSex());			
			basicMap.put("nickname", memberInfo.getNickname() == null ? "" : decodeParam(memberInfo.getNickname()));		
			basicMap.put("school", memberInfo.getSchool() == null ? "" : decodeParam(memberInfo.getSchool()));	
			//城市
			String cityCode = memberInfo.getCityCode();
			Map<String,Object> cityMap = getCityInfo(cityCode);
			basicMap.putAll(cityMap);						
			basicMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
			basicMap.put("identityStatus", memberInfo.getIdentityStatus() == null ? "" : memberInfo.getIdentityStatus());
			basicMap.put("educationStatus", memberInfo.getEducationStatus() == null ? "" : memberInfo.getEducationStatus());
			
			//头像
			String uuid = memberInfo.getImgUuid();
			SystemPictureInfo systemPictureInfo = systemPictureInfoService.selectByUuid(uuid);
			if(systemPictureInfo != null){
				basicMap.put("headUrl", getBasePath(request) + systemPictureInfo.getUrlPath());
			}else{
				basicMap.put("headUrl", "");
			}			
			basicMap.put("weight", "");
			basicMap.put("height", "");
			basicMap.put("hipline", "");
			basicMap.put("chest", "");
			basicMap.put("waist", "");
			basicMap.put("roleLableNames", "");
			basicMap.put("skillLabelNames", "");
		}else{
			basicMap = getResumeBasicInfo(basicMap, memberResumeTemplate, memberInfo,request);
		}
		map.put("success", "yes");
		map.put("basicInfo", basicMap);
		return map;
	}
	
	/**
	 * 基本信息编辑
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/resume_basic_edit_if")
	@ResponseBody
	public Map<String,Object> resume_basic_edit_if(HttpServletRequest request,HttpServletResponse response,
			String memberId, MemberResumeTemplate info,MultipartFile imageFile){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberInfo memberInfo = memberInfoService.selectById(memberId);
		if(memberInfo == null){
			map.put("success", "no");
			map.put("message", "找不到用户信息！");
			return map;
		}
		MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(memberId);
		int result = 0;
		if(memberResumeTemplate == null){
			//添加简历
			memberResumeTemplate = new MemberResumeTemplate();
			memberResumeTemplate.setRealName(encodeParam(info.getRealName()));
			memberResumeTemplate.setSex(info.getSex());
			memberResumeTemplate.setHeight(info.getHeight());
			memberResumeTemplate.setWeight(info.getWeight());
			memberResumeTemplate.setBirthday(info.getBirthday());
			memberResumeTemplate.setChest(info.getChest());
			memberResumeTemplate.setWaist(info.getWaist());
			memberResumeTemplate.setHipline(info.getHipline());
			memberResumeTemplate.setSchool(encodeParam(info.getSchool()));
			memberResumeTemplate.setCityCode(info.getCityCode());
			if(StringUtils.isNotBlank(info.getRoleLabel())){
				memberResumeTemplate.setRoleLabel(encodeParam("," + info.getRoleLabel()));
			}
			if(StringUtils.isNotBlank(info.getSkillLabel())){
				memberResumeTemplate.setSkillLabel("," + encodeParam("," + info.getSkillLabel()));
			}
			memberResumeTemplate.setId(UUIDUtil.getUUID());
			memberResumeTemplate.setMemberId(memberId);
			memberResumeTemplate.setStatus(ResumeStatusEnum.UNCOMMITED.getCode());
			memberResumeTemplate.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			memberResumeTemplate.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			if(imageFile != null){
				String imgUuid = uploadAndGetImageId(request,imageFile);
				info.setImgUuid(imgUuid);
				memberInfo.setImgUuid(imgUuid);
			}else{
				memberResumeTemplate.setImgUuid(memberInfo.getImgUuid());
			}
			result = memberResumeTemplateService.insert(memberResumeTemplate);
			
			double percent = generateResumePercent(memberResumeTemplate);
			double resumeCompleteLimit = Double.parseDouble(CustomPropertiesUtils.getValue("resume_complete_limit"));
			if(percent >= resumeCompleteLimit){
				//修改为新建审核中和完成度
				memberResumeTemplate.setStatus(ResumeStatusEnum.NEW_AUDTING.getCode());				
			}			
			memberResumeTemplate.setCompletion(percent * 100);
			result = memberResumeTemplateService.update(memberResumeTemplate);
		}else{
			memberResumeTemplate.setRealName(encodeParam(info.getRealName()));
			memberResumeTemplate.setSex(info.getSex());
			memberResumeTemplate.setHeight(info.getHeight());
			memberResumeTemplate.setWeight(info.getWeight());
			memberResumeTemplate.setBirthday(info.getBirthday());
			memberResumeTemplate.setChest(info.getChest());
			memberResumeTemplate.setWaist(info.getWaist());
			memberResumeTemplate.setHipline(info.getHipline());
			memberResumeTemplate.setSchool(encodeParam(info.getSchool()));
			memberResumeTemplate.setCityCode(info.getCityCode());			
			if(StringUtils.isNotBlank(info.getRoleLabel())){
				memberResumeTemplate.setRoleLabel(encodeParam(info.getRoleLabel()));
			}
			if(StringUtils.isNotBlank(info.getSkillLabel())){
				memberResumeTemplate.setSkillLabel(encodeParam(info.getSkillLabel()));
			}						
			memberResumeTemplate.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			if(imageFile != null){
				String imgUuid = uploadAndGetImageId(request,imageFile);
				info.setImgUuid(imgUuid);
				memberInfo.setImgUuid(imgUuid);
			}else{
				memberResumeTemplate.setImgUuid(memberInfo.getImgUuid());
			}
			
			//如果该条记录审核通过,则将状态改为"修改审核中"
			double percent = generateResumePercent(memberResumeTemplate);
			if(memberResumeTemplate.getStatus().equals(ResumeStatusEnum.AUDITED.getCode())){
				memberResumeTemplate.setStatus(ResumeStatusEnum.MODIFY_AUDITING.getCode());
			}else if(memberResumeTemplate.getStatus().equals(ResumeStatusEnum.UNCOMMITED.getCode())){
				double resumeCompleteLimit = Double.parseDouble(CustomPropertiesUtils.getValue("resume_complete_limit"));
				if(percent >= resumeCompleteLimit){
					//修改为新建审核中和完成度
					memberResumeTemplate.setStatus(ResumeStatusEnum.NEW_AUDTING.getCode());				
				}
			}
			memberResumeTemplate.setCompletion(percent * 100);
			result = memberResumeTemplateService.update(memberResumeTemplate);
		}		
		if(memberInfo != null && !memberInfo.getEducationStatus().equals("1")){
			memberInfo.setSchool(encodeParam(info.getSchool()));
		}
		if(result > 0){
			memberInfo.setCityCode(info.getCityCode());
			memberInfo.setSex(info.getSex());
			memberInfo.setRealName(encodeParam(info.getRealName()));
			result = memberInfoService.update(memberInfo);
		}
		if(result > 0){
			map.put("success", "yes");
			map.put("message", "保存基本信息成功!");
		}else{
			map.put("success", "no");
			map.put("message", "保存基本信息失败!");
		}
		return map;
	}
	
	/**
	 * 见组照查询
	 * @param request
	 * @param response
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/resume_image_info_if")
	@ResponseBody
	public Map<String,Object> resume_image_info_if(HttpServletRequest request,HttpServletResponse response,String memberId){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(memberId);
		Map<String,Object> imageMap = new HashMap<String,Object>();
		if(memberResumeTemplate != null){
			imageMap = getResumeImages(request, imageMap, memberResumeTemplate);
		}
		if(imageMap == null){
			imageMap = new HashMap<String,Object>();
		}
		map.put("success", "yes");
		map.put("images", imageMap);
		return map;
	}
	
	/**
	 * 见组照编辑
	 * @param request
	 * @param response
	 * @param memberId
	 * @param frontImage
	 * @param leftImage
	 * @param left45Image
	 * @param rightImage
	 * @param right45Image
	 * @param fullImage
	 * @return
	 */
	@RequestMapping(value = "/resume_image_edit_if")
	@ResponseBody
	public Map<String,Object> resume_image_edit_if(HttpServletRequest request,HttpServletResponse response,
			String memberId, MultipartFile frontImage,MultipartFile leftImage,MultipartFile left45Image,
			MultipartFile rightImage,MultipartFile right45Image,MultipartFile fullImage){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(memberId);
		int result = 0;
		if(memberResumeTemplate == null){
			boolean uploadFlag = false;
			memberResumeTemplate = new MemberResumeTemplate();
			if(frontImage != null){
				String frontImageUuid = uploadAndGetImageId(request, frontImage);
				memberResumeTemplate.setImgUuid1(frontImageUuid);
				uploadFlag = true;
			}
			if(leftImage != null){
				String frontImageUuid = uploadAndGetImageId(request, leftImage);
				memberResumeTemplate.setImgUuid2(frontImageUuid);
				uploadFlag = true;
			}
			if(left45Image != null){
				String left45ImageUuid = uploadAndGetImageId(request, left45Image);
				memberResumeTemplate.setImgUuid3(left45ImageUuid);
				uploadFlag = true;
			}
			if(rightImage != null){
				String rightImageUuid = uploadAndGetImageId(request, rightImage);
				memberResumeTemplate.setImgUuid4(rightImageUuid);
				uploadFlag = true;
			}
			if(right45Image != null){
				String right45ImageUuid = uploadAndGetImageId(request, right45Image);
				memberResumeTemplate.setImgUuid5(right45ImageUuid);
				uploadFlag = true;
			}
			if(fullImage != null){
				String fullImageUuid = uploadAndGetImageId(request, fullImage);
				memberResumeTemplate.setImgUuid6(fullImageUuid);
				uploadFlag = true;
			}			
			if(!uploadFlag){
				map.put("success", "no");
				map.put("message","请上传至少一张图片!");
				return map;
			}			
			memberResumeTemplate.setId(UUIDUtil.getUUID());
			memberResumeTemplate.setMemberId(memberId);
			memberResumeTemplate.setStatus(ResumeStatusEnum.UNCOMMITED.getCode());
			memberResumeTemplate.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			memberResumeTemplate.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			result = memberResumeTemplateService.insert(memberResumeTemplate);
						
			double percent = generateResumePercent(memberResumeTemplate);
			double resumeCompleteLimit = Double.parseDouble(CustomPropertiesUtils.getValue("resume_complete_limit"));
			if(percent >= resumeCompleteLimit){
				//修改为新建审核中和完成度
				memberResumeTemplate.setStatus(ResumeStatusEnum.NEW_AUDTING.getCode());				
			}
			memberResumeTemplate.setCompletion(percent * 100);
			result = memberResumeTemplateService.update(memberResumeTemplate);
										
		}else{
			boolean uploadFlag = false;			
			if(frontImage != null){
				String frontImageUuid = uploadAndGetImageId(request, frontImage);
				memberResumeTemplate.setImgUuid1(frontImageUuid);
				uploadFlag = true;
			}
			if(leftImage != null){
				//左侧
				String leftImageUuid = uploadAndGetImageId(request, leftImage);
				memberResumeTemplate.setImgUuid2(leftImageUuid);
				uploadFlag = true;
			}
			if(left45Image != null){
				//左侧45度
				String left45ImageUuid = uploadAndGetImageId(request, left45Image);
				memberResumeTemplate.setImgUuid3(left45ImageUuid);
				uploadFlag = true;
			}
			if(rightImage != null){
				//右侧
				String rightImageUuid = uploadAndGetImageId(request, rightImage);
				memberResumeTemplate.setImgUuid4(rightImageUuid);
				uploadFlag = true;
			}
			if(right45Image != null){
				//右侧45
				String right45ImageUuid = uploadAndGetImageId(request, right45Image);
				memberResumeTemplate.setImgUuid5(right45ImageUuid);
				uploadFlag = true;
			}
			if(fullImage != null){
				//右侧
				String fullImageUuid = uploadAndGetImageId(request, fullImage);
				memberResumeTemplate.setImgUuid6(fullImageUuid);
				uploadFlag = true;
			}
			if(uploadFlag){
				memberResumeTemplate.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));								
				//如果该条记录审核通过,则将状态改为"修改审核中"
				double percent = generateResumePercent(memberResumeTemplate);
				if(memberResumeTemplate.getStatus().equals(ResumeStatusEnum.AUDITED.getCode())){
					memberResumeTemplate.setStatus(ResumeStatusEnum.MODIFY_AUDITING.getCode());
				}else if(memberResumeTemplate.getStatus().equals(ResumeStatusEnum.UNCOMMITED.getCode())){
					double resumeCompleteLimit = Double.parseDouble(CustomPropertiesUtils.getValue("resume_complete_limit"));
					if(percent >= resumeCompleteLimit){
						//修改为新建审核中和完成度
						memberResumeTemplate.setStatus(ResumeStatusEnum.NEW_AUDTING.getCode());
					}
				}
				memberResumeTemplate.setCompletion(percent * 100);
				result = memberResumeTemplateService.update(memberResumeTemplate);
			}						
		}
		if(result > 0){
			map.put("success", "yes");
			map.put("message","保存成功!");
			return map;
		}else{
			map.put("success", "no");
			map.put("message","保存失败!");
			return map;
		}
	}
	
	/**
	 * 查询简历视频
	 * @param request
	 * @param response
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/resume_video_info_if")
	@ResponseBody
	public Map<String,Object> resume_video_info_if(HttpServletRequest request,HttpServletResponse response,String memberId){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(memberId);
		String videoUrl = "";
		if(memberResumeTemplate != null){			
			String videoUuid = memberResumeTemplate.getVideoUuid();
			if(StringUtils.isBlank(videoUuid)){
				map.put("success", "yes");
				map.put("videoUrl", "");
				return map;
			}
			SystemFileInfo systemFileInfo = systemFileInfoService.selectByUuid(videoUuid);
			if(systemFileInfo == null){
				map.put("success", "yes");
				map.put("videoUrl", "");
				return map;
			}
			videoUrl = systemFileInfo.getUrlPath();		
		}else{
			map.put("success", "yes");
			map.put("videoUrl", "");
			return map;
		}
		map.put("success", "yes");
		map.put("videoUrl", getBasePath(request) + videoUrl);
		return map;
	}
	
	/**
	 * 简历视频修改
	 * @param request
	 * @param response
	 * @param memberId
	 * @param videoFile
	 * @return
	 */
	@RequestMapping(value = "/resume_video_edit_if")
	@ResponseBody
	public Map<String,Object> resume_video_edit_if(HttpServletRequest request,HttpServletResponse response,
			String memberId,MultipartFile videoFile){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(memberId);
		int result = 0;
		if(memberResumeTemplate == null){
			memberResumeTemplate = new MemberResumeTemplate();
			//新增
			if(videoFile == null){
				map.put("success", "no");
				map.put("message","请上传视频文件!");
				return map;
			}
			String videoUuid = uploadAndGetVideoId(request,videoFile);							
			memberResumeTemplate.setVideoUuid(videoUuid);
			memberResumeTemplate.setId(UUIDUtil.getUUID());
			memberResumeTemplate.setMemberId(memberId);
			memberResumeTemplate.setStatus(ResumeStatusEnum.UNCOMMITED.getCode());
			memberResumeTemplate.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			memberResumeTemplate.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
			double percent = generateResumePercent(memberResumeTemplate);
			double resumeCompleteLimit = Double.parseDouble(CustomPropertiesUtils.getValue("resume_complete_limit"));
			if(percent >= resumeCompleteLimit){
				//修改为新建审核中和完成度
				memberResumeTemplate.setStatus(ResumeStatusEnum.NEW_AUDTING.getCode());				
			}
			memberResumeTemplate.setCompletion(percent * 100);
			result = memberResumeTemplateService.insert(memberResumeTemplate);
		}else{
			if(videoFile != null){
				String newVideoUuid = uploadAndGetVideoId(request,videoFile);
				memberResumeTemplate.setVideoUuid(newVideoUuid);
				memberResumeTemplate.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				//如果该条记录审核通过,则将状态改为"修改审核中"
				double percent = generateResumePercent(memberResumeTemplate);
				if(memberResumeTemplate.getStatus().equals(ResumeStatusEnum.AUDITED.getCode())){
					memberResumeTemplate.setStatus(ResumeStatusEnum.MODIFY_AUDITING.getCode());
				}else if(memberResumeTemplate.getStatus().equals(ResumeStatusEnum.UNCOMMITED.getCode())){
					double resumeCompleteLimit = Double.parseDouble(CustomPropertiesUtils.getValue("resume_complete_limit"));
					if(percent >= resumeCompleteLimit){
						//修改为新建审核中和完成度
						memberResumeTemplate.setStatus(ResumeStatusEnum.NEW_AUDTING.getCode());				
					}
				}
				memberResumeTemplate.setCompletion(percent * 100);
				result = memberResumeTemplateService.update(memberResumeTemplate);
			}else{
				result = 1;
			}			
		}
		if(result > 0){
			map.put("success", "yes");
			map.put("message","保存成功!");
			return map;
		}else{
			map.put("success", "no");
			map.put("message","保存失败!");
			return map;
		}
	}
	
	/**
	 * 获取简历剧照生活照列表
	 * @param request
	 * @param response
	 * @param memberId
	 * @param resumeId
	 * @return
	 */
	@RequestMapping(value = "/member_resume_info_photos_if")
	@ResponseBody
	public Map<String,Object> member_resume_info_photos_if(HttpServletRequest request,HttpServletResponse response,
			String memberId){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> resumePhotoList = new ArrayList<Map<String,Object>>();
		MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(memberId);
		if(memberResumeTemplate == null){
			map.put("success", "yes");
			map.put("resumePhotos", resumePhotoList);
			return map;
		}
		resumePhotoList = getResumePhotos(request, resumePhotoList,memberId,memberResumeTemplate);
		map.put("success", "yes");
		map.put("resumePhotos", resumePhotoList);
		return map;		
	}
	
	/**
	 *  获取简历剧照生活照详情
	 * @param request
	 * @param response
	 * @param resumePhotoId
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/member_resum_photos_detail_if")
	@ResponseBody
	public Map<String,Object> member_resum_photos_detail_if(HttpServletRequest request,HttpServletResponse response,
			String resumePhotoId,String memberId){
		Map<String,Object> map = new HashMap<String,Object>();
		MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(memberId);
		if(memberResumeTemplate == null){
			map.put("success", "no");
			map.put("message", "获取用户简历信息失败!");
			return map;
		}
		MemberResumeTemplatePhotos memberResumeTemplatePhotos = memberResumeTemplatePhotosService.selectById(resumePhotoId);
		if(memberResumeTemplatePhotos == null){
			map.put("success", "no");
			map.put("message", "获取用户剧照生活照失败!");
			return map;
		}
		Map<String,Object> photoMap = new HashMap<String,Object>();
		photoMap.put("id", resumePhotoId);
		photoMap.put("title", decodeParam(memberResumeTemplatePhotos.getTitle()));
		photoMap.put("resumeId", memberResumeTemplatePhotos.getResumeId());
		
		List<String> imgIdList = new ArrayList<String>();
		if(StringUtils.isNotBlank(memberResumeTemplatePhotos.getImgUuid1())){
			imgIdList.add(memberResumeTemplatePhotos.getImgUuid1());
		}
		if(StringUtils.isNotBlank(memberResumeTemplatePhotos.getImgUuid2())){
			imgIdList.add(memberResumeTemplatePhotos.getImgUuid2());
		}			
		if(StringUtils.isNotBlank(memberResumeTemplatePhotos.getImgUuid3())){
			imgIdList.add(memberResumeTemplatePhotos.getImgUuid3());
		}			
		if(StringUtils.isNotBlank(memberResumeTemplatePhotos.getImgUuid4())){
			imgIdList.add(memberResumeTemplatePhotos.getImgUuid4());
		}			
		if(StringUtils.isNotBlank(memberResumeTemplatePhotos.getImgUuid5())){
			imgIdList.add(memberResumeTemplatePhotos.getImgUuid5());
		}
		if(StringUtils.isNotBlank(memberResumeTemplatePhotos.getImgUuidAll())){
			imgIdList.add(memberResumeTemplatePhotos.getImgUuidAll());
		}
		if(imgIdList.isEmpty()){
			map.put("success", "no");
			map.put("message", "获取用户剧照生活照失败!");
			return map;
		}
		List<SystemPictureInfo> picList = systemPictureInfoService.selectByUuids(imgIdList);
		if(picList == null || picList.isEmpty()){
			map.put("success", "no");
			map.put("message", "获取用户剧照生活照失败!");
			return map;
		}
		Map<String,String> picMap = new HashMap<String,String>();
		for(SystemPictureInfo pictureInfo : picList){
			picMap.put(pictureInfo.getUuid(), pictureInfo.getUrlPath());			
		}
		if(StringUtils.isNotBlank(memberResumeTemplatePhotos.getImgUuid1())){
			String imgUrl1 = picMap.get(memberResumeTemplatePhotos.getImgUuid1());
			photoMap.put("imgUrl1", imgUrl1);
		}
		if(StringUtils.isNotBlank(memberResumeTemplatePhotos.getImgUuid2())){
			String imgUrl2 = picMap.get(memberResumeTemplatePhotos.getImgUuid2());
			photoMap.put("imgUrl2", imgUrl2);
		}			
		if(StringUtils.isNotBlank(memberResumeTemplatePhotos.getImgUuid3())){
			String imgUrl3 = picMap.get(memberResumeTemplatePhotos.getImgUuid3());
			photoMap.put("imgUrl3", imgUrl3);
		}			
		if(StringUtils.isNotBlank(memberResumeTemplatePhotos.getImgUuid4())){
			String imgUrl4 = picMap.get(memberResumeTemplatePhotos.getImgUuid4());
			photoMap.put("imgUrl4", imgUrl4);
		}			
		if(StringUtils.isNotBlank(memberResumeTemplatePhotos.getImgUuid5())){
			String imgUrl5 = picMap.get(memberResumeTemplatePhotos.getImgUuid5());
			photoMap.put("imgUrl5", imgUrl5);
		}
		if(StringUtils.isNotBlank(memberResumeTemplatePhotos.getImgUuidAll())){
			String imgUrlAll = picMap.get(memberResumeTemplatePhotos.getImgUuidAll());
			photoMap.put("imgUrlAll", imgUrlAll);
		}
		map.put("success", "yes");
		map.put("photos", photoMap);
		return map;
	}
	
	/**
	 * 保存生活照剧照
	 * @param request
	 * @param response
	 * @param resumePhotoId
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/member_resum_photos_edit_if")
	@ResponseBody
	public Map<String,Object> member_resum_photos_edit_if(HttpServletRequest request,HttpServletResponse response,
			String resumePhotoId,String memberId, MultipartFile img1,MultipartFile img2,MultipartFile img3,
			String title,MultipartFile img4,MultipartFile img5,MultipartFile imgAll,String width,String height){
		
		Map<String,Object> map = new HashMap<String,Object>();
		MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(memberId);
		int result = 0;
		if(memberResumeTemplate == null){
			//添加
			memberResumeTemplate = new MemberResumeTemplate();
			memberResumeTemplate.setId(UUIDUtil.getUUID());
			memberResumeTemplate.setMemberId(memberId);
			memberResumeTemplate.setStatus(ResumeStatusEnum.UNCOMMITED.getCode());
			memberResumeTemplate.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			memberResumeTemplate.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			result = memberResumeTemplateService.insert(memberResumeTemplate);
			if(result <= 0){
				map.put("success", "no");
				map.put("message", "添加剧照生活照失败!");
				return map;
			}
			MemberResumeTemplatePhotos memberResumeTemplatePhotos = new MemberResumeTemplatePhotos();	
			if(StringUtils.isBlank(title)){
				map.put("success", "no");
				map.put("message", "请输入图片描述!");
				return map;
			}
			memberResumeTemplatePhotos.setTitle(encodeParam(title));
			if(img1 != null){				
				String img1Uuid = uploadAndGetImageId(request,img1);
				memberResumeTemplatePhotos.setImgUuid1(img1Uuid);
			}			
			if(img2 != null){				
				String img2Uuid = uploadAndGetImageId(request,img2);
				memberResumeTemplatePhotos.setImgUuid2(img2Uuid);
			}			
			if(img3 != null){				
				String img3Uuid = uploadAndGetImageId(request,img3);
				memberResumeTemplatePhotos.setImgUuid3(img3Uuid);
			}			
			if(img4 != null){				
				String img4Uuid = uploadAndGetImageId(request,img4);
				memberResumeTemplatePhotos.setImgUuid4(img4Uuid);
			}			
			if(img5 != null){
				String img5Uuid = uploadAndGetImageId(request,img5);
				memberResumeTemplatePhotos.setImgUuid5(img5Uuid);
			}			
			if(imgAll != null){
				String imgAllUuid = uploadAndGetImageId(request,imgAll);
				memberResumeTemplatePhotos.setImgUuidAll(imgAllUuid);
			}
			memberResumeTemplatePhotos.setId(UUIDUtil.getUUID());
			memberResumeTemplatePhotos.setResumeId(memberResumeTemplate.getId());
			memberResumeTemplatePhotos.setWidth(width);
			memberResumeTemplatePhotos.setHeight(height);
			memberResumeTemplatePhotos.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));			
			result = memberResumeTemplatePhotosService.insert(memberResumeTemplatePhotos);
			
			double percent = generateResumePercent(memberResumeTemplate);
			double resumeCompleteLimit = Double.parseDouble(CustomPropertiesUtils.getValue("resume_complete_limit"));
			if(percent >= resumeCompleteLimit){
				//修改为新建审核中和完成度
				memberResumeTemplate.setStatus(ResumeStatusEnum.NEW_AUDTING.getCode());				
			}
			memberResumeTemplate.setCompletion(percent * 100);
			result = memberResumeTemplateService.update(memberResumeTemplate);
		}else{
			memberResumeTemplate.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			result = memberResumeTemplateService.update(memberResumeTemplate);
			if(result <= 0){
				map.put("success", "no");
				map.put("message", "保存剧照生活照失败!");
				return map;
			}
			if(StringUtils.isBlank(resumePhotoId)){
				//添加
				MemberResumeTemplatePhotos memberResumeTemplatePhotos = new MemberResumeTemplatePhotos();	
				if(StringUtils.isBlank(title)){
					map.put("success", "no");
					map.put("message", "请输入图片描述!");
					return map;
				}
				memberResumeTemplatePhotos.setTitle(encodeParam(title));
				if(img1 != null){				
					String img1Uuid = uploadAndGetImageId(request,img1);
					memberResumeTemplatePhotos.setImgUuid1(img1Uuid);
				}			
				if(img2 != null){				
					String img2Uuid = uploadAndGetImageId(request,img2);
					memberResumeTemplatePhotos.setImgUuid2(img2Uuid);
				}			
				if(img3 != null){				
					String img3Uuid = uploadAndGetImageId(request,img3);
					memberResumeTemplatePhotos.setImgUuid3(img3Uuid);
				}			
				if(img4 != null){				
					String img4Uuid = uploadAndGetImageId(request,img4);
					memberResumeTemplatePhotos.setImgUuid4(img4Uuid);
				}			
				if(img5 != null){
					String img5Uuid = uploadAndGetImageId(request,img5);
					memberResumeTemplatePhotos.setImgUuid5(img5Uuid);
				}			
				if(imgAll != null){
					String imgAllUuid = uploadAndGetImageId(request,imgAll);
					memberResumeTemplatePhotos.setImgUuidAll(imgAllUuid);
				}
				memberResumeTemplatePhotos.setId(UUIDUtil.getUUID());
				memberResumeTemplatePhotos.setResumeId(memberResumeTemplate.getId());
				memberResumeTemplatePhotos.setWidth(width);
				memberResumeTemplatePhotos.setHeight(height);
				memberResumeTemplatePhotos.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				result = memberResumeTemplatePhotosService.insert(memberResumeTemplatePhotos);
				
				double percent = generateResumePercent(memberResumeTemplate);
				if(memberResumeTemplate.getStatus().equals(ResumeStatusEnum.AUDITED.getCode())){
					memberResumeTemplate.setStatus(ResumeStatusEnum.MODIFY_AUDITING.getCode());
				}else if(memberResumeTemplate.getStatus().equals(ResumeStatusEnum.UNCOMMITED.getCode())){
					double resumeCompleteLimit = Double.parseDouble(CustomPropertiesUtils.getValue("resume_complete_limit"));
					if(percent >= resumeCompleteLimit){
						//修改为新建审核中和完成度
						memberResumeTemplate.setStatus(ResumeStatusEnum.NEW_AUDTING.getCode());				
					}
				}
				memberResumeTemplate.setCompletion(percent * 100);
				result = memberResumeTemplateService.update(memberResumeTemplate);
			}else{
				//修改
				MemberResumeTemplatePhotos memberResumeTemplatePhotos = memberResumeTemplatePhotosService.selectById(resumePhotoId);
				if(memberResumeTemplatePhotos == null){
					map.put("success", "no");
					map.put("message", "获取剧照生活照失败!");
					return map;
				}
				if(StringUtils.isBlank(title)){
					map.put("success", "no");
					map.put("message", "请输入图片描述!");
					return map;
				}
				memberResumeTemplatePhotos.setTitle(encodeParam(title));
				if(imgAll != null){
					if(img1 != null){
						String img1Uuid = uploadAndGetImageId(request,img1);
						memberResumeTemplatePhotos.setImgUuid1(img1Uuid);
					}else{
						memberResumeTemplatePhotos.setImgUuid1("");
					}
					if(img2 != null){			
						String img2Uuid = uploadAndGetImageId(request,img2);
						memberResumeTemplatePhotos.setImgUuid2(img2Uuid);
					}else{
						memberResumeTemplatePhotos.setImgUuid2("");
					}
					if(img3 != null){
						String img3Uuid = uploadAndGetImageId(request,img3);
						memberResumeTemplatePhotos.setImgUuid3(img3Uuid);
					}else{
						memberResumeTemplatePhotos.setImgUuid3("");
					}
					if(img4 != null){	
						String img4Uuid = uploadAndGetImageId(request,img4);
						memberResumeTemplatePhotos.setImgUuid4(img4Uuid);
					}else{
						memberResumeTemplatePhotos.setImgUuid4("");
					}			
					if(img5 != null){
						String img5Uuid = uploadAndGetImageId(request,img5);
						memberResumeTemplatePhotos.setImgUuid5(img5Uuid);
					}else{
						memberResumeTemplatePhotos.setImgUuid5("");
					}			
					String imgAllUuid = uploadAndGetImageId(request,imgAll);
					memberResumeTemplatePhotos.setImgUuidAll(imgAllUuid);
				}
				memberResumeTemplatePhotos.setResumeId(memberResumeTemplate.getId());
				memberResumeTemplatePhotos.setWidth(width);
				memberResumeTemplatePhotos.setHeight(height);
				memberResumeTemplatePhotos.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				result = memberResumeTemplatePhotosService.update(memberResumeTemplatePhotos);
				
				double percent = generateResumePercent(memberResumeTemplate);
				if(memberResumeTemplate.getStatus().equals(ResumeStatusEnum.AUDITED.getCode())){
					memberResumeTemplate.setStatus(ResumeStatusEnum.MODIFY_AUDITING.getCode());
				}else if(memberResumeTemplate.getStatus().equals(ResumeStatusEnum.UNCOMMITED.getCode())){
					double resumeCompleteLimit = Double.parseDouble(CustomPropertiesUtils.getValue("resume_complete_limit"));
					if(percent >= resumeCompleteLimit){
						//修改为新建审核中和完成度
						memberResumeTemplate.setStatus(ResumeStatusEnum.NEW_AUDTING.getCode());				
					}
				}
				memberResumeTemplate.setCompletion(percent * 100);
				result = memberResumeTemplateService.update(memberResumeTemplate);
			}
		}
		if(result > 0){
			map.put("success", "yes");
			map.put("message", "保存剧照成功!");
		}else{
			map.put("success", "no");
			map.put("message", "保存剧照失败!");
		}
		return map;
	}
	
	/**
	 * 删除剧照
	 * @param request
	 * @param response
	 * @param photoId
	 * @return
	 */
	@RequestMapping(value = "/delete_resume_photo_if")
	@ResponseBody
	public Map<String,Object> delete_resume_photo_if(HttpServletRequest request,HttpServletResponse response,
			String photoId){		
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isBlank(photoId)){
			map.put("success", "no");
			map.put("message", "找不到剧照信息!");
			return map;
		}
		MemberResumeTemplatePhotos photo = memberResumeTemplatePhotosService.selectById(photoId);
		int result = 0;
		if(photo != null){
			result = memberResumeTemplatePhotosService.delete(photo);
			if(result > 0){
				MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectById(photo.getResumeId());
				if(memberResumeTemplate != null){
					memberResumeTemplate.setCompletion(generateResumePercent(memberResumeTemplate) * 100);
					result = memberResumeTemplateService.update(memberResumeTemplate);
				}
			}
		}		
		if(result > 0){
			map.put("success", "yes");
			map.put("message", "删除剧照成功!");
		}else{
			map.put("success", "no");
			map.put("message", "删除剧照失败!");
		}
		return map;		
	}
		
	/**
	 * 个人简历预览
	 * @param request
	 * @param response
	 * @param memberId
	 * @param resumeId
	 * @return
	 */
	@RequestMapping(value = "/preview_resume_if")
	@ResponseBody
	public Map<String,Object> preview_resume_if(HttpServletRequest request,HttpServletResponse response,
			String memberId) throws Exception{
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "1");
		if(StringUtils.isBlank(memberId)){
			map.put("success", "no");
			map.put("message", "获取用户信息失败!");
			map.put("status", "0");
			return map;
		}
		MemberInfo memberInfo = memberInfoService.selectById(memberId);
		MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(memberId);
		if(memberResumeTemplate == null){
			map.put("success", "yes");
			map.put("message", "您还没有制作您的简历呢!");
			map.put("status", "0");
			return map;
		}				
		//计算简历百分比	
		double percent = generateResumePercent(memberResumeTemplate);
		double resumeCompleteLimit = Double.parseDouble(CustomPropertiesUtils.getValue("resume_complete_limit"));
		if(percent < resumeCompleteLimit){
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMaximumFractionDigits(2);
			String percentStr = nf.format(percent);
			map.put("success", "yes");
			map.put("message", "您目前的简历完善度不够(目前完善度" + percentStr + "),无法生成简历!");
			map.put("status", "0");
			return map;
		}
		
		//获取基本信息
		Map<String,Object> basicMap = new HashMap<String,Object>();
		basicMap = getResumeBasicInfo(basicMap, memberResumeTemplate, memberInfo, request);		
		
		//获取见组照
		Map<String,Object> imageMap = new HashMap<String,Object>();
		imageMap = getResumeImages(request, imageMap, memberResumeTemplate);
		
		//获取自我介绍视频
		String videoUrl = "";
		String videoUuid = memberResumeTemplate.getVideoUuid();
		if(StringUtils.isNotBlank(videoUuid)){
			SystemFileInfo systemFileInfo = systemFileInfoService.selectByUuid(videoUuid);
			if(systemFileInfo != null){
				videoUrl = getBasePath(request) + systemFileInfo.getUrlPath();
			}
		}								
		//map.put("videoUrl", videoUrl);
				
		//获取剧照生活照
		List<Map<String,Object>> resumePhotoList = new ArrayList<Map<String,Object>>();
		resumePhotoList = getResumePhotos(request, resumePhotoList, memberId, memberResumeTemplate);
		//map.put("photos", resumePhotoList);
		
		map.put("success", "yes");
		map.put("basicInfo", basicMap);
		map.put("images", imageMap);
		map.put("videoUrl", videoUrl);
		map.put("resumePhotos", resumePhotoList);
		map.put("message", "");
		map.put("shareUrl", getBasePath(request) + "rest/view/ResumeInfo.do?memberId="+memberId);
		return map;
	}
	
	@RequestMapping(value = "/check_resume_if")
	@ResponseBody
	public Map<String,Object> check_resume_if(HttpServletRequest request,HttpServletResponse response,
			String memberId) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(memberId);
		if(memberResumeTemplate == null){
			map.put("success", "no");
			map.put("message", "您还没有制作您的简历呢!");
			return map;
		}				
		//计算简历百分比	
		double percent = generateResumePercent(memberResumeTemplate);
		double resumeCompleteLimit = Double.parseDouble(CustomPropertiesUtils.getValue("resume_complete_limit"));
		if(percent < resumeCompleteLimit){
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMaximumFractionDigits(2);
			String percentStr = nf.format(percent);
			map.put("success", "no");
			map.put("message", "您目前的简历完善度不够(目前完善度" + percentStr + "),无法生成简历!");
		}else{
			map.put("success", "yes");
			map.put("message", "简历已达成可投递完成度。");
		}
		return map;
	}
	
	private Map<String,Object> getResumeBasicInfo(Map<String,Object> basicMap,MemberResumeTemplate memberResumeTemplate,
			MemberInfo memberInfo,HttpServletRequest request) throws Exception{
		
		basicMap.put("realName", memberInfo.getRealName() == null ? "" : decodeParam(memberInfo.getRealName()));		
		basicMap.put("sex", memberInfo.getSex() == null ? "" : memberInfo.getSex());	
		basicMap.put("nickname", memberInfo.getNickname() == null ? "" : decodeParam(memberInfo.getNickname()));
		basicMap.put("school", memberInfo.getSchool() == null ? "" : decodeParam(memberInfo.getSchool()));
		
		//城市
		String cityCode = memberResumeTemplate.getCityCode();
		Map<String,Object> cityInfo = getCityInfo(cityCode);
		basicMap.putAll(cityInfo);
		
		basicMap.put("height", memberResumeTemplate.getHeight() == null ? "" : memberResumeTemplate.getHeight());
		basicMap.put("weight", memberResumeTemplate.getWeight() == null ? "" : memberResumeTemplate.getWeight());		
		basicMap.put("birthday", memberResumeTemplate.getBirthday() == null ? "" : memberResumeTemplate.getBirthday());
		basicMap.put("chest",memberResumeTemplate.getChest() == null ? "" : memberResumeTemplate.getChest());
		basicMap.put("waist",memberResumeTemplate.getWaist() == null ? "" : memberResumeTemplate.getWaist());
		basicMap.put("hipline",memberResumeTemplate.getHipline() == null ? "" : memberResumeTemplate.getHipline());
		basicMap.put("id", memberResumeTemplate.getId());
		if(StringUtils.isNotBlank(memberResumeTemplate.getBirthday())){
			basicMap.put("age", DateUtils.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(memberResumeTemplate.getBirthday())));
		}				
		basicMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
		basicMap.put("identityStatus", memberInfo.getIdentityStatus() == null ? "" : memberInfo.getIdentityStatus());
		basicMap.put("educationStatus", memberInfo.getEducationStatus() == null ? "" : memberInfo.getEducationStatus());
						
		//头像
		String uuid = memberInfo.getImgUuid();
		SystemPictureInfo systemPictureInfo = systemPictureInfoService.selectByUuid(uuid);
		if(systemPictureInfo != null){
			basicMap.put("headUrl", getBasePath(request) + systemPictureInfo.getUrlPath());
		}else{
			basicMap.put("headUrl", "");
		}
		                                                                                                                                                                                                                                                                     
		//角色标签
		String roleLabel = decodeParam(memberResumeTemplate.getRoleLabel());		
		if(StringUtils.isBlank(roleLabel)){
			basicMap.put("roleLableNames", "");
		}else{
			if(roleLabel.substring(0, 1).equals(",")){
				roleLabel = roleLabel.substring(1, roleLabel.length());
			}
			List<String> roleLabelList = Arrays.asList(roleLabel.toString().split(","));
			StringBuilder roleLabelNames = new StringBuilder("");
			for(String roleLabelName : roleLabelList){
				roleLabelNames.append(roleLabelName).append(",");
			}
			if(StringUtils.isNotBlank(roleLabelNames.toString())){
				roleLabelNames = roleLabelNames.deleteCharAt(roleLabelNames.length() - 1);
			}
			basicMap.put("roleLableNames", roleLabelNames);
		}
		
		//技能标签
		String skillLabel = decodeParam(memberResumeTemplate.getSkillLabel());		
		if(StringUtils.isBlank(skillLabel)){
			basicMap.put("skillLabelNames", "");
		}else{
			if(skillLabel.substring(0, 1).equals(",")){
				skillLabel = skillLabel.substring(1, skillLabel.length());
			}
			List<String> skillLabelList = Arrays.asList(skillLabel.toString().split(","));
			StringBuilder skillLabelNames = new StringBuilder("");
			for(String skillLabelName : skillLabelList){
				skillLabelNames.append(skillLabelName).append(",");
			}
			if(StringUtils.isNotBlank(skillLabelNames.toString())){
				skillLabelNames = skillLabelNames.deleteCharAt(skillLabelNames.length() - 1);
			}
			basicMap.put("skillLabelNames", skillLabelNames);
		}	
		return basicMap;
	}
	
	private Map<String,Object> getResumeImages(HttpServletRequest request, Map<String,Object> imageMap,MemberResumeTemplate memberResumeTemplate){		
		List<String> imgIdList = new ArrayList<String>();
		Map<String,String> params = new HashMap<String,String>();
		//正面照
		if(StringUtils.isNotBlank(memberResumeTemplate.getImgUuid1())){
			imgIdList.add(memberResumeTemplate.getImgUuid1());
			params.put(memberResumeTemplate.getImgUuid1(),"frontImage");
		}else{
			params.put(StringUtils.EMPTY,"frontImage");
		}
		//左侧
		if(StringUtils.isNotBlank(memberResumeTemplate.getImgUuid2())){
			imgIdList.add(memberResumeTemplate.getImgUuid2());
			params.put(memberResumeTemplate.getImgUuid2(),"leftImage");
		}else{
			params.put(StringUtils.EMPTY,"leftImage");
		}
		//左45度
		if(StringUtils.isNotBlank(memberResumeTemplate.getImgUuid3())){
			imgIdList.add(memberResumeTemplate.getImgUuid3());
			params.put(memberResumeTemplate.getImgUuid3(),"left45Image");
		}else{
			params.put(StringUtils.EMPTY,"left45Image");
		}
		//右侧
		if(StringUtils.isNotBlank(memberResumeTemplate.getImgUuid4())){
			imgIdList.add(memberResumeTemplate.getImgUuid4());
			params.put(memberResumeTemplate.getImgUuid4(),"rightImage");
		}else{
			params.put(StringUtils.EMPTY,"rightImage");
		}
		//右侧45
		if(StringUtils.isNotBlank(memberResumeTemplate.getImgUuid5())){
			imgIdList.add(memberResumeTemplate.getImgUuid5());
			params.put(memberResumeTemplate.getImgUuid5(),"right45Image");
		}else{
			params.put(StringUtils.EMPTY,"right45Image");
		}
		//全身
		if(StringUtils.isNotBlank(memberResumeTemplate.getImgUuid6())){
			imgIdList.add(memberResumeTemplate.getImgUuid6());
			params.put(memberResumeTemplate.getImgUuid6(),"fullImage");
		}else{
			params.put(StringUtils.EMPTY,"fullImage");
		}
		if(imgIdList.isEmpty()){
			imageMap.put("frontImage", StringUtils.EMPTY);
			imageMap.put("leftImage", StringUtils.EMPTY);
			imageMap.put("left45Image", StringUtils.EMPTY);
			imageMap.put("rightImage", StringUtils.EMPTY);
			imageMap.put("right45Image", StringUtils.EMPTY);
			imageMap.put("fullImage", StringUtils.EMPTY);
			return imageMap;
		}
		List<SystemPictureInfo> picList = systemPictureInfoService.selectByUuids(imgIdList);
		if(picList == null || picList.isEmpty()){
			imageMap.put("frontImage", StringUtils.EMPTY);
			imageMap.put("leftImage", StringUtils.EMPTY);
			imageMap.put("left45Image", StringUtils.EMPTY);
			imageMap.put("rightImage", StringUtils.EMPTY);
			imageMap.put("right45Image", StringUtils.EMPTY);
			imageMap.put("fullImage", StringUtils.EMPTY);
			return imageMap;
		}
		for(SystemPictureInfo systemPictureInfo : picList){
			String uuid = systemPictureInfo.getUuid();
			if(!params.containsKey(uuid)){
				continue;
			}
			String mapText = params.get(uuid);
			String urlPath = systemPictureInfo.getUrlPath();
			if(StringUtils.isNotBlank(urlPath)){
				imageMap.put(mapText, getBasePath(request) + urlPath);
			}else{
				imageMap.put(mapText, "");
			}			
		}
		return imageMap;
	}
	
	private List<Map<String,Object>> getResumePhotos(HttpServletRequest request,List<Map<String,Object>> resumePhotoList,String memberId,MemberResumeTemplate memberResumeTemplate){
		String resumId = memberResumeTemplate.getId();
		Map<String,Object> photosParams = new HashMap<String,Object>();
		photosParams.put("resumeId", resumId);
		List<MemberResumeTemplatePhotos> photoList = memberResumeTemplatePhotosService.selectAll(photosParams);
		if(photoList == null || photoList.isEmpty()){
			return resumePhotoList;
		}
		List<String> imgIdList = new ArrayList<String>();
		Map<String,String> imgMap = new HashMap<String,String>();
		for(MemberResumeTemplatePhotos infoPhoto : photoList){
			if(StringUtils.isNotBlank(infoPhoto.getImgUuid1())){
				imgIdList.add(infoPhoto.getImgUuid1());
				imgMap.put(infoPhoto.getImgUuid1(), "img1");
			}else{
				imgMap.put(StringUtils.EMPTY, "img1");
			}			
			if(StringUtils.isNotBlank(infoPhoto.getImgUuid2())){
				imgIdList.add(infoPhoto.getImgUuid2());
				imgMap.put(infoPhoto.getImgUuid2(), "img2");
			}else{
				imgMap.put(StringUtils.EMPTY, "img2");
			}			
			if(StringUtils.isNotBlank(infoPhoto.getImgUuid3())){
				imgIdList.add(infoPhoto.getImgUuid3());
				imgMap.put(infoPhoto.getImgUuid3(), "img3");
			}else{
				imgMap.put(StringUtils.EMPTY, "img3");
			}
			if(StringUtils.isNotBlank(infoPhoto.getImgUuid4())){
				imgIdList.add(infoPhoto.getImgUuid4());
				imgMap.put(infoPhoto.getImgUuid4(), "img4");
			}else{
				imgMap.put(StringUtils.EMPTY, "img4");
			}
			if(StringUtils.isNotBlank(infoPhoto.getImgUuid5())){
				imgIdList.add(infoPhoto.getImgUuid5());
				imgMap.put(infoPhoto.getImgUuid5(), "img5");
			}else{
				imgMap.put(StringUtils.EMPTY, "img5");
			}
			if(StringUtils.isNotBlank(infoPhoto.getImgUuidAll())){
				imgIdList.add(infoPhoto.getImgUuidAll());
				imgMap.put(infoPhoto.getImgUuidAll(), "imgAll");
			}else{
				imgMap.put(StringUtils.EMPTY, "imgAll");
			}
		}
		if(imgIdList.isEmpty()){
			return resumePhotoList;
		}
		List<SystemPictureInfo> picList = systemPictureInfoService.selectByUuids(imgIdList);
		if(picList == null || picList.isEmpty()){
			return resumePhotoList;
		}
		Map<String,String> picMap = new HashMap<String,String>();
		for(SystemPictureInfo pictureInfo : picList){
			picMap.put(pictureInfo.getUuid(), pictureInfo.getUrlPath());			
		}
		String basePath = getBasePath(request);
		for(MemberResumeTemplatePhotos infoPhoto : photoList){
			Map<String,Object> photoMap = new HashMap<String,Object>();
			photoMap.put("photoId", infoPhoto.getId());
			photoMap.put("title", decodeParam(infoPhoto.getTitle()));
			photoMap.put("resumeId", infoPhoto.getResumeId());
			
			String imgUuid1 = infoPhoto.getImgUuid1();
			if(StringUtils.isNotBlank(imgUuid1)){
				String imgUrl1 = picMap.get(imgUuid1);
				if(StringUtils.isNotBlank(imgUrl1)){
					photoMap.put("imgUrl1", basePath + imgUrl1);
				}else{
					photoMap.put("imgUrl1", StringUtils.EMPTY);
				}								
			}else{
				photoMap.put("imgUrl1", StringUtils.EMPTY);
			}
			
			String imgUuid2 = infoPhoto.getImgUuid2();
			if(StringUtils.isNotBlank(imgUuid2)){
				String imgUrl2 = picMap.get(imgUuid2);
				if(StringUtils.isNotBlank(imgUrl2)){
					photoMap.put("imgUrl2", basePath + imgUrl2);
				}else{
					photoMap.put("imgUrl2",StringUtils.EMPTY);
				}								
			}else{
				photoMap.put("imgUrl2", StringUtils.EMPTY);
			}
			
			String imgUuid3 = infoPhoto.getImgUuid3();
			if(StringUtils.isNotBlank(imgUuid3)){
				String imgUrl3 = picMap.get(imgUuid3);
				if(StringUtils.isNotBlank(imgUrl3)){
					photoMap.put("imgUrl3", basePath + imgUrl3);
				}else{
					photoMap.put("imgUrl3",StringUtils.EMPTY);
				}							
			}else{
				photoMap.put("imgUrl3",StringUtils.EMPTY);
			}			
			
			String imgUuid4 = infoPhoto.getImgUuid4();
			if(StringUtils.isNotBlank(imgUuid4)){
				String imgUrl4 = picMap.get(imgUuid4);
				if(StringUtils.isNotBlank(imgUrl4)){
					photoMap.put("imgUrl4", basePath + imgUrl4);
				}else{
					photoMap.put("imgUrl4",StringUtils.EMPTY);
				}
			}else{
				photoMap.put("imgUrl4",StringUtils.EMPTY);
			}
			
			String imgUuid5 = infoPhoto.getImgUuid5();
			if(StringUtils.isNotBlank(imgUuid5)){
				String imgUrl5 = picMap.get(imgUuid5);
				if(StringUtils.isNotBlank(imgUrl5)){
					photoMap.put("imgUrl5", basePath + imgUrl5);
				}else{
					photoMap.put("imgUrl5",StringUtils.EMPTY);
				}
			}else{
				photoMap.put("imgUrl5",StringUtils.EMPTY);
			}
			
			String imgUuidAll = infoPhoto.getImgUuidAll();
			if(StringUtils.isNotBlank(imgUuidAll)){
				String imgUrlAll = picMap.get(imgUuidAll);
				if(StringUtils.isNotBlank(imgUrlAll)){
					photoMap.put("imgUrlAll", basePath + imgUrlAll);
				}else{
					photoMap.put("imgUrlAll",StringUtils.EMPTY);
				}	
			}else{
				photoMap.put("imgUrlAll",StringUtils.EMPTY);
			}
			photoMap.put("width", infoPhoto.getWidth() == null ? "" : infoPhoto.getWidth());
			photoMap.put("height", infoPhoto.getHeight() == null ? "" : infoPhoto.getHeight());
			resumePhotoList.add(photoMap);
		}
		return resumePhotoList;
	}
	
	private String uploadAndGetImageId(HttpServletRequest request,MultipartFile imageFile){
		String root = request.getSession().getServletContext().getRealPath("/");
		String pathTmp = Constant.UPLOAD_MEMBER_RESUME_PATH + "/";
		String path = pathTmp + DateUtils.toString(new Date(), "yyyy/MM/dd") + "/";
		String realPath = root + path;
		SystemPictureInfo imgAllPicInfo = systemPictureInfoService.insertFileInfo(imageFile, realPath, path);
		String uuid = "";
		if(imgAllPicInfo != null){
			uuid = imgAllPicInfo.getUuid();
		}
		return uuid;
	}
	
	private String uploadAndGetVideoId(HttpServletRequest request,MultipartFile videoFile){
		String root = request.getSession().getServletContext().getRealPath("/");
		String pathTmp = Constant.UPLOAD_MEMBER_RESUME_PATH + "/";
		String path = pathTmp + DateUtils.toString(new Date(), "yyyy/MM/dd") + "/";
		String realPath = root + path;
		SystemFileInfo fileInfo = systemFileInfoService.insertFileInfo(videoFile, realPath, path);
		String uuid = "";
		if(fileInfo != null){
			uuid = fileInfo.getUuid();
		}
		return uuid;
	}
				
	private Map<String,Object> getCityInfo(String cityCode){
		Map<String,Object> cityMap = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(cityCode)){
			SystemHotspotCity cityInfo = systemHotspotCityService.selectById(cityCode);
			if(cityInfo != null){
				String cityName = cityInfo.getCityName();
				if(StringUtils.isNotBlank(cityName)){
					cityMap.put("cityCode", cityCode);
					cityMap.put("cityName", cityName);				
				}else{
					cityMap.put("cityCode", "");
					cityMap.put("cityName", "");
				}
			}else{
				cityMap.put("cityCode", "");
				cityMap.put("cityName", "");
			}
		}else{
			cityMap.put("cityCode", "");
			cityMap.put("cityName", "");
		}
		return cityMap;
	}
	
	/**
	 * 计算简历完成度(基数为25)
	 * @param memberResumeTemplate
	 * @param resumeTemplatePhotos
	 * @return
	 */
	private double generateResumePercent(MemberResumeTemplate memberResumeTemplate){
		int numerator = 0;
		if(StringUtils.isNotBlank(memberResumeTemplate.getRealName())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getSex())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getHeight())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getWeight())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getBirthday())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getChest())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getWaist())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getHipline())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getSchool())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getCityCode())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getImgUuid())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getImgUuid1())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getImgUuid2())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getImgUuid3())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getImgUuid4())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getImgUuid5())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getImgUuid6())){
			numerator += 1;
		}
		if(StringUtils.isNotBlank(memberResumeTemplate.getVideoUuid())){
			numerator += 1;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("resumeId", memberResumeTemplate.getId());
		int photoCount = memberResumeTemplatePhotosService.selectCount(params);
		numerator += photoCount;
		int resumeBaseNumber = Integer.parseInt(CustomPropertiesUtils.getValue("resume_base_number"));
		return (double)numerator / (double)resumeBaseNumber;
	}
}