package com.wom.cms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.wom.cms.util.HelperUtil;

@Entity
@Table(name = "womdatabase.tblproduct")
public class Product implements Serializable{

	public Product(){}
	public Product(String productcode, String storecode, String categorycode, String brand, String productname, 
			String photocode, String unitquantity, String packquantity, String rrprice, 
			String packweight, String packmass, String gst, String compareweight, String comparemass, 
			String checkoutweight, String discount, String inventorylevel, String stockleveldays, 
			String keepfresh, String description){
		
		this.productCode = productcode;
		this.storeCode = storecode;
		this.categoryCode = categorycode;
		this.brand = brand;
		this.productName = productname;
		
		this.photoCode = photocode;
		this.unitQuantity = unitquantity;
		this.packQuantity = packquantity;
		this.rRPrice = rrprice;
		this.packWeight = packweight;
		this.packMass = packmass;
		this.gst = gst;
		this.compareWeight = compareweight;
		this.compareMass = comparemass;
		this.checkoutWeight = checkoutweight;
		this.discount = discount;
		this.inventoryLevel = inventorylevel;
		this.stockLevelDays = stockleveldays;
		this.keepFresh = keepfresh;
		this.description = description;
		this.active = "YES";
		this.promotionalPrice = "0";
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		this.entryDate = currdatenow;
	}
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator="idgen")
	@Column(name = "Id")
	private long Id;
	 
	@Column(name = "ProductCode")
	private String productCode;
	
	@Column(name = "ProductName")
	private String productName;
	
	@Column(name = "Brand")
	private String brand;
	
	@Column(name = "CategoryCode")
	private String categoryCode;
	
	@Column(name = "StoreCode")
	private String storeCode;
	
	@Column(name = "PhotoCode")
	private String photoCode;
	
	@Column(name = "UnitQuantity")
	private String unitQuantity;
	
	@Column(name = "PackQuantity")
	private String packQuantity;
	
	@Column(name = "PackFormula")
	private String packFormula;
	
	@Column(name = "RRPrice")
	private String rRPrice;
	
	@Column(name = "Discount")
	private String discount;
	
	@Column(name = "DiscountAmount")
	private String discountamount;
	
	@Column(name = "PromotionalPrice")
	private String promotionalPrice;
	
	@Column(name = "PackWeight")
	private String packWeight;
	
	@Column(name = "PackMass")
	private String packMass;
	
	@Column(name = "CompareWeight")
	private String compareWeight;
	
	@Column(name = "CompareMass")
	private String compareMass;
	
	@Column(name = "StockLevelDays")
	private String stockLevelDays;

	@Column(name = "GST")
	private String gst;
	
	@Column(name = "UOM")
	private String uom;
	
	@Column(name = "Active")
	private String active;
	
	@Column(name = "EntryDate")
	private String entryDate;
	
	@Column(name = "KeepFresh")
	private String keepFresh;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "InventoryLevel")
	private String inventoryLevel;
	
	@Column(name = "CheckoutWeight")
	private String checkoutWeight;
	
	@Column(name = "Photo")
	private String photo;
	
	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getPhotoCode() {
		return photoCode;
	}

	public void setPhotoCode(String photoCode) {
		this.photoCode = photoCode;
	}

	public String getUnitQuantity() {
		return unitQuantity;
	}

	public void setUnitQuantity(String unitQuantity) {
		this.unitQuantity = unitQuantity;
	}

	public String getPackQuantity() {
		return packQuantity;
	}

	public void setPackQuantity(String packQuantity) {
		this.packQuantity = packQuantity;
	}

	public String getPackFormula() {
		return packFormula;
	}

	public void setPackFormula(String packFormula) {
		this.packFormula = packFormula;
	}

	public String getrRPrice() {
		return rRPrice;
	}
	
	public void setrRPrice(String rRPrice) {
		this.rRPrice = rRPrice;
	}
	
	public String getDiscount() {
		return discount;
	}
	
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	public String getDiscountamount() {
		return discountamount;
	}
	
	public void setDiscountamount(String discountamount) {
		this.discountamount = discountamount;
	}
	
	public String getPromotionalPrice() {
		return promotionalPrice;
	}
	
	public void setPromotionalPrice(String promotionalPrice) {
		this.promotionalPrice = promotionalPrice;
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

	public String getCompareWeight() {
		return compareWeight;
	}

	public void setCompareWeight(String compareWeight) {
		this.compareWeight = compareWeight;
	}

	public String getCompareMass() {
		return compareMass;
	}
	
	public void setCompareMass(String compareMass) {
		this.compareMass = compareMass;
	}
	
	public String getStockLevelDays() {
		return stockLevelDays;
	}

	public void setStockLevelDays(String stockLevelDays) {
		this.stockLevelDays = stockLevelDays;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	public String getEntryDate() {
		return entryDate;
	}
	
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	
	public String getKeepFresh() {
		return keepFresh;
	}
	
	public void setKeepFresh(String keepFresh) {
		this.keepFresh = keepFresh;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getInventoryLevel() {
		return inventoryLevel;
	}
	
	public void setInventoryLevel(String inventoryLevel) {
		this.inventoryLevel = inventoryLevel;
	}
	
	public String getCheckoutWeight() {
		return checkoutWeight;
	}
	
	public void setCheckoutWeight(String checkoutWeight) {
		this.checkoutWeight = checkoutWeight;
	}
	
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
}
