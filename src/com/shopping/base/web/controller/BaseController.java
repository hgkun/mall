package com.shopping.base.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.shopping.base.constant.Conf;
import com.shopping.base.entity.User;
import com.shopping.base.enums.OrderStatus;
import com.shopping.base.service.AddressService;
import com.shopping.base.service.AdminService;
import com.shopping.base.service.DiscountService;
import com.shopping.base.service.HeaderImgService;
import com.shopping.base.service.ItemService;
import com.shopping.base.service.ItemTypeService;
import com.shopping.base.service.MessageService;
import com.shopping.base.service.OrderService;
import com.shopping.base.service.ReviewService;
import com.shopping.base.service.ShopCartService;
import com.shopping.base.service.UserService;
import com.shopping.base.service.impl.AdministrationServiceImpl;
//import com.shopping.base.service.ScheduleJobService;

public abstract class BaseController {
	
	

	public static final String PUBLIC_404 = "redirect:/notfound.do";

	protected Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Resource
	protected HeaderImgService headerImgService;
	@Resource
	protected UserService userService;
	@Resource
	protected DiscountService discountService;
	@Resource
	protected AddressService addressService ;
	@Resource
	protected ItemService itemService;
	@Resource
	protected ItemTypeService itemTypeService;
	@Resource
	protected OrderService orderService;
	@Resource
	protected ReviewService reviewService;
	@Resource
	protected AdminService adminService;
	@Resource
	protected MessageService messageService;
	@Resource
	protected ShopCartService shopCartService;
	@Resource
	protected AdministrationServiceImpl administrationService;
	


	
	static{
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	}

	

	
	

	
	
	public User getSessionUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null)
			return null;
		return (User) session.getAttribute(Conf.SESSION_USER);
	}

	protected String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getDecode( String str) {
		if (StringUtils.isNotBlank(str)) 
			return StringEscapeUtils.unescapeHtml4(str);
		return "";
	}
	
	
	protected List<Map<String, String>> getOrderStatusList(){
		
		List<Map<String, String>> list = new ArrayList<>();
		OrderStatus[] values = OrderStatus.values();
		for (int i = 0; i < values.length; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("value", values[i].getValue());
			map.put("name", values[i].name());
			list.add(map);
		}
		return list;
	}
}