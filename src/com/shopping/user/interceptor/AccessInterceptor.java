package com.shopping.user.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shopping.base.constant.Conf;
import com.shopping.base.entity.User;

/**
 * 登录拦截器
 * 

 * @version V1.0
 * @since V1.0
 */
public class AccessInterceptor extends HandlerInterceptorAdapter {
  
	/**
	 * 目标地址
	 */
	private String redirect;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		User user = (User) request.getSession().getAttribute(Conf.SESSION_USER);
        if (user == null) {
			//
        	if ("GET".equalsIgnoreCase(request.getMethod())) {
        		request.getRequestDispatcher(redirect).forward(request, response);
			}else{
				response.sendRedirect(redirect);
			}
			return false;
		}
			return true;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

}
