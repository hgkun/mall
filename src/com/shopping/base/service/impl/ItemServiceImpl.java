package com.shopping.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.shopping.base.dao.BaseDao;
import com.shopping.base.dao.ItemDao;
import com.shopping.base.entity.Item;
import com.shopping.base.entity.ItemType;
import com.shopping.base.enums.ValidStatus;
import com.shopping.base.service.ItemService;
import com.shopping.core.dto.Order;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;
/**
 * 

 * @version V1.0
 * @since V1.0
 */
@Service
public class ItemServiceImpl extends AbstractBaseServiceImpl<Item> implements ItemService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7538258313801820715L;
	@Resource
	private ItemDao itemDao;
	@Override
	public BaseDao<Item> getBaseDao() {
		// TODO Auto-generated method stub
		return itemDao;
	}
	
	@Override
	public List< Item > list(ItemType itemType ,Pager pager){
		if (itemType==null) {
			return new ArrayList<>();
		}
		return itemDao.list(pager.getOffset(), pager.getSize(), " o.itemType=? and o.validStatus=? ", itemType,ValidStatus.NORMAL);
	}
	
	@Override
	public QueryResult<Item> query(String keyword,ValidStatus validStatus,Pager pager){
		
		StringBuffer sb = new StringBuffer("1=1 ");
		List<Object> params = new ArrayList<>();
		
		if (validStatus!=null) {
			sb.append( "and o.vaildStatus = ?");
			params.add(validStatus);
		}
		if (StringUtils.isNoneBlank(keyword)) {
			
			sb.append(" and ( o.itemName like ? or o.title like ? )");
			String param = "%"+keyword.trim()+"%";
			params.add(param);
			params.add(param);
		}
		return itemDao.query(pager.getOffset(), pager.getSize(),sb.toString() , params.toArray());
	}
	
	@Override
	public QueryResult<Item> query(String keyword,Order order,ItemType itemType,Pager pager){
		StringBuffer sb = new StringBuffer("1=1");
		List<Object> params = new ArrayList<>();
		
		sb.append(" and o.validStatus = ?");
		params.add(ValidStatus.NORMAL);
		if (itemType!=null && itemType.getItemType()!=null) {
		sb.append(" and o.itemType = ?");
		params.add(itemType);
		}else if(itemType!=null&& itemType.getItemType()==null){
			sb.append(" and (o.itemType = ? or o.itemType.itemType = ?)");
			params.add(itemType);
			params.add(itemType);
		}
		
		if (StringUtils.isNotBlank(keyword)) {
			sb.append(" and ( o.itemName like ? or o.title like ? )");
			String param = "%"+keyword.trim()+"%";
			params.add(param);
			params.add(param);
		}
		if (order!=null) {
			return itemDao.query(order, pager.getOffset(), pager.getSize(), sb.toString(), params.toArray());
		}
		
		return itemDao.query( pager.getOffset(), pager.getSize(), sb.toString(), params.toArray());
	}
	

}
