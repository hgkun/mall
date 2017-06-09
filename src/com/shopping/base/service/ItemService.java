package com.shopping.base.service;

import java.util.List;

import com.shopping.base.entity.Item;
import com.shopping.base.entity.ItemType;
import com.shopping.base.enums.ValidStatus;
import com.shopping.core.dto.Order;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

public interface ItemService extends BaseService<Item> {

	List<Item> list(ItemType itemType, Pager pager);


	QueryResult<Item> query(String keyword, ValidStatus validStatus, Pager pager);


	QueryResult<Item> query(String keyword, Order order, ItemType itemType, Pager pager);

}
