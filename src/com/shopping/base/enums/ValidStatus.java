package com.shopping.base.enums;

public enum ValidStatus {
	
	NORMAL("有效"),
	STOP("无效");
	
	private String value;
	
	private ValidStatus(String value){
		this.value = value;
		
	}

	public String getValue() {
		return value;
	}

	
	

}
