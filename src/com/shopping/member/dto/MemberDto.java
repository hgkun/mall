package com.shopping.member.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 *
 */
public class MemberDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7111780623618532963L;
	/** 
	 * ID
	 */
	private String id;
	/**
	 * 用户名称
	 */
	private String name;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 手机号码
	 *
	 */
	private String phone;
	/**
	 * 创建时间
	 */
	private Date createTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
