package com.shopping.base.service;

import java.io.Serializable;
import java.util.List;

import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

/**
 * service层基类，所有的service层接口都要继承自该接口

 * @version V1.0
 * @since V1.0
 * @param <T> 泛型参数
 * @see com.tentcoo.core.service.impl.AbstractBaseServiceImpl
 */
public interface BaseService<T> extends Serializable{
	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体
	 */
	public boolean save(T entity);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            实体
	 */
	public boolean update(T entity);
	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            实体
	 */
	public boolean update(T entity, Serializable entityId, boolean lock);

	/**
	 * 删除实体
	 * 
	 * @param entityIds
	 *            实体ids
	 */
	public void delete(Serializable... entityIds);

	/**
	 * 获得实体
	 * 
	 * @param id
	 *            实体id
	 */
	public T get(Serializable id);

	/**
	 * 判断实体是否存在
	 * 
	 * @param entityId
	 *            实体id
	 * @return 存在返回true，不存在返回false。
	 */
	public boolean isEntityExist(Serializable entityId);

	/**
	 * 查询全部记录
	 * 
	 * @param pager
	 *            分页
	 * @return 如果pager为空，则返回null，否则返回QueryResult
	 */
	public QueryResult<T> query(Pager pager);
	/**
	 * 查询所有的实体
	 * @return 实体列表
	 */
	public List<T> all();
	/**
	 * 更新多个信息（不加锁更新）
	 * @param properties 属性名称
	 * @param values 属性值
	 * @param entityId 实体id
	 * @return
	 */
	public boolean updateProperties(String[] properties, Object[] values, Serializable entityId);
	/**
	 * 更新单个信息（不加锁更新）
	 * @param property 属性名称
	 * @param value 属性值
	 * @param entityId 实体id
	 * @return
	 */
	public boolean updateProperty(String property, Object value, Serializable entityId);
	/**
	 * 更新多个信息
	 * @param properties 属性名称
	 * @param values 属性值
	 * @param entityId 实体id
	 * @param lock 是否加锁
	 * @return
	 */
	public boolean updateProperties(String[] properties, Object[] values, Serializable entityId, boolean lock);
	/**
	 * 更新单个信息
	 * @param property 属性名称
	 * @param value 属性值
	 * @param entityId 实体id
	 * @param lock 是否加锁
	 * @return
	 */
	public boolean updateProperty(String property, Object value, Serializable entityId, boolean lock);
}
