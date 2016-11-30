package com.bluemobi.www.security.recruit.controller;


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
import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.recruit.RecruitInfo;
import com.bluemobi.www.data.model.recruit.RecruitInfoTemp;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfo;
import com.bluemobi.www.data.model.recruit.RecruitRoleInfoTemp;
import com.bluemobi.www.data.model.system.FileInfo;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.data.model.system.SystemRolePic;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.recruit.service.RecruitInfoService;
import com.bluemobi.www.security.recruit.service.RecruitInfoTempService;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoService;
import com.bluemobi.www.security.recruit.service.RecruitRoleInfoTempService;
import com.bluemobi.www.security.system.service.SystemLableInfoService;
import com.bluemobi.www.security.system.service.SystemPictureInfoService;
import com.bluemobi.www.security.system.service.SystemRolePicService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.FileTool;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: RecruitRoleInfoController 
* @Description: 招募角色信息控制层 
* @author sundq 
* @date 2016-02-16 16:08:15 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class RecruitRoleInfoController extends BaseController {
	
	@Resource
	private RecruitRoleInfoService service;
	
	@Resource
	private RecruitRoleInfoTempService tempRoleService;
	
	@Resource
	private RecruitInfoService infoService;
	
	@Resource
	private RecruitInfoTempService infoTempService;
	
	@Resource
	private SystemPictureInfoService systemPictureInfoService;
	
	@Resource
	private SystemLableInfoService lableInfoService;
	
	@Resource
	private SystemRolePicService picService;
	
	@RequestMapping(value = "recruit/recruitRoleInfoList")
	public String recruitRoleInfoList(HttpServletRequest request,
			HttpServletResponse response, String recruitId, String type , String backUrl) {
		RecruitInfo entity = infoService.selectById(recruitId);
		if(entity != null){
			entity.setTitle(decodeParam(entity.getTitle()));
			MemberInfo memberInfo = entity.getMemberInfo();
			memberInfo.setNickname(decodeParam(memberInfo.getNickname()));
			entity.setMemberInfo(memberInfo);
			entity.setDirector(decodeParam(entity.getDirector()));
			entity.setScreenwriter(decodeParam(entity.getScreenwriter()));
			entity.setScriptOutline(decodeParam(entity.getScriptOutline()));
			entity.setRemark(decodeParam(entity.getRemark()));
			request.setAttribute("recruitId", recruitId);
			request.setAttribute("entity", entity);
			request.setAttribute("backUrl", backUrl);
			request.setAttribute("type", type);
		}								
		if(type!=null && type.equals("1")){
			return "recruit/recruit_role_info_list_view";
		}
		if(type!=null && type.equals("2")){
			return "recruit/recruit_role_info_list_view";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "1");
		map.put("order", "orderList");
		map.put("sort", "asc");
		List<SystemRolePic> listPic = picService.selectAll();
		request.setAttribute("listPic", listPic);
		return "recruit/recruit_role_info_list_edit";		
	}

	@RequestMapping(value = "recruit/recruitRoleInfoAjaxPage")
	@ResponseBody
	public PageInfo<RecruitRoleInfo> recruitRoleInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, RecruitRoleInfo info, Integer page,
			Integer rows) {
		
		PageInfo<RecruitRoleInfo> pageInfo = new PageInfo<RecruitRoleInfo>();
		if(StringUtils.isNotBlank(info.getRecruitId())){
			pageInfo.setPage(page);
			pageInfo.setPageSize(rows);
			info.setIsDelete("0");
			info.setStatus("1");			
			service.selectAll(info, pageInfo);
			List<RecruitRoleInfo> roleList = pageInfo.getRows();
			if(roleList != null){
				for(RecruitRoleInfo recruitRoleInfo : roleList){
					recruitRoleInfo.setName(decodeParam(recruitRoleInfo.getName()));
					recruitRoleInfo.setDialogue(decodeParam(recruitRoleInfo.getDialogue()));
					recruitRoleInfo.setLableCode(decodeParam(recruitRoleInfo.getLableCode()));
					recruitRoleInfo.setName(decodeParam(recruitRoleInfo.getName()));
					recruitRoleInfo.setRequirement(decodeParam(recruitRoleInfo.getRequirement()));
				}
			}
		}				
		return pageInfo;
	}
	
	@RequestMapping(value = "recruit/recruitRoleInfoAjaxAll")
	@ResponseBody
	public List<RecruitRoleInfo> recruitRoleInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, RecruitRoleInfo info, Integer page,
			Integer rows) {
		List<RecruitRoleInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "recruit/recruitRoleInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> recruitRoleInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, RecruitRoleInfo info){
		int result = 0;
		String msg = "";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String root = request.getSession().getServletContext().getRealPath("/");
						
		try{
			// 上传图片
			String meetingImgUuid = UUIDUtil.getUUID();
			MultipartFile headImg = multipartRequest.getFile("sysImg");		
			if (headImg != null && headImg.getSize() > 0) {
				// 新图片处理
				saveImg(root, meetingImgUuid, headImg);
				info.setImgUuid(meetingImgUuid);
			}								
			if (info.getId() == null || info.getId().equals("")) {									
				info.setId(UUIDUtil.getUUID());
				info.setStatus("1");
				info.setIsDelete("0");
				info.setCreateDate(DateUtils.currentStringDate());
				result = service.insert(info);
				
				String recruitId = info.getRecruitId();
				Map<String,Object> rtParams = new HashMap<String,Object>();
				rtParams.put("recruitId", recruitId);
				RecruitInfoTemp infoTemp = infoTempService.selectEntity(rtParams);
							
				RecruitRoleInfoTemp roleTemp = new RecruitRoleInfoTemp();				
				BeanUtils.copyProperties(info, roleTemp);
				roleTemp.setId(UUIDUtil.getUUID());
				roleTemp.setRecruitId(infoTemp.getId());
				result = tempRoleService.insert(roleTemp);
				
				msg = "保存失败！";
			} else {
				result = service.update(info);
				
				Map<String,Object> roleParams = new HashMap<String,Object>();
				roleParams.put("recruitId", info.getRecruitId());
				List<RecruitRoleInfo> roleInfoList = service.selectAll(roleParams);			
				
				String recruitId = info.getRecruitId();
				Map<String,Object> rtParams = new HashMap<String,Object>(roleParams);
				rtParams.put("recruitId", recruitId);
				RecruitInfoTemp infoTemp = infoTempService.selectEntity(rtParams);
				
				Map<String, Object> infoMap = new HashMap<String, Object>();
				infoMap.put("recruitId", infoTemp.getId());
				List<RecruitRoleInfoTemp> listRole =  tempRoleService.selectAll(infoMap);			
				if(listRole != null && !listRole.isEmpty()){								
					for(RecruitRoleInfoTemp roleInfo : listRole){
						tempRoleService.delete(roleInfo);
					}							
				}
				
				for (RecruitRoleInfo roleInfo : roleInfoList) {
					RecruitRoleInfoTemp roleInfoTempNew = new RecruitRoleInfoTemp();
					BeanUtils.copyProperties(roleInfo, roleInfoTempNew);								
					roleInfoTempNew.setRecruitId(infoTemp.getId());
					roleInfoTempNew.setId(UUIDUtil.getUUID());
					roleInfoTempNew.setRoleId(roleInfo.getId());
					tempRoleService.insert(roleInfoTempNew);								
				}
				
				msg = "修改失败！";
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "recruit/recruitRoleInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> recruitRoleInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, RecruitRoleInfo info) {
		int result = 0;
		try {
			info.setStatus("0");
			info.setIsDelete("1");
			result = service.update(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return getJsonResult(result,"操作成功", "删除失败！");
	}
	
	@RequestMapping(value = "recruit/recruitRoleInfoById")
	public String recruitRoleInfoById(HttpServletRequest request,
			HttpServletResponse response, RecruitRoleInfo info, String type, String backUrl) {
		try {
			info = service.selectById(info.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("entity", info);
		request.setAttribute("type", type);
		request.setAttribute("backUrl", backUrl);
		return "recruit/recruit_role_info_detail";
	}
	
	private int saveImg(String root, String meetingImgUuid,MultipartFile listfile) {
		String pathTmp = Constant.UPLOAD_RECRUIT_PATH + "/";
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
		return 0;
	}
}