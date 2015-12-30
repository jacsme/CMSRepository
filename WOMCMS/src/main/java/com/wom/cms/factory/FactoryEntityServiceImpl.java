package com.wom.cms.factory;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.wom.cms.constant.MainEnum;

public class FactoryEntityServiceImpl<H> implements FactoryEntityService<H>{
	
	@SuppressWarnings("unchecked")
	@Override
	public H getEntity(MainEnum mainenum, String param, Session session) throws Exception{
		
		H resultentity = null;
		if(MainEnum.LOGIN.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.cms.model.LoginUser");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("userCode", new String(param))).uniqueResult();
		
		}else if(MainEnum.PRODUCT.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.cms.model.Product");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("productCode", new String(param))).uniqueResult();
		}
		
		return resultentity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<H> getEntityProductList(MainEnum mainenum, String productcode, String brand, String categorycode, Session session) throws Exception{
		Criteria criteria = null;
		List<H> resultentitylist = null;
		if(MainEnum.PRODUCT.equals(mainenum)){
			
			Class<?> cls = Class.forName("com.wom.cms.model.Product");
			criteria = session.createCriteria(cls);
			
			if(!productcode.equalsIgnoreCase("-")) { criteria.add(Restrictions.eq("productCode", new String(productcode))); }
			if(!brand.equalsIgnoreCase("-")) { criteria.add(Restrictions.eq("brand", new String(brand))); }
			if(!categorycode.equalsIgnoreCase("-")) { criteria.add(Restrictions.eq("categoryCode", new String(categorycode))); }
			
			resultentitylist = criteria.list();
		
		} else if(MainEnum.EDITPRODUCT.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.cms.model.Product");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("productCode", new String(productcode))).list();
		
		}
		
		return resultentitylist;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<H> getEntityProductSupplier(String productcode, Session session) throws Exception{
		List<H> resultentitylist = null;
		
		Class<?> cls = Class.forName("com.wom.cms.model.ProductSupplier");
		resultentitylist = (List<H>) session.createCriteria(cls)
				.add(Restrictions.eq("productCode", productcode))
				.list();
		
		return resultentitylist;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<H> getCategoryEntityList(Session session) throws Exception{
		List<H> resultentitylist = null;
		
		Class<?> cls = Class.forName("com.wom.cms.model.Category");
		resultentitylist = (List<H>) session.createCriteria(cls)
				.list();
		
		return resultentitylist;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public H getEntitySupplier(String suppliercode, Session session) throws Exception{
		H resultentitylist = null;
		
		Class<?> cls = Class.forName("com.wom.cms.model.Supplier");
		resultentitylist = (H) session.createCriteria(cls)
				.add(Restrictions.eq("supplierCode", suppliercode))
				.uniqueResult();
		
		return resultentitylist;
	}
	
}