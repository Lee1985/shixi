package com.bluemobi.www.security.app.recruit;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bluemobi.www.constant.Constant;
import com.bluemobi.www.data.model.member.MemberActionsInfo;
import com.bluemobi.www.data.model.member.MemberFollowInfo;
import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.recruit.RecruitApplyInfo;
import com.bluemobi.www.data.model.recruit.RecruitCategory;
import com.bluemobi.www.data.model.recruit.RecruitInfo;
import com.bluemobi.www.data.model.recruit.RecruitInfoTemp;
import com.bluemobi.www.data.model.recruit.RecruitInformantInfo;
import com.bluemobi.www.data.model.recruit.RecruitInformantType;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfo;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfoTemp;
import com.bluemobi.www.data.model.system.FileInfo;
import com.bluemobi.www.data.model.system.SystemHotspotCity;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.data.model.system.SystemRolePic;
import com.bluemobi.www.data.model.system.SystemRoleType;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberActionsInfoService;
import com.bluemobi.www.security.member.service.MemberFollowInfoService;
import com.bluemobi.www.security.member.service.MemberInfoService;
import com.bluemobi.www.security.recruit.service.RecruitApplyInfoService;
import com.bluemobi.www.security.recruit.service.RecruitCategoryService;
import com.bluemobi.www.security.recruit.service.RecruitInfoService;
import com.bluemobi.www.security.recruit.service.RecruitInfoTempService;
import com.bluemobi.www.security.recruit.service.RecruitInformantInfoService;
import com.bluemobi.www.security.recruit.service.RecruitInformantTypeService;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoService;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoTempService;
import com.bluemobi.www.security.system.service.SystemHotspotCityService;
import com.bluemobi.www.security.system.service.SystemLableInfoService;
import com.bluemobi.www.security.system.service.SystemPictureInfoService;
import com.bluemobi.www.security.system.service.SystemRolePicService;
import com.bluemobi.www.security.system.service.SystemRoleTypeService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.FileTool;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
 * @author sundq 招募模块接口(招募，角色)
 */
@Controller
public class RestRecruitInfoController extends BaseController {
	
	@Resource
	private RecruitInfoService service;
	@Resource
	private SystemPictureInfoService systemPictureInfoService;
	@Resource
	private RecruitCategoryService categoryService;
	@Resource
	private RecruitRoleInfoService roleInfoService;
	@Resource
	private SystemLableInfoService lableInfoService;
	@Resource
	private SystemRoleTypeService typeService;
	@Resource
	private SystemRolePicService picService;
	@Resource
	private MemberFollowInfoService followService;
	@Resource
	private RecruitInformantTypeService informantTypeService;
	@Resource
	private RecruitInformantInfoService infoService;
	@Resource
	private RecruitApplyInfoService applyInfoService;
	@Resource
	private RecruitInfoTempService tempService;
	@Resource
	private RecruitRoleInfoTempService infoTempService;
	@Resource
	private MemberInfoService memberInfoService;
	@Resource
	private MemberActionsInfoService actionsInfoService;
	@Resource
	private SystemHotspotCityService cityService;
	@Resource
	private SystemRolePicService systemRolePicService;
	
