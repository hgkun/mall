package com.shopping.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.shopping.base.dao.AddressDao;
import com.shopping.base.entity.Address;

@Repository
public class AddressDaoImpl extends AbstractBaseDaoImpl<Address> implements AddressDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4317541379313647018L;

	@Override
	public int updateProperty(String sql, Object... params) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update(sql, params);
	}

}
