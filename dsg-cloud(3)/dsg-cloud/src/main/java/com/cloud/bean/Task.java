package com.cloud.bean;

public class Task {
	private Integer id;
	private String username;
	private String phone;
	private String token;
	private Integer platform;
	private String mac;
	private String ip;
	private String os;
	private String content; //任务内容
	private Integer code; //任务代码
	private Integer status; //任务状态
	private String starttime; //任务执行时间
	private String endtime; //任务结束时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	
	public Integer getPlatform() {
		return platform;
	}
	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	@Override
	public String toString() {
		return "Task [id=" + id + ", username=" + username + ", phone=" + phone + ", token=" + token + ", platform="
				+ platform + ", mac=" + mac + ", ip=" + ip + ", os=" + os + ", content=" + content + ", code=" + code
				+ ", status=" + status + ", starttime=" + starttime + ", endtime=" + endtime + "]";
	}
}
