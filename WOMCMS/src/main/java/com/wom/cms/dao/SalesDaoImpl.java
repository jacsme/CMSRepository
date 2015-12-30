package com.wom.cms.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.cms.constant.StatusCode;
import com.wom.cms.util.HelperUtil;
import com.wom.cms.util.HibernateUtil;
import com.wom.cms.vo.SalesOrderVO;

public class SalesDaoImpl implements SalesDao{
	
	@Autowired
	SessionFactory sessionFactory;
	
	Session session = null;
	Transaction tx = null;
	
	static final Logger logger = Logger.getLogger(SalesDaoImpl.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SalesOrderVO> searchSalesOrder(String salesordercode, String custumercode, String dateordered) throws Exception {
		
		Session session = HibernateUtil.callSession(sessionFactory);
		
		List<String> searchsalesorderList = null;
		List<SalesOrderVO> searchsalesordervo = new ArrayList<SalesOrderVO>();
		
		StringBuffer stringcriteria = new StringBuffer();
		Query criteria = null;
		
		try {
			if (!salesordercode.equalsIgnoreCase("-")) {	stringcriteria.append(" AND A.SALESORDERCODE =:salesordercode "); }
			if (!custumercode.equalsIgnoreCase("-")) { stringcriteria.append(" AND A.CUSTOMERCODE like :custumercode ");}
			if (!dateordered.equalsIgnoreCase("-")) { stringcriteria.append(" AND DATE(A.SALESDATE) =:dateordered ");}

			if(stringcriteria.length() != 0){
				criteria = session.createSQLQuery(" SELECT DISTINCT A.SALESORDERCODE, B.ADDRESSINFO, A.CUSTOMERCODE, "
						+ " A.SALESDATE, A.DELIVERYDATE, A.DELIVERYTIME, A.WCPAIDAMOUNT, A.CCPAIDAMOUNT,"
						+ " A.STATUS FROM tblsalesorderinfo A "
						+ " INNER JOIN tbladdress B ON A.ADDRESS = B.ADDRESSCODE WHERE A.STORECODE = 'MW01' "
						+ stringcriteria.toString() + " ORDER BY A.SALESORDERCODE ");
			}else{
				criteria = session.createSQLQuery(" SELECT DISTINCT A.SALESORDERCODE, B.ADDRESSINFO, A.CUSTOMERCODE, "
						+ " A.SALESDATE, A.DELIVERYDATE, A.DELIVERYTIME, A.WCPAIDAMOUNT, A.CCPAIDAMOUNT,"
						+ " A.STATUS FROM tblsalesorderinfo A "
						+ " INNER JOIN tbladdress B ON A.ADDRESS = B.ADDRESSCODE "
						+ " WHERE A.STORECODE = 'MW01' ORDER BY A.SALESORDERCODE");
			}
			
			if (!salesordercode.equalsIgnoreCase("-")) {	criteria.setString("salesordercode", salesordercode); }
			if (!custumercode.equalsIgnoreCase("-")) {	criteria.setString("custumercode", "%" + custumercode + "%" ); }
			if (!dateordered.equalsIgnoreCase("-")) {	criteria.setString("dateordered", dateordered); }
				
			searchsalesorderList = criteria.list();
			
			if(searchsalesorderList.size()!=0){
				for (Iterator it = searchsalesorderList.iterator(); it.hasNext();){
					Object[] resultListRecord = (Object[]) it.next();
					
					SalesOrderVO salesordervo = new SalesOrderVO();
					
					salesordervo.setSalesOrderCode(HelperUtil.checkNullString(resultListRecord[0]));
					salesordervo.setAddress(HelperUtil.checkNullString(resultListRecord[1]));
					salesordervo.setCustomerCode(HelperUtil.checkNullString(resultListRecord[2]));
					salesordervo.setDateOrdered(HelperUtil.checkNullDate(resultListRecord[3]));
					salesordervo.setDeliveryDate(HelperUtil.checkNullDate(resultListRecord[4]));
					salesordervo.setDeliveryTime(HelperUtil.checkNullString(resultListRecord[5]));
					salesordervo.setWomCoin(HelperUtil.checkNullAmount(resultListRecord[6]));
					salesordervo.setPurchaseAmount(HelperUtil.checkNullAmount(resultListRecord[7]));
					salesordervo.setStatus(HelperUtil.checkNullString(resultListRecord[8]));
					searchsalesordervo.add(salesordervo);
				}
			}
			HibernateUtil.callCommit(sessionFactory);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("StatusCode:" + StatusCode.EXCEPTION_ERROR_CODE + " Message:" + e.getMessage());
		}finally{
			HibernateUtil.callClose(sessionFactory);
		}
		return searchsalesordervo;
	}
}
