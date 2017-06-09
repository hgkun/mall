package com.shopping.base.dao;

import java.util.Map;

import com.shopping.base.entity.ShopCart;

public interface ShopCartDao extends BaseDao<ShopCart> {

	Map<String, Object> queryCount(String[] ids);

}
