package com.shopping.base.service;

import java.util.List;
import java.util.Map;

import com.shopping.base.entity.Item;
import com.shopping.base.entity.ShopCart;
import com.shopping.base.entity.User;


public interface ShopCartService extends BaseService<ShopCart> {

	List<ShopCart> list(User user);

	ShopCart findByUserAndItem(User user, Item item);

	Map<String, Object> queryCount(String[] ids);

}
