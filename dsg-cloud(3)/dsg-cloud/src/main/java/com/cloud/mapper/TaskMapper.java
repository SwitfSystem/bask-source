package com.cloud.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cloud.bean.Task;
import com.cloud.bean.TaskRecord;

@Mapper
public interface TaskMapper {

	void insertTask(TaskRecord taskRecord);

	TaskRecord selectTask(Integer task_id);

	void updateTask(TaskRecord t);

	Integer selectTaskRecordByCount();

	ArrayList<TaskRecord> selectAllTaskRecord(@Param("start")Integer start, @Param("end")Integer end);

	Integer selectTaskRecordByCountAndStatus(Integer status);

	ArrayList<TaskRecord> selectAllTaskRecordByStatus(@Param("start")Integer start, @Param("end")Integer end, @Param("status")Integer status);

	Integer insertTaskUser(Task task);
	
	void updateTaskUser(Task task);

	Integer selectTaskByCountAndStatus(@Param("phone")String phone,@Param("status")Integer status);

	ArrayList<Task> selectAllTaskByStatus(@Param("phone")String phone, @Param("start")Integer start, @Param("end")Integer end, @Param("status")Integer status);

	Integer selectTaskByCount(String phone);

	ArrayList<Task> selectAllTask(@Param("phone")String phone, @Param("start")Integer start, @Param("end")Integer end);

	ArrayList<Task> selectAllTaskToBill(@Param("phone")String phone, @Param("status")Integer status);
}
