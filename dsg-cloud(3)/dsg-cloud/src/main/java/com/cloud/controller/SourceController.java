package com.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cloud.service.SourceService;
import com.cloud.service.TokenService;
import com.cloud.util.SysResult;

@Controller
@ResponseBody
@RequestMapping("/source")
@CrossOrigin(originPatterns = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class SourceController {
	
	@Autowired
	private SourceService sourceService;
	
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping("/get")
	public SysResult get(String token,String type,Integer page) {
		String new_token = tokenService.updateAdmin(token);
		if(new_token == null) {
			return new SysResult(-1,"令牌过期,请重新登录",false,null);
		}
		SysResult result = sourceService.get(type,page);
		result.setToken(new_token);
		return result;
	}
	
	@RequestMapping("/add")
	public SysResult add(String token,String type,String name,String comment) {
		String new_token = tokenService.updateAdmin(token);
		if(new_token == null) {
			return new SysResult(-1,"令牌过期,请重新登录",false,null);
		}
		SysResult result = sourceService.add(type,name,comment);
		result.setToken(new_token);
		return result;
	}
}
