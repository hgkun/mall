/**
 * $Id: SmsUtils.java Jul 31, 2015 11:35:32 AM hdp
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

import com.bcloud.msg.http.HttpSender;

/**
 * 短信发送工具

 * @version V1.0
 * @since V1.0
 */
public class SmsUtils {
	/**
	 * 台地址
	 */
	private static final String url = "http://222.73.117.158/msg/";
	/**
	 * 账号
	 */
	private static final String account = "vipmmhts";
	/**
	 * 密码
	 */
	private static final String password = "Tch123456";
	
	public static boolean send(String phone, String content) {
		boolean needstatus = true;//是否需要状态报告，需要true，不需要false
		String product = null;//产品ID
		String extno = null;//扩展码
		 
		try {
			String returnString = HttpSender.batchSend(url, account, password, phone, content, needstatus, product, extno);
			if (returnString.split("\n")[0].split(",")[1].equals("0")) return true;
		} catch (Exception e) {
			//TODO 处理异常
			e.printStackTrace();
		}
		return false;
	}
}
