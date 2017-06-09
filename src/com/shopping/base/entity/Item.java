package com.shopping.base.entity;

import java.math.BigDecimal;

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
@Table(name="tb_item")
public class Item extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	
	private BigDecimal price;
	
	private String imgurl;
	
	private ItemType itemType;
	
	private String itemName;
	
	private ValidStatus validStatus;
	
	
	
	
	
	
	
	
	@Column(name="f_item_name")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name="f_title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	@Column(name="f_price")
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name="f_img_url")
	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_item_type")
	public ItemType getItemType() {
		return itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name="f_valid_status")
	public ValidStatus getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(ValidStatus validStatus) {
		this.validStatus = validStatus;
	}


	
	

	
	
	

}
