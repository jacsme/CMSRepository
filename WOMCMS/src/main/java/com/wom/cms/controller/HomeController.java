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
public class HomeController {
		
		@Autowired
		StaffService staffService;
		
		protected static Logger logger = Logger.getLogger(HomeController.class);

		@RequestMapping(value="/home", method = RequestMethod.GET)
		public String getHomePage(ModelMap model) {
			logger.info("Received request to show Home GET");
			model.addAttribute("message", "Welcome");
			return "mainpage";
		}
		
		@RequestMapping(value="/home", method = RequestMethod.POST)
		public String postHomePage(ModelMap model) {
			logger.info("Received request to show Home POST");
			model.addAttribute("message", "Welcome");
			return "mainpage";
		}
		
		@RequestMapping(value="/login", method = RequestMethod.GET)
		public String getLoginPage(ModelMap model) {
			logger.info("Received request to Login GET");
			model.addAttribute("message", "Welcome");
			return "login";
		}
		
		@RequestMapping(value="/login", method = RequestMethod.POST)
		public String postLoginPage(ModelMap model) {
			logger.info("Received request to Login POST");
			model.addAttribute("message", "Welcome");
			return "login";
		}
		
		@RequestMapping(value="/submitlogin", method = RequestMethod.POST)
		public String submitloginPOST(
				@RequestParam(value="userid", required=true) String userid,
				@RequestParam(value="password", required=true) String password,
				ModelMap model) throws Exception {
			
			logger.info("Received request to show submitlogin POST");
			
			String loginresult = null;
			String directpage = null;
			String message = null;
			try{
				loginresult = staffService.submitLogin(userid, password);
				
				if(loginresult.equalsIgnoreCase("200")){
					directpage = "mainpage";
					message = "Successfully Login";
				}else{
					directpage = "login";
					message = "User Id or Password is incorrect";
				}
					
			}catch(Exception e){
				e.printStackTrace();
			}
			model.addAttribute("message", message);
			return directpage;
		}
	}
