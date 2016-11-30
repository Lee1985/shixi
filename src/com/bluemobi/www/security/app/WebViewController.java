package com.bluemobi.www.security.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.www.data.model.activity.ActivityCompanyInternal;
import com.bluemobi.www.data.model.message.MessageOfficialNotice;
import com.bluemobi.www.data.model.system.SystemAdvertisementInfo;
import com.bluemobi.www.data.model.system.SystemCommonQuestion;
import com.bluemobi.www.data.model.system.SystemFullText;
import com.bluemobi.www.security.activity.service.ActivityCompanyInternalService;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.message.service.MessageOfficialNoticeService;
import com.bluemobi.www.security.system.service.SystemAdvertisementInfoService;
import com.bluemobi.www.security.system.service.SystemCommonQuestionService;
import com.bluemobi.www.security.system.service.SystemFullTextService;
import com.bluemobi.www.utils.VideoUtil;

@Controller
public class WebViewController extends BaseController {
	
	@Resource
	private MessageOfficialNoticeService noticeService;
	@Resource
	private SystemCommonQuestionService questionService;
	@Resource
	private ActivityCompanyInternalService internalService;
	@Resource
	private SystemFullTextService fullTextService;
	@Resource
	private SystemAdvertisementInfoService advertisementInfoService;
	/**
	 * 用户协议webview
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "user_agreement_view")
	public String user_agreement_view(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", 1);
		SystemFullText fullText = fullTextService.selectEntity(map);
		request.setAttribute("fullText", fullText);
		return "webview/user_agreement";
	}

	/**
	 * 广告详情webview
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "adv_detail_view")
	public String adv_detail(HttpServletRequest request,HttpServletResponse response,String advId){
		SystemAdvertisementInfo advertisementInfo = advertisementInfoService.selectById(advId);
		advertisementInfo.setContent(VideoUtil.embedToVideo(advertisementInfo.getContent()));
		request.setAttribute("advertisementInfo", advertisementInfo);
		return "webview/adv_detail";
	}
	
	/**
	 * 公司内部活动详情webview
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "company_activity_detail_view")
	public String inner_activity_detail(HttpServletRequest request,HttpServletResponse response,String activityDetailId){
		ActivityCompanyInternal internal = internalService.selectById(activityDetailId);
		internal.setContent(VideoUtil.embedToVideo(internal.getContent()));
		request.setAttribute("internal", internal);
		return "webview/company_activity_detail";
	}
	
	/**
	 * 官方通知webview
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "official_message_detail_view")
	public String official_message_detail(HttpServletRequest request,HttpServletResponse response,String activityDetailId){
		MessageOfficialNotice officialNotice = noticeService.selectById(activityDetailId);
		officialNotice.setContent(VideoUtil.embedToVideo(officialNotice.getContent()));
		request.setAttribute("officialNotice", officialNotice);
		return "webview/official_message_detail";
	}
	
	/**
	 * 帮助详情webview
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "help_detail_view")
	public String help_detail(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sort", "createDate");
		map.put("order", "asc");
		List<SystemCommonQuestion> list = questionService.selectAll(map);
		request.setAttribute("list", list);
		return "webview/help_detail";
	}
	
	@RequestMapping(value = "qrcode")
	public String qrcode(HttpServletRequest request,HttpServletResponse response){		
		return "webview/qrcode";
	}
	
	@RequestMapping(value = "getCookies")
	@ResponseBody
	public String getCookies(HttpServletRequest request,HttpServletResponse response){		
		Cookie[] cookies = request.getCookies();
		StringBuffer str = new StringBuffer();
        for(Cookie c :cookies ){
        	str.append(c.getName()+"--->"+c.getValue() + "\n");
        }		
        return str.toString();
	}
}
