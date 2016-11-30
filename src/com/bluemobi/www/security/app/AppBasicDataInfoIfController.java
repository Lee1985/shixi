package com.bluemobi.www.security.app;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bluemobi.www.data.model.system.SystemHotspotCity;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberInfoService;
import com.bluemobi.www.security.system.service.SystemHotspotCityService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.FileTool;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
 * @author lip
 * 基础数据接口(城市信息、标签)
 */
@Controller
public class AppBasicDataInfoIfController extends BaseController{
	
	@Resource
	private SystemHotspotCityService systemHotspotCityService;
	
	@Resource
	private MemberInfoService memberInfoService;
	
	/**
	 * 初始化城市信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/init_city_if")
	@ResponseBody
	public Map<String,Object> init_City_if(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		//读取json文件
		String imageListPath = request.getSession().getServletContext().getRealPath("/json");
		String jsonContent = FileTool.readFileContent(imageListPath + "/city.json");
		if(StringUtils.isBlank(jsonContent)){
			map.put("success", "no");
			map.put("message", "找不到城市信息!");
			return map;
		}
		List<Map<String,String>> cityList = JSON.parseObject(jsonContent,new TypeReference<List<Map<String,String>>>(){});
		int index = 0;
		for(Map<String,String> cityMap : cityList){			
			String cityName = cityMap.get("name");
			String spell = cityMap.get("pinyin");
			String firstSpell = String.valueOf(spell.charAt(0));
			String zipCode = cityMap.get("zip");
			index++;
			
			SystemHotspotCity hotspotCity = new SystemHotspotCity();
			hotspotCity.setId(UUIDUtil.getUUID());
			hotspotCity.setCreateDate(DateUtils.currentStringDate());
			hotspotCity.setCityName(cityName);
			hotspotCity.setOrderList(index);
			hotspotCity.setSpell(firstSpell);
			hotspotCity.setZipcode(zipCode);
			hotspotCity.setStatus("1");
			systemHotspotCityService.insert(hotspotCity);
			//systemHotspotCityService.selectAll();
			//memberInfoService.selectAll();
		}		
		map.put("success", "yes");
		map.put("message", "初始化城市信息成功!");
		return map;
	}
	
	/**
	 * 获取热点城市
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/hotspot_City_if")
	@ResponseBody
	public Map<String,Object> hotspot_City_if(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("status", "2");
		params.put("sort", "orderList");
		params.put("order", "asc");
		List<SystemHotspotCity> hotSpotCityList = systemHotspotCityService.selectAll(params);
		if(hotSpotCityList == null || hotSpotCityList.isEmpty()){
			map.put("success", "no");
			map.put("message", "暂无信息!");
			return map;
		}
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>(); 
		for(SystemHotspotCity systemHotspotCity : hotSpotCityList){
			Map<String,Object> cityMap = new HashMap<String,Object>();
			cityMap.put("orderList", systemHotspotCity.getOrderList());
			cityMap.put("cityName", systemHotspotCity.getCityName());
			cityMap.put("zipcode", systemHotspotCity.getId());
			resultList.add(cityMap);
		}
		map.put("success", "yes");
		map.put("data", resultList);
		return map;
	}
}
