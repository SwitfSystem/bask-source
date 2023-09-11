package com.cloud.bean;

import java.util.Date;

// 任务执行记录
public class TaskRecord {
	private Integer id;
	private String task_id;
	private Date task_start_time;
	private Date task_end_time;
	private Integer task_create_user;
	private Integer task_status;
	private String task_byexecute_user;
	private Integer task_user_count;
	private Integer task_complete_count;
	private String task_prameter;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public Date getTask_start_time() {
		return task_start_time;
	}
	public void setTask_start_time(Date task_start_time) {
		this.task_start_time = task_start_time;
	}
	public Date getTask_end_time() {
		return task_end_time;
	}
	public void setTask_end_time(Date task_end_time) {
		this.task_end_time = task_end_time;
	}
	public Integer getTask_create_user() {
		return task_create_user;
	}
	public void setTask_create_user(Integer task_create_user) {
		this.task_create_user = task_create_user;
	}
	public Integer getTask_status() {
		return task_status;
	}
	public void setTask_status(Integer task_status) {
		this.task_status = task_status;
	}
	public String getTask_byexecute_user() {
		return task_byexecute_user;
	}
	public void setTask_byexecute_user(String task_byexecute_user) {
		this.task_byexecute_user = task_byexecute_user;
	}
	public Integer getTask_user_count() {
		return task_user_count;
	}
	public void setTask_user_count(Integer task_user_count) {
		this.task_user_count = task_user_count;
	}
	public Integer getTask_complete_count() {
		return task_complete_count;
	}
	public void setTask_complete_count(Integer task_complete_count) {
		this.task_complete_count = task_complete_count;
	}
	public String getTask_prameter() {
		return task_prameter;
	}
	public void setTask_prameter(String task_prameter) {
		this.task_prameter = task_prameter;
	}
	@Override
	public String toString() {
		return "TaskRecord [id=" + id + ", task_id=" + task_id + ", task_start_time=" + task_start_time
				+ ", task_end_time=" + task_end_time + ", task_create_user=" + task_create_user + ", task_status="
				+ task_status + ", task_byexecute_user=" + task_byexecute_user + ", task_user_count=" + task_user_count
				+ ", task_complete_count=" + task_complete_count + ", task_prameter=" + task_prameter + "]";
	}
	
}
