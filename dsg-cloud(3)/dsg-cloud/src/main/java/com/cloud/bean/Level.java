package com.cloud.bean;

public class Level {
	private Integer id;
	private String explain;
	private Integer device_count;
	private double origunal_price;
	private double price;
	private double rebate;
	private double order_discount;
	private Integer matime;
	private double unit_price;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public Integer getDevice_count() {
		return device_count;
	}
	public void setDevice_count(Integer device_count) {
		this.device_count = device_count;
	}
	public double getOrigunal_price() {
		return origunal_price;
	}
	public void setOrigunal_price(double origunal_price) {
		this.origunal_price = origunal_price;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getRebate() {
		return rebate;
	}
	public void setRebate(double rebate) {
		this.rebate = rebate;
	}
	public double getOrder_discount() {
		return order_discount;
	}
	public void setOrder_discount(double order_discount) {
		this.order_discount = order_discount;
	}
	public Integer getMatime() {
		return matime;
	}
	public void setMatime(Integer matime) {
		this.matime = matime;
	}
	public double getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}
	
}
