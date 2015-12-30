package com.wom.cms.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.hibernate.Query;
import org.hibernate.Session;

import com.wom.cms.constant.MainEnum;
import com.wom.cms.constant.Messages;
import com.wom.cms.constant.StatusCode;

public class ResultGeneratorUtil {

	public static String codeGenerator(String code, String sequencename, String prefixname, Session session){
		  String codeformatted = null;
		  Query lastid = session.createSQLQuery("SELECT nextval('" + sequencename + "') as next_sequence");
		  BigInteger lastidresult = (BigInteger) lastid.uniqueResult();
		  
		  if(code.equals("")){
			  codeformatted = prefixname +  StringUtils.leftPad("" + lastidresult, 6, '0');
		  }else{
			  codeformatted = lastidresult.toString();
		  }
		  return codeformatted;
	}
	
	public static BigInteger idGenerator(String code, String sequencename, Session session){
		  Query lastid = session.createSQLQuery("SELECT nextval('" + sequencename + "') as next_sequence");
		  BigInteger lastidresult = (BigInteger) lastid.uniqueResult();
		  return lastidresult;
	}
	
	@SuppressWarnings("rawtypes")
	public static JSONArray populateresults(List<String> resultList, MainEnum mainEnum) throws Exception{
		
		Map<String, String> resultmap = new HashMap<String, String>();
		
		JSONArray resultArray = new JSONArray();
		
		String strcomparemass = null;
		String strpackmass = null;
		BigDecimal newcompareprice = null;
		BigDecimal packprice = new BigDecimal(0.00);
		BigDecimal packweight = new BigDecimal(0.00);
		BigDecimal compareweight = new BigDecimal(0.00);
		BigDecimal unitquantity = new BigDecimal(0.00);
		
		if(resultList == null || resultList.size()==0){
			
			resultmap.put("StatusCode", StatusCode.NO_RECORD_FOUND_CODE);
			resultmap.put("Message", Messages.NO_RECORD_FOUND_MESSAGE);
			resultArray.put(resultmap);
			
		}else{
			for (Iterator it = resultList.iterator(); it.hasNext();){
				StringBuffer packcontentssb = new StringBuffer();
				Object[] resultListRecord = (Object[]) it.next();
				
				if (MainEnum.PRODUCT.equals(mainEnum)){
					
					//0B.STORECODE, 1A.STORENAME, 
					//2B.PRODUCTCODE, 3B.PRODUCTNAME, 4B.BRAND, 
					//5B.CATEGORYCODE, 6B.PACKPRICE, 7B.PACKQUANTITY, 
					//8B.PACKFORMULA, 9B.PACKWEIGHT, 10B.PACKMASS, 
					//11'1' AS UNITPRICE, 
					//12B.UNITQUANTITY,
					//13'1' AS UNITWEIGHT,
					//14'1' AS COMPAREPRICE, 
					//15B.COMPAREWEIGHT, 16C.FILENAME, 17C.FILETYPE, 
					//18'1' AS PACKCONTENT, 
					//19B.GST, 20.B.PHOTOCODE, 21E.AVAILABLESTOCKQTY
					//22A.QUANTITY, 23A.PRICE, 24A.CUSTOMERCODE, 25A.STATUS  - FOR CUSTOMERCART
			        
					strpackmass = HelperUtil.checkNullMass(resultListRecord[10]);
					packprice = new BigDecimal((String) HelperUtil.checkNullAmount(resultListRecord[6]));
					packweight = new BigDecimal( (String) HelperUtil.checkNullAmount(resultListRecord[9]));
					compareweight = new BigDecimal( (String) HelperUtil.checkNullAmount(resultListRecord[15]));
					unitquantity = new BigDecimal( (String) HelperUtil.checkNullNumbers(resultListRecord[12]));
					
					resultmap.put("StoreCode", HelperUtil.checkNullString(resultListRecord[0]));
					resultmap.put("StoreName", HelperUtil.checkNullString(resultListRecord[1]));
					resultmap.put("ProductCode", HelperUtil.checkNullString(resultListRecord[2]));
					resultmap.put("ProductName", HelperUtil.checkNullString(resultListRecord[3]));
					resultmap.put("Brand", HelperUtil.checkNullString(resultListRecord[4]));
					resultmap.put("CategoryCode", HelperUtil.checkNullString(resultListRecord[5]));
					resultmap.put("PackPrice", HelperUtil.checkNullAmount(resultListRecord[6]));
					resultmap.put("PackQuantity", HelperUtil.checkNullNumbers(resultListRecord[7]));
					resultmap.put("PackFormula", HelperUtil.checkNullString(resultListRecord[8]));
					resultmap.put("PackWeight", HelperUtil.checkNullString(resultListRecord[9]));
					resultmap.put("PackMass", HelperUtil.checkNullMass(resultListRecord[10]));
					resultmap.put("UnitPrice", HelperUtil.checkNullAmount(resultListRecord[6]));
					resultmap.put("UnitQuantity", HelperUtil.checkNullNumbers(resultListRecord[12]));
					resultmap.put("UnitWeight", HelperUtil.checkNullString(resultListRecord[9]));
					
					//COMPAREPRICE
					
					if (strpackmass.equalsIgnoreCase("EACH") || strpackmass.equalsIgnoreCase("BOXES") || strpackmass.equalsIgnoreCase("PIECES")
							 || strpackmass.equalsIgnoreCase("PACK") || strpackmass.equalsIgnoreCase("TABLETS") || strpackmass.equalsIgnoreCase("BOX")
							 || strpackmass.equalsIgnoreCase("ROLL") || strpackmass.equalsIgnoreCase("DOZEN")){
						
						newcompareprice = packprice.divide(packweight, 2, RoundingMode.HALF_EVEN).divide(unitquantity, 2, RoundingMode.HALF_EVEN);
						resultmap.put("ComparePrice", newcompareprice.toString());
						strcomparemass = HelperUtil.checkNullMass(resultListRecord[10]);
						
					}else if (strpackmass.equalsIgnoreCase("GRAM") || strpackmass.equalsIgnoreCase("ML")){
						if (packweight.compareTo(new BigDecimal(100.00)) == -1){
							newcompareprice = packprice.divide(unitquantity, 2, RoundingMode.HALF_EVEN);
							resultmap.put("ComparePrice", newcompareprice.toString());
							strcomparemass = HelperUtil.checkNullMass(resultListRecord[10]);
						}else{
							newcompareprice = packprice.divide(packweight.divide(compareweight, 2, RoundingMode.HALF_EVEN), 2, RoundingMode.HALF_EVEN).divide(unitquantity, 2, RoundingMode.HALF_EVEN);
							resultmap.put("ComparePrice", newcompareprice.toString());
							strcomparemass = HelperUtil.checkNullMass(resultListRecord[10]);
						}
							
					}else{
						
						newcompareprice = packprice.divide(packweight.divide(compareweight, 2, RoundingMode.HALF_EVEN), 2, RoundingMode.HALF_EVEN).divide(unitquantity, 2, RoundingMode.HALF_EVEN);
						resultmap.put("ComparePrice", newcompareprice.toString());
						strcomparemass = HelperUtil.checkNullMass(resultListRecord[10]);
					}
					
					resultmap.put("CompareMass", strcomparemass);
					resultmap.put("CompareWeight", HelperUtil.checkNullString(resultListRecord[15]));
							
					if(!(HelperUtil.checkNullString(resultListRecord[9]).equalsIgnoreCase("0"))){
						packcontentssb.append(HelperUtil.checkNullNumbers(resultListRecord[12]));
						packcontentssb.append(" x " + HelperUtil.checkNullString(resultListRecord[9]) + " " + HelperUtil.checkNullMass(resultListRecord[10]));
						if(!(HelperUtil.checkNullNumbers(resultListRecord[7]).equalsIgnoreCase("0"))){
							packcontentssb.append(" (" + HelperUtil.checkNullNumbers(resultListRecord[7]) + " Pack)");
						}
					}else{
						if((HelperUtil.checkNullNumbers(resultListRecord[12]).equalsIgnoreCase("0")) && !(HelperUtil.checkNullNumbers(resultListRecord[10]).equalsIgnoreCase("0"))){
							packcontentssb.append("(" + HelperUtil.checkNullNumbers(resultListRecord[7]) + " Pack)");
						}else{
							packcontentssb.append(HelperUtil.checkNullNumbers(resultListRecord[12]) + " Each");
						}
					}
					
					resultmap.put("PhotoCode", HelperUtil.checkNullString(resultListRecord[20]));
					resultmap.put("AvailableStockQty", HelperUtil.checkNullNumbers(resultListRecord[21]));
					resultmap.put("PackContents", packcontentssb.toString());
					
					resultArray.put(resultmap);
				}
				
			}
		}
		return resultArray;
	}
}
