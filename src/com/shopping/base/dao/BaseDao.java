package com.shopping.base.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.LockModeType;

import com.shopping.core.dto.Order;
import com.shopping.core.dto.QueryResult;
import com.shopping.core.exception.DaoException;

/**
 * 所有dao的基础接口

 * @version V1.0
 * @since V1.0
 */
public interface BaseDao<T extends Serializable> extends Serializable{
	/**
	 * 持久化实体
	 * @param entity 实体
	 * @throws DaoException 当jpa运行时发生错误时抛出
	 */
	public void persist(T entity);

	/**
	 * 更新实体
	 * @param entity 实体
	 * @throws DaoException 当jpa运行时发生错误时抛出
	 */
	public T merge(T entity);

	/**
	 * 删除实体
	 * @param entityIds 实体id数组
	 * @throws DaoException 当jpa运行时发生错误时抛出
	 */
	public void remove(Serializable... entityIds);

	/**
	 * 通过实体id获得实体
	 * @param entityId 实体id
	 * @return 存在返回实体，不存在返回null
	 */
	public T find(Serializable entityId);
	/**
	 * 通过实体id获得实体
	 * @param entityId 实体id
	 * @param lockModeType  the lock mode to obtain
	 * @return 存在返回实体，不存在返回null
	 */
	public T find(Serializable entityId, LockModeType lockModeType);

	/**
	 * 通过条件获得实体
	 * @param whereSql 条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param params 参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @return 存在返回实体，不存在返回null
	 */
	public T find(String whereSql, Object... params);
	/**
	 * 通过条件获得实体
	 * @param whereSql 条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param lockModeType lockModeType  the lock mode to obtain
	 * @param params 参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @return 存在返回实体，不存在返回null
	 */
	public T find(String whereSql, LockModeType lockModeType, Object... params);

	/**
	 * 通过条件，排序获得实体个数。
	 * @param whereSql  条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param params 
	 * <p>参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 *            更新为可变参数，传入数组也是可以的
	 * </p>
	 * @return 存在记录返回大于0的整数，不存在返回0。
	 */
	public long getCount(String whereSql, Object... params);
	
	
	public long getCount(String field,String whereSql, Object... params);
	
	
	/**
	 * 直接使用jpql语句进行修改操作
	 * @param jpql 
	 * @param params
	 * @return
	 */
	public int update(String jpql, Object... params);
	/**
	 * 通过条件获得实体列表。
	 * 
	 * @param whereSql 条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param params
	 * <p>参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 *            更新为可变参数，传入对象数组也是可以的
	 * </p>
	 * @return 返回List对象，里面包含T
	 */
	public List<T> list(String whereSql, Object... params);
	/**
	 * 通过条件获得实体列表。
	 * 
	 * @param whereSql 条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param lockModeType 锁表模式
	 * @param params
	 * <p>参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 *            更新为可变参数，传入对象数组也是可以的
	 * </p>
	 * @return 返回List对象，里面包含T
	 */
	public List<T> list(String whereSql, LockModeType lockModeType, Object... params);
	
