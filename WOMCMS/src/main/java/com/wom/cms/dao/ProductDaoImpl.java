package com.wom.cms.dao;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wom.cms.constant.MainEnum;
import com.wom.cms.factory.FactoryEntityService;
import com.wom.cms.factory.FactoryEntityServiceImpl;
import com.wom.cms.model.AuditTrail;
import com.wom.cms.model.Category;
import com.wom.cms.model.Product;
import com.wom.cms.model.ProductSupplier;
import com.wom.cms.model.Supplier;
import com.wom.cms.transferer.ProdSupplierTransferService;
import com.wom.cms.util.HelperUtil;
import com.wom.cms.util.HibernateUtil;
import com.wom.cms.util.ResultGeneratorUtil;
import com.wom.cms.vo.ProductSupplierVO;
@Transactional
public class ProductDaoImpl implements ProductDao{
	
	@Autowired
	ProdSupplierTransferService prodsuppliertransferService;
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(ProductDaoImpl.class);
	
	FactoryEntityService<Product> factoryentityService = new FactoryEntityServiceImpl<Product>();
	FactoryEntityService<ProductSupplier> factoryProductSupplier = new FactoryEntityServiceImpl<ProductSupplier>();
	FactoryEntityService<Category> factoryCategory = new FactoryEntityServiceImpl<Category>();
	FactoryEntityService<Supplier> factorySupplier = new FactoryEntityServiceImpl<Supplier>();
	
	@Override
	public List<Product> searchProductCode(String productcode, String brand, String categorycode) throws Exception {
		
		List<Product> searchProductList = null;
		Session session = sessionFactory.openSession();
		try {
			searchProductList = factoryentityService.getEntityProductList(MainEnum.PRODUCT, productcode, brand, categorycode, session);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			HibernateUtil.callClose(session);
		}
		return searchProductList;
	}
	
	@Override
	public List<ProductSupplierVO> searchPromoProducts(String productcode, String brand, String categorycode) throws Exception {
		
		List<Product> searchProductList = null;
		List<ProductSupplierVO> productsuppliervo = null;
		Session session = sessionFactory.openSession();
		try {
			searchProductList = factoryentityService.getEntityProductList(MainEnum.PRODUCT, productcode, brand, categorycode, session);
			productsuppliervo = prodsuppliertransferService.generatePSTransfer(searchProductList, session);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			HibernateUtil.callClose(session);
		}
		return productsuppliervo;
	}
	
	
	
