/**
 * $Id: MemcacheUtils.java Jan 9, 2015 2:57:14 PM hdp
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
package com.shopping.core.util;

import java.io.IOException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

/**
 * Memcache工具类

 * @version V1.0
 * @since V1.0
 */
public final class MemcacheUtils {
	
	private MemcacheUtils() {
		throw new RuntimeException("can't init");
	}
	
	/**
	 * Memcached客户端
	 */
	private static MemcachedClient memcachedClient;
	
	static {
		try {
			memcachedClient = new MemcachedClient(AddrUtil.getAddresses("182.254.141.186:11211"));
		} catch (IOException e) {
			throw new ExceptionInInitializerError("Memcache init error");
		}
	}
	
	
	public static void main(String[] args) {
		// Store a value (async) for one hour
		memcachedClient.set("someKey", 3600, "ni");
		// Retrieve a value (synchronously).
		Object myObject=memcachedClient.get("someKey");
		System.out.println(myObject);
	}
	
}
