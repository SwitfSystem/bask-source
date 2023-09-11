package com.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cloud.bean.User;
import com.cloud.config.VerifyToken;
import com.cloud.service.AgentService;
import com.cloud.util.SysResult;

@RequestMapping("/agent")
@Controller
@ResponseBody
@CrossOrigin
public class AgentController {
	
	@Autowired
	private AgentService agentService;
	
	@RequestMapping("/list")
	public SysResult list(String token, Integer page) {
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			result = agentService.queryAll(page);
		}else {
			User user = (User)result.getData();
			result = agentService.queryAll(page, user);
		}
		result.setToken(token);
		return result;
	}
	
	@RequestMapping("/invitecode")
	public SysResult invitate(String token, Integer page) {
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			result = agentService.queryInvitate(page);
		}else {
			User user = (User)result.getData();
			result = agentService.queryInvitate(page,user);
		}
		result.setToken(token);
		return result;
	}
	
	@RequestMapping("/update")
	public SysResult update(String token,Integer id,Integer viplevel,Integer agentlevel,Integer balance) {
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			result = agentService.update(id,viplevel,agentlevel,balance);
			result.setToken(token);
		}else {
			return new SysResult(2,token,"没有权限修改",false,null);
		}
		return result;
	}
}
