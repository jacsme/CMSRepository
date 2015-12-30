package com.wom.cms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wom.cms.dao.SupplierDao;
import com.wom.cms.model.Supplier;
import com.wom.cms.vo.SupplierVO;

public class SupplierServiceImpl implements SupplierService {

	@Autowired
	SupplierDao supplierDao;
	
	@Override
	public List<SupplierVO> getSupplierList() throws Exception{
		return supplierDao.getSupplierList();
	}

	@Override
	public List<Supplier> submitNewSupplier(String suppliername, String address, String phone, String fax,
			String website, String email, String contactperson, String gstid, String contactpersonphone)
			throws Exception {
		return supplierDao.submitNewSupplier(suppliername, address, phone, fax, website, email, 
				contactperson, gstid, contactpersonphone);
	}
}
