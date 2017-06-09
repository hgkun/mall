package com.shopping.base.service;

import java.sql.SQLException;
import java.util.List;

import com.shopping.base.entity.Order;
import com.shopping.base.entity.User;
import com.shopping.base.enums.OrderStatus;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

public interface OrderService extends BaseService<Order> {


	String getNewId(User user ,String iid);


	QueryResult<Order> query(String keyword, OrderStatus orderStatus, Pager pager);

	boolean CreateOrder(User user, OrderStatus orderStatus, String Iid, Integer num) throws SQLException;


	Object getPersonDetail(User user);


	List<Order> listForOrderByStatus(User user, OrderStatus orderStatus);


	List<Order> list(User user);

}