	/**
	 * 通过条件，排序获得实体列表。
	 * 
	 * @param whereSql
	 *            条件， 如："o.username=?1 and o.password=?2" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param params
	 *            参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @param order
	 *            排序语句，如：order.put("username",Order.ASC)升序
	 *            或者order.put("username",Order.DESC)降序; 例子：Order order = new Order();
	 *            order.put("username",Order.DESC);
	 * @return 返回List对象，里面包含T
	 */
	public List<T> list(Order order, String whereSql, Object... params);
	/**
	 * 通过条件，排序获得实体列表。
	 * 
	 * @param whereSql
	 *            条件， 如："o.username=?1 and o.password=?2" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param params
	 *            参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @param order
	 *            排序语句，如：order.put("username",Order.ASC)升序
	 *            或者order.put("username",Order.DESC)降序; 例子：Order order = new Order();
	 *            order.put("username",Order.DESC);
	 * @param lockModeType 锁表模式
	 * @return 返回List对象，里面包含T
	 */
	public List<T> list(Order order, String whereSql, LockModeType lockModeType, Object... params);
	/**
	 * 有分页，通过条件，排序获得实体列表。
	 * 
	 * @param firstResult
	 *            第一个记录数 大于-1的时候才会分页
	 * @param maxResults
	 *            最大记录数 大于-1的时候才会分页
	 * @param whereSql
	 *            条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param params
	 *            参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @return 返回List对象，里面包含T
	 */
	public List<T> list(int firstResult, int maxResults, String whereSql, Object... params);
	/**
	 * 有分页，通过条件，排序获得实体列表。
	 * 
	 * @param firstResult
	 *            第一个记录数 大于-1的时候才会分页
	 * @param maxResults
	 *            最大记录数 大于-1的时候才会分页
	 * @param whereSql
	 *            条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param params
	 *            参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @param lockModeType 锁表模式
	 * @return 返回List对象，里面包含T
	 */
	public List<T> list(int firstResult, int maxResults, String whereSql, LockModeType lockModeType, Object... params);
	/**
	 * 有分页,通过条件，排序获得实体列表。
	 * 
	 * @param firstResult
	 *            第一个记录数 大于-1的时候才会分页
	 * @param maxResults
	 *            最大记录数 大于-1的时候才会分页
	 * @param whereSql
	 *            条件， 如："o.username=?1 and o.password=?2" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param params
	 *            参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @param order
	 *            排序语句，如：order.put("username",Order.ASC)升序
	 *            或者order.put("username",Order.DESC)降序; 例子：Order order = new Order();
	 *            order.put("username",Order.DESC);
	 * @return 返回List对象，里面包含T
	 */
	public List<T> list(Order order, int firstResult, int maxResults, String whereSql, Object... params);
	/**
	 * 有分页,通过条件，排序获得实体列表。
	 * 
	 * @param firstResult
	 *            第一个记录数 大于-1的时候才会分页
	 * @param maxResults
	 *            最大记录数 大于-1的时候才会分页
	 * @param whereSql
	 *            条件， 如："o.username=?1 and o.password=?2" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param params
	 *            参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @param order
	 *            排序语句，如：order.put("username",Order.ASC)升序
	 *            或者order.put("username",Order.DESC)降序; 例子：Order order = new Order();
	 *            order.put("username",Order.DESC);
	 * @param lockModeType 锁表模式
	 * @return 返回List对象，里面包含T
	 */
	public List<T> list(Order order, int firstResult, int maxResults, String whereSql, LockModeType lockModeType, Object... params);
	
	/**
	 * 有分页,通过条件，排序获得实体列表。
	 * 
	 * @param firstResult
	 *            第一个记录数 大于-1的时候才会分页
	 * @param maxResults
	 *            最大记录数 大于-1的时候才会分页
	 * @param join
	 *            联合查询
	 * @param whereSql
	 *            条件， 如："o.username=?1 and o.password=?2" 为空或者为""则不会处理。Hibernate
	 *            4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param params
	 *            参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @param order
	 *            排序语句，如：order.put("username",Order.ASC)升序
	 *            或者order.put("username",Order.DESC)降序; 例子：Order order = new
	 *            Order(); order.put("username",Order.DESC);
	 * @return 返回List对象，里面包含T
	 */
	public List<T> listJoin(Order order, String join, int firstResult, int maxResults, String whereSql, Object... params);
	/**
	 * 有分页，条件，参数，排序
	 * 
	 * @param firstResult 第一个记录数 大于-1的时候才会分页
	 * @param maxResults 最大记录数 大于-1的时候才会分页
	 * @param whereSql 条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。
	 * @param params 参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @param order
	 *            排序语句，如：order.put("username",Order.ASC)升序
	 *            或者order.put("username",Order.DESC)降序; 例子：Order order = new Order();
	 *            order.put("username",Order.DESC);
	 * @return 返回QueryResult对象，里面包含results（List<T>），count(Long)。分别为总记录，总记录数
	 * @see com.blackants.core.dto.QueryResult
	 */
	public QueryResult<T> query(Order order, int firstResult, int maxResults, String whereSql, Object... params);
	/**
	 * 有分页，条件，参数，排序
	 * 
	 * @param firstResult 第一个记录数 大于-1的时候才会分页
	 * @param maxResults 最大记录数 大于-1的时候才会分页
	 * @param join 联合查询， 如："join o.users u join" 为空或者为""则不会处理。
	 * @param countJoin 统计是需加入的join， 如："join o.users" 为空或者为""则不会处理。
	 * @param whereSql 条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。
	 * @param params 参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @param order
	 *            排序语句，如：order.put("username",Order.ASC)升序
	 *            或者order.put("username",Order.DESC)降序; 例子：Order order = new Order();
	 *            order.put("username",Order.DESC);
	 * @return 返回QueryResult对象，里面包含results（List<T>），count(Long)。分别为总记录，总记录数
	 * @see com.blackants.core.dto.QueryResult
	 */
	public QueryResult<T> queryJoin(Order order, String countJoin, String join, int firstResult, int maxResults, String whereSql, Object... params);
	
