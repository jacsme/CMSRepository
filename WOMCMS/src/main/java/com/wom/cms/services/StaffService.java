package com.wom.cms.services;


public interface StaffService {
	public String submitNewPassword(String userid, String password) throws Exception;
	public String submitLogin(String userid, String password) throws Exception;
}
