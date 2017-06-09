package com.shopping.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.shopping.base.dao.BaseDao;
import com.shopping.base.dao.MessageDao;
import com.shopping.base.entity.Message;
import com.shopping.base.entity.User;
import com.shopping.base.enums.ValidStatus;
import com.shopping.base.service.MessageService;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
@Service
public class MessageServiceImpl extends AbstractBaseServiceImpl<Message> implements MessageService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 58022956011245384L;

	@Resource
	private MessageDao messageDao;
	@Override
	public BaseDao<Message> getBaseDao() {
		// TODO Auto-generated method stub
		return messageDao;
	}
	
	@Override
	public QueryResult<Message> query(String keyword,ValidStatus validStatus,Pager pager){
	
		StringBuffer sb = new StringBuffer("1=1 ");
		List<Object> params = new ArrayList<>();
		
		if (validStatus!=null) {
			sb.append( "and o.validStatus = ?");
			params.add(validStatus);
		}
		if (StringUtils.isNotBlank(keyword)) {
			
			sb.append(" and ( o.title like ? or o.context like ? )");
			String param = "%"+keyword.trim()+"%";
			params.add(param);
			params.add(param);
		}
		return messageDao.query(pager.getOffset(), pager.getSize(),sb.toString() , params.toArray());
	}
	
	
	@Override
	public long count(User user){
		return messageDao.getCount("o.user = ? and o.validStatus = ?", user,ValidStatus.NORMAL);
	}
	
	@Override
	public List<Message> list(User user){
		return messageDao.list("o.user = ? and o.validStatus = ?", user,ValidStatus.NORMAL);
	}

}
