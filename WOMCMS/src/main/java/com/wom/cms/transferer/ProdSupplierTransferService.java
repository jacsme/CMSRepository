package com.wom.cms.transferer;

import java.util.List;

import org.hibernate.Session;

import com.wom.cms.model.Product;
import com.wom.cms.model.Supplier;
import com.wom.cms.vo.ProductSupplierVO;
import com.wom.cms.vo.SupplierVO;

public interface ProdSupplierTransferService {
	public List<ProductSupplierVO> generatePSTransfer(List<Product> productlist, Session session ) throws Exception;
	public List<SupplierVO> generateSuppTransfer(List<Supplier> supplierlist, Session session ) throws Exception;
}
