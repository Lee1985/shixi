package com.bluemobi.www.security.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.multipart.MultipartFile;

import com.bluemobi.www.constant.Constant;
import com.bluemobi.www.data.model.activity.ActivityCompanyInternal;
import com.bluemobi.www.data.model.activity.ActivityInfo;
import com.bluemobi.www.data.model.activity.ActivityVideoComment;
import com.bluemobi.www.data.model.activity.ActivityVideoInfo;
import com.bluemobi.www.data.model.activity.ActivityVideoLike;
import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.member.MemberResumeTemplate;
import com.bluemobi.www.data.model.recruit.RecruitInfo;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfo;
import com.bluemobi.www.data.model.system.DiscoverInfo;
import com.bluemobi.www.data.model.system.SystemAdvertisementInfo;
import com.bluemobi.www.data.model.system.SystemFileInfo;
import com.bluemobi.www.data.model.system.SystemHomePage;
import com.bluemobi.www.data.model.system.SystemLableInfo;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.activity.service.ActivityCompanyInternalService;
import com.bluemobi.www.security.activity.service.ActivityInfoService;
import com.bluemobi.www.security.activity.service.ActivityVideoCommentService;
import com.bluemobi.www.security.activity.service.ActivityVideoInfoService;
import com.bluemobi.www.security.activity.service.ActivityVideoLikeService;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberFollowInfoService;
import com.bluemobi.www.security.member.service.MemberInfoService;
import com.bluemobi.www.security.member.service.MemberResumeTemplateService;
import com.bluemobi.www.security.recruit.service.RecruitInfoService;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoService;
import com.bluemobi.www.security.system.service.DiscoverInfoService;
import com.bluemobi.www.security.system.service.SystemAdvertisementInfoService;
import com.bluemobi.www.security.system.service.SystemFileInfoService;
import com.bluemobi.www.security.system.service.SystemHomePageService;
import com.bluemobi.www.security.system.service.SystemHotspotCityService;
import com.bluemobi.www.security.system.service.SystemLableInfoService;
import com.bluemobi.www.security.system.service.SystemPictureInfoService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.FileTool;
import com.bluemobi.www.utils.UUIDUtil;
import com.bluemobi.www.utils.compare.IntegerComparator;

@Controller
public class AppHomePageInfoIfController extends BaseController {
	
	@Resource
	private SystemHomePageService systemHomePageService;
	
	@Resource
	private RecruitInfoService recruitInfoService;
	
	@Resource
	private SystemAdvertisementInfoService systemAdvertisementInfoService;
	
	@Resource
	private SystemFileInfoService systemFileInfoService;
	
	@Resource
	private ActivityInfoService activityInfoService;
	
	@Resource
	private ActivityVideoInfoService activityVideoInfoService;
	
	@Resource
	private MemberInfoService memberInfoService;
	
	@Resource
	private ActivityVideoCommentService activityVideoCommentService;
	
	@Resource
	private SystemPictureInfoService systemPictureInfoService;
	
	@Resource
	private MemberFollowInfoService memberFollowInfoService;
	
	@Resource
	private SystemHotspotCityService systemHotspotCityService;
	
	@Resource
	private RecruitRoleInfoService recruitRoleInfoService;
	
	@Resource
	private SystemLableInfoService systemLableInfoService;
	
	@Resource
	private MemberResumeTemplateService memberResumeTemplateService;
	
	@Resource
	private ActivityVideoLikeService activityVideoLikeService;
	
	@Resource
	private ActivityCompanyInternalService activityCompanyInternalService;
	
	@Resource
	private DiscoverInfoService discoverInfoService;
	
