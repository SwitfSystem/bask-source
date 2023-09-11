package com.cloud.bean;

public class AgentSetting {
	
	private String name;
	private Double dlgj;
	private Double dlvip;
	private Double dldl;
	private Double dlprice;
	private Integer invitenum;
	
	public AgentSetting() {
		
	}
	
	public AgentSetting(String name, Double dlgj, Double dlvip, Double dldl, Double dlprice, Integer invitenum) {
		super();
		this.name = name;
		this.dlgj = dlgj;
		this.dlvip = dlvip;
		this.dldl = dldl;
		this.dlprice = dlprice;
		this.invitenum = invitenum;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getDlgj() {
		return dlgj;
	}

	public void setDlgj(Double dlgj) {
		this.dlgj = dlgj;
	}

	public Double getDlvip() {
		return dlvip;
	}

	public void setDlvip(Double dlvip) {
		this.dlvip = dlvip;
	}

	public Double getDldl() {
		return dldl;
	}

	public void setDldl(Double dldl) {
		this.dldl = dldl;
	}

	public Double getDlprice() {
		return dlprice;
	}

	public void setDlprice(Double dlprice) {
		this.dlprice = dlprice;
	}

	public Integer getInvitenum() {
		return invitenum;
	}

	public void setInvitenum(Integer invitenum) {
		this.invitenum = invitenum;
	}
	
	
}
