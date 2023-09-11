package com.cloud.service;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cloud.bean.Admin;
import com.cloud.bean.Level;
import com.cloud.bean.User;
import com.cloud.config.VerifyToken;
import com.cloud.mapper.UserManagerMapper;
import com.cloud.mapper.UserMapper;
import com.cloud.util.PageUtil;
import com.cloud.util.SecUtil;
import com.cloud.util.SysResult;

@Service
public class UserService {
	
	private static Logger log = LoggerFactory.getLogger("USER");
	
	@Autowired
	private UserManagerMapper userManagerMapper;
	
	@Autowired
	private UserMapper userMapper;
	/**
	 * 管理员查询所有用户
	 * @param page
	 * @return
	 */
	public SysResult queryAllUser(Integer page) {
		if(page == null) {
			page = 1;
		}
		Integer count = userMapper.selectCount();
		PageUtil<User> pageUtil = new PageUtil<User>(20,count,page);
		int start = pageUtil.getStart();
		int end = pageUtil.getEnd();
		ArrayList<User> list = userMapper.selectByPage(start,end);
		pageUtil.setList(list);
		return new SysResult(0,"success",true,pageUtil);
	}
	/**
	 * 用户查询下级用户信息
	 * @param page
	 * @return
	 */
	public SysResult queryAllUser(Integer page,User user) {
		if(page == null) {
			page = 1;
		}
		Integer count = userMapper.selectCountUser(user.getId());
		PageUtil<User> pageUtil = new PageUtil<User>(20,count,page);
		int start = pageUtil.getStart();
		int end = pageUtil.getEnd();
		ArrayList<User> list = userMapper.selectByPageUser(user.getId(),start,end);
		VerifyToken.encodeUser(list);
		pageUtil.setList(list);
		return new SysResult(0,"success",true,pageUtil);
	}
	
	public SysResult queryUserByLevel(Integer page) {
		ArrayList<Level> level = userManagerMapper.selectListLevel();
		return new SysResult(0,"success",true,level);
	}

	public SysResult updateUserInfo(Integer id, Integer viplevel, Integer agentlevel, Double balance, Integer status,
			Integer pid, String password) {
		User user = userMapper.selectById(id);
		if(StringUtils.hasLength(viplevel.toString())) {
			user.setViplevel(viplevel);
		}
		if(StringUtils.hasLength(agentlevel.toString())) {
			user.setAgentlevel(agentlevel);
		}
		if(StringUtils.hasLength(balance.toString())) {
			user.setBalance(balance);
		}
		if(StringUtils.hasLength(status.toString())) {
			if(status == 0) {log.info("解冻/激活用户,用户ID={}",id);
				//解冻本人
				user.setStatus(0);
			}else if(status == 1) {log.info("冻结用户,用户ID={}",id);
				//冻结本人
				user.setStatus(1);
			}else if(status ==2) {log.info("冻结用户及下级,用户ID={}",id);
				//冻结本人及下级
				ArrayList<User> list = userManagerMapper.selectUserByPid(id);
				for(User u : list) {
					u.setStatus(1);
					userManagerMapper.updateUserStatus(u);
				}
			}else if(status == 3) {log.info("解冻/激活用户及下级,用户ID={}",id);
				//解冻本人及下级
				ArrayList<User> list = userManagerMapper.selectUserByPid(id);
				for(User u : list) {
					u.setStatus(0);
					userManagerMapper.updateUserStatus(u);
				}
			}
		}
		if(StringUtils.hasLength(pid.toString())) {
			User up_user = userMapper.selectById(pid);
			if(up_user != null) {
				user.setPid(pid);
			}else {
				log.error("此上级用户不存在 pid={}",pid);
			}
		}
		if(StringUtils.hasLength(password)) {
			try {
				password = SecUtil.MD5(password);
			} catch (Exception e) {
				log.error("MD5 密码检验出错");
			}
			user.setPassword(password);
		}
		userManagerMapper.updateUser(user);
		user.setPassword("NULL");
		return new SysResult(0,"success",true,user);
	}

	public SysResult deleteUser(Integer id) {
		User user = userMapper.selectById(id);
		if(user == null) {
			return new SysResult(1,"用户不存在",false,null);
		}
		userManagerMapper.deleteUserById(id);
		log.info("删除用户 用户ID={}",id);
		//return queryAllUser(page);
		user.setPassword("NULL");
		return new SysResult(0,"",true,user);
	}
	public SysResult selectAdmin(String phone) {
		Admin admin = userMapper.selectAdminByPhone(phone);
		return new SysResult(0,"",true,admin);
	}

}
