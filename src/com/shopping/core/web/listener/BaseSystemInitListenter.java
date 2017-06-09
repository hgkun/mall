/**
 * $Id: BaseSystemInitListenter.java Dec 15, 2014 2:29:10 PM hdp
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
package com.shopping.core.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统启动时做处理

 * @version V1.0
 * @since V1.0
 */
public class BaseSystemInitListenter implements ServletContextListener {
	/**
	 * 日志
	 */
	private static Logger LOG = LoggerFactory.getLogger(BaseSystemInitListenter.class);
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if (LOG.isDebugEnabled()) LOG.debug("---------------- jedis begin destroy -------------------------");
		
		if (LOG.isDebugEnabled()) LOG.debug("---------------- jedis destroy end -------------------------");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		if (LOG.isDebugEnabled()) LOG.debug("---------------- jedis init begin -------------------------");
//		JedisUtil jedisUtil = JedisUtil.getInstance();
//		jedisUtil.closeJedis(jedisUtil.getJedis());
		if (LOG.isDebugEnabled()) LOG.debug("---------------- jedis init end -------------------------");
	}

}
