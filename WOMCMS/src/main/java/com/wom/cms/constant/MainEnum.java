package com.wom.cms.constant;

public enum MainEnum {
	
	LOGIN("LOGIN"), PRODUCT("PRODUCT"), EDITPRODUCT("EDITPRODUCT"), EDITPRODUCTSUPPLIER("EDITPRODUCTSUPPLIER");
	private String mainType;
	
	private MainEnum(String mType){
		mainType = mType;
	}
	
	public String getMainType(){
		return mainType;
	}

}