	@RequestMapping(value = "rest/myRecruitInfoList")
	@ResponseBody
	public Map<String, Object> myRecruitInfoList(HttpServletRequest request,
			HttpServletResponse response, String memberId, Integer page, Integer rows) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isBlank(memberId)){
			map.put("success", "yes");
			map.put("message", "用户信息错误!");
			return map;
		}						
		PageInfo<RecruitInfoTemp> pageInfo = new PageInfo<RecruitInfoTemp>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);		
		RecruitInfoTemp info = new RecruitInfoTemp();
		info.setIsDelete("0");
		info.setSort("createDate");
		info.setOrder("desc");
		info.setMemberId(memberId);
		tempService.selectAll(info, pageInfo);
		if (pageInfo.getRows() == null || pageInfo.getRows().isEmpty()) {
			map.put("success", "yes");
			map.put("message", "无结果!");
			return map;
		}
		MemberInfo memberInfo = memberInfoService.selectById(memberId);
		if(memberInfo == null){
			map.put("success", "yes");
			map.put("message", "用户信息错误!");
			return map;
		}
		SystemPictureInfo pictureInfo = memberInfo.getPictureInfo();
		String userHeadUrl = "";
		if(pictureInfo != null){
			userHeadUrl = getBasePath(request) + pictureInfo.getUrlPath();
		}
		
		//关注数
		Map<String, Object> followMap = new HashMap<String, Object>();
		followMap.put("followMemberId", memberId);
		Integer followCount = followService.selectCount(followMap);
		followCount = followCount == null ? 0 : followCount;
				
		//关联类别
		List<String> categoryIdList = new ArrayList<String>();
		for(RecruitInfoTemp recruitInfoTemp : pageInfo.getRows()){
			categoryIdList.add(recruitInfoTemp.getLableCode());
		}		
		Map<String,RecruitCategory> categoryMap = new HashMap<String,RecruitCategory>();
		if(categoryIdList != null && !categoryIdList.isEmpty()){
			List<RecruitCategory> categoryList = categoryService.selectByIds(categoryIdList);
			for(RecruitCategory recruitCategory : categoryList){
				categoryMap.put(recruitCategory.getId(), recruitCategory);
			}
		}
		
		//关联城市
		List<String> cityIdList = new ArrayList<String>();
		for(RecruitInfoTemp recruitInfoTemp : pageInfo.getRows()){
			cityIdList.add(recruitInfoTemp.getCityCode());
		}		
		Map<String,SystemHotspotCity> cityMap = new HashMap<String,SystemHotspotCity>();
		if(cityIdList != null && !cityIdList.isEmpty()){
			List<SystemHotspotCity> cityList = cityService.selectByIds(cityIdList);
			for(SystemHotspotCity city : cityList){
				cityMap.put(city.getId(), city);
			}
		}
		
		//关联审核通过的招募信息
		List<String> recruitIdList = new ArrayList<String>();
		for(RecruitInfoTemp recruitInfoTemp : pageInfo.getRows()){
			if(StringUtils.isBlank(recruitInfoTemp.getRecruitId())){
				continue;
			}
			recruitIdList.add(recruitInfoTemp.getRecruitId());
		}		
		Map<String,RecruitInfo> recruitInfoMap = new HashMap<String,RecruitInfo>();
		if(recruitIdList != null && !recruitIdList.isEmpty()){
			List<RecruitInfo> recruitInfoList = service.selectByIds(recruitIdList);
			for(RecruitInfo recruitInfo : recruitInfoList){
				recruitInfoMap.put(recruitInfo.getId(), recruitInfo);
			}
		}
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (RecruitInfoTemp recruitInfoTemp : pageInfo.getRows()) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", recruitInfoTemp.getId());
			resultMap.put("name", decodeParam(memberInfo.getNickname()));
			resultMap.put("urlPath", userHeadUrl);
			resultMap.put("title", decodeParam(recruitInfoTemp.getTitle()));// 标题
			
			RecruitCategory category = categoryMap.get(recruitInfoTemp.getLableCode());
			resultMap.put("lableName", category.getName());// 类型
			resultMap.put("lableColor", category.getColor());// 类型颜色
			resultMap.put("cityCode", recruitInfoTemp.getCityCode());// 城市编码
			resultMap.put("cityName", cityMap.get(recruitInfoTemp.getCityCode()).getCityName());// 城市名称
						
			RecruitInfo recruitInfo = recruitInfoMap.get(recruitInfoTemp.getRecruitId());			
			resultMap.put("views", recruitInfo == null ? 0 : recruitInfo.getViewNum());// 浏览数
			resultMap.put("recruitMemberId", memberId);// 发布招募的用户ID
			resultMap.put("publishStatus", recruitInfo == null ? 0 : recruitInfo.getPublishStatus());// 加急状态 // 1加急
			resultMap.put("type", recruitInfoTemp.getType());// 招募类型 1-官方招募 // 2-私人招募
			resultMap.put("vipStatus", memberInfo.getVip());// 1-v
			resultMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
			resultMap.put("follows",followCount);// 关注数
			if (recruitInfoTemp.getType() != null && recruitInfoTemp.getType().equals("1")) {// 官方认证返回关注数				
				resultMap.put("identity", memberInfo.getIdentityInfo());// 身份
			}
			resultMap.put("status", recruitInfoTemp.getStatus());// 状态  1通过 2 拒绝  0未审核 3修改审核 4未提交 5禁用
			resultMap.put("remainTime",DateUtils.getDateSubtraction(recruitInfoTemp.getDeadlineDate(), new Date()));// 剩余天数
			resultList.add(resultMap);
		}
		map.put("success", "yes");
		map.put("totle", pageInfo.getTotal());
		map.put("page", pageInfo.getPage());
		map.put("pageSize", pageInfo.getPageSize());
		map.put("data", resultList);		
		return map;		
	}
	
	@RequestMapping(value = "rest/recruit/selectMyRecruitById")
	@ResponseBody
	public Map<String, Object> selectMyRecruitById(HttpServletRequest request,
			HttpServletResponse response, String recruitId, String memberId) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		RecruitInfoTemp recruitInfoTemp = tempService.selectById(recruitId);
		if (recruitInfoTemp != null) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", recruitInfoTemp.getId());
			resultMap.put("recruitId", recruitInfoTemp.getRecruitId());
			MemberInfo memberInfo = recruitInfoTemp.getMemberInfo();
			resultMap.put("name", decodeParam(memberInfo.getNickname()));
			resultMap.put("vipStatus", memberInfo.getVip());// 1-v
			resultMap.put("urlPath", getBasePath(request) + memberInfo.getPictureInfo().getUrlPath());
			resultMap.put("lableId", recruitInfoTemp.getLableCode());// 类型
			resultMap.put("title", decodeParam(recruitInfoTemp.getTitle()));// 标题
			RecruitCategory category = recruitInfoTemp.getCategory();
			resultMap.put("lableName", category.getName());// 类型
			resultMap.put("lableColor", category.getColor());// 类型颜色
			resultMap.put("cityCode", recruitInfoTemp.getCityCode());// 城市
			resultMap.put("cityName", recruitInfoTemp.getCity().getCityName());// 城市名称
			resultMap.put("type", recruitInfoTemp.getType());// 招募类型 1-官方招募 // 2-私人招募
			resultMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
			Map<String, Object> followMap = new HashMap<String, Object>();
			followMap.put("followMemberId", recruitInfoTemp.getMemberId());
			resultMap.put("follows", followService.selectCount(followMap));// 关注数
			resultMap.put("director", decodeParam(recruitInfoTemp.getDirector()));// 导演
			resultMap.put("screenwriter", decodeParam(recruitInfoTemp.getScreenwriter()));// 编剧
			resultMap.put("startDate", recruitInfoTemp.getStartDate());// 开始时间
			resultMap.put("endDate", recruitInfoTemp.getEndDate());// 结束时间
			resultMap.put("deadline", recruitInfoTemp.getDeadline());// 试戏截止时间
			resultMap.put("publishMemberId", recruitInfoTemp.getMemberId());// 发布人
			resultMap.put("scriptOutline",decodeParam(recruitInfoTemp.getScriptOutline()));// 剧本大纲
			resultMap.put("remark", decodeParam(recruitInfoTemp.getRemark()));// 备注
			followMap.put("memberId", memberId);
			resultMap.put("identity", recruitInfoTemp.getMemberInfo().getIdentityInfo());// 身份
			resultMap.put("remainTime",DateUtils.getDateSubtraction(recruitInfoTemp.getDeadlineDate(), new Date()));// 剩余天数
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("recruitId", recruitInfoTemp.getId());
			condition.put("status", "1");
			condition.put("isDelete", "0");
			condition.put("sort", "createDate");
			condition.put("order", "asc");
			List<RecruitRoleInfoTemp> list = infoTempService.selectAll(condition);
			for (RecruitRoleInfoTemp roleInfo : list) {
				Map<String, Object> resultRoleMap = new HashMap<String, Object>();
				resultRoleMap.put("id", roleInfo.getId());// 角色ID
				//String systemRolePicId = roleInfo.getImgUuid();
				//systemRolePicService.selectById(systemRolePicId);
				
				resultRoleMap.put("urlPath", getBasePath(request) + roleInfo.getPictureInfo().getUrlPath());// 角色头像
				resultRoleMap.put("name", decodeParam(roleInfo.getName()));// 角色名称
				resultRoleMap.put("roleId", roleInfo.getRoleId());// 正式表角色ID
				resultRoleMap.put("sex", roleInfo.getSex());// 角色性别 1 男 2 女
				resultRoleMap.put("paid",roleInfo.getPaidType().equals("面议") ? "面议": 
						roleInfo.getPaidMax() == null || roleInfo.getPaidMax().equals("") ?
						roleInfo.getPaidMin() + roleInfo.getPaidType():
						roleInfo.getPaidMin() + "-" + roleInfo.getPaidMax()
							+ roleInfo.getPaidType());
				resultRoleMap.put("paidType", roleInfo.getPaidType());
				resultRoleMap.put("paidMin", roleInfo.getPaidMin());
				resultRoleMap.put("paidMax", roleInfo.getPaidMax());
				resultRoleMap.put("requirement", decodeParam(roleInfo.getRequirement()));
				resultRoleMap.put("dialogue", decodeParam(roleInfo.getDialogue()));
				resultRoleMap.put("lableCode", decodeParam(roleInfo.getLableCode()));// 标签code
				resultRoleMap.put("lableName",getLableName(decodeParam(roleInfo.getLableCode())));// 标签名称
				resultList.add(resultRoleMap);
			}
			map.put("success", "yes");
			map.put("data", resultMap);
			map.put("dataRole", resultList);			
			Map<String,Object> shareMap = new HashMap<String,Object>();
			shareMap.put("title", decodeParam(recruitInfoTemp.getTitle()));
			shareMap.put("description", "");
			shareMap.put("url", getBasePath(request) + "rest/share/myShareRecruitInfo.do?recruitId="+recruitInfoTemp.getId());
			shareMap.put("images", "");
			map.put("shareMap", shareMap);
			map.put("recruitTempId", recruitInfoTemp.getId());
		} else {
			map.put("success", "yes");
			map.put("message", "无结果!");
		}
		return map;
	}
	
	/**
	 * 保存招募信息
	 * 
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "rest/recruit/saveMyRecruit")
	@ResponseBody
	public Map<String, Object> saveMyRecruit(HttpServletRequest request,HttpServletResponse response, RecruitInfoTemp info) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		try{
			//添加
			String recruitId = info.getRecruitId();
			
			//是否私人招募
			info.setType("2");
			if (info.getMemberId() != null && !info.getMemberId().equals("")) {
				MemberInfo memberInfo = memberInfoService.selectById(info.getMemberId());
				if (memberInfo.getIdentityStatus() != null && memberInfo.getIdentityStatus().equals("1")) {
					info.setType("1");
				}
			}
			if(StringUtils.isBlank(recruitId)){
				info.setId(UUIDUtil.getUUID());					
				if(StringUtils.isNotBlank(info.getTitle())){
					info.setTitle(encodeParam(info.getTitle()));
				}
				if(StringUtils.isNotBlank(info.getDirector())){
					info.setDirector(encodeParam(info.getDirector()));
				}
				if(StringUtils.isNotBlank(info.getScreenwriter())){
					info.setScreenwriter(encodeParam(info.getScreenwriter()));
				}									
				info.setIsDelete("0");													
				info.setStatus("4");
				info.setCreateDate(DateUtils.currentStringDate());
				result = tempService.insert(info);
				copyInfoToInfo(UUIDUtil.getUUID(), info.getId());													
			}else{
				//修改
				RecruitInfoTemp toEditInfo = tempService.selectById(recruitId);				
				if(toEditInfo == null){
					map.put("success", "no");
					map.put("message", "招募信息错误!");
				}
				String recruitInfoId = toEditInfo.getRecruitId();
				BeanUtils.copyProperties(info, toEditInfo);
				toEditInfo.setId(recruitId);
				toEditInfo.setRecruitId(recruitInfoId);
				
				if(StringUtils.isNotBlank(info.getTitle())){
					toEditInfo.setTitle(encodeParam(info.getTitle()));
				}
				if(StringUtils.isNotBlank(info.getDirector())){
					toEditInfo.setDirector(encodeParam(info.getDirector()));
				}
				if(StringUtils.isNotBlank(info.getScreenwriter())){
					toEditInfo.setScreenwriter(encodeParam(info.getScreenwriter()));
				}
				if(StringUtils.isNotBlank(info.getScriptOutline())){
					toEditInfo.setScriptOutline(encodeParam(info.getScriptOutline()));
				}
				if(StringUtils.isNotBlank(info.getRemark())){
					toEditInfo.setRemark(encodeParam(info.getRemark()));
				}
				toEditInfo.setStatus("3");
				result = tempService.update(toEditInfo);
				
				RecruitInfo recruitInfoNew = service.selectById(toEditInfo.getRecruitId());
				if(recruitInfoNew == null){
					recruitInfoNew = new RecruitInfo();
					BeanUtils.copyProperties(toEditInfo, recruitInfoNew);
					recruitInfoNew.setId(UUIDUtil.getUUID());
					result = service.insert(recruitInfoNew);
					info.setId(toEditInfo.getId());
					
					toEditInfo.setRecruitId(recruitInfoNew.getId());
					result = tempService.update(toEditInfo);
				}else{
					recruitInfoNew.setStatus("3");
					result = service.update(recruitInfoNew);
					info.setId(toEditInfo.getId());
				}
				
			}
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", info.getId());
			map.put("data", resultMap);
			if (result > 0) {
				map.put("recruitTempId", info.getId());
				map.put("success", "yes");
				map.put("message", "操作成功");
			} else {
				map.put("success", "no");
				map.put("message", "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		}			
		return map;
	}
	
	@RequestMapping(value = "rest/recruit/saveMyRecruitRole")
	@ResponseBody
	public Map<String, Object> saveMyRecruitRole(HttpServletRequest request,
			HttpServletResponse response, RecruitRoleInfoTemp info,MultipartFile sysImg) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			int result = 0;
			if(StringUtils.isBlank(info.getRoleId())){				
				if(sysImg == null && StringUtils.isBlank(info.getImgUuid())){
					map.put("success", "no");
					map.put("message", "找不到所需的角色头像!");
					return map;
				}				
				//判断是否是上传
				if(sysImg != null){
					String imgUuid = uploadAndGetImageId(request,sysImg);
					info.setImgUuid(imgUuid);
				}		
				//添加角色
				info.setId(UUIDUtil.getUUID());
				info.setRecruitId(info.getRecruitId());
				info.setStatus("1");
				info.setIsDelete("0");
				info.setCreateDate(DateUtils.currentStringDate());
				if(StringUtils.isNotBlank(info.getName())){
					info.setName(encodeParam(info.getName()));
				}				
				if(StringUtils.isNotBlank(info.getRequirement())){
					info.setRequirement(encodeParam(info.getRequirement()));
				}
				if(StringUtils.isNotBlank(info.getDialogue())){
					info.setDialogue(encodeParam(info.getDialogue()));
				}	
				if(StringUtils.isNotBlank(info.getLableCode())){
					info.setLableCode(encodeParam(info.getLableCode()));
				}				
				result = infoTempService.insert(info);								
			}else{
				RecruitRoleInfoTemp toEditInfo = infoTempService.selectById(info.getRoleId());
				//判断是否是上传
				if(sysImg != null){
					String imgUuid = uploadAndGetImageId(request,sysImg);
					toEditInfo.setImgUuid(imgUuid);
				}
								
				String roleImgUUID = toEditInfo.getImgUuid();				
				BeanUtils.copyProperties(info, toEditInfo);								
				toEditInfo.setId(info.getRoleId());
				if(StringUtils.isBlank(info.getImgUuid())){
					toEditInfo.setImgUuid(roleImgUUID);
				}				
				if(StringUtils.isNotBlank(info.getName())){
					toEditInfo.setName(encodeParam(info.getName()));
				}				
				if(StringUtils.isNotBlank(info.getRequirement())){
					toEditInfo.setRequirement(encodeParam(info.getRequirement()));
				}
				if(StringUtils.isNotBlank(info.getDialogue())){
					toEditInfo.setDialogue(encodeParam(info.getDialogue()));
				}				
				if(StringUtils.isNotBlank(info.getLableCode())){
					toEditInfo.setLableCode(encodeParam(info.getLableCode()));
				}
				result = infoTempService.update(toEditInfo);
			}
			
			//修改招募信息状态
			RecruitInfoTemp recruitInfoTemp = tempService.selectById(info.getRecruitId());
			if(recruitInfoTemp == null){
				map.put("success", "no");
				map.put("message", "招募信息错误!");
			}
			recruitInfoTemp.setStatus("3");
			result = tempService.update(recruitInfoTemp);
			
			RecruitInfo recruitInfo = service.selectById(recruitInfoTemp.getRecruitId());
			recruitInfo.setStatus("3");
			result = service.update(recruitInfo);									
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", info.getId());
			if (result > 0) {
				map.put("recruitTempId", recruitInfoTemp.getId());
				map.put("success", "yes");
				map.put("message", "操作成功");
			} else {
				map.put("success", "no");
				map.put("message", "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return map;
	}
	
	/**
	 * 获取招募列表(所有,我的,根据查询条件)
	 * 
	 * @param request
	 * @param response
	 * @param info
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "rest/recruitInfoList")
	@ResponseBody
	public Map<String, Object> recruitInfoList(HttpServletRequest request,
			HttpServletResponse response, RecruitInfo info, Integer page,String hideDeadline,
			Integer rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 传分页信息page页数
		PageInfo<RecruitInfo> pageInfo = new PageInfo<RecruitInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setIsDelete("0");
		info.setSort("");
		info.setOrder("publishStatus desc , createDate desc");
		if (info.getMemberId() != null && !info.getMemberId().equals("")) {
			//我发布的招募
		}else{
			info.setStatus("1");
		}
		
		if(info.getLableCodes() != null && !info.getLableCodes().equals("")){
			String[] codes = info.getLableCodes().split(",");
			List<String> codeList = new ArrayList<String>();
			for (int i = 0; i < codes.length; i++) {
				codeList.add(codes[i]);
			}
			info.setCodeList(codeList);
		}
		
		//是否是我发布的招募,如果memberId不为空,则为我发布的招募
		if(StringUtils.isBlank(info.getMemberId())){
			//不显示试戏截止日期到期的数据
			info.setHideDeadlineDate("1");
			//实名认证不通过则不显示该招募
			info.setRealNameIdentity("1");
		}else{
			if(StringUtils.isNotBlank(hideDeadline) && "1".equals(hideDeadline)){
				info.setHideDeadlineDate("1");
				//实名认证不通过则不显示该招募
				info.setRealNameIdentity("1");
			}
		}				
				
		service.selectAll(info, pageInfo);
		map.put("totle", pageInfo.getTotal());
		map.put("page", pageInfo.getPage());
		map.put("pageSize", pageInfo.getPageSize());
		if (pageInfo.getRows() == null || pageInfo.getRows().size() <= 0) {
			map.put("success", "yes");
			map.put("message", "无结果!");
		} else {
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (RecruitInfo recruitInfo : pageInfo.getRows()) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				RecruitCategory category = recruitInfo.getCategory();
				MemberInfo memberInfo = recruitInfo.getMemberInfo();
				if(memberInfo == null){
					continue;
				}
				resultMap.put("id", recruitInfo.getId());
				resultMap.put("name", decodeParam(memberInfo.getNickname()));
				resultMap.put("urlPath", getBasePath(request) + memberInfo.getPictureInfo().getUrlPath());
				resultMap.put("title", decodeParam(recruitInfo.getTitle()));// 标题
				resultMap.put("lableName", category.getName());// 类型
				resultMap.put("lableColor", category.getColor());// 类型颜色
				resultMap.put("cityCode", recruitInfo.getCityCode());// 城市编码
				resultMap.put("cityName", recruitInfo.getCity().getCityName());// 城市名称
				resultMap.put("views", recruitInfo.getViewNum());// 浏览数
				resultMap.put("recruitMemberId", recruitInfo.getMemberId());// 发布招募的用户ID
				resultMap.put("publishStatus", recruitInfo.getPublishStatus());// 加急状态
																				// 1加急
				resultMap.put("type", recruitInfo.getType());// 招募类型 1-官方招募
																// 2-私人招募
				resultMap.put("vipStatus", memberInfo.getVip());// 1-v
				resultMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
				if (recruitInfo.getType() != null && recruitInfo.getType().equals("1")) {// 官方认证返回关注数
					Map<String, Object> followMap = new HashMap<String, Object>();
					followMap.put("followMemberId", recruitInfo.getMemberId());
					resultMap.put("follows",followService.selectCount(followMap));// 关注数
					resultMap.put("identity", memberInfo.getIdentityInfo() == null ? "" : memberInfo.getIdentityInfo());// 身份
				}
				resultMap.put("status", recruitInfo.getStatus());// 状态  1通过 2 拒绝  0未审核 3修改审核 4未提交 5禁用
				resultMap.put("modify", recruitInfo.getModify());// 状态 1 处于修改审核中 0 未处于修改审核
				resultMap.put("remainTime",DateUtils.getDateSubtraction(recruitInfo.getDeadlineDate(), new Date()));// 剩余天数
				resultList.add(resultMap);
			}
			map.put("success", "yes");
			map.put("data", resultList);
		}
		return map;
	}

	/**
	 * 招募类别列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "rest/recruit/recruitCategoryList")
	@ResponseBody
	public Map<String, Object> recruitCategoryList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("status", "1");
		condition.put("sort", "orderList");
		condition.put("order", "asc");
		List<RecruitCategory> list = categoryService.selectAll(condition);
		if (list != null && !list.isEmpty()) {
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (RecruitCategory recruitCategory : list) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("id", recruitCategory.getId());
				resultMap.put("name", recruitCategory.getName());
				resultMap.put("color", recruitCategory.getColor());
				resultList.add(resultMap);
			}
			map.put("success", "yes");
			map.put("data", resultList);
		} else {
			map.put("success", "yes");
			map.put("message", "无结果!");
		}
		return map;
	}

	/**
	 * 删除招募
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "rest/recruit/deleteRecruit")
	@ResponseBody
	public Map<String, Object> deleteRecruit(HttpServletRequest request,
			HttpServletResponse response , String recruitId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		if(StringUtils.isBlank(recruitId)){
			map.put("success", "no");
			map.put("message", "招募信息错误!");
			return map;
		}
		RecruitInfoTemp recruitInfoTemp = tempService.selectById(recruitId);
		if(recruitInfoTemp == null){
			map.put("success", "no");
			map.put("message", "招募信息错误!");
			return map;
		}
		recruitInfoTemp.setIsDelete("1");
		result = tempService.update(recruitInfoTemp);
		
		String recruitInfoId = recruitInfoTemp.getRecruitId();
		if(StringUtils.isBlank(recruitInfoId)){
			map.put("success", "no");
			map.put("message", "招募信息错误!");
			return map;
		}
		RecruitInfo recruitInfo = service.selectById(recruitInfoId);
		if(recruitInfo == null){
			map.put("success", "no");
			map.put("message", "招募信息错误!");
			return map;
		}
		recruitInfo.setIsDelete("1");
		result = service.update(recruitInfo);				
		if(result>0){
			map.put("success", "yes");
			map.put("message", "删除成功");
		}else {
			map.put("success", "no");
			map.put("message", "删除失败");
		}
		return map;
	}
	
	/**
	 * 获取招募详细信息
	 * 
	 * @param request
	 * @param response
	 * @param recruitId
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "rest/recruit/selectRecruitById")
	@ResponseBody
	public Map<String, Object> selectRecruitById(HttpServletRequest request,
			HttpServletResponse response, String recruitId, String memberId,
			boolean viewBool, String edit) {
		Map<String, Object> map = new HashMap<String, Object>();
		RecruitInfo recruitInfo = service.selectById(recruitId);
		if (StringUtils.isBlank(edit) || ("1".equals(edit) && "1".equals(recruitInfo.getStatus()))) {
			if (recruitInfo != null) {
				MemberInfo memberInfo = recruitInfo.getMemberInfo();
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("id", recruitInfo.getId());
				resultMap.put("name", decodeParam(memberInfo.getNickname()));
				resultMap.put("urlPath", getBasePath(request) + memberInfo.getPictureInfo().getUrlPath());
				resultMap.put("vipStatus", memberInfo.getVip());
				resultMap.put("title", decodeParam(recruitInfo.getTitle()));// 标题
				resultMap.put("lableId", recruitInfo.getLableCode());// 类型
				RecruitCategory  category = recruitInfo.getCategory();
				resultMap.put("lableColor", category.getColor());// 类型颜色
				resultMap.put("lableName", category.getName());// 类型
				resultMap.put("cityCode", recruitInfo.getCityCode());// 城市
				resultMap.put("cityName", recruitInfo.getCity().getCityName());// 城市名称
				resultMap.put("views", recruitInfo.getViewNum());// 浏览数
				resultMap.put("type", recruitInfo.getType());// 招募类型 1-官方招募  // 2-私人招募
				resultMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
				Map<String, Object> followMap = new HashMap<String, Object>();
				followMap.put("followMemberId", recruitInfo.getMemberId());
				resultMap.put("follows", followService.selectCount(followMap));// 关注数
				
				resultMap.put("remainTime",DateUtils.getDateSubtraction(recruitInfo.getDeadlineDate(), new Date()));// 剩余天数
				resultMap.put("director", decodeParam(recruitInfo.getDirector()));// 导演
				resultMap.put("screenwriter", decodeParam(recruitInfo.getScreenwriter()));// 编剧
				resultMap.put("startDate", recruitInfo.getStartDate());// 开始时间
				resultMap.put("endDate", recruitInfo.getEndDate());// 结束时间
				resultMap.put("deadline", recruitInfo.getDeadline());// 试戏截止时间
				resultMap.put("publishMemberId", recruitInfo.getMemberId());// 发布人
				resultMap.put("scriptOutline", decodeParam(recruitInfo.getScriptOutline()));// 剧本大纲
				resultMap.put("remark", decodeParam(recruitInfo.getRemark()));// 备注
				followMap.put("memberId", memberId);
				if (memberId != null && !memberId.equals("")) {
					resultMap.put("isFollow",followService.selectCount(followMap));// 是否关注 1 是 0 // 否
				}
				resultMap.put("identity", recruitInfo.getMemberInfo().getIdentityInfo());// 身份
				List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("recruitId", recruitId);
				condition.put("status", "1");
				condition.put("isDelete", "0");
				condition.put("sort", "createDate");
				condition.put("order", "asc");
				List<RecruitRoleInfo> list = roleInfoService.selectAll(condition);
				for (RecruitRoleInfo roleInfo : list) {
					Map<String, Object> resultRoleMap = new HashMap<String, Object>();
					resultRoleMap.put("id", roleInfo.getId());// 角色ID
					resultRoleMap.put("urlPath", getBasePath(request) + roleInfo.getPictureInfo().getUrlPath());// 角色头像
					resultRoleMap.put("name", decodeParam(roleInfo.getName()));// 角色名称
					resultRoleMap.put("sex", roleInfo.getSex());// 角色性别 1 男 2 女
					resultRoleMap.put("paid",roleInfo.getPaidType().equals("面议") ? "面议" : 
						roleInfo.getPaidMax() == null || roleInfo.getPaidMax().equals("") ?
						roleInfo.getPaidMin() + roleInfo.getPaidType():
						roleInfo.getPaidMin() + "-" + roleInfo.getPaidMax() + roleInfo.getPaidType());
					resultRoleMap.put("paidType", roleInfo.getPaidType());
					resultRoleMap.put("paidMin", roleInfo.getPaidMin());
					resultRoleMap.put("paidMax", roleInfo.getPaidMax());
					resultRoleMap.put("requirement", decodeParam(roleInfo.getRequirement()));
					resultRoleMap.put("dialogue", decodeParam(roleInfo.getDialogue()));
					resultRoleMap.put("lableCode", roleInfo.getLableCode() != null && roleInfo.getLableCode().length() > 1 ? decodeParam(roleInfo.getLableCode()) : "");// 标签code
					resultRoleMap.put("lableName",getLableName(decodeParam(roleInfo.getLableCode())));// 标签名称
					resultList.add(resultRoleMap);
				}
				map.put("success", "yes");
				map.put("data", resultMap);
				map.put("dataRole", resultList);
				Map<String,Object> shareMap = new HashMap<String,Object>();
				shareMap.put("title", decodeParam(recruitInfo.getTitle()));
				shareMap.put("description", "");
				shareMap.put("url", getBasePath(request) + "rest/view/recruitInfo.do?recruitId="+recruitInfo.getId());
				shareMap.put("images", "");
				map.put("shareMap", shareMap);
				map.put("recruitTempId", "");
			} else {
				//map.put("success", "yes");
				//map.put("message", "无结果!");
				map = selectRecruitTempById(recruitId,memberId,map,request);
			}
		} else {
			map = selectRecruitTempById(recruitId,memberId,map,request);
		}
		addViewNum(recruitInfo, viewBool);
		return map;
	}

	private Map<String, Object> selectRecruitTempById(String recruitId,String memberId,Map<String,Object> map,HttpServletRequest request){
		Map<String, Object> mapTemp = new HashMap<String, Object>();
		mapTemp.put("recruitId", recruitId);
		RecruitInfoTemp recruitInfoTemp = tempService.selectEntity(mapTemp);
		if (recruitInfoTemp != null) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", recruitInfoTemp.getRecruitId());
//			resultMap.put("recruitId", recruitInfoTemp.getRecruitId());
			MemberInfo memberInfo = recruitInfoTemp.getMemberInfo();
			resultMap.put("name", decodeParam(memberInfo.getNickname()));
			resultMap.put("vipStatus", memberInfo.getVip());// 1-v
			resultMap.put("urlPath", getBasePath(request) + memberInfo.getPictureInfo().getUrlPath());
			resultMap.put("lableId", recruitInfoTemp.getLableCode());// 类型
			resultMap.put("title", decodeParam(recruitInfoTemp.getTitle()));// 标题
			RecruitCategory category = recruitInfoTemp.getCategory();
			resultMap.put("lableName", category.getName());// 类型
			resultMap.put("lableColor", category.getColor());// 类型颜色
			resultMap.put("cityCode", recruitInfoTemp.getCityCode());// 城市
			resultMap.put("cityName", recruitInfoTemp.getCity().getCityName());// 城市名称
			resultMap.put("type", recruitInfoTemp.getType());// 招募类型 1-官方招募
																// 2-私人招募
			resultMap.put("realNameStatus", memberInfo.getRealNameStatus() == null ? "" : memberInfo.getRealNameStatus());
			Map<String, Object> followMap = new HashMap<String, Object>();
			followMap.put("followMemberId", recruitInfoTemp.getMemberId());
			resultMap.put("follows", followService.selectCount(followMap));// 关注数
			resultMap.put("director", decodeParam(recruitInfoTemp.getDirector()));// 导演
			resultMap.put("screenwriter", decodeParam(recruitInfoTemp.getScreenwriter()));// 编剧
			resultMap.put("startDate", recruitInfoTemp.getStartDate());// 开始时间
			resultMap.put("endDate", recruitInfoTemp.getEndDate());// 结束时间
			resultMap.put("deadline", recruitInfoTemp.getDeadline());// 试戏截止时间
			resultMap.put("publishMemberId", recruitInfoTemp.getMemberId());// 发布人
			resultMap.put("scriptOutline",decodeParam(recruitInfoTemp.getScriptOutline()));// 剧本大纲
			resultMap.put("remark", decodeParam(recruitInfoTemp.getRemark()));// 备注
			followMap.put("memberId", memberId);
			resultMap.put("identity", recruitInfoTemp.getMemberInfo().getIdentityInfo());// 身份
			resultMap.put("remainTime",DateUtils.getDateSubtraction(recruitInfoTemp.getDeadlineDate(), new Date()));// 剩余天数
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("recruitId", recruitInfoTemp.getId());
			condition.put("status", "1");
			condition.put("isDelete", "0");
			condition.put("sort", "createDate");
			condition.put("order", "asc");
			List<RecruitRoleInfoTemp> list = infoTempService.selectAll(condition);
			for (RecruitRoleInfoTemp roleInfo : list) {
				Map<String, Object> resultRoleMap = new HashMap<String, Object>();
				resultRoleMap.put("id", roleInfo.getId());// 角色ID
				resultRoleMap.put("urlPath", getBasePath(request) + roleInfo.getPictureInfo().getUrlPath());// 角色头像
				resultRoleMap.put("name", decodeParam(roleInfo.getName()));// 角色名称
				resultRoleMap.put("roleId", roleInfo.getRoleId());// 正式表角色ID
				resultRoleMap.put("sex", roleInfo.getSex());// 角色性别 1 男 2 女
				resultRoleMap.put("paid",roleInfo.getPaidType().equals("面议") ? "面议": 
						roleInfo.getPaidMax() == null || roleInfo.getPaidMax().equals("") ?
						roleInfo.getPaidMin() + roleInfo.getPaidType():
						roleInfo.getPaidMin() + "-" + roleInfo.getPaidMax()
							+ roleInfo.getPaidType());
				resultRoleMap.put("paidType", roleInfo.getPaidType());
				resultRoleMap.put("paidMin", roleInfo.getPaidMin());
				resultRoleMap.put("paidMax", roleInfo.getPaidMax());
				resultRoleMap.put("requirement", decodeParam(roleInfo.getRequirement()));
				resultRoleMap.put("dialogue", decodeParam(roleInfo.getDialogue()));
				resultRoleMap.put("lableCode", decodeParam(roleInfo.getLableCode()));// 标签code
				resultRoleMap.put("lableName",getLableName(decodeParam(roleInfo.getLableCode())));// 标签名称
				resultList.add(resultRoleMap);
			}
			map.put("success", "yes");
			map.put("data", resultMap);
			map.put("dataRole", resultList);
			Map<String,Object> shareMap = new HashMap<String,Object>();
			shareMap.put("title", decodeParam(recruitInfoTemp.getTitle()));
			shareMap.put("description", "");
			shareMap.put("url", getBasePath(request) + "rest/view/recruitInfo.do?recruitId="+recruitInfoTemp.getId());
			shareMap.put("images", "");
			map.put("shareMap", shareMap);
			map.put("recruitTempId", recruitInfoTemp.getId());
		} else {
			map.put("success", "yes");
			map.put("message", "无结果!");
		}
		return map;
	}
	
	/**
	 * 保存招募信息
	 * 
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "rest/recruit/saveRecruit")
	@ResponseBody
	public Map<String, Object> saveRecruit(HttpServletRequest request,
			HttpServletResponse response, RecruitInfoTemp info) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			int result = 0;
			String msg = "";
			String recruitInfoId = UUIDUtil.getUUID();
			String recruitTempId = UUIDUtil.getUUID();
			RecruitInfoTemp recruitInfoTemp = getRecruitInfoTempByRecruitId(info.getRecruitId());
			
			info.setType("2");
			if (info.getMemberId() != null && !info.getMemberId().equals("")) {
				MemberInfo memberInfo = memberInfoService.selectById(info.getMemberId());
				if (memberInfo.getIdentityStatus() != null && memberInfo.getIdentityStatus().equals("1")) {
					info.setType("1");
				}
			}
			if (recruitInfoTemp == null || recruitInfoTemp.getId() == null || recruitInfoTemp.getId().equals("")) {
				if (StringUtils.isNotBlank(info.getId())) {
					copyInfoToTemp(info.getRecruitId(), recruitTempId);
					info.setId(recruitTempId);
					if (StringUtils.isNotBlank(info.getRecruitId())) {
						info.setCreateDate(DateUtils.currentStringDate());
						if(StringUtils.isNotBlank(info.getTitle())){
							info.setTitle(encodeParam(info.getTitle()));
						}
						if(StringUtils.isNotBlank(info.getDirector())){
							info.setDirector(encodeParam(info.getDirector()));
						}
						if(StringUtils.isNotBlank(info.getScreenwriter())){
							info.setScreenwriter(encodeParam(info.getScreenwriter()));
						}
						if(StringUtils.isNotBlank(info.getScriptOutline())){
							info.setScriptOutline(encodeParam(info.getScriptOutline()));
						}
						if(StringUtils.isNotBlank(info.getRemark())){
							info.setRemark(encodeParam(info.getRemark()));
						}						
						result = tempService.insert(info);
						copyInfoToInfo(recruitInfoId, info.getId());
					} else {						
						if(StringUtils.isNotBlank(info.getTitle())){
							info.setTitle(encodeParam(info.getTitle()));
						}
						if(StringUtils.isNotBlank(info.getDirector())){
							info.setDirector(encodeParam(info.getDirector()));
						}
						if(StringUtils.isNotBlank(info.getScreenwriter())){
							info.setScreenwriter(encodeParam(info.getScreenwriter()));
						}
						if(StringUtils.isNotBlank(info.getScriptOutline())){
							info.setScriptOutline(encodeParam(info.getScriptOutline()));
						}
						if(StringUtils.isNotBlank(info.getRemark())){
							info.setRemark(encodeParam(info.getRemark()));
						}						
						result = tempService.update(info);
					}
					msg = "保存失败！";
				} else {
					info.setId(recruitTempId);					
					if(StringUtils.isNotBlank(info.getTitle())){
						info.setTitle(encodeParam(info.getTitle()));
					}
					if(StringUtils.isNotBlank(info.getDirector())){
						info.setDirector(encodeParam(info.getDirector()));
					}
					if(StringUtils.isNotBlank(info.getScreenwriter())){
						info.setScreenwriter(encodeParam(info.getScreenwriter()));
					}									
					info.setIsDelete("0");													
					RecruitInfo recruitInfoNew = service.selectById(info.getRecruitId());
					if(recruitInfoNew == null){
						info.setStatus("0");
						info.setCreateDate(DateUtils.currentStringDate());
						result = tempService.insert(info);
						copyInfoToInfo(recruitInfoId, info.getId());						
					}else{
						//添加剧本大纲
						if(StringUtils.isNotBlank(info.getScriptOutline())){
							BeanUtils.copyProperties(recruitInfoNew, info);
							info.setScriptOutline(info.getScriptOutline());
							info.setCreateDate(DateUtils.currentStringDate());
						}
						//添加备注
						if(StringUtils.isNotBlank(info.getRemark())){
							BeanUtils.copyProperties(recruitInfoNew, info);
							info.setRemark(info.getRemark());
							info.setCreateDate(DateUtils.currentStringDate());
						}
						info.setStatus("3");
						result = tempService.insert(info);
						BeanUtils.copyProperties(info, recruitInfoNew);
						recruitInfoNew.setId(info.getRecruitId());
						recruitInfoNew.setStatus("3");
						service.update(recruitInfoNew);
					}										
					msg = "保存失败！";
				}
			} else {
				info.setId(recruitInfoTemp.getId());
				recruitTempId = recruitInfoTemp.getId();
				if(StringUtils.isNotBlank(info.getTitle())){
					info.setTitle(encodeParam(info.getTitle()));
				}
				if(StringUtils.isNotBlank(info.getDirector())){
					info.setDirector(encodeParam(info.getDirector()));
				}
				if(StringUtils.isNotBlank(info.getScreenwriter())){
					info.setScreenwriter(encodeParam(info.getScreenwriter()));
				}
				if(StringUtils.isNotBlank(info.getScriptOutline())){
					info.setScriptOutline(encodeParam(info.getScriptOutline()));
				}
				if(StringUtils.isNotBlank(info.getRemark())){
					info.setRemark(encodeParam(info.getRemark()));
				}
				info.setStatus("3");
				result = tempService.update(info);
				
				RecruitInfo recruitInfoNew = service.selectById(info.getRecruitId());
				BeanUtils.copyProperties(info, recruitInfoNew);
				recruitInfoNew.setId(info.getRecruitId());
				recruitInfoNew.setStatus("3");
				service.update(recruitInfoNew);
				
				msg = "修改失败！";
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", recruitInfoId);
			map.put("data", resultMap);
			if (result > 0) {
				map.put("recruitTempId", recruitTempId);
				map.put("success", "yes");
				map.put("message", "操作成功");
			} else {
				map.put("success", "no");
				map.put("message", msg);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取角色详细信息
	 * 
	 * @param request
	 * @param response
	 * @param roleId
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "rest/recruit/selectRecruitRoleById")
	@ResponseBody
	public Map<String, Object> selectRecruitRoleById(HttpServletRequest request, HttpServletResponse response,String roleId, String memberId, String view) {
		Map<String, Object> map = new HashMap<String, Object>();			
		RecruitRoleInfo roleInfo = roleInfoService.selectById(roleId);
		if (roleInfo != null) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", roleInfo.getId());// 角色ID
			resultMap.put("name", decodeParam(roleInfo.getName()));// 角色姓名
			resultMap.put("urlPath", getBasePath(request) + roleInfo.getPictureInfo().getUrlPath());// 角色图片地址
			resultMap.put("sex", roleInfo.getSex());// 性别
			resultMap.put("lableName", getLableName(decodeParam(roleInfo.getLableCode())));// 类型
			resultMap.put("lableCode", getLableCode(decodeParam(roleInfo.getLableCode())));// 类型code
			resultMap.put("requirement", decodeParam(roleInfo.getRequirement()));// 导演要求
			resultMap.put("dialogue", decodeParam(roleInfo.getDialogue()));// 试戏台词
			resultMap.put("recruitId", roleInfo.getRecruitId());// 招募ID
			
			if (memberId != null && !memberId.equals("")) {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("roleId", roleId);
				condition.put("memberId", memberId);
				RecruitApplyInfo applyInfo = applyInfoService.selectEntity(condition);
				if (applyInfo != null) {															
					resultMap.put("applyStatus", "1");// 申请状态
					resultMap.put("resumeId", applyInfo.getResumeId());// 简历ID
					resultMap.put("videoUrlPath", getBasePath(request) + applyInfo.getFileInfo().getUrlPath());// 视频地址
					resultMap.put("applyId", applyInfo.getId());// 视频地址
				} else {
					resultMap.put("applyStatus", "0");// 申请状态
				}
				if(view != null && view.equals("1")){
					MemberActionsInfo actionsInfo = new MemberActionsInfo();
					actionsInfo.setId(UUIDUtil.getUUID());
					actionsInfo.setCreateDate(DateUtils.currentStringDate());
					actionsInfo.setMemberId(memberId);
					actionsInfo.setRoleId(roleId);
					actionsInfoService.insert(actionsInfo);
				}
			}else{
				resultMap.put("applyStatus", "0");// 申请状态
			}
			Map<String,Object> shareMap = new HashMap<String,Object>();
			shareMap.put("title", decodeParam(roleInfo.getName()));
			shareMap.put("description", decodeParam(roleInfo.getRequirement()));
			shareMap.put("url", getBasePath(request) + "rest/view/recruitRoleInfo.do?roleId=" + roleInfo.getId());
			shareMap.put("images", "");
			
			RecruitInfo recruitInfo = roleInfo.getRecruitInfo();
			if(recruitInfo != null){
				
				//判断是否已删除
				if("1".equals(recruitInfo.getIsDelete())){
					map.put("success", "no");
					map.put("message", "该角色所在招募信息已被删除!");
					map.put("data", resultMap);
					map.put("shareMap", shareMap);
					return map;
				}
				
				//禁用
				if("5".equals(recruitInfo.getStatus())){
					map.put("success", "false");
					map.put("message", "该角色所在招募信息已暂被禁用!");
					map.put("data", resultMap);
					map.put("shareMap", shareMap);
					return map;
				}
				
				//已截止
				long ldays = DateUtils.getDateSubtraction(recruitInfo.getDeadlineDate(), new Date());				
				if(ldays < 0){
					map.put("success", "false");
					map.put("message", "该角色所在招募信息已截止!");
					map.put("data", resultMap);
					map.put("shareMap", shareMap);
					return map;
				}
			}else{
				map.put("success", "false");
				map.put("message", "该角色所在招募信息有误!");
				map.put("data", resultMap);
				map.put("shareMap", shareMap);
				return map;
			}
			
			map.put("success", "yes");
			map.put("data", resultMap);
			map.put("shareMap", shareMap);
		} else {
			map.put("success", "false");
			map.put("message", "无结果!");
		}
		return map;
	}

	/**
	 * 保存角色信息
	 * 
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "rest/recruit/saveRecruitRole")
	@ResponseBody
	public Map<String, Object> saveRecruitRole(HttpServletRequest request,
			HttpServletResponse response, RecruitRoleInfoTemp info) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			int result = 0;
			String msg = "";			
			String recruitTempId = UUIDUtil.getUUID();
			// 获取招募临时表
			RecruitInfoTemp recruitInfoTemp = getRecruitInfoTempByRecruitId(info.getRecruitId());
			RecruitRoleInfoTemp roleInfoTemp = null;
			if (recruitInfoTemp == null || recruitInfoTemp.getId() == null || recruitInfoTemp.getId().equals("")) {
				copyInfoToTemp(info.getRecruitId(), recruitTempId);
				roleInfoTemp = getRoleInfoTempByRoleId(info.getRoleId());
			} else {
				recruitTempId = recruitInfoTemp.getId();
				roleInfoTemp = infoTempService.selectById(info.getRoleId());
			}
			// 招募临时表有数据 新建或者修改角色信息
//			RecruitRoleInfoTemp roleInfoTemp = getRoleInfoTempByRoleId(info
//					.getRoleId());
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String root = request.getSession().getServletContext().getRealPath("/");
			// 上传图片
			String meetingImgUuid = UUIDUtil.getUUID();
			MultipartFile headImg = multipartRequest.getFile("sysImg");
			if (headImg != null && headImg.getSize() > 0) {
				// 删除图片
				deleteImg(info.getId(), root);
				// 新图片处理
				saveImg(root, meetingImgUuid, headImg);
				info.setImgUuid(meetingImgUuid);
			}
			if (roleInfoTemp == null || roleInfoTemp.getId() == null) {
				info.setId(UUIDUtil.getUUID());
				info.setRecruitId(recruitTempId);
				info.setStatus("1");
				info.setIsDelete("0");
				info.setCreateDate(DateUtils.currentStringDate());
				if(StringUtils.isNotBlank(info.getName())){
					info.setName(encodeParam(info.getName()));					
				}				
				if(StringUtils.isNotBlank(info.getRequirement())){
					info.setRequirement(encodeParam(info.getRequirement()));
				}
				if(StringUtils.isNotBlank(info.getDialogue())){
					info.setDialogue(encodeParam(info.getDialogue()));
				}
				if(StringUtils.isNotBlank(info.getLableCode())){
					info.setLableCode(encodeParam(info.getLableCode()));
				}
				result = infoTempService.insert(info);
				msg = "保存失败！";
			} else {
				info.setId(roleInfoTemp.getId());
				info.setRecruitId(recruitTempId);
				info.setRoleId(null);
				if(StringUtils.isNotBlank(info.getName())){
					info.setName(encodeParam(info.getName()));
				}				
				if(StringUtils.isNotBlank(info.getRequirement())){
					info.setRequirement(encodeParam(info.getRequirement()));
				}
				if(StringUtils.isNotBlank(info.getDialogue())){
					info.setDialogue(encodeParam(info.getDialogue()));
				}
				if(StringUtils.isNotBlank(info.getLableCode())){
					info.setLableCode(encodeParam(info.getLableCode()));
				}
				result = infoTempService.update(info);
				msg = "修改失败！";
			}
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("id", info.getId());
			if (result > 0) {
				map.put("recruitTempId", recruitTempId);
				map.put("success", "yes");
				map.put("message", "操作成功");
			} else {
				map.put("success", "no");
				map.put("message", msg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return map;
	}

	/**
	 * 获取角色头像列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "rest/recruit/recruitRolePic")
	@ResponseBody
	public Map<String, Object> recruitRolePic(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("status", "1");
		condition.put("sort", "orderList");
		condition.put("order", "asc");
		List<SystemRoleType> list = typeService.selectAll(condition);
		if (list != null && !list.isEmpty()) {
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (SystemRoleType type : list) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("typeId", type.getId());// 类别ID
				resultMap.put("typeName", type.getTypeName());
				Map<String, Object> conditionPic = new HashMap<String, Object>();
				conditionPic.put("status", "1");
				conditionPic.put("sort", "orderList");
				conditionPic.put("order", "asc");
				conditionPic.put("typeId", type.getId());
				List<SystemRolePic> picList = picService.selectAll(conditionPic);
				List<Map<String, Object>> resultPicList = new ArrayList<Map<String, Object>>();
				for (SystemRolePic pic : picList) {
					Map<String, Object> resultRoleMap = new HashMap<String, Object>();
					resultRoleMap.put("picId", pic.getImgUuid());
					resultRoleMap.put("title", pic.getTitle());
					resultRoleMap.put("urlPath", getBasePath(request) + pic.getPictureInfo().getUrlPath());
					resultPicList.add(resultRoleMap);
				}
				resultMap.put("info", resultPicList);
				resultList.add(resultMap);
			}
			map.put("success", "yes");
			map.put("data", resultList);
		} else {
			map.put("success", "no");
			map.put("message", "无结果!");
		}
		return map;
	}

	/**
	 * 关注与取消关注
	 * 
	 * @param request
	 * @param response
	 * @param memberId
	 * @param followId
	 * @param followStatus
	 * @return
	 */
	@RequestMapping(value = "rest/recruit/followChange")
	@ResponseBody
	public Map<String, Object> followChange(HttpServletRequest request,
			HttpServletResponse response, String memberId, String followId,
			boolean followStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> condition = new HashMap<String, Object>();
		int result = 0;
		String msg = "";
		condition.put("memberId", memberId);
		condition.put("followMemberId", followId);
		int count = followService.selectCount(condition);
		if (followStatus) {// 关注
			if (count == 0) {
				MemberFollowInfo info = new MemberFollowInfo();
				info.setId(UUIDUtil.getUUID());
				info.setMemberId(memberId);
				info.setFollowMemberId(followId);
				info.setCreateDate(DateUtils.currentStringDate());
				result = followService.insert(info);
				msg = "关注失败";
			} else {
				result = 1;
			}
		} else {
			if (count > 0) {
				result = followService.delete(condition);
				msg = "取消关注失败";
			}
			result = 1;
		}
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
	 * 获取举报类别
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "rest/recruit/recruitInformantTypeList")
	@ResponseBody
	public Map<String, Object> recruitInformantTypeList(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("status", "1");
		condition.put("sort", "orderList");
		condition.put("order", "asc");
		List<RecruitInformantType> list = informantTypeService.selectAll(condition);
		if (list != null && !list.isEmpty()) {
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (RecruitInformantType type : list) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("id", type.getId());
				resultMap.put("name", type.getTitle());
				resultList.add(resultMap);
			}
			map.put("success", "yes");
			map.put("data", resultList);
		} else {
			map.put("success", "yes");
			map.put("message", "无结果!");
		}
		return map;
	}

	/**
	 * 关注与取消关注
	 * 
	 * @param request
	 * @param response
	 * @param memberId
	 * @param followId
	 * @param followStatus
	 * @return
	 */
	@RequestMapping(value = "rest/recruit/recruitInformantSave")
	@ResponseBody
	public Map<String, Object> recruitInformantSave(HttpServletRequest request,
			HttpServletResponse response, RecruitInformantInfo info) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		if (info.getInformantsId() == null || info.getInformantsId().equals("")) {
			map.put("success", "no");
			map.put("message", "举报人为空");
		} else if (info.getRecruitId() == null
				|| info.getRecruitId().equals("")) {
			map.put("success", "no");
			map.put("message", "剧目为空");
		} else {
			info.setId(UUIDUtil.getUUID());
			info.setStatus("1");
			info.setCreateDate(DateUtils.currentStringDate());
			result = infoService.insert(info);
			if (result > 0) {
				map.put("success", "yes");
				map.put("message", "操作成功");
			} else {
				map.put("success", "no");
				map.put("message", "操作失败");
			}
		}
		return map;
	}

	/**
	 * 招募发布
	 * 
	 * @param request
	 * @param response
	 * @param recruitTempId
	 * @return
	 */
	@RequestMapping(value = "rest/recruit/recruitPulish")
	@ResponseBody
	public Map<String, Object> recruitPulish(HttpServletRequest request,
			HttpServletResponse response, String recruitTempId) {
		Map<String, Object> map = new HashMap<String, Object>();
		RecruitInfoTemp recruitInfoTemp = tempService.selectById(recruitTempId);
		if (recruitInfoTemp != null) {
			if (recruitInfoTemp.getRecruitId() == null || recruitInfoTemp.getRecruitId().equals("")) {
				RecruitInfo recruitInfo = new RecruitInfo();
				BeanUtils.copyProperties(recruitInfoTemp, recruitInfo);
				String id = UUIDUtil.getUUID();
				recruitInfo.setId(id);
				recruitInfo.setStatus("2");// 新建审核中
				recruitInfoTemp.setRecruitId(id);
				recruitInfoTemp.setStatus("1");
				recruitInfoTemp.setIsDelete("0");
				tempService.update(recruitInfoTemp);
				service.insert(recruitInfo);
				Map<String, Object> roleMap = new HashMap<String, Object>();
				roleMap.put("recruitId", recruitInfoTemp.getId());
				roleMap.put("isDelete", "0");
				List<RecruitRoleInfoTemp> roleInfos = infoTempService.selectAll();
				for (RecruitRoleInfoTemp roleInfoTemp : roleInfos) {
					RecruitRoleInfo roleInfo = new RecruitRoleInfo();
					BeanUtils.copyProperties(roleInfoTemp, roleInfo);
					String roleId = UUIDUtil.getUUID();
					roleInfo.setId(roleId);
					roleInfo.setRecruitId(id);
					roleInfoService.insert(roleInfo);

					roleInfoTemp.setRoleId(roleId);
					infoTempService.update(roleInfoTemp);
				}
			} else {
				Map<String, Object> updateMap = new HashMap<String, Object>();
				updateMap.put("id", recruitInfoTemp.getRecruitId());
				updateMap.put("status", "3");
				service.update(updateMap);
			}
		}
		map.put("success", "yes");
		map.put("message", "操作成功");
		return map;
	}
	
	
	@RequestMapping(value = "rest/recruit/publishRecruit")
	@ResponseBody
	public Map<String, Object> publishRecruit(HttpServletRequest request,
			HttpServletResponse response, String recruitTempId){
		Map<String, Object> map = new HashMap<String, Object>();
		
		RecruitInfoTemp recruitInfoTemp = tempService.selectById(recruitTempId);
		if(recruitInfoTemp == null){
			map.put("success", "no");
			map.put("message", "招募信息错误!");
			return map;
		}
		recruitInfoTemp.setStatus("0");						
		int result = tempService.update(recruitInfoTemp);
		
		String recruitId = recruitInfoTemp.getRecruitId();
		if(StringUtils.isBlank(recruitId)){
			map.put("success", "no");
			map.put("message", "招募信息错误!");
			return map;
		}
		RecruitInfo recruitInfo = service.selectById(recruitId);
		if(recruitInfo != null){
			recruitInfo.setStatus("0");
			result = service.update(recruitInfo);
		}
		
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
	 * 获取标签名称
	 * 
	 * @param roleInfo
	 */
	private String getLableName(String lable) {
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
	 * 获取标签名称
	 * 
	 * @param roleInfo
	 */
	private String getLableCode(String lable) {
		if (lable != null && lable.length() > 0) {
			String first = lable.substring(0, 1);
			if (first.equals(",")) {
				return lable.substring(1, lable.length());
			}
		}
		return lable;
	}

	/**
	 * 保存图片
	 * 
	 * @param root
	 * @param meetingImgUuid
	 * @param listfile
	 * @return
	 */
	private int saveImg(String root, String meetingImgUuid,
			MultipartFile listfile) {
		String pathTmp = Constant.UPLOAD_RECRUIT_PATH + "/";
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
		pictureInfo.setCdate(DateUtils.toString(new Date(),"yyyy-MM-dd HH:mm:ss"));
		systemPictureInfoService.insert(pictureInfo);
		return 0;
	}

	/**
	 * 添加图片
	 * 
	 * @param id
	 * @param root
	 * @return
	 */
	private int deleteImg(String id, String root) {
		if (id != null && !id.equals("")) {
			// 删除旧图片
			RecruitRoleInfo info = roleInfoService.selectById(id);
			if (info.getPictureInfo() != null) {
				String delRealPath = info.getPictureInfo().getUrlPath();
				if (delRealPath != null) {
					deleteFile(root + delRealPath);
					SystemPictureInfo delp = new SystemPictureInfo();
					delp.setUuid(info.getImgUuid());
					systemPictureInfoService.delete(delp);
				}
			}
		}
		return 0;
	}

	/**
	 * 更新招募浏览次数
	 * 
	 * @param id
	 * @param viewBool
	 */
	private synchronized void addViewNum(RecruitInfo recruitInfo,
			boolean viewBool) {
		if (viewBool && recruitInfo != null) {
			service.addViewNum(recruitInfo);
		}
	}

	/**
	 * 根据招募ID 获取招募申请信息
	 * 
	 * @param recruitId
	 * @return
	 */
	private RecruitInfoTemp getRecruitInfoTempByRecruitId(String recruitId) {
		RecruitInfoTemp infoTemp = new RecruitInfoTemp();
		if (recruitId != null && !recruitId.equals("")) {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("recruitId", recruitId);
			infoTemp = tempService.selectEntity(condition);
		}
		return infoTemp;
	}

	/**
	 * 根据招募角色ID 获取招募角色申请信息
	 * 
	 * @param roleId
	 * @return
	 */
	private RecruitRoleInfoTemp getRoleInfoTempByRoleId(String roleId) {
		RecruitRoleInfoTemp infoTemp = new RecruitRoleInfoTemp();
		if (roleId != null && !roleId.equals("")) {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("roleId", roleId);
			infoTemp = infoTempService.selectEntity(condition);
		}
		return infoTemp;
	}

	/**
	 * 复制生成临时表数据
	 * 
	 * @param recruitInfoId
	 * @param recruitTempInfoId
	 */
	private void copyInfoToTemp(String recruitInfoId, String recruitTempInfoId) {
		RecruitInfo recruitInfo = service.selectById(recruitInfoId);
		if (recruitInfo != null) {
			RecruitInfoTemp recruitInfoTemp = new RecruitInfoTemp();
			BeanUtils.copyProperties(recruitInfo, recruitInfoTemp);
			recruitInfoTemp.setRecruitId(recruitInfo.getId());
			recruitInfoTemp.setId(recruitTempInfoId);
			recruitInfoTemp.setStatus("0");
			recruitInfoTemp.setIsDelete("0");
			recruitInfoTemp.setCreateDate(DateUtils.currentStringDate());
			tempService.insert(recruitInfoTemp);
			Map<String, Object> roleMap = new HashMap<String, Object>();
			roleMap.put("recruitId", recruitInfo.getId());
			roleMap.put("isDelete", "0");
			List<RecruitRoleInfo> roleInfos = roleInfoService
					.selectAll(roleMap);
			for (RecruitRoleInfo roleInfo : roleInfos) {
				RecruitRoleInfoTemp roleInfoTemp = new RecruitRoleInfoTemp();
				roleInfo.setRecruitInfo(null);
				BeanUtils.copyProperties(roleInfo, roleInfoTemp);
				roleInfoTemp.setId(UUIDUtil.getUUID());
				roleInfoTemp.setRoleId(roleInfo.getId());
				roleInfoTemp.setRecruitId(recruitTempInfoId);
				infoTempService.insert(roleInfoTemp);
			}
			recruitInfo.setModify("1");// 处于修改状态
			service.update(recruitInfo);
		}
	}

	private void copyInfoToInfo(String recruitInfoId, String tempId) {
		RecruitInfoTemp temp = tempService.selectById(tempId);
		if (temp != null) {
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put("recruitId", temp.getId());
			RecruitInfo recruitInfoNew = new RecruitInfo();
			BeanUtils.copyProperties(temp, recruitInfoNew);
			recruitInfoNew.setId(recruitInfoId);
			recruitInfoNew.setStatus("4");
			service.insert(recruitInfoNew);

			temp.setRecruitId(recruitInfoId);
			tempService.update(temp);
		}
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
}
