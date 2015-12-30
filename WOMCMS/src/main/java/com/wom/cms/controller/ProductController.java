package com.wom.cms.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wom.cms.model.Category;
import com.wom.cms.model.Product;
import com.wom.cms.services.ProductService;
import com.wom.cms.vo.AddProductSupplierVO;
import com.wom.cms.vo.POSupplierVO;
import com.wom.cms.vo.ProductSupplierVO;

@Controller
@RequestMapping("/cms")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	static final Logger logger = Logger.getLogger(ProductController.class);

	@RequestMapping(value="/uploadimages", method = RequestMethod.GET)
	public String getUploadImagesGET(ModelMap model) {
		logger.info("Received request to show UploadImages GET");
		model.addAttribute("message", "Welcome");
		return "uploadimages";
	}
	
	@RequestMapping(value="/productpromotional1", method = RequestMethod.GET)
	public String getProductPromotional1GET(ModelMap model) {
		logger.info("Received request to show getProductPromotional1GET GET");
		model.addAttribute("message", "Welcome");
		return "productpromotional1";
	}
	
	@RequestMapping(value="/addnewproductpage", method = RequestMethod.GET)
	public String getaddnewproductspageGET(ModelMap model) {
		logger.info("Received request to show add new products page GET");
		model.addAttribute("message", "Welcome");
		return "addnewproduct";
	}
	
	@RequestMapping(value="/maindetails", method = RequestMethod.GET)
	public String getMainDetailsGET(ModelMap model) {
		logger.info("Received request to show Amend Main Product Details GET");
		model.addAttribute("message", "Welcome");
		return "editproductsmain";
	}
		
	@RequestMapping(value="/otherdetails", method = RequestMethod.GET)
	public String getOtherDetailsGET(ModelMap model) {
		logger.info("Received request to show Amend Other Product Details GET");
		model.addAttribute("message", "Welcome");
		return "editproductsothers";
	}
	
	@RequestMapping(value="/purchaseorders", method = RequestMethod.GET)
	public String getPurchaseOrdersGET(ModelMap model) {
		logger.info("Received request to show Purchase Orders GET");
		model.addAttribute("message", "Welcome");
		return "purchaseorders";
	}
	
	/** GET Request 
	 * @throws JSONException **/
	@RequestMapping(value = "/searchProductCode/{productcode:.+}/{brand:.+}/{categorycode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Product> searchProductCodeGET(@PathVariable("productcode") String productcode,
			@PathVariable("brand") String brand, @PathVariable("categorycode") String categorycode) throws Exception{
		
		logger.info(" Request for searchProductCodeGET() " + productcode + "/" + brand + "/" + categorycode);
		
		List<Product> searchProductCodeList = null;
		try{
			searchProductCodeList = productService.searchProductCode(productcode, brand, categorycode);
		}catch(Exception e){
			e.printStackTrace();
		}
		return searchProductCodeList;
	}
	
	/** GET Request 
	 * @throws JSONException **/
	@RequestMapping(value = "/searchPromoProducts/{productcode:.+}/{brand:.+}/{categorycode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<ProductSupplierVO> searchPromoProductsGET(@PathVariable("productcode") String productcode,
			@PathVariable("brand") String brand, @PathVariable("categorycode") String categorycode) throws Exception{
		
		logger.info(" Request for searchPromoProductsGET() " + productcode + "/" + brand + "/" + categorycode);
		
		List<ProductSupplierVO> searchPromoProductList = null;
		try{
			searchPromoProductList = productService.searchPromoProducts(productcode, brand, categorycode);
		}catch(Exception e){
			e.printStackTrace();
		}
		return searchPromoProductList;
	}
	
	/** GET Request 
	 * @throws JSONException **/
	@RequestMapping(value = "/searchPurchaseOrder/{purchaseordercode:.+}/{suppliername:.+}/{dateissued:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<POSupplierVO> searchPurchaseOrderGET(@PathVariable("purchaseordercode") String purchaseordercode,
			@PathVariable("suppliername") String suppliername, @PathVariable("dateissued") String dateissued) throws Exception{
		
		logger.info(" Request for searchPurchaseOrderGET() " + purchaseordercode + "/" + suppliername + "/" + dateissued);
		
		List<POSupplierVO> searchpurchaseorderlist = null;
		try{
			searchpurchaseorderlist = productService.searchPurchaseOrder(purchaseordercode, suppliername, dateissued);
		}catch(Exception e){
			e.printStackTrace();
		}
		return searchpurchaseorderlist;
	}
	
	/** GET Request 
	 * @throws JSONException **/
	@RequestMapping(value = "/getCategoryList", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Category> getCategoryListGET() throws Exception{
		
		logger.info(" Request for getCategoryListGET() ");
		
		List<Category> categorylist = null;
		
		try{
			categorylist = productService.getCategoryList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return categorylist;
	}
	
	/** POST Request 
	 * @throws JSONException **/
	@RequestMapping(value = "/searchProductCode/{productcode:.+}/{brand:.+}/{categorycode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Product> searchProductCodePOST(@PathVariable("productcode") String productcode,
			@PathVariable("brand") String brand, @PathVariable("categorycode") String categorycode) throws Exception{
		
		logger.info(" Request for searchProductCodePOST()");
		
		List<Product> searchProductCodeList = null;
		try{
			searchProductCodeList = productService.searchProductCode(productcode, brand, categorycode);
		}catch(Exception e){
			e.printStackTrace();
		}
		return searchProductCodeList;
	}
	
	@RequestMapping(value = "/updateproductmaindetails/{productcode:.+}/{brand:.+}/{productname:.+}/{categorycode:.+}/{barcode:.+}/{unitquantity:.+}/{packquantity:.+}/{retailprice:.+}/{discount:.+}/{packweight:.+}/{packmass:.+}/{compareweight:.+}/{comparemass:.+}/{gst:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Product> updateproductmaindetailsPOST(
			@PathVariable("productcode") String productcode,
			@PathVariable("brand") String brand,
			@PathVariable("productname") String productname,
			@PathVariable("categorycode") String categorycode,
			@PathVariable("barcode") String barcode,
			@PathVariable("unitquantity") String unitquantity,
			@PathVariable("packquantity") String packquantity,
			@PathVariable("retailprice") String retailprice,
			@PathVariable("discount") String discount,
			@PathVariable("packweight") String packweight,
			@PathVariable("packmass") String packmass,
			@PathVariable("compareweight") String compareweight,
			@PathVariable("comparemass") String comparemass,
			@PathVariable("gst") String gst) throws Exception {
		
		logger.info("Received request to show updateproductmaindetails POST");
		
		List<Product> updatemessage = null;
		try{
			updatemessage = productService.updateproductmaindetails(productcode, brand, productname, categorycode, barcode, unitquantity, packquantity, retailprice, discount, packweight, packmass, compareweight, comparemass, gst);
		}catch(Exception e){
			e.printStackTrace();
		}
		return updatemessage;
	}
	
	@RequestMapping(value = "/updateproductotherdetails/{productcode:.+}/{brand:.+}/{productname:.+}/{categorycode:.+}/{barcode:.+}/{stockleveldays:.+}/{checkoutweight:.+}/{inventorylevel:.+}/{description:.+}/{keepfresh:.+}/{active:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Product> updateproductotherdetailsPOST(
			@PathVariable("productcode") String productcode,
			@PathVariable("brand") String brand,
			@PathVariable("productname") String productname,
			@PathVariable("categorycode") String categorycode,
			@PathVariable("barcode") String barcode,
			@PathVariable("stockleveldays") String stockleveldays,
			@PathVariable("checkoutweight") String checkoutweight,
			@PathVariable("inventorylevel") String inventorylevel,
			@PathVariable("description") String description,
			@PathVariable("keepfresh") String keepfresh,
			@PathVariable("active") String active
			) throws Exception {
		
		logger.info("Received request to show updateproductdetails POST");
		
		List<Product> updatemessage = null;
		try{
			updatemessage = productService.updateproductotherdetails(productcode, brand, productname, categorycode, barcode, stockleveldays, checkoutweight, inventorylevel, description, keepfresh, active);
		}catch(Exception e){
			e.printStackTrace();
		}
		return updatemessage;
	}
			 
	@RequestMapping(value = "/updatepromotional1/{productcode:.+}/{retailprice:.+}/{discount:.+}/{discountamount:.+}/{promotionalprice:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Product> updatepromotional1POST(
			@PathVariable("productcode") String productcode,
			@PathVariable("retailprice") String retailprice,
			@PathVariable("discount") String discount,
			@PathVariable("discountamount") String discountamount,
			@PathVariable("promotionalprice")String  promotionalprice) throws Exception {
		
		logger.info("Received request to show updatepromotional1 POST");
		
		List<Product> updatemessage = null;
		try{
			updatemessage = productService.updatepromotional1(productcode, retailprice, discount, discountamount, promotionalprice);
		}catch(Exception e){
			e.printStackTrace();
		}
		return updatemessage;
	}
	
	@RequestMapping(value="/submituploadfile", method = RequestMethod.POST)
	public String submituploadfilePOST(
			@RequestParam(value="productcode", required=true) String productcode,
			@RequestParam(value="imageA", required=true) MultipartFile imageA,
			@RequestParam(value="imageB", required=false) MultipartFile imageB,
			@RequestParam(value="imageC", required=false) MultipartFile imageC,
			@RequestParam(value="imageD", required=false) MultipartFile imageD,
			@RequestParam(value="imageE", required=false) MultipartFile imageE,
			ModelMap model) throws Exception {
		
		logger.info("Received request to show submituploadfile POST");
		
		String uploadingmessage = null;
		try{
			uploadingmessage = productService.submituploadfile(productcode, imageA, imageB, imageC, imageD, imageE);
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("message", uploadingmessage);
		return "uploadimages";
	}
	
	@RequestMapping(value = "/submitNewProduct", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Product> submitNewProduct(@RequestBody AddProductSupplierVO addproductsuppliervo			
			) throws Exception {
			
		logger.info("Received request to show submitNewProduct POST");
		
		List<Product> saveproductsupplier = null;
		try{
			saveproductsupplier = productService.submitNewProduct(
					addproductsuppliervo.getSupplierCode(), addproductsuppliervo.getSupplierPackqty(), 
					addproductsuppliervo.getPackUnit(), addproductsuppliervo.getPackPrice(), 
					addproductsuppliervo.getPaymentTerms(), addproductsuppliervo.getStoreCode(), 
					addproductsuppliervo.getCategoryCode(), addproductsuppliervo.getBrandName(), 
					addproductsuppliervo.getProductName(), addproductsuppliervo.getBarcode(), 
					addproductsuppliervo.getUnitQuantity(), addproductsuppliervo.getPackQuantity(), 
					addproductsuppliervo.getrRPrice(), addproductsuppliervo.getPackWeight(), 
					addproductsuppliervo.getPackMass(), addproductsuppliervo.getGst(), 
					addproductsuppliervo.getCompareWeight(), addproductsuppliervo.getCompareMass(), 
					addproductsuppliervo.getCheckoutWeight(), addproductsuppliervo.getDiscount(), 
					addproductsuppliervo.getInventoryLevel(), addproductsuppliervo.getStockLevelDays(), 
					addproductsuppliervo.getKeepFresh(), addproductsuppliervo.getDescription());
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return saveproductsupplier;
	}

}
