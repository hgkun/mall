package com.shopping.base.dao;

import java.util.List;
import java.util.Map;

import com.shopping.base.entity.Order;

public interface OrderDao extends BaseDao<Order> {

	List<Map<String, Object>> getPersonDetail(String id);

}
