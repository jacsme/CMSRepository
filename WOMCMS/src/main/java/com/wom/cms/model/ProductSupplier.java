package com.wom.cms.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "womdatabase.tblproductsupplier")
public class ProductSupplier implements Serializable{

	private static final long serialVersionUID = -7860222614035058667L;
	
	public ProductSupplier(){}
	public ProductSupplier(BigInteger id, String productcode, String suppliercode, String packquantity, String packunit, String packprice, String paymentterms){
		this.id = id;
		this.productCode = productcode;
		this.supplierCode = suppliercode;
		this.packQuantity = packquantity;
		this.packUnit = packunit;
		this.packPrice = packprice;
		this.paymentTerms = paymentterms;
	}
	@Id
	//@GenericGenerator(name = "idgen", strategy = "increment")
	//@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private BigInteger id;
	
	@Column(name = "SupplierCode")
	private String supplierCode;
	
	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "PackQuantity")
	private String packQuantity;
	
	@Column(name = "PackUnit")
	private String packUnit;
	
	@Column(name = "PackPrice")
	private String packPrice;
	
	@Column(name = "PaymentTerms")
	private String paymentTerms;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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

	@Override
	public String toString(){
		
		String mystring = "supplierCode=" + supplierCode + "&" + "productCode=" + productCode
				+ "&" + "packQuantity=" + packQuantity + "&" + "packUnit=" + packUnit
				+ "&" + "packPrice=" + packPrice + "&" + "paymentTerms=" + paymentTerms;
		return mystring;
	}

}
