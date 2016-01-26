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

import com.wom.cms.services.SalesService;
import com.wom.cms.vo.SalesOrderVO;

@Controller
@RequestMapping("/cms")
public class SalesController {
	
	@Autowired
	SalesService salesService;
	
	static final Logger logger = Logger.getLogger(SalesController.class);
		
	@RequestMapping(value="/salesorder", method = RequestMethod.GET)
	public String getSalesOrdersGET(ModelMap model) {
		logger.info("Received request to show Sales Order GET");
		model.addAttribute("message", "Welcome");
		return "salesorder";
	}
	
	
	/** GET Request 
	 * @throws JSONException **/
	@RequestMapping(value = "/searchSalesOrder/{saleseordercode:.+}/{customercode:.+}/{datedelivered:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<SalesOrderVO> searchSalesOrderGET(@PathVariable("saleseordercode") String saleseordercode,
			@PathVariable("customercode") String customercode, @PathVariable("datedelivered") String datedelivered) throws Exception{
		
		logger.info(" Request for searchSalesOrderGET() " + saleseordercode + "/" + customercode + "/" + datedelivered);
		
		List<SalesOrderVO> searchsalesorderlist = null;
		try{
			searchsalesorderlist = salesService.searchSalesOrder(saleseordercode, customercode, datedelivered);
		}catch(Exception e){
			e.printStackTrace();
		}
		return searchsalesorderlist;
	}
	
}
