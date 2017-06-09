package com.shopping.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shopping.base.enums.ValidStatus;
import com.shopping.core.entity.IdEntity;

/**
 * 头部轮播图片表

 * @version V1.0
 * @since V1.0
 */
@Entity
@Table(name="tb_header_img")
public class HeaderImg extends IdEntity {
	private static final long serialVersionUID = 9091015606758991140L;
	
	private String url;
	
	private Item item;
	
	private ValidStatus validStatus;

	@Column(name="f_url")
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	@ManyToOne
	@JoinColumn(name="f_item")
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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
