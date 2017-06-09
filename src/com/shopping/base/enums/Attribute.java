package com.shopping.base.enums;
/**
 * 联系人属性 
 * 

 * @version V1.0
 * @since V1.0
 */
public enum Attribute {
	
	ORIGINAL("原始"),
	ADD("新增");
	
	private String value;
	
	private Attribute(String value){
		this.value = value;
		
	}

	public String getValue() {
		return value;
	}

	
	

}
