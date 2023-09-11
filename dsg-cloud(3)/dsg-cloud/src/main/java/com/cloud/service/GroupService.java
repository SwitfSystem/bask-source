package com.cloud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.bean.AgentSetting;
import com.cloud.bean.UserSetting;
import com.cloud.mapper.GroupMapper;
import com.cloud.util.SysResult;

@Service
public class GroupService {
	
	private static Logger log = LoggerFactory.getLogger("GROUP");
	
	@Autowired
	private GroupMapper groupMapper;

	public SysResult setUserInfo(String name, Double bill, Double price, Integer time, Integer type) {
		log.info("修改用户配置 name={} bill={} price={} time={} type={}",name,bill,price,time,type);
		UserSetting set = new UserSetting(name,bill,price,time,type);
		groupMapper.insertUserSet(set);
		return new SysResult(0,"success",true,null);
	}

	public SysResult setAgentInfo(String name, Double dlgj, Double dlvip, Double dldl, Double dlprice,
			Integer invitenum) {
		log.info("修改代理配置 name={} dlgj={} dlvip={} dldl={} dlprice={} invitenum={}",name,dlgj,dlvip,dldl,dlprice,invitenum);
		AgentSetting set = new AgentSetting(name,dlgj,dlvip,dldl,dlprice,invitenum);
		groupMapper.intertAgentSet(set);
		return new SysResult(0,"success",true,null);
	}
	
	public SysResult deleteUserInfo(UserSetting set) {
		log.info("删除用户配置 usersetting={}",set);
		groupMapper.deleteUserSet(set);
		return new SysResult(0,"",true,null);
	}
	
	public SysResult deleteAgentInfo(AgentSetting set) {
		log.info("删除代理配置 agentsetting={}",set);
		groupMapper.deleteAgentSet(set);
		return new SysResult(0,"",true,null);
	}
}
