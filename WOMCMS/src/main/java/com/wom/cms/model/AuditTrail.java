package com.wom.cms.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "womdatabase.tblaudittrail")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AuditTrail {
	public AuditTrail(){}
	public AuditTrail(BigInteger id, String applicationName, String functionName, String supplierCode, String productCode,
			String transDate, String actionTaken, String staffCode){

		this.Id = id;
		this.applicationName = applicationName;
		this.functionName = functionName;
		this.supplierCode = supplierCode;
		this.productCode = productCode;
		this.transDate = transDate;
		this.actionTaken = actionTaken;
		this.staffCode = staffCode;
	}

	@Id
	@Column(name = "Id")
	private BigInteger Id;
	
	@Column(name = "ApplicationName")
	private String applicationName;
	
	@Column(name = "FunctionName")
	private String functionName;
	
	@Column(name = "SupplierCode")
	private String supplierCode;
	
	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "TransDate")
	private String transDate;
	
	@Column(name = "ActionTaken")
	private String actionTaken;
	
	@Column(name = "StaffCode")
	private String staffCode;

	public BigInteger getId() {
		return Id;
	}
	public void setId(BigInteger id) {
		Id = id;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getActionTaken() {
		return actionTaken;
	}
	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

}
