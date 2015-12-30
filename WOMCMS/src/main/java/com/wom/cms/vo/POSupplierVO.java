package com.wom.cms.vo;

import java.io.Serializable;

public class POSupplierVO implements Serializable{

	public POSupplierVO(){}
	private static final long serialVersionUID = -5118239787367792706L;
	
	private String purchaseOrderCode;
	private String SupplierCode;
	private String SupplierName;
	private String issueDate;
	private String dateReceived;
	
	public String getPurchaseOrderCode() {
		return purchaseOrderCode;
	}
	
	public void setPurchaseOrderCode(String purchaseOrderCode) {
		this.purchaseOrderCode = purchaseOrderCode;
	}
	
	public String getSupplierCode() {
		return SupplierCode;
	}
	
	public void setSupplierCode(String supplierCode) {
		SupplierCode = supplierCode;
	}
	
	public String getSupplierName() {
		return SupplierName;
	}
	
	public void setSupplierName(String supplierName) {
		SupplierName = supplierName;
	}
	
	public String getIssueDate() {
		return issueDate;
	}
	
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	
	public String getDateReceived() {
		return dateReceived;
	}
	
	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;
	}
}
