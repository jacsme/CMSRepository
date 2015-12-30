package com.wom.cms.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.wom.cms.dao.StaffDao;

public class StaffServiceImpl implements StaffService {

	@Autowired
	StaffDao staffDao;
	
	@Override
	public String submitNewPassword(String userid, String password) throws Exception {
		return staffDao.submitNewPassword(userid, password);
	}

	@Override
	public String submitLogin(String userid, String password) throws Exception {
		return staffDao.submitLogin(userid, password);
	}
}
