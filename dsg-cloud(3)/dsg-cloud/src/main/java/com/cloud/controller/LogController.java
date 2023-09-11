package com.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cloud.config.VerifyToken;
import com.cloud.service.LogService;
import com.cloud.util.SysResult;

@Controller
@ResponseBody
@RequestMapping("/log")
@CrossOrigin
public class LogController {
	
	@Autowired
	private LogService logService;
	
	@RequestMapping("/list")
	public SysResult getLogs(String token, String log, Integer page) {
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 1) {
			return new SysResult(2,token,"无权查看",false,null);
		}
		switch(log) {
			case "app":
				result = logService.logList(log, page);
				break;
			case "bill":
				result = logService.logList(log, page);
				break;
			case "tx":
				result = logService.logList(log, page);
				break;
			default:
				result = logService.logList("app", page);
				break;
		}
		result.setToken(token);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/download")
	public SysResult getLog(String token,Integer id) {
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 1) {
			return new SysResult(2,token,"无权查看",false,null);
		}
		result = logService.log(id);
		result.setToken(token);
		return result;
	}
}
