package com.wom.cms.services;

import java.util.List;

import com.wom.cms.vo.SalesOrderVO;

public interface SalesService {
	public List<SalesOrderVO> searchSalesOrder(String salesordercode, String custumercode, String dateordered) throws Exception;
}
