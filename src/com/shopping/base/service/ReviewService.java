package com.shopping.base.service;

import java.util.List;

import com.shopping.base.entity.Review;
import com.shopping.core.dto.Pager;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
public interface ReviewService extends BaseService<Review>{

	
	public List<Review> list(String id, Pager pager);
}
