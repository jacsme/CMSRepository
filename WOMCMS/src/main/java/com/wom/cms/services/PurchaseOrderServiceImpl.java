package com.wom.cms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wom.cms.dao.PurchaseOrderDao;
import com.wom.cms.model.PurchaseRequest;
import com.wom.cms.vo.POAmendmentVO;
import com.wom.cms.vo.POSupplierVO;

public class PurchaseOrderServiceImpl implements PurchaseOrderService{
	@Autowired
	PurchaseOrderDao purchaseorderdao;
	
	@Override
	public List<POAmendmentVO> searchPOAmendmentList(String suppliercode, String pocode, String productcode) throws Exception{
		return purchaseorderdao.searchPOAmendmentList(suppliercode, pocode, productcode);
	}
	
	@Override
	public List<POSupplierVO> searchPurchaseOrder(String purchaseordercode, String suppliername, String dateissued) throws Exception{
		return purchaseorderdao.searchPurchaseOrder(purchaseordercode, suppliername, dateissued);
	}

	@Override
	public List<PurchaseRequest> saveEditedPOAmendment(POAmendmentVO poamendment) throws Exception {
		return purchaseorderdao.saveEditedPOAmendment(poamendment);
	}
	
}
