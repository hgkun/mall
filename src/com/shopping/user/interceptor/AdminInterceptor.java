package com.shopping.user.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shopping.base.constant.Conf;
import com.shopping.base.entity.Admin;
import com.shopping.base.entity.User;

/**
 * 登录拦截器
 * 

 * @version V1.0
 * @since V1.0
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 目标地址
	 */

	private String redirect;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Admin admin = (Admin) request.getSession().getAttribute(Conf.SESSION_ADMIN);
		if (admin == null) {
			request.getRequestDispatcher(redirect).forward(request, response);
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
