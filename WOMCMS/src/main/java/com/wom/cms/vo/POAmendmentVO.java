package com.wom.cms.vo;

import java.io.Serializable;

public class POAmendmentVO implements Serializable {
	
	private static final long serialVersionUID = -3776455899874387691L;
	
	private String supplierCode;
	private String poCode;
	private String productCode;
	private String productName;
	private String packQuantity;
	private String packUnit;
	private String packTotalUnit;
	private String packPrice;
	private String gst;
	private String amount;
	private String totalAmount;
	private String unitQuantity;
	private String invPackQuantity;
	private String invTotalUnit;
	
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getPoCode() {
		return poCode;
	}
	public void setPoCode(String poCode) {
		this.poCode = poCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPackQuantity() {
		return packQuantity;
	}
	public void setPackQuantity(String packQuantity) {
		this.packQuantity = packQuantity;
	}
	public String getPackUnit() {
		return packUnit;
	}
	public void setPackUnit(String packUnit) {
		this.packUnit = packUnit;
	}
	public String getPackTotalUnit() {
		return packTotalUnit;
	}
	public void setPackTotalUnit(String packTotalUnit) {
		this.packTotalUnit = packTotalUnit;
	}
	public String getPackPrice() {
		return packPrice;
	}
	public void setPackPrice(String packPrice) {
		this.packPrice = packPrice;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getUnitQuantity() {
		return unitQuantity;
	}
	public void setUnitQuantity(String unitQuantity) {
		this.unitQuantity = unitQuantity;
	}
	public String getInvPackQuantity() {
		return invPackQuantity;
	}
	public void setInvPackQuantity(String invPackQuantity) {
		this.invPackQuantity = invPackQuantity;
	}
	public String getInvTotalUnit() {
		return invTotalUnit;
	}
	public void setInvTotalUnit(String invTotalUnit) {
		this.invTotalUnit = invTotalUnit;
	}
}
