package com.bluemobi.www.security.system.controller;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.bluemobi.www.data.model.system.SystemHotspotCity;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemHotspotCityService;
import com.bluemobi.www.utils.UUIDUtil;
/**
 * 
* @ClassName: SystemHotspotCityController 
* @Description: 城市信息控制层 
* @author sundq 
* @date 2016-02-19 10:56:19 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class SystemHotspotCityController extends BaseController {
	@Resource
	private SystemHotspotCityService service;

	@RequestMapping(value = "system/systemHotspotCityList")
	public String systemHotspotCityList(HttpServletRequest request,
			HttpServletResponse response) {
		return "system/system_hotspot_city_list";
	}

	@RequestMapping(value = "system/systemHotspotCityAjaxPage")
	@ResponseBody
	public PageInfo<SystemHotspotCity> systemHotspotCityAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SystemHotspotCity info, Integer page,
			Integer rows) {
		PageInfo<SystemHotspotCity> pageInfo = new PageInfo<SystemHotspotCity>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setSort("");
		info.setOrder("status desc , orderList asc");
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "system/systemHotspotCityAjaxAll")
	@ResponseBody
	public List<SystemHotspotCity> systemHotspotCityAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemHotspotCity info, Integer page,
			Integer rows) {
		List<SystemHotspotCity> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/systemHotspotCityAjaxSave")
	@ResponseBody
	public Map<String,Object> systemHotspotCityAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SystemHotspotCity info) {
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

	@RequestMapping(value = "system/systemHotspotCityAjaxDelete")
	@ResponseBody
	public Map<String,Object> systemHotspotCityAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SystemHotspotCity info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}

	@RequestMapping(value = "system/systemHotspotCityUpdateStatus")
	@ResponseBody
	public Map<String,Object> systemHotspotCityUpdateStatus(HttpServletRequest request,
			HttpServletResponse response, SystemHotspotCity info) {
		int result = 0;
		try {
			result = service.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
	
	
	@SuppressWarnings("unused")
	public void regionXml() {
		String xmlStr = null;
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        try {
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            Document document = builder.newDocument();
	            document.setXmlVersion("1.0");

	            Element root = document.createElement("root");
	            document.appendChild(root);
	            
	            Map<String, Object> map = new HashMap<String, Object>();
	    		map.put("sort"," ");
	    		map.put("order","spell asc , orderList asc");
	    		List<SystemHotspotCity> list = service.selectAll(map);
	    		for (int i = 0; i < list.size(); i++) {
	    			SystemHotspotCity region = list.get(i);
	    			Element province = document.createElement("city");
	    			province.setAttribute("name", region.getCityName());
	    			province.setAttribute("code", region.getId());
	    			province.setAttribute("spell", region.getSpell());
	    			root.appendChild(province);
				}

	            TransformerFactory transFactory = TransformerFactory.newInstance();
	            Transformer transFormer = transFactory.newTransformer();
	            DOMSource domSource = new DOMSource(document);
	            //export string
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            transFormer.transform(domSource, new StreamResult(bos));
	            xmlStr = bos.toString();
	            //-------
	            //save as file
	            File file = new File("E:\\city.xml");
	            if(!file.exists()){
	                file.createNewFile();
	            }
	            FileOutputStream out = new FileOutputStream(file);
	            StreamResult xmlResult = new StreamResult(out);
	            transFormer.transform(domSource, xmlResult);
	            //--------
	        } catch (ParserConfigurationException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }catch (TransformerConfigurationException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }catch (TransformerException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}
}
