package com.wom.cms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.wom.cms.dao.ProductDao;
import com.wom.cms.model.Category;
import com.wom.cms.model.Product;
import com.wom.cms.vo.ProductSupplierVO;

public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductDao productDao;
	
	@Override
	public List<Product> searchProductCode(String productcode, String brand, String categorycode) throws Exception{
		return productDao.searchProductCode(productcode, brand, categorycode);
	}

	@Override
	public String submituploadfile(String productcode, MultipartFile imageA, MultipartFile imageB,
			MultipartFile imageC, MultipartFile imageD, MultipartFile imageE) throws Exception {
		return productDao.submituploadfile(productcode, imageA, imageB, imageC, imageD, imageE);
	}

	@Override
	public List<Product> updateproductmaindetails(String productcode, String brand, String productname,
			 String categorycode, String barcode, String unitquantity, String packquantity, String retailprice, 
			 String discount, String packweight, String packmass, 
			 String compareweight, String comparemass, String gst) throws Exception {
		return productDao.updateproductmaindetails(productcode, brand, productname, categorycode, barcode, unitquantity, packquantity, retailprice, 
				discount, packweight, packmass, compareweight, comparemass, gst);
	}

	@Override
	public List<Product> updateproductotherdetails(String productcode, String brand, String productname,
			String categorycode, String barcode, String stockleveldays, String checkoutweight, String inventorylevel,
			String description, String keepfresh, String active) throws Exception {
		return productDao.updateproductotherdetails(productcode, brand, productname, categorycode, barcode, stockleveldays, checkoutweight, inventorylevel, description, keepfresh, active);
	}

	@Override
	public List<Product> updatepromotional1(String productcode, String retailprice,
			String discount, String discountamount, String promotionalprice) throws Exception {
		return productDao.updatepromotional1(productcode, retailprice, discount, discountamount, promotionalprice);
	}

	@Override
	public List<ProductSupplierVO> searchPromoProducts(String productcode, String brand, String categorycode) throws Exception {
		return productDao.searchPromoProducts(productcode, brand, categorycode);
	}

	@Override
	public List<Category> getCategoryList() throws Exception {
		return productDao.getCategoryList();
	}

	@Override
	public List<Product> submitNewProduct(String suppliercode, String supppackquantity, String packunit, String packprice,
			String paymentterms, String storecode, String categorycode, String brand, String productname,
			String photocode, String unitquantity, String packquantity, String rrprice, String packweight,
			String packmass, String gst, String compareweight, String comparemass, String checkoutweight,
			String discount, String inventorylevel, String stockleveldays, String keepfresh, String description)
			throws Exception {
		return productDao.submitNewProduct(suppliercode, supppackquantity, packunit, packprice, paymentterms, storecode, categorycode, 
				brand, productname, photocode, unitquantity, packquantity, rrprice, packweight, packmass, gst, compareweight, comparemass, 
				checkoutweight, discount, inventorylevel, stockleveldays, keepfresh, description);
	}
}
