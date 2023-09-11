package com.cloud.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cloud.service.LoginService;
import com.cloud.util.CookieUtil;
import com.cloud.util.SysResult;

@Controller
@CrossOrigin
public class LoginController {
	private static Logger log = LoggerFactory.getLogger("SYSTEM");
	@Autowired
	private LoginService loginService;
	
	@ResponseBody
	@PostMapping("/admin")
	public SysResult admin(String phone,String password,String code,String time,String remember,HttpServletRequest request,HttpServletResponse response) {
		if(!StringUtils.hasLength(phone) || !StringUtils.hasLength(password)) {
			return new SysResult(1,"账号或密码不能为空",false,null);
		}
		if(!StringUtils.hasLength(code) || !StringUtils.hasLength(time)) {
			return new SysResult(1,"验证码不能为空",false,null);
		}
		code = code.toLowerCase();
		SysResult result = loginService.admin(phone,password,code,time,remember,request,response);
		return result;
	}
	
	@ResponseBody
	@GetMapping("/")
	public SysResult main(HttpServletRequest request,HttpServletResponse response){
//		boolean result = loginService.loginCheck(request,response,mode);
//		if(!result) {
//			log.error("用户未登录或登录超时");
//			return "login";
//		}
//		return "index";
		boolean result = check(request);
		if(result) {
			return SysResult.OK();
		}
		return new SysResult(1,"",false,null);
	}
	
//	@ResponseBody
//	@GetMapping("/login")
//	public String login(String token){
//		log.info("登录页面...");
//		return "login";
//	}
	
	@ResponseBody
	@PostMapping("/login")
	public SysResult loginverify(String phone,String password,String code,String time,String remember,HttpServletRequest request,HttpServletResponse response) {
		log.info("用户信息是: {}-{}-{}-{}-{}",phone,password,code,time,remember);
		if(!StringUtils.hasLength(phone) || !StringUtils.hasLength(password)) {
			return new SysResult(1,"账号或密码不能为空",false,null);
		}
		if(!StringUtils.hasLength(code) || !StringUtils.hasLength(time)) {
			return new SysResult(1,"验证码不能为空",false,null);
		}
		code = code.toLowerCase();
		SysResult result = loginService.login(phone,password,code,time,remember,request,response);
		return result;
	}
	
	@ResponseBody
	@PostMapping("/register")
	public SysResult register(String phone,String username,String password,String invitecode,String code,String time,HttpServletRequest request) {
		if(!StringUtils.hasLength(phone) || !StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
			return new SysResult(1,"必填信息不能为空",false,null);
		}
		if(!StringUtils.hasLength(code) || !StringUtils.hasLength(time)) {
			return new SysResult(1,"验证码不能为空",false,null);
		}
		code = code.toLowerCase();
		SysResult result = loginService.register(phone,username,password,invitecode,code,time,request);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/logout")
	public SysResult logout(String token, String phone,HttpServletRequest request,HttpServletResponse response) {
		CookieUtil.deleteCookie(request, response, "DSG_CLOUD");
		CookieUtil.deleteCookie(request, response, "token");
		request.getSession().removeAttribute("DSG_CLOUD");
		return loginService.logout(phone,token);
	}
	
	@ResponseBody
	@GetMapping("/code")
	public SysResult code() {
		SysResult result = loginService.getCode();
		return result;
	}
	
	private boolean check(HttpServletRequest request) {
		String value = CookieUtil.getCookieValue(request, "token", true);
		if(value == null) {
			return false;
		}
		return true;
	}
}
