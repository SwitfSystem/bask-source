package com.cloud.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cloud.bean.AgentSetting;
import com.cloud.bean.User;
import com.cloud.bean.UserSetting;
import com.cloud.service.GroupService;
import com.cloud.service.TokenService;
import com.cloud.util.SysResult;

@Component
public class VerifyToken {
	
	@Autowired
	private static TokenService tokenService;
	
	@Autowired
	private static GroupService groupService;
	
	/**
	 * 
	 * @param token
	 * @return -1=过期 0=管理员 1=用户
	 */
	public static SysResult verify(String token) {
		if(!StringUtils.hasLength(token)) {
			return new SysResult(-1,"","令牌过期,请重新登录",false,null);
		}
		if(token.startsWith("A")) {
			String new_token = tokenService.updateAdmin(token);
			if(new_token == null) {
				return new SysResult(-1,"","令牌过期,请重新登录",false,null);
			}
			String phone = tokenService.getAdminInfo(new_token);
			return new SysResult(0,new_token,"",true,phone);
		}else {
			User user = tokenService.getUser(token);
			if(user == null) {
				return new SysResult(-1,"","令牌过期,请重新登录",false,null);
			}
			return new SysResult(1,token,"",true,user);
		}
	}
	/**
	 * 加密用户信息
	 * @param list
	 */
	public static void encodeUser(ArrayList<User> list) {
		ArrayList<User> newList = new ArrayList<User>();
		for(User u:list) {
			u.setPhone(encodePhone(u.getPhone()));
			u.setUsername(encodeName(u.getUsername()));
			u.setPassword(null);
			u.setBalance(null);
			u.setId(null);
			u.setInvitationcode(null);
			u.setLoginip(null);
			u.setEmail(null);
			u.setImageCode(null);
			u.setPid(null);
			u.setToken(null);
			u.setUpdatetime(null);
			newList.add(u);
		}
		list = newList;
	}
	
	private static String encodePhone(String phone) {
		String temp = phone.substring(3, 7);
		phone = phone.replace(temp, "****");
		return phone;
	}
	private static String encodeName(String name) {
		char[] c = new char[1];
		c[0] = name.charAt(0);
		name = new String(c) + "***";
		return name;
	}
	/**
	 * 计算佣金
	 * @param agentlevel
	 * @param viplevel
	 * @param type 0=挂机 1=开通VIP 2=开通代理
	 * @return
	 */
	public static Double computer(Integer agentlevel, Integer viplevel, Integer type) {
		//AgentSetting a = groupService.getAgentConfig();
		//UserSetting u = groupService.getVipCongig();
		Double d = 0d;
		switch (type) {
			case 0:
				
				break;
			case 1:
				
				break;
			case 2:
	
				break;
			default:
				break;
		}
		return d;
	}
}
