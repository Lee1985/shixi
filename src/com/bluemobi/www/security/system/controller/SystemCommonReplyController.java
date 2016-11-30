package com.bluemobi.www.security.system.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.constant.ReplyEnum;
import com.bluemobi.www.data.model.system.SystemCommonReply;
import com.bluemobi.www.page.PageInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.system.service.SystemCommonReplyService;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.UUIDUtil;

/**
 * 
* @ClassName: SystemCommonReplyController 
* @Description: 常用回复控制层 
* @author sundq 
* @date 2016-02-26 09:15:03 
* @Copyright：上海科匠信息科技有限公司 2015
 */
@Controller
public class SystemCommonReplyController extends BaseController {
	@Resource
	private SystemCommonReplyService service;

	@RequestMapping(value = "system/systemCommonReplyList")
	public String systemCommonReplyList(HttpServletRequest request,
			HttpServletResponse response) {
						
		request.setAttribute("replyTypes", ReplyEnum.values());
		return "system/system_common_reply_list";
	}

	@RequestMapping(value = "system/systemCommonReplyAjaxPage")
	@ResponseBody
	public PageInfo<SystemCommonReply> systemCommonReplyAjaxPage(HttpServletRequest request,
			HttpServletResponse response, SystemCommonReply info, Integer page,
			Integer rows) {
		PageInfo<SystemCommonReply> pageInfo = new PageInfo<SystemCommonReply>();
		pageInfo.setPage(page);
		pageInfo.setPageSize(rows);
		info.setSort("orderList");
		info.setOrder("asc");
		PageInfo<SystemCommonReply> systemCommonReplyList = service.selectAll(info, pageInfo);
		List<SystemCommonReply> list = systemCommonReplyList.getRows();
		if(list != null && !list.isEmpty()){
			for(SystemCommonReply commonReply : list){
				Integer type = commonReply.getType();
				if(type != null){
					commonReply.setTypeName(ReplyEnum.getName(commonReply.getType()));
				}else{
					commonReply.setTypeName("");
				}				
			}
		}
		return pageInfo;
	}

	@RequestMapping(value = "system/systemCommonReplyAjaxAll")
	@ResponseBody
	public List<SystemCommonReply> systemCommonReplyAjaxAll(HttpServletRequest request,
			HttpServletResponse response, SystemCommonReply info, Integer page,
			Integer rows) {
		info.setSort("orderList");
		info.setOrder("asc");
		List<SystemCommonReply> results= service.selectAll(info);
		return results; 
	}
	
	@RequestMapping(value = "system/systemCommonReplyAjaxSave")
	@ResponseBody
	public Map<String,Object> systemCommonReplyAjaxSave(HttpServletRequest request,
			HttpServletResponse response, SystemCommonReply info) {
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

	@RequestMapping(value = "system/systemCommonReplyAjaxDelete")
	@ResponseBody
	public Map<String,Object> systemCommonReplyAjaxDelete(HttpServletRequest request,
			HttpServletResponse response, SystemCommonReply info) {
		int result = 0;
		try {
			result = service.delete(info);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getJsonResult(result,"操作成功", "删除失败！");
	}	
}
