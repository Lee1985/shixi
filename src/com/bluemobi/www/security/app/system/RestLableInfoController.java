package com.bluemobi.www.security.app.system;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.system.SystemLableInfo;
import com.bluemobi.www.data.model.system.SystemLableType;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemLableInfoService;
import com.bluemobi.www.security.system.service.SystemLableTypeService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: RestLableInfoController 
* @Description: 标签管理控制层 
* @author sundq 
* @date 2016-02-02 16:42:04 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class RestLableInfoController extends BaseController {
	@Resource
	private SystemLableInfoService service;
	@Resource
	private SystemLableTypeService typeService;
	

	/**
	 * 获取技能/角色标签列表
	 * @param request
	 * @param response
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "rest/lableInfoList")
	@ResponseBody
	public Map<String,Object> lableInfoList(HttpServletRequest request,HttpServletResponse response, String type, String memberId){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> condition = new HashMap<String,Object>();
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); 
		
		condition.put("type", type);// 标签类型1角色 2 技能
		condition.put("status", "1");
		condition.put("sort", "orderList");
		condition.put("order", "asc");
		
		Map<String,Object> mycondition = new HashMap<String,Object>();
		mycondition.put("memberId", memberId);
		mycondition.put("status", "1");
		List<SystemLableInfo> myList = service.selectAll(mycondition);
		if(myList != null && !myList.isEmpty()){
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("typeId", "0");//类别ID
			resultMap.put("typeName", "我的标签");
			List<Map<String,Object>> resultInfoList = new ArrayList<Map<String,Object>>(); 
			for (SystemLableInfo info: myList) {
				Map<String,Object> resultInfoMap = new HashMap<String,Object>();
				resultInfoMap.put("infoId", info.getId());
				resultInfoMap.put("lableName", decodeParam(info.getLableName()));
				resultInfoList.add(resultInfoMap);
			}
			resultMap.put("info", resultInfoList);
			resultList.add(resultMap);
		}else{
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("typeId", "0");//类别ID
			resultMap.put("typeName", "我的标签");
			resultMap.put("info", new ArrayList<Map<String,Object>>());
			resultList.add(resultMap);
		}
		List<SystemLableType> list = typeService.selectAll(condition);
		if(list != null && !list.isEmpty()){
			for(SystemLableType lableType : list){
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.put("typeId", lableType.getId());//类别ID
				resultMap.put("typeName", lableType.getTypeName());
				Map<String,Object> conditionInfo = new HashMap<String,Object>();
				conditionInfo.put("status", "1");
				conditionInfo.put("sort", "createDate");
				conditionInfo.put("order", "asc");
				conditionInfo.put("typeId", lableType.getId());
				List<SystemLableInfo> infoList = service.selectAll(conditionInfo);
				List<Map<String,Object>> resultInfoList = new ArrayList<Map<String,Object>>(); 
				for (SystemLableInfo info : infoList) {
					Map<String,Object> resultInfoMap = new HashMap<String,Object>();
					resultInfoMap.put("infoId", info.getId());
					resultInfoMap.put("lableName", decodeParam(info.getLableName()));
					resultInfoList.add(resultInfoMap);
				}
				resultMap.put("info", resultInfoList);
				resultList.add(resultMap);
			}
			map.put("success", "yes");
			map.put("data", resultList);
		}else{
			map.put("success", "no");
			map.put("message", "无结果!");
		}
		return map;
	}
	
	
	/**
	 * 保存角色/技能标签
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "rest/saveLableInfo")
	@ResponseBody
	public Map<String,Object> saveLableInfo(HttpServletRequest request,HttpServletResponse response, 
			SystemLableInfo info){
		Map<String,Object> map = new HashMap<String,Object>();
		int result = 0;
		if(info.getMemberId() == null || info.getMemberId().equals("")){
			map.put("success", "no");
			map.put("message", "memberId为空");
			return map;
		}
		if(info.getLableType() == null || info.getLableType().equals("")){
			map.put("success", "no");
			map.put("message", "lableType为空");
			return map;
		}
		if(info.getLableName() == null || info.getLableName().equals("")){
			map.put("success", "no");
			map.put("message", "lableName为空");
			return map;
		}
		String uuid = UUIDUtil.getUUID();
		info.setLableName(encodeParam(info.getLableName()));
		info.setId(uuid);
		info.setCreateDate(DateUtils.currentStringDate());
		info.setTypeId("0");
		info.setStatus("1");
		info.setLableContent("");
		result = service.insert(info);
		if(result == 1){
			map.put("success", "yes");
			map.put("message", "保存成功");
			map.put("id", uuid);
		}else{
			map.put("success", "no");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	/**
	 * 删除角色/技能标签
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "rest/deleteLableInfo")
	@ResponseBody
	public Map<String,Object> deleteLableInfo(HttpServletRequest request,HttpServletResponse response, 
			SystemLableInfo info){
		Map<String,Object> map = new HashMap<String,Object>();
		int result = 0;
		info.setStatus("0");
		result = service.update(info);
		if(result == 1){
			map.put("success", "yes");
			map.put("message", "保存成功");
		}else{
			map.put("success", "no");
			map.put("message", "保存失败");
		}
		return map;
	}
}
