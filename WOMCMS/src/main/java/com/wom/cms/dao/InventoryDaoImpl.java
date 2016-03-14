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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.transaction.annotation.Transactional;

import com.wom.cms.factory.FactoryEntityService;
import com.wom.cms.factory.FactoryEntityServiceImpl;
import com.wom.cms.model.AuditTrail;
import com.wom.cms.model.Inventory;
import com.wom.cms.model.Product;
import com.wom.cms.util.HelperUtil;
import com.wom.cms.util.HibernateUtil;
import com.wom.cms.util.ResultGeneratorUtil;
import com.wom.cms.vo.InventorySummaryVO;
@Transactional
public class InventoryDaoImpl implements InventoryDao {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	static final Logger logger = Logger.getLogger(InventoryDaoImpl.class);
	
	FactoryEntityService<Inventory> factoryInventoryEntityService = new FactoryEntityServiceImpl<Inventory>();
	FactoryEntityService<Product> factoryProductEntityService = new FactoryEntityServiceImpl<Product>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<InventorySummaryVO> searchInventoryList(String productcode, String stocklevel, String stocklocation) throws Exception {
		
		Session session = sessionFactory.openSession();
		List<String> searchInventoryListString = null;
		List<InventorySummaryVO> searchInventoryList = new ArrayList<InventorySummaryVO>();
		StringBuffer stringcriteria = new StringBuffer();
		Query query = null;
		try {
			if (!productcode.equalsIgnoreCase("-")) {stringcriteria.append(" AND A.PRODUCTCODE =:productcode "); }
			if (!stocklevel.equalsIgnoreCase("-")) {
				if(stocklevel.equalsIgnoreCase("Stocks on Hand")){
					stringcriteria.append(" AND AVAILABLESTOCKQTY2 >:availablestock ");
				}else if (stocklevel.equalsIgnoreCase("Stocks Finished")){
					stringcriteria.append(" AND AVAILABLESTOCKQTY2 =:availablestock ");
				}
			}
			if (!stocklocation.equalsIgnoreCase("-")) {stringcriteria.append(" AND A.STOCKLOCATION =:stocklocation ");}
			
			if(stringcriteria.length() != 0){
				query = session.createSQLQuery(" SELECT DATE(G.TDATE) AS TDATE,  A.PRODUCTCODE, E.BRAND, E.PRODUCTNAME, E.PACKWEIGHT, E.PACKMASS, "
						+ " A.STOCKLOCATION, F.REQUESTPACKINGPRICE/F.RequestUnit AS PACKINGPRICE, E.RRPRICE, "  
						+ " IF((((SUM(A.POUNIT)/E.UNITQUANTITY)) - (SUM(A.SOUNIT) - SUM(A.SORETURNUNIT)))<1, 0,(((SUM(A.POUNIT)/E.UNITQUANTITY)) - (SUM(A.SOUNIT) - SUM(A.SORETURNUNIT))))  AS AVAILABLESTOCKQTY, "
						+ " IF((((SUM(A.POUNIT)/E.UNITQUANTITY) - SUM(A.PORETURNUNIT)) - (SUM(A.SOUNIT) - SUM(A.SORETURNUNIT)))<1, 0,(((SUM(A.POUNIT)/E.UNITQUANTITY)- SUM(A.PORETURNUNIT)) - (SUM(A.SOUNIT) - SUM(A.SORETURNUNIT))))  AS AVAILABLESTOCKQTY2, "
						+ " IFNULL(E.PHOTO, 'NO') AS PHOTO, SUM(A.PORETURNUNIT) AS PORETURNUNIT, H.COMMENTS FROM tblinventory A INNER JOIN ( SELECT MAX(TransactionDate) AS TDATE, PRODUCTCODE "
						+ " FROM tblinventory WHERE INVENTORYSOURCE = 'GR' GROUP BY PRODUCTCODE) G ON G.ProductCode = A.PRODUCTCODE INNER JOIN tblproduct E ON A.ProductCode = E.ProductCode "
						+ " LEFT JOIN tblpurchaserequest F ON F.PURCHASEORDERCODE = A.SOURCECODE AND F.PRODUCTCODE = A.ProductCode "
						+ " LEFT JOIN (SELECT MAX(TransactionDate) AS TDATE, PRODUCTCODE, COMMENTS FROM tblinventory WHERE InventorySource = 'RTS' GROUP BY PRODUCTCODE, COMMENTS) H ON H.PRODUCTCODE = A.PRODUCTCODE "
						+ " GROUP BY G.TDATE, A.STOCKLOCATION, A.PRODUCTCODE, E.PHOTO, H.COMMENTS HAVING LENGTH(A.STOCKLOCATION) = 9 "
						+ stringcriteria.toString() + " ORDER BY G.TDATE ");
			}else{
				query = session.createSQLQuery(" SELECT DATE(G.TDATE) AS TDATE,  A.PRODUCTCODE, E.BRAND, E.PRODUCTNAME, E.PACKWEIGHT, E.PACKMASS, "
						+ " A.STOCKLOCATION, F.REQUESTPACKINGPRICE/F.RequestUnit AS PACKINGPRICE, E.RRPRICE, "  
						+ " IF((((SUM(A.POUNIT)/E.UNITQUANTITY)) - (SUM(A.SOUNIT) - SUM(A.SORETURNUNIT)))<1, 0,(((SUM(A.POUNIT)/E.UNITQUANTITY)) - (SUM(A.SOUNIT) - SUM(A.SORETURNUNIT))))  AS AVAILABLESTOCKQTY, "
						+ " IF((((SUM(A.POUNIT)/E.UNITQUANTITY) - SUM(A.PORETURNUNIT)) - (SUM(A.SOUNIT) - SUM(A.SORETURNUNIT)))<1, 0,(((SUM(A.POUNIT)/E.UNITQUANTITY) - SUM(A.PORETURNUNIT)) - (SUM(A.SOUNIT) - SUM(A.SORETURNUNIT))))  AS AVAILABLESTOCKQTY2, "
						+ " IFNULL(E.PHOTO, 'NO') AS PHOTO, SUM(A.PORETURNUNIT) AS PORETURNUNIT, H.COMMENTS FROM tblinventory A INNER JOIN ( SELECT MAX(TransactionDate) AS TDATE, PRODUCTCODE "
						+ " FROM tblinventory WHERE INVENTORYSOURCE = 'GR' GROUP BY PRODUCTCODE) G ON G.ProductCode = A.PRODUCTCODE INNER JOIN tblproduct E ON A.ProductCode = E.ProductCode "
						+ " LEFT JOIN tblpurchaserequest F ON F.PURCHASEORDERCODE = A.SOURCECODE AND F.PRODUCTCODE = A.ProductCode "
						+ " LEFT JOIN (SELECT MAX(TransactionDate) AS TDATE, PRODUCTCODE, COMMENTS FROM tblinventory WHERE InventorySource = 'RTS' GROUP BY PRODUCTCODE, COMMENTS) H ON H.PRODUCTCODE = A.PRODUCTCODE "
						+ " GROUP BY G.TDATE, A.STOCKLOCATION, A.PRODUCTCODE, E.PHOTO, H.COMMENTS HAVING LENGTH(A.STOCKLOCATION) = 9 "
						+ " ORDER BY G.TDATE ");
			}
			
			if (!productcode.equalsIgnoreCase("-")) { query.setString("productcode", productcode); }
			if (!stocklevel.equalsIgnoreCase("-")) { query.setInteger("availablestock", 0); }
			if (!stocklocation.equalsIgnoreCase("-")) {	query.setString("stocklocation", stocklocation); }
			
			searchInventoryListString = query.list();
			
			if(searchInventoryListString.size()!=0){
				for (Iterator it = searchInventoryListString.iterator(); it.hasNext();){
					Object[] resultListRecord = (Object[]) it.next();
					
					InventorySummaryVO inventorysummaryvo = new InventorySummaryVO();
					
					inventorysummaryvo.setTransactionDate(HelperUtil.checkNullString(resultListRecord[0]));
					inventorysummaryvo.setProductCode(HelperUtil.checkNullString(resultListRecord[1]));
					inventorysummaryvo.setBrand(HelperUtil.checkNullString(resultListRecord[2]));
					inventorysummaryvo.setProductName(HelperUtil.checkNullString(resultListRecord[3]));
					inventorysummaryvo.setPackWeight(HelperUtil.checkNullString(resultListRecord[4]));
					inventorysummaryvo.setPackMass(HelperUtil.checkNullString(resultListRecord[5]));
					inventorysummaryvo.setLocation(HelperUtil.checkNullString(resultListRecord[6]));
					inventorysummaryvo.setBuyingPrice(HelperUtil.checkNullAmount(resultListRecord[7]));
					inventorysummaryvo.setRrprice(HelperUtil.checkNullAmount(resultListRecord[8]));
					inventorysummaryvo.setUnits(HelperUtil.checkNullNumbers(resultListRecord[9]));
					inventorysummaryvo.setUnitsAll(HelperUtil.checkNullNumbers(resultListRecord[10]));
					inventorysummaryvo.setPhoto(HelperUtil.checkNullString(resultListRecord[11]));
					inventorysummaryvo.setReturnUnits(HelperUtil.checkNullNumbers(resultListRecord[12]));
					inventorysummaryvo.setComments(HelperUtil.checkNullString(resultListRecord[13]));
					searchInventoryList.add(inventorysummaryvo);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			HibernateUtil.callClose(session);
		}
		return searchInventoryList;
	}

	@Override
	public List<Inventory> updateInventoryLocation(String location, String productcode) throws Exception {
		
		List<Inventory> searchinventory = null;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		String actiontaken1 = null;
		Session session = sessionFactory.openSession();
		try {
			searchinventory = factoryInventoryEntityService.getEntityInventory(productcode, session);
			if(searchinventory.size() != 0){
				for (Inventory inv : searchinventory) {
					//insert audit trail
					//TODO Please include the staffcode
					actiontaken1 = "1. Update Stock Location from " + inv.getStockLocation() + " to " + location;
					
					BigInteger audittrailid = ResultGeneratorUtil.idGenerator("", "sq_audittrail_id", session);
					AuditTrail audittrail = new AuditTrail(audittrailid, "CMS", "updateInventoryLocation", "-", productcode,
							currdatenow, actiontaken1, HelperUtil.getLoginusercode());
					session.save(audittrail);
					
					inv.setStockLocation(location);
					session.save(inv);
				}
				HibernateUtil.callCommitClose(session);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchinventory;
	}
	
	@Override
	public List<Inventory> addReturnUnits(String location, String productcode, String units, String comments) throws Exception {
		
		List<Inventory> searchinventory = null;
		
		DateTime dateTimeKL = DateTime.now( DateTimeZone.forID("Asia/Kuala_Lumpur"));
		String currdatenow = HelperUtil.checkNullTimeZone(dateTimeKL);
		String actiontaken1 = null;
		String unitquantity = null;
		String stockcode = null;
		Session session = sessionFactory.openSession();
		try {
			searchinventory = factoryInventoryEntityService.getInventoryLocation(location, productcode, session);
			if(searchinventory.size() != 0 && searchinventory !=null){
				for(Inventory inv : searchinventory){
					stockcode = inv.getStockCode();
					unitquantity = inv.getUnitQuantity();
					break;
				}
				
				//insert audit trail
				//TODO Please include the staffcode
				actiontaken1 = "1. Add Return Stocks of " + units + " items to Supplier";
				
				BigInteger audittrailid = ResultGeneratorUtil.idGenerator("", "sq_audittrail_id", session);
				AuditTrail audittrail = new AuditTrail(audittrailid, "CMS", "addReturnUnits", "-", productcode,
						currdatenow, actiontaken1, HelperUtil.getLoginusercode());
				session.save(audittrail);
				
				//TODO check here if we have already return items. if yes then deduct it to current return
				BigInteger inventoryid = ResultGeneratorUtil.idGenerator("", "sq_inventory_id", session);
				Inventory inventoryreturn = new Inventory(inventoryid, "-", productcode, "MW01", unitquantity, location, 
					units, "RTS", "RTS", HelperUtil.getLoginusercode(), stockcode, "Items on Hand", comments); 
				session.save(inventoryreturn);
				
				HibernateUtil.callCommitClose(session);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return searchinventory;
	}
	
}
