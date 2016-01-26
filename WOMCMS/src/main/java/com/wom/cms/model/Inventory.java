package com.wom.cms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "womdatabase.tblinventory")
public class Inventory implements Serializable{

	public Inventory(){}
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private long Id;
	
	@Column(name = "SourceCode")
	private String sourceCode;
	
	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "StoreCode")
	private String storeCode;
	
	@Column(name = "UnitQuantity")
	private String unitQuantity;
	
	@Column(name = "POQuantity")
	private String poQuantity;
	
	@Column(name = "POUnit")
	private String poUnit;
	
	@Column(name = "POReturnUnit")
	private String poReturnUnit;
	
	@Column(name = "SOUnit")
	private String soUnit;
	
	@Column(name = "SOReturnUnit")
	private String soReturnUnit;
	
	@Column(name = "InventorySource")
	private String inventorySource;
	
	@Column(name = "TransactionDate")
	private String transactionDate;
	
	@Column(name = "StockLocation")
	private String stockLocation;
	
	@Column(name = "StaffCode")
	private String staffCode;
	
	@Column(name = "StockCode")
	private String stockCode;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "JobId")
	private String jobId;
	
	@Column(name = "Requested")
	private String requested;
	
	@Column(name = "Comments")
	private String comments;
	
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getUnitQuantity() {
		return unitQuantity;
	}
	public void setUnitQuantity(String unitQuantity) {
		this.unitQuantity = unitQuantity;
	}
	public String getPoQuantity() {
		return poQuantity;
	}
	public void setPoQuantity(String poQuantity) {
		this.poQuantity = poQuantity;
	}
	public String getPoUnit() {
		return poUnit;
	}
	public void setPoUnit(String poUnit) {
		this.poUnit = poUnit;
	}
	public String getPoReturnUnit() {
		return poReturnUnit;
	}
	public void setPoReturnUnit(String poReturnUnit) {
		this.poReturnUnit = poReturnUnit;
	}
	public String getSoUnit() {
		return soUnit;
	}
	public void setSoUnit(String soUnit) {
		this.soUnit = soUnit;
	}
	public String getSoReturnUnit() {
		return soReturnUnit;
	}
	public void setSoReturnUnit(String soReturnUnit) {
		this.soReturnUnit = soReturnUnit;
	}
	public String getInventorySource() {
		return inventorySource;
	}
	public void setInventorySource(String inventorySource) {
		this.inventorySource = inventorySource;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getStockLocation() {
		return stockLocation;
	}
	public void setStockLocation(String stockLocation) {
		this.stockLocation = stockLocation;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getRequested() {
		return requested;
	}
	public void setRequested(String requested) {
		this.requested = requested;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
