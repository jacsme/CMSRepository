package com.wom.cms.dao;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wom.cms.constant.MainEnum;
import com.wom.cms.constant.StatusCode;
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
import com.wom.cms.vo.POSupplierVO;
import com.wom.cms.vo.ProductSupplierVO;
@Transactional
public class ProductDaoImpl implements ProductDao{
	
	@Autowired
	ProdSupplierTransferService prodsuppliertransferService;
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	Session session; 
	
	static final Logger logger = Logger.getLogger(ProductDaoImpl.class);
	
	FactoryEntityService<Product> factoryentityService = new FactoryEntityServiceImpl<Product>();
	FactoryEntityService<ProductSupplier> factoryProductSupplier = new FactoryEntityServiceImpl<ProductSupplier>();
	FactoryEntityService<Category> factoryCategory = new FactoryEntityServiceImpl<Category>();
	FactoryEntityService<Supplier> factorySupplier = new FactoryEntityServiceImpl<Supplier>();
	
	@Override
	public List<Product> searchProductCode(String productcode, String brand, String categorycode) throws Exception {
		
		
		List<Product> searchProductList = null;
		try {
			session = sessionFactory.openSession();
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
		try {
			session = sessionFactory.openSession();
			searchProductList = factoryentityService.getEntityProductList(MainEnum.PRODUCT, productcode, brand, categorycode, session);
			productsuppliervo = prodsuppliertransferService.generatePSTransfer(searchProductList, session);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			HibernateUtil.callClose(session);
		}
		return productsuppliervo;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<POSupplierVO> searchPurchaseOrder(String purchaseordercode, String suppliername, String dateissued) throws Exception {
		
		List<String> searchpurchaseorderList = null;
		List<POSupplierVO> searchpurchaseordervo = new ArrayList<POSupplierVO>();
		
		StringBuffer stringcriteria = new StringBuffer();
		Query criteria = null;
		
		try {
			session = sessionFactory.openSession();
			if (!purchaseordercode.equalsIgnoreCase("-")) {	stringcriteria.append(" AND A.PURCHASEORDERCODE =:purchaseordercode "); }
			if (!suppliername.equalsIgnoreCase("-")) { stringcriteria.append(" AND B.SUPPLIERNAME like :suppliername ");}
			if (!dateissued.equalsIgnoreCase("-")) { stringcriteria.append(" AND DATE(A.ISSUEDATE) =:dateissued ");}

			if(stringcriteria.length() != 0){
				criteria = session.createSQLQuery(" SELECT A.PURCHASEORDERCODE, A.SUPPLIERCODE, B.SUPPLIERNAME, "
						+ " A.ISSUEDATE, A.DATERECEIVED FROM tblpurchaseorder A "
						+ " INNER JOIN tblsupplier B ON A.SUPPLIERCODE = B.supplierCode WHERE STATUS = 'Emailed' "
						+ stringcriteria.toString() + " ORDER BY A.PURCHASEORDERCODE ");
			}else{
				criteria = session.createSQLQuery(" SELECT A.PURCHASEORDERCODE, A.SUPPLIERCODE, B.SUPPLIERNAME, "
						+ " A.ISSUEDATE, A.DATERECEIVED FROM tblpurchaseorder A "
						+ " INNER JOIN tblsupplier B ON A.SUPPLIERCODE = B.supplierCode WHERE STATUS = 'Emailed' ORDER BY A.PURCHASEORDERCODE");
			}
			
			if (!purchaseordercode.equalsIgnoreCase("-")) {	criteria.setString("purchaseordercode", purchaseordercode); }
			if (!suppliername.equalsIgnoreCase("-")) {	criteria.setString("suppliername", "%" + suppliername + "%" ); }
			if (!dateissued.equalsIgnoreCase("-")) {	criteria.setString("dateissued", dateissued); }
				
			searchpurchaseorderList = criteria.list();
			
			if(searchpurchaseorderList.size()!=0){
				for (Iterator it = searchpurchaseorderList.iterator(); it.hasNext();){
					Object[] resultListRecord = (Object[]) it.next();
					
					POSupplierVO posupplier = new POSupplierVO();
					posupplier.setPurchaseOrderCode(HelperUtil.checkNullString(resultListRecord[0]));
					posupplier.setSupplierCode(HelperUtil.checkNullString(resultListRecord[1]));
					posupplier.setSupplierName(HelperUtil.checkNullString(resultListRecord[2]));
					posupplier.setIssueDate(HelperUtil.checkNullString(resultListRecord[3]));
					posupplier.setDateReceived(HelperUtil.checkNullString(resultListRecord[4]));
					searchpurchaseordervo.add(posupplier);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return searchpurchaseordervo;
	}
	
	@Override
	public List<Product> updateproductmaindetails(String productcode, String brand, String productname,
			 String categorycode, String barcode, String unitquantity, String packquantity, String retailprice, 
			 String discount, String packweight, String packmass, 
			 String compareweight, String comparemass, String gst) throws Exception {
		
		List<Product> searchProduct = null;
		try {
			session = sessionFactory.openSession();
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
		try {
			session = sessionFactory.openSession();
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
		try {
			session = sessionFactory.openSession();
			searchProduct = factoryentityService.getEntityProductList(MainEnum.EDITPRODUCT, productcode, "", "",session);
			
			if(searchProduct.size() != 0){
				for (Product prod : searchProduct) {
					//insert audit trail
					//TODO Please include the staffcode
					actiontaken1 = "1. Update Promotional Price from " + prod.getPromotionalPrice() + " to " + promotionalprice;
					actiontaken2 = "2. Update RRPrice from " + prod.getrRPrice() + " to " + retailprice;
					
					BigInteger audittrailid = ResultGeneratorUtil.idGenerator("", "sq_audittrail_id", session);
					AuditTrail audittrail = new AuditTrail(audittrailid, "CMS", "updatepromotional1", "-", productcode,
							currdatenow, actiontaken1 + " | " + actiontaken2, "-");
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
			session = sessionFactory.openSession();
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
		try {
			session = HibernateUtil.callSession(sessionFactory);
			
			String productcode = ResultGeneratorUtil.codeGenerator("", "sq_product_code", "22", session);
			String strproductcode = productcode + yearnow;
			Product product = null;
			
			//Check if the productcode is already taken
			Product productexist = factoryentityService.getEntity(MainEnum.PRODUCT, strproductcode, session);
			if (productexist == null) {
				product = new Product(strproductcode, storecode, categorycode, brand, productname, 
						photocode, unitquantity, packquantity, rrprice, 
						packweight, packmass, gst, compareweight, comparemass, 
						checkoutweight, discount, inventorylevel, stockleveldays, 
						keepfresh, description);
			}else{
				productcode = ResultGeneratorUtil.codeGenerator("", "sq_product_code", "22", session);
				strproductcode = productcode + yearnow;
				product = new Product(strproductcode, storecode, categorycode, brand, productname, 
						photocode, unitquantity, packquantity, rrprice, 
						packweight, packmass, gst, compareweight, comparemass, 
						checkoutweight, discount, inventorylevel, stockleveldays, 
						keepfresh, description);
			}
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
			
		}finally{
			HibernateUtil.callClose(session);
		}
		return uploadedmessage;
	}
	
}
