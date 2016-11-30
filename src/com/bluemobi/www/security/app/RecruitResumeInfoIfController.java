package com.bluemobi.www.security.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.bluemobi.www.data.model.member.MemberResumeApply;
import com.bluemobi.www.data.model.member.MemberResumeApplyPhotos;
import com.bluemobi.www.data.model.recruit.RecruitApplyInfo;
import com.bluemobi.www.data.model.system.SystemFileInfo;
import com.bluemobi.www.data.model.system.SystemHotspotCity;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberInfoService;
import com.bluemobi.www.security.member.service.MemberResumeApplyPhotosService;
import com.bluemobi.www.security.member.service.MemberResumeApplyService;
import com.bluemobi.www.security.recruit.service.RecruitApplyInfoService;
import com.bluemobi.www.security.system.service.SystemFileInfoService;
import com.bluemobi.www.security.system.service.SystemHotspotCityService;
import com.bluemobi.www.security.system.service.SystemLableInfoService;
import com.bluemobi.www.security.system.service.SystemPictureInfoService;
import com.bluemobi.www.utils.DateUtils;

/**
 * 招募者查看个人简历相关信息接口类
 * @author lip
 *
 */
@Controller
public class RecruitResumeInfoIfController extends BaseController {
	
	@Resource
	private MemberInfoService memberInfoService;
	
	@Resource
	private MemberResumeApplyService memberResumeApplyService;
	
	@Resource
	private SystemFileInfoService systemFileInfoService;
	
	@Resource
	private SystemPictureInfoService systemPictureInfoService;
	
	@Resource
	private SystemHotspotCityService systemHotspotCityService;
	
	@Resource
	private SystemLableInfoService systemLableInfoService;
	
	@Resource
	private MemberResumeApplyPhotosService memberResumeApplyPhotosService;
	
	@Resource
	private RecruitApplyInfoService applyInfoService;
	/**
	 * 个人简历预览
	 * @param request
	 * @param response
	 * @param memberId
	 * @param resumeId
	 * @return
	 */
	@RequestMapping(value = "/recruit_resume_if")
	@ResponseBody
	public Map<String,Object> preview_resume_if(HttpServletRequest request,HttpServletResponse response,
			String memberId,String resumeId , String applyId) throws Exception{
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isBlank(memberId)){
			map.put("success", "no");
			map.put("message", "获取用户信息失败!");
			return map;
		}
		MemberInfo memberInfo = memberInfoService.selectById(memberId);
		if(StringUtils.isBlank(resumeId)){
			map.put("success", "no");
			map.put("message", "获取用户信息失败!");
			return map;
		}
		MemberResumeApply memberResumeApply = memberResumeApplyService.selectById(resumeId);
		if(memberResumeApply == null){
			map.put("success", "no");
			map.put("message", "找不到该演员的简历信息!");
			return map;
		}
		//投递信息
		RecruitApplyInfo applyInfo = applyInfoService.selectById(applyId);
		
		//获取基本信息
		Map<String,Object> basicMap = new HashMap<String,Object>();
		basicMap = getResumeBasicInfo(basicMap, memberResumeApply, memberInfo, request);
		
		//获取见组照
		Map<String,Object> imageMap = new HashMap<String,Object>();
		imageMap = getResumeImages(request, imageMap, memberResumeApply);
		
		//获取自我介绍视频
		String videoUrl = "";
		String videoUuid = memberResumeApply.getVideoUuid();
		if(StringUtils.isNotBlank(videoUuid)){
			SystemFileInfo systemFileInfo = systemFileInfoService.selectByUuid(videoUuid);
			if(systemFileInfo != null){
				videoUrl = getBasePath(request) + systemFileInfo.getUrlPath();
			}
		}								
		//map.put("videoUrl", videoUrl);
				
		//获取剧照生活照
		List<Map<String,Object>> resumePhotoList = new ArrayList<Map<String,Object>>();
		resumePhotoList = getResumePhotos(request, resumePhotoList, memberId, memberResumeApply);
		//map.put("photos", resumePhotoList);
		
