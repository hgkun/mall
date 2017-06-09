/**
 * $Id: CommonUtils.java Feb 10, 2015 2:21:35 PM hdp
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
package com.shopping.base.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * 公共工具类

 * @version V1.0
 * @since V1.0
 */
public final class CommonUtils {
	/**
	 * 通用图片路径
	 */
	public static final String IMAGE_PATH = "static/";
	/**
	 * 通用录音路径
	 */
	public static final String VIDEO_PATH = "video/";
	/**
	 * 私有文件路径
	 */
	public static final String PRIVATE_PATH = "private/";
	/**
	 * 文章图片路径
	 */
	public static final String ARTICLE_PATH = "article/";
	/**
	 * 洗号录音路径
	 */
	public static final String CALL_PATH = "call/";
	/**
	 * 当前服务器ip地址
	 */
	private static final int IP;
	/**
	 * 
	 */
	public static SerializeConfig JSON_DATETIME_MAPPING;
	
	static {
		int ipadd;
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
		
		
		JSON_DATETIME_MAPPING = new SerializeConfig();
		JSON_DATETIME_MAPPING.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
		JSON_DATETIME_MAPPING.put(java.sql.Time.class, new SimpleDateFormatSerializer("HH:mm:ss")); 
		JSON_DATETIME_MAPPING.put(java.sql.Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss")); 
	}
	/**
	 * 计数器
	 */
	private static short counter = (short) 0;
	/**
	 * JVM
	 */
	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);
	
	
	
	/**
	 * 获取hash路径
	 * @param uuid uuid
	 * @return
	 */
	public static String getHashPath(String uuid) {
		int hashCode = uuid.hashCode();
		StringBuilder sb = new StringBuilder(IMAGE_PATH);
		String tmp = format(hashCode);
		for (int i = 0; i < 4; i++) {
			sb.append(tmp.substring(i * 2, (i + 1) * 2)).append("/");
		}
		return sb.toString();
	}
	/**
	 * 获取洗号hash路径
	 * @param uuid uuid
	 * @return
	 */
	public static String getCallHashPath(String uuid) {
		int hashCode = uuid.hashCode();
		StringBuilder sb = new StringBuilder(CALL_PATH);
		String tmp = format(hashCode);
		for (int i = 0; i < 4; i++) {
			sb.append(tmp.substring(i * 2, (i + 1) * 2)).append("/");
		}
		return sb.toString();
	}
	/**
	 * 获取hash路径
	 * @param uuid uuid
	 * @return
	 */
	public static String getVideoHashPath(String uuid) {
		int hashCode = uuid.hashCode();
		StringBuilder sb = new StringBuilder(VIDEO_PATH);
		String tmp = format(hashCode);
		for (int i = 0; i < 4; i++) {
			sb.append(tmp.substring(i * 2, (i + 1) * 2)).append("/");
		}
		return sb.toString();
	}
	/**
	 * 获取私密hash路径
	 * @param uuid uuid
	 * @return
	 */
	public static String getPrivateHashPath(String uuid) {
		int hashCode = uuid.hashCode();
		StringBuilder sb = new StringBuilder(PRIVATE_PATH);
		String tmp = format(hashCode);
		for (int i = 0; i < 4; i++) {
			sb.append(tmp.substring(i * 2, (i + 1) * 2)).append("/");
		}
		return sb.toString();
	}
	/**
	 * 获取私密hash路径
	 * @param uuid uuid
	 * @return
	 */
	public static String getEmotionHashPath(String uuid) {
		int hashCode = uuid.hashCode();
		StringBuilder sb = new StringBuilder("emotion/");
		String tmp = format(hashCode);
		for (int i = 0; i < 4; i++) {
			sb.append(tmp.substring(i * 2, (i + 1) * 2)).append("/");
		}
		return sb.toString();
	}
	/**
	 * 获取随机uuid，不包含“-”符号的
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("\\-", "");
	}
	
	/**
	 * 产生一个32位的UUID
	 * @return
	 */
	public static String generate() {
		return new StringBuilder(32).append(format(getIP())).append(
				format(getJVM())).append(format(getHiTime())).append(
				format(getLoTime())).append(format(getCount())).toString();
	}

