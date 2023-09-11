package com.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cloud.bean.User;
import com.cloud.config.VerifyToken;
import com.cloud.service.DriverService;
import com.cloud.util.SysResult;

@Controller
@ResponseBody
@RequestMapping("/driver")
@CrossOrigin
public class DriverController {
	
	@Autowired
	private DriverService driverService;
	
	@RequestMapping("/list")
	public SysResult driverList(String token,Integer page){
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			result = driverService.getDriverList(page);
		}else {
			User user = (User)result.getData();
			result = driverService.getDriverList(page,user);
		}
		result.setToken(token);
		return result;
	}
	
	@PostMapping("/set")
	public SysResult setting(String token,String gjnum,String ipnum) {
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			driverService.set(gjnum,ipnum);
			return new SysResult(0,result.getToken(),"设置成功",true,null);
		}else {
			return new SysResult(2,result.getToken(),"无权设置",false,null);
		}
	}
	
	@RequestMapping("/note")
	public SysResult note(String token,String content) {
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			driverService.note(content);
			return new SysResult(0,result.getToken(),"设置成功",true,null);
		}else {
			return new SysResult(2,result.getToken(),"无权设置",true,null);
		}
	}
}
