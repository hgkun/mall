package com.shopping.base.service;

import java.util.List;

import com.shopping.base.entity.Address;
import com.shopping.base.entity.User;
import com.shopping.core.dto.Order;
import com.shopping.core.dto.Pager;

public interface AddressService extends BaseService<Address> {

	 List<Address> list(Pager pager,Order order,User user);

	int updateProperty(String property, User user);
}
