package com.wom.cms.vo;

import java.io.Serializable;


public class ProductSupplierVO implements Serializable{
	
	public ProductSupplierVO() {}
	private static final long serialVersionUID = 6326053509882158341L;
	
	private String supplierCode;
	private String supplierName;
	private String brandName;
	private String productCode;
	private String productName;
	private String buyingPrice;
	private String retailPrice;
	private String discount;
	private String discountAmount;
	private String promotionalPrice;
	
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

	public String getBuyingPrice() {
		return buyingPrice;
	}
	
	public void setBuyingPrice(String buyingPrice) {
		this.buyingPrice = buyingPrice;
	}
	
	public String getRetailPrice() {
		return retailPrice;
	}
	
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	
	public String getDiscount() {
		return discount;
	}
	
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	public String getDiscountAmount() {
		return discountAmount;
	}
	
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getPromotionalPrice() {
		return promotionalPrice;
	}

	public void setPromotionalPrice(String promotionalPrice) {
		this.promotionalPrice = promotionalPrice;
	}
	
	
}
