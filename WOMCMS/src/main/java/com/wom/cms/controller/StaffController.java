package com.wom.cms.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wom.cms.services.StaffService;

@Controller
@RequestMapping("/cms")
public class StaffController {
	protected static Logger logger = Logger.getLogger(StaffController.class);

	@Autowired
	StaffService staffService;
	
	@RequestMapping(value="/staff", method = RequestMethod.GET)
	public String getStaffPage(ModelMap model) {
		logger.info("Received request to show Staff GET");
		model.addAttribute("message", "Welcome");
		return "staffpage";
	}
	
	@RequestMapping(value="/password", method = RequestMethod.GET)
	public String getPasswordPage(ModelMap model) {
		logger.info("Received request to show Password GET");
		model.addAttribute("message", "Note: Password should be alphanumeric only");
		return "changepassword";
	}
	
	@RequestMapping(value="/savepassword", method = RequestMethod.POST)
	public String postStaffPage(@RequestParam(value="userId", required=true) String userid,
			@RequestParam(value="newpassword", required=true) String password, 
			ModelMap model) throws Exception {
		logger.info("Received request to show Staff POST");
		
		String resultmessage = staffService.submitNewPassword(userid, password);
		model.addAttribute("message", resultmessage);
		
		return "changepassword";
	}
	
	
	
}
