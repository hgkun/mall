package com.shopping.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shopping.base.dao.BaseDao;
import com.shopping.base.dao.ReviewDao;
import com.shopping.base.entity.Review;
import com.shopping.base.service.ReviewService;
import com.shopping.core.dto.Pager;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
@Service
public class ReviewServiceImpl extends AbstractBaseServiceImpl<Review> implements ReviewService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3529298365346612966L;
	@Resource
	private ReviewDao reviewDao;

	@Override
	public BaseDao<Review> getBaseDao() {
		// TODO Auto-generated method stub
		return reviewDao;
	}
	
	
	public List<Review> list(String id, Pager pager){
		return reviewDao.list(pager.getOffset(), pager.getSize(), "o.item.id = ? ", id);
	}

}
