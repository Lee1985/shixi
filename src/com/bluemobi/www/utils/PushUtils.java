package com.bluemobi.www.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bluemobi.www.constant.Constant;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;
import com.gexin.rp.sdk.template.PopupTransmissionTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class PushUtils {

	static String appId = Constant.APPID;
	static String appkey = Constant.APPKEY;
	static String master = Constant.MASTERSECRET;
	static String CID = "5e8fc788ec4c155d3ea7a1eff928cd96";
	static String Alias = "请输入别名"; // 根据别名推送，需要先绑定别名，请参考alias_bind_unbind.java

	static String host = Constant.PUSH_URL;

	/**
	 * 单个推送
	 * @param cid
	 * @param title
	 * @param text
	 * @param transmissionContent
	 * @throws Exception
	 */
	public static void pushToSingle(String cid, String title, String text,
			String transmissionContent) throws Exception {
		if (appId.equals("")) {
			Utils utils = new Utils();
			utils.init();
		}
		IGtPush push = new IGtPush(host, appkey, master);
		push.connect();

		TransmissionTemplate template = getTransmissionTemplate(title, text,transmissionContent);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// message.setPushNetWorkType(1);
		// //判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
		Target target = new Target();

		target.setAppId(appId);
		target.setClientId(cid);
		// 用户别名推送，cid和用户别名只能2者选其一
		// String alias = "个";
		// target.setAlias(alias);
		IPushResult ret = push.pushMessageToSingle(message, target);
		System.out.println(ret.getResponse().toString());
	}
	
	public static void pushToSingleIos(String cid, String title, String text,
			String transmissionContent) throws Exception {
		if (appId.equals("")) {
			Utils utils = new Utils();
			utils.init();
		}
		IGtPush push = new IGtPush(host, appkey, master);
		push.connect();

//		NotificationTemplate template = getNotificationTemplate(title, text,
//				transmissionContent);
		TransmissionTemplate template = getTransmissionTemplate(title, text,transmissionContent);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// message.setPushNetWorkType(1);
		// //判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
		Target target = new Target();

		target.setAppId(appId);
		target.setClientId(cid);
		// 用户别名推送，cid和用户别名只能2者选其一
		// String alias = "个";
		// target.setAlias(alias);
		IPushResult ret = push.pushMessageToSingle(message, target);
		System.out.println(ret.getResponse().toString());
	}

	public static PopupTransmissionTemplate PopupTransmissionTemplateDemo() {
		PopupTransmissionTemplate template = new PopupTransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setText("1212");
		template.setTitle("dddd");
		template.setImg("");
		template.setConfirmButtonText("");
		template.setCancelButtonText("");
		template.setTransmissionContent("111");
		template.setTransmissionType(1);

		return template;
	}

	public static TransmissionTemplate getTransmissionTemplate(String title,
			String text, String transmissionContent)
			throws Exception {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTransmissionType(1);
		template.setTransmissionContent(transmissionContent);
		//template.setPushInfo("dd1", 1, text, "com.gexin.ios.silence", "", "", "", "");
		//template.set

	    APNPayload payload = new APNPayload();
	    payload.setAutoBadge("+1");
	    payload.setContentAvailable(1);
	    payload.setSound("default");
	    payload.setCategory("");
	    //简单模式APNPayload.SimpleMsg 
	    payload.setAlertMsg(new APNPayload.SimpleAlertMsg(title));
	    //字典模式使用下者
	    //payload.setAlertMsg(getDictionaryAlertMsg());
	    template.setAPNInfo(payload);
		return template;
	}

	public static LinkTemplate linkTemplateDemo() throws Exception {
		LinkTemplate template = new LinkTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTitle("");
		template.setText("");
		template.setLogo("text.png");
		// template.setLogoUrl("");
		// template.setIsRing(true);
		// template.setIsVibrate(true);
		// template.setIsClearable(true);
		template.setUrl("http://www.baidu.com");
		// template.setPushInfo("actionLocKey", 1, "message", "sound",
		// "payload", "locKey", "locArgs", "launchImage");
		return template;
	}

	public static NotificationTemplate getNotificationTemplate(String title,
			String text, String transmissionContent) throws Exception {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appkey);
		// 设置通知栏标题与内容
		template.setTitle(title);
		template.setText(text);
		// 配置通知栏图标
		template.setLogo("ic_launcher.png");
		// 配置通知栏网络图标
		template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(2);
		template.setTransmissionContent(transmissionContent);
		return template;
	}

	public static NotyPopLoadTemplate NotyPopLoadTemplateDemo() {
		NotyPopLoadTemplate template = new NotyPopLoadTemplate();
		// 填写appid与appkey
		template.setAppId(appId);
		template.setAppkey(appkey);
		// 填写通知标题和内容
		template.setNotyTitle("标题");
		template.setNotyContent("内容");
		// template.setLogoUrl("");
		// 填写图标文件名称
		template.setNotyIcon("text.png");
		// 设置响铃，震动，与可清除
		// template.setBelled(false);
		// template.setVibrationed(false);
		// template.setCleared(true);

		// 设置弹框标题与内容
		template.setPopTitle("弹框标题");
		template.setPopContent("弹框内容");
		// 设置弹框图片
		template.setPopImage("http://www-igexin.qiniudn.com/wp-content/uploads/2013/08/logo_getui1.png");
		template.setPopButton1("打开");
		template.setPopButton2("取消");

		// 设置下载标题，图片与下载地址
		template.setLoadTitle("下载标题");
		template.setLoadIcon("file://icon.png");
		template.setLoadUrl("http://gdown.baidu.com/data/wisegame/c95836e06c224f51/weixinxinqing_5.apk");
		template.setActived(true);
		template.setAutoInstall(true);
		template.setAndroidMark("");
		return template;
	}
	
	public static Map<String,Object> setBadgeForDeviceToken(String cid,String badge) {
        List<String> deviceTokenList = new ArrayList<String>();
        deviceTokenList.add(cid);
        IGtPush push = new IGtPush(appkey, master);
        IQueryResult res = push.setBadgeForDeviceToken(badge, appId, deviceTokenList);
        System.out.println(res.getResponse());
        return res.getResponse();
    }

	public static void main(String[] args) {
		try {
//			("d13403c38cbee8cfc1f8ed663956a58b", title, text, transmissionContent);
			pushToSingle("9341b8f74d83ce0ce98b501e9331c0cf", "测试2",
					"恭喜您，您的15998265655（订单号）\r获得免单，免单金额1000元", "type=1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
