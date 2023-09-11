package com.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cloud.config.VerifyToken;
import com.cloud.service.GroupService;
import com.cloud.util.SysResult;

@Controller
@ResponseBody
@RequestMapping("/group")
@CrossOrigin
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	@RequestMapping("/user")
	public SysResult user(String token,String name,Double bill,Double price,Integer time,Integer type) {
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			result = groupService.setUserInfo(name,bill,price,time,type);
		}else {
			result = new SysResult(2,token,"无权设置",false,null);
		}
		result.setToken(token);
		return result;
	}
	
	@RequestMapping("/agent")
	public SysResult agent(String token,String name,Double dlgj,Double dlvip,Double dldl,Double dlprice,Integer invitenum) {
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			result = groupService.setAgentInfo(name,dlgj,dlvip,dldl,dlprice,invitenum);
		}else {
			result = new SysResult(2,token,"无权设置",false,null);
		}
		result.setToken(token);
		return result;
	}
	
	@RequestMapping("/deluser")
	public SysResult delUser() {
		return groupService.deleteUserInfo(null);
	}
	
	@RequestMapping("/delagent")
	public SysResult delAgent() {
		return groupService.deleteAgentInfo(null);
	}
}