		map.put("success", "yes");
		map.put("basicInfo", basicMap);
		map.put("images", imageMap);
		map.put("videoUrl", videoUrl);
		map.put("resumePhotos", resumePhotoList);
		map.put("optional", applyInfo.getOptional());
		
		//修改时间轴状态(剧组已查看)
		applyInfo.setLevel("3");
		applyInfoService.update(applyInfo);
		
		return map;
	}
	
	private Map<String,Object> getResumeBasicInfo(Map<String,Object> basicMap,MemberResumeApply memberResumeApply,
			MemberInfo memberInfo,HttpServletRequest request) throws Exception{
		
		basicMap.put("mobile", memberInfo.getMobile());
		basicMap.put("realName", memberResumeApply.getRealName() == null ? "" : decodeParam(memberResumeApply.getRealName()));		
		basicMap.put("sex", memberResumeApply.getSex() == null ? "" : memberResumeApply.getSex());
		basicMap.put("email", memberInfo.getEmail() == null ? "" : memberInfo.getEmail());
		
		//城市
		String cityCode = memberResumeApply.getCityCode();
		Map<String,Object> cityInfo = getCityInfo(cityCode);
		basicMap.putAll(cityInfo);
		
		basicMap.put("height", memberResumeApply.getHeight() == null ? "" : memberResumeApply.getHeight());
		basicMap.put("weight", memberResumeApply.getWeight() == null ? "" : memberResumeApply.getWeight());		
		basicMap.put("birthday", memberResumeApply.getBirthday() == null ? "" : memberResumeApply.getBirthday());
		basicMap.put("chest",memberResumeApply.getChest() == null ? "" : memberResumeApply.getChest());
		basicMap.put("waist",memberResumeApply.getWaist() == null ? "" : memberResumeApply.getWaist());
		basicMap.put("hipline",memberResumeApply.getHipline() == null ? "" : memberResumeApply.getHipline());
		basicMap.put("id", memberResumeApply.getId());
		if(StringUtils.isNotBlank(memberResumeApply.getBirthday())){
			basicMap.put("age", DateUtils.getAge(new SimpleDateFormat("yyyy-MM-dd").parse(memberResumeApply.getBirthday())));
		}				
		basicMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
		basicMap.put("identityStatus", memberInfo.getIdentityStatus() == null ? "" : memberInfo.getIdentityStatus());
		basicMap.put("nickname", memberInfo.getNickname() == null ? "" : decodeParam(memberInfo.getNickname()));
		basicMap.put("school", memberResumeApply.getSchool() == null ? "" : decodeParam(memberResumeApply.getSchool()));
		
		//头像
		String uuid = memberInfo.getImgUuid();
		SystemPictureInfo systemPictureInfo = systemPictureInfoService.selectByUuid(uuid);
		if(systemPictureInfo != null){
			basicMap.put("headUrl", getBasePath(request) + systemPictureInfo.getUrlPath());
		}else{
			basicMap.put("headUrl", "");
		}
		
		//角色标签
		String roleLabel = decodeParam(memberResumeApply.getRoleLabel());
		if(StringUtils.isBlank(roleLabel)){
			basicMap.put("roleLableNames", "");
		}else{
			if(roleLabel!= null && roleLabel.length()>1 && roleLabel.substring(0, 1).equals(",")){
				roleLabel = roleLabel.substring(1, roleLabel.length());
			}
			List<String> roleLabelNameList = Arrays.asList(roleLabel.toString().split(","));
			StringBuilder roleLabelNames = new StringBuilder("");
			for(String roleLabelName : roleLabelNameList){
				roleLabelNames.append(roleLabelName).append(",");
			}
			if(StringUtils.isNotBlank(roleLabelNames.toString())){
				roleLabelNames = roleLabelNames.deleteCharAt(roleLabelNames.length() - 1);
			}				
			basicMap.put("roleLableNames", roleLabelNames);		
		}
		
		//技能标签
		String skillLabel = decodeParam(memberResumeApply.getSkillLabel());
		if(StringUtils.isBlank(skillLabel)){
			basicMap.put("skillLabelNames", "");
		}else{
			if(skillLabel!= null && skillLabel.length()>1 && skillLabel.subSequence(0, 1).equals(",")){
				skillLabel = skillLabel.substring(1, skillLabel.length());
			}
			List<String> skillLabelNameList = Arrays.asList(skillLabel.toString().split(","));
			StringBuilder skillLabelNames = new StringBuilder("");
			for(String skillLabelName : skillLabelNameList){
				skillLabelNames.append(skillLabelName).append(",");
			}
			if(StringUtils.isNotBlank(skillLabelNames.toString())){
				skillLabelNames = skillLabelNames.deleteCharAt(skillLabelNames.length() - 1);					
			}
			basicMap.put("skillLabelNames", skillLabelNames);
		
		}	
		return basicMap;
	}
	
	private Map<String,Object> getResumeImages(HttpServletRequest request, Map<String,Object> imageMap,MemberResumeApply memberResumeApply){
		
		List<String> imgIdList = new ArrayList<String>();
		Map<String,String> params = new HashMap<String,String>();			
		//正面照
		if(StringUtils.isNotBlank(memberResumeApply.getImgUuid1())){
			imgIdList.add(memberResumeApply.getImgUuid1());
			params.put(memberResumeApply.getImgUuid1(),"frontImage");
		}else{
			params.put(StringUtils.EMPTY,"frontImage");
		}
		//左侧
		if(StringUtils.isNotBlank(memberResumeApply.getImgUuid2())){
			imgIdList.add(memberResumeApply.getImgUuid2());
			params.put(memberResumeApply.getImgUuid2(),"leftImage");
		}else{
			params.put(StringUtils.EMPTY,"leftImage");
		}
		//左45度
		if(StringUtils.isNotBlank(memberResumeApply.getImgUuid3())){
			imgIdList.add(memberResumeApply.getImgUuid3());
			params.put(memberResumeApply.getImgUuid3(),"left45Image");
		}else{
			params.put(StringUtils.EMPTY,"left45Image");
		}
		//右侧
		if(StringUtils.isNotBlank(memberResumeApply.getImgUuid4())){
			imgIdList.add(memberResumeApply.getImgUuid4());
			params.put(memberResumeApply.getImgUuid4(),"rightImage");
		}else{
			params.put(StringUtils.EMPTY,"rightImage");
		}
		//右侧45
		if(StringUtils.isNotBlank(memberResumeApply.getImgUuid5())){
			imgIdList.add(memberResumeApply.getImgUuid5());
			params.put(memberResumeApply.getImgUuid5(),"right45Image");
		}else{
			params.put(StringUtils.EMPTY,"right45Image");
		}
		//全身
		if(StringUtils.isNotBlank(memberResumeApply.getImgUuid6())){
			imgIdList.add(memberResumeApply.getImgUuid6());
			params.put(memberResumeApply.getImgUuid6(),"fullImage");
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
	
	private List<Map<String,Object>> getResumePhotos(HttpServletRequest request,List<Map<String,Object>> resumePhotoList,
			String memberId,MemberResumeApply memberResumeApply){
		String resumeId = memberResumeApply.getId();
		Map<String,Object> photosParams = new HashMap<String,Object>();
		photosParams.put("resumeId", resumeId);
		List<MemberResumeApplyPhotos> photoList = memberResumeApplyPhotosService.selectAll(photosParams);
		if(photoList == null || photoList.isEmpty()){
			return resumePhotoList;
		}
		List<String> imgIdList = new ArrayList<String>();
		Map<String,String> imgMap = new HashMap<String,String>();
		for(MemberResumeApplyPhotos infoPhoto : photoList){
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
		List<SystemPictureInfo> picList = systemPictureInfoService.selectByUuids(imgIdList);
		if(picList == null || picList.isEmpty()){
			return resumePhotoList;
		}
		Map<String,String> picMap = new HashMap<String,String>();
		for(SystemPictureInfo pictureInfo : picList){
			picMap.put(pictureInfo.getUuid(), pictureInfo.getUrlPath());		
		}
		String basePath = getBasePath(request);
		for(MemberResumeApplyPhotos infoPhoto : photoList){
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
}
