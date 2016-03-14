package com.wom.cms.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.wom.cms.util.HelperUtil;

@Entity
@Table(name = "WOMDBPR.tblinventory")
public class Inventory implements Serializable{

	public Inventory(){}
	
	public Inventory(BigInteger id, String sourcecode, String productcode, String storecode, String unitquantity, String stocklocation, 
			String unit, String unitfrom, String source, String staffcode, String stockcode, String status, String comments){
		this.Id = id;
		this.sourceCode = sourcecode;
		this.productCode = productcode;
		this.storeCode = storecode;
		this.stockLocation = stocklocation;
		this.inventorySource = source;
		this.unitQuantity = unitquantity;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		
		this.transactionDate = currdatenow;
		this.staffCode = staffcode;
		this.stockCode = stockcode;
		this.status = status;
		this.comments = comments;
		
		if (unitfrom.equalsIgnoreCase("GR")){ this.poUnit = unit; this.soUnit = "0"; this.poReturnUnit = "0"; this.soReturnUnit = "0";}
		if (unitfrom.equalsIgnoreCase("STI")){ this.poUnit = unit; this.soUnit = "0"; this.poReturnUnit = "0"; this.soReturnUnit = "0";}
		if (unitfrom.equalsIgnoreCase("SO")){ this.poUnit = "0"; this.soUnit = unit; this.poReturnUnit = "0"; this.soReturnUnit = "0";}
		if (unitfrom.equalsIgnoreCase("RTS")){ this.poUnit = "0"; this.soUnit = "0"; this.poReturnUnit = unit; this.soReturnUnit = "0";}
		if (unitfrom.equalsIgnoreCase("RFC")){ this.poUnit = "0"; this.soUnit = "0"; this.poReturnUnit = "0"; this.soReturnUnit = unit;}
	}
	private static final long serialVersionUID = 1L;
	
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger Id;
	
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
	
	@Column(name = "SupplierCode")
	private String supplierCode;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "JobId")
	private String jobId;
	
	@Column(name = "Requested")
	private String requested;
	
	@Column(name = "Comments")
	private String comments;
	
	
	public BigInteger getId() {
		return Id;
	}

	public void setId(BigInteger id) {
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
	
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
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
