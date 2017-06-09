/**
 * $Id: DateUtils.java 2015年12月19日 上午11:16:06 liaoyongchao
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/** 
* @ClassName: DateUtils 
* @Description: TODO Date related Utils

* 
*/
public class DateUtils {
	
	public static void main(String args[]){
   	// System.out.println(getDayBeginTime());
		
		Date beginDate = StrToDate("2016-04-19","yyyy-MM-dd");
		Date endDate = StrToDate("2016-03-09","yyyy-MM-dd");
		
		boolean is = isOneMonth(beginDate, endDate);
		System.out.println(is);
		
   }
	
	/**
	 * 是否在同一个月内
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static boolean isOneMonth(Date beginDate,Date endDate){
		
		Calendar cal1 = Calendar.getInstance();		
		cal1.setTime(beginDate);		
		Calendar cal2 = Calendar.getInstance();		
		cal2.setTime(endDate);
		
		int year1 = cal1.get(Calendar.YEAR);
		int month1= cal1.get(Calendar.MONTH);
		int day1 = cal1.get(Calendar.DAY_OF_MONTH);
		
		int year2 = cal2.get(Calendar.YEAR);
		int month2= cal2.get(Calendar.MONTH);
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		
		if(year1==year2){
			if(month1==month2){
				if(day2>=day1){
					return true;
				}
			}
		}
		
		
		
		return false;
	}

	/**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     */
    public static long getDaySub(String beginDateStr,String endDateStr, String rule)
    {
    	int days=0;
    	try {
			SimpleDateFormat sdf=new SimpleDateFormat(rule);
			Calendar cal1=Calendar.getInstance();
			cal1.setTime(sdf.parse(beginDateStr));
			Calendar cal2=Calendar.getInstance();
			cal2.setTime(sdf.parse(endDateStr));
			long l=cal2.getTimeInMillis()-cal1.getTimeInMillis();
			days = new Long(l/(1000*60*60*24)).intValue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return days;
    }
    
    /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     */
    public static long getDaySub(Date beginDate,Date endDate)
    {
    	int days=0;
			
			Calendar cal1=Calendar.getInstance();
			cal1.setTime(beginDate);
			Calendar cal2=Calendar.getInstance();
			cal2.setTime(endDate);
			long l=cal2.getTimeInMillis()-cal1.getTimeInMillis();
			days = new Long(l/(1000*60*60*24)).intValue();
		
    	return days;
    }
    
    /**
    * 日期转换成字符串
    * @param date 
    * @return str
    */
    public static String DateToStr(Date date, String formatStr) {
      
       String str = null;
	if (date != null) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		str = format.format(date);
	}
       return str;
    }
    
    
    
    /**
    * 字符串转换成日期
    * @param str
    * @return date
    */
    public static Date StrToDate(String str, String formatStr) {
      
       SimpleDateFormat format = new SimpleDateFormat(formatStr);
       Date date = null;
       try {
        date = format.parse(str);
       } catch (ParseException e) {
        e.printStackTrace();
       }
       return date;
    }
    
    /**
     * 获取当天0点的时间
     * @return
     */
    public static Date getDayBeginTime() {
    	  Date date = new Date();
    	  GregorianCalendar gc = new GregorianCalendar();
    	  gc.setTime(date);
    	  Date date2 = new Date(date.getTime() - gc.get(gc.HOUR_OF_DAY) * 60 * 60
    	    * 1000 - gc.get(gc.MINUTE) * 60 * 1000 - gc.get(gc.SECOND)
    	    * 1000);
    	  return new Date(date2.getTime());
    	 }
    
	/**
	 * 得到几天前的时间 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {  
		Calendar now = Calendar.getInstance();  
		now.setTime(d);  
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);  
		return now.getTime();  
	} 
	 
	/**
	 * 数分钟前的时间（Date）
	 * @param d
	 * @param minutes
	 * @return
	 */
	public static Date getMinutesAgoTime(Date d, int minutes) {
        Calendar now = Calendar. getInstance ();
        now.setTime(d);   
        now.set(Calendar.MINUTE , now.get(Calendar.MINUTE) + minutes) ; 
         return  now.getTime();
	}
}
