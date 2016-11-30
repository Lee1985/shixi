package com.bluemobi.www.utils;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESUtils {

	public static String encryptStr(String key, String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes("UTF-8");
			byteMi = encryptByte(key, byteMing);
			strMi = base64en.encode(byteMi);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 解密 以 String 密文输入 ,String 明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public static String decryptStr(String key, String strMi) {
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = base64De.decodeBuffer(strMi);
			byteMing = decryptByte(key, byteMi);
			strMing = new String(byteMing, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	private static byte[] encryptByte(String _key, byte[] byteS) {
		byte[] byteFina = null;
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			_generator.init(new SecureRandom(_key.getBytes("utf-8")));
			Key key = _generator.generateKey();
			_generator = null;
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
		}
		return byteFina;
	}

	private static byte[] decryptByte(String _key, byte[] byteD) {
		Cipher cipher = null;
		byte[] byteFina = null;
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			_generator.init(new SecureRandom(_key.getBytes("utf-8")));
			Key key = _generator.generateKey();
			_generator = null;
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
		}
		return byteFina;
	}

}
