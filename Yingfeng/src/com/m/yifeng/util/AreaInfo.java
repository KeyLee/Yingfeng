package com.m.yifeng.util;

public class AreaInfo {

	private String text;
	private String consignee;
	private String tel;
	private String is_default;
	private String address_id;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getIs_default() {
		return is_default;
	}
	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}
	public String getAddress_id() {
		return address_id;
	}
	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}
	@Override
	public String toString() {
		return "AreaInfo [text=" + text + ", consignee=" + consignee + ", tel="
				+ tel + ", is_default=" + is_default + ", address_id="
				+ address_id + "]";
	}
	public AreaInfo(String text, String consignee, String tel,
			String is_default, String address_id) {
		super();
		this.text = text;
		this.consignee = consignee;
		this.tel = tel;
		this.is_default = is_default;
		this.address_id = address_id;
	}
	public AreaInfo() {
		super();
	}
	
	
}
