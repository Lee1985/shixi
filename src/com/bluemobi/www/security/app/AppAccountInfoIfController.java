package com.bluemobi.www.security.app;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

import com.bluemobi.www.constant.Constant;
import com.bluemobi.www.data.model.member.MemberInfo;
import com.bluemobi.www.data.model.member.MemberResumeTemplate;
import com.bluemobi.www.data.model.system.SystemHotspotCity;
import com.bluemobi.www.data.model.system.SystemPictureInfo;
import com.bluemobi.www.security.base.controller.BaseController;
import com.bluemobi.www.security.member.service.MemberInfoService;
import com.bluemobi.www.security.member.service.MemberResumeTemplateService;
import com.bluemobi.www.security.system.service.SystemFileInfoService;
import com.bluemobi.www.security.system.service.SystemHotspotCityService;
import com.bluemobi.www.security.system.service.SystemPictureInfoService;
import com.bluemobi.www.utils.CipherUtil;
import com.bluemobi.www.utils.CustomPropertiesUtils;
import com.bluemobi.www.utils.DateUtils;
import com.bluemobi.www.utils.FileTool;
import com.bluemobi.www.utils.NumbericUtils;
import com.bluemobi.www.utils.PushUtils;
import com.bluemobi.www.utils.UUIDUtil;
import com.bluemobi.www.utils.YunPianUtils;
import com.bluemobi.www.utils.email.SimpleMailSender;

/**
 * 个人账号相关
 * @author lip
 * 用户相关接口
 */
@Controller
public class AppAccountInfoIfController extends BaseController{
	
	@Resource
	private MemberInfoService memberInfoService;
	
	@Resource
	private SystemFileInfoService systemFileInfoService;
	
	@Resource
	private SystemPictureInfoService systemPictureInfoService;
	
	@Resource
	private SystemHotspotCityService systemHotspotCityService;
	
	@Resource
	private MemberResumeTemplateService memberResumeTemplateService;
			
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @param info
	 * @param msgCode
	 * @param confirmPass
	 * @param imageFile
	 * @return
	 */
	@RequestMapping(value = "/regist_if")
	@ResponseBody
	public Map<String,Object> regist_if(HttpServletRequest request,HttpServletResponse response, 
			MemberInfo info,MultipartFile imageFile){
		Map<String, Object> map = new HashMap<String, Object>();
		int result = 0;
		if(StringUtils.isBlank(info.getMobile())){
			map.put("success", "no");
			map.put("message", "用户账号不能为空!");
			return map;
		}		
		if(StringUtils.isBlank(info.getPassword())){
			map.put("success", "no");
			map.put("message", "密码不能为空!");
			return map;
		}					
		if(imageFile == null){
			map.put("success", "no");
			map.put("message", "请上传头像!");
			return map;
		}
		if(StringUtils.isBlank(info.getNickname())){
			map.put("success", "no");
			map.put("message", "昵称不能为空!");
			return map;
		}
		if(StringUtils.isBlank(info.getSex())){
			map.put("success", "no");
			map.put("message", "请选择性别!");
			return map;
		}
		if(StringUtils.isBlank(info.getEmail())){
			map.put("success", "no");
			map.put("message", "邮箱不能为空!");
			return map;
		}
		if(StringUtils.isBlank(info.getCityCode())){
			map.put("success", "no");
			map.put("message", "所在城市不能为空!");
			return map;
		}
				
		//手机号是否已经注册
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("mobile", info.getMobile());
		List<MemberInfo> memberList = memberInfoService.selectAll(params);
		if(memberList != null && !memberList.isEmpty()){
			map.put("success", "no");
			map.put("message", "此手机号已被注册!");
			return map;
		}
		
		//昵称是否重复
		params = new HashMap<String,Object>();
		params.put("nickname", info.getNickname());
		memberList = memberInfoService.selectAll(params);
		if(memberList != null && !memberList.isEmpty()){
			map.put("success", "no");
			map.put("message", "该昵称已被占用,请重新填写!");
			return map;
		}
		
		//邮箱是否被注册
		params = new HashMap<String,Object>();
		params.put("email", info.getEmail());
		memberList = memberInfoService.selectAll(params);
		if(memberList != null && !memberList.isEmpty()){
			map.put("success", "no");
			map.put("message", "此邮箱已被注册!");
			return map;
		}
		
		//头像上传
		String root = request.getSession().getServletContext().getRealPath("/");
		String pathTmp = Constant.UPLOAD_MEMBER_PATH + "/";
		String path = pathTmp + DateUtils.toString(new Date(), "yyyy/MM/dd") + "/";
		String realPath = root + path;				
		SystemPictureInfo fileInfo = systemPictureInfoService.insertFileInfo(imageFile, realPath, path);
		if(fileInfo != null){
			String uuid = fileInfo.getUuid();
			info.setImgUuid(uuid);
		}
				
		//注册保存
		String buildPwd = CipherUtil.generatePassword(info.getPassword());
		info.setId(UUIDUtil.getUUID());
		info.setNickname(encodeParam(info.getNickname()));
		info.setPassword(buildPwd);
		info.setIdentityStatus("0");
		info.setRealNameStatus("0");
		info.setEducationStatus("0");
		info.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		info.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		info.setStatus("1");		
		result = memberInfoService.insert(info);
		if(result > 0){
			map.put("success", "yes");
			map.put("message", "注册成功!");
			map.put("memberId", info.getId());
			map.put("nickname", info.getNickname());
		}else{
			map.put("success", "no");
			map.put("message", "注册失败!");
		}		
		return map;	
	}	
	
