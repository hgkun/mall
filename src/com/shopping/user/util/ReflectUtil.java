package com.shopping.user.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ReflectUtil {
	
	/**
	 * 反射获得有值的字段
	 * @param clazz
	 * @return 返回所有有值字段的集合list ，集合中存放map<字段名 ,值>
	 * @throws Exception
	 */
	public static List getField(Object obj) throws Exception{
		Class clazz = (Class)obj.getClass();
		List<Object> list = new ArrayList<Object>();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field:fields){
			Map<String,Object> map = new HashMap<String,Object>();
			String name = toUppercaseName(field.getName());
			Method m = clazz.getMethod("get"+name);
			String value = (String) m.invoke(clazz);
			if(!StringUtils.isBlank(value)){
				map.put(name, value);
				list.add(map);
			}
		}
		
		return list;		
	}
	
	/**
	 * 将字段首字母转换为大写
	 * @param name
	 * @return
	 */
	public static String toUppercaseName(String name){
		
       return name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
          
	}
	

}
