package com.wom.cms.dao;

import java.util.List;

import com.wom.cms.model.PurchaseRequest;
import com.wom.cms.vo.POAmendmentVO;
import com.wom.cms.vo.POSupplierVO;

public interface PurchaseOrderDao {
	public List<POAmendmentVO> searchPOAmendmentList(String suppliercode, String pocode, String productcode) throws Exception;
	public List<POSupplierVO> searchPurchaseOrder(String purchaseordercode, String suppliername, String dateissued) throws Exception;
	public List<PurchaseRequest> saveEditedPOAmendment(POAmendmentVO poamendment) throws Exception;
}
