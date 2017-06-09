package com.shopping.user.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户dto
 * 

 * @version V1.0
 * @since V1.0
 */
public class UserDto implements Serializable{
	

	/**
	 * serialVersionUID
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
	 * 身份证
	 */
	private String idcard;
	/**
	 * 手机号码
	 *
	 */
	private String phone;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 状态
	 */
	private String userStatus;
	/**
	 * 组别
	 */
	private String group;
	/**
	 * 账号
	 */
	private String account;
	private String email;
	
	/**
	 * 性别
	 */
	private String gender ;
	
	
	private String brithDay;
	
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBrithDay() {
		return brithDay;
	}

	public void setBrithDay(String brithDay) {
		this.brithDay = brithDay;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
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

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
}
