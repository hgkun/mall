package com.shopping.base.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.shopping.base.dao.AdministrationDao;
import com.shopping.base.entity.Administration;

@Repository
public class AdministrationDaoImpl extends AbstractBaseDaoImpl<Administration> implements AdministrationDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7619605340076430930L;

	@Override
	public List<Map<String, Object>> getProvince() {
		
		StringBuffer sb  = new StringBuffer(); 
		sb.append(" select DISTINCT o.f_province as province from tb_administration o");
		return jdbcTemplate.queryForList(sb.toString());
	}

	@Override
	public List<Map<String, Object>> getCity(String key) {
		StringBuffer sb  = new StringBuffer(); 
		sb.append(" select DISTINCT o.f_city as city from tb_administration o where o.f_province=?");
		return jdbcTemplate.queryForList(sb.toString(),key);
	}

	@Override
	public List<Map<String, Object>> getCounty(String key1,String key2) {
		StringBuffer sb  = new StringBuffer(); 
		sb.append(" select o.f_id as id,o.f_county as county from tb_administration o where o.f_province = ? and o.f_city = ?");
		return jdbcTemplate.queryForList(sb.toString(),key1,key2);
	}

}
