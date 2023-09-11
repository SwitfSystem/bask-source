package com.cloud.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cloud.bean.Admin;
import com.cloud.bean.User;
@Mapper
public interface UserMapper {
	
	User selectById(Integer id);
	
	User selectByName(String username);
	
	void insert(User user);

	User selectByPhone(String phone);

	User selectByInvitationcode(String invitationcode);

	String selectOrderByInvitationcode();

	ArrayList<User> selectByPage(@Param("start")Integer start, @Param("end")Integer end);
	
	ArrayList<User> selectByPageUser(@Param("pid")Integer pid, @Param("start")Integer start, @Param("end")Integer end);

	Integer selectCount();
	
	Integer selectCountUser(Integer pid);

	ArrayList<User> selectByAgentLevel(@Param("start")Integer start, @Param("end")Integer end);
	
	ArrayList<User> selectByAgentLevelUser(@Param("pid")Integer pid, @Param("start")Integer start, @Param("end")Integer end);

	ArrayList<String> selectByInvitate(@Param("start")Integer start, @Param("end")Integer end);
	
	ArrayList<String> selectByInvitateUser(@Param("id")Integer id, @Param("start")Integer start, @Param("end")Integer end);

	Admin selectAdminByPhone(String phone);

	User selectAgentByPhone(String phone);

	User selectAgentByName(String username);

	void updateBalance(User user);
	
}
