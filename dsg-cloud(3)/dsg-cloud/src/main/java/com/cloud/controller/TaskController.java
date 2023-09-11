package com.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.cloud.bean.User;
import com.cloud.config.VerifyToken;
import com.cloud.service.TaskService;
import com.cloud.service.TokenService;
import com.cloud.util.SysResult;

@Controller
@ResponseBody
@RequestMapping("/task")
@CrossOrigin
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private TokenService tokenService;
	
	private static Logger log = LoggerFactory.getLogger(TaskController.class);
	
	@RequestMapping("/list")
	public SysResult list(String token,Integer page,Integer status) {
		SysResult result = VerifyToken.verify(token);
		int code = result.getCode();
		token = result.getToken();
		if(code == -1) {
			return result;
		}else if(code == 0) {
			result = taskService.allTask(page,status);
		}else {
			User user = (User)result.getData();
			result = taskService.allTask(page,status,user);
		}
		result.setToken(token);
		return result;
	}
	//执行任务
	
	@RequestMapping("/execute")
	public SysResult execute(String token,Integer platform,String content,String code,Integer num,Integer method,Long time) {
		log.info("发布任务: {}-{}-{}-{}-{}-{}",platform,content,code,num,method,time);
		SysResult result = VerifyToken.verify(token);
		int code1 = result.getCode();
		token = result.getToken();
		if(code1 == -1) {
			return result;
		}else if(code1 == 1) {
			return new SysResult(2,token,"无权操作",false,null);
		}
		JSONObject json = tokenService.parseObject(token);
		Integer id = json.getInteger("id");
		result = taskService.newTask(id,platform,content,code,num,method,time);
		result.setToken(token);
		return result;
	}
	//实时刷新任务列表
	@RequestMapping("/flush")
	public SysResult flushTask(String token) {
		SysResult result = VerifyToken.verify(token);
		int code1 = result.getCode();
		token = result.getToken();
		if(code1 == -1) {
			return result;
		}else if(code1 == 1) {
			return new SysResult(2,token,"无权操作",false,null);
		}
		result = taskService.flush();
		result.setToken(token);
		return result;
	}
	
	//客户端返回执行结果
	@RequestMapping("/result")
	public void result(String token,String phone,Integer code) {
		
		taskService.updateTask(phone,token,code);
	}
}