	/**
	 * 将整型数据格式化为8位的16进制字符串
	 * @param intval 整型
	 * @return 8位的16进制字符串
	 */
	private final static String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuilder buf = new StringBuilder("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}
	/**
	 * 将整型数据格式化为4位的16进制字符串
	 * @param shortval 短整型
	 * @return 4位的16进制字符串
	 */
	private final static String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuilder buf = new StringBuilder("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	/**
	 * 获取JVM
	 * @return
	 */
	private final static int getJVM() {
		return JVM;
	}
	
	/**
	 * 获取计数器
	 * @return
	 */
	private final static short getCount() {
		synchronized (CommonUtils.class) {
			if (counter < 0)
				counter = 0;
			return counter++;
		}
	}
	
	/**
	 * 获取当前服务器ip地址
	 * @return
	 */
	private final static int getIP() {
		return IP;
	}

	/**
	 * Unique down to millisecond
	 */
	private final static short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	private final static int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	/**
	 * 字节转为整型数据
	 * @param bytes 字节数组
	 * @return
	 */
	private final static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i< 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}
	/**
	 * 两个BigDecimal为null的时候数据相加
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static BigDecimal addBigDecimalValue(BigDecimal value1, BigDecimal value2){
		if(value1 == null) value1 = new BigDecimal(0);
		if(value2 == null) value2 = new BigDecimal(0);
		return value1.add(value2);
	}
	
	/**
	 * 两个BigDecimal为null的时候数据相减
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static BigDecimal subtractBigDecimalValue(BigDecimal value1, BigDecimal value2){
		if(value1 == null) value1 = new BigDecimal(0);
		if(value2 == null) value2 = new BigDecimal(0);
		return value1.subtract(value2);
	}
	
	/**
	 * 两个BigDecimal为null的时候数据相除
	 * @param value1
	 * @param value2
	 * @return
	 * @throws Exception 
	 */
	public static BigDecimal divideBigDecimalValue(BigDecimal value1, BigDecimal value2) throws Exception{
		if(value1 == null) value1 = new BigDecimal(0);
		if(value2 == null) value2 = new BigDecimal(0);
		
		if(value2.compareTo(BigDecimal.ZERO)!= 0){
			return value1.setScale(2, RoundingMode.HALF_UP).divide(value2, 2);
		}else{
			throw new Exception("除数为0");
		}
	}
	
	/**
	 * 两个BigDecimal为null的时候数据相除
	 * @param value1
	 * @param value2
	 * @return
	 * @throws Exception 
	 */
	public static BigDecimal divideBigDecimalValue1(BigDecimal value1, BigDecimal value2){
		if(value1 == null) value1 = new BigDecimal(0);
		if(value2 == null) value2 = new BigDecimal(0);
		
		if(value2.compareTo(BigDecimal.ZERO)!= 0){
			return value1.setScale(2, RoundingMode.HALF_UP).divide(value2, 2);
		}
		return new BigDecimal(0);
	}
	/**
	 * 两个BigDecimal为null的时候数据相除
	 * @param value1
	 * @param value2
	 * @return
	 * @throws Exception 
	 */
	public static BigDecimal divideBigDecimalValue2(BigDecimal value1, BigDecimal value2,Integer scale){
		if(value1 == null) value1 = new BigDecimal(0);
		if(value2 == null) value2 = new BigDecimal(0);
		
		if(value2.compareTo(BigDecimal.ZERO)!= 0){
			return value1.setScale(scale, RoundingMode.HALF_UP).divide(value2, scale);
		}
		return new BigDecimal(0);
	}
	
	/**
	 * 两个BigDecimal为null的时候数据相乘
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static BigDecimal multiplyBigDecimalValue(BigDecimal value1, BigDecimal value2){
		if(value1 == null) value1 = new BigDecimal(0);
		if(value2 == null) value2 = new BigDecimal(0);
		
		return value1.multiply(value2);
	}
	
	
	
	public static String Split(String filed,String match,int i){
				if(filed != null)
					return filed.split(match)[i];
				return "";
		
	}
	
    /**
     * 判断List是否为空,非空返回true,空则返回false
     * 
     * @param list
     * @return boolean
     */
    public static boolean isListNotNull(List<?> list) {
        return null != list && list.size() > 0;
    }
    /**
     * 判断是否有数字
     * @param number
     * @return
     */
	public static boolean isNumber(String number){
		int index = number.indexOf(".");
		if(index<0){
			return StringUtils.isNumeric(number);
		}else{
			String num1 = number.substring(0,index);
			String num2 = number.substring(index+1);			
			return StringUtils.isNumeric(num1) && StringUtils.isNumeric(num2);
		}
	}
	
	public static Object isNull(Object obj){
	
		return obj==null?"":obj;
	}
	
	public static boolean isExist(String[] s){
		
		boolean flag = false;
		
		Set<String> set = new HashSet<String>();
		for(String str : s){
			set.add(str);
		}
		
		if(set.size() < s.length){
			
			flag = true;
		}	
		return flag;
	}
	
	public static String changToString(String number){
		
		if(number.lastIndexOf(".")==-1){
			return number+".00";
		}
		
		return number.substring(0,number.lastIndexOf(".")+3);
	}
	
	/**
	 * 转换成2位小数
	 * @param number
	 * @return
	 */
	public static BigDecimal chang2bitNumber(BigDecimal number){
		
		if(number.compareTo(BigDecimal.ZERO)==0){
			return new BigDecimal(changToString("0"));
		}		
		String numberString = String.valueOf(number);		
		numberString = changToString(numberString);
		number = new BigDecimal(numberString);
		
		return number;
	}
	
	public static void main(String[] args) {
		System.out.println(chang2bitNumber(new BigDecimal("252")));
	}
}