	/**
	 * 首页信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/home_page_if")
	@ResponseBody
	public Map<String,Object> home_page_if(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "orderList");
		params.put("order", "asc");
		params.put("status", '1');
		List<SystemHomePage> homePageList = systemHomePageService.selectAll(params);
		if(homePageList != null && !homePageList.isEmpty()){
			for(SystemHomePage systemHomePage : homePageList){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", systemHomePage.getId());
				map.put("title", systemHomePage.getTitle());
				map.put("orderList", systemHomePage.getOrderList());
				map.put("status", systemHomePage.getStatus());
				map.put("type", systemHomePage.getType());
				//广告
				if("1".equals(systemHomePage.getType())){
					//查询启用的广告信息
					Map<String,Object> advParams = new HashMap<String,Object>();
					advParams.put("status", "1");
					advParams.put("sort", "orderList");
					advParams.put("order", "asc");
					List<SystemAdvertisementInfo> advList = systemAdvertisementInfoService.selectAll(advParams);
					List<Map<String,Object>> advInfoList = new ArrayList<Map<String,Object>>();
					if(advList != null && !advList.isEmpty()){
						//map.put("advList", advList);
						List<String> imageIdList = new ArrayList<String>();
						for(SystemAdvertisementInfo advertisementInfo : advList){
							imageIdList.add(advertisementInfo.getImgUuid());
						}
						List<SystemPictureInfo> picList = systemPictureInfoService.selectByUuids(imageIdList);
						Map<String,String> imageMap = new HashMap<String,String>();
						for(SystemPictureInfo pictureInfo : picList){
							imageMap.put(pictureInfo.getUuid(), pictureInfo.getUrlPath());
						}	
						for(SystemAdvertisementInfo advertisementInfo : advList){
							Map<String,Object> advMap = new HashMap<String,Object>();
							advMap.put("id", advertisementInfo.getId());
							advMap.put("title", advertisementInfo.getTitle());
							if(StringUtils.isNotBlank(imageMap.get(advertisementInfo.getImgUuid()))){
								advMap.put("imageUrl", getBasePath(request) + imageMap.get(advertisementInfo.getImgUuid()));
							}else{
								advMap.put("imageUrl", "");
							}							
							advMap.put("orderList", advertisementInfo.getOrderList());
							advMap.put("createDate", advertisementInfo.getCreateDate());						
							advInfoList.add(advMap);
						}
					}
					resultMap.put("advertisement", advInfoList);
				}else if("2".equals(systemHomePage.getType())){
					//招聘推荐只返回图片
					String imgUrl = getImageUrl(systemHomePage.getImgUuid());
					if(StringUtils.isNotBlank(imgUrl)){
						map.put("imgUrl", getBasePath(request) + imgUrl);
					}else{
						map.put("imgUrl", "");
					}					
					resultMap.put("recommendRecruit", map);
				}else if("3".equals(systemHomePage.getType())){
					//急聘信息
					String recruitId = systemHomePage.getRecruitId();
					if(StringUtils.isNotBlank(recruitId)){
						map.put("recruitId", recruitId);
						//获取预览图
						String imgUrl = getImageUrl(systemHomePage.getImgUuid());
						if(StringUtils.isNotBlank(imgUrl)){
							map.put("imgUrl", getBasePath(request) + imgUrl);
						}else{
							map.put("imgUrl", "");
						}
						resultMap.put("helpWanted", map);
					}else{
						resultMap.put("helpWanted", new HashMap<String,Object>());
					}					
					
				}else if("4".equals(systemHomePage.getType())){
					//飙戏活动
					String activityId = systemHomePage.getActivityId();
					if(StringUtils.isNotBlank(activityId)){						
						map.put("activityId", activityId);
						//获取预览图
						String imgUrl = getImageUrl(systemHomePage.getImgUuid());
						if(StringUtils.isNotBlank(imgUrl)){
							map.put("imgUrl", getBasePath(request) + imgUrl);
						}else{
							map.put("imgUrl", "");
						}
						resultMap.put("activity", map);
					}else{
						resultMap.put("activity", new HashMap<String,Object>());
					}
					
				}else if("5".equals(systemHomePage.getType())){
					//公司内部活动
					String companyActivityId = systemHomePage.getCompanyActivityId();					
					if(StringUtils.isNotBlank(companyActivityId)){
						ActivityCompanyInternal companyInternal = activityCompanyInternalService.selectById(companyActivityId);
						if(companyInternal == null){
							resultMap.put("companyEvent", new HashMap<String,Object>());
							return resultMap;
						}
						
						map.put("companyActivityId", companyActivityId);
						//获取预览图
						String imgUrl = getImageUrl(systemHomePage.getImgUuid());
						if(StringUtils.isNotBlank(imgUrl)){
							map.put("imgUrl", getBasePath(request) + imgUrl);
						}else{
							map.put("imgUrl", "");
						}			
						resultMap.put("companyEvent", map);	
					}else{
						resultMap.put("companyEvent", new HashMap<String,Object>());
					}									
				}
			}
		}		
		return resultMap;
	}
	
	
	/**
	 * 首页信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/discover_if")
	@ResponseBody
	public Map<String,Object> discover_if(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "orderList");
		params.put("order", "asc");
		params.put("status", '1');
		List<DiscoverInfo> discoverList = discoverInfoService.selectAll(params);			
		List<Map<String,Object>> infoList = new ArrayList<Map<String,Object>>();
		if(discoverList != null && !discoverList.isEmpty()){
			for(DiscoverInfo discoverInfo : discoverList){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", discoverInfo.getId());
				map.put("title", discoverInfo.getTitle());
				map.put("orderList", discoverInfo.getOrderList());
				map.put("status", discoverInfo.getStatus());
				map.put("type", discoverInfo.getType());
				//广告
				if("1".equals(discoverInfo.getType())){
					//查询启用的广告信息
					Map<String,Object> advParams = new HashMap<String,Object>();
					advParams.put("status", "1");
					advParams.put("sort", "orderList");
					advParams.put("order", "asc");
					List<SystemAdvertisementInfo> advList = systemAdvertisementInfoService.selectAll(advParams);
					List<Map<String,Object>> advInfoList = new ArrayList<Map<String,Object>>();
					if(advList != null && !advList.isEmpty()){
						//map.put("advList", advList);
						List<String> imageIdList = new ArrayList<String>();
						for(SystemAdvertisementInfo advertisementInfo : advList){
							imageIdList.add(advertisementInfo.getImgUuid());
						}
						List<SystemPictureInfo> picList = systemPictureInfoService.selectByUuids(imageIdList);
						Map<String,String> imageMap = new HashMap<String,String>();
						for(SystemPictureInfo pictureInfo : picList){
							imageMap.put(pictureInfo.getUuid(), pictureInfo.getUrlPath());
						}	
						for(SystemAdvertisementInfo advertisementInfo : advList){
							Map<String,Object> advMap = new HashMap<String,Object>();
							advMap.put("id", advertisementInfo.getId());
							advMap.put("title", advertisementInfo.getTitle());
							if(StringUtils.isNotBlank(imageMap.get(advertisementInfo.getImgUuid()))){
								advMap.put("imageUrl", getBasePath(request) + imageMap.get(advertisementInfo.getImgUuid()));
							}else{
								advMap.put("imageUrl", "");
							}							
							advMap.put("orderList", advertisementInfo.getOrderList());
							advMap.put("createDate", advertisementInfo.getCreateDate());						
							advInfoList.add(advMap);
						}
					}
					resultMap.put("advertisement", advInfoList);
				}else if("2".equals(discoverInfo.getType())){
					//招聘推荐只返回图片
					String imgUrl = discoverInfo.getCover();
					if(StringUtils.isNotBlank(imgUrl)){
						map.put("imgUrl", getBasePath(request) + imgUrl);
					}else{
						map.put("imgUrl", "");
					}					
					resultMap.put("recommendRecruit", map);
				}else if("3".equals(discoverInfo.getType())){
					//急聘信息
					String recruitId = discoverInfo.getBusinessId();
					if(StringUtils.isNotBlank(recruitId)){
						map.put("recruitId", recruitId);
						//获取预览图
						String imgUrl = discoverInfo.getCover();
						if(StringUtils.isNotBlank(imgUrl)){
							map.put("imgUrl", getBasePath(request) + imgUrl);
						}else{
							map.put("imgUrl", "");
						}
						//resultMap.put("helpWanted", map);
						infoList.add(map);
					}else{
						//resultMap.put("helpWanted", new HashMap<String,Object>());
					}					
					
				}else if("4".equals(discoverInfo.getType())){
					//飙戏活动
					String activityId = discoverInfo.getBusinessId();
					if(StringUtils.isNotBlank(activityId)){						
						map.put("activityId", activityId);
						//获取预览图
						String imgUrl = discoverInfo.getCover();
						if(StringUtils.isNotBlank(imgUrl)){
							map.put("imgUrl", getBasePath(request) + imgUrl);
						}else{
							map.put("imgUrl", "");
						}
						//resultMap.put("activity", map);
						infoList.add(map);
					}else{
						//resultMap.put("activity", new HashMap<String,Object>());
					}
					
				}else if("5".equals(discoverInfo.getType())){
					//公司内部活动
					String companyActivityId = discoverInfo.getBusinessId();	
					if(StringUtils.isNotBlank(companyActivityId)){
						ActivityCompanyInternal companyInternal = activityCompanyInternalService.selectById(companyActivityId);
						if(companyInternal == null){
							resultMap.put("companyEvent", new HashMap<String,Object>());
							return resultMap;
						}
						
						map.put("companyActivityId", companyActivityId);
						//获取预览图
						String imgUrl = discoverInfo.getCover();
						if(StringUtils.isNotBlank(imgUrl)){
							map.put("imgUrl", getBasePath(request) + imgUrl);
						}else{
							map.put("imgUrl", "");
						}			
						infoList.add(map);
						//resultMap.put("companyEvent", map);	
					}else{
						//resultMap.put("companyEvent", new HashMap<String,Object>());
					}									
				}
				resultMap.put("info", infoList);
			}
		}		
		return resultMap;
	}
	
	private String getImageUrl(String imgUuid){
		if(imgUuid != null){
			Map<String,Object> fileParams = new HashMap<String,Object>();
			fileParams.put("uuid", imgUuid);
			List<SystemPictureInfo> fileList =  systemPictureInfoService.selectAll(fileParams);
			if(fileList == null || fileList.isEmpty()){
				return "";
			}
			SystemPictureInfo pictureInfo = fileList.get(0);
			return pictureInfo.getUrlPath();
		}else{
			return "";
		}
	}
	
	/**
	 * 活动详情
	 * @param request
	 * @param response
	 * @param activityId
	 * @return
	 */
	@RequestMapping(value = "/activity_detail_if")
	@ResponseBody
	public Map<String,Object> activity_detail_if(HttpServletRequest request,HttpServletResponse response,
			String activityId,String memberId){
		Map<String,Object> map = new HashMap<String,Object>();
		ActivityInfo activityInfo = activityInfoService.selectById(activityId);
		if(activityInfo == null){
			map.put("success", "no");
			map.put("message", "找不到相关的活动信息!");
			return map;
		}
		map.put("id", activityId);
		map.put("title", activityInfo.getTitle());
		map.put("content", activityInfo.getContent());
		String videoId = activityInfo.getVideoUuid();
		if(StringUtils.isNotBlank(videoId)){
			SystemFileInfo fileInfo = systemFileInfoService.selectByUuid(videoId);
			if(fileInfo != null){
				String urlPath = fileInfo.getUrlPath();
				map.put("videoUrl", getBasePath(request) + urlPath);
			}
		}				
		//获取飙戏视频
		Map<String,Object> videoParams = new HashMap<String,Object>();
		videoParams.put("activityId", activityId);
		videoParams.put("status", "1");
		videoParams.put("sort", "isHot desc,createDate desc");					
		//当前用户的视频置顶->热门视频->时间倒叙排序
		List<ActivityVideoInfo> activityVideoList = activityVideoInfoService.selectAll(videoParams);
		if(activityVideoList == null){
			activityVideoList = new ArrayList<ActivityVideoInfo>();
		}
		List<String> videoIdList = new ArrayList<String>();
		List<String> memberIdList = new ArrayList<String>();
		for(ActivityVideoInfo activityVideoInfo : activityVideoList){
			videoIdList.add(activityVideoInfo.getVideoId());
			memberIdList.add(activityVideoInfo.getMemberId());
		}
		Map<String,String> fileMap = new HashMap<String,String>();
		if(!videoIdList.isEmpty()){
			List<SystemFileInfo> fileList = systemFileInfoService.selectByUuids(videoIdList);			
			for(SystemFileInfo systemFileInfo : fileList){
				fileMap.put(systemFileInfo.getUuid(), systemFileInfo.getUrlPath());
			}
		}
		
		Map<String,MemberInfo> memberMap = new HashMap<String,MemberInfo>();
		if(!memberIdList.isEmpty()){
			List<MemberInfo> memberList = memberInfoService.selectByIdsWithImage(memberIdList);			
			for(MemberInfo memberInfo : memberList){
				memberMap.put(memberInfo.getId(), memberInfo);
			}
		}
				
		List<Map<String,Object>> videoList = new ArrayList<Map<String,Object>>();
		for(ActivityVideoInfo activityVideoInfo : activityVideoList){
			Map<String,Object> videoMap = new HashMap<String,Object>();
			String fileUrlPath = fileMap.get(activityVideoInfo.getVideoId());
			if(StringUtils.isNotBlank(fileUrlPath)){
				videoMap.put("videoUrlPath", getBasePath(request) + fileUrlPath);
			}else{
				videoMap.put("videoUrlPath", "");
			}			
			videoMap.put("viewNum", activityVideoInfo.getViewNum() == null ? 0 :  activityVideoInfo.getViewNum());
			videoMap.put("shareNum", activityVideoInfo.getShareNum() == null ? 0 :  activityVideoInfo.getShareNum());
			videoMap.put("likeNum", activityVideoInfo.getLikeNum() == null ? 0 :  activityVideoInfo.getLikeNum());
			videoMap.put("commentNum", activityVideoInfo.getCommentNum() == null ? 0 : activityVideoInfo.getCommentNum());
			videoMap.put("createDate", activityVideoInfo.getCreateDate() == null ? "" : activityVideoInfo.getCreateDate());
			videoMap.put("isHot", activityVideoInfo.getIsHot() == null ? "" : activityVideoInfo.getIsHot());
			videoMap.put("memberId", activityVideoInfo.getMemberId() == null ? "" : activityVideoInfo.getMemberId());			
			MemberInfo memberInfo = memberMap.get(activityVideoInfo.getMemberId());		
			String headImageUrl = memberInfo.getHeadImageUrl();
			if(StringUtils.isNotBlank(headImageUrl)){
				videoMap.put("headUrl", getBasePath(request) + headImageUrl);
			}else{
				videoMap.put("headUrl", "");
			}
			videoMap.put("identityStatus", memberInfo.getIdentityStatus() == null ? "" : memberInfo.getIdentityStatus());
			videoMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
			videoMap.put("phoneType", activityVideoInfo.getPhoneType() == null ? "" : activityVideoInfo.getPhoneType());
			videoMap.put("nickName", memberInfo.getNickname() == null ? "" : memberInfo.getNickname());
			videoMap.put("activityVideoId", activityVideoInfo.getId() == null ? "" : activityVideoInfo.getId());
			videoMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());						
			if(StringUtils.isNotBlank(memberId) && memberId.equals(memberInfo.getId())){
				//当前用户
				videoMap.put("orderNo", 1);
			}else{
				videoMap.put("orderNo", 0);
			}					
			//TODO 实名验证显示不同效果
			
			videoMap.put("title", activityInfo.getTitle());
			videoMap.put("description", activityInfo.getContent());
			videoMap.put("url", getBasePath(request) + "rest/view/videoInfo.do?videoId="+activityVideoInfo.getId());
			videoMap.put("images", getBasePath(request) + activityInfo.getPictureInfo().getUrlPath());
			
			videoList.add(videoMap);
		}
		//当前用户的视频置顶
		Collections.sort(videoList, new IntegerComparator());
		map.put("videoList", videoList);	
		
