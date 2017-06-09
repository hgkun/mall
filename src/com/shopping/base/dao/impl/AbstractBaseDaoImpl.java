package com.shopping.base.dao.impl;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.shopping.base.dao.BaseDao;
import com.shopping.core.dto.Order;
import com.shopping.core.dto.QueryResult;
import com.shopping.core.exception.DaoException;
import com.shopping.core.util.EntityUtils;

/**
 * 基类dao抽象实现，所有的dao实现层都要继承该类，并为泛型参数赋值

 * @version V1.0
 * @since V1.0
 * @see com.shopping.base.dao.BaseDao
 * @param <T>泛型参数
 */
public abstract class AbstractBaseDaoImpl<T extends Serializable> implements BaseDao<T> {
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 215110020842063052L;

	// 如果在jboss中使用的话，则下面的这个换成这个
	@PersistenceContext
	protected EntityManager em;//
	// JPAUtils.getEntityManager();
	// protected EntityManager em = JPAUtils.getEntityManager();
	
	@Resource
	protected JdbcTemplate jdbcTemplate;

	@Resource
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	

	/**
	 * Memcached客户端spymemcache实现
	 */
	//@Resource
	protected MemcachedClient spymemcachedClient;
	/**
	 * Memcached客户端xmemcached实现
	 */
	//@Resource
	protected net.rubyeye.xmemcached.MemcachedClient xmemcachedClient;
	/**
	 * MongoDB
	 */
	//@Resource
	protected MongoTemplate mongoTemplate;
	/**
	 * 日志记录
	 */
	protected Logger LOG = LoggerFactory.getLogger(getClass());
	/**
	 * 泛型class
	 */
	@SuppressWarnings("unchecked")
	protected Class<T> entityClass = (Class<T>) EntityUtils.getEntityClass(this.getClass());
	/**
	 * 实体名称
	 */
	protected String entityName = getEntityName();
	/**
	 * 实体id名称
	 */
	protected String entityIdName = getEntityIdName();

	
	@Override
	public T merge(T entity) {
		T result = null;
		try {
			result = getEntityManager().merge(entity);
			if (LOG.isDebugEnabled()) LOG.debug("实体"+entity.getClass() + "保存成功");
		} catch (Exception e) {
			if (LOG.isDebugEnabled()) LOG.debug("实体"+entity.getClass() + "保存失败");
			throw new DaoException(e);
		}
		return result;
	}

