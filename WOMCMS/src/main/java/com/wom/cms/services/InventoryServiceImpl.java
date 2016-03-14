package com.wom.cms.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.wom.cms.dao.InventoryDao;
import com.wom.cms.model.Inventory;
import com.wom.cms.vo.InventorySummaryVO;


public class InventoryServiceImpl implements InventoryService {

	@Autowired
	InventoryDao inventoryDao;
	
	static final Logger logger = Logger.getLogger(InventoryServiceImpl.class);

	
	public List<InventorySummaryVO> searchInventoryList(String productcode, String stocklevel, String stocklocation) throws Exception{
		return inventoryDao.searchInventoryList(productcode, stocklevel, stocklocation);
	}
	
	public List<Inventory> updateInventoryLocation(String location, String productcode) throws Exception{
		return inventoryDao.updateInventoryLocation(location, productcode);
	}

	@Override
	public List<Inventory> addReturnUnits(String location, String productcode, String units, String comments)
			throws Exception {
		return inventoryDao.addReturnUnits(location, productcode, units, comments);
	}
}
