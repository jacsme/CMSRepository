package com.wom.cms.factory;

import java.util.List;

import org.hibernate.Session;

import com.wom.cms.constant.MainEnum;

public interface FactoryEntityService<H> {
	public H getEntity(MainEnum mainenum, String param, Session session) throws Exception;
	public H getEntity(MainEnum mainenum, String param1, String param2, Session session) throws Exception;
	public List<H> getEntityProductList(MainEnum mainenum, String productcode, String brand, String categorycode, Session session) throws Exception;
	public List<H> getEntityProductSupplier(String productcode, Session session) throws Exception;
	public H getEntitySupplier(String suppliercode, Session session) throws Exception;
	public List<H> getCategoryEntityList(Session session) throws Exception;
	public List<H> getEntityProductSupplier(String suppliercode, String productcode, Session session) throws Exception;
	public List<H> getEntityInventory(String productcode, Session session) throws Exception;
	public List<H> getInventoryLocation(String location, String productcode, Session session) throws Exception;
}
	