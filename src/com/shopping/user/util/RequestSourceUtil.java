package com.shopping.user.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class RequestSourceUtil {
	
	
	public static String getPath2(String scheme,String serverName,String serverPort,String contextPath,String servletPath,Map<String, String[]> params){
		StringBuilder build = new StringBuilder();
		
		if(StringUtils.isAnyBlank(scheme,serverName,serverPort,contextPath,servletPath))return null;
		
		build.append(scheme+"://"+serverName+":"+serverPort+contextPath+servletPath);
			
		if(params.size()>0 || params !=null){
			build.append("?");
			 for (String key : params.keySet()) {  
		            String[] values = params.get(key);  
		            for (int i = 0; i < values.length; i++) {  
		                String value = values[i];  
		                build.append(key + "=" + value + "&");
		            }  
		        }  
			build.deleteCharAt(build.length()-1);
		}
			
		return build.toString();
	}
	public static String getPath(HttpServletRequest request){
		StringBuilder build = new StringBuilder();
		
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		String serverPort = request.getServerPort()+"";
		String contextPath = request.getContextPath();
		Map<String, String[]> params = request.getParameterMap();
		String servletPath = request.getRequestURI();
		build.append(scheme+"://"+serverName+":"+serverPort+contextPath+servletPath);
			
		if(params.size()>0 || params !=null){
			build.append("?");
			 for (String key : params.keySet()) {  
		            String[] values = params.get(key);  
		            for (int i = 0; i < values.length; i++) {  
		                String value = values[i];  
		                build.append(key + "=" + value + "&");
		            }  
		        }  
			build.deleteCharAt(build.length()-1);
		}
			
		return build.toString();
	}
}
