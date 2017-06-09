package com.shopping.base.dao;

import java.util.List;
import java.util.Map;

import com.shopping.base.entity.Administration;

public interface AdministrationDao extends BaseDao<Administration> {
	
	public List<Map<String, Object>> getProvince();
	public List<Map<String, Object>> getCity(String key);
	public List<Map<String, Object>> getCounty(String key1,String key2);

}
