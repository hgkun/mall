/**
 * $Id: SessionRepositoryRequestWrapper.java Feb 25, 2015 6:21:49 PM hdp
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
package com.shopping.core.web.session;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
public class SessionRepositoryRequestWrapper extends HttpServletRequestWrapper {
	/**
	 * 日志
	 */
	private static Logger LOG = LoggerFactory.getLogger(SessionRepositoryRequestWrapper.class);
	/**
	 * HttpServletRequest
	 */
	private HttpServletRequest request;
	/**
	 * HttpServletResponse
	 */
	private HttpServletResponse response;
	/**
	 * cookie名称
	 */
	private String cookieName = "jsessionid";
	/**
	 * 过期时间(秒)
	 */
	private int expiry = 1800;
	/**
	 * redis模板
	 */
	private StringRedisTemplate redisTemplate;
	/**
	 * @param request
	 */
	public SessionRepositoryRequestWrapper(HttpServletRequest request, HttpServletResponse response) {
		super(request);
		this.request = request;
		this.response = response;
		ApplicationContext act = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		redisTemplate = (StringRedisTemplate)act.getBean("redisTemplate");
	}
	
	public HttpSession getSession() {
        return getSession(true);
    }

    public HttpSession getSession(boolean createNew) {
    	Cookie[] cookies = request.getCookies();
    	
    	if (null == cookies || cookies.length == 0) {
    		return createNew ? createNewSession() : null;
    	}
    	
    	for (Cookie cookie : cookies) {
    		if (cookieName.equals(cookie.getName())) {
    			return findSession(cookie.getValue());
    		}
    	}
    	return createNew ? createNewSession() : null;
    }

    private HttpSession findSession(final String sessionid) {
    	HttpSession session = redisTemplate.execute(new RedisCallback<SessionWrapper>() {
			@Override
			public SessionWrapper doInRedis(RedisConnection redisConnection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] key = serializer.serialize(sessionid);
				byte[] bytes = redisConnection.get(key);
				if (null == bytes || bytes.length == 0) return null;
				SessionWrapper session = readObject(bytes);
				session.setLastAccessedTime(System.currentTimeMillis());
				session.setNew(false);
				redisConnection.setNX(key, serialize(session));
				redisConnection.expire(key, expiry);
				return session;
			}
		});
    	return (null == session) ? new SessionWrapper() : session;
	}
    
	/**
	 */
	private HttpSession createNewSession() {
		final String sessionid = UUID.randomUUID().toString();
		Cookie cookie = new Cookie(cookieName, sessionid);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
		final SessionWrapper session = new SessionWrapper();
		long currentTime = System.currentTimeMillis();
		session.setCreationTime(currentTime);
		session.setLastAccessedTime(currentTime);
		session.setMaxInactiveInterval(expiry);
		session.setNew(true);
		session.setId(sessionid);
		
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				redisConnection.setNX(serializer.serialize(sessionid), serialize(session));
				return redisConnection.expire(serializer.serialize(sessionid), expiry);
			}
		});
		
		session.setRequest(request);
		return session;
	}

	/**
	 * 将session序列化
	 * @param session
	 * @return
	 */
	private static byte[] serialize(SessionWrapper session) {
		byte[] serialized = null;

		/*try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(bos));) {
			oos.writeObject(session);
			oos.flush();
			serialized = bos.toByteArray();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}*/
		serialized = JSONObject.toJSONString(session).getBytes();

		return serialized;
	}
	
	private static SessionWrapper readObject(byte[] bytes) {
		/*try (ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(bytes));) {
			return (SessionWrapper)oin.readObject();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}*/
		
		return JSONObject.parseObject(new String(bytes), SessionWrapper.class);
	}
}
