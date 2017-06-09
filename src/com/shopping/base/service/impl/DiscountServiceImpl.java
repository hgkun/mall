package com.shopping.base.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shopping.base.dao.BaseDao;
import com.shopping.base.dao.DiscountDao;
import com.shopping.base.entity.Discount;
import com.shopping.base.service.DiscountService;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
@Service
public class DiscountServiceImpl extends AbstractBaseServiceImpl<Discount> implements DiscountService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8064999958308754061L;
	@Resource
	private DiscountDao discountDao;
	
	@Override
	public BaseDao<Discount> getBaseDao() {
		// TODO Auto-generated method stub
		return discountDao;
	}
	
	@Override
	public QueryResult<Discount> query(String keywork,Pager pager,Date startDate,Date endDate){
		
		return null;
	}

}
