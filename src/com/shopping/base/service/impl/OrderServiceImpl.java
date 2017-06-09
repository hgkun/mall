package com.shopping.base.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.shopping.base.dao.BaseDao;
import com.shopping.base.dao.ItemDao;
import com.shopping.base.dao.OrderDao;
import com.shopping.base.entity.Item;
import com.shopping.base.entity.Order;
import com.shopping.base.entity.User;
import com.shopping.base.enums.OrderStatus;
import com.shopping.base.service.OrderService;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
@Service
public class OrderServiceImpl extends AbstractBaseServiceImpl<Order> implements OrderService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7419487206888908830L;

	@Resource
	private OrderDao orderDao;
	
	@Resource
	private ItemDao itemDao;
	@Override
	public BaseDao<Order> getBaseDao() {
		// TODO Auto-generated method stub
		return orderDao;
	}

	@Transactional
	@Override
	public boolean CreateOrder(User user,OrderStatus orderStatus,String Iid,Integer num ) throws SQLException{
		
		Order order = new Order();
		order.setUser(user);
		Item item = itemDao.find(Iid);
		order.setItem(item);
		order.setNum(num);
		order.setRealPrice(item.getPrice().multiply(new BigDecimal(num)));
		order.setOrderStatus(orderStatus);
		orderDao.persist(order);
		return true;
	}
	
	@Override 
	public String getNewId(User user,String iid){
		List<Order> items = orderDao.list("o.user.id = ? and o.item.id = ? order by o.createTime desc",user.getId(),iid );
		if (items!=null && !items.isEmpty()) {
			return items.get(0).getId();
		}
		return"";
	}
	
	@Override
	public QueryResult<Order> query(String keyword,OrderStatus orderStatus,Pager pager){
		
		StringBuffer sb = new StringBuffer("1=1 ");
		List<Object> params = new ArrayList<>();
		
		if (orderStatus!=null) {
			sb.append( "and o.orderStatus = ?");
			params.add(orderStatus);
		}
		if (StringUtils.isNotBlank(keyword)) {
			
			sb.append(" and ( o.user.name like ? or o.desc like ? or o.item.itemName like ?)");
			String param = "%"+keyword.trim()+"%";
			params.add(param);
			params.add(param);
			params.add(param);
	
		}
		return orderDao.query(pager.getOffset(), pager.getSize(), sb.toString(), params.toArray());
	}

	@Override
	public Object getPersonDetail(User user){
		List<Map<String, Object>> result =  orderDao.getPersonDetail(user.getId());
		OrderStatus[] status = OrderStatus.values();
		Map<String, Object> map1 = new HashMap<>();
		if (result!=null && !result.isEmpty()) {
			for (Map<String, Object> map : result) {
				Map<String, Object> map2 = new HashMap<>();
				map2.put("status", status[(int)map.get("status")].name());
				map2.put("num", map.get("num"));
				map1.put(status[(int)map.get("status")].getValue(), map2);
			}
		}
		return map1;
	}
	
	@Override
	public List<Order> list(User user){
		return orderDao.list("o.user=? and o.orderStatus=?",user, OrderStatus.OBLIGATION);
		
	}
	
	
	@Override
	public List<Order> listForOrderByStatus(User user ,OrderStatus orderStatus){
		
		return orderDao.list("o.user=? and o.orderStatus=? ",user,orderStatus );
	}
	
	
}