	@Override
	public List<Product> updateproductmaindetails(String productcode, String brand, String productname,
			 String categorycode, String barcode, String unitquantity, String packquantity, String retailprice, 
			 String discount, String packweight, String packmass, 
			 String compareweight, String comparemass, String gst) throws Exception {
		
		List<Product> searchProduct = null;
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		String actiontaken1 = null;
		String actiontaken2 = null;
		Session session = sessionFactory.openSession();
		try {
			searchProduct = factoryentityService.getEntityProductList(MainEnum.EDITPRODUCT, productcode, "", "",session);
			if(searchProduct.size() != 0){
				for (Product prod : searchProduct) {
					
					prod.setBrand(brand);
					prod.setProductName(productname);
					prod.setCategoryCode(categorycode);
					prod.setPhotoCode(barcode);
					prod.setUnitQuantity(unitquantity);
					prod.setPackQuantity(packquantity);
					prod.setrRPrice(retailprice);
					prod.setDiscount(discount);
					prod.setPackWeight(packweight);
					prod.setPackMass(packmass);
					prod.setCompareWeight(compareweight);
					prod.setCompareMass(comparemass);
					prod.setGst(gst);
					session.save(prod);
				}
				//Audit Trail
				actiontaken1 = "1. Edit Product categorycode, brand, productname, photocode, unitquantity, packquantity, rrprice, "
					+ " packweight, packmass, gst, compareweight, comparemass "; 
				actiontaken2 = "2. Values " + categorycode + "," + brand + "," + productname + "," + barcode + "," + unitquantity
						+ "," + packquantity + "," + retailprice + "," + packweight + "," + packmass + "," + gst + "," + compareweight + "," + comparemass ;

				BigInteger audittrailid = ResultGeneratorUtil.idGenerator("", "sq_audittrail_id", session);
				AuditTrail audittrail = new AuditTrail(audittrailid, "CMS", "updateproductmaindetails", "-", productcode,
						currdatenow, actiontaken1 + actiontaken2, HelperUtil.getLoginusercode());
				session.save(audittrail);
				
			}
			HibernateUtil.callCommitClose(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchProduct;
	}
	
	@Override
	public List<Product> updateproductotherdetails(String productcode, String brand, String productname,
			 String categorycode, String barcode,  String stockleveldays, String checkoutweight, String inventorylevel, 
			 String description, String keepfresh, String active) throws Exception {
		
		List<Product> searchProduct = null;
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		String actiontaken1 = null;
		String actiontaken2 = null;
		Session session = sessionFactory.openSession();
		try {
			searchProduct = factoryentityService.getEntityProductList(MainEnum.EDITPRODUCT, productcode, "", "",session);
			if(searchProduct.size() != 0){
				for (Product prod : searchProduct) {
					
					if(description.equalsIgnoreCase("-")){ description = ""; }
					prod.setBrand(brand);
					prod.setProductName(productname);
					prod.setCategoryCode(categorycode);
					prod.setPhotoCode(barcode);
					prod.setStockLevelDays(stockleveldays);
					prod.setCheckoutWeight(checkoutweight);
					prod.setInventoryLevel(inventorylevel);
					prod.setDescription(description);
					prod.setKeepFresh(keepfresh);
					prod.setActive(active);
					
					session.save(prod);
				}
				
				//Audit Trail
				actiontaken1 = "1. Edit Product categorycode, brand, productname, photocode, checkoutweight, inventorylevel, stockleveldays, keepfresh, description "; 
				actiontaken2 = "2. Values " + categorycode + "," + brand + "," + productname + "," + barcode 
						+ "," + checkoutweight + "," + inventorylevel + "," + stockleveldays + "," + keepfresh + "," + description; 
				
				BigInteger audittrailid = ResultGeneratorUtil.idGenerator("", "sq_audittrail_id", session);
				AuditTrail audittrail = new AuditTrail(audittrailid, "CMS", "updateproductotherdetails", "-", productcode,
						currdatenow, actiontaken1 + actiontaken2, HelperUtil.getLoginusercode());
				session.save(audittrail);
			}
			HibernateUtil.callCommitClose(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchProduct;
	}
	
	@Override
	public List<Product> updatepromotional1(String productcode, String retailprice, 
			 String discount, String discountamount, String promotionalprice) throws Exception {
		
		List<Product> searchProduct = null;
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		String actiontaken1 = null;
		String actiontaken2 = null;
		Session session = sessionFactory.openSession();
		try {
			searchProduct = factoryentityService.getEntityProductList(MainEnum.EDITPRODUCT, productcode, "", "",session);
			if(searchProduct.size() != 0){
				for (Product prod : searchProduct) {
					//insert audit trail
					//TODO Please include the staffcode
					actiontaken1 = "1. Update Promotional Price from " + prod.getPromotionalPrice() + " to " + promotionalprice;
					actiontaken2 = "2. Update RRPrice from " + prod.getrRPrice() + " to " + retailprice;
					
					BigInteger audittrailid = ResultGeneratorUtil.idGenerator("", "sq_audittrail_id", session);
					AuditTrail audittrail = new AuditTrail(audittrailid, "CMS", "updatepromotional1", "-", productcode,
							currdatenow, actiontaken1 + " | " + actiontaken2, HelperUtil.getLoginusercode());
					session.save(audittrail);
					
					prod.setrRPrice(retailprice);
					prod.setDiscountamount(discountamount);
					prod.setPromotionalPrice(promotionalprice);
					session.save(prod);
				}
			}
			HibernateUtil.callCommitClose(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchProduct;
	}
	
	@Override
	public List<Category> getCategoryList() throws Exception {
		
		List<Category> categorylist = null;
		try {
			Session session = sessionFactory.openSession();
			categorylist = factoryCategory.getCategoryEntityList(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return categorylist;
	}
	
	/**
	private String productName;
	private String brand;
	private String categoryCode;
	private String storeCode;
	private String photoCode;
	private String unitQuantity;
	private String packQuantity;
	private String packFormula;
	private String packPrice;
	private String packWeight;
	private String packMass;
	private String stockLevelDays;
	private String gst;
	private String uom;
	**/
	
	@Override
	public List<Product> submitNewProduct(String suppliercode, String supppackquantity, String packunit, String packprice, String paymentterms, 
			String storecode, String categorycode, String brand, String productname, 
			String photocode, String unitquantity, String packquantity, String rrprice, 
			String packweight, String packmass, String gst, String compareweight, String comparemass, 
			String checkoutweight, String discount, String inventorylevel, String stockleveldays, String keepfresh,
			String description) throws Exception{
		
		String yearnow = HelperUtil.yearformat.format(new Date());
		List<Product> saveProduct = new ArrayList<Product>();
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		String actiontaken1 = null;
		String actiontaken2 = null;
		Session session = HibernateUtil.callSession(sessionFactory);
		try {
			String productcode = ResultGeneratorUtil.codeGenerator("", "sq_product_code", "22", session);
			String strproductcode = productcode + yearnow;
			Product product = null;
			
			//Audit Trail
			actiontaken1 = "1. Add New Product productcode, categorycode, brand, productname, photocode, unitquantity, packquantity, rrprice, "
				+ " packweight, packmass, gst, compareweight, comparemass, checkoutweight, discount, inventorylevel, stockleveldays, " 
				+ " keepfresh, description "; 
			actiontaken2 = "2. Values " + productcode + "," + categorycode + "," + brand + "," + productname + "," + photocode + "," + unitquantity
					+ "," + packquantity + "," + rrprice + "," + packweight + "," + packmass + "," + gst + "," + compareweight + "," + comparemass
					+ "," + checkoutweight + "," + discount + "," + inventorylevel + "," + stockleveldays + "," + keepfresh + "," + description; 
			
			BigInteger audittrailid = ResultGeneratorUtil.idGenerator("", "sq_audittrail_id", session);
			AuditTrail audittrail = new AuditTrail(audittrailid, "CMS", "submitNewProduct", "-", productcode,
					currdatenow, actiontaken1 + actiontaken2, HelperUtil.getLoginusercode());
			session.save(audittrail);
			
			product = new Product(strproductcode, storecode, categorycode, brand, productname, 
				photocode, unitquantity, packquantity, rrprice, 
				packweight, packmass, gst, compareweight, comparemass, 
				checkoutweight, discount, inventorylevel, stockleveldays, 
				keepfresh, description);
			session.save(product);
			saveProduct.add(product);
			
			Supplier supplier = factorySupplier.getEntitySupplier(suppliercode, session);
			
			if (supplier != null){
				BigInteger productsupplierid = ResultGeneratorUtil.idGenerator("", "sq_productsupplier_id", session);
				ProductSupplier productsupplier = new ProductSupplier(productsupplierid, strproductcode, suppliercode, packquantity,
						packunit, packprice, paymentterms);
				session.save(productsupplier);
				HibernateUtil.callCommitClose(session);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return saveProduct;
	}
	
	public String submituploadfile(String productcode, MultipartFile imageA, MultipartFile imageB,
			MultipartFile imageC, MultipartFile imageD, MultipartFile imageE) throws Exception {
		
		String uploadedmessage = "Unable to upload";
		String serverloc = HelperUtil.PRODUCT_IMAGE_LOCATIONTMP;
		
		try {
			
			if (!imageA.isEmpty()) {
				String filetypeA = imageA.getContentType(); //get the file type
				String filenameA = productcode + "A." + filetypeA;
				File fileA = new File(serverloc + "/" + filenameA);
				FileUtils.writeByteArrayToFile(fileA, imageA.getBytes());
				uploadedmessage = "Successful Uploading";
			}
			
			if (!imageB.isEmpty()) {
				String filetypeB = imageB.getContentType(); //get the file type
				String filenameB = productcode + "B." + filetypeB;
				File fileB = new File(serverloc + "/" + filenameB);
				FileUtils.writeByteArrayToFile(fileB, imageB.getBytes());
				uploadedmessage = "Successful Uploading";
			}
			
			if (!imageC.isEmpty()) {
				String filetypeC = imageC.getContentType(); //get the file type
				String filenameC = productcode + "C." + filetypeC;
				File fileC = new File(serverloc + "/" + filenameC);
				FileUtils.writeByteArrayToFile(fileC, imageC.getBytes());
				uploadedmessage = "Successful Uploading";
			}
			
			if (!imageD.isEmpty()) {
				String filetypeD = imageD.getContentType(); //get the file type
				String filenameD = productcode + "D." + filetypeD;
				File fileD = new File(serverloc + "/" + filenameD);
				FileUtils.writeByteArrayToFile(fileD, imageD.getBytes());
				uploadedmessage = "Successful Uploading";
			}
			
			if (!imageE.isEmpty()) {
				String filetypeE = imageE.getContentType(); //get the file type
				String filenameE = productcode + "E." + filetypeE;
				File fileE = new File(serverloc + "/" + filenameE);
				FileUtils.writeByteArrayToFile(fileE, imageE.getBytes());
				uploadedmessage = "Successful Uploading";
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		//}finally{
		//	HibernateUtil.callClose(session);
		}
		return uploadedmessage;
	}
	
}
