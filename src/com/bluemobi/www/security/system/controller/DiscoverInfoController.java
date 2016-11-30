package com.bluemobi.www.security.system.controller;


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
import com.bluemobi.www.constant.DiscoverTypeEnum;
import com.bluemobi.www.data.model.activity.ActivityCompanyInternal;
import com.bluemobi.www.data.model.activity.ActivityInfo;
import com.bluemobi.www.data.model.recruit.RecruitInfo;
import com.bluemobi.www.data.model.system.DiscoverInfo;
import com.bluemobi.www.data.model.system.FileInfo;
import com.bluemobi.www.data.model.system.SystemHomePage;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.security.activity.service.ActivityCompanyInternalService;
import com.bluemobi.www.security.activity.service.ActivityInfoService;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.recruit.service.RecruitInfoService;
import com.bluemobi.www.security.system.service.DiscoverInfoService;
import com.bluemobi.www.security.system.service.SystemPictureInfoService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.FileTool;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: DiscoverInfoController 
* @Description: 发现控制层 
* @author lip 
* @date 2016-08-10 23:21:27 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class DiscoverInfoController extends BaseController {
	
	@Resource
	private DiscoverInfoService service;
	
	@Resource
	private ActivityCompanyInternalService activityCompanyInternalService;
	
	@Resource
	private RecruitInfoService recruitInfoService;
	
	@Resource
	private ActivityInfoService activityInfoService;
	
	@Resource
	private SystemPictureInfoService systemPictureInfoService;

	@RequestMapping(value = "system/discoverInfoList")
	public String discoverInfoList(HttpServletRequest request,HttpServletResponse response) {
		return "system/discover_info_list";
	}

	@RequestMapping(value = "system/discoverInfoAjaxPage")
	@ResponseBody
	public List<DiscoverInfo> discoverInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, DiscoverInfo info, Integer page,
			Integer rows) {
		info.setSort("orderList");
		info.setOrder("asc");
		return service.selectAll(info);
	}

	@RequestMapping(value = "system/discoverInfoAjaxAll")
	@ResponseBody
	public List<DiscoverInfo> discoverInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, DiscoverInfo info, Integer page,
			Integer rows) {
		List<DiscoverInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/discoverInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> discoverInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, DiscoverInfo info) {
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

	@RequestMapping(value = "system/discoverInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> discoverInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, DiscoverInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
		
	@RequestMapping(value = "system/discoverUpdateStatus")
	@ResponseBody
	public Map<String,Object> discoverUpdateStatus(HttpServletRequest request,
			HttpServletResponse response, DiscoverInfo info) {
		int result = 0;
		try {
			result = service.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
	
	@RequestMapping(value = "system/configActivityCompanyInternalList")
	public String configActivityCompanyInternalList(HttpServletRequest request,HttpServletResponse response){
		return "system/config_activity_company_internal_list";
	}
	
	@RequestMapping(value = "system/configActivityCompanyInternalDoList")
	@ResponseBody
	public List<ActivityCompanyInternal> configActivityCompanyInternalDoList(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "createDate");
		params.put("order", "desc");
		return activityCompanyInternalService.selectAll(params); 
	}
	
	@RequestMapping(value = "system/configActivityCompany")
	@ResponseBody
	public Map<String,Object> configActivityCompany(HttpServletRequest request,HttpServletResponse response,String ids){
		int result = 0;
		if(StringUtils.isBlank(ids)){
			return getJsonResult(result,"", "请至少选择一个内部活动！");
		}						
		String[] idArray = ids.split(",");		
		for(String id : idArray){			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("type", String.valueOf(DiscoverTypeEnum.COMPANY_INNER_ACTIVITY.getCode()));
			params.put("businessId", id);
			List<DiscoverInfo> discoverList = service.selectAll(params);
			if(discoverList != null && !discoverList.isEmpty()){
				continue;
			}			
			DiscoverInfo discoverInfo = new DiscoverInfo();
			discoverInfo.setId(UUIDUtil.getUUID());
			discoverInfo.setBusinessId(id);
			discoverInfo.setCreateTime(new Date());
			discoverInfo.setType(String.valueOf(DiscoverTypeEnum.COMPANY_INNER_ACTIVITY.getCode()));
			discoverInfo.setStatus("1");
			discoverInfo.setOrderList(maxOrder() + 1);
			
			
			ActivityCompanyInternal activityCompanyInternal = activityCompanyInternalService.selectById(id);
			//设置标题
			String title = activityCompanyInternal.getTitle();
			discoverInfo.setTitle(title);
			//查找封面			
			String imgUuid = activityCompanyInternal.getImgUuid();
			SystemPictureInfo systemPictureInfo = systemPictureInfoService.selectByUuid(imgUuid);
			discoverInfo.setCover(systemPictureInfo.getUrlPath());
			
			result = service.insert(discoverInfo) + result;
		}		
		return getJsonResult(1,"操作成功", "操作失败！");
	}
	
	private int maxOrder(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "orderList");
		params.put("order", "desc");
		List<DiscoverInfo> discoverList = service.selectAll(params);
		int maxValue = 0;
		if(discoverList != null && !discoverList.isEmpty()){
			DiscoverInfo info = discoverList.get(0);
			maxValue = info.getOrderList();
		}
		return maxValue;
	}
	
	@RequestMapping(value = "system/removeDiscoverItem")
	@ResponseBody
	public Map<String,Object> removeDiscoverItem(HttpServletRequest request,HttpServletResponse response,String id){
		int result = 0;
		if(StringUtils.isBlank(id)){
			return getJsonResult(result,"", "请至少选择一项！");
		}
		DiscoverInfo info = new DiscoverInfo();
		info.setId(id);
		service.delete(info);
		return getJsonResult(1,"操作成功", "操作失败！");
	}
	
	/**
	 * 配置急聘信息列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "system/configImportantRecruitList")
	public String configImportantRecruitList(HttpServletRequest request,HttpServletResponse response){
		return "system/config_important_recruit_list";
	}
	
	/**
	 * 急聘信息列表数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "system/configImportantRecruitDoList")
	@ResponseBody
	public List<RecruitInfo> configImportantRecruitDoList(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("isDelete", "0");
		params.put("status", "-1");
		params.put("publishStatus", "1");
		params.put("sort", "createDate");
		params.put("order", "desc");
		return recruitInfoService.selectAll(params);			
	}
	
	@RequestMapping(value = "system/configImportantRecruit")
	@ResponseBody
	public Map<String,Object> configImportantRecruit(HttpServletRequest request,HttpServletResponse response,String ids){
		int result = 0;
		if(StringUtils.isBlank(ids)){
			return getJsonResult(result,"", "请至少选择一个急聘信息！");
		}						
		String[] idArray = ids.split(",");		
		for(String id : idArray){			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("type", String.valueOf(DiscoverTypeEnum.IMPORTANT_RECRUITMENT.getCode()));
			params.put("businessId", id);
			List<DiscoverInfo> discoverList = service.selectAll(params);
			if(discoverList != null && !discoverList.isEmpty()){
				continue;
			}			
			DiscoverInfo discoverInfo = new DiscoverInfo();
			discoverInfo.setId(UUIDUtil.getUUID());
			discoverInfo.setBusinessId(id);
			discoverInfo.setCreateTime(new Date());
			discoverInfo.setType(String.valueOf(DiscoverTypeEnum.IMPORTANT_RECRUITMENT.getCode()));
			discoverInfo.setStatus("1");
			discoverInfo.setOrderList(maxOrder() + 1);			
			result = service.insert(discoverInfo) + result;
			
			RecruitInfo recruitInfo = recruitInfoService.selectById(id);
			//设置标题
			String title = recruitInfo.getTitle();
			discoverInfo.setTitle(title);
		}		
		return getJsonResult(1,"操作成功", "操作失败！");
	}
	
	/**
	 * 配置彪戏活动列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "system/configActingActivityList")
	public String configActingActivityList(HttpServletRequest request,HttpServletResponse response){
		return "system/config_acting_activity_list";
	}
	
	/**
	 * 急聘信息列表数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "system/configActingActivityDoList")
	@ResponseBody
	public List<ActivityInfo> configActingActivityDoList(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("status", "1");
		params.put("sort", "createDate");
		params.put("order", "desc");		
		return activityInfoService.selectAll(params);
	}
	
	@RequestMapping(value = "system/configActingActivity")
	@ResponseBody
	public Map<String,Object> configActingActivity(HttpServletRequest request,HttpServletResponse response,String ids){
		int result = 0;
		if(StringUtils.isBlank(ids)){
			return getJsonResult(result,"", "请至少选择一个彪戏活动！");
		}						
		String[] idArray = ids.split(",");		
		for(String id : idArray){			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("type", String.valueOf(DiscoverTypeEnum.ACTING_ACTIVITY.getCode()));
			params.put("businessId", id);
			List<DiscoverInfo> discoverList = service.selectAll(params);
			if(discoverList != null && !discoverList.isEmpty()){
				continue;
			}			
			DiscoverInfo discoverInfo = new DiscoverInfo();
			discoverInfo.setId(UUIDUtil.getUUID());
			discoverInfo.setBusinessId(id);
			discoverInfo.setCreateTime(new Date());
			discoverInfo.setType(String.valueOf(DiscoverTypeEnum.ACTING_ACTIVITY.getCode()));
			discoverInfo.setStatus("1");
			discoverInfo.setOrderList(maxOrder() + 1);
			
			ActivityInfo activityInfo = activityInfoService.selectById(id);
			//设置标题
			discoverInfo.setTitle(activityInfo.getTitle());
			//查找封面			
			String imgUuid = activityInfo.getImgUuid();
			SystemPictureInfo systemPictureInfo = systemPictureInfoService.selectByUuid(imgUuid);
			discoverInfo.setCover(systemPictureInfo.getUrlPath());
			
			result = service.insert(discoverInfo) + result;
		}		
		return getJsonResult(1,"操作成功", "操作失败！");
	}
	
	@RequestMapping(value = "system/configCover")
	@ResponseBody
	public Map<String,Object> systemHomePageAjaxSave(HttpServletRequest request,
			HttpServletResponse response,DiscoverInfo info) {
		int result = 0;
		String msg = "";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String root = request.getSession().getServletContext().getRealPath("/");
		// 上传图片
		String meetingImgUuid = UUIDUtil.getUUID();
		MultipartFile headImg = multipartRequest.getFile("sysImg");
		if (headImg != null) {
			// 删除图片
			deleteImg(info.getId(), root);
			// 新图片处理
			SystemPictureInfo pictureInfo = saveImg(root, meetingImgUuid, headImg);
			info.setCover(pictureInfo.getUrlPath());
		}
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
	
	private int deleteImg(String id, String root) {
		if (id != null && !id.equals("")) {
			// 删除旧图片
			DiscoverInfo info = service.selectById(id);
			if (StringUtils.isNotBlank(info.getCover())) {
				String delRealPath = info.getCover();
				if (delRealPath != null) {
					deleteFile(root + delRealPath);					
				}
			}
		}
		return 0;
	}
	
	private SystemPictureInfo saveImg(String root, String meetingImgUuid, MultipartFile listfile) {
		String pathTmp = Constant.UPLOAD_HOMEPAGE_PATH + "/";
		String path = pathTmp + DateUtils.toString(new Date(), "yyyy/MM/dd") + "/";
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
		pictureInfo.setCdate(DateUtils.toString(new Date(),"yyyy-MM-dd HH:mm:ss"));
		systemPictureInfoService.insert(pictureInfo);
		return pictureInfo;
	}
}
