package com.wom.cms.dao;

import java.util.List;

import com.wom.cms.model.ProductSupplier;
import com.wom.cms.model.Supplier;
import com.wom.cms.vo.SupplierProductVO;
import com.wom.cms.vo.SupplierVO;

public interface SupplierDao {
	public List<SupplierVO> getSupplierList() throws Exception;
	public List<Supplier> submitNewSupplier(String suppliername, String address, 
			String phone, String fax, String website, String email, 
			String contactperson, String gstid, String contactpersonphone) throws Exception;
	public List<SupplierProductVO> getSupplierProductList(String suppliercode, String productcode, String brandname) throws Exception;
	public List<ProductSupplier> updateSupplierProduct(String suppliercode, String productcode, String packunit,
			String packprice, String paymentterms) throws Exception;
	public void addSupplierProduct(String suppliercode, String productcode, String packunit, String packprice,
			 String paymentterms) throws Exception;
}
