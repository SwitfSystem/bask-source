package com.cloud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.bean.User;
import com.cloud.util.RedisUtil;
import com.cloud.util.SysResult;
import com.cloud.util.TokenUtil;

@Service
public class TokenService {
	
	private static Logger log = LoggerFactory.getLogger("TOKEN");
	
	@Autowired
	private RedisUtil redisUtil;
	
	private String setAdmin(String phone) {
		TokenUtil t = new TokenUtil(1,1,3,3);
		String token = "A"+t.nextId();
		redisUtil.set(phone, token, 60*30);
		redisUtil.set(token, phone, 60*30);
		return token;
	}
	
	public String getAdminInfo(String token) {
		return (String)redisUtil.get(token);
	}
	//验证管理员是否登录
	public String getAdmin(String phone) {
		String token = "";
		if(redisUtil.hasKey(phone)) {
			redisUtil.del(phone);
			token = setAdmin(phone);
		}else {
			token = setAdmin(phone);
		}
		return token;
	}
	
	public String updateAdmin(String token) {
		if(redisUtil.hasKey(token)) {
			long expire = redisUtil.getExpire(token);
			if(expire < 600) {
				String phone = (String)redisUtil.get(token);
				token = setAdmin(phone);
			}
			return token;
		}
		log.info("管理员token已经过期,重新登录 token={}",token);
		return null;
	}
	
	public String setUser(String phone,Object obj) {
		TokenUtil t = new TokenUtil(1,1,3,3);
		String token = t.nextId();
		String json = JSON.toJSONString(obj);
		redisUtil.set(token, json);
		redisUtil.set(phone, token);
		log.info("添加token token={} obj={}",token,json);
		return token;
	}
	
	public User getUser(String token) {
		Object obj = redisUtil.get(token);
		if(obj == null) {
			return null;
		}
		User user = JSON.parseObject(JSON.toJSONString(obj), User.class);
		return user;
	}
	
	public synchronized String updateToken(String token) {
		if(redisUtil.hasKey(token)) {
			long expire = redisUtil.getExpire(token);
			if(expire < 600) {
				Object obj = redisUtil.get(token);
				String json = JSON.toJSONString(obj);
				TokenUtil t = new TokenUtil(1,1,3,3);
				String token_new = t.nextId();
				redisUtil.set(token_new, json);
				log.info("token将要过期,进行更新 token={} obj={}",token,json);
				return token_new;
			}
			log.info("无需更新token token={}",token);
			return token;
		}
		log.info("用户token已经过期,重新登录 token={}",token);
		return null; // token过期需要登录
	}
	
	public void delToken(String phone,String token) {
		log.info("删除token token={}",token);
		if(phone == null || token == null) {
			return;
		}
		redisUtil.del(phone);
		redisUtil.del(token);
	}
	
	public JSONObject parseObject(String token) {
		String json = (String)redisUtil.get(token);
		JSONObject obj = JSON.parseObject(json);
		return obj;
	}
	
	public SysResult token(String token,Object obj) {
		String new_token = updateToken(token);
		if(new_token == null) {
			return new SysResult(1,null,"令牌过期,请重新登录",false,null);
		}else {
			return new SysResult(0,new_token,"success",false,obj);
		}
	}
}
