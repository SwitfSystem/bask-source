package com.cloud.bean;
//公告
public class Note {
	private Integer id;
	private String content;
	private String date;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Note [id=" + id + ", content=" + content + ", date=" + date + "]";
	}
	
}
