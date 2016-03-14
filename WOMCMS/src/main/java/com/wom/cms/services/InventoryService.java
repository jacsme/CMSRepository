package com.wom.cms.services;

import java.util.List;

import com.wom.cms.model.Inventory;
import com.wom.cms.vo.InventorySummaryVO;


public interface InventoryService {
	public List<InventorySummaryVO> searchInventoryList(String productcode, String stocklevel, String stocklocation) throws Exception;
	public List<Inventory> updateInventoryLocation(String location, String productcode) throws Exception;
	public List<Inventory> addReturnUnits(String location, String productcode, String units, String comments) throws Exception;
}
