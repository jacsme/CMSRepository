package com.wom.cms.vo;

import java.io.Serializable;


public class SupplierProductVO implements Serializable{
	
	public SupplierProductVO() {}
	private static final long serialVersionUID = 6326053509882158341L;
	
	private String supplierCode;
	private String supplierName;
	private String brandName;
	private String productCode;
	private String productName;
	private String photoCode;
	private String packWeight;
	private String packMass;
	private String packQuantity;
	private String packUnit;
	private String packPrice;
	private String paymentTerms;
	private String gst;
	
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
	public String getPhotoCode() {
		return photoCode;
	}
	public void setPhotoCode(String photoCode) {
		this.photoCode = photoCode;
	}
	public String getPackWeight() {
		return packWeight;
	}
	public void setPackWeight(String packWeight) {
		this.packWeight = packWeight;
	}
	public String getPackMass() {
		return packMass;
	}
	public void setPackMass(String packMass) {
		this.packMass = packMass;
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
	public String getPackPrice() {
		return packPrice;
	}
	public void setPackPrice(String packPrice) {
		this.packPrice = packPrice;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	
}
