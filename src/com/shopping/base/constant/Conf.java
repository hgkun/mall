/**
 * $Id: Conf.java Dec 15, 2014 5:36:46 PM hdp
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
package com.shopping.base.constant;

import java.io.IOException;
import java.util.Properties;

/**
 * 基本配置文件

 * @version V1.0
 * @since V1.0
 */
public class Conf {
	static {
		Properties props = new Properties();
		try {
			props.load(Conf.class.getClassLoader().getResourceAsStream("conf.properties"));
		} catch (IOException e) {
			throw new ExceptionInInitializerError("conf.properties is not correct");
		}
		
		QINIU_ACCESS_KEY = props.getProperty("qiniu.access");
		QINIU_SECRET_KEY = props.getProperty("qiniu.secret");
		QINIU_BUCKET_NAME = props.getProperty("qiniu.bucket");
		QINIU_PRIVATE_BUCKET_NAME = props.getProperty("qiniu.private.bucket");
		QINIU_PRIVATE_EXPIRE = Integer.parseInt(props.getProperty("qiniu.private.expire"));
		
		STATIC_HOST = props.getProperty("static.host");
		PRIVATE_HOST = props.getProperty("private.host");
		
		CODE_LENGTH = Integer.parseInt(props.getProperty("code.length"));
		
		SESSION_CODE = props.getProperty("session.code");
		SESSION_ADMIN = props.getProperty("session.admin");
		SESSION_USER = props.getProperty("session.user");
		SESSION_SMS = props.getProperty("session.sms");
		SESSION_FORGET = props.getProperty("session.forget");
		//SESSION_ROLES = props.getProperty("session.roles");
		SESSION_OPENID = props.getProperty("session.openid");
		
		PUSH_APPKEY = props.getProperty("push.appkey");
		
		PUSH_MASTERSECRET = props.getProperty("push.masterSecret");
		
		
	}
	/**
	 * 七牛访问秘钥
	 */
	public static final String QINIU_ACCESS_KEY;
	/**
	 * 七牛秘钥
	 */
	public static final String QINIU_SECRET_KEY;
	/**
	 * 七牛空间名称
	 */
	public static final String QINIU_BUCKET_NAME;
	/**
	 * 七牛私密空间
	 */
	public static final String QINIU_PRIVATE_BUCKET_NAME;
	/**
	 * 私有服务器下载token
	 */
	public static final int QINIU_PRIVATE_EXPIRE;
	/**
	 * 图片服务器域名
	 */
	public static final String STATIC_HOST;
	
	/**
	 * 私有服务器域名
	 */
	public static final String PRIVATE_HOST;
	/**
	 * 验证码长度 
	 */
	public static final int CODE_LENGTH;
	/**
	 * 验证码在session里面的名字
	 */
	public static final String SESSION_CODE;
	/**
	 * 管理员session名称
	 */
	public static final String SESSION_ADMIN;
	/**
	 * 用户session名称
	 */
	public static final String SESSION_USER;
	/**
	 * 用户collector名称
	 */
	public static final String SESSION_COLLECTOR = "collector";
	/**
	 * 用户collector名称
	 */
	public static final String SESSION_ROUTE = "route";
	/**
	 * 手机验证码在session名称
	 */
	public static final String SESSION_SMS;
	/**
	 * 重置密码session名称
	 */
	public static final String SESSION_FORGET;
	/**
	 * 复制权限
	 */
	public static final String SESSION_COPY = "copy" ;
	
	public static final String SESSION_OPENID;
	public static final String SESSION_PERMISSIONS ="permissions";
	public static final String SESSION_ROLES ="roles";
	public static final String SESSION_PANELS ="panels";
	/**
	 * 用户拥有的菜单目录
	 */
	public static final String SESSION_MENUS ="menus";
	
	/**
	 * 客户application
	 */
	public static final String APPLICATION_CUSTOMERS = "customers";
	/**
	 * 任务类型application
	 */
	public static final String APPLICATION_TASK_TYPES = "taskTypes";
	/**
	 * 任务结果application
	 */
	public static final String APPLICATION_TASK_RESULTS = "taskResults";
	/**
	 * 手别
	 */
	public static final String APPLICATION_TASK_TURNTIMES = "turnTimes";
	
	/**
	 * 管理员404页面
	 */
	public static final String PAGE_ADMIN_404 = "/user/notfound";
	
	public static final String IMAGE_SUFFIX = ".jpg";
	/**
	 * 录音后缀名称
	 */
	public static final String VIDEO_SUFFIX = ".wav";
	
	
	
	public static final String PUSH_APPKEY;
	
	public static final String PUSH_MASTERSECRET;
	
	/**
	 * 洗号录音录音
	 */
	public static final String CALL_VIDEO_PATH = "/shopping/www/base/";
	
	
}
