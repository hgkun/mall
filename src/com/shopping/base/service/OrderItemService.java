package com.shopping.base.service;

import java.util.List;

import com.shopping.base.entity.OrderItem;
import com.shopping.base.entity.User;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
public interface OrderItemService extends BaseService<OrderItem> {

	List<OrderItem> list(User user);

	List<OrderItem> list(String oid);

}
