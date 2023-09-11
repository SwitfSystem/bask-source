package com.cloud.bean;

public class Bill {
	private Integer id;
	private Integer user_id;
	private String phone;
	private double money; //提现金额
	private double balance; //余额
	private String time; //提现时间
	private String alipay; //支付宝号
	private String wxpay; //微信号
	private Integer status; //审核状态 0=通过  1=未通过  2=正在审核
	private String reason; //未通过原因
	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAlipay() {
		return alipay;
	}
	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}
	public String getWxpay() {
		return wxpay;
	}
	public void setWxpay(String wxpay) {
		this.wxpay = wxpay;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "Bill [id=" + id + ", phone=" + phone + ", money=" + money + ", balance=" + balance + ", time=" + time
				+ ", alipay=" + alipay + ", wxpay=" + wxpay + ", status=" + status + ", reason=" + reason + "]";
	}
	
}
