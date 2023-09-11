package com.cloud.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.bean.AgentSetting;
import com.cloud.bean.UserSetting;

@Mapper
public interface GroupMapper {

	void insertUserSet(UserSetting set);

	void intertAgentSet(AgentSetting set);
	
	void updateUserSet(UserSetting set);
	
	void updateAgentSet(AgentSetting set);
	
	void deleteUserSet(UserSetting set);
	
	void deleteAgentSet(AgentSetting set);
}
