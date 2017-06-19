package com.bluemobi.www.security.recruit.controller;


import java.util.ArrayList;
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

import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.recruit.RecruitInfo;
import com.bluemobi.www.data.model.recruit.RecruitInfoTemp;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.recruit.service.RecruitInfoService;
import com.bluemobi.www.security.recruit.service.RecruitInfoTempService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: RecruitInfoController 
* @Description: 招募信息控制层 
* @author sundq 
* @date 2016-02-16 08:55:47 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class RecruitInfoController extends BaseController {
	
	@Resource
	private RecruitInfoService service;
	
	@Resource
	private RecruitInfoTempService tempService;

	@RequestMapping(value = "recruit/recruitInfoList")
	public String recruitInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		return "recruit/recruit_info_list";
	}
	
	@RequestMapping(value = "recruit/recruitInfoAjaxPage")
	@ResponseBody
	public PageInfo<RecruitInfo> recruitInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, RecruitInfo info, Integer page,
			Integer rows) {
		
		PageInfo<RecruitInfo> pageInfo = new PageInfo<RecruitInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		
		if(StringUtils.isNotBlank(info.getTitle())){			
			info.setTitle(queryLikeParamHandler(info.getTitle()));
		}
		if(StringUtils.isNotBlank(info.getMemberName())){
			info.setMemberName(queryLikeParamHandler(info.getMemberName()));
		}
		
		info.setIsDelete("0");
		info.setStatus("2");
		info.setSort("");
		info.setOrder("publishStatus desc , createDate desc");
		service.selectAll(info, pageInfo);
		List<RecruitInfo> recruitList = pageInfo.getRows();
		if(recruitList != null){
			for(RecruitInfo recruitInfo : recruitList){
				recruitInfo.setTitle(decodeParam(recruitInfo.getTitle()));
				MemberInfo memberInfo = recruitInfo.getMemberInfo();
				memberInfo.setNickname(decodeParam(memberInfo.getNickname()));
				
			}
		}
		return pageInfo;
	}

	@RequestMapping(value = "recruit/recruitInfoAjaxAll")
	@ResponseBody
	public List<RecruitInfo> recruitInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, RecruitInfo info, Integer page,
			Integer rows) {
		List<RecruitInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "recruit/recruitInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> recruitInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, RecruitInfoTemp info) {
		int result = 0;
		String msg = "";
		Map<String, Object> map = new HashMap<String, Object>();
		String recruitId = info.getId();
		try{
			if (info.getId() == null || info.getId().equals("")) {
				
				info.setId(UUIDUtil.getUUID());
				info.setStatus("1");
				info.setIsDelete("0");
				info.setCreateDate(DateUtils.currentStringDate());
				info.setTitle(encodeParam(info.getTitle()));
				info.setDirector(encodeParam(info.getDirector()));
				info.setScreenwriter(encodeParam(info.getScreenwriter()));
				info.setScriptOutline(encodeParam(info.getScriptOutline()));
				result = tempService.insert(info);
				
				RecruitInfo recruitInfo = new RecruitInfo();				
				BeanUtils.copyProperties(info, recruitInfo);
				recruitInfo.setId(UUIDUtil.getUUID());				
				result = service.insert(recruitInfo);
				
				info.setRecruitId(recruitInfo.getId());
				result = tempService.update(info);
				
				recruitId = recruitInfo.getId();
				msg = "保存失败！";
			} else {
				RecruitInfo recruitInfo = service.selectById(info.getId());				
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("recruitId", info.getId());
				RecruitInfoTemp infoTemp = tempService.selectEntity(params);
				info.setId(infoTemp.getId());
				info.setTitle(encodeParam(info.getTitle()));
				info.setDirector(encodeParam(info.getDirector()));
				info.setScreenwriter(encodeParam(info.getScreenwriter()));
				info.setScriptOutline(encodeParam(info.getScriptOutline()));
				result = tempService.update(info);
				BeanUtils.copyProperties(info, recruitInfo);
				recruitInfo.setId(recruitId);
				service.update(recruitInfo);
				msg = "修改失败！";
			}			
			if (result > 0) {
				map.put("success", true);
				map.put("msg", "操作成功");
				map.put("id", recruitId);
			} else {
				map.put("success", false);
				map.put("msg", msg);
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return map;
	}

	@RequestMapping(value = "recruit/recruitInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> recruitInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, RecruitInfo info) {
		int result = 0;
		try {
			String[] idss = info.getIds().split(",");
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < idss.length; i++) {
				list.add(idss[i]);
			}
			result = service.batchDelete(list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
	
	@RequestMapping(value = "recruit/recruitInfoUpdateStatus")
	@ResponseBody
	public Map<String,Object> recruitInfoUpdateStatus(HttpServletRequest request,
			HttpServletResponse response, RecruitInfo info) {
		int result = 0;
		try {
			String[] idss = info.getIds().split(",");
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < idss.length; i++) {
				list.add(idss[i]);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			if(StringUtils.isNotBlank(info.getPublishStatus())){
				map.put("publishStatus", info.getPublishStatus());
			}
			if(StringUtils.isNotBlank(info.getStatus())){
				map.put("status", info.getStatus());
			}
			if(StringUtils.isNotBlank(info.getIsDelete())){
				map.put("isDelete", info.getIsDelete());
			}			
			result = service.batchUpdate(map);
			
			for(String recruitId : list){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("recruitId", recruitId);
				RecruitInfoTemp recruitInfoTemp = tempService.selectEntity(params);
				RecruitInfo recruitInfo = null;
				if(StringUtils.isNotBlank(recruitInfoTemp.getRecruitId())){
					recruitInfo = service.selectById(recruitInfoTemp.getRecruitId());
				}
				
				if(recruitInfoTemp != null){
					if(StringUtils.isNotBlank(info.getStatus())){
						recruitInfoTemp.setStatus(info.getStatus());						
						if(recruitInfo != null){
							recruitInfo.setStatus(info.getStatus());
						}
					}
					if(StringUtils.isNotBlank(info.getIsDelete())){
						recruitInfoTemp.setIsDelete(info.getIsDelete());
						if(recruitInfo != null){
							recruitInfo.setIsDelete(info.getIsDelete());
						}
					}					
					result = tempService.update(recruitInfoTemp);
					if(recruitInfo != null){
						service.update(recruitInfo);
					}
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
}
