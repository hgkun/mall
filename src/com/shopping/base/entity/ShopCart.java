package com.shopping.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shopping.base.enums.CartStatus;
import com.shopping.core.entity.IdEntity;


@Entity
@Table(name="tb_shop_cart")
public class ShopCart extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -382574698486783095L;
	
	private Item item;
	private User user;
	private Integer num;
	private CartStatus cartStatus;
	@ManyToOne
	@JoinColumn(name="f_item")
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	@ManyToOne
	@JoinColumn(name="f_user")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Column(name="f_num")
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	@Enumerated(EnumType.ORDINAL)
	@Column(name="f_cart_status")
	public CartStatus getCartStatus() {
		return cartStatus;
	}
	public void setCartStatus(CartStatus cartStatus) {
		this.cartStatus = cartStatus;
	}
	
	
}
