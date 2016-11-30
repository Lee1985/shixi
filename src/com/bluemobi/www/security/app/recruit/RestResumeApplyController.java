package com.bluemobi.www.security.app.recruit;

import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.bluemobi.www.constant.ResumeStatusEnum;
import com.bluemobi.www.data.model.member.MemberResumeTemplate;
import com.bluemobi.www.data.model.recruit.RecruitApplyInfo;
import com.bluemobi.www.data.model.system.FileInfo;
import com.bluemobi.www.data.model.system.SystemFileInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberResumeApplyPhotosService;
import com.bluemobi.www.security.member.service.MemberResumeApplyService;
import com.bluemobi.www.security.member.service.MemberResumeTemplatePhotosService;
import com.bluemobi.www.security.member.service.MemberResumeTemplateService;
import com.bluemobi.www.security.recruit.service.RecruitApplyInfoService;
import com.bluemobi.www.security.system.service.SystemFileInfoService;
import com.bluemobi.www.utils.CustomPropertiesUtils;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.FileTool;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
 * @author sundq
 * 简历投递接口(简历表数据的相互传递)
 */
@Controller
public class RestResumeApplyController extends BaseController{
	
	@Resource
	private RecruitApplyInfoService service;
	
	@Resource
	private MemberResumeApplyService applyService;
	
	@Resource
	private MemberResumeTemplateService templateService;
	
	@Resource
	private MemberResumeApplyPhotosService applyPhotosService;
	
	@Resource
	private MemberResumeTemplatePhotosService templatePhotosService;
	
	@Resource
	private SystemFileInfoService systemFileInfoService;
	
	/**
	 * 角色页面,简历和视频提交
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "rest/recruit/saveResumeApply")
	@ResponseBody
	public Map<String,Object> saveRecruitRole(HttpServletRequest request,HttpServletResponse response,RecruitApplyInfo info){
		Map<String,Object> map = new HashMap<String,Object>();
		int result = 0;
		String msg = "";
		RecruitApplyInfo applyInfo = null;
		if(StringUtils.isNotBlank(info.getId())){
			applyInfo = service.selectById(info.getId());
		}else{
			Map<String, Object> mapc = new HashMap<String , Object>();
			mapc.put("roleId", info.getRoleId());
			mapc.put("memberId", info.getMemberId());
			applyInfo = service.selectEntity(mapc);
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String root = request.getSession().getServletContext().getRealPath("/");
		// 上传视频
//		String videoUuid = UUIDUtil.getUUID();
		String uuid = "";
		MultipartFile sysVideo = multipartRequest.getFile("sysVideo");
		if (sysVideo != null && sysVideo.getSize() > 0) {
			uuid = UUIDUtil.getUUID();
			// 删除旧视频
			deleteFile(applyInfo != null ? applyInfo.getVideoUuid():"", root);
			// 新文件处理
			saveFile(root, uuid, sysVideo);
			info.setVideoUuid(uuid);
		}
		//保存申请的简历信息
		MemberResumeTemplate template = templateService.selectEntityByMemberId(info.getMemberId());
		if(template == null){
			map.put("success", "yes");
			map.put("message", "您还没有制作您的简历,请制作您的简历再进行此操作!");
			return map;
		}
		double percent = generateResumePercent(template);
		if(template.getStatus().equals(ResumeStatusEnum.UNCOMMITED.getCode())){
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMaximumFractionDigits(2);
			String percentStr = nf.format(percent);
			map.put("success", "yes");
			map.put("message", "您目前的简历完善度不够(目前完善度" + percentStr + "),无法生成简历!");
			map.put("status", "0");
			return map;
		}		
		if(StringUtils.isBlank(uuid)){
			map.put("success", "yes");
			map.put("message", "网络状态不佳,视频上传失败,请重试!");
			return map;
		}		
		info.setStatus("0");
		String date = DateUtils.currentStringDate();
		info.setCreateDate(date);
		info.setVideoUuid(uuid);
		info.setResumeId(template.getId());
		info.setVideoStatus("0");
		info.setLevel("1");
		info.setUpdateDate(date);
		info.setCompletion(percent*100);
		if(applyInfo != null && StringUtils.isNotBlank(applyInfo.getId())){
			info.setId(applyInfo.getId());
			result = service.update(info);
		}else{
			info.setId(UUIDUtil.getUUID());
			result = service.insert(info);
		}
		msg = "保存失败！";
		SystemFileInfo fileInfo = systemFileInfoService.selectByUuid(uuid);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("id", info.getId());
		resultMap.put("resumeId", template.getId());
		resultMap.put("videoPath", getBasePath(request) + fileInfo.getUrlPath());
		if (result > 0) {
			resultMap.put("applyStatus", "1");// 申请状态
			map.put("success", "yes");
			map.put("message", "操作成功");
			map.put("data", resultMap);
		} else {
			map.put("success", "no");
			map.put("message", msg);
		}
		return map;
	}
			
	/**
	 * 删除文件
	 * 
	 * @param id
	 * @param root
	 * @return
	 */
	private int deleteFile(String uuid, String root) {
		if (uuid != null && !uuid.equals("")) {
			// 删除旧文件
			SystemFileInfo videoInfo = systemFileInfoService.selectById(uuid);
			if (videoInfo != null) {
				String delRealPath = videoInfo.getUrlPath();
				if (delRealPath != null) {
					deleteFile(root + delRealPath);
					SystemFileInfo delp = new SystemFileInfo();
					delp.setUuid(uuid);
					systemFileInfoService.delete(delp);
				}
			}
		}
		return 0;
	}
	
	/**
	 * 保存文件
	 * 
	 * @param root
	 * @param uuid
	 * @param listfile
	 * @return
	 */
	private FileInfo saveFile(String root, String uuid, MultipartFile listfile) {
		String path = Constant.UPLOAD_PLAY_VIDEO_PATH + DateUtils.toString(new Date(), "/yyyy/MM/dd") + "/";
		String realPath = root + path;
		FileInfo imageInfo = FileTool.saveFile(listfile, realPath);
		String urlPath = path + imageInfo.getRealName();
		SystemFileInfo systemFileInfo = new SystemFileInfo();
		systemFileInfo.setId(UUIDUtil.getUUID());
		systemFileInfo.setUuid(uuid);
		systemFileInfo.setUrlPath(urlPath);
		systemFileInfo.setFsize(imageInfo.getFsize());
		systemFileInfo.setName(imageInfo.getRealName());
		systemFileInfo.setSuffix(imageInfo.getSuffix());
		systemFileInfo.setCdate(DateUtils.toString(new Date(),"yyyy-MM-dd HH:mm:ss"));
		systemFileInfoService.insert(systemFileInfo);
		return imageInfo;
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
		int photoCount = templatePhotosService.selectCount(params);
		numerator += photoCount;
		int resumeBaseNumber = Integer.parseInt(CustomPropertiesUtils.getValue("resume_base_number"));
		return (double)numerator / (double)resumeBaseNumber;
	}
}
