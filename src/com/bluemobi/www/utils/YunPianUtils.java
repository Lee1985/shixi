package com.bluemobi.www.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;

import com.bluemobi.www.constant.Constant;

public class YunPianUtils {
	public static String url = Constant.YUNPIAN_URL;
	public static String apikey = Constant.YUNPIAN_APIKEY;
	public static String sign = "【试戏APP】";

	public static String sendMessage(String mobile, String content) {

		String res = "";
		try {
			HttpClient client = new HttpClient();
			UTF8PostMethod post = new UTF8PostMethod(url);
			post.addParameter("apikey", apikey);
			post.addParameter("mobile", mobile);
			post.addParameter("text", sign+content);
			int status = client.executeMethod(post);
			System.out.println("##########"+status);
			InputStream in = post.getResponseBodyAsStream();
			BufferedReader re = new BufferedReader(new InputStreamReader(in));
			String line = "";

			while ((line = re.readLine()) != null) {
				res += line;
			}
			System.out.println("##########" + res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}

	public static void main(String[] args) {
		String message="您的验证码是123456";
		System.out.println(message);
		sendMessage("15998265655", message);
		//13284267135
	}
}
