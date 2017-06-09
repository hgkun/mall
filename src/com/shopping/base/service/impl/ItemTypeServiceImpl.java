package com.shopping.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.shopping.base.dao.BaseDao;
import com.shopping.base.dao.ItemDao;
import com.shopping.base.dao.ItemTypeDao;
import com.shopping.base.entity.Item;
import com.shopping.base.entity.ItemType;
import com.shopping.base.enums.ValidStatus;
import com.shopping.base.service.ItemTypeService;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
@Service
public class ItemTypeServiceImpl extends AbstractBaseServiceImpl<ItemType> implements ItemTypeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3948884497666867693L;
	@Resource
	private ItemTypeDao itemTypeDao;
	@Resource
	private ItemDao itemDao;
	@Override
	public BaseDao<ItemType> getBaseDao() {
		// TODO Auto-generated method stub
		return itemTypeDao;
	}
	
	@Override
	public List<ItemType> list(Pager pager){
		
		return itemTypeDao.list(pager.getOffset(), pager.getSize(), "", null);
	}
	
	@Override
	public QueryResult<ItemType> query(String keyword,ValidStatus validStatus,Pager pager){
		StringBuffer sb = new StringBuffer("1=1 ");
		List<Object> params = new ArrayList<>();
		
		if (validStatus!=null) {
			sb.append( "and o.validStatus = ?");
			params.add(validStatus);
		}
		if (StringUtils.isNotBlank(keyword)) {
			
			sb.append(" and o.name like ? ");
			String param = "%"+keyword.trim()+"%";
			params.add(param);
		}
		return itemTypeDao.query(pager.getOffset(), pager.getSize(),sb.toString() , params.toArray());
		
	}
	@Override
	public List<ItemType> listForFather(){
		return itemTypeDao.list("o.itemType is null", null);
	}
	
	@Override
	public List<ItemType> listForchild(ItemType itemType){
		return itemTypeDao.list("o.itemType = ?", itemType);
	}
	
	@Override
	public List<ItemType> getTypeListByKeywordAndItid(String keyword,String itId){
		StringBuffer sb = new StringBuffer(" 1=1 ");
		List<Object> params = new ArrayList<>();
		List<ItemType> list = new ArrayList<>();
		Item item = null;
		if (!StringUtils.isBlank(itId)) {
			sb.append(" and o.itemType.id = ?");
			params.add(itId);
		}
		if (StringUtils.isNoneBlank(keyword)) {
			String param = "%"+keyword.trim()+"%";
			sb.append(" and (o.itemName like ? or o.title like ?) ");
			params.add(param);
			params.add(param);
		}
		List<Item> items = itemDao.list(sb.toString(),params.toArray());
		if (items!=null && !items.isEmpty()) {
			item = items.get(0);
			ItemType itemType = item.getItemType();
			if (itemType.getItemType() ==null) {
				list = listForchild(itemType);
			}else{
				list = listForchild(itemType.getItemType());
			}
		}
		return list;
	}

}
