package com.shopping.admin.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.shopping.base.entity.Address;
import com.shopping.base.entity.Order;
import com.shopping.base.enums.OrderStatus;
import com.shopping.base.web.controller.BaseController;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;
import com.shopping.user.dto.OrderDto;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
@Controller
@RequestMapping("/admin/order")
public class OrderController extends BaseController {
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/list" }, method=RequestMethod.GET)
	public String save(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("orderStatus", getOrderStatusList());
		return"/admin/order/list";
	}
	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/doList", method=RequestMethod.POST)
	public String list(HttpServletRequest request,
			@RequestParam(required=false, value="keyword") String keyword,
			@RequestParam(value="orderStatus",required=false)OrderStatus orderStatus,
			@RequestParam(value="pagesize", defaultValue="10") Integer pagesize, 
			@RequestParam(value="offset", defaultValue="0") Integer offset){
		
		QueryResult<Order> qr = orderService.query(keyword, orderStatus,new Pager(offset, pagesize));
		List<Order> orders = qr.getResults();
		List<OrderDto> orderDtos = new ArrayList<>();
		QueryResult<OrderDto> qrd = new QueryResult<>(orderDtos, qr.getCount());
		if (orders!=null && !orders.isEmpty() && qr.getCount()>0) {
			OrderDto orderDto = null;
			for (Order order : orders) {
				orderDto = new OrderDto();
				orderDto.setCreateTime(order.getCreateTime());
				orderDto.setId(order.getId());
				orderDto.setItemName(order.getItem().getItemName());
				orderDto.setItemTitle(order.getItem().getTitle());
				orderDto.setItemType(order.getItem().getItemType().getName());
				orderDto.setRealPrice(order.getRealPrice());
				orderDto.setUser(order.getUser().getName());
				orderDto.setOrderStatus(order.getOrderStatus().getValue());
				orderDto.setDesc(order.getDesc());
				orderDto.setAddress(order.getAddress()==null?new Address():order.getAddress());
				orderDtos.add(orderDto);
				
			}
		}
	
		return JSON.toJSONString(qrd,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		
	}
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/edit" }, method=RequestMethod.GET)
	public String edit(HttpServletRequest request,@RequestParam("id")String id){
		
		request.setAttribute("order", orderService.get(id));
		request.setAttribute("orderStatus", getOrderStatusList());
		return"/admin/order/edit";
	}
	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/doEdit", method=RequestMethod.POST)
	public String doEdit(HttpServletRequest request,
			@RequestParam("id")String id,
			@RequestParam("desc")String desc,
			@RequestParam("realPrice")BigDecimal realPrice,
			@RequestParam("orderStatus")OrderStatus orderStatus){
		Map<String, Object> map = new  HashMap<>();
		
		if (StringUtils.isBlank(id)) {
			map.put("status", "fail");
			map.put("desc", "id不能为空");
			return JSON.toJSONString(map);
		}
		Order order = orderService.get(id);
		if (order==null) {
			map.put("status", "fail");
			map.put("desc", "此订单不存在");
			return JSON.toJSONString(map);
		}
		order.setDesc(desc);
		order.setRealPrice(realPrice);
		order.setOrderStatus(orderStatus);
		orderService.update(order);
		map.put("status", "success");
		map.put("desc", "修改成功");
		return JSON.toJSONString(map);
	}
	
	
	
	
	
	

}
