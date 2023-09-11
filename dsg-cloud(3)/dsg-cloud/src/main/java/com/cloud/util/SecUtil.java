package com.cloud.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密工具
 * @author touch
 *
 */
public class SecUtil {
	private static Logger log = LoggerFactory.getLogger(SecUtil.class);
	/**
	 * 16位
	 */
	private static String key = "0123456789012345";
	/**
	 * 加密方式
	 */
	private static String trans = "AES/CBC/PKCS5Padding";

	/**
	 * AES加密
	 * @param 要加密的字符串
	 */
	public static String encode(String str) throws Exception {
		log.info("[AES开始加密...]");
		Cipher cipher = Cipher.getInstance(trans);
		SecretKeySpec secret = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		IvParameterSpec iv = new IvParameterSpec(key.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, secret,iv);
		byte[] bytes = cipher.doFinal(str.getBytes());
		Encoder encoder = Base64.getEncoder();
		log.info("[AES加密完成...]");
		return encoder.encodeToString(bytes);
	}
	/**
	 * AES解密
	 * @param 要解密的Base64字符串
	 */
	public static String decode(String str) throws Exception {
		log.info("[AES开始解密...]");
		Cipher cipher = Cipher.getInstance(trans);
		SecretKeySpec secret = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		IvParameterSpec iv = new IvParameterSpec(key.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, secret,iv);
		Decoder decoder = Base64.getDecoder();
		byte[] decode = decoder.decode(str.getBytes());
		byte[] bytes = cipher.doFinal(decode);
		log.info("[AES解密完成...]");
		return new String(bytes);
	}
	/**
	 * MD5校验
	 * @throws Exception 
	 */
	public static String MD5(String str) throws Exception {
		byte[] digest = MessageDigest.getInstance("md5").digest(str.getBytes("UTF-8"));
		String md5 = new BigInteger(1, digest).toString(16);
		for (int i = 0; i < 32 - md5.length(); i++) {
			md5 = "0" + md5;
		}
		return md5;
	}
	/**
	 * Base64编码
	 */
	public static String Base64Encode(String str) {
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(str.getBytes());
	}
	/**
	 * Base64解码
	 */
	public static String Base64Decode(String str) {
		Decoder decoder = Base64.getDecoder();
		byte[] bytes = decoder.decode(str.getBytes());
		return new String(bytes);
	}
}