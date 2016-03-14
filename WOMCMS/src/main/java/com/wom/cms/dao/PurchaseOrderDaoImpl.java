package com.wom.cms.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.wom.cms.constant.MainEnum;
import com.wom.cms.constant.StatusCode;
import com.wom.cms.factory.FactoryEntityService;
import com.wom.cms.factory.FactoryEntityServiceImpl;
import com.wom.cms.model.Inventory;
import com.wom.cms.model.Product;
import com.wom.cms.model.ProductSupplier;
import com.wom.cms.model.PurchaseOrder;
import com.wom.cms.model.PurchaseOrderProducts;
import com.wom.cms.model.PurchaseRequest;
import com.wom.cms.util.HelperUtil;
import com.wom.cms.util.HibernateUtil;
import com.wom.cms.vo.POAmendmentVO;
import com.wom.cms.vo.POSupplierVO;

public class PurchaseOrderDaoImpl implements PurchaseOrderDao{
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(PurchaseOrderDaoImpl.class);
	FactoryEntityService<PurchaseRequest> purchaserequestfs = new FactoryEntityServiceImpl<PurchaseRequest>();
	FactoryEntityService<PurchaseOrderProducts> purchaseorderproductsfs = new FactoryEntityServiceImpl<PurchaseOrderProducts>();
	FactoryEntityService<Inventory> inventoryfs = new FactoryEntityServiceImpl<Inventory>();
	FactoryEntityService<Product> productfs = new FactoryEntityServiceImpl<Product>();
	FactoryEntityService<ProductSupplier> productsupplierfs = new FactoryEntityServiceImpl<ProductSupplier>();
	FactoryEntityService<PurchaseOrder> purchaseorderfs = new FactoryEntityServiceImpl<PurchaseOrder>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<POAmendmentVO> searchPOAmendmentList(String suppliercode, String pocode, String productcode) throws Exception{
		
		List<String> searchpoamendmentlist = null;
		List<POAmendmentVO> searchpoamendmentvo = new ArrayList<POAmendmentVO>();
		
		StringBuffer stringcriteria = new StringBuffer();
		Query criteria = null;
		Session session = sessionFactory.openSession();
		try {
			if (!suppliercode.equalsIgnoreCase("-")) {	stringcriteria.append(" AND A.SUPPLIERCODE =:suppliercode "); }
			if (!pocode.equalsIgnoreCase("-")) { stringcriteria.append(" AND A.PURCHASEORDERCODE =:pocode ");}
			if (!productcode.equalsIgnoreCase("-")) { stringcriteria.append(" AND B.PRODUCTCODE =:productcode ");}

			if(stringcriteria.length() != 0){
				criteria = session.createSQLQuery(" SELECT DISTINCT A.PurchaseOrderCode, A.SupplierCode, B.ProductCode, D.ProductName, C.REQUESTQUANTITY, "
						+ " C.RequestUnit, C.RequestTotalUnit, C.RequestPackingPrice, C.GST, C.RequestTotalAmount, "  
						+ " C.RequestTotalAmountGST, D.UnitQuantity, B.ActualOrderQty, E.POUnit " 
						+ " FROM tblpurchaseorder A "
						+ " INNER JOIN tblpurchaseorderproducts B ON B.PurchaseOrderCode = A.PurchaseOrderCode " 
						+ " INNER JOIN tblpurchaserequest C ON C.PurchaseOrderCode = B.PurchaseOrderCode AND C.PRODUCTCODE = B.PRODUCTCODE "
						+ " INNER JOIN tblproduct D ON D.PRODUCTCODE = B.ProductCode "
						+ " INNER JOIN tblinventory E ON E.ProductCode = B.ProductCode AND E.SourceCode = B.PurchaseOrderCode "
						+ " INNER JOIN tblproductsupplier F ON F.ProductCode = B.PRODUCTCODE AND F.SUPPLIERCODE = A.SUPPLIERCODE "
						+ " WHERE E.STORECODE = 'MW01' " + stringcriteria.toString() + " ORDER BY B.PRODUCTCODE ");
			}else{
				criteria = session.createSQLQuery(" SELECT DISTINCT A.PurchaseOrderCode, A.SupplierCode, B.ProductCode, D.ProductName, C.REQUESTQUANTITY, "
						+ " C.RequestUnit, C.RequestTotalUnit, C.RequestPackingPrice, C.GST, C.RequestTotalAmount, "  
						+ " C.RequestTotalAmountGST, D.UnitQuantity, B.ActualOrderQty, E.POUnit " 
						+ " FROM tblpurchaseorder A "
						+ " INNER JOIN tblpurchaseorderproducts B ON B.PurchaseOrderCode = A.PurchaseOrderCode " 
						+ " INNER JOIN tblpurchaserequest C ON C.PurchaseOrderCode = B.PurchaseOrderCode AND C.PRODUCTCODE = B.PRODUCTCODE "
						+ " INNER JOIN tblproduct D ON D.PRODUCTCODE = B.ProductCode "
						+ " INNER JOIN tblinventory E ON E.ProductCode = B.ProductCode AND E.SourceCode = B.PurchaseOrderCode "
						+ " INNER JOIN tblproductsupplier F ON F.ProductCode = B.PRODUCTCODE AND F.SUPPLIERCODE = A.SUPPLIERCODE "
						+ " WHERE E.STORECODE = 'MW01' ORDER BY B.PRODUCTCODE ");
			}
			
			if (!suppliercode.equalsIgnoreCase("-")) { criteria.setString("suppliercode", suppliercode); }
			if (!pocode.equalsIgnoreCase("-")) { criteria.setString("pocode", pocode); }
			if (!productcode.equalsIgnoreCase("-")) { criteria.setString("productcode", productcode); }
				
			searchpoamendmentlist = criteria.list();
			
			if(searchpoamendmentlist.size()!=0){
				for (Iterator it = searchpoamendmentlist.iterator(); it.hasNext();){
					Object[] resultListRecord = (Object[]) it.next();
					
					POAmendmentVO poamendmentvo = new POAmendmentVO();
					poamendmentvo.setPoCode(HelperUtil.checkNullString(resultListRecord[0]));
					poamendmentvo.setSupplierCode(HelperUtil.checkNullString(resultListRecord[1]));
					poamendmentvo.setProductCode(HelperUtil.checkNullString(resultListRecord[2]));
					poamendmentvo.setProductName(HelperUtil.checkNullString(resultListRecord[3]));
					poamendmentvo.setPackQuantity(HelperUtil.checkNullNumbers(resultListRecord[4]));
					poamendmentvo.setPackUnit(HelperUtil.checkNullNumbers(resultListRecord[5]));
					poamendmentvo.setPackTotalUnit(HelperUtil.checkNullNumbers(resultListRecord[6]));
					poamendmentvo.setPackPrice(HelperUtil.checkNullAmount(resultListRecord[7]));
					poamendmentvo.setGst(HelperUtil.checkNullAmount(resultListRecord[8]));
					poamendmentvo.setAmount(HelperUtil.checkNullAmount(resultListRecord[9]));
					poamendmentvo.setTotalAmount(HelperUtil.checkNullAmount(resultListRecord[10]));
					poamendmentvo.setUnitQuantity(HelperUtil.checkNullNumbers(resultListRecord[11]));
					poamendmentvo.setInvPackQuantity(HelperUtil.checkNullNumbers(resultListRecord[12]));
					poamendmentvo.setInvTotalUnit(HelperUtil.checkNullNumbers(resultListRecord[13]));
					searchpoamendmentvo.add(poamendmentvo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return searchpoamendmentvo;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<POSupplierVO> searchPurchaseOrder(String purchaseordercode, String suppliername, String dateissued) throws Exception {
		
		List<String> searchpurchaseorderList = null;
		List<POSupplierVO> searchpurchaseordervo = new ArrayList<POSupplierVO>();
		
		StringBuffer stringcriteria = new StringBuffer();
		Query criteria = null;
		Session session = sessionFactory.openSession();
		try {
			if (!purchaseordercode.equalsIgnoreCase("-")) {	stringcriteria.append(" AND A.PURCHASEORDERCODE =:purchaseordercode "); }
			if (!suppliername.equalsIgnoreCase("-")) { stringcriteria.append(" AND B.SUPPLIERNAME like :suppliername ");}
			if (!dateissued.equalsIgnoreCase("-")) { stringcriteria.append(" AND DATE(A.ISSUEDATE) =:dateissued ");}

			if(stringcriteria.length() != 0){
				criteria = session.createSQLQuery(" SELECT A.PURCHASEORDERCODE, A.SUPPLIERCODE, B.SUPPLIERNAME, "
						+ " A.ISSUEDATE, A.DATERECEIVED FROM tblpurchaseorder A "
						+ " INNER JOIN tblsupplier B ON A.SUPPLIERCODE = B.supplierCode WHERE STATUS = 'Emailed' "
						+ stringcriteria.toString() + " ORDER BY A.PURCHASEORDERCODE ");
			}else{
				criteria = session.createSQLQuery(" SELECT A.PURCHASEORDERCODE, A.SUPPLIERCODE, B.SUPPLIERNAME, "
						+ " A.ISSUEDATE, A.DATERECEIVED FROM tblpurchaseorder A "
						+ " INNER JOIN tblsupplier B ON A.SUPPLIERCODE = B.supplierCode WHERE STATUS = 'Emailed' ORDER BY A.PURCHASEORDERCODE");
			}
			
			if (!purchaseordercode.equalsIgnoreCase("-")) {	criteria.setString("purchaseordercode", purchaseordercode); }
			if (!suppliername.equalsIgnoreCase("-")) {	criteria.setString("suppliername", "%" + suppliername + "%" ); }
			if (!dateissued.equalsIgnoreCase("-")) {	criteria.setString("dateissued", dateissued); }
				
			searchpurchaseorderList = criteria.list();
			
			if(searchpurchaseorderList.size()!=0){
				for (Iterator it = searchpurchaseorderList.iterator(); it.hasNext();){
					Object[] resultListRecord = (Object[]) it.next();
					
					POSupplierVO posupplier = new POSupplierVO();
					posupplier.setPurchaseOrderCode(HelperUtil.checkNullString(resultListRecord[0]));
					posupplier.setSupplierCode(HelperUtil.checkNullString(resultListRecord[1]));
					posupplier.setSupplierName(HelperUtil.checkNullString(resultListRecord[2]));
					posupplier.setIssueDate(HelperUtil.checkNullString(resultListRecord[3]));
					posupplier.setDateReceived(HelperUtil.checkNullString(resultListRecord[4]));
					searchpurchaseordervo.add(posupplier);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(session);
		}
		return searchpurchaseordervo;
	}

	@Override
	public List<PurchaseRequest> saveEditedPOAmendment(POAmendmentVO poamendment) throws Exception {
		
		List<PurchaseRequest> searchpurchaservo = new ArrayList<PurchaseRequest>();
		Session session = sessionFactory.openSession();
		
		try{
			PurchaseOrder purchaseorder = purchaseorderfs.getEntity(MainEnum.PURCHASEORDER, poamendment.getPoCode(), poamendment.getSupplierCode(), session);
			if(purchaseorder != null){
				for(PurchaseOrderProducts purchaseorderproducts: purchaseorder.getPurchaseOrderProducts()){
					if(purchaseorderproducts.getProductCode().equalsIgnoreCase(poamendment.getProductCode())){
						purchaseorderproducts.setRequestQuantity(poamendment.getPackQuantity());
						purchaseorderproducts.setRequestUnit(poamendment.getPackTotalUnit());
						purchaseorderproducts.setTotalAmount(poamendment.getAmount());
						purchaseorderproducts.setActualOrderQty(poamendment.getInvPackQuantity());
						purchaseorderproducts.setSubmittedToInventory(poamendment.getInvTotalUnit());
						session.save(purchaseorderproducts);
						
						PurchaseRequest purchaserequest = purchaserequestfs.getEntity(MainEnum.PURCHASEREQUEST, poamendment.getPoCode(), poamendment.getProductCode(), session);
						
						if (purchaserequest != null){
							purchaserequest.setRequestQuantity(poamendment.getPackQuantity());
							purchaserequest.setRequestUnit(poamendment.getPackUnit());
							purchaserequest.setRequestTotalUnit(poamendment.getPackTotalUnit());
							purchaserequest.setRequestPackingPrice(poamendment.getPackPrice());
							purchaserequest.setGst(poamendment.getGst());
							purchaserequest.setRequestTotalAmount(poamendment.getAmount());
							purchaserequest.setRequestTotalAmountGST(poamendment.getTotalAmount());
							session.save(purchaserequest);
							
							Inventory inventory = inventoryfs.getEntity(MainEnum.INVENTORY, poamendment.getPoCode(), poamendment.getProductCode(), session);
							if (inventory != null){
								inventory.setPoUnit(poamendment.getInvTotalUnit());
								inventory.setUnitQuantity(poamendment.getPackUnit());
								session.save(inventory);
							}
							
							Product product = productfs.getEntity(MainEnum.PRODUCT, poamendment.getProductCode(), session);
							if (product != null){
								product.setUnitQuantity(poamendment.getPackUnit());
							}
							
							ProductSupplier productsupplier = productsupplierfs.getEntity(MainEnum.PRODUCTSUPPLIER, poamendment.getProductCode(), poamendment.getSupplierCode(), session);
							if(productsupplier != null){
								productsupplier.setPackUnit(poamendment.getPackUnit());
								productsupplier.setPackPrice(poamendment.getPackPrice());
							}
						}
						searchpurchaservo.add(purchaserequest);
						break;
					}
				}
				HibernateUtil.callCommitClose(session);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}
		return searchpurchaservo;
	}
	
}
