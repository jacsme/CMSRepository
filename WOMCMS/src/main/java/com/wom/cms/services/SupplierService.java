package com.wom.cms.services;

import java.util.List;

import com.wom.cms.model.Supplier;
import com.wom.cms.vo.SupplierVO;

public interface SupplierService {
	public List<SupplierVO> getSupplierList() throws Exception;
	public List<Supplier> submitNewSupplier(String suppliername, String address, 
			String phone, String fax, String website, String email, 
			String contactperson, String gstid, String contactpersonphone) throws Exception;
}
