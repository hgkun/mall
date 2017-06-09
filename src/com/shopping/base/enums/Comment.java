package com.shopping.base.enums;

public enum Comment {

	GOOD("好评"),	
	JUSTOK("中评"),
	BAD("差评");
	
	private String value;
	
	private Comment(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
