package com.wom.cms.transferer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.wom.cms.factory.FactoryEntityService;
import com.wom.cms.factory.FactoryEntityServiceImpl;
import com.wom.cms.model.Product;
import com.wom.cms.model.ProductSupplier;
import com.wom.cms.model.Supplier;
import com.wom.cms.util.HelperUtil;
import com.wom.cms.vo.ProductSupplierVO;
import com.wom.cms.vo.SupplierVO;

public class ProdSupplierTransferServiceImpl implements ProdSupplierTransferService{
	
	FactoryEntityService<ProductSupplier> factoryProductSupplier = new FactoryEntityServiceImpl<ProductSupplier>();
	FactoryEntityService<Supplier> factorySupplier = new FactoryEntityServiceImpl<Supplier>();
	
	@Override
	public List<ProductSupplierVO> generatePSTransfer(List<Product> productlist, Session session ) throws Exception{
		
		List<ProductSupplierVO> productsupplierlist = new ArrayList<ProductSupplierVO>();
		ProductSupplierVO pruductsuppliervo = new ProductSupplierVO();
		BigDecimal packprice = new BigDecimal(0.00);
		BigDecimal buyingprice = new BigDecimal(0.00);
		BigDecimal packunit = new BigDecimal(0.00);
		
		for (Product prolist:productlist){
			
			List<ProductSupplier> productsupplier = factoryProductSupplier.getEntityProductSupplier(HelperUtil.checkNullString(prolist.getProductCode()), session);
			if(productsupplier.size() != 0){
				for(ProductSupplier prosupp : productsupplier) {
					pruductsuppliervo = new ProductSupplierVO();
					
					packprice = new BigDecimal((String) HelperUtil.checkNullAmount(prosupp.getPackPrice()));
					packunit = new BigDecimal((String) HelperUtil.checkNullNumbers(prosupp.getPackUnit()));
					
					if(packunit.compareTo(new BigDecimal(0.00)) == 1){
						buyingprice = packprice.divide(packunit, 2, RoundingMode.HALF_UP);
						pruductsuppliervo.setBuyingPrice(buyingprice.toString());
					}
					
					Supplier supplier = factorySupplier.getEntitySupplier(HelperUtil.checkNullString(prosupp.getSupplierCode()), session);
					if(supplier != null){
						pruductsuppliervo.setSupplierCode(HelperUtil.checkNullString(supplier.getSupplierCode()));
						pruductsuppliervo.setSupplierName(HelperUtil.checkNullString(supplier.getSupplierName()));
					}else{
						pruductsuppliervo.setSupplierCode("-");
						pruductsuppliervo.setSupplierName("-");
					}
					
					pruductsuppliervo.setProductCode(HelperUtil.checkNullString(prolist.getProductCode())); 
					pruductsuppliervo.setBrandName(HelperUtil.checkNullString(prolist.getBrand()));
					pruductsuppliervo.setProductName(HelperUtil.checkNullString(prolist.getProductName()) + " " + HelperUtil.checkNullString(prolist.getPackWeight()) + " " + HelperUtil.checkNullString(prolist.getPackMass())); 
					
					pruductsuppliervo.setRetailPrice(HelperUtil.checkNullAmount(prolist.getrRPrice())); 
					pruductsuppliervo.setDiscount(HelperUtil.checkNullNumbers(prolist.getDiscount())); 
					pruductsuppliervo.setDiscountAmount(HelperUtil.checkNullAmount(prolist.getDiscountamount()));
					pruductsuppliervo.setPromotionalPrice(HelperUtil.checkNullAmount(prolist.getPromotionalPrice()));
					
					productsupplierlist.add(pruductsuppliervo);
				}
			}
		}
			
		return productsupplierlist;
	}
	
	@Override
	public List<SupplierVO> generateSuppTransfer(List<Supplier> supplierlist, Session session ) throws Exception{
		
		List<SupplierVO> supplierlisting = new ArrayList<SupplierVO>();
		
		for (Supplier supplist:supplierlist){
			SupplierVO suppliervo = new SupplierVO();
			suppliervo.setSupplierCode(HelperUtil.checkNullString(supplist.getSupplierCode()));
			suppliervo.setSupplierName(HelperUtil.checkNullString(supplist.getSupplierName()));
			
			supplierlisting.add(suppliervo);
		}
			
		return supplierlisting;
	}

}
