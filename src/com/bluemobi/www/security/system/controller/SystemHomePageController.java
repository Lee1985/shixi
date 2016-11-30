package com.bluemobi.www.security.system.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bluemobi.www.constant.Constant;
import com.bluemobi.www.data.model.system.FileInfo;
import com.bluemobi.www.data.model.system.SystemAdvertisementInfo;
import com.bluemobi.www.data.model.system.SystemHomePage;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemAdvertisementInfoService;
import com.bluemobi.www.security.system.service.SystemHomePageService;
import com.bluemobi.www.security.system.service.SystemPictureInfoService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.FileTool;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemHomePageController 
* @Description: 首页管理控制层 
* @author sundq 
* @date 2016-02-15 13:28:48 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class SystemHomePageController extends BaseController {
	@Resource
	private SystemHomePageService service;
	@Resource
	private SystemPictureInfoService systemPictureInfoService;
	@Resource
	private SystemAdvertisementInfoService infoService;

	@RequestMapping(value = "system/systemHomePageList")
	public String systemHomePageList(HttpServletRequest request,
			HttpServletResponse response) {
		return "system/system_home_page_list";
	}

	@RequestMapping(value = "system/systemHomePageAjaxPage")
	@ResponseBody
	public PageInfo<SystemHomePage> systemHomePageAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SystemHomePage info, Integer page,
			Integer rows) {
		PageInfo<SystemHomePage> pageInfo = new PageInfo<SystemHomePage>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setSort("orderList");
		info.setOrder("asc");
		service.selectAll(info, pageInfo);
		StringBuffer homeStr = new StringBuffer();
		List<SystemHomePage> homeList = pageInfo.getRows();
		for (int i = 0; i < homeList.size(); i++) {
			if(homeList.get(i).getType().equals("1")){
				Map map = new HashMap();
				map.put("status", "1");
				List<SystemAdvertisementInfo> list = infoService.selectAll(map);
				for (int j = 0; j < list.size(); j++) {
					homeStr.append(list.get(j).getTitle());
					if(j < list.size() -1){
						homeStr.append(",");
					}
					homeList.get(i).setPictureInfo(list.get(j).getPictureInfo());
				}
				homeList.get(i).setHomeStr(homeStr.toString());
			}
		}
		return pageInfo;
	}

	@RequestMapping(value = "system/systemHomePageAjaxAll")
	@ResponseBody
	public List<SystemHomePage> systemHomePageAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemHomePage info, Integer page,
			Integer rows) {
		List<SystemHomePage> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/systemHomePageAjaxSave")
	@ResponseBody
	public Map<String,Object> systemHomePageAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SystemHomePage info) {
		int result = 0;
		String msg = "";
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

	@RequestMapping(value = "system/systemHomePageAjaxDelete")
	@ResponseBody
	public Map<String,Object> systemHomePageAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SystemHomePage info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
	
	@RequestMapping(value = "system/systemHomePageUpdateStatus")
	@ResponseBody
	public Map<String,Object> systemHomePageUpdateStatus(HttpServletRequest request,
			HttpServletResponse response, SystemHomePage info) {
		int result = 0;
		try {
			result = service.update(info);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getJsonResult(result,"操作成功", "操作失败！");
	}
	
	private int saveImg(String root, String meetingImgUuid,
			MultipartFile listfile) {
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
		pictureInfo.setCdate(DateUtils.toString(new Date(),
				"yyyy-MM-dd HH:mm:ss"));
		systemPictureInfoService.insert(pictureInfo);
		return 0;
	}

	private int deleteImg(String id, String root) {
		if (id != null && !id.equals("")) {
			// 删除旧图片
			SystemHomePage info = service.selectById(id);
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
}