	/**
	 * 有分页，排序
	 * @param firstResult 第一个记录数 大于-1的时候才会分页
	 * @param maxResults 最大记录数 大于-1的时候才会分页
	 * @param order
	 *            排序语句，如：order.put("username",Order.ASC)升序
	 *            或者order.put("username",Order.DESC)降序; 例子：Order order = new Order();
	 *            order.put("username",Order.DESC);
	 * @return 返回QueryResult对象，里面包含results(List<T>)，count(Long)。分别为总记录，总记录数
	 * @see com.blackants.core.dto.QueryResult
	 */
	public QueryResult<T> query(Order order,int firstResult, int maxResults);

	/**
	 * 有分页，条件，参数
	 * 
	 * @param firstResult
	 *            第一个记录数 大于-1的时候才会分页
	 * @param maxResults
	 *            最大记录数 大于-1的时候才会分页
	 * @param whereSql
	 *            条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param params
	 *            参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @return 返回QueryResult对象，里面包含results（List<T>），count(Long)。分别为总记录，总记录数
	 * @see com.blackants.core.dto.QueryResult
	 */
	public QueryResult<T> query(int firstResult, int maxResults, String whereSql, Object... params);

	/**
	 * 查询QueryResult（全部），并进行分页
	 * @param firstResult
	 *            第一个记录数 大于-1的时候才会分页
	 * @param maxResults
	 *            最大记录数 大于-1的时候才会分页
	 * @return 返回QueryResult对象，里面包含results（List<T>），count(Long)。分别为总记录，总记录数
	 * @see com.blackants.core.dto.QueryResult
	 */
	public QueryResult<T> query(int firstResult, int maxResults);
	/**
	 * 通过有条件，参数查询QueryResult，并进行排序
	 * @param whereSql
	 *            条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param params
	 *            参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @param order
	 *            排序语句，如：order.put("username",Order.ASC)升序
	 *            或者order.put("username",Order.DESC)降序; 例子：Order order = new Order();
	 *            order.put("username",Order.DESC);
	 * @return 返回QueryResult对象，里面包含results（List<T>），count(Long)。分别为总记录，总记录数
	 * @see com.blackants.core.dto.QueryResult
	 */
	public QueryResult<T> query(Order order, String whereSql, Object... params);

	/**
	 * 通过条件，参数查询QueryResult
	 * @param whereSql
	 *            条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param params
	 *            参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @return 返回QueryResult对象，里面包含results（List<T>），count(Long)。分别为总记录，总记录数
	 * @see com.blackants.core.dto.QueryResult
	 */
	public QueryResult<T> query(String whereSql, Object... params);

	/**
	 * 通过实体id查找实体的属性
	 * 
	 * @param property
	 *            属性名称，如："username"
	 * @param entityId
	 *            实体id
	 * @return 实体属性值
	 */
	public Object queryForProperty(String property, Serializable entityId);

	/**
	 * 通过实体id更新实体的属性
	 * 
	 * @param property
	 *            属性名称，如："username"
	 * @param entityId
	 *            实体id
	 * @param value
	 *            实体的属性值
	 * @throws DaoException 当jpa运行时发生错误时抛出
	 */
	public int updateProperty(String property, Object value, Serializable entityId);

	/**
	 * 通过实体id更新实体的多个属性
	 * 
	 * @param properties
	 *            属性名称，如：new String[]{"username","password"}
	 * @param values
	 *            实体的属性值，如：new Object[]{username,password}
	 * @param entityId
	 *            实体id
	 */
	public int updateProperties(String[] properties, Object[] values, Serializable entityId);

	/**
	 * 判断实体是否存在
	 * 
	 * @param entityId
	 *            实体id
	 * @return 存在返回true，不存在返回false。
	 */
	public boolean isEntityExist(Serializable entityId);
	/**
	 * 通过where语句来判断实体是否存在
	 * @param whereSql 条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。Hibernate 4推荐最新的编写方式,必须按着1,2,3的方式定义
	 *            "o.username=? and o.password=?"的方式还是可行的
	 * @param params 参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @return 存在返回true，不存在返回false。
	 */
	public boolean isEntityExist(String whereSql, Object... params);

