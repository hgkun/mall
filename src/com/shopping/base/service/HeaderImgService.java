package com.shopping.base.service;

import java.util.List;

import com.shopping.base.entity.HeaderImg;
import com.shopping.base.enums.ValidStatus;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
public interface HeaderImgService extends BaseService<HeaderImg> {

	List<HeaderImg> list(Pager pager);

	QueryResult<HeaderImg> query(String keyword, ValidStatus validStatus, Pager pager);

}