	@Override
	public void persist(T entity) {
		try {
			getEntityManager().persist(entity);
			if (LOG.isDebugEnabled()) LOG.debug("实体"+entity.getClass() + "保存成功");
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	@Override
	public void remove(Serializable... entityIds) {
		EntityManager em = getEntityManager();
		try {
			for (Serializable entityId : entityIds) {
				em.remove(em.getReference(entityClass, entityId));
			}
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	@Override
	public T find(Serializable entityId) {
		return getEntityManager().find(entityClass, entityId);
	}
	
	@Override
	public T find(Serializable entityId, LockModeType lockModeType) {
		return getEntityManager().find(entityClass, entityId, lockModeType);
	}
	/* (non-Javadoc)
	 * @see com.tentcoo.core.dao.BaseDao#find(java.lang.String, javax.persistence.LockModeType, java.lang.Object[])
	 */
	@Override
	public T find(String whereSql, LockModeType lockModeType, Object... params) {
		StringBuilder jpqlBuilder = new StringBuilder();
		jpqlBuilder.append("select o from ").append(entityName).append(" o ");
		buildWhereSql(whereSql, jpqlBuilder);
		TypedQuery<T> query = getQueryWithDefaultType(jpqlBuilder.toString());
		if (null != lockModeType) query.setLockMode(lockModeType);
		setParameters(query, params);
		T t = null;
		try {
			List<T> results = query.getResultList();
			if (null != results && !results.isEmpty()) {
				if (results.size() == 1) {
					t = results.get(0);
				} else {
					throw new NonUniqueResultException();
				}
			}
		} catch (NonUniqueResultException e) {
			if (LOG.isErrorEnabled()) {
				StringBuilder sb = new StringBuilder();
				for (Object param : params) {
					sb.append(param).append(" ");
				}
				LOG.error(jpqlBuilder.toString() + ":" + sb.toString() + "：查询的结果有重复的记录", e);
			}
		} catch (IllegalStateException e) {
			if (LOG.isErrorEnabled()) {
				StringBuilder sb = new StringBuilder();
				for (Object param : params) {
					sb.append(param).append(" ");
				}
				LOG.error(jpqlBuilder.toString() + ":" + sb.toString() + "：输入的SQL语句有误", e);
			}
		}
		return t;
	}
	@Override
	public T find(String whereSql, Object... params) {
		return find(whereSql, null, params);
	}

	@Override
	public QueryResult<T> query(int firstResult, int maxResults, String whereSql, Object... params) {
		return query(null, firstResult, maxResults, whereSql, params);
	}

	@Override
	public QueryResult<T> query(int firstResult, int maxResults) {
		return query(null, firstResult, maxResults, null, new Object[0]);
	}

	@Override
	public long getCount(String whereSql, Object... params) {
		StringBuilder jpqlBuilder = new StringBuilder("select count(o) from ");
		jpqlBuilder.append(entityName).append(" o ");
		buildWhereSql(whereSql, jpqlBuilder);
		TypedQuery<Long> query = getQuery(jpqlBuilder.toString(), Long.class);
		setParameters(query, params);
		Long singleResult = query.getSingleResult();
		return singleResult == null ? 0L : (Long) singleResult;
	}
	
	
	@Override
	public long getCount(String field,String whereSql, Object... params) {
		StringBuilder jpqlBuilder = new StringBuilder("select count(distinct o."+field+") from ");
		jpqlBuilder.append(entityName).append(" o ");
		buildWhereSql(whereSql, jpqlBuilder);
		TypedQuery<Long> query = getQuery(jpqlBuilder.toString(), Long.class);
		setParameters(query, params);
		Long singleResult = query.getSingleResult();
		return singleResult == null ? 0L : (Long) singleResult;
	}

	/* (non-Javadoc)
	 * @see com.blackants.core.dao.BaseDao#update(java.lang.String, java.lang.Object[])
	 */
	@Override
	public int update(String jpql, Object... params) {
		Query query = getQuery(jpql);
		setParameters(query, params);
		return query.executeUpdate();
	}

	@Override
	public Object queryForProperty(String property, Serializable entityId) {
		String jpql = "select o." + property + " from " + entityName + " o where o." + entityIdName + "=?1";
		try {
			return getQuery(jpql).setParameter(1, entityId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}



	@Override
	public int updateProperty(String property, Object value, Serializable entityId) {
		return updateProperties(new String[] { property }, new Object[] { value }, entityId);
	}

	@Override
	public int updateProperties(String[] properties, Object[] values, Serializable entityId) {
		String jpql = "update " + entityName + " o set " + buildProperties(properties) + " where o." + entityIdName + "=?";
		Query query = getQuery(jpql);
		setParameters(query, values);
		query.setParameter(values.length + 1, entityId);
		return query.executeUpdate();
	}

	@Override
	public boolean isEntityExist(Serializable entityId) {
		return isEntityExist("o."+entityIdName+"=?1", entityId);
	}

	@Override
	public boolean isEntityExist(String whereSql, Object... params) {
		return getCount(whereSql, params) > 0L;
	}

	@Override
	public boolean contain(String jpql, Serializable entityId, Object obj) {
		Query query = getQuery("select count(o) from " + entityName + " o where o."+entityIdName+"=?1 and ?2 member of " + jpql, Long.class);
		setParameters(query, new Object[] { entityId, obj });
		return ((Long) query.getSingleResult() > 0);
	}

	@Override
	public List<T> list(Order order, String whereSql, Object... params) {
		return list(order, -1, -1, whereSql, params);
	}

	@Override
	public List<T> list(Order order, int firstResult, int maxResults,
			String whereSql, Object... params) {
		return list(order, firstResult, maxResults, whereSql, null, params);
	}

	@Override
	public List<T> list(String whereSql, Object... params) {
		return list(null, -1, -1, whereSql, params);
	}

	@Override
	public List<T> list(int firstResult, int maxResults, String whereSql,
			Object... params) {
		return list(null, firstResult, maxResults, whereSql, params);
	}

	/* (non-Javadoc)
	 * @see com.blackants.core.dao.BaseDao#listJoin(com.blackants.core.dto.Order, java.lang.String, int, int, java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<T> listJoin(Order order, String join, int firstResult,
			int maxResults, String whereSql, Object... params) {
		boolean flag = (null != join && !"".equals(join.trim()));
		StringBuilder jpql = new StringBuilder("select ");
		if (flag) jpql.append(" distinct ");
		jpql.append(" o from ");
		jpql.append(entityName).append(" o ");
		if (flag) jpql.append(" ").append(join).append(" ");
		buildWhereSql(whereSql, jpql);
		buildOrderBy(order, jpql);
		if (LOG.isDebugEnabled()) LOG.debug(jpql.toString());
		TypedQuery<T> query = getQueryWithDefaultType(jpql.toString());
		// 设置参数
		setParameters(query, params);
		if (firstResult != -1 && maxResults != -1)
			query.setFirstResult(firstResult).setMaxResults(maxResults);
		return query.getResultList();
	}

	@Override
	public QueryResult<T> query(Order order, int firstResult, int maxResults,
			String whereSql, Object... params) {
		StringBuilder countBuilder = new StringBuilder();
		countBuilder.append("select count(o) from ").append(entityName).append(" o ");
		StringBuilder followBuilder = new StringBuilder();
		// 拼凑where语句
		buildWhereSql(whereSql, followBuilder);
		
		countBuilder.append(followBuilder);
		TypedQuery<Long> countQuery = getQuery(countBuilder.toString(), Long.class);
		setParameters(countQuery, params);
		long count = (Long) countQuery.getSingleResult();
		// 如果没有记录数为0则不必继续查了，直接返回。
		if (count < 1) return new QueryResult<T>(null, 0);
		
		StringBuilder resultBuilder = new StringBuilder();
		resultBuilder.append("select ");
		resultBuilder.append(" o from ").append(entityName).append(" o ");
		// 拼凑orderBy语句
		buildOrderBy(order, followBuilder);
		resultBuilder.append(followBuilder);
		TypedQuery<T> resultQuery = getQueryWithDefaultType(resultBuilder.toString());
		setParameters(resultQuery, params);
		if (firstResult > -1 && maxResults > -1)
			resultQuery.setFirstResult(firstResult).setMaxResults(maxResults);
		List<T> results = resultQuery.getResultList();
		return new QueryResult<T>(results, count);
	}

	/* (non-Javadoc)
	 * @see com.blackants.core.dao.BaseDao#queryJoin(com.blackants.core.dto.Order, java.lang.String, java.lang.String, int, int, java.lang.String, java.lang.Object[])
	 */
	@Override
	public QueryResult<T> queryJoin(Order order, String countJoin, String join,
			int firstResult, int maxResults, String whereSql, Object... params) {
		StringBuilder countBuilder = new StringBuilder();
		countBuilder.append("select count(distinct o) from ").append(entityName).append(" o ");
		if (null != countJoin && !"".equals(countJoin.trim())) countBuilder.append(" ").append(countJoin).append(" ");
		StringBuilder followBuilder = new StringBuilder();
		// 拼凑where语句
		buildWhereSql(whereSql, followBuilder);
		
		countBuilder.append(followBuilder);
		TypedQuery<Long> countQuery = getQuery(countBuilder.toString(), Long.class);
		setParameters(countQuery, params);
		long count = (Long) countQuery.getSingleResult();
		// 如果没有记录数为0则不必继续查了，直接返回。
		if (count < 1) return new QueryResult<T>(null, 0);
		
		StringBuilder resultBuilder = new StringBuilder();
		boolean flag = (null != join && !"".equals(join.trim()));
		resultBuilder.append("select ");
		if (flag) resultBuilder.append(" distinct ");
		resultBuilder.append(" o from ").append(entityName).append(" o ");
		if (flag) resultBuilder.append(" ").append(join).append(" ");
		// 拼凑orderBy语句
		buildOrderBy(order, followBuilder);
		resultBuilder.append(followBuilder);
		TypedQuery<T> resultQuery = getQueryWithDefaultType(resultBuilder.toString());
		setParameters(resultQuery, params);
		if (firstResult > -1 && maxResults > -1)
			resultQuery.setFirstResult(firstResult).setMaxResults(maxResults);
		List<T> results = resultQuery.getResultList();
		return new QueryResult<T>(results, count);
	}

	@Override
	public QueryResult<T> query(Order order, int firstResult, int maxResults) {
		return query(order, firstResult, maxResults, null);
	}

	@Override
	public QueryResult<T> query(Order order, String whereSql, Object... params) {
		return query(order, -1, -1, whereSql, params);
	}
	@Override
	public QueryResult<T> query(String whereSql, Object... params) {
		return query(null, -1, -1, whereSql, params);
	}
	

	@Override
	public void detach(T entity) {
		getEntityManager().detach(entity);
	}

	@Override
	public void detach(T[] entities) {
		EntityManager em = getEntityManager();
		for (T entity : entities) {
			em.detach(entity);
		}
	}

	@Override
	public void flush() {
		getEntityManager().flush();
	}

	@Override
	public void refresh(T entity) {
		try {
			getEntityManager().refresh(entity);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void refresh(T entity, LockModeType lockModeType) {
		try {
			getEntityManager().refresh(entity, lockModeType);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	@Override
	public void clear() {
		getEntityManager().clear();
	}

	@Override
	public Long getSum(String field, String whereSql, Object[] params) {
		StringBuilder sb = new StringBuilder("");
		buildWhereSql(whereSql, sb);
		String jpql = "select sum("+ field +") from " + entityName + " o " + sb.toString();
		Query query = getQuery(jpql);
		setParameters(query, params);
		Object singleResult = query.getSingleResult();
		return singleResult == null ? 0 : (Long) singleResult;
	}
	

	@Override
	public <E> E getSum(String field, String whereSql, Class<E> resultType, Object... params) {
		StringBuilder sb = new StringBuilder("");
		buildWhereSql(whereSql, sb);
		String jpql = "select sum("+ field +") from " + entityName + " o " + sb.toString();
		TypedQuery<E> query = getQuery(jpql, resultType);
		setParameters(query, params);
		return query.getSingleResult();
	}

	@Override
	public <E> List<E> list(String jpql, Object[] params, int firstResult, int maxResults, Class<E> resultClass) {		TypedQuery<E> query = getQuery(jpql, resultClass);
		setParameters(query, params);		if (firstResult > -1 && maxResults > -1)			query.setFirstResult(firstResult).setMaxResults(maxResults);		return query.getResultList();
	}	@Override	public <E> List<E> list(String jpql, Object[] params, Class<E> resultClass) {		return list(jpql, params, -1, -1, resultClass);	}

	/* (non-Javadoc)
	 * @see com.tentcoo.core.dao.BaseDao#updateSQL(java.lang.String, java.lang.Object[])
	 */
	@Override
	public int updateSQL(String sql, Object... params) {
		Query query = getEntityManager().createNativeQuery(sql);
		setParameters(query, params);
		return query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.tentcoo.core.dao.BaseDao#batchSave(java.util.List)
	 */
	@Override
	public void batchSave(List<T> entities) {
		EntityManager em = getEntityManager();
		int count = 0;
		for (T entity : entities) {
			em.persist(entity);
			count++;
			if (count % 200 == 0) {
				em.flush();
				em.clear();
			}
		}
		em.flush();
		em.clear();
	}


	/* (non-Javadoc)
	 * @see com.tentcoo.core.dao.BaseDao#queryForSingle(java.lang.Class, java.lang.String, java.lang.Object[])
	 */
	@Override
	public <E> E queryForSingle(Class<E> resultType, String jpql, Object... params) {
		TypedQuery<E> query = getQuery(jpql, resultType);
		setParameters(query, params);
		List<E> results = query.getResultList();
		if (null == results || results.isEmpty()) return null;
		if (LOG.isInfoEnabled() && results.size() > 1) {
			LOG.info(jpql+": 有多个结果");
		}
		return results.get(0);
	}

	/* (non-Javadoc)
	 * @see com.tentcoo.core.dao.BaseDao#queryForList(java.lang.Class, java.lang.String, java.lang.Object[])
	 */
	@Override
	public <E> List<E> queryForList(Class<E> resultType, String jpql, Object... params) {
		return queryForList(resultType, jpql, -1, -1, params);
	}

	/* (non-Javadoc)
	 * @see com.tentcoo.core.dao.BaseDao#queryForList(java.lang.Class, java.lang.String, int, int, java.lang.Object[])
	 */
	@Override
	public <E> List<E> queryForList(Class<E> resultType, String jpql, int firstResult, int maxResults, Object... params) {
		TypedQuery<E> query = getQuery(jpql, resultType);
		setParameters(query, params);
		if (firstResult > -1 && maxResults > -1) {
			query.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		return query.getResultList();
	}

	

	/* (non-Javadoc)
	 * @see com.tentcoo.core.dao.BaseDao#list(java.lang.String, javax.persistence.LockModeType, java.lang.Object[])
	 */
	@Override
	public List<T> list(String whereSql, LockModeType lockModeType, Object... params) {
		return list(null, -1, -1, whereSql, lockModeType, params);
	}

	/* (non-Javadoc)
	 * @see com.tentcoo.core.dao.BaseDao#list(com.tentcoo.core.dto.Order, java.lang.String, javax.persistence.LockModeType, java.lang.Object[])
	 */
	@Override
	public List<T> list(Order order, String whereSql, LockModeType lockModeType, Object... params) {
		return list(order, -1, -1, whereSql, lockModeType, params);
	}

	/* (non-Javadoc)
	 * @see com.tentcoo.core.dao.BaseDao#list(int, int, java.lang.String, javax.persistence.LockModeType, java.lang.Object[])
	 */
	@Override
	public List<T> list(int firstResult, int maxResults, String whereSql, LockModeType lockModeType, Object... params) {
		return list(null, firstResult, maxResults, whereSql, lockModeType, params);
	}

	/* (non-Javadoc)
	 * @see com.tentcoo.core.dao.BaseDao#list(com.tentcoo.core.dto.Order, int, int, java.lang.String, javax.persistence.LockModeType, java.lang.Object[])
	 */
	@Override
	public List<T> list(Order order, int firstResult, int maxResults, String whereSql, LockModeType lockModeType,
			Object... params) {
		StringBuilder jpql = new StringBuilder("select ");
		jpql.append(" o from ");
		jpql.append(entityName).append(" o ");
		buildWhereSql(whereSql, jpql);
		buildOrderBy(order, jpql);
		if (LOG.isDebugEnabled()) LOG.debug(jpql.toString());
		TypedQuery<T> query = getQueryWithDefaultType(jpql.toString());
		if (null != lockModeType) query.setLockMode(lockModeType);
		// 设置参数
		setParameters(query, params);
		if (firstResult != -1 && maxResults != -1)
			query.setFirstResult(firstResult).setMaxResults(maxResults);
		return query.getResultList();
	}

	/**
	 * 将属性连接起来
	 * @param properties 属性数组
	 * @return 如果属性数组为空，或者长度为0，则返回""空字符串，否则返回"o.username=?,o.password=?"
	 */
	protected static String buildProperties(String[] properties) {
		if (null == properties || properties.length == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		for (String property : properties) {
			sb.append("o.").append(property).append("=?,");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 拼凑orderBy语句（当order不为null，且包含最少一个元素时才会进行拼凑）
	 * @param order 排序
	 * @param sb 包含jpql语句的StringBuilder
	 */
	protected static void buildOrderBy(Order order, StringBuilder sb) {
		if (null != order && !order.isEmpty()) {
			sb.append(" order by ");
			for (String key : order.keySet()) {
				sb.append("o.").append(key).append(" ").append(order.get(key)).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
	}

	/**
	 * 拼凑where语句（当whereSql不为空的且不为空字符串的时候，才有进行拼凑）
	 * @param whereSql where语句
	 * @param sb 包含jpql语句的StringBuilder
	 */
	protected static void buildWhereSql(String whereSql, StringBuilder sb) {
		if (null != whereSql && !"".equals(whereSql.trim())) {
			sb.append(" where ").append(whereSql.trim()).append(" ");
		}
	}

	/**
	 * 设置参数
	 * @param query 查询query
	 * @param params 参数数组
	 */
	protected static void setParameters(Query query, Object[] params) {
		if (null != query && null != params && params.length > 0) {
			for (int i = 1; i <= params.length; i++) {
				query.setParameter(i, params[i - 1]);
			}
		}
	}

	/**
	 * 获得实体ID名称，主键
	 * <p>
	 * 先看看字段有没有注解Id,如果字段上没有注解，则在getter方法上面找.
	 * </p>
	 * @return
	 * @throws  com.blackants.core.exception.DaoException 没有对id进行注解时，或者在获取Class描述发生错误时
	 */
	private String getEntityIdName() {
		if (null != entityIdName && !"".equals(entityIdName)) return entityIdName;
		Field[] fields = entityClass.getDeclaredFields();
		Id id = null;
		String entityId = null;
		// 先看看字段有没有注解Id
		for (Field field : fields) {
			id = field.getAnnotation(Id.class);
			if (null != id) {
				entityId = field.getName();
				break;
			}
		}
		// 如果字段上没有注解，则在getter方法上面找.
		if (null == id) {
			try {
				PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(entityClass).getPropertyDescriptors();
				Method readMethod = null;
				for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
					readMethod = propertyDescriptor.getReadMethod();
					id = readMethod.getAnnotation(Id.class);
					if (null != id) {
						entityId = propertyDescriptor.getName();
						break;
					}
				}
			} catch (IntrospectionException e) {
				throw new DaoException(e);
			}
		}

		if (entityId == null)
			throw new DaoException("主键没有设置");

		return entityId;
	}

	/**
	 * 获得实体的类名
	 * @return 如果注解上面没有写name属性，则直接使用类名，否则返回注解上面的name值
	 */
	protected String getEntityName() {
		String entityName = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		String name = null;
		if (null != entity)
			name = entity.name();
		return null == name || "".equals(name.trim()) ? entityName : name.trim();
	}

	/**
	 * 获取EntityManager对象
	 * @return 返回当前的线程的EntityManager对象
	 */
	protected EntityManager getEntityManager() {
		return em;
	}
	/**
	 * 默认是实体作为类别，获取query对象
	 * @param jpql jpql语句
	 * @return 返回TypedQuery
	 * @see javax.persistence.TypedQuery
	 */
	protected TypedQuery<T> getQueryWithDefaultType(String jpql) {
		return getEntityManager().createQuery(jpql, entityClass);
	}
	/**
	 * 自动识别类别，获取query对象
	 * @param jpql jpql语句
	 * @return 返回query
	 * @see javax.persistence.Query
	 */
	protected Query getQuery(String jpql) {
		return getEntityManager().createQuery(jpql);
	}
	
	/**
	 * 通过返回类型获取query对象
	 * @param jpql jpql语句
	 * @param resultClass 返回类型
	 * @return  返回TypedQuery
	 * @see javax.persistence.TypedQuery
	 */
	protected <X> TypedQuery<X> getQuery(String jpql, Class<X> resultClass) {
		return getEntityManager().createQuery(jpql, resultClass);
	}

	/**
	 * @return the jdbcTemplate
	 */
	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}




	/**
	 * @return the spymemcachedClient
	 */
	public MemcachedClient getSpymemcachedClient() {
		return spymemcachedClient;
	}

	/**
	 * @return the xmemcachedClient
	 */
	public net.rubyeye.xmemcached.MemcachedClient getXmemcachedClient() {
		return xmemcachedClient;
	}

}
