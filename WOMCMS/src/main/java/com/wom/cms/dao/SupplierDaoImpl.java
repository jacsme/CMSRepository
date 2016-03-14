package com.wom.cms.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.wom.cms.constant.StatusCode;
import com.wom.cms.factory.FactoryEntityService;
import com.wom.cms.factory.FactoryEntityServiceImpl;
import com.wom.cms.model.ProductSupplier;
import com.wom.cms.model.Supplier;
import com.wom.cms.transferer.ProdSupplierTransferService;
import com.wom.cms.util.HelperUtil;
import com.wom.cms.util.HibernateUtil;
import com.wom.cms.util.ResultGeneratorUtil;
import com.wom.cms.vo.SupplierProductVO;
import com.wom.cms.vo.SupplierVO;
@Transactional
public class SupplierDaoImpl implements SupplierDao{

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	ProdSupplierTransferService prodsuppliertransferService;
	
	FactoryEntityService<Supplier> factorySupplier = new FactoryEntityServiceImpl<Supplier>();
	FactoryEntityService<ProductSupplier> factoryProductSupplier = new FactoryEntityServiceImpl<ProductSupplier>();
	
	static final Logger logger = Logger.getLogger(SupplierDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SupplierVO> getSupplierList() throws Exception {
		List<Supplier> supplierlist = null;
		List<SupplierVO> suppliervo = null;
		Session session = sessionFactory.openSession();
		try {
			supplierlist = session.createCriteria(Supplier.class)
					.addOrder(Order.asc("supplierName"))
					.list();
			suppliervo = prodsuppliertransferService.generateSuppTransfer(supplierlist, session);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return suppliervo;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SupplierProductVO> getSupplierProductList(String suppliercode, String productcode, String brandname) throws Exception {
		List<String> supplierproductlist = null;
		List<SupplierProductVO> supplierproductlistvo = new ArrayList<SupplierProductVO>();
		
		StringBuffer stringcriteria = new StringBuffer();
		Query criteria = null;
		Session session = sessionFactory.openSession();
		try {
			if (!productcode.equalsIgnoreCase("-")) {	stringcriteria.append(" AND D.PRODUCTCODE =:productcode "); }
			if (!suppliercode.equalsIgnoreCase("-")) { stringcriteria.append(" AND A.SUPPLIERCODE =:suppliercode ");}
			if (!brandname.equalsIgnoreCase("-")) { stringcriteria.append(" AND D.BRAND =:brandname ");}

			if(stringcriteria.length() != 0){
				criteria = session.createSQLQuery(" SELECT A.SUPPLIERCODE, A.SUPPLIERNAME, D.BRAND, "
						+ " D.PRODUCTCODE, D.PRODUCTNAME, D.PHOTOCODE, D.PACKWEIGHT, D.PACKMASS, "
						+ " C.PACKUNIT, C.PACKPRICE, C.PAYMENTTERMS, D.GST "
						+ " FROM tblsupplier A "
						+ " LEFT JOIN tblimages B ON A.SUPPLIERCODE = B.CODE "
						+ " INNER JOIN tblproductsupplier C ON C.SUPPLIERCODE=A.SUPPLIERCODE "
						+ " INNER JOIN tblproduct D ON D.PRODUCTCODE = C.PRODUCTCODE"
						+ " WHERE A.ACTIVE = 'YES' "
						+ stringcriteria.toString() + " ORDER BY D.BRAND, D.PRODUCTNAME ASC ");
			}
			
			if (!productcode.equalsIgnoreCase("-")) {	criteria.setString("productcode", productcode); }
			if (!suppliercode.equalsIgnoreCase("-")) {	criteria.setString("suppliercode", suppliercode); }
			if (!brandname.equalsIgnoreCase("-")) {	criteria.setString("brandname", brandname); }
				
			supplierproductlist = criteria.list();
			
			if(supplierproductlist.size()!=0){
				for (Iterator it = supplierproductlist.iterator(); it.hasNext();){
					Object[] resultListRecord = (Object[]) it.next();
					
					SupplierProductVO supplierproductvo = new SupplierProductVO();
					supplierproductvo.setSupplierCode(HelperUtil.checkNullString(resultListRecord[0]));
					supplierproductvo.setSupplierName(HelperUtil.checkNullString(resultListRecord[1]));
					supplierproductvo.setBrandName(HelperUtil.checkNullString(resultListRecord[2]));
					supplierproductvo.setProductCode(HelperUtil.checkNullString(resultListRecord[3]));
					supplierproductvo.setProductName(HelperUtil.checkNullString(resultListRecord[4]));
					supplierproductvo.setPhotoCode(HelperUtil.checkNullString(resultListRecord[5]));
					supplierproductvo.setPackWeight(HelperUtil.checkNullString(resultListRecord[6]));
					supplierproductvo.setPackMass(HelperUtil.checkNullString(resultListRecord[7]));
					supplierproductvo.setPackUnit(HelperUtil.checkNullNumbers(resultListRecord[8]));
					supplierproductvo.setPackPrice(HelperUtil.checkNullAmount(resultListRecord[9]));
					supplierproductvo.setPaymentTerms(HelperUtil.checkNullNumbers(resultListRecord[10]));
					supplierproductvo.setGst(HelperUtil.checkNullString(resultListRecord[11]));
					supplierproductlistvo.add(supplierproductvo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return supplierproductlistvo;
	}
	
	@Override
	public List<Supplier> submitNewSupplier(String suppliername, String address, 
			String phone, String fax, String website, String email, 
			String contactperson, String gstid, String contactpersonphone) throws Exception {
		
		List<Supplier> savesupplier = new ArrayList<Supplier>();
		Session session = sessionFactory.openSession();
		try {
			String suppliercode = ResultGeneratorUtil.codeGenerator("", "sq_supplier_code", "SU22", session);
			BigInteger supplierid = ResultGeneratorUtil.idGenerator("", "sq_supplier_id", session);
			Supplier supplier = new Supplier(supplierid, suppliercode, suppliername, address, phone, fax, website, 
					email, contactperson, gstid, contactpersonphone);
			session.save(supplier);
			savesupplier.add(supplier);
			HibernateUtil.callCommitClose(session);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}
		return savesupplier;
	}
	
	@Override
	public List<ProductSupplier> updateSupplierProduct(String suppliercode, String productcode, String packunit, String packprice,
			 String paymentterms) throws Exception {
		List<ProductSupplier> supplierproduct = null;
		Session session = sessionFactory.openSession();
		try {
			supplierproduct = factoryProductSupplier.getEntityProductSupplier(suppliercode, productcode, session);
			if(supplierproduct.size() != 0){
				for (ProductSupplier suppprod : supplierproduct) {
					
					suppprod.setPackUnit(packunit);
					suppprod.setPackPrice(packprice);
					suppprod.setPaymentTerms(paymentterms);
					session.save(suppprod);
				}
				HibernateUtil.callCommitClose(session);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}
		return supplierproduct;
	}
	
	@Override
	public void addSupplierProduct(String suppliercode, String productcode, String packunit, String packprice,
			 String paymentterms) throws Exception {
		Session session = sessionFactory.openSession();
		try {
			BigInteger productsupplierid = ResultGeneratorUtil.idGenerator("", "sq_productsupplier_id", session);
			ProductSupplier productsupplier = new ProductSupplier(productsupplierid, productcode, suppliercode, "1", packunit, packprice, paymentterms);
			session.save(productsupplier);
			HibernateUtil.callCommitClose(session);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}
	}
	
}
