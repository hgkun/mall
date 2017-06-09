package com.shopping.base.enums;

/**
 * 用户状态
 * 

 * @version V1.0
 * @since V1.0
 */
public enum UserStatus {
	
	NORMAL("正常"),	
	FAILURE("失效"),
	STOP("停用");
	
	private String value;
	
	private UserStatus(String value){
		this.value = value;
		
	}

	public String getValue() {
		return value;
	}

	
	

}
