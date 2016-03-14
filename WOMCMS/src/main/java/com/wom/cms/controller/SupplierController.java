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
import org.springframework.web.bind.annotation.ResponseBody;

import com.wom.cms.model.ProductSupplier;
import com.wom.cms.model.Supplier;
import com.wom.cms.services.SupplierService;
import com.wom.cms.vo.SupplierProductVO;
import com.wom.cms.vo.SupplierVO;

@Controller
@RequestMapping("/cms")
public class SupplierController {
	
	@Autowired
	SupplierService supplierService;

	static final Logger logger = Logger.getLogger(SupplierController.class);
	
	@RequestMapping(value="/addnewsupplierpage", method = RequestMethod.GET)
	public String addnewsupplierGET(ModelMap model) {
		logger.info("Received request to show addnewsupplierpageGET");
		model.addAttribute("message", "Welcome");
		return "addnewsupplier";
	}
	
	@RequestMapping(value="/editsupplierproductpage", method = RequestMethod.GET)
	public String editsupplierproductpageGET(ModelMap model) {
		logger.info("Received request to show editsupplierproductpageGET");
		model.addAttribute("message", "Welcome");
		return "editsupplierproductlist";
	}
	
	
	@RequestMapping(value = "/getSupplierList", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<SupplierVO> getSupplierListGET() throws JSONException{
		logger.info(" Request for getSupplierListGET");
		
		List<SupplierVO> supplierlist =  null;
		
		try { 
			supplierlist = supplierService.getSupplierList();
		} catch (Exception e) {
			logger.error(" Error Message: " + e.getMessage());
		}
		return supplierlist;
	}
	
	@RequestMapping(value = "/submitNewSupplier", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Supplier> submitNewSupplierPOST(@RequestBody SupplierVO suppliervo) throws JSONException{
		logger.info(" Request for submitNewSupplier");
		
		List<Supplier> newsupplier =  null;
		
		try { 
			newsupplier = supplierService.submitNewSupplier(suppliervo.getSupplierName(),
					suppliervo.getAddress(), suppliervo.getPhone(), 
					suppliervo.getFax(), suppliervo.getWebsite(), suppliervo.getEmail(), 
					suppliervo.getContactPerson(), suppliervo.getGstID(), 
					suppliervo.getContactPersonPhone());
		} catch (Exception e) {
			logger.error(" Error Message: " + e.getMessage());
		}
		return newsupplier;
	}
	
	@RequestMapping(value = "/getSupplierProductList/{suppliercode:.+}/{brandname:.+}/{productcode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<SupplierProductVO> getSupplierProductListGET(
			@PathVariable("suppliercode") String suppliercode, 
			@PathVariable("brandname") String brandname, 
			@PathVariable("productcode") String productcode ) throws JSONException{
		logger.info(" Request for getSupplierProductListGET");
		
		List<SupplierProductVO> supplierproductlist =  null;
		
		try { 
			supplierproductlist = supplierService.getSupplierProductList(suppliercode, productcode, brandname);
		} catch (Exception e) {
			logger.error(" Error Message: " + e.getMessage());
		}
		return supplierproductlist;
	}
	
	@RequestMapping(value = "/updateSupplierProduct/{suppliercode:.+}/{productcode:.+}/{packunit:.+}/{packprice:.+}/{paymentterms:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<ProductSupplier> updateSupplierProductPOST(
			@PathVariable("suppliercode") String suppliercode, 
			@PathVariable("productcode") String productcode, 
			@PathVariable("packunit") String packunit,
			@PathVariable("packprice") String packprice, 
			@PathVariable("paymentterms") String paymentterms) throws Exception {
		
		logger.info("Received request to update updateSupplierProduct POST");
		
		List<ProductSupplier> updatemessage = null;
		try{
			updatemessage = supplierService.updateSupplierProduct(suppliercode, productcode, packunit, packprice, paymentterms);
		}catch(Exception e){
			e.printStackTrace();
		}
		return updatemessage;
	}

	@RequestMapping(value = "/addSupplierProduct/{suppliercode:.+}/{productcode:.+}/{packunit:.+}/{packprice:.+}/{paymentterms:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<SupplierProductVO> addproductmaindetailsPOST(
			@PathVariable("suppliercode") String suppliercode, 
			@PathVariable("productcode") String productcode, 
			@PathVariable("packunit") String packunit,
			@PathVariable("packprice") String packprice, 
			@PathVariable("paymentterms") String paymentterms) throws Exception {
		
		logger.info("Received request to update updateSupplierProduct POST");
		
		List<SupplierProductVO> supplierproductlist =  null;
		try{
			supplierService.addSupplierProduct(suppliercode, productcode, packunit, packprice, paymentterms);
			supplierproductlist = supplierService.getSupplierProductList(suppliercode, "-", "-");
		}catch(Exception e){
			e.printStackTrace();
		}
		return supplierproductlist;
	}
}
