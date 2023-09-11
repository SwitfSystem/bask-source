package com.cloud.util;
/**
 * 一般返回结果工具
 * @author touch
 *
 */
public class SysResult {
	private Integer code; //一般0表示成功
	private String token;
	private String msg;
	private Boolean status;
	private Object data;
	public SysResult() {
		
	}
	public SysResult(Integer code,String token,String msg,Boolean status,Object data) {
		this.code = code;
		this.token = token;
		this.msg = msg;
		this.status = status;
		this.data = data;
	}
	public SysResult(Integer code,String msg,Boolean status,Object data) {
		this.code = code;
		this.msg = msg;
		this.status = status;
		this.data = data;
	}
	public static SysResult OK() {
		return new SysResult(0,"success",true,null);
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
