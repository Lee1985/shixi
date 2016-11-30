package com.bluemobi.www.security.activity.controller;


import java.util.Date;
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
import com.bluemobi.www.data.model.activity.ActivityInfo;
import com.bluemobi.www.data.model.system.FileInfo;
import com.bluemobi.www.data.model.system.SystemFileInfo;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemFileInfoService;
import com.bluemobi.www.security.system.service.SystemPictureInfoService;
import com.bluemobi.www.security.activity.service.ActivityInfoService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.FileTool;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: ActivityInfoController 
* @Description: 活动信息控制层 
* @author sundq 
* @date 2016-02-14 14:13:11 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class ActivityInfoController extends BaseController {
	@Resource
	private ActivityInfoService service;
	@Resource
	private SystemPictureInfoService systemPictureInfoService;
	@Resource
	private SystemFileInfoService systemFileInfoService;

	@RequestMapping(value = "activity/activityInfoList")
	public String activityInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		return "activity/activity_info_list";
	}

	@RequestMapping(value = "activity/activityInfoAjaxPage")
	@ResponseBody
	public PageInfo<ActivityInfo> activityInfoAjaxPage(HttpServletRequest request,
			HttpServletResponse response, ActivityInfo info, Integer page,
			Integer rows) {
		PageInfo<ActivityInfo> pageInfo = new PageInfo<ActivityInfo>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "activity/activityInfoAjaxAll")
	@ResponseBody
	public List<ActivityInfo> activityInfoAjaxAll(HttpServletRequest request,
			HttpServletResponse response, ActivityInfo info, Integer page,
			Integer rows) {
		List<ActivityInfo> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "activity/activityInfoAjaxSave")
	@ResponseBody
	public Map<String,Object> activityInfoAjaxSave(HttpServletRequest request,
			HttpServletResponse response, ActivityInfo info) {
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
		String videoUuid = UUIDUtil.getUUID();
		//视频
		MultipartFile videoVideo = multipartRequest.getFile("videoVideo");
		if (videoVideo != null && videoVideo.getSize() > 0) {
			// 删除旧视频
			deleteFile(info.getId(), root);
			// 新文件处理
			saveFile(root, videoUuid, videoVideo);
			info.setVideoUuid(videoUuid);
		}
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			info.setStatus("0");
			info.setCreateDate(DateUtils.currentStringDate());
			result = service.insert(info);
			msg = "保存失败！";
		} else {
			result = service.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "activity/activityInfoAjaxDelete")
	@ResponseBody
	public Map<String,Object> activityInfoAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, ActivityInfo info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
	
	
	@RequestMapping(value = "activity/activityInfoUpdateStatus")
	@ResponseBody
	public Map<String,Object> activityInfoUpdateStatus(HttpServletRequest request,
			HttpServletResponse response, ActivityInfo info) {
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
		String pathTmp = Constant.UPLOAD_ACTIVITY_PATH + "/";
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
		pictureInfo.setCdate(DateUtils.toString(new Date(),
				"yyyy-MM-dd HH:mm:ss"));
		systemPictureInfoService.insert(pictureInfo);
		return 0;
	}

	private int deleteImg(String id, String root) {
		if (id != null && !id.equals("")) {
			// 删除旧图片
			ActivityInfo info = service.selectById(id);
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
	 * 删除文件
	 * 
	 * @param id
	 * @param root
	 * @return
	 */
	private int deleteFile(String id, String root) {
		if (id != null && !id.equals("")) {
			// 删除旧文件
			ActivityInfo activityInfo = service.selectById(id);
			if (activityInfo.getFileInfo() != null) {
				String delRealPath = activityInfo.getFileInfo()
						.getUrlPath();
				if (delRealPath != null) {
					deleteFile(root + delRealPath);
					SystemFileInfo delp = new SystemFileInfo();
					delp.setUuid(activityInfo.getVideoUuid());
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
		String path = Constant.UPLOAD_ACTIVITY_VIDEO_PATH
				+ DateUtils.toString(new Date(), "/yyyy/MM/dd") + "/";
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
		systemFileInfo.setCdate(DateUtils.toString(new Date(),
				"yyyy-MM-dd HH:mm:ss"));
		systemFileInfoService.insert(systemFileInfo);
		return imageInfo;
	}
}
