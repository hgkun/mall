package com.shopping.base.service;

import java.util.List;

import com.shopping.base.entity.ItemType;
import com.shopping.base.enums.ValidStatus;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

public interface ItemTypeService extends BaseService<ItemType> {

	List<ItemType> list(Pager pager);

	QueryResult<ItemType> query(String keyword, ValidStatus validStatus, Pager pager);

	List<ItemType> listForFather();

	List<ItemType> listForchild(ItemType itemType);


	List<ItemType> getTypeListByKeywordAndItid(String keyword, String itId);

}