		return map;
	}
	
	/**
	 * 添加活动飙戏视频
	 * @param request
	 * @param response
	 * @param activityId
	 * @param videoFile
	 * @return
	 */
	@RequestMapping(value = "/activity_add_video_if")
	@ResponseBody
	public Map<String,Object> activity_add_video_if(HttpServletRequest request,HttpServletResponse response,
			String activityId,String memberId,MultipartFile videoFile,String phoneType){
		Map<String,Object> map = new HashMap<String,Object>();
		if(videoFile == null){
			map.put("success", "no");
			map.put("message", "找不到视频文件!");
			return map;
		}
		if(StringUtils.isBlank(activityId)){
			map.put("success", "no");
			map.put("message", "找不到活动信息!");
			return map;
		}
		//视频上传
		SystemFileInfo fileInfo = uploadAndGetVideoInfo(request,videoFile);
		if(fileInfo == null){
			map.put("success", "no");
			map.put("message", "找不到视频文件!");
			return map;
		}
		ActivityVideoInfo activityVideoInfo = new ActivityVideoInfo();
		activityVideoInfo.setId(UUIDUtil.getUUID());
		activityVideoInfo.setVideoId(fileInfo.getUuid());
		activityVideoInfo.setActivityId(activityId);
		activityVideoInfo.setMemberId(memberId);
		activityVideoInfo.setViewNum(0);
		activityVideoInfo.setShareNum(0);
		activityVideoInfo.setLikeNum(0);
		activityVideoInfo.setCommentNum(0);
		activityVideoInfo.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		activityVideoInfo.setStatus("0");
		activityVideoInfo.setIsHot("0");
		activityVideoInfo.setPhoneType(phoneType);
		int result = activityVideoInfoService.insert(activityVideoInfo);
		if(result > 0){
			map.put("success", "yes");
			map.put("message", "添加飙戏视频成功,正在审核!");
			return map;
		}else{
			map.put("success", "no");
			map.put("message", "添加飙戏视频失败!");
		}
		return map;
	}
	
	/**
	 * 获取活动视频详情页
	 * @param request
	 * @param response
	 * @param activityId
	 * @param activityVideoId
	 * @return 
	 */
	@RequestMapping(value = "/activity_video_detail_if")
	@ResponseBody
	public Map<String,Object> activity_video_detail_if(HttpServletRequest request,HttpServletResponse response,
			String activityId, String activityVideoId, Integer page,Integer rows){
		Map<String,Object> map = new HashMap<String,Object>();
		
		//活动信息
		ActivityInfo activityInfo = activityInfoService.selectById(activityId);
		if(activityInfo == null){
			map.put("success", "no");
			map.put("message", "找不到相关的活动信息!");
			return map;
		}
		Map<String,Object> activityMap = new HashMap<String,Object>();
		activityMap.put("id", activityId);
		activityMap.put("title", activityInfo.getTitle());
		activityMap.put("content", activityInfo.getContent());
				
		//活动视频信息
		ActivityVideoInfo activityVideoInfo = activityVideoInfoService.selectById(activityVideoId);
		if(activityVideoInfo == null){
			map.put("success", "no");
			map.put("message", "找不到当前用户的活动视频信息!");
			return map;
		}
		if(StringUtils.isBlank(activityVideoInfo.getVideoId())){
			map.put("success", "no");
			map.put("message", "找不到当前用户的活动视频信息!");
			return map;
		}
		Map<String,Object> videoMap = new HashMap<String,Object>();
		videoMap.put("activityVideoId", activityVideoId);
		Map<String,Object> fileParams = new HashMap<String,Object>();
		fileParams.put("uuid", activityVideoInfo.getVideoId());
		List<SystemFileInfo> fileList = systemFileInfoService.selectAll(fileParams);
		if(fileList == null || fileList.isEmpty()){
			map.put("success", "no");
			map.put("message", "找不到当前用户的活动视频信息!");
			return map;
		}
		SystemFileInfo systemFileInfo = fileList.get(0);
		if(StringUtils.isNotBlank(systemFileInfo.getUrlPath())){
			videoMap.put("videoUrlPath", getBasePath(request) + systemFileInfo.getUrlPath());
		}else{
			videoMap.put("videoUrlPath", "");
		}		
		videoMap.put("viewNum", activityVideoInfo.getViewNum() == null ? 0 : activityVideoInfo.getViewNum());
		videoMap.put("shareNum", activityVideoInfo.getShareNum() == null ? 0 : activityVideoInfo.getShareNum());
		videoMap.put("likeNum", activityVideoInfo.getLikeNum() == null ? 0 : activityVideoInfo.getLikeNum());
		videoMap.put("commentNum", activityVideoInfo.getCommentNum() == null ? 0 : activityVideoInfo.getCommentNum());
		videoMap.put("createDate", activityVideoInfo.getCreateDate() == null ? "" : activityVideoInfo.getCreateDate());
		videoMap.put("isHot", activityVideoInfo.getIsHot() == null ? "" : activityVideoInfo.getIsHot());
		videoMap.put("memberId", activityVideoInfo.getMemberId() == null ? "" : activityVideoInfo.getMemberId());
		MemberInfo memberInfo = memberInfoService.selectById(activityVideoInfo.getMemberId());		
		videoMap.put("memberName", memberInfo.getNickname() == null ? "" : memberInfo.getNickname());
		videoMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
		String imgUuid = memberInfo.getImgUuid();
		fileParams.put("uuid", imgUuid);
		fileList = systemFileInfoService.selectAll(fileParams);
		if(fileList != null && !fileList.isEmpty()){
			SystemFileInfo fileInfo = fileList.get(0);
			videoMap.put("headUrl", getBasePath(request) + fileInfo.getUrlPath());
		}else{
			videoMap.put("headUrl", "");
		}		
		videoMap.put("identityStatus", memberInfo.getIdentityStatus() == null ? "" : memberInfo.getIdentityStatus());
		videoMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
		videoMap.put("phoneType", activityVideoInfo.getPhoneType() == null ? "" : activityVideoInfo.getPhoneType());
				
		//获取评论信息
		List<Map<String,Object>> commentList = new ArrayList<Map<String,Object>>();
		Map<String,Object> commentParams = new HashMap<String,Object>();
		commentParams.put("videoInfoId", activityVideoId);
		commentParams.put("order", "asc");
		commentParams.put("sort", "createDate");
		PageInfo<ActivityVideoComment> pageInfo = new PageInfo<ActivityVideoComment>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		PageInfo<ActivityVideoComment> commentPage =  activityVideoCommentService.selectAll(commentParams,pageInfo);
		List<ActivityVideoComment> videoCommentList = commentPage.getRows();
		if(videoCommentList != null && !videoCommentList.isEmpty()){
			List<String> memberIdList = new ArrayList<String>();
			for(ActivityVideoComment activityVideoComment : videoCommentList){
				memberIdList.add(activityVideoComment.getMemberId());
			}
			List<MemberInfo> memberList = memberInfoService.selectByIdsWithImage(memberIdList);
			Map<String,MemberInfo> memberMap = new HashMap<String,MemberInfo>();
			for(MemberInfo info : memberList){
				memberMap.put(info.getId(), info);
			}
			for(ActivityVideoComment activityVideoComment : videoCommentList){
				Map<String,Object> commentMap = new HashMap<String,Object>();
				MemberInfo member = memberMap.get(activityVideoComment.getMemberId());
				commentMap.put("memberId",member.getId() == null ? "" : member.getId());
				commentMap.put("memberName",member.getNickname() == null ? "" : member.getNickname());
				if(StringUtils.isNotBlank(member.getHeadImageUrl())){
					commentMap.put("headUrl",getBasePath(request) + member.getHeadImageUrl());
				}else{
					commentMap.put("headUrl","");
				}				
				commentMap.put("identityStatus", member.getIdentityStatus() == null ? "" : member.getIdentityStatus());
				commentMap.put("realNameStatus", member.getRealNameStatus() == null ? "" : member.getRealNameStatus());
				commentMap.put("createDate", activityVideoComment.getCreateDate() == null ? "" : activityVideoComment.getCreateDate());
				commentMap.put("content", activityVideoComment.getContent() == null ? "" : activityVideoComment.getContent());
				commentList.add(commentMap);
			}
		}
		map.put("success", "yes");
		map.put("activity", activityMap);//活动信息
		map.put("activityVideo", videoMap);//活动视频信息
		map.put("commentList", commentList);//评论信息
		Map<String,Object> shareMap = new HashMap<String,Object>();
		shareMap.put("title", activityInfo.getTitle());
		shareMap.put("description", activityInfo.getContent());
		shareMap.put("url", getBasePath(request) + "rest/view/videoInfo.do?videoId="+activityVideoId);
		shareMap.put("images", getBasePath(request) + activityInfo.getPictureInfo().getUrlPath());
		map.put("shareMap", shareMap);//
		
		return map;
	}
	
	/**
	 * 重新上传活动视频
	 * @param request
	 * @param response
	 * @param activityVideoId
	 * @param videoFile
	 * @return
	 */
	@RequestMapping(value = "/reup_activity_video_if")
	@ResponseBody
	public Map<String,Object> reup_activity_video_if(HttpServletRequest request,HttpServletResponse response,
			String activityVideoId,MultipartFile videoFile){
		Map<String,Object> map = new HashMap<String,Object>();
		if(videoFile == null){
			map.put("success", "no");
			map.put("message", "找不到需要替换的活动视频!");
			return map;
		}
		ActivityVideoInfo activityVideoInfo = activityVideoInfoService.selectById(activityVideoId);
		if(activityVideoInfo == null){
			map.put("success", "no");
			map.put("message", "找不到需要替换的活动视频!");
			return map;
		}
		SystemFileInfo videoFileInfo = systemFileInfoService.selectByUuid(activityVideoInfo.getVideoId());
		//删除视频
		if(videoFileInfo != null){
			systemFileInfoService.delete(videoFileInfo);
			FileTool.deleteFile(videoFileInfo.getUrlPath());
		}
		//添加新视频 
		SystemFileInfo newFileInfo = uploadAndGetVideoInfo(request,videoFile);
		if(newFileInfo == null){
			map.put("success", "no");
			map.put("message", "替换视频失败!");
			return map;
		}
		//修改活动视频				
		activityVideoInfo.setVideoId(newFileInfo.getUuid());
		activityVideoInfo.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		activityVideoInfoService.update(activityVideoInfo);
		map.put("success", "yes");
		map.put("message", "替换视频成功!");
		map.put("videoUrl", newFileInfo.getUrlPath());
		return map;
	}
	
	/**
	 * 删除活动视频
	 * @param request
	 * @param response
	 * @param activityVideoId
	 * @param videoFile
	 * @return
	 */
	@RequestMapping(value = "/delete_activity_video_if")
	@ResponseBody
	public Map<String,Object> delete_activity_video_if(HttpServletRequest request,HttpServletResponse response,
			String activityVideoId){
		Map<String,Object> map = new HashMap<String,Object>();
		ActivityVideoInfo activityVideoInfo = activityVideoInfoService.selectById(activityVideoId);
		if(activityVideoInfo == null){
			map.put("success", "no");
			map.put("message", "找不到需要删除的活动视频!");
			return map;
		}
		SystemFileInfo videoFileInfo = systemFileInfoService.selectByUuid(activityVideoInfo.getVideoId());
		//删除视频
		int result = activityVideoInfoService.delete(activityVideoInfo);
		if(videoFileInfo != null){
			if(systemFileInfoService.delete(videoFileInfo) > 0){
				FileTool.deleteFile(request.getSession().getServletContext().getRealPath("/" + videoFileInfo.getUrlPath()));
			}			
			//删除视频评论
			Map<String,Object> activityVideoParams = new HashMap<String,Object>();
			activityVideoParams.put("videoInfoId", activityVideoInfo.getVideoId());
			List<ActivityVideoComment> commentList = activityVideoCommentService.selectAll(activityVideoParams);
			if(commentList != null){
				for(ActivityVideoComment activityVideoComment : commentList){
					activityVideoCommentService.delete(activityVideoComment);
				}
			}				
		}		
		if(result > 0){
			map.put("success", "yes");
			map.put("message", "删除视频成功!");
			return map;
		}else{
			map.put("success", "no");
			map.put("message", "删除视频失败!");
			return map;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
		}
	}
	
	/**
	 * 活动视频评论列表
	 * @param request
	 * @param response
	 * @param activityVideoId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/comments_info_if")
	@ResponseBody
	public Map<String,Object> comments_info_if(HttpServletRequest request,HttpServletResponse response,
			String activityVideoId,Integer page,Integer rows){
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isBlank(activityVideoId)){
			map.put("success", "no");
			map.put("message", "找不到相关视频信息!");
			return map;
		}
		Map<String,Object> commentParams = new HashMap<String,Object>();
		commentParams.put("videoInfoId", activityVideoId);
		commentParams.put("order", "desc");
		commentParams.put("sort", "createDate");
		PageInfo<ActivityVideoComment> pageInfo = new PageInfo<ActivityVideoComment>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		PageInfo<ActivityVideoComment> commentPage= activityVideoCommentService.selectAll(commentParams,pageInfo);
		List<ActivityVideoComment> commentList = commentPage.getRows();
		if(commentList == null || commentList.isEmpty()){
			map.put("success", "yes");
			map.put("message", "暂时还没有评论信息!");
			return map;
		}
		List<String> memberIdList = new ArrayList<String>();
		for(ActivityVideoComment activityVideoComment : commentList){
			memberIdList.add(activityVideoComment.getMemberId());
		}
		List<MemberInfo> memberList = memberInfoService.selectByIdsWithImage(memberIdList);
		if(memberList == null || memberList.isEmpty()){
			map.put("success", "yes");
			map.put("message", "暂时还没有评论信息!");
			return map;
		}
		Map<String,MemberInfo> memberMap = new HashMap<String,MemberInfo>();
		for(MemberInfo memberInfo : memberList){
			memberMap.put(memberInfo.getId(), memberInfo);
		}
		List<Map<String,Object>> comments = new ArrayList<Map<String,Object>>();
		for(ActivityVideoComment activityVideoComment : commentList){
			Map<String,Object> commentMap = new HashMap<String,Object>();
			MemberInfo member = memberMap.get(activityVideoComment.getMemberId());
			commentMap.put("memberId",member.getId() == null ? "" : member.getId());
			commentMap.put("memberName",member.getNickname() == null ? "" : decodeParam(member.getNickname()));
			if(StringUtils.isNotBlank(member.getHeadImageUrl())){
				commentMap.put("headUrl",getBasePath(request) + member.getHeadImageUrl());
			}else{
				commentMap.put("headUrl","");
			}				
			commentMap.put("identityStatus", member.getIdentityStatus() == null ? "" : member.getIdentityStatus());
			commentMap.put("realNameStatus", member.getRealNameStatus() == null ? "" : member.getRealNameStatus());
			commentMap.put("createDate", activityVideoComment.getCreateDate() == null ? "" : activityVideoComment.getCreateDate());
			commentMap.put("content", activityVideoComment.getContent() == null ? "" : decodeParam(activityVideoComment.getContent()));
			comments.add(commentMap);
		}
		map.put("success", "yes");
		map.put("comments", comments);		
		return map;
	}
	
	/**
	 * 发表评论
	 * @param request
	 * @param response
	 * @param memberId
	 * @param activityVideoId
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/send_comments_if")
	@ResponseBody
	public Map<String,Object> send_comments_if(HttpServletRequest request,HttpServletResponse response,
			String memberId,String activityVideoId,String content){
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isBlank(memberId)){
			map.put("success", "no");
			map.put("message", "找不到用户信息!");
			return map;
		}		
		if(StringUtils.isBlank(activityVideoId)){
			map.put("success", "no");
			map.put("message", "找不到活动视频!");
			return map;
		}
		if(StringUtils.isBlank(content)){
			map.put("success", "no");
			map.put("message", "请输入评论内容!");
			return map;
		}
		ActivityVideoComment activityVideoComment = new ActivityVideoComment();
		activityVideoComment.setId(UUIDUtil.getUUID());
		activityVideoComment.setMemberId(memberId);
		activityVideoComment.setVideoInfoId(activityVideoId);
		activityVideoComment.setContent(encodeParam(content));
		activityVideoComment.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		int result = activityVideoCommentService.insert(activityVideoComment);
		if(result <= 0){
			map.put("success", "yes");
			map.put("message", "评论失败!");
			return map;
		}
		//修改活动视频评论计数
		ActivityVideoInfo activityVideoInfo = activityVideoInfoService.selectById(activityVideoId);
		int commentNum = 0;
		if(activityVideoInfo.getLikeNum() != null){
			commentNum = activityVideoInfo.getCommentNum();
		}
		activityVideoInfo.setCommentNum(commentNum + 1);
		result = activityVideoInfoService.update(activityVideoInfo);	
		if(result > 0){
			map.put("success", "yes");
			map.put("message", "评论成功!");
		}else{
			map.put("success", "yes");
			map.put("message", "评论失败!");
		}
		return map;
	}
	
	/**
	 * 点赞
	 * @param request
	 * @param response
	 * @param memberId
	 * @param activityVideoId
	 * @return
	 */
	@RequestMapping(value = "/click_like_if")
	@ResponseBody
	public Map<String,Object> click_like_if(HttpServletRequest request,HttpServletResponse response,
			String memberId,String activityVideoId){
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isBlank(memberId)){
			map.put("success", "no");
			map.put("message", "找不到用户信息!");
			return map;
		}
		if(StringUtils.isBlank(activityVideoId)){
			map.put("success", "no");
			map.put("message", "找不到活动视频!");
			return map;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("memberId", memberId);
		params.put("videoInfoId", activityVideoId);
		int likeCount = activityVideoLikeService.selectCount(params);
		if(likeCount > 0){
			map.put("success", "no");
			map.put("message", "您已点过赞啦!");
			return map;
		}
		ActivityVideoLike activityVideoLike = new ActivityVideoLike();
		activityVideoLike.setId(UUIDUtil.getUUID());
		activityVideoLike.setMemberId(memberId);
		activityVideoLike.setVideoInfoId(activityVideoId);
		activityVideoLike.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		int result = activityVideoLikeService.insert(activityVideoLike);
		if(result <= 0){
			map.put("success", "no");
			map.put("message", "点赞失败!");
			return map;
		}
		//修改活动视频点赞计数
		int likeNum = 0;
		ActivityVideoInfo activityVideoInfo = activityVideoInfoService.selectById(activityVideoId);
		if(activityVideoInfo == null){
			map.put("success", "no");
			map.put("message", "点赞失败!");
			return map;
		}
		if(activityVideoInfo.getLikeNum() != null){
			likeNum = activityVideoInfo.getLikeNum();
		}
		activityVideoInfo.setLikeNum(likeNum + 1);
		result = activityVideoInfoService.update(activityVideoInfo);
		if(result > 0){
			map.put("success", "yes");
			map.put("message", "点赞成功!");
		}else{
			map.put("success", "no");
			map.put("message", "点赞失败!");
		}				
		return map;
	}
	
	/**
	 * 播放视频计数
	 * @param request
	 * @param response
	 * @param memberId
	 * @param activityVideoId
	 * @return
	 */
	@RequestMapping(value = "/view_video_count_if")
	@ResponseBody
	public Map<String,Object> view_video_count_if(HttpServletRequest request,HttpServletResponse response,
			String memberId,String activityVideoId){
		Map<String,Object> map = new HashMap<String,Object>();
		//修改活动视频点赞计数
		ActivityVideoInfo activityVideoInfo = activityVideoInfoService.selectById(activityVideoId);
		int viewNum = 0;
		if(activityVideoInfo.getViewNum() != null){
			viewNum = activityVideoInfo.getViewNum();
		}
		activityVideoInfo.setViewNum(viewNum + 1);
		int result = activityVideoInfoService.update(activityVideoInfo);
		if(result > 0){
			map.put("success", "yes");
			map.put("message", "播放计数成功!");
		}else{
			map.put("success", "no");
			map.put("message", "播放计数失败!");
		}		
		return map;
	}
	
	/**
	 * 招募推荐列表
	 * @param request
	 * @param response
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/recommended_recruitment_if")
	@ResponseBody
	public Map<String,Object> recommended_recruitment_if(HttpServletRequest request,HttpServletResponse response,String memberId){
		Map<String,Object> map = new HashMap<String,Object>();
		//查询用户信息
		MemberInfo memberInfo = memberInfoService.selectById(memberId);
		if(memberInfo == null){
			map.put("success", "yes");
			map.put("message", "获取用户信息失败!");
			return map;   
		}
				
		//简历标签
		MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(memberId);
		if(memberResumeTemplate==null){
			map.put("success", "yes");
			map.put("message", "您还没有添加自己简历，请新添加简历!");
			return map;
		}
		String userRoleLabel = memberResumeTemplate.getRoleLabel();
		if(StringUtils.isBlank(userRoleLabel)){
			map.put("success", "yes");
			map.put("message", "您还没有添加自己的标签呢，添加几个试试吧!");
			return map;
		}
		userRoleLabel = new StringBuilder(userRoleLabel).deleteCharAt(0).toString();
		List<String> userRoleLableList = new ArrayList<String>(Arrays.asList(userRoleLabel.split(",")));
		
		//查询可用的招募信息(未删除,已审核,未过期)
		Map<String,Object> recruitParams = new HashMap<String,Object>();
		recruitParams.put("isDelete", "0");
		recruitParams.put("status", "1");
		recruitParams.put("dateNow", DateUtils.currentYourDate("yyyy-MM-dd"));
		recruitParams.put("sort", "createDate");
		recruitParams.put("order", "desc");
		List<RecruitInfo> recruitList = recruitInfoService.selectAll(recruitParams);
		if(recruitList == null || recruitList.isEmpty()){
			map.put("success", "yes");
			map.put("message", "也许你的标签太冷门了，换一下自己的标签试试?");
			return map;
		}
		List<String> idList = new ArrayList<String>();
		Map<String,RecruitInfo> recruitInfoMap = new HashMap<String,RecruitInfo>();
		for(RecruitInfo recruitInfo : recruitList){
			idList.add(recruitInfo.getId());
			recruitInfoMap.put(recruitInfo.getId(), recruitInfo);
		}				
		//根据招募id列表查询角色
		List<RecruitRoleInfo> recruitRoleList = recruitRoleInfoService.selectByRecruitIdsWithPictureInfo(idList);
		if(recruitRoleList == null || recruitRoleList.isEmpty()){
			map.put("success", "yes");
			map.put("message", "也许你的标签太冷门了，换一下自己的标签试试?");
			return map;
		}	
		
		//获取所有标签列表
		Map<String,Object> labelParams = new HashMap<String,Object>();
		labelParams.put("status", "1");
		List<SystemLableInfo> labelList = systemLableInfoService.selectAll(labelParams);
		Map<String,String> labelMap = new HashMap<String,String>();
		if(labelList != null && !labelList.isEmpty()){
			for(SystemLableInfo systemLableInfo : labelList){				
				labelMap.put(systemLableInfo.getId(), systemLableInfo.getLableName());
			}
		}
		
		List<Map<String,Object>> recommendedList = new ArrayList<Map<String,Object>>();
		for(RecruitRoleInfo recruitRoleInfo : recruitRoleList){
			Map<String,Object> recommendedMap = new HashMap<String,Object>();						
			//获取角色标签
			String roleLabels = recruitRoleInfo.getLableNames();
			if(StringUtils.isNotBlank(roleLabels)){				
				if(roleLabels.substring(0, 1).equals(",")){
					roleLabels = roleLabels.substring(1, roleLabels.length());
				}		
						
				List<String> roleLabelList = new ArrayList<String>(Arrays.asList(roleLabels.split(",")));
				//与用户的角色获取交集
				if(userRoleLableList.isEmpty()){
					userRoleLableList = new ArrayList<String>(Arrays.asList(userRoleLabel.split(",")));
				}
				userRoleLableList.retainAll(roleLabelList);
				int orderNo = userRoleLableList.size();
				if(orderNo <= 0){
					continue;
				}											
				recommendedMap.put("roleId", recruitRoleInfo.getId() == null ? "" : recruitRoleInfo.getId());
				recommendedMap.put("roleName", recruitRoleInfo.getName() == null ? "" : recruitRoleInfo.getName());
				if(StringUtils.isNotBlank(recruitRoleInfo.getImageUrl())){
					recommendedMap.put("roleImageUrl",getBasePath(request) + recruitRoleInfo.getImageUrl());
				}else{
					recommendedMap.put("roleImageUrl","");
				}				
				recommendedMap.put("rolePaidMin", recruitRoleInfo.getPaidMin() == null ? "" : recruitRoleInfo.getPaidMin());
				recommendedMap.put("rolePaidMax", recruitRoleInfo.getPaidMax() == null ? "" : recruitRoleInfo.getPaidMax());
				recommendedMap.put("rolePaidType", recruitRoleInfo.getPaidType() == null ? "" : recruitRoleInfo.getPaidType());
				recommendedMap.put("requirement", recruitRoleInfo.getRequirement() == null ? "" : recruitRoleInfo.getRequirement());
				RecruitInfo recruitInfo = recruitRoleInfo.getRecruitInfo();
				recommendedMap.put("publishMemberId", recruitInfo.getMemberId());
				
				if(recruitInfo != null){
					recommendedMap.put("recruitName", recruitInfo.getTitle());
				}else{
					recommendedMap.put("recruitName", "");
				}					
				recommendedMap.put("orderNo", userRoleLableList.size());				
				StringBuilder labelNames = new StringBuilder("");
				for(String labelId : roleLabelList){
					labelNames.append(labelMap.get(labelId)).append(",");
				}
				labelNames.deleteCharAt(labelNames.length() - 1);
				recommendedMap.put("labelNames", labelNames.toString());
				recommendedList.add(recommendedMap);
			}
		}		
		if(recommendedList.isEmpty()){
			map.put("success", "yes");
			map.put("message", "也许你的标签太冷门了，换一下自己的标签试试?");
			return map;
		}
		Collections.sort(recommendedList, new IntegerComparator());
		map.put("success", "yes");
		map.put("recommendeds", recommendedList);
		return map;
	}
	
	/**
	 * 增加分享数
	 * @param request
	 * @param response
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/rest/addShareVideoNum")
	@ResponseBody
	public Map<String,Object> addShareVideoNum(HttpServletRequest request,HttpServletResponse response,ActivityVideoInfo info){
		Map<String,Object> map = new HashMap<String, Object>();
		int result = activityVideoInfoService.addShareNum(info);
		if(result == 1){
			map.put("success", "yes");
		}else{
			map.put("success", "no");
		}
		return map;
	
	}
	
	private SystemFileInfo uploadAndGetVideoInfo(HttpServletRequest request,MultipartFile videoFile){
		String root = request.getSession().getServletContext().getRealPath("/");
		String pathTmp = Constant.UPLOAD_ACTIVITY_PATH + "/";
		String path = pathTmp + DateUtils.toString(new Date(), "yyyy/MM/dd") + "/";
		String realPath = root + path;
		return systemFileInfoService.insertFileInfo(videoFile, realPath, path);		
	}
	
	@RequestMapping(value = "/getMemberInfo")
	@ResponseBody
	public Map<String,Object> getMemberInfo(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();		
		MemberInfo memberInfo = memberInfoService.selectById("0625cfa937ce427fb7892e3a8cab7847");
		map.put("id", memberInfo.getId());
		map.put("nickName", memberInfo.getNickname());
		map.put("mobile", memberInfo.getMobile());
		map.put("email", memberInfo.getEmail());
		return map;
	}
}
