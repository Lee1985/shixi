package com.bluemobi.www.security.system.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.system.SystemFullText;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemFullTextService;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemFullTextController 
* @Description: 系统信息控制层 
* @author sundq 
* @date 2016-02-14 13:32:36 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class SystemFullTextController extends BaseController {
	@Resource
	private SystemFullTextService service;

	@RequestMapping(value = "/system/systemFullTextEntity")
	public String systemFullTextEntity(HttpServletRequest request,
			HttpServletResponse response, SystemFullText info) {
		//1用户协议
		SystemFullText systemFullText =service.selectEntity(info);
		request.setAttribute("entity", systemFullText);
		return "system/system_full_text_detail";
	}
	
	@RequestMapping(value = "/system/systemFullTextEdit")
	public String systemFullTextEdit(HttpServletRequest request,
			HttpServletResponse response, SystemFullText info) {
		//
		SystemFullText systemFullText =service.selectEntity(info);
		request.setAttribute("entity", systemFullText);
		return "system/system_full_text_edit";
	}

	@RequestMapping(value = "/system/systemFullTextSave")
	@ResponseBody
	public Object systemFullTextSave(HttpServletRequest request,
			HttpServletResponse response, SystemFullText info) {
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
}
