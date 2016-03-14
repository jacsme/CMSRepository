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

import com.wom.cms.model.PurchaseRequest;
import com.wom.cms.services.PurchaseOrderService;
import com.wom.cms.vo.POAmendmentVO;
import com.wom.cms.vo.POSupplierVO;

@Controller
@RequestMapping("/cms")
public class PurchaseOrderController {

	@Autowired
	PurchaseOrderService purchaseOrderService;
	
	static final Logger logger = Logger.getLogger(PurchaseOrderController.class);
	
	@RequestMapping(value="/purchaseorders", method = RequestMethod.GET)
	public String purchaseordersGET(ModelMap model) {
		logger.info("Received request to show Purchase Orders GET");
		model.addAttribute("message", "Welcome");
		return "purchaseorders";
	}
	
	@RequestMapping(value="/poamendment", method = RequestMethod.GET)
	public String poamendmentGET(ModelMap model) {
		logger.info("Received request to show PO Amendment GET");
		model.addAttribute("message", "Welcome");
		return "poamendment";
	}
	
	/** GET Request 
	 * @throws JSONException **/
	@RequestMapping(value = "/searchPurchaseOrder/{purchaseordercode:.+}/{suppliername:.+}/{dateissued:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<POSupplierVO> searchPurchaseOrderGET(@PathVariable("purchaseordercode") String purchaseordercode,
			@PathVariable("suppliername") String suppliername, @PathVariable("dateissued") String dateissued) throws Exception{
		
		logger.info(" Request for searchPurchaseOrderGET() " + purchaseordercode + "/" + suppliername + "/" + dateissued);
		
		List<POSupplierVO> searchpurchaseorderlist = null;
		try{
			searchpurchaseorderlist = purchaseOrderService.searchPurchaseOrder(purchaseordercode, suppliername, dateissued);
		}catch(Exception e){
			e.printStackTrace();
		}
		return searchpurchaseorderlist;
	}
	
	/** GET Request 
	 * @throws JSONException **/
	@RequestMapping(value = "/searchPOAmendmentList/{suppliercode:.+}/{pocode:.+}/{productcode:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<POAmendmentVO> searchPOAmendmentListGET(@PathVariable("suppliercode") String suppliercode,
			@PathVariable("pocode") String pocode, @PathVariable("productcode") String productcode) throws Exception{
		
		logger.info(" Request for searchPOAmendmentList/" + suppliercode + "/" + pocode + "/" + productcode);
		
		List<POAmendmentVO> searchpoamendmentlist = null;
		try{
			searchpoamendmentlist = purchaseOrderService.searchPOAmendmentList(suppliercode, pocode, productcode);
		}catch(Exception e){
			e.printStackTrace();
		}
		return searchpoamendmentlist;
	}
	
	@RequestMapping(value = "/saveEditedPOAmendment", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<PurchaseRequest> submitNewProduct(@RequestBody POAmendmentVO poamendmentvo			
			) throws Exception {
			
		List<PurchaseRequest> saveeditedpoamendment = null;
		try{
			saveeditedpoamendment = purchaseOrderService.saveEditedPOAmendment(poamendmentvo);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return saveeditedpoamendment;
	}

}
