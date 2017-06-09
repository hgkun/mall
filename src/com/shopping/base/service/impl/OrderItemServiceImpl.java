package com.shopping.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shopping.base.dao.BaseDao;
import com.shopping.base.dao.OrderItemDao;
import com.shopping.base.entity.OrderItem;
import com.shopping.base.entity.User;
import com.shopping.base.service.OrderItemService;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
@Service
public class OrderItemServiceImpl extends AbstractBaseServiceImpl<OrderItem> implements OrderItemService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3602242753629119751L;
	
	@Resource
	private OrderItemDao orderItemDao;

	@Override
	public BaseDao<OrderItem> getBaseDao() {
		// TODO Auto-generated method stub
		return orderItemDao;
	}
	
	@Override
	public List<OrderItem> list(User user){
		
		return orderItemDao.list("o.order.user = ?", user);
	}
	
	@Override
	public List<OrderItem> list (String oid){
		return orderItemDao.list("o.order.id = ?",oid);
	}
	
	
	
}
