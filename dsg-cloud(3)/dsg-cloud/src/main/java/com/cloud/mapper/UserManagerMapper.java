package com.cloud.mapper;

import java.util.ArrayList;

import com.cloud.bean.Level;
import com.cloud.bean.User;

public interface UserManagerMapper {
	
	ArrayList<Level> selectListLevel();
	
	void updateUser(User user);

	void deleteUserById(Integer id);

	ArrayList<User> selectUserByPid(Integer id);

	void updateUserStatus(User user);
}
