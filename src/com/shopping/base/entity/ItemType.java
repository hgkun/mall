package com.shopping.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shopping.base.enums.ValidStatus;
import com.shopping.core.entity.IdEntity;


@Entity
@Table(name="tb_item_type")
public class ItemType extends IdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8297559638948288348L;
	
	private String name;
	
	private ItemType itemType;
	
	private ValidStatus ValidStatus;
	
	
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="f_valid_status")
	public ValidStatus getValidStatus() {
		return ValidStatus;
	}

	public void setValidStatus(ValidStatus validStatus) {
		ValidStatus = validStatus;
	}

	@Column(name="f_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_item_type")
	public ItemType getItemType() {
		return itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}




	
	

}
