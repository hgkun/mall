package com.shopping.base.service;

import java.util.Date;

import com.shopping.base.entity.Discount;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

public interface DiscountService extends BaseService<Discount> {

	QueryResult<Discount> query(String keywork, Pager pager, Date startDate, Date endDate);

}
