package com.shopping.core.dto;

import java.io.Serializable;
import java.util.List;
/**
 * 记录工具类

 * @version V1.0
 * @since V1.0
 * @param <T> 泛型参数
 */
public class QueryResult<T> implements Serializable{
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 记录列表
	 */
	private List<T> results;
	/**
	 * 总记录数
	 */
	private long count;
	/**
	 * 构造器
	 * @param results 记录列表
	 * @param count 总记录数
	 */
	public QueryResult(List<T> results, long count) {
		this.results = results;
		this.count = count;
	}
	/**
	 * 获得记录列表
	 * @return 记录列表
	 */
	public List<T> getResults() {
		return results;
	}
	/**
	 * 设置记录列表
	 * @param results 记录列表
	 */
	public void setResults(List<T> results) {
		this.results = results;
	}
	/**
	 * 获得总记录数
	 * @return 0 或者总记录数
	 */
	public long getCount() {
		return count;
	}
	
	/**
	 * @param count 设置总记录数
	 */
	public void setCount(long count) {
		this.count = count;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (count ^ (count >>> 32));
		result = prime * result + ((results == null) ? 0 : results.hashCode());
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryResult<T> other = (QueryResult<T>) obj;
		if (count != other.count)
			return false;
		if (results == null) {
			if (other.results != null)
				return false;
		} else if (!results.equals(other.results))
			return false;
		return true;
	}
}
