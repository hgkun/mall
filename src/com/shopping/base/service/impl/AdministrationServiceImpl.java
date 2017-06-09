package com.shopping.base.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.shopping.base.dao.AdministrationDao;
import com.shopping.base.dao.BaseDao;
import com.shopping.base.entity.Administration;
import com.shopping.base.service.AdministrationService;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

@Service
public class AdministrationServiceImpl extends AbstractBaseServiceImpl<Administration> implements AdministrationService{

	@Resource
	private AdministrationDao administrationDao;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1961192390960981695L;

	@Override
	public BaseDao<Administration> getBaseDao() {
		// TODO Auto-generated method stub
		return administrationDao;
	}
	
	@Override
	@Transactional
	public void saveAll(List<Administration> list){
			if (list!=null &&!list.isEmpty()) {
				for (Administration administration : list) {
					administrationDao.persist(administration);
				}
			}
	}
	@Override
	public QueryResult<Administration> query(String keyword, Pager pager) {
		StringBuffer sb = new StringBuffer(); 
		
		if (!StringUtils.isBlank(keyword)) {
			sb.append("o.province like ? or o.city like ? or o.county like ? ");
			String params = "%"+keyword+"%";
			return administrationDao.query(pager.getOffset(), pager.getSize(), sb.toString(),params,params,params );
		}
		
		return administrationDao.query(pager.getOffset(), pager.getSize());
	}
	
	@Override
	public List<Map<String, Object>> getProvince(){
		return administrationDao.getProvince();
	}
	
	@Override
	public List<Map<String, Object>> getCity(String key){
		return administrationDao.getCity(key);
	}
	@Override
	public List<Map<String, Object>> getCounty(String key1,String key2){
		return administrationDao.getCounty(key1, key2);
	}
	

}
