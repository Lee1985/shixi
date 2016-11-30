package com.bluemobi.www.constant;

public final class Constant {
	public final static boolean ISPASS = false;
	public final static boolean NOTPASS = true;
	
	public final static String APPID = "CEAIb588AE8kvBUshnSSN6";//个推
	public final static String APPKEY = "xtbDPhv1536atgITOqFcz4";//个推
	public final static String MASTERSECRET = "J974t8t0Mu92fplM7TVSc3";//个推
	public final static String PUSH_URL = "http://sdk.open.api.igexin.com/serviceex";//个推
	
	public final static String YUNPIAN_APIKEY = "9972f7b4c47b6ea5e1f435f52ccc7fe9";//云片
	public final static String YUNPIAN_URL = "https://yunpian.com/v1/sms/send.json";//云片
	
	
	public final static String INITAIL_DATABASE_DATA = "true"; 
	
	/** 当前用户 */
	public final static String CURRENT_USER = "currentUser";
	/** 当前验证码 */
	public final static String CURRENT_USER_VALIDATE_CODE_KEY = "CURRENT_USER_VALIDATE_CODE_KEY";
	
	public final static String WEB_SITE_ROOT_PATH = "picture/";
	private final static byte[] bRN = {1};
	
	public final static String STR_RN = new String(bRN);
	
	/** APP禁用角色ID(管理员)  */
	public final static String ROLEID_ONE="be13245f2dcf44fcbbf7dc845bf90920";
	
	/** APP普通用户  */
	public final static String ROLEID_TWO="c61d99459283405ca7da9d020de67f89";
	
	/** 超级管理员  */
	public final static String ROLEID_THREE="69537fcf7e2743678c3bf62d996b1178";
	
	/** 商务通员工  */
	public final static String ROLEID_FOUR="179b455ee7f44a78b6dd711732c074be";
	
	/** 代理商  */
	public final static String ROLEID_FIVE="c76fbd1edfb6435f92456107621770f7";
	
	/** 启用状态 */
	public final static Integer STATUS_ENABLED = new Integer(1);
	
	/** 禁用状态 */
	public final static Integer STATUS_DISABLED = new Integer(0);
	
	/** 未处理状态 */
	public final static Integer STATUS_UNHANDLED = new Integer(0);
	
	/** 已处理状态 */
	public final static Integer STATUS_HANDLED = new Integer(1);
	
	/** 开状态 */
	public final static Integer STATUS_OPEN = new Integer(1);
	
	/** 关状态 */
	public final static Integer STATUS_CLOSE = new Integer(0);
	
	/** 角色 ：主用户 */
	public final static Integer ROLE_MAIN_USER = new Integer(1);
	
	/** 角色 ：可编辑用户 */
	public final static Integer ROLE_SUPPLIER_USER = new Integer(2);
	
	/** 角色 ：次要用户 */
	public final static Integer ROLE_SECONDARY_USER = new Integer(3);
	
	/** 角色 ：主供应商*/
	public final static Integer ROLE_MAIN_SUPPLIER = new Integer(4);
	
	/** 角色 ：普通供应商*/
	public final static Integer ROLE_SECONDARY_SUPPLIER = new Integer(5);
	
	/** 角色 ：超级管理员*/
	public final static Integer ROLE_MAIN_MANAGER = new Integer(6);
	
	/** 角色 ：普通管理员*/
	public final static Integer ROLE_SECONDARY_MANAGER = new Integer(7);
	
	/** 场景：常用*/
	public final static Integer SCENE_IS_COMMON_USE = new Integer(1);
	public final static Integer SCENE_IS_NOT_USE = new Integer(0);
	
	/** 登录类型 */
	public final static String ACCOUNT_TYPE_ADMIN = "admin";
	public final static String ACCOUNT_TYPE_SUPPLIER = "supplier";
	public final static String ACCOUNT_TYPE_USER = "user";
	
	/**
	 * 图片库类型
	 */
	
	public final static Integer IMAGE_TYPE_BUTTON = new Integer(1);//按钮
	public final static Integer IMAGE_TYPE_LOOP = new Integer(2); //回路
	public final static Integer IMAGE_TYPE_SCENE = new Integer(3);//场景
	
	
	public final static String TEMPLATE_PATH = "/WEB-INF/views/custom/template/";

	//叶子节点
	public static final Integer IS_LEAF = 1;
	public static final Integer NOT_LEAF = 0;
	
	
	
	
	
	/**----------------------------------图片视频地址--------------------------------------------------/
	/**
	 * 广告图片
	 */
	public final static String UPLOAD_HOMEPAGE_PATH = "file/system/homepage";
	/**
	 * 广告图片
	 */
	public final static String UPLOAD_ADVERTISEMENT_PATH = "file/system/advertisement";
	/**
	 * 活动图片
	 */
	public final static String UPLOAD_ACTIVITY_PATH = "file/activity/info";
	/**
	 * 招募角色图片
	 */
	public final static String UPLOAD_RECRUIT_PATH = "file/recruit/role";
	/**
	 * 用户图片
	 */
	public final static String UPLOAD_MEMBER_PATH = "file/member/info";
	/**
	 * 简历图片
	 */
	public final static String UPLOAD_MEMBER_RESUME_PATH = "file/member/resume";
	/**
	 * 自我介绍视频
	 */
	public final static String UPLOAD_INTRODUCTION_VIDEO_PATH = "file/video/introduction";
	/**
	 * 活动视频
	 */
	public final static String UPLOAD_ACTIVITY_VIDEO_PATH = "file/video/activity";
	/**
	 * 试戏视频
	 */
	public final static String UPLOAD_PLAY_VIDEO_PATH = "file/video/play";
	
	
	/**----------------------------------推送类型--------------------------------------------------/
	/**
	 * 用户反馈回复
	 */
	public final static String PUSH_REPLY = "1";
	/**
	 * 用户反馈回复
	 */
	public final static String PUSH_REPLY_TITLE = "用户反馈回复";
	/**
	 * 身份认证
	 */
	public final static String PUSH_IDENTITY = "2";
	/**
	 * 实名认证
	 */
	public final static String PUSH_REALNAME = "3";
	/**
	 * 学历认证
	 */
	public final static String PUSH_EDUCATION = "4";
	/**
	 * 招募审核
	 */
	public final static String PUSH_RECRUIT = "5";
	/**
	 * 剧组回复
	 */
	public final static String PUSH_RECRUIT_REPLY = "6";
	/**
	 * 学历认证
	 */
	public final static String PUSH_VIP = "7";
}
