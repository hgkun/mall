package com.shopping.base.dao;

import com.shopping.base.entity.Address;

public interface AddressDao extends BaseDao<Address> {
	
	
	int updateProperty(String sql,Object...params );

}
