package com.shopping.core.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 实体工具类

 * @version V1.0
 * @since V1.0
 */
public class EntityUtils {
	/**
	 * 获取实体的泛型类型
	 * @param entity 实体
	 * @param index 索引
	 * @return 泛型类
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getEntityClass(Class<T> entity, int index){
		if (null == entity) return null;
		if (Object.class.equals(entity)) return (Class<T>)Object.class;
		
		ParameterizedType type = (ParameterizedType)entity.getGenericSuperclass();
		if (null == type) return (Class<T>)Object.class;
		Type[] types = type.getActualTypeArguments();
		if (null == types || types.length == 0) return (Class<T>)Object.class;
		if (index > -1) {
			return (Class<T>)(index < types.length ? types[index] : types[types.length - 1]);
		} else {
			return (Class<T>)types[0];
		}
	}
	/**
	 * 获取实体的泛型类型
	 * @param entity
	 * @return 泛型类
	 */
	public static <T> Class<T> getEntityClass(Class<T> entity){
		return (Class<T>)getEntityClass(entity, 0);
	}
}
