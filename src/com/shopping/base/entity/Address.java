package com.shopping.base.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shopping.base.enums.ValidStatus;
import com.shopping.core.dto.BooleanToIntegerConverter;
import com.shopping.core.entity.IdEntity;

@Entity
@Table(name="tb_address")
public class Address extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9129642505572627572L;
	
	private Administration administration;
	
	private String address;
	
	private String postcode;
	
	private String person;
	
	private String phone;
	
	private User user;
	
	private Boolean selected = false;
	
	private ValidStatus validStatus = ValidStatus.NORMAL;
	
	
	
	
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="f_valid_status")
	public ValidStatus getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(ValidStatus validStatus) {
		this.validStatus = validStatus;
	}

	@ManyToOne
	@JoinColumn(name="f_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name="f_administration")
	public Administration getAdministration() {
		return administration;
	}

	public void setAdministration(Administration administration) {
		this.administration = administration;
	}

	@Column(name="f_address")
	public String getAddress() {
		return address;
	}

	
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name="f_phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name="f_post_code")
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name="f_person")
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	
	@Column(name="f_selected")
	@Convert(converter=BooleanToIntegerConverter.class)
	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	
	
	
	
	
	

}
