package com.shopping.base.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shopping.core.entity.IdEntity;


@Entity
@Table(name="tb_order_item")
public class OrderItem extends IdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1622839207137085239L;
	
	
	private Order order;
	
	private Item item;
	
	private Integer num;
	
	private BigDecimal realPrice;
	
	@ManyToOne
	@JoinColumn(name="f_order")
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@ManyToOne
	@JoinColumn(name="f_item")
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name="f_num")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name="f_real_price")
	public BigDecimal getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}
	
	
	
	
	

}
