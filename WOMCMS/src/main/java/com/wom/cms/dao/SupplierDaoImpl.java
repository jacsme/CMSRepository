package com.wom.cms.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.cms.factory.FactoryEntityService;
import com.wom.cms.factory.FactoryEntityServiceImpl;
import com.wom.cms.model.Supplier;
import com.wom.cms.transferer.ProdSupplierTransferService;
import com.wom.cms.util.HelperUtil;
import com.wom.cms.util.HibernateUtil;
import com.wom.cms.util.ResultGeneratorUtil;
import com.wom.cms.vo.SupplierVO;

public class SupplierDaoImpl implements SupplierDao{

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	ProdSupplierTransferService prodsuppliertransferService;
	
	FactoryEntityService<Supplier> factorySupplier = new FactoryEntityServiceImpl<Supplier>();
	
	static final Logger logger = Logger.getLogger(SupplierDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SupplierVO> getSupplierList() throws Exception {
		
		Session session = HibernateUtil.callSession(sessionFactory);
		List<Supplier> supplierlist = null;
		List<SupplierVO> suppliervo = null;
		try {
			supplierlist = session.createCriteria(Supplier.class).list();
			suppliervo = prodsuppliertransferService.generateSuppTransfer(supplierlist, session);
			
		} catch (Exception e) {
			logger.error("Error Message " + e.getMessage());
		}finally{
			HibernateUtil.callClose(sessionFactory);
		}
		return suppliervo;
	}
	
	@Override
	public List<Supplier> submitNewSupplier(String suppliername, String address, 
			String phone, String fax, String website, String email, 
			String contactperson, String gstid, String contactpersonphone) throws Exception {
		
		List<Supplier> savesupplier = new ArrayList<Supplier>();
		try {
			Session session = HibernateUtil.callSession(sessionFactory);
			
			String suppliercode = ResultGeneratorUtil.codeGenerator("", "sq_supplier_code", "SU22", session);
			BigInteger supplierid = ResultGeneratorUtil.idGenerator("", "sq_supplier_id", session);
			Supplier supplier = new Supplier(supplierid, suppliercode, suppliername, address, phone, fax, website, 
					email, contactperson, gstid, contactpersonphone);
			session.save(supplier);
			savesupplier.add(supplier);
			HibernateUtil.callCommit(sessionFactory);
		} catch (Exception e) {
			HibernateUtil.callRollBack(sessionFactory);
		}finally{
			HibernateUtil.callClose(sessionFactory);
		}
		return savesupplier;
	}
	
}
