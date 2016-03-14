package com.wom.cms.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.wom.cms.model.Category;
import com.wom.cms.model.Product;
import com.wom.cms.vo.ProductSupplierVO;

public interface ProductService {
	public List<Product> searchProductCode(String productcode, String brand, String categorycode) throws Exception;
	public String submituploadfile(String productcode, MultipartFile imageA, MultipartFile imageB,
			MultipartFile imageC, MultipartFile imageD, MultipartFile imageE) throws Exception;
	public List<Product> updateproductmaindetails(String productcode, String brand, String productname,
			 String categorycode, String barcode, String unitquantity, String packquantity, String retailprice, 
			 String discount, String packweight, String packmass, 
			 String compareprice, String comparemass, String gst) throws Exception;
	public List<Product> updateproductotherdetails(String productcode, String brand, String productname,
			 String categorycode, String barcode,  String stockleveldays, String checkoutweight, String inventorylevel, 
			 String description, String keepfresh, String active) throws Exception;

	public List<Product> updatepromotional1(String productcode, String retailprice, String discount,
			String discountamount, String promotionalprice) throws Exception;
	public List<ProductSupplierVO> searchPromoProducts(String productcode, String brand, String categorycode) throws Exception;
	public List<Category> getCategoryList() throws Exception;
	public List<Product> submitNewProduct(String suppliercode, String supppackquantity, String packunit, String packprice, String paymentterms, 
			String storecode, String categorycode, String brand, String productname, 
			String photocode, String unitquantity, String packquantity, String rrprice, 
			String packweight, String packmass, String gst, String compareweight, String comparemass, 
			String checkoutweight, String discount, String inventorylevel, String stockleveldays, String keepfresh,
			String description) throws Exception;
}
