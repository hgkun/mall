/**
 * $Id: UserServiceImpl.java Nov 18, 2014 11:03:41 AM hdp
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shopping.base.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.shopping.base.dao.BaseDao;
import com.shopping.base.dao.UserDao;
import com.shopping.base.entity.User;
import com.shopping.base.service.UserService;
import com.shopping.core.dto.Order;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

/**
 * 用户service实现

 * @version V1.0
 * @since V1.0
 */
@Service
public class UserServiceImpl extends AbstractBaseServiceImpl<User> implements UserService {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3353124519663143312L;
	/**
	 * 用户dao
	 */
	@Resource
	private UserDao userDao;
	
	/* (non-Javadoc)
	 * @see com.tentcoo.base.service.impl.AbstractBaseServiceImpl#getBaseDao()
	 */
	@Override
	public BaseDao<User> getBaseDao() {
		return userDao;
	}

	/* (non-Javadoc)
	 * @see com.tentcoo.base.service.UserService#find(java.lang.String, java.lang.String)
	 */
	@Override
	public User find(String phone, String password) {
		User user = userDao.find("o.account=?or o.phone=?", phone,phone);
		if (null == user) return null;
		String tmp = password + user.getSalt();
		String md5 = DigestUtils.md5Hex(tmp);
		if (!user.getPassword().equals(md5)) return null;
		return user;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tentcoo.base.service.UserService#isExist(java.lang.String)
	 */
	@Override
	public boolean isExist(String account) {
		return userDao.isEntityExist("o.phone=?1 or o.account=?2", account, account);
		
	}


	@Override
	public boolean isPhoneExist(String phone) {
		return userDao.isEntityExist("o.phone=?1", phone);
	}

	@Override
	public boolean isAccountExist(String account) {
		return userDao.isEntityExist("o.account=?1", account);
	}

	/* (non-Javadoc)
	 * @see com.tentcoo.base.service.UserService#find(java.lang.String)
	 */
	@Override
	public User find(String phone) {
		return userDao.find("o.phone=?1", phone);
	}

	/* (non-Javadoc)
	 * @see com.tentcoo.base.service.UserService#query(java.lang.String, com.tentcoo.core.dto.Pager)
	 */
	@Override
	public QueryResult<User> query(String keyword, Pager pager) {
		return querySort(null, keyword, pager);
	}

	

	@Override
	public User findUser(String phone, String password) {
		User user = userDao.find("o.phone=?1 or o.account=?2", phone, phone);
		if(user==null)
			return null;
		String tmp = password + user.getSalt();
		
		if(!user.getPassword().equals(DigestUtils.md5Hex(tmp)))
			return null;
		
		return user;
	}
	@Transactional
	@Override
	public boolean changePassword(User user, String password) {
		User tmp = userDao.find(user.getId(), LockModeType.PESSIMISTIC_WRITE);
		tmp.setPassword(DigestUtils.md5Hex(password + tmp.getSalt()));
		userDao.merge(tmp);
		return true;
	}

	@Override
	public List<User> queryUserByGroup(String groupId ) {
		return userDao.list("o.group.id=?1 or o.group.parentGroup.id = ?2", new Object[]{groupId,groupId});
	}

	@Override
	public QueryResult<User> queryUserByGroup(String groupId, String keyword, Pager pager) {
		Order order = new Order();
		order.put("createTime", Order.DESC);
		if (StringUtils.isNotBlank(keyword)) {
			String param = "%"+keyword.trim()+"%";
			return userDao.query(order, pager.getOffset(), pager.getSize(), "o.name like ?1  and o.group.id = ?2", param, groupId);
		}
		return userDao.query(order, pager.getOffset(), pager.getSize()," o.group.id = ?1",groupId);
	}
	
	@Override
	public QueryResult<User> querySort(Order order, String keyword, Pager pager) {
		if (null == order) {
			order = new Order();
			order.put("createTime", Order.DESC);
		}
		if (StringUtils.isNotBlank(keyword)) {
			String param = "%"+keyword.trim()+"%";
			return userDao.query(order, pager.getOffset(), pager.getSize(), "o.name like ?1 or o.account like ?2 or o.idcard like ?3 or o.phone like ?4", param, param, param, param);
		}
		return userDao.query(order, pager.getOffset(), pager.getSize());
	}
	
	
}
