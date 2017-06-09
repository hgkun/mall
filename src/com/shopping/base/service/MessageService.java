package com.shopping.base.service;

import java.util.List;

import com.shopping.base.entity.Message;
import com.shopping.base.entity.User;
import com.shopping.base.enums.ValidStatus;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
public interface MessageService extends BaseService<Message> {

	QueryResult<Message> query(String keyword, ValidStatus validStatus, Pager pager);

	long count(User user);

	List<Message> list(User user);

}
