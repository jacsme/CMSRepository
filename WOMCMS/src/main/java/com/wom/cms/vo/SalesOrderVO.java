package com.wom.cms.vo;

import java.io.Serializable;

public class SalesOrderVO implements Serializable{

	public SalesOrderVO(){}
	private static final long serialVersionUID = -5118239787367792706L;
	
	private String salesOrderCode;
	private String address;
	private String customerCode;
	private String dateOrdered;
	private String deliveryDate;
	private String deliveryTime;
	private String womCoin;
	private String purchaseAmount;
	private String status;
	
	public String getSalesOrderCode() {
		return salesOrderCode;
	}
	
	public void setSalesOrderCode(String salesOrderCode) {
		this.salesOrderCode = salesOrderCode;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCustomerCode() {
		return customerCode;
	}
	
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	public String getDateOrdered() {
		return dateOrdered;
	}
	
	public void setDateOrdered(String dateOrdered) {
		this.dateOrdered = dateOrdered;
	}
	
	public String getDeliveryDate() {
		return deliveryDate;
	}
	
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	public String getWomCoin() {
		return womCoin;
	}
	
	public void setWomCoin(String womCoin) {
		this.womCoin = womCoin;
	}
	
	public String getPurchaseAmount() {
		return purchaseAmount;
	}
	
	public void setPurchaseAmount(String purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
