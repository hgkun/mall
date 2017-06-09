package com.shopping.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shopping.base.dao.AddressDao;
import com.shopping.base.dao.BaseDao;
import com.shopping.base.entity.Address;
import com.shopping.base.entity.User;
import com.shopping.base.enums.ValidStatus;
import com.shopping.base.service.AddressService;
import com.shopping.core.dto.Order;
import com.shopping.core.dto.Pager;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
@Service
public class AddressServiceImpl extends AbstractBaseServiceImpl<Address> implements AddressService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9088651990846115032L;
	@Resource
	private AddressDao addressDao;
	@Override
	public BaseDao<Address> getBaseDao() {
		// TODO Auto-generated method stub
		return addressDao;
	}
	
	@Override
	public List<Address> list(Pager pager,Order order,User user){
		
		return addressDao.list(order, pager.getOffset(), pager.getSize(), "o.user=? and o.validStatus=?",  user,ValidStatus.NORMAL);
	}
	
	@Override
	public int updateProperty(String property,User user ){
		StringBuffer sql = new StringBuffer("UPDATE tb_address set ");
		sql.append(property+"=false ");
		sql.append("where ");
		sql.append(" f_user = ? ");
		return addressDao.updateProperty(sql.toString(), user.getId());
		
	}

}
