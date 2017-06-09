package com.shopping.base.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.shopping.base.enums.ValidStatus;
import com.shopping.core.entity.IdEntity;

/**
 * 打折活动
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_discount")
public class Discount extends IdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2681331657866888962L;
	
	private BigDecimal discount;
	
	private Date startDate;
	
	private Date endDate;
	
	private Item item;
	
	private ValidStatus validStatus;
	

	
	
	@Column(name="f_discount")
	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "f_start_date")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "f_end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@ManyToOne(fetch=FetchType.LAZY)
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
