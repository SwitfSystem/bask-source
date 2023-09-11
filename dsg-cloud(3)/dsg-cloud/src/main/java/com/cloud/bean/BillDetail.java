package com.cloud.bean;

public class BillDetail {
	private Integer id;
	private String order; //订单号
	private String user_id;
	private double yhgj; // 用户挂机返佣
	private double yhvip; // 用户开通VIP返佣
	private double yhdl; // 用户开通代理返佣
	private double dlgj; // 代理挂机返佣
	private double dlvip; // 代理开通VIP返佣
	private double dldl; // 代理开通代理返佣
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public double getYhgj() {
		return yhgj;
	}
	public void setYhgj(double yhgj) {
		this.yhgj = yhgj;
	}
	public double getYhvip() {
		return yhvip;
	}
	public void setYhvip(double yhvip) {
		this.yhvip = yhvip;
	}
	public double getYhdl() {
		return yhdl;
	}
	public void setYhdl(double yhdl) {
		this.yhdl = yhdl;
	}
	public double getDlgj() {
		return dlgj;
	}
	public void setDlgj(double dlgj) {
		this.dlgj = dlgj;
	}
	public double getDlvip() {
		return dlvip;
	}
	public void setDlvip(double dlvip) {
		this.dlvip = dlvip;
	}
	public double getDldl() {
		return dldl;
	}
	public void setDldl(double dldl) {
		this.dldl = dldl;
	}
	@Override
	public String toString() {
		return "BillDetail [id=" + id + ", order=" + order + ", yhgj=" + yhgj + ", yhvip=" + yhvip
				+ ", yhdl=" + yhdl + ", dlgj=" + dlgj + ", dlvip=" + dlvip + ", dldl=" + dldl + "]";
	}
	
}
