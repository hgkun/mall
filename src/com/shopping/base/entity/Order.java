package com.shopping.base.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shopping.base.enums.OrderStatus;
import com.shopping.core.entity.IdEntity;

@Entity
@Table(name="tb_order")
public class Order extends IdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6695506944497316229L;
	
	private User user;
	
	private OrderStatus orderStatus;
	
	private BigDecimal realPrice;
	
	private String desc;
	
	private Address address; 
	
	private Item item;
	
	private Integer num;
	
	
	
	
	@Column(name="f_num")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@ManyToOne
	@JoinColumn(name="f_item")
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name="f_desc")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@ManyToOne
	@JoinColumn(name="f_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	@Enumerated(EnumType.ORDINAL)
	@Column(name="f_order_status")
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name="f_real_price")
	public BigDecimal getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(BigDecimal realPrice) {
		this.realPrice = realPrice;
	}
	@ManyToOne
	@JoinColumn(name="f_address")
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
	
}
