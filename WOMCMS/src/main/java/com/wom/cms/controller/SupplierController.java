package com.wom.cms.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wom.cms.model.Supplier;
import com.wom.cms.services.SupplierService;
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
	
	@RequestMapping(value = "/submitNewSupplier/{suppliername:.+}/{address:.+}/{phone:.+}/{fax:.+}/{website:.+}/{email:.+}/{contactperson:.+}/{gstid:.+}/{contactpersonphone:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Supplier> submitNewSupplierPOST(
			@PathVariable("suppliername") String suppliername, @PathVariable("address") String address, 
			@PathVariable("phone") String phone, @PathVariable("fax") String fax, 
			@PathVariable("website") String website, @PathVariable("email") String email, 
			@PathVariable("contactperson") String contactperson, @PathVariable("gstid") String gstid, 
			@PathVariable("contactpersonphone") String contactpersonphone) throws JSONException{
		logger.info(" Request for submitNewSupplier");
		
		List<Supplier> newsupplier =  null;
		
		try { 
			newsupplier = supplierService.submitNewSupplier(suppliername, address, phone, fax, website, email, contactperson, gstid, contactpersonphone);
		} catch (Exception e) {
			logger.error(" Error Message: " + e.getMessage());
		}
		return newsupplier;
	}
}
