package com.m.yifeng.util;

public class OrderInfo {

	
	private String order_sn;
	private String order_status;
	private String shipping_fee;
	private String goods_amount;
	private String address;
	private String short_order_time;
	private String total_fee;
	
	public String getOrder_sn() {
		return order_sn;
	}
	public void setOrder_sn(String order_sn) {
		this.order_sn = order_sn;
	}
	public String getShipping_fee() {
		return shipping_fee;
	}
	public void setShipping_fee(String shipping_fee) {
		this.shipping_fee = shipping_fee;
	}
	public String getGoods_amount() {
		return goods_amount;
	}
	public void setGoods_amount(String goods_amount) {
		this.goods_amount = goods_amount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getShort_order_time() {
		return short_order_time;
	}
	public void setShort_order_time(String short_order_time) {
		this.short_order_time = short_order_time;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	@Override
	public String toString() {
		return "OrderInfo [order_sn=" + order_sn + ", shipping_fee="
				+ shipping_fee + ", goods_amount=" + goods_amount
				+ ", address=" + address + ", short_order_time="
				+ short_order_time + ", total_fee=" + total_fee + "]";
	}
	public OrderInfo(String order_sn, String shipping_fee, String goods_amount,
			String address, String short_order_time, String total_fee) {
		super();
		this.order_sn = order_sn;
		this.shipping_fee = shipping_fee;
		this.goods_amount = goods_amount;
		this.address = address;
		this.short_order_time = short_order_time;
		this.total_fee = total_fee;
	}
	public OrderInfo() {
		super();
	}
}
