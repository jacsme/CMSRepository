package com.wom.cms.dao;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.wom.cms.constant.MainEnum;
import com.wom.cms.constant.StatusCode;
import com.wom.cms.crypt.DecryptionUtil;
import com.wom.cms.crypt.EncryptionUtil;
import com.wom.cms.factory.FactoryEntityService;
import com.wom.cms.factory.FactoryEntityServiceImpl;
import com.wom.cms.model.LoginUser;
import com.wom.cms.util.HibernateUtil;
@Transactional
public class StaffDaoImpl implements StaffDao{
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	Session session; 
	
	FactoryEntityService<LoginUser> factoryentityService = new FactoryEntityServiceImpl<LoginUser>();
	
	@Override
	public String submitNewPassword(String userid, String password) throws Exception {
		
		String encryptedpwd = null;
		String results = null;
		try {
			session = sessionFactory.openSession();
			LoginUser loginuser = factoryentityService.getEntity(MainEnum.LOGIN, userid, session);
			
			if (loginuser==null){
				results = "No Record Found";
			}else{
				encryptedpwd = EncryptionUtil.encrypt(password);
				loginuser.setPassword(encryptedpwd);
				session.save(loginuser);
				results = "You have successfully change your password";
			}
			HibernateUtil.callCommitClose(session);
		} catch (Exception e) {
			results = e.getMessage();
		}
		return results;
	}
	
	@Override
	public String submitLogin(String userid, String password) throws Exception {
		String results = null;
		try {
			session = sessionFactory.openSession();
			LoginUser loginuser = factoryentityService.getEntity(MainEnum.LOGIN, userid, session);
			
			if (loginuser==null){
				results = "No Record Found";
			}else{
				if(DecryptionUtil.decrypt(loginuser.getPassword()).equals(password)){
					results = StatusCode.SUCCESSFUL_CODE;
				}else{
					results = StatusCode.LOGIN_PASSWORD_ERROR_CODE;
				}
			}
		} catch (Exception e) {
			results = StatusCode.LOGIN_PASSWORD_ERROR_CODE;
		}finally{
			HibernateUtil.callClose(session);
		}
		return results;
	}

}
