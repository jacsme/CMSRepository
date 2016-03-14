package com.wom.cms.controller;

import java.util.ArrayList;
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

import com.wom.cms.model.Inventory;
import com.wom.cms.services.InventoryService;
import com.wom.cms.vo.InventorySummaryVO;

@Controller
@RequestMapping("/cms")
public class SummaryController {
	
	@Autowired
	InventoryService inventoryService;
	
	static final Logger logger = Logger.getLogger(SummaryController.class);

	@RequestMapping(value="/inventorysummary", method = RequestMethod.GET)
	public String getInventorySummary(ModelMap model) {
		logger.info("Received request to show Inventory Summary");
		model.addAttribute("message", "Inventory");
		return "inventorysumry";
	}
	
	@RequestMapping(value="/returnstocks", method = RequestMethod.GET)
	public String getReturnStocks(ModelMap model) {
		logger.info("Received request to show Return Inventory");
		model.addAttribute("message", "Inventory");
		return "returnproducts";
	}
	
	@RequestMapping(value="/salessummary", method = RequestMethod.GET)
	public String getSalesSummary(ModelMap model) {
		logger.info("Received request to show Sales Summary");
		model.addAttribute("message", "Sales");
		return "salessumry";
	}
	
	/** GET Request 
	 * @throws JSONException **/
	@RequestMapping(value = "/searchInventoryList/{productcode:.+}/{stocklevel:.+}/{stocklocation:.+}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<InventorySummaryVO> searchProductCodeGET(@PathVariable("productcode") String productcode,
			@PathVariable("stocklevel") String stocklevel, @PathVariable("stocklocation") String stocklocation) throws Exception{
		
		logger.info(" searchInventoryList/ " + productcode + "/" + stocklevel + "/" + stocklocation);
		List<InventorySummaryVO> searchInventoryList = new ArrayList<InventorySummaryVO>();
		
		try{
			searchInventoryList = inventoryService.searchInventoryList(productcode, stocklevel, stocklocation);
		}catch(Exception e){
			e.printStackTrace();
		}
		return searchInventoryList;
	}
	
	@RequestMapping(value = "/updateInventoryLocation/{location:.+}/{productcode:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Inventory> updateInventoryLocationPOST(
			@PathVariable("location") String location,
			@PathVariable("productcode") String productcode
			) throws Exception {
		
		logger.info("/updateInventoryLocation/" + location + "/" + productcode);
		
		List<Inventory> updatemessage = null;
		try{
			updatemessage = inventoryService.updateInventoryLocation(location, productcode);
		}catch(Exception e){
			e.printStackTrace();
		}
		return updatemessage;
	}
	
	@RequestMapping(value = "/addReturnUnits/{location:.+}/{productcode:.+}/{units:.+}/{comments:.+}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Inventory> addReturnUnitsPOST(
			@PathVariable("location") String location,
			@PathVariable("productcode") String productcode,
			@PathVariable("units") String units,
			@PathVariable("comments") String comments
			) throws Exception {
		
		logger.info("/addReturnUnits/" + location + "/" + productcode + "/" + units + "/" + comments);
		
		List<Inventory> updatemessage = null;
		try{
			updatemessage = inventoryService.addReturnUnits(location, productcode, units, comments);
		}catch(Exception e){
			e.printStackTrace();
		}
		return updatemessage;
	}
}
