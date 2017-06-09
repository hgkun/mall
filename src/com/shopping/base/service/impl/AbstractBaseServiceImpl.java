package com.shopping.base.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.LockModeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.base.dao.BaseDao;
import com.shopping.base.service.BaseService;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;
import com.shopping.core.exception.ServiceException;

/**
 * BaseService实现层，所有的service实现层都要继承自该类。

 * @version V1.0
 * @since V1.0
 * @param <T> 泛型参数
 * @see com.shopping.base.service.BaseService
 */

public abstract class AbstractBaseServiceImpl<T extends Serializable> implements BaseService<T> {
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 559268767097149785L;
	/**
	 * 日志记录
	 */
	protected Logger LOG = LoggerFactory.getLogger(getClass());
	/**
	 * 该方法由子类实现
	 * @return 返回baseDao实例
	 */
	public abstract BaseDao<T> getBaseDao();
	@Transactional
	@Override
	public boolean save(T entity) {
		boolean flag = false;
		try {
			getBaseDao().persist(entity);
			flag = true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return flag;
	}
	@Transactional
	@Override
	public boolean update(T entity, Serializable entityId, boolean lock) {
		boolean flag = false;
		try {
			if (lock) getBaseDao().find(entityId, LockModeType.PESSIMISTIC_WRITE);
			getBaseDao().merge(entity);
			flag = true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return flag;
	}
	@Transactional
	@Override
	public boolean update(T entity) {
		boolean flag = false;
		try {
			getBaseDao().merge(entity);
			flag = true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return flag;
	}
	@Transactional
	@Override
	public void delete(Serializable... entityIds) {
		try {
			getBaseDao().remove(entityIds);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public T get(Serializable id) {
		if (null == id) return null;
		try {
			return getBaseDao().find(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean isEntityExist(Serializable entityId) {
		if (null == entityId)
			return false;
		try {
			return getBaseDao().isEntityExist(entityId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public QueryResult<T> query(Pager pager) {
		try {
			QueryResult<T> qr = null;
			if (null != pager) {
				qr = getBaseDao().query(pager.getOffset(), pager.getSize());
			}
			return qr;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<T> all() {
		try {
			return getBaseDao().list(null);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Transactional
	@Override
	public boolean updateProperties(String[] properties, Object[] values, Serializable entityId) {
		return updateProperties(properties, values, entityId, false);
	}
	
	@Transactional
	@Override
	public boolean updateProperty(String property, Object value, Serializable entityId) {
		return updateProperties(new String[]{property}, new Object[]{value}, entityId, false);
	}
	@Transactional
	@Override
	public boolean updateProperties(String[] properties, Object[] values, Serializable entityId, boolean lock) {
		try {
			if (lock) getBaseDao().find(entityId, LockModeType.PESSIMISTIC_WRITE);
			return getBaseDao().updateProperties(properties, values, entityId) == 1;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Transactional
	@Override
	public boolean updateProperty(String property, Object value, Serializable entityId, boolean lock) {
		return updateProperties(new String[]{property}, new Object[]{value}, entityId, lock);
	}
	
}
