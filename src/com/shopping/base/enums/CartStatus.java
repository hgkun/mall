package com.shopping.base.enums;

public enum CartStatus {
	
	NORMAL("有效"),
	DELETE("删除"),
	OVER("完结");
	
	private String value;
	
	private CartStatus(String value){
		this.value = value;
		
	}

	public String getValue() {
		return value;
	}

	
	

}
