package com.wom.cms.dao;

import java.util.List;

import com.wom.cms.vo.SalesOrderVO;

public interface SalesDao {
	public List<SalesOrderVO> searchSalesOrder(String salesordercode, String custumercode, String dateordered) throws Exception;
}
