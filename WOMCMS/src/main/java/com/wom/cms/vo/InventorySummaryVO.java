package com.wom.cms.vo;

import java.io.Serializable;

public class InventorySummaryVO implements Serializable{

	private static final long serialVersionUID = -5085909229210745781L;

	public InventorySummaryVO(){}
	
	private String transactionDate;
	private String productCode;
	private String brand;
	private String productName;
	private String packWeight;
	private String packMass;
	private String location;
	private String buyingPrice;
	private String rrprice;
	private String units;
	private String unitsAll;
	private String returnUnits;
	private String photo;
	private String comments;

	public String getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public String getProductCode() {
		return productCode;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
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
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getBuyingPrice() {
		return buyingPrice;
	}
	
	public void setBuyingPrice(String buyingPrice) {
		this.buyingPrice = buyingPrice;
	}
	
	public String getRrprice() {
		return rrprice;
	}
	
	public void setRrprice(String rrprice) {
		this.rrprice = rrprice;
	}
	
	public String getUnits() {
		return units;
	}
	
	public void setUnits(String units) {
		this.units = units;
	}
	
	public String getUnitsAll() {
		return unitsAll;
	}

	public void setUnitsAll(String unitsAll) {
		this.unitsAll = unitsAll;
	}

	public String getReturnUnits() {
		return returnUnits;
	}
	
	public void setReturnUnits(String returnUnits) {
		this.returnUnits = returnUnits;
	}
	
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
