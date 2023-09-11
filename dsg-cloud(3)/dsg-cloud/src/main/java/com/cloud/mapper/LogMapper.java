package com.cloud.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cloud.bean.Log;

@Mapper
public interface LogMapper {
	
	ArrayList<Log> selectType(@Param("type")String type, @Param("start")Integer start, @Param("end")Integer end);
	
	Integer selectMd5(Integer md5);
	
	String selectOne(Integer id);
	
	Integer count();
	
	void insertOne(Log log);

	Integer selectLogCount(String type);
}