	/**
	 * 获取手机验证码
	 * @param request
	 * @param response
	 * @param mobile
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/mobile_code_if")
	@ResponseBody
	public Map<String,Object> mobile_code_if(HttpServletRequest request,HttpServletResponse response,String mobile,String type){
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isBlank(mobile)){
			map.put("success", "no");
			map.put("message", "请输入您的手机号码!");
			return map;
		}
		if(!"1".equals(type)){
			//手机号是否已经注册
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("mobile", mobile);
			List<MemberInfo> memberList = memberInfoService.selectAll(params);
			if(memberList == null || memberList.isEmpty()){
				map.put("success", "no");
				map.put("message", "此手机号暂未注册!");
				return map;
			}
			if(memberList.size() > 1){
				map.put("success", "no");
				map.put("message", "此手机号对应多条账号信息!");
				return map;
			}
			map.put("memberId", memberList.get(0).getId());
		}else{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("mobile", mobile);
			List<MemberInfo> memberList = memberInfoService.selectAll(params);
			if(memberList.size() >= 1){
				map.put("success", "no");
				map.put("message", "此手机号已注册!");
				return map;
			}
		}
		String valiCode = NumbericUtils.getRandNum(6);
		YunPianUtils.sendMessage(mobile, "您的验证码是" + valiCode);
		map.put("success", "yes");
		map.put("message", "您的验证码短信已成功发送!");
		map.put("mobileCode", valiCode);
		return map;
	}
	
	/**
	 * 获取邮箱验证码
	 * @param request
	 * @param response
	 * @param email
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mail_code_if")
	@ResponseBody
	public Map<String,Object> mail_code_if(HttpServletRequest request,HttpServletResponse response,
			String email){
		Map<String,Object> map = new HashMap<String,Object>();
		//邮箱是否已经注册
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("email", email);
		List<MemberInfo> memberList = memberInfoService.selectAll(params);
		if(memberList == null || memberList.isEmpty()){
			map.put("success", "no");
			map.put("message", "此邮箱暂未注册!");
			return map;
		}
		if(memberList.size() > 1){
			map.put("success", "no");
			map.put("message", "此邮箱对应多条账号信息!");
			return map;
		}				
		String title = CustomPropertiesUtils.getValue("find_passoword_title");
		String valiCode = NumbericUtils.getRandNum(6);
		String content = CustomPropertiesUtils.getValue("find_passoword_content",valiCode);
		boolean sendFlag = false;
		try {
			sendFlag = SimpleMailSender.sendMail(title, content, "1", email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(sendFlag){
			map.put("success", "yes");
			map.put("message", "邮件发送成功！");
			map.put("emailCode", valiCode);
			map.put("memberId", memberList.get(0).getId());
		}else{
			map.put("success", "no");
			map.put("message", "邮件发送失败！");
		}
		return map;
	}
	
	/**
	 * 找回密码
	 * @param request
	 * @param response
	 * @param memberId
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/find_pass_if")
	@ResponseBody
	public Map<String,Object> find_pass_if(HttpServletRequest request,HttpServletResponse response,
			String memberId,String password){
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isBlank(memberId)){
			map.put("success", "no");
			map.put("message", "找不到用户信息!");
			return map;
		}
		if(StringUtils.isBlank(password)){
			map.put("success", "no");
			map.put("message", "请输入您要修改的密码!");
			return map;
		}		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", memberId);
		params.put("password", CipherUtil.generatePassword(password));
		int result = memberInfoService.update(params);
		if(result > 0){
			map.put("success", "yes");
			map.put("message", "密码修改成功,请返回登录页重新登录!");
		}else{
			map.put("success", "no");
			map.put("message", "密码修改失败!");
		}
		return map;
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @param memberId
	 * @param oldPass
	 * @param newPass
	 * @return
	 */
	@RequestMapping(value = "/update_pass_if")
	@ResponseBody
	public Map<String,Object> update_pass_if(HttpServletRequest request,HttpServletResponse response,
			String memberId,String oldPass,String newPass){
		Map<String,Object> map = new HashMap<String,Object>();
		int result = 0;
		if(StringUtils.isBlank(oldPass)){
			map.put("success", "no");
			map.put("message", "请输入您的原始密码!");
			return map;
		}
		if(StringUtils.isBlank(newPass)){
			map.put("success", "no");
			map.put("message", "请输入您的新密码!");
			return map;
		}
		
		MemberInfo memberInfo = memberInfoService.selectById(memberId);
		oldPass = CipherUtil.generatePassword(oldPass);
		if(!oldPass.equals(memberInfo.getPassword())){
			map.put("success", "no");
			map.put("message", "您输入的原始密码不正确!");
			return map;
		}
		newPass = CipherUtil.generatePassword(newPass);
		memberInfo.setPassword(newPass);
		memberInfo.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		result = memberInfoService.update(memberInfo);
		if(result > 0){
			map.put("success", "yes");
			map.put("message", "密码修改成功,请重新登录!");
			return map;
		}else{
			map.put("success", "yes");
			map.put("message", "密码修改失败!");
			return map;
		}			
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @param memberId
	 * @param oldPass
	 * @param newPass
	 * @return
	 */
	@RequestMapping(value = "/set_badge_if")
	@ResponseBody
	public Map<String,Object> set_badge_if(HttpServletRequest request,HttpServletResponse response,String cid,String badge){
		return PushUtils.setBadgeForDeviceToken(cid, badge);
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @param memberId
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login_if")
	@ResponseBody
	public Map<String,Object> login_if(HttpServletRequest request,HttpServletResponse response,
			String account,String password,String cid){
		Map<String,Object> map = new HashMap<String,Object>();
		List<MemberInfo> memberList = memberInfoService.selectByAccountAndPass(account, CipherUtil.generatePassword(password));				
		if(memberList.size() > 1){
			map.put("success", "no");
			map.put("message", "您的账号存在异常,请联系管理员！");
			return map;
		}
		if(memberList.size() <= 0){
			map.put("success", "no");
			map.put("message", "您输入的账号或密码不正确！");
			return map;
		}
		MemberInfo memberInfo = memberList.get(0);
		if("0".equals(memberInfo.getStatus())){
			map.put("success", "no");
			map.put("message", "您的账号被禁用,如有疑问请联系客服人员！");
			return map;
		}	
		//更新该用户的设备Id
		if(StringUtils.isNotBlank(cid)){
			memberInfo.setCid(cid);
			memberInfoService.update(memberInfo);
		}				
		PushUtils.setBadgeForDeviceToken(cid,"0");
		map.put("success", "yes");
		map.put("message", "欢迎您," + memberInfo.getNickname());
		String memberId = memberList.get(0).getId();
		map.put("memberId", memberId);
		map.put("nickname", memberList.get(0).getNickname());	
		return map;
	}
	
	@RequestMapping(value = "/personal_info_if")
	@ResponseBody
	public Map<String,Object> personal_info_if(HttpServletRequest request,HttpServletResponse response,String memberId){
		Map<String,Object> map  = new HashMap<String,Object>();
		if(StringUtils.isBlank(memberId)){
			map.put("success", "no");
			map.put("message", "找不到信息用户登录信息！");
			return map;
		}
		MemberInfo memberInfo = memberInfoService.selectById(memberId);
		if(memberInfo == null){
			map.put("success", "no");
			map.put("message", "找不到信息用户登录信息！");
			return map;
		}
		Map<String,Object> memberMap = new HashMap<String,Object>();
		memberMap.put("nickname", memberInfo.getNickname() == null ? "" : decodeParam(memberInfo.getNickname()));
		memberMap.put("sex", memberInfo.getSex() == null ? "" : memberInfo.getSex());
		memberMap.put("mobile", memberInfo.getMobile() == null ? "" : memberInfo.getMobile());
		memberMap.put("email", memberInfo.getEmail() == null ? "" : memberInfo.getEmail());
		String cityCode = memberInfo.getCityCode();
		Map<String,Object> cityMap = getCityInfo(cityCode);
		memberMap.putAll(cityMap);
		
		//头像
		if(StringUtils.isNotBlank(memberInfo.getImgUuid())){
			SystemPictureInfo systemPictureInfo = systemPictureInfoService.selectByUuid(memberInfo.getImgUuid());
			if(systemPictureInfo != null){
				if(StringUtils.isNotBlank(systemPictureInfo.getUrlPath())){
					memberMap.put("headUrl", getBasePath(request) + systemPictureInfo.getUrlPath());
				}else{
					memberMap.put("headUrl", "");
				}				
			}else{
				memberMap.put("headUrl", "");
			}
		}else{
			memberMap.put("headUrl", "");
		}						
		map.put("success", "yes");		
		map.put("memberInfo",memberMap);		
		return map;
	}
	
	/**
	 * 个人信息修改
	 * @param request
	 * @param response
	 * @param info
	 * @param imageFile
	 * @return
	 */
	@RequestMapping(value = "/personal_edit_if")
	@ResponseBody
	public Map<String,Object> personal_edit_if(HttpServletRequest request,HttpServletResponse response,
			MemberInfo info,MultipartFile imageFile){
		Map<String,Object> map = new HashMap<String,Object>();
		String id = info.getId();
		MemberInfo memberInfo = memberInfoService.selectById(id);
		
		//判断手机号是否重复
		List<MemberInfo> memberList = memberInfoService.selectByMobile(info.getMobile(), id);
		if(memberList != null && !memberList.isEmpty()){
			map.put("success", "no");
			map.put("message", "您输入的手机号已被注册!");
			return map;
		}
		
		//判断邮箱是否重复
		memberList = memberInfoService.selectByEmail(info.getEmail(), id);
		if(memberList != null && !memberList.isEmpty()){
			map.put("success", "no");
			map.put("message", "您输入的邮箱已被注册!");
			return map;
		}
		
		//判断昵称是否重复
		memberList = memberInfoService.selectByNickname(encodeParam(info.getNickname()), id);
		if(memberList != null && !memberList.isEmpty()){
			map.put("success", "no");
			map.put("message", "您输入的昵称已被使用,请重新输入!");
			return map;
		}
				
		if(imageFile != null){
			String imageUuid = memberInfo.getImgUuid();
			//找到当前头像图片
			Map<String,Object> fileParams = new HashMap<String,Object>();
			fileParams.put("uuid", imageUuid);
			List<SystemPictureInfo> fileList = systemPictureInfoService.selectAll(fileParams);
			if(fileList.size() > 0){
				//删除当前图片
				SystemPictureInfo fileInfo = fileList.get(0);
				String filePath = fileInfo.getUrlPath();
				FileTool.deleteFile(filePath);				
			}
			
			//添加新图片
			//头像上传
			String root = request.getSession().getServletContext().getRealPath("/");
			String pathTmp = Constant.UPLOAD_MEMBER_PATH + "/";
			String path = pathTmp + DateUtils.toString(new Date(), "yyyy/MM/dd") + "/";
			String realPath = root + path;
			SystemPictureInfo newFileInfo = systemPictureInfoService.insertFileInfo(imageFile, realPath, path);
			String newImageUuid = newFileInfo.getUuid();
			info.setImgUuid(newImageUuid);					
		}
		
		info.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));			
		if(StringUtils.isNotBlank(info.getCityCode())){
			Map<String,Object> cityParams = new HashMap<String,Object>();
			cityParams.put("zipcode", info.getCityCode());	
			List<SystemHotspotCity> cityList = systemHotspotCityService.selectAll(cityParams);
			if(cityList != null && !cityList.isEmpty()){
				info.setCityCode(cityList.get(0).getId());
			}
		}
		info.setNickname(encodeParam(info.getNickname()));
		int result = memberInfoService.update(info);
		if(result > 0){
			MemberResumeTemplate memberResumeTemplate = memberResumeTemplateService.selectEntityByMemberId(id);
			if(memberResumeTemplate != null){
				//修改简历信息
				memberResumeTemplate.setCityCode(info.getCityCode());
				memberResumeTemplate.setSex(info.getSex());
				memberResumeTemplate.setImgUuid(info.getImgUuid());
				result = memberResumeTemplateService.update(memberResumeTemplate);
			}
		}			
		if(result > 0){				
			map.put("success", "yes");
			map.put("message", "修改个人信息成功");
		}else{
			map.put("success", "no");
			map.put("message", "修改个人信息失败");
		}
		return map;
	}	
	
	private Map<String,Object> getCityInfo(String cityCode){
		Map<String,Object> cityMap = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(cityCode)){
			SystemHotspotCity cityInfo = systemHotspotCityService.selectById(cityCode);
			if(cityInfo != null){
				String cityName = cityInfo.getCityName();
				if(StringUtils.isNotBlank(cityName)){
					cityMap.put("cityCode", cityCode);
					cityMap.put("cityName", cityName);				
				}else{
					cityMap.put("cityCode", "");
					cityMap.put("cityName", "");
				}
			}else{
				cityMap.put("cityCode", "");
				cityMap.put("cityName", "");
			}
		}else{
			cityMap.put("cityCode", "");
			cityMap.put("cityName", "");
		}
		return cityMap;
	}
}
