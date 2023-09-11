package com.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cloud.bean.User;
import com.cloud.config.VerifyToken;
import com.cloud.service.UserService;
import com.cloud.util.SysResult;

@Controller
@ResponseBody
@RequestMapping("/user")
@CrossOrigin
public class UserController {
	private static Logger log = LoggerFactory.getLogger("USER");
	@Autowired
	private UserService userService;
	
	@RequestMapping("/list")
	public SysResult list(String token,Integer page) {
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			result = userService.queryAllUser(page);
		}else {
			User user = (User)result.getData();
			result = userService.queryAllUser(page,user);
		}
		result.setToken(token);
		return result;
	}
	
	@RequestMapping("/info")
	public SysResult user(String token) {
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			if(result.getData() == null) {
				result = new SysResult(-1,token,"令牌过期",false,null);
			}else {
				String phone = (String)result.getData();
				result = userService.selectAdmin(phone);
			}
		}else {
			User user = (User)result.getData();
			result = new SysResult(0,token,"",true,user);
		}
		result.setToken(token);
		return result;
	}
	
	@RequestMapping("/delete")
	public SysResult delete(String token,Integer id) {
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			result = userService.deleteUser(id);
		}else {
			result = new SysResult(2,token,"无权操作",false,null);
		}
		result.setToken(token);
		return result;
	}
	
	@RequestMapping("/update")
	public SysResult update(String token,Integer id,Integer viplevel,Integer agentlevel,Double balance,Integer status,Integer pid,String password) {
		log.info("更新参数: {}-{}-{}-{}-{}-{}-{}-{}",token,id,viplevel,agentlevel,balance,status,pid,password);
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			result = userService.updateUserInfo(id,viplevel,agentlevel,balance,status,pid,password);
		}else {
			result = new SysResult(2,token,"无权操作",false,null);
		}
		result.setToken(token);
		return result;
	}
}