	/**
	 * 判断某个实体是否是包含在集合内
	 * @param jpql 如： o.users
	 * @param entityId 实体id
	 * @param obj 实体
	 * @return 包含返回true。不包含返回false。
	 */
	public boolean contain(String jpql, Serializable entityId, Object obj);
	/**
	 * 将一个实体变为脱管状态。
	 * Remove the given entity from the persistence context, causing a managed entity to become detached.
	 * @param entity 实体
	 */
	public void detach(T entity);
	/**
	 * 将多个实体变为脱管状态。
	 * Remove the given entity from the persistence context, causing a managed entity to become detached.
	 * @param entities 实体数组
	 */
	public void detach(T[] entities);
	/**
	 * 同步到数据库
	 */
	public void flush();
	/**
	 * 刷新实体
	 * refresh the state of the instance from the database, using the specified properties, and overwriting changes made to the entity, if any.
	 * @param entity 实体
	 * @throws DaoException 当jpa运行时发生错误时抛出
	 */
	public void refresh(T entity);
	/**
	 * 刷新实体
	 * refresh the state of the instance from the database, using the specified properties, and overwriting changes made to the entity, if any.
	 * @param entity 实体
	 * @param lockModeType
	 * @throws DaoException 当jpa运行时发生错误时抛出
	 */
	public void refresh(T entity, LockModeType lockModeType);
	/**
	 * 将所有的当前上下文的实体变为脱管状态
	 * Clear the persistence context, causing all managed entities to become detached.
	 */
	public void clear();
	
	/**
	 * 获取某个属性的总数
	 * @param field 如：o.name
	 * @param whereSql
	 *            条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。
	 * @param params
	 *            参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @return 存在记录返回大于0的整数，不存在返回0。
	 */
	public Long getSum(String field, String whereSql, Object[] params);
	/**
	 * 获取某个属性的总数
	 * @param field 如：o.name
	 * @param whereSql
	 *            条件， 如："o.username=? and o.password=?" 为空或者为""则不会处理。
	 * @param params
	 *            参数，是一个数组{"xxxx","xxx"},个数必须与whereSql语句中的？的个数一致，为空或者0个元素则不设置值。
	 * @return 存在记录返回大于0的整数，不存在返回0。
	 */
	public <E> E getSum(String field, String whereSql, Class<E> resultType, Object... params);
	/**
	 * 直接执行jpql语句，传入class，作为返回类型
	 * @param jpql 查询语句
	 * @param params 查询参数
	 * @param resultClass 返回类型
	 * @param firstResult
	 *            第一个记录数 大于-1的时候才会分页
	 * @param maxResults
	 *            最大记录数 大于-1的时候才会分页
	 * @return
	 */
	public <E> List<E> list(String jpql, Object[] params, int firstResult, int maxResults, Class<E> resultClass);
	
	/**
	 * 直接执行jpql语句，传入class，作为返回类型
	 * @param jpql 查询语句
	 * @param params 查询参数
	 * @param resultClass 返回类型
	 * @return
	 */
	public <E> List<E> list(String jpql, Object[] params, Class<E> resultClass);
	/**
	 * 通过jpql搜索单一的结果
	 * @param resultType 返回类型class
	 * @param jpql jpql语句
	 * @param params 可变参数
	 * @return 如果存在返回，否则返回null
	 */
	public <E> E queryForSingle(Class<E> resultType, String jpql, Object...params);
	/**
	 * 通过jpql搜索列表的结果
	 * @param resultType
	 * @param jpql
	 * @param params
	 * @return
	 */
	public <E> List<E> queryForList(Class<E> resultType, String jpql, Object...params);
	/**
	 * 通过jpql搜索列表的结果
	 * @param resultType
	 * @param jpql
	 * @param params
	 * @return
	 */
	public <E> List<E> queryForList(Class<E> resultType, String jpql, int firstResult, int maxResults,  Object...params);
	/**
	 * 直接运行sql语句，
	 * @param sql sql语句
	 * @param params 参数
	 * @return
	 */
	public int updateSQL(String sql, Object... params);
	
	/**
	 * 批量保存
	 * @param entities
	 */
	public void batchSave(List<T> entities);
}
