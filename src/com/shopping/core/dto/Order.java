package com.shopping.core.dto;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 排序Bean,继承自LinkedHashMap

 * @version V1.0
 * @since V1.0
 * @see java.util.LinkedHashMap
 */
public class Order extends LinkedHashMap<String, String> {
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 5303704014009245091L;
	/**
	 * 降序
	 */
	public static final String DESC = "DESC";
	/**
	 * 升序
	 */
	public static final String ASC = "ASC";
	
	public Order() {
		super();
	}
	public Order(LinkedHashMap<String, String> order) {
		super();
		if (null != order && !order.isEmpty()) {
			this.putAll(order);
		}
	}
	
	/**
	 * 是否允许的排序
	 * @param allowFields
	 * @param field
	 * @return
	 */
	public static boolean allowSortField(List<String> allowSortFields, String sortfield) {
		if (StringUtils.isBlank(sortfield) || allowSortFields == null || allowSortFields.isEmpty()) return false;
		return allowSortFields.contains(sortfield.trim());
	}
	/**
	 * 创建排序字段
	 * @param allowSortFields 允许的字段
	 * @param sortName 排序名称
	 * @param sortDirection up为升序，其他为降序
	 * @return
	 */
	public static Order buildOrder(List<String> allowSortFields, String sortName, String sortDirection) {
		Order order = null;
		if (!StringUtils.isAnyBlank(sortName,sortDirection) && allowSortField(allowSortFields, sortName)) {
			order = new Order();
			order.put(sortName, "up".equals(sortDirection)?Order.ASC:Order.DESC);
		}
		return order;
	}
}
