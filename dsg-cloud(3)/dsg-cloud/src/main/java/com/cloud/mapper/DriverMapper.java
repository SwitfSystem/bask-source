package com.cloud.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cloud.bean.Driver;
import com.cloud.bean.Note;

@Mapper
public interface DriverMapper {

	ArrayList<Driver> selectAll(@Param("start")Integer start, @Param("end")Integer end);
	
	Driver selectDriverByPhone(String phone);

	void updateSet(@Param("gjnum")String gjnum, @Param("ipnum")String ipnum);

	void insertNote(Note note);

	Driver selectDriverByName(String username);

	ArrayList<Driver> selectAllDriverByStatus(Integer status);

	Integer selectDriverByCount();

	void insertDriver(Driver driver);

	void updateDriver(Driver driver);

	Integer selectDriverByCountStatus(Integer status);

	ArrayList<Driver> selectDriverByStatus(@Param("start")Integer start, @Param("end")Integer end, @Param("status")Integer status);

}
