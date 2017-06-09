package com.shopping.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpSend {
	private static Logger LOG = LoggerFactory.getLogger(HttpSend.class);
	public static String httpRequestStr(String requestUrl,
			String requestMethod, String outputStr) {
		if (requestUrl.indexOf("https") == -1) {
			if ("GET".equalsIgnoreCase(requestMethod)) {
				return send(requestUrl);
			} else  {
				return post(requestUrl, outputStr);
			}
		}
		
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	public static String post(String requestUrl, String outputStr) {
		CloseableHttpClient httpclient = HttpClients.createSystem();
		HttpPost post = new HttpPost(requestUrl);
		CloseableHttpResponse response = null;
		try {
			try {
				post.setEntity(new StringEntity(outputStr, Charset.forName("UTF-8")));
			    response = httpclient.execute(post);
			    if (LOG.isInfoEnabled()) LOG.info(outputStr);
			    if (null != response) {
			    	HttpEntity entity = response.getEntity();
				    if (entity != null) {
				    	return readStream(entity.getContent());
				    }
			    }
			} finally {
			    if(null != response) response.close();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static String doPost(String requestUrl, String outputStr) {
		CloseableHttpClient httpclient = HttpClients.createSystem();
		HttpPost post = new HttpPost(requestUrl);
		CloseableHttpResponse response = null;
		try {
			try {
				// 设置类型    
				StringEntity reqEntity = new StringEntity(outputStr, Charset.forName("UTF-8"));
	             reqEntity.setContentType("application/x-www-form-urlencoded"); 
				post.setEntity(reqEntity);
			    response = httpclient.execute(post);
			    if (LOG.isInfoEnabled()) LOG.info(outputStr);
			    if (null != response) {
			    	HttpEntity entity = response.getEntity();
				    if (entity != null) {
				    	return readStream(entity.getContent());
				    }
			    }
			} finally {
			    if(null != response) response.close();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static String doPost(String requestUrl, Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createSystem();
		HttpPost post = new HttpPost(requestUrl);
		CloseableHttpResponse response = null;
		
		 // 创建参数队列    
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		
		for(String key:params.keySet()){
			 String values = params.get(key); 
			 formparams.add(new BasicNameValuePair(key, values));    
		}
		
       
        UrlEncodedFormEntity uefEntity;  
		
		try {
			try {
				// 设置类型    
				//StringEntity reqEntity = new StringEntity(outputStr, Charset.forName("UTF-8"));
	            // reqEntity.setContentType("application/x-www-form-urlencoded"); 
				uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
				post.setEntity(uefEntity);
			    response = httpclient.execute(post);
			    if (null != response) {
			    	HttpEntity entity = response.getEntity();
				    if (entity != null) {
				    	return readStream(entity.getContent());
				    }
			    }
			} finally {
			    if(null != response) response.close();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	private static String send(String requestUrl) {
		if (requestUrl.indexOf("https") != -1) return HttpSend.httpRequestStr(requestUrl, "GET", null);
		CloseableHttpClient httpclient = HttpClients.createSystem();
		HttpGet httpget = new HttpGet(requestUrl);
		CloseableHttpResponse response = null;
		try {
			try {
			    response = httpclient.execute(httpget);
			    if (null != response) {
			    	HttpEntity entity = response.getEntity();
				    if (entity != null) {
				    	return readStream(entity.getContent());
				    }
			    }
			} finally {
			    if(null != response) response.close();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	private static String readStream(InputStream instream) throws IOException {
		byte[] bytes = IOUtils.toByteArray(instream);
		return new String(bytes, Charset.forName("UTF-8"));
	}
}
