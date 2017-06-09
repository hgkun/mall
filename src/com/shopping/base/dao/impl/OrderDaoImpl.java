package com.shopping.base.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.shopping.base.dao.OrderDao;
import com.shopping.base.entity.Order;

@Repository
public class OrderDaoImpl extends AbstractBaseDaoImpl<Order> implements OrderDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3283927969533553590L;
	
	
	@Override
	public List<Map<String, Object>> getPersonDetail(String id){
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(" o.f_order_status as status, ");
		sql.append(" count(o.f_id) as num ");
		sql.append(" from tb_order o ");
		sql.append(" where o.f_user = ? ");
		sql.append(" group by o.f_order_status ");
		return jdbcTemplate.queryForList(sql.toString(), id);
	}

}
