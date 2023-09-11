package com.cloud.service;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.cloud.bean.Admin;
import com.cloud.bean.User;
import com.cloud.mapper.UserMapper;
import com.cloud.util.CookieUtil;
import com.cloud.util.DateUtils;
import com.cloud.util.RedisUtil;
import com.cloud.util.SecUtil;
import com.cloud.util.SysResult;
import com.cloud.util.VerifyCodeUtil;
import com.cloud.util.YQMUtils;

@Service
public class LoginService {
	
	private static Logger log = LoggerFactory.getLogger("LOGIN");
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserMapper userMapper;
	/**
	 * 登陆验证
	 * @param phone		手机
	 * @param password	密码
	 * @param code		验证码
	 * @param remember	记住我
	 * @return
	 */
	public SysResult login(String phone, String password, String code, String verificatcode, String remember,HttpServletRequest request,HttpServletResponse response) {
		log.info("用户登陆请求信息 phone="+phone+" password="+password+" code="+code+" verificatcode="+verificatcode+" remember="+remember);
		String object = (String)redisUtil.get("CODE_"+verificatcode);
		try {
			if(object == null) {
				return new SysResult(1,"验证码超时",false,null);
			}
			object = object.toLowerCase();
			if(!code.equals(object)) {
				return new SysResult(11,"验证码错误",false,null);
			}
		} catch (Exception e) {
			log.error("获取验证码出错 用户是={}",phone);
			return new SysResult(11,"验证码错误",false,null);
		}
		User user = userMapper.selectByPhone(phone);
		if(user == null) {
			return new SysResult(1,"用户账号或密码错误",false,null);
		}
		String pwd = "";
		try {
			pwd = SecUtil.MD5(password);
		} catch (Exception e) {
			log.error("用户密码MD5出错 用户是={}",phone);
			e.printStackTrace();
			return new SysResult(1,"密码格式错误",false,null);
		}
		if(!pwd.equals(user.getPassword())) {
			return new SysResult(1,"用户账号或密码错误",false,null);
		}
		if(remember != null) {
			CookieUtil.setCookie(request, response, "DSGCLOUD", phone, true);
		}else {
			CookieUtil.setCookie(request, response, "DSGCLOUD", phone, 60*30,true);
		}
		//request.getSession().setAttribute("DSG_CLOUD", phone);
		log.info("用户登录成功 用户是={}",phone);
		String token = tokenService.setUser(phone,user);
		user.setToken(token);
		user.setPassword("NULL");
		return new SysResult(0,token,"登陆成功",true,user);
	}
	/**
	 * 设置验证码到redis,保存60秒
	 * @return
	 */
	public SysResult getCode() {
		VerifyCodeUtil verify = new VerifyCodeUtil();
		String encode = verify.drawImage();
		String code = verify.getCode();
		long time = System.currentTimeMillis();
		boolean setnx = redisUtil.setnx("CODE_"+time, code, 60);
		log.info("验证码="+code+" setnx="+setnx);
		return new SysResult(0,""+time,true,encode);
	}
	/**
	 * 注册
	 * @param phone
	 * @param username
	 * @param password
	 * @param invitationcode
	 * @param code
	 * @param verificatcode
	 * @param request
	 * @return
	 */
	public SysResult register(String phone, String username, String password, String invitationcode, String code, String verificatcode,HttpServletRequest request) {
		log.info("用户注册请求信息 phone="+phone+" password="+password+" code="+code+" verificatcode="+verificatcode+" invitationcode="+invitationcode);
		String object = (String)redisUtil.get("CODE_"+verificatcode);
		try {
			if(object == null) {
				return new SysResult(11,"验证码超时",false,null);
			}
			object = object.toLowerCase();
			if(!code.equals(object)) {
				return new SysResult(11,"验证码错误",false,null);
			}
		} catch (Exception e) {
			log.error("获取验证码出错 用户是={}",phone);
			return new SysResult(11,"验证码错误",false,null);
		}
		User user = userMapper.selectByPhone(phone);
		if(user != null) {
			return new SysResult(1,"用户已存在",false,null);
		}
		try {
			password = SecUtil.MD5(password);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("用户密码MD5出错 用户是={}",phone);
			return new SysResult(1,"密码格式错误",false,null);
		}
		user = new User();
		synchronized (LoginService.class) {
			if(StringUtils.hasLength(invitationcode)) {
				User oldUser = userMapper.selectByInvitationcode(invitationcode);
				if(oldUser == null) {
					return new SysResult(1,"邀请码不存在",false,null);
				}else {
					user.setPid(oldUser.getId());
					String last = userMapper.selectOrderByInvitationcode();
					user.setInvitationcode(YQMUtils.getString(1,last));
				}
			}else {
				String last = userMapper.selectOrderByInvitationcode();
				user.setInvitationcode(YQMUtils.getString(1,last));
				user.setPid(0);
			}
		}
		user.setPhone(phone);
		user.setUsername(username);
		user.setPassword(password);
		user.setViplevel(1);
		user.setAgentlevel(1);
		user.setCreatetime(DateUtils.date2String(new Date(), "yyyy-MM-dd"));
		user.setStatus(0);
		user.setLoginip(request.getRemoteAddr());
		user.setBalance(0d);
		user.setUpdatetime(DateUtils.date2String(new Date(), "yyyy-MM-dd"));
		userMapper.insert(user);
		log.info("用户注册成功 用户是={}",phone);
		return new SysResult(0,"注册成功",true,null);
	}
	/**
	 * 验证token
	 * @param token
	 * @return
	 */
	public SysResult checkToken(String token) {
		Object object = redisUtil.get(token);
		if(object == null) {
			new SysResult(1,"token不存在",false,null);
		}
		return new SysResult(0,token,true,object);
	}
	/**
	 * 登录验证
	 * @param request
	 * @param response
	 * @param mode
	 * @return
	 */
//	public boolean loginCheck(HttpServletRequest request,HttpServletResponse response, ModelMap mode) {
//		String cookieValue = CookieUtil.getCookieValue(request, "DSG_CLOUD", true);
//		if(cookieValue == null) {
//			Object attribute = request.getSession().getAttribute("DSG_CLOUD");
//			if(attribute == null) {
//				mode.addAttribute("msg", "登录超时");
//				return false;
//			}else {
//				CookieUtil.setCookie(request, response, "DSG_CLOUD", attribute.toString(), 60*60,true);
//			}
//		}
//		return true;
//	}
	public SysResult admin(String phone, String password, String code, String verificatcode, String remember, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("管理员登录信息 phone={} password={} code={} verificatcode={} remember={}",phone,password,code,verificatcode,remember);
		String object = (String)redisUtil.get("CODE_"+verificatcode);
		try {
			if(object == null) {
				return new SysResult(1,"验证码超时",false,null);
			}
			object = object.toLowerCase();
			if(!code.equals(object)) {
				return new SysResult(11,"验证码错误",false,null);
			}
		} catch (Exception e) {
			log.error("获取验证码出错 用户是={}",phone);
			return new SysResult(11,"验证码错误",false,null);
		}
		Admin admin = userMapper.selectAdminByPhone(phone);
		if(admin == null) {
			return new SysResult(1,"用户账号或密码错误",false,null);
		}
		try {
			password = SecUtil.MD5(password);
		} catch (Exception e) {
			log.error("用户密码MD5出错 用户是={}",phone);
			e.printStackTrace();
			return new SysResult(1,"密码格式错误",false,null);
		}
		String pwd = admin.getPassword();
		if(!pwd.equals(password)) {
			return new SysResult(1,"用户账号或密码错误",false,null);
		}
		String token = tokenService.getAdmin(phone);
		//CookieUtil.setCookie(request, response, "token", token, 60*30,true);
		if(remember != null) {
			CookieUtil.setCookie(request, response, "DSGCLOUD", phone, 60*60*24*7, true);
		}else {
			CookieUtil.setCookie(request, response, "DSGCLOUD", phone, 60*30,true);
		}
		//request.getSession().setAttribute("DSG_CLOUD", phone);
		log.info("管理员登录成功 管理员是={}",phone);
		admin.setToken(token);
		admin.setPassword("NULL");
		return new SysResult(0,token,"登陆成功",true,admin);
	}
	public SysResult logout(String phone,String token) {
		tokenService.delToken(phone,token);
		log.info("用户退出 token={}",token);
		return SysResult.OK();
	}
}
