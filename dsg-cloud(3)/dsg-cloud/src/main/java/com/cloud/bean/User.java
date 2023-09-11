package com.cloud.bean;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;

public class User implements Serializable {
    private static final long serialVersionUID = -17671979085160933L;

    private Integer id;

    private String username;

    private String password;
    /**
     * 代理等级
     */
    private Integer agentlevel;
    /**
     * vip等级
     */
    private Integer viplevel;
    /**
     * 创建时间
     */
    private String createtime;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 上级id
     */
    private Integer pid;
    /**
     * 登录失败次数
     */
    private Integer loginfailure;
    /**
     * 登录ip
     */
    private String loginip;
    /**
     * 0:正常 1:停用
     */
    private Integer status;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String updatetime;
    /**
     * seesion标识
     */
    private String token;
    /**
     * 邀请码
     */
    private String invitationcode;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 余额
     */
    private Double balance;
    
    private String imageCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAgentlevel() {
		return agentlevel;
	}

	public void setAgentlevel(Integer agentlevel) {
		this.agentlevel = agentlevel;
	}

	public Integer getViplevel() {
		return viplevel;
	}

	public void setViplevel(Integer viplevel) {
		this.viplevel = viplevel;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getLoginfailure() {
		return loginfailure;
	}

	public void setLoginfailure(Integer loginfailure) {
		this.loginfailure = loginfailure;
	}

	public String getLoginip() {
		return loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getInvitationcode() {
		return invitationcode;
	}

	public void setInvitationcode(String invitationcode) {
		this.invitationcode = invitationcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}
    
    
}
