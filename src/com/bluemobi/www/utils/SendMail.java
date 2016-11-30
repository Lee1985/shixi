package com.bluemobi.www.utils;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class SendMail {

public static void sendMail(String message, String toUser) throws AddressException, MessagingException {
		
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");//发送邮件协议
		properties.setProperty("mail.smtp.auth", "true");//需要验证
		// properties.setProperty("mail.debug", "true");//设置debug模式 后台输出邮件发送的过程
		Session session = Session.getInstance(properties);
		session.setDebug(true);//debug模式
		//邮件信息
		Message messgae = new MimeMessage(session);
//		messgae.setFrom(new InternetAddress(SendMailConfig.getValue("mail.address")));//设置发送人
		messgae.setFrom(new InternetAddress("byf890315@163.com"));
		messgae.setText(message);//设置邮件内容
		messgae.setSubject("【中联重科】APP用户密码找回");//设置邮件主题
		
		//发送邮件
		Transport tran = session.getTransport();
//		tran.connect(SendMailConfig.getValue("mail.smtp"), 25, SendMailConfig.getValue("mail.address"), SendMailConfig.getValue("mail.address.password"));//连接到邮箱服务器
		tran.connect("smtp.163.com", 25, "byf890315@163.com", "123456");
		tran.sendMessage(messgae, new Address[]{ new InternetAddress(toUser)});//设置邮件接收人
		tran.close();
	}

public static void forgotPassword(String message) throws AddressException, MessagingException {
	
	Properties properties = new Properties();
	properties.setProperty("mail.transport.protocol", "smtp");//发送邮件协议
	properties.setProperty("mail.smtp.auth", "true");//需要验证
	// properties.setProperty("mail.debug", "true");//设置debug模式 后台输出邮件发送的过程
	Session session = Session.getInstance(properties);
	session.setDebug(true);//debug模式
	//邮件信息
	Message messgae = new MimeMessage(session);
//	messgae.setFrom(new InternetAddress(SendMailConfig.getValue("mail.address")));//设置发送人
	messgae.setFrom(new InternetAddress("overseas_branding@zoomlion.com"));
	messgae.setText(message);//设置邮件内容
	messgae.setSubject("【Zoomlion】APP retrieve password");//设置邮件主题
	
	//发送邮件
	Transport tran = session.getTransport();
//	tran.connect(SendMailConfig.getValue("mail.smtp"), 25, SendMailConfig.getValue("mail.address"), SendMailConfig.getValue("mail.address.password"));//连接到邮箱服务器
	tran.connect("smtp.zoomlion.com", 25, "overseas_branding@zoomlion.com", "zlzk.157");
	tran.sendMessage(messgae, new Address[]{ new InternetAddress("overseas_marketing@zoomlion.com")});//设置邮件接收人
	tran.close();
}

public static void feedBack(String message) throws AddressException, MessagingException {
	
	Properties properties = new Properties();
	properties.setProperty("mail.transport.protocol", "smtp");//发送邮件协议
	properties.setProperty("mail.smtp.auth", "true");//需要验证
	// properties.setProperty("mail.debug", "true");//设置debug模式 后台输出邮件发送的过程
	Session session = Session.getInstance(properties);
	session.setDebug(true);//debug模式
	//邮件信息
	Message messgae = new MimeMessage(session);
//	messgae.setFrom(new InternetAddress(SendMailConfig.getValue("mail.address")));//设置发送人
	messgae.setFrom(new InternetAddress("overseas_branding@zoomlion.com"));
	messgae.setText(message);//设置邮件内容
	messgae.setSubject("【Zoomlion】User feedback");//设置邮件主题
	
	//发送邮件
	Transport tran = session.getTransport();
//	tran.connect(SendMailConfig.getValue("mail.smtp"), 25, SendMailConfig.getValue("mail.address"), SendMailConfig.getValue("mail.address.password"));//连接到邮箱服务器
	tran.connect("smtp.zoomlion.com", 25, "overseas_branding@zoomlion.com", "zlzk.157");
	tran.sendMessage(messgae, new Address[]{ new InternetAddress("overseas_marketing@zoomlion.com")});//设置邮件接收人
	tran.close();
}

public static void leaveMessage(String message) throws AddressException, MessagingException {
	
	Properties properties = new Properties();
	properties.setProperty("mail.transport.protocol", "smtp");//发送邮件协议
	properties.setProperty("mail.smtp.auth", "true");//需要验证
	// properties.setProperty("mail.debug", "true");//设置debug模式 后台输出邮件发送的过程
	Session session = Session.getInstance(properties);
	session.setDebug(true);//debug模式
	//邮件信息
	Message messgae = new MimeMessage(session);
//	messgae.setFrom(new InternetAddress(SendMailConfig.getValue("mail.address")));//设置发送人
	messgae.setFrom(new InternetAddress("overseas_branding@zoomlion.com"));
	messgae.setText(message);//设置邮件内容
	messgae.setSubject("【Zoomlion】User message");//设置邮件主题
	
	//发送邮件
	Transport tran = session.getTransport();
//	tran.connect(SendMailConfig.getValue("mail.smtp"), 25, SendMailConfig.getValue("mail.address"), SendMailConfig.getValue("mail.address.password"));//连接到邮箱服务器
	tran.connect("smtp.zoomlion.com", 25, "overseas_branding@zoomlion.com", "zlzk.157");
	tran.sendMessage(messgae, new Address[]{ new InternetAddress("overseas_marketing@zoomlion.com")});//设置邮件接收人
	tran.close();
}


/**  
 * @Title: comment
 * @Description: TODO  销售网络反馈邮件
 * @Author: wangmaosen   
 * @CreateTime: 2015-7-30 上午11:31:57
 * @param message
 * @throws AddressException
 * @throws MessagingException void
 * @Throws   
 */
public static void comment(String message) throws AddressException, MessagingException {
	
	Properties properties = new Properties();
	properties.setProperty("mail.transport.protocol", "smtp");//发送邮件协议
	properties.setProperty("mail.smtp.auth", "true");//需要验证
	// properties.setProperty("mail.debug", "true");//设置debug模式 后台输出邮件发送的过程
	Session session = Session.getInstance(properties);
	session.setDebug(true);//debug模式
	//邮件信息
	Message messgae = new MimeMessage(session);
//	messgae.setFrom(new InternetAddress(SendMailConfig.getValue("mail.address")));//设置发送人
	messgae.setFrom(new InternetAddress("overseas_branding@zoomlion.com"));
	messgae.setText(message);//设置邮件内容
	messgae.setSubject("[Zoomlion] Sales Network Comment");//设置邮件主题

	//发送邮件
	Transport tran = session.getTransport();
//	tran.connect(SendMailConfig.getValue("mail.smtp"), 25, SendMailConfig.getValue("mail.address"), SendMailConfig.getValue("mail.address.password"));//连接到邮箱服务器
	tran.connect("smtp.zoomlion.com", 25, "overseas_branding@zoomlion.com", "zlzk.157");
	tran.sendMessage(messgae, new Address[]{ new InternetAddress("overseas_marketing@zoomlion.com")});//设置邮件接收人
	tran.close();
}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			comment("小东,您好. 您正在验证您的账号, 验证码是:111111, 请勿告知他人. ");
		} catch (Exception e) {
		 	e.printStackTrace();
		} 
	}

}
