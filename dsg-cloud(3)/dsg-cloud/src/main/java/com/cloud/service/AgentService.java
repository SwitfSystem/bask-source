package com.cloud.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.bean.User;
import com.cloud.config.VerifyToken;
import com.cloud.mapper.UserManagerMapper;
import com.cloud.mapper.UserMapper;
import com.cloud.util.PageUtil;
import com.cloud.util.SysResult;

@Service
public class AgentService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserManagerMapper userManagerMapper;
	/**
	 * 管理员查询所有代理
	 * @param page
	 * @return
	 */
	public SysResult queryAll(Integer page) {
		if(page == null) {
			page = 1;
		}
		int count = userMapper.selectCount();
		PageUtil<User> pageUtil = new PageUtil<User>(20,count,page);
		int start = pageUtil.getStart();
		int end = pageUtil.getEnd();
		ArrayList<User> list = userMapper.selectByAgentLevel(start,end);
		pageUtil.setList(list);
		return new SysResult(0,"",true,pageUtil);
	}
	/**
	 * 用户查询名下代理
	 * @param page
	 * @param user
	 * @return
	 */
	public SysResult queryAll(Integer page, User user) {
		if(page == null) {
			page = 1;
		}
		int count = userMapper.selectCountUser(user.getId());
		PageUtil<User> pageUtil = new PageUtil<User>(20,count,page);
		int start = pageUtil.getStart();
		int end = pageUtil.getEnd();
		ArrayList<User> list = userMapper.selectByAgentLevelUser(user.getId(),start,end);
		VerifyToken.encodeUser(list);
		pageUtil.setList(list);
		return new SysResult(0,"",true,pageUtil);
	}
	/**
	 * 管理员获取所有邀请码
	 * @param page
	 * @return
	 */
	public SysResult queryInvitate(Integer page) {
		if(page == null) {
			page = 1;
		}
		Integer count = userMapper.selectCount();
		PageUtil<String> pageUtil = new PageUtil<String>(20,count,page);
		int start = pageUtil.getStart();
		int end = pageUtil.getEnd();
		ArrayList<String> list = userMapper.selectByInvitate(start,end);
		pageUtil.setList(list);
		return new SysResult(0,"",true,pageUtil);
	}
	/**
	 * 用户获取自己的邀请码
	 * @param page
	 * @return
	 */
	public SysResult queryInvitate(Integer page, User user) {
		if(page == null) {
			page = 1;
		}
		Integer count = 1;
		PageUtil<String> pageUtil = new PageUtil<String>(20,count,page);
		int start = pageUtil.getStart();
		int end = pageUtil.getEnd();
		ArrayList<String> list = userMapper.selectByInvitateUser(user.getId(), start, end);
		pageUtil.setList(list);
		return new SysResult(0,"",true,pageUtil);
	}
	/**
	 * 管理员修改用户资料
	 * @param id
	 * @param viplevel
	 * @param agentlevel
	 * @param balance
	 * @return
	 */
	public SysResult update(Integer id,Integer viplevel, Integer agentlevel, Integer balance) {
		User user = userMapper.selectById(id);
		user.setViplevel(viplevel);
		user.setAgentlevel(agentlevel);
		user.setBalance((double)balance);
		userManagerMapper.updateUser(user);
		return null;
	}

}
