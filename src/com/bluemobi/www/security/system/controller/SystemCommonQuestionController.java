package com.bluemobi.www.security.system.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.system.SystemCommonQuestion;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemCommonQuestionService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemCommonQuestionController 
* @Description: 常见问题控制层 
* @author sundq 
* @date 2016-02-02 15:06:22 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class SystemCommonQuestionController extends BaseController {
	@Resource
	private SystemCommonQuestionService service;

	@RequestMapping(value = "system/systemCommonQuestionList")
	public String systemCommonQuestionList(HttpServletRequest request,
			HttpServletResponse response) {
		return "system/system_common_question_list";
	}

	@RequestMapping(value = "system/systemCommonQuestionAjaxPage")
	@ResponseBody
	public PageInfo<SystemCommonQuestion> systemCommonQuestionAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SystemCommonQuestion info, Integer page,
			Integer rows) {
		PageInfo<SystemCommonQuestion> pageInfo = new PageInfo<SystemCommonQuestion>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		service.selectAll(info, pageInfo);
		return pageInfo;
	}

	@RequestMapping(value = "system/systemCommonQuestionAjaxAll")
	@ResponseBody
	public List<SystemCommonQuestion> systemCommonQuestionAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemCommonQuestion info, Integer page,
			Integer rows) {
		List<SystemCommonQuestion> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/systemCommonQuestionAjaxSave")
	@ResponseBody
	public Map<String,Object> systemCommonQuestionAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SystemCommonQuestion info) {
		int result = 0;
		String msg = "";
		if (info.getId() == null || info.getId().equals("")) {
			info.setId(UUIDUtil.getUUID());
			info.setCreateDate(DateUtils.currentStringDate());
			result = service.insert(info);
			msg = "保存失败！";
		} else {
			result = service.update(info);
			msg = "修改失败！";
		}
		return getJsonResult(result, "操作成功",msg);
	}

	@RequestMapping(value = "system/systemCommonQuestionAjaxDelete")
	@ResponseBody
	public Map<String,Object> systemCommonQuestionAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SystemCommonQuestion info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return getJsonResult(result,"操作成功", "删除失败！");
	}
}
