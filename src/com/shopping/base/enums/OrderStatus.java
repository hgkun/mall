package com.shopping.base.enums;

public enum OrderStatus {
	
	OBLIGATION("代付款"),	
	WITETOSHIPMENTS("待发货"),
	WITETOTAKEITEM("待收货"),
	WITETOEVALUATE("待评价"),
	WITETOSALESRETURN("待退货");
	
	private String value;
	
	private OrderStatus(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
