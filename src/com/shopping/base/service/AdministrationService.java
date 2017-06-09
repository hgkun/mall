package com.shopping.base.service;

import java.util.List;
import java.util.Map;

import com.shopping.base.entity.Administration;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

public interface AdministrationService extends BaseService<Administration> {

	void saveAll(List<Administration> list);

	QueryResult<Administration> query(String keyword, Pager pager);

	List<Map<String, Object>> getCounty(String key1, String key2);

	List<Map<String, Object>> getCity(String key);

	List<Map<String, Object>> getProvince();

}
