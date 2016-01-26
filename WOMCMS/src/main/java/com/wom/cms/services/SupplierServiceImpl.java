package com.wom.cms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wom.cms.dao.SupplierDao;
import com.wom.cms.model.ProductSupplier;
import com.wom.cms.model.Supplier;
import com.wom.cms.vo.SupplierProductVO;
import com.wom.cms.vo.SupplierVO;

public class SupplierServiceImpl implements SupplierService {

	@Autowired
	SupplierDao supplierDao;
	
	@Override
	public List<SupplierVO> getSupplierList() throws Exception{
		return supplierDao.getSupplierList();
	}

	@Override
	public List<SupplierProductVO> getSupplierProductList(String suppliercode, String photocode, String brandname) throws Exception {
		return supplierDao.getSupplierProductList(suppliercode, photocode, brandname);
	}
	
	@Override
	public List<Supplier> submitNewSupplier(String suppliername, String address, String phone, String fax,
			String website, String email, String contactperson, String gstid, String contactpersonphone)
			throws Exception {
		return supplierDao.submitNewSupplier(suppliername, address, phone, fax, website, email, 
				contactperson, gstid, contactpersonphone);
	}

	@Override
	public List<ProductSupplier> updateSupplierProduct(String suppliercode, String productcode, String packunit,
			String packprice, String paymentterms) throws Exception {
		return supplierDao.updateSupplierProduct(suppliercode, productcode, packunit, packprice, paymentterms);
	}

	
}
