package com.bluemobi.www.security.member.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.member.MemberResumeInfo;
import com.bluemobi.www.data.model.member.MemberResumeInfoPhotos;
import com.bluemobi.www.data.model.member.MemberResumeTemplate;
import com.bluemobi.www.data.model.member.MemberResumeTemplatePhotos;
import com.bluemobi.www.data.model.system.SystemHotspotCity;
import com.bluemobi.www.data.model.system.SystemLableInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberResumeInfoPhotosService;
import com.bluemobi.www.security.member.service.MemberResumeInfoService;
import com.bluemobi.www.security.member.service.MemberResumeTemplatePhotosService;
import com.bluemobi.www.security.member.service.MemberResumeTemplateService;
import com.bluemobi.www.security.system.service.SystemHotspotCityService;
import com.bluemobi.www.security.system.service.SystemLableInfoService;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: MemberResumeInfoController 
* @Description: 简历管理控制层 
* @author sundq 
* @date 2016-02-18 14:18:03 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class MemberResumeInfoController extends BaseController {
	@Resource
	private MemberResumeInfoService service;
	@Resource
	private MemberResumeInfoPhotosService photosService;
	@Resource
	private SystemLableInfoService lableInfoService;
	@Resource
	private SystemHotspotCityService cityService;
	@Resource
	private MemberResumeTemplateService memberResumeTemplateService;
	@Resource
	private MemberResumeTemplatePhotosService templatePhotosService;

	@RequestMapping(value = "member/memberResumeInfoList")
	public String memberResumeInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		return "member/member_resume_info_list";
	}

	@RequestMapping(value = "member/memberResumeInfoAjaxPage")
	@ResponseBody
	public PageInfo<MemberResumeInfo> memberResumeInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, MemberResumeInfo info, Integer page,
			Integer rows) {
		PageInfo<MemberResumeInfo> pageInfo = new PageInfo<MemberResumeInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		if(info.getSort().equals("id")){
			info.setSort("updateDate");
			info.setOrder("desc");
		}
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "member/memberResumeInfoAjaxAll")
	@ResponseBody
	public List<MemberResumeInfo> memberResumeInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, MemberResumeInfo info, Integer page,
			Integer rows) {
		List<MemberResumeInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "member/memberResumeInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> memberResumeInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, MemberResumeInfo info) {
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

	@RequestMapping(value = "member/memberResumeInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> memberResumeInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, MemberResumeInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
	
	
	@RequestMapping(value = "member/memberResumeInfoByMemberId")
	public String memberResumeInfoByMemberId(HttpServletRequest request,
			HttpServletResponse response,Model model, String memberId) {
		MemberResumeTemplate info = new MemberResumeTemplate();
		List<MemberResumeTemplatePhotos> photosList = new ArrayList<MemberResumeTemplatePhotos>();
		if(memberId != null && !memberId.equals("")){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("memberId", memberId);
			info = memberResumeTemplateService.selectEntity(params);
			Map<String, Object> map = new HashMap<String, Object>();
			if (info != null && info.getId() != null) {
				map.put("resumeId", info.getId());
				photosList = templatePhotosService.selectAll(map);				
				getRoleLableNameFromTemp(info);//赋值角色名称
				getSkillLableNameFromTemp(info);//赋值技能名称
				getCityNameFromTemp(info);		
				info.setRealName(decodeParam(info.getRealName()));
				info.setSchool(decodeParam(info.getSchool()));		
				model.addAttribute("entity", info);
				if(photosList != null){
					for(MemberResumeTemplatePhotos infoPhotos : photosList){
						infoPhotos.setTitle(decodeParam(infoPhotos.getTitle()));
					}
				}
				
				model.addAttribute("photosList", photosList);
			}
		}				
		return "member/member_resume_apply_detail";
	}
	
	@RequestMapping(value = "member/memberResumeInfoById")
	public String memberResumeInfoById(HttpServletRequest request,
			HttpServletResponse response,Model model, String id) {
		MemberResumeInfo info = new MemberResumeInfo();
		List<MemberResumeInfoPhotos> photosList = new ArrayList<MemberResumeInfoPhotos>();
		if(id != null && !id.equals("")){
			info = service.selectById(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resumeId", id);
			photosList = photosService.selectAll(map);
		}
		getRoleLableName(info);//赋值角色名称
		getSkillLableName(info);//赋值技能名称
		getCityName(info);
		model.addAttribute("entity", info);
		model.addAttribute("photosList", photosList);
		return "member/member_resume_apply_detail";
	}
	
	
	/**
	 * 赋值角色
	 * @param applyInfo
	 */
	private void getRoleLableName(MemberResumeInfo applyInfo){
		String lableName = "";
		String lable = decodeParam(applyInfo.getRoleLabel());
		if(lable != null){
			String[] lables = lable.split(",");
			for (int j = 0; j < lables.length; j++) {
				if (lables[j]!=null && !lables[j].equals("")) {
					SystemLableInfo info = lableInfoService
							.selectById(lables[j]);
					if (info!=null && info.getLableName()!=null) {
						lableName = lableName + info.getLableName()
								+ ",";
					}
				}
			}
			applyInfo.setRoleLabelName(lableName.substring(0, lableName.length()-1));
		}
	}
	
	/**
	 * 赋值角色
	 * @param applyInfo
	 */
	private void getRoleLableNameFromTemp(MemberResumeTemplate applyInfo){
		String lableName = "";
		String lable = decodeParam(applyInfo.getRoleLabel());
		if (lable != null) {
			String[] lables = lable.split(",");
			for(String name : lables){
				if(StringUtils.isNotBlank(name)){
					lableName += name + ",";
				}				
			}
		}		
		applyInfo.setRoleLabel(lableName.substring(0, lableName.length() - 1));
	}
	
	/**
	 * 赋值技能
	 * @param applyInfo
	 */
	private void getSkillLableName(MemberResumeInfo applyInfo){
		String lableName = "";
		String lable = decodeParam(applyInfo.getSkillLabel());
		if(lable != null){
			String[] lables = lable.split(",");
			for (int j = 0; j < lables.length; j++) {
				if (lables[j]!=null && !lables[j].equals("")) {
					SystemLableInfo info = lableInfoService
							.selectById(lables[j]);
					if (info!=null && info.getLableName()!=null) {
						lableName = lableName + info.getLableName()
								+ ",";
					}
				}
			}
			applyInfo.setSkillLabelName(lableName.substring(0, lableName.length()-1));
		}
	}
	
	/**
	 * 赋值技能
	 * @param applyInfo
	 */
	private void getSkillLableNameFromTemp(MemberResumeTemplate applyInfo){
		String lableName = "";
		String lable = decodeParam(applyInfo.getSkillLabel());
		if (lable != null) {
			String[] lables = lable.split(",");
			for(String name : lables){
				if(StringUtils.isNotBlank(name)){
					lableName += name + ",";
				}				
			}
		}		
		applyInfo.setSkillLabel(lableName.substring(0, lableName.length() - 1));
	}
	
	/**
	 * 常驻城市
	 * @param applyInfo
	 */
	private void getCityName(MemberResumeInfo applyInfo){
		String codeName = "";
		String code = applyInfo.getCityCode();
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
			applyInfo.setCityName(codeName.substring(0, codeName.length()-1));
		}
	}
	
	/**
	 * 常驻城市
	 * @param applyInfo
	 */
	private void getCityNameFromTemp(MemberResumeTemplate applyInfo){
		String codeName = "";
		String code = applyInfo.getCityCode();
		if(StringUtils.isNotBlank(code)){
			String[] codes = code.split(",");
			for (int j = 0; j < codes.length; j++) {
				if (codes[j]!=null && !codes[j].equals("")) {
					SystemHotspotCity info = cityService.selectById(codes[j]);
					if (info!=null && info.getCityName()!=null) {
						codeName = codeName + info.getCityName()
								+ ",";
					}
				}
			}
			applyInfo.setCityName(codeName.substring(0, codeName.length()-1));
		}
	}
}
