package com.wom.cms.factory;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.wom.cms.constant.MainEnum;

@SuppressWarnings("unchecked")
public class FactoryEntityServiceImpl<H> implements FactoryEntityService<H>{
	

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
	
	@Override
	public H getEntity(MainEnum mainenum, String param1, String param2, Session session) throws Exception{
		
		H resultentity = null;
		if(MainEnum.PURCHASEREQUEST.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.cms.model.PurchaseRequest");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("purchaseOrderCode", new String(param1)))
					.add(Restrictions.eq("productCode", new String(param2)))
					.uniqueResult();
		
		}else if (MainEnum.PURCHASEORDERPRODUCTS.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.cms.model.PurchaseOrderProducts");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("purchaseOrderCode", new String(param1)))
					.add(Restrictions.eq("productCode", new String(param2)))
					.uniqueResult();;
			
		}else if (MainEnum.PURCHASEORDER.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.cms.model.PurchaseOrder");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("purchaseOrderCode", new String(param1)))
					.add(Restrictions.eq("supplierCode", new String(param2)))
					.uniqueResult();;
			
		}else if(MainEnum.INVENTORY.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.cms.model.Inventory");
			resultentity = (H) session.createCriteria(cls)
				.add(Restrictions.eq("sourceCode", new String(param1)))
				.add(Restrictions.eq("productCode", new String(param2)))
				.add(Restrictions.eq("inventorySource", new String("GR")))
				.uniqueResult();
		
		}else if (MainEnum.PRODUCTSUPPLIER.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.cms.model.ProductSupplier");
			resultentity = (H) session.createCriteria(cls)
					.add(Restrictions.eq("productCode", new String(param1)))
					.add(Restrictions.eq("supplierCode", new String(param2)))
					.uniqueResult();
		}
		
		return resultentity;
	}

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
			
			resultentitylist = criteria.addOrder(Order.asc("brand")).list();
		
		} else if(MainEnum.EDITPRODUCT.equals(mainenum)){
			Class<?> cls = Class.forName("com.wom.cms.model.Product");
			resultentitylist = (List<H>) session.createCriteria(cls)
					.add(Restrictions.eq("productCode", new String(productcode)))
					.addOrder(Order.asc("productName"))
					.list();
		
		}
		
		return resultentitylist;
	}
	

	@Override
	public List<H> getEntityProductSupplier(String productcode, Session session) throws Exception{
		List<H> resultentitylist = null;
		
		Class<?> cls = Class.forName("com.wom.cms.model.ProductSupplier");
		resultentitylist = (List<H>) session.createCriteria(cls)
				.add(Restrictions.eq("productCode", productcode))
				.addOrder(Order.asc("productCode"))
				.list();
		
		return resultentitylist;
	}
	
	@Override
	public List<H> getEntityProductSupplier(String suppliercode, String productcode, Session session) throws Exception{
		List<H> resultentitylist = null;
		
		Class<?> cls = Class.forName("com.wom.cms.model.ProductSupplier");
		resultentitylist = (List<H>) session.createCriteria(cls)
				.add(Restrictions.eq("supplierCode", suppliercode))
				.add(Restrictions.eq("productCode", productcode))
				.addOrder(Order.asc("supplierCode"))
				.list();
		
		return resultentitylist;
	}
	
	
	@Override
	public List<H> getCategoryEntityList(Session session) throws Exception{
		List<H> resultentitylist = null;
		
		Class<?> cls = Class.forName("com.wom.cms.model.Category");
		resultentitylist = (List<H>) session.createCriteria(cls)
				.addOrder(Order.asc("categoryName"))
				.list();
		
		return resultentitylist;
	}
	

	@Override
	public H getEntitySupplier(String suppliercode, Session session) throws Exception{
		H resultentitylist = null;
		
		Class<?> cls = Class.forName("com.wom.cms.model.Supplier");
		resultentitylist = (H) session.createCriteria(cls)
				.add(Restrictions.eq("supplierCode", suppliercode))
				.uniqueResult();
		
		return resultentitylist;
	}

	@Override
	public List<H> getEntityInventory(String productcode, Session session) throws Exception {
		List<H> resultentitylist = null;
		
		Class<?> cls = Class.forName("com.wom.cms.model.Inventory");
		resultentitylist = (List<H>) session.createCriteria(cls)
				.add(Restrictions.eq("productCode", productcode))
				.list();
		
		return resultentitylist;
	}
	
	@Override
	public List<H> getInventoryLocation(String location, String productcode, Session session) throws Exception {
		List<H> resultentitylist = null;
		
		Class<?> cls = Class.forName("com.wom.cms.model.Inventory");
		resultentitylist = (List<H>) session.createCriteria(cls)
				.add(Restrictions.eq("stockLocation", location))
				.add(Restrictions.eq("productCode", productcode))
				.list();
		
		return resultentitylist;
	}
}