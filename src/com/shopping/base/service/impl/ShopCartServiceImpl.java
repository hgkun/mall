package com.shopping.base.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shopping.base.dao.BaseDao;
import com.shopping.base.dao.ShopCartDao;
import com.shopping.base.entity.Item;
import com.shopping.base.entity.ShopCart;
import com.shopping.base.entity.User;
import com.shopping.base.enums.CartStatus;
import com.shopping.base.service.ShopCartService;

@Service
public class ShopCartServiceImpl extends AbstractBaseServiceImpl<ShopCart> implements ShopCartService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5926772751217323015L;
	
	@Resource
	private ShopCartDao shopCartDao;

	@Override
	public BaseDao<ShopCart> getBaseDao() {
		// TODO Auto-generated method stub
		return shopCartDao;
	}

	@Override
	public List<ShopCart> list(User user){
		
		return shopCartDao.list("o.user = ? and o.cartStatus = ?", user,CartStatus.NORMAL);
	}
	
	@Override
	public ShopCart findByUserAndItem(User user,Item item){
		return shopCartDao.find("o.user = ? and o.item = ?", user,item);
	}
	@Override
	public Map<String , Object> queryCount(String[] ids){
		return shopCartDao.queryCount(ids);
	}
}
