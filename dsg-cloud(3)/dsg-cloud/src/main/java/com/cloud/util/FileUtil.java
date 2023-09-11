package com.cloud.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private static Logger log = LoggerFactory.getLogger(FileUtil.class);
	private final static Map<String,String> FILE_TYPE_MAP = new HashMap<String,String>();
	static {
		setFileType();
	}
 	public static boolean delete(File file) {
		if(!file.exists()) {
			return false;
		}
		if(file.isFile()) {
			file.delete();
			return true;
		}
		return false;
	}
	
	private static void release(Reader r,Writer w) {
		if(r!=null) {
			try {
				r.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		if(w!=null) {
			try {
				w.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void release(InputStream i,OutputStream o) {
		if(i!=null) {
			try {
				i.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		if(o!=null) {
			try {
				o.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 字符文件读取
	 * @param file 文本文件
	 * @param encoding 编码格式，默认UTF-8
	 * @return
	 */
	public static StringBuffer read(File file,String encoding) {
		StringBuffer str = new StringBuffer();
		if(!file.isFile()) {
			log.info("[read] read file and file is not a file");
			return str;
		}
		if(encoding == null) {
			encoding = "UTF-8";
		}
		Reader r = null;
		BufferedReader b = null;
		try {
			FileInputStream in = new FileInputStream(file);
			r = new InputStreamReader(in,encoding);
			b = new BufferedReader(r);
			char[] c = new char[1024*1024];
			int len;
			while((len=b.read(c))!=-1) {
				str.append(new String(c,0,len));
			}
		}catch(Exception e) {
			log.error("[read] read file stream have Exception",e.getMessage());
		}finally {
			release(b,null);
			release(r,null);
		}
		return str;
	}
	/**
	 * 字符编码转换
	 * @param file 文本文件
	 * @param encode1 原文件编码格式
	 * @param encode2 新文件编码格式
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String transCode(File file,String encode1,String encode2) throws UnsupportedEncodingException {
		StringBuffer read = read(file,encode1);
		String temp = read.toString();
		return new String(temp.getBytes(encode2),encode2);
	}
	/**
	 * 文件复制
	 * @param file 文件
	 * @param dir 目的真实路径
	 * @return
	 */
	public static boolean copy(File file,String dir) {
		File to = new File(dir);
		if(!file.isFile()) {
			return false;
		}
		if(!to.isDirectory()) {
			return false;
		}
		if(!to.exists()) {
			to.mkdirs();
		}
		InputStream i = null;
		OutputStream o = null;
		BufferedInputStream bi = null;
		BufferedOutputStream bo = null;
		try {
			String name = file.getName();
			String split = System.getProperty("file.separator");
			i = new FileInputStream(file);
			bi = new BufferedInputStream(i);
			to = new File(to.getPath()+split+name);
			o = new FileOutputStream(to);
			bo = new BufferedOutputStream(o);
			byte[] buf = new byte[bi.available()];
			while(bi.read(buf)!=-1) {
				bo.write(buf);
			}
			bo.flush();
		}catch(IOException e) {
			log.error("[copy] copy file Exception");
			return false;
		}finally {
			release(bi,bo);
			release(i,o);
		}
		return true;
	}
	
	public static String format(File file) {
		if(!file.isFile()) {
			log.error("[format] this args is not a File");
			return null;
		}
		String res = null;
		try {
			FileInputStream is = new FileInputStream(file);
			byte[] b = new byte[3];
			is.read(b,0,b.length);
			res = FILE_TYPE_MAP.get(hex(b));
			is.close();
		}catch(Exception e) {
			log.error("[format] get file format error");
			return null;
		}
		if(res == null) {
			log.warn("[format] this file format is not exist in FILE_TYPE_MAP");
			return null;
		}
		return res;
	}
		
	private static void setFileType() {
		FILE_TYPE_MAP.put("ffd8ff","jpg");
		FILE_TYPE_MAP.put("89504e","png");
		FILE_TYPE_MAP.put("474946","gif");
		FILE_TYPE_MAP.put("49492a","tif");
		FILE_TYPE_MAP.put("424d22","bmp");
		FILE_TYPE_MAP.put("424d82","bmp");
		FILE_TYPE_MAP.put("424d8e","bmp");
		FILE_TYPE_MAP.put("414331","dwg");
		FILE_TYPE_MAP.put("3c2144","html");
		FILE_TYPE_MAP.put("3c2164","htm");
		FILE_TYPE_MAP.put("48544d","css");
		FILE_TYPE_MAP.put("696b21","js");
		FILE_TYPE_MAP.put("384250","psd");
		FILE_TYPE_MAP.put("d0cf11","doc"); // doc xls ppt msi
		FILE_TYPE_MAP.put("255044","pdf");
		FILE_TYPE_MAP.put("2e524d","rmvb");
		FILE_TYPE_MAP.put("464c56","flv");
		FILE_TYPE_MAP.put("000000","mp4");
		FILE_TYPE_MAP.put("494433","mp3");
		FILE_TYPE_MAP.put("415649","avi");
		FILE_TYPE_MAP.put("000001","mpg");
		FILE_TYPE_MAP.put("3026b2","wmv");
		FILE_TYPE_MAP.put("524946","wav");
		FILE_TYPE_MAP.put("504b03","zip"); // zip jar
		FILE_TYPE_MAP.put("526172","rar");
		FILE_TYPE_MAP.put("235468","ini");
		FILE_TYPE_MAP.put("4d5a90","exe");
		FILE_TYPE_MAP.put("3c2540","jsp");
		FILE_TYPE_MAP.put("3c3f78","xml");
		FILE_TYPE_MAP.put("494e53","sql");
		FILE_TYPE_MAP.put("706163","java");
		FILE_TYPE_MAP.put("406563","bat");
		FILE_TYPE_MAP.put("1f8b08","gz");
		FILE_TYPE_MAP.put("6c6f67","properties");
		FILE_TYPE_MAP.put("cafeba","class");
		FILE_TYPE_MAP.put("643130","torrent");
		FILE_TYPE_MAP.put("6d6f6f","mov");
		FILE_TYPE_MAP.put("23212f","sh");
		FILE_TYPE_MAP.put("efbbbf","txt");
		FILE_TYPE_MAP.put("7b0d0a","json");
		FILE_TYPE_MAP.put("5b3230","log");
	}

	private static String hex(byte[] str) {
		StringBuilder sb = new StringBuilder();
		if(str == null || str.length <= 0) {
			log.warn("[hex] this file length is < 0");
			return null;
		}
		for(int i=0;i<str.length;i++) {
			int v = str[i] & 0xFF;
			String hv = Integer.toHexString(v).toLowerCase();
			if(hv.length()<2) {
				sb.append(0);
			}
			sb.append(hv);
		}
		log.info("[hex] this file's hex="+sb.toString());
		return sb.toString();
	}

	public static boolean upload(InputStream is,String dir) throws Exception {
		String name = SecUtil.MD5(dir);
		File to = new File(dir);
		OutputStream o = null;
		BufferedInputStream bi = null;
		BufferedOutputStream bo = null;
		try {
			String split = System.getProperty("file.separator");
			bi = new BufferedInputStream(is);
			to = new File(to.getPath()+split+name);
			o = new FileOutputStream(to);
			bo = new BufferedOutputStream(o);
			byte[] buf = new byte[bi.available()];
			while(bi.read(buf)!=-1) {
				bo.write(buf);
			}
			bo.flush();
		}catch(IOException e) {
			log.error("[copy] copy file Exception");
			return false;
		}finally {
			release(bi,bo);
			release(is,o);
		}
		return true;
	}
	
	public static boolean download(InputStream is,String url) {
		
		return true;
	}
}
