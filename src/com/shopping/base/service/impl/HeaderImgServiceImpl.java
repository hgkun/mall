package com.shopping.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.shopping.base.dao.BaseDao;
import com.shopping.base.dao.HeaderImgDao;
import com.shopping.base.entity.HeaderImg;
import com.shopping.base.enums.ValidStatus;
import com.shopping.base.service.HeaderImgService;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

/**
 * 
 * 轮播server
 * @author wangyihui
 */
@Service
public class HeaderImgServiceImpl extends AbstractBaseServiceImpl<HeaderImg> implements HeaderImgService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7602067461417609209L;

	@Resource
	private HeaderImgDao headerImgDao;
	@Override
	public BaseDao<HeaderImg> getBaseDao() {
		// TODO Auto-generated method stub
		return headerImgDao;
	}
	
	@Override
	public List<HeaderImg> list(Pager pager){
		
		return headerImgDao.list(pager.getOffset(),pager.getSize(),"o.validStatus = ?", ValidStatus.NORMAL);
	}
	
	
	@Override
	public QueryResult<HeaderImg> query(String keyword,ValidStatus validStatus,Pager pager){
		
		StringBuffer sb = new StringBuffer("1=1 ");
		List<Object> params = new ArrayList<>();
		
		if (validStatus!=null) {
			sb.append( "and o.validStatus = ?");
			params.add(validStatus);
		}
		if (StringUtils.isNotBlank(keyword)) {
			
			sb.append(" and ( o.item.title like ? or o.item.itemName like ? )");
			String param = "%"+keyword.trim()+"%";
			params.add(param);
			params.add(param);
		}
		return headerImgDao.query(pager.getOffset(), pager.getSize(),sb.toString() , params.toArray());
	}

}
