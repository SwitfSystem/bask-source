package com.cloud.bean;

public class UserSetting {
	
	private String name;
	private Double bill;
	private Double price;
	private Integer time;
	private Integer type;
	
	public UserSetting() {
		
	}
	public UserSetting(String name, Double bill, Double price, Integer time, Integer type) {
		super();
		this.name = name;
		this.bill = bill;
		this.price = price;
		this.time = time;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getBill() {
		return bill;
	}
	public void setBill(Double bill) {
		this.bill = bill;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
