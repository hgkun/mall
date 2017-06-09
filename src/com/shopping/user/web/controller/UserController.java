package com.shopping.user.web.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.shopping.base.constant.Conf;
import com.shopping.base.entity.Address;
import com.shopping.base.entity.Administration;
import com.shopping.base.entity.Item;
import com.shopping.base.entity.Order;
import com.shopping.base.entity.Review;
import com.shopping.base.entity.ShopCart;
import com.shopping.base.entity.User;
import com.shopping.base.enums.CartStatus;
import com.shopping.base.enums.Comment;
import com.shopping.base.enums.Gender;
import com.shopping.base.enums.OrderStatus;
import com.shopping.base.enums.ValidStatus;
import com.shopping.base.web.controller.BaseController;
import com.shopping.core.dto.Pager;

@Controller
@RequestMapping("/user/user")
public class UserController extends BaseController {
	

	
	
	/**
	 * 个人信息修改
	 * @param request
	 * @return
	 */
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/perfect" }, method=RequestMethod.GET)
	public String perfect(HttpServletRequest request,HttpServletResponse response){
		
		return "/personalInformation";
	}
	/**
	 * 个人信息修改
	 * @param request
	 * @return
	 */
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/address" }, method=RequestMethod.GET)
	public String address(HttpServletRequest request,HttpServletResponse response){
		User user = getSessionUser(request);
		com.shopping.core.dto.Order order = new com.shopping.core.dto.Order();
		order.put("selected", "desc");
		request.setAttribute("addresss", addressService.list(new Pager(0, 3), order, user));
		request.setAttribute("provinces", administrationService.getProvince());
		return "/address";
	}
	/**
	 * @return
	 */
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/success"/*这个名字随意*/ }, method=RequestMethod.GET)
	public String goSuccess(HttpServletRequest request,@RequestParam("id")String[] id){
		BigDecimal sum = new BigDecimal(0);
		Address address = null;
		if (id!=null && id.length>0) {
			for (int i = 0; i < id.length; i++) {
			 	Order order = orderService.get(id[i]);
			 	address = order.getAddress();
			 	if (order==null) {
			 		continue;
				}
			 	sum=sum.add(order.getRealPrice());
			}
		}
		
		request.setAttribute("address",address);
		request.setAttribute("sum",sum);
		return"/success";
	}
	
	/**
	 * 修改编辑
	 * 用户
	 */
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/perfectUser", method=RequestMethod.POST)
	public String perfectUser(HttpServletRequest request,
			@RequestParam(required=false, value="name")String name,
			@RequestParam("nickName")String nickName,
			@DateTimeFormat(pattern = "yyyy/MM/dd") @RequestParam(value="brithDay")Date brithDay,
			@RequestParam(required=false, value="gender")Gender gender,
			@RequestParam(required=false, value="phone")String phone,
			@RequestParam(required=false, value="email")String email){
		Map<String, Object> map = new HashMap<>();
		User user = (User)request.getSession().getAttribute(Conf.SESSION_USER);
		if (user==null) {
			map.put("status", "fail");
			map.put("desc", "请登录再修改");
			return JSON.toJSONString(map);
		}
		user.setEmail(email.trim());
		user.setNickName(nickName.trim());
		
		user.setGender(gender);
		user.setPhone(phone.trim());
		user.setName(name.trim());
		user.setBrithDay(brithDay);
		userService.update(user);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		request.getSession().setAttribute(Conf.SESSION_USER, user);
		request.getSession().setAttribute("brithday", sdf.format(brithDay));
		map.put("status", "success");
		map.put("desc", "修改成功！");
		return JSON.toJSONString(map);
		
	}
	
	/**
	 * 创建支付订单并跳转到支付页面
	 * @param request
	 * @param num
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = { "/doOrder" }, method=RequestMethod.POST)
	public String doOrder(HttpServletRequest request,
			@RequestParam(value="num",defaultValue="1")Integer num,
			@RequestParam("id")String id){

		Map<String , Object> map = new HashMap<>();
		User user = getSessionUser(request);
		
		try {
			boolean flag = orderService.CreateOrder(user, OrderStatus.OBLIGATION, id, num);
			if (flag) {
				map.put("status", "success");
				map.put("desc", "创建订单成功！");
				map.put("id", orderService.getNewId(user, id));
				
			}else{
				map.put("status", "fail");
				map.put("desc", "创建订单失败！");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return JSON.toJSONString(map);
		
	}
	/**
	 * 
	 * @param request
	 * @param oid
	 * @return
	 */
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/pay" }, method=RequestMethod.GET)
	public String pay(HttpServletRequest request ,
			@RequestParam(value="oid",required=false)String oid){
		
		if (StringUtils.isBlank(oid)) {
			List<Order> list =orderService.list(getSessionUser(request));
			
			BigDecimal sum = new BigDecimal(0);
			if (list!=null && !list.isEmpty()) {
				
				for (Order order : list) {
					sum = sum.add(order.getRealPrice());
				}
			}
			request.setAttribute("sum", sum);
			request.setAttribute("items", list);
		}else{
			Order order = orderService.get(oid);
			request.setAttribute("item", order);
		}
		
		User user = getSessionUser(request);
		com.shopping.core.dto.Order order = new com.shopping.core.dto.Order();
		order.put("selected", "desc");
		request.setAttribute("addresss", addressService.list(new Pager(0, 3), order, user));
		
		return"/pay";
	}
	
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = { "/doPay" }, method=RequestMethod.POST)
	public String doPay(HttpServletRequest request ,HttpServletResponse response,
			@RequestParam(value="id")String id,
			@RequestParam(value="aid")String aid,
			@RequestParam(value="desc",required = false)String desc,
			@RequestParam("num")Integer num){
		Map<String, Object> map = new HashMap<>();
		Order order = orderService.get(id);
		if (order==null) {
			map.put("status", "fail");
			map.put("desc", "该订单不存在，无法支付");
			return JSON.toJSONString(map);
		}
		Address address = addressService.get(aid);
		
		order.setDesc(desc);
		order.setAddress(address);
		order.setRealPrice(num==order.getNum()?order.getRealPrice():order.getItem().getPrice().multiply(new BigDecimal(num)));
		order.setNum(num);
		order.setOrderStatus(OrderStatus.WITETOSHIPMENTS);
		orderService.update(order);
		map.put("status", "success");
		map.put("ids", "id="+order.getId());
		map.put("desc", "支付成功！");
		return JSON.toJSONString(map);
	}
	
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = { "/doManyPay" }, method=RequestMethod.POST)
	public String doManyPay(HttpServletRequest request ,HttpServletResponse response,
			@RequestParam("ids")String[] ids,
			@RequestParam(value="aid")String aid,
			@RequestParam(value="desc",required=false)String desc){
		Map<String, Object> map = new HashMap<>();
		if (ids==null||ids.length==0) {
			map.put("status", "fail");
			map.put("desc", "请选择订单进行支付");
			return JSON.toJSONString(map);
		}
		Address address = addressService.get(aid);
		StringBuffer params = new StringBuffer();
		for (String id : ids) {
			Order order = orderService.get(id);
			params.append("id="+id+"&");
			if (order==null) {
				map.put("status", "fail");
				map.put("desc", "该订单不存在，无法支付");
				return JSON.toJSONString(map);
			}
			order.setDesc(desc);
			order.setAddress(address);
			order.setRealPrice(order.getItem().getPrice().multiply(new BigDecimal(order.getNum())));
			order.setOrderStatus(OrderStatus.WITETOSHIPMENTS);
			orderService.update(order);
		}
		
		map.put("status", "success");
		map.put("ids", params);
		map.put("desc", "支付成功！");
		return JSON.toJSONString(map);
	}
	
	
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/person" }, method=RequestMethod.GET)
	public String person(HttpServletRequest request){
		User user = getSessionUser(request);
		request.setAttribute("personDatail", orderService.getPersonDetail(user));
		List<Map<String, String>> list =getOrderStatusList();
		Map<String, Object> map = new HashMap<>();
		for (Map<String, String> map1 : list) {
			map.put(map1.get("value"), map1);
		}
		request.setAttribute("status", map);
		return"/person";
	}
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/logout" }, method=RequestMethod.GET)
	public String logout(HttpServletRequest request){
		request.getSession().setAttribute("user", null);
		return "/index";
	}
	/**
	 * 个人订单列表
	 * @param request
	 * @param orderStatus
	 * @return
	 */
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = "/personDetail", method=RequestMethod.POST)
	public String personDetail(HttpServletRequest request,
			@RequestParam("orderStatus")OrderStatus orderStatus){
		User user = getSessionUser(request);
		List< Order> orders = orderService.listForOrderByStatus(user, orderStatus);
		return JSON.toJSONString(orders,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = "/orderEnd", method=RequestMethod.POST)
	public String orderEnd(HttpServletRequest request ,@RequestParam("oid")String oid){
		Map< String, Object> map = new HashMap<>();
		Order order = orderService.get(oid);
		if (order==null) {
			map.put("status", "fail");
			map.put("desc", "订单不存在");
			return JSON.toJSONString(map);
		}
		order.setOrderStatus(OrderStatus.WITETOEVALUATE);
		orderService.update(order);
		map.put("status", "success");
		map.put("desc", "已确认收货");
		return JSON.toJSONString(map);
	}
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/shopcart" }, method=RequestMethod.GET)
	public String shopcart(HttpServletRequest request){
		User user = getSessionUser(request);
		List<ShopCart> list = shopCartService.list(user);
		BigDecimal sum = new BigDecimal(0);
		int count = 0;
		for (ShopCart shopCart : list) {
			sum =sum.add(shopCart.getItem().getPrice().multiply(new BigDecimal(shopCart.getNum())));
		
			count+=shopCart.getNum();
			
		}
		
		request.setAttribute("carts", list);
		request.setAttribute("sum", sum);
		request.setAttribute("count", count);
		return"/shopcart";
	}
	/**
	 * 评论页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/review" }, method=RequestMethod.GET)
	public String review(HttpServletRequest request,
			@RequestParam("id")String id,
			@RequestParam("oid")String oid){
		//User user = getSessionUser(request);
		Item item = itemService.get(id);
		
		request.setAttribute("oid", oid);
		request.setAttribute("item", item);
		return"/commentlist";
	}
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = "/doReview", method=RequestMethod.POST)
	public String doReview(HttpServletRequest request,
			@RequestParam("content")String content,
			@RequestParam("id")String id ,
			@RequestParam("oid")String oid,
			@RequestParam("comment")Comment comment){
		Map<String, Object> map = new HashMap<>();

		User user = getSessionUser(request);
		Item item = itemService.get(id);
		Order order = orderService.get(oid);
		if (item==null) {
			map.put("status", "fail");
			map.put("desc", "该商品不存在，怎么评论？");
			return JSON.toJSONString(map);
		}
		if (comment==null) {
			map.put("status", "fail");
			map.put("desc", "至少得选个评价吧？");
			return JSON.toJSONString(map);
		}
		Review review = new Review();
		review.setCreateTime(new Date());
		review.setComment(comment);
		review.setItem(item);
		review.setContent(content);
		review.setUser(user);
		reviewService.save(review);
		order.setOrderStatus(OrderStatus.WITETOSALESRETURN);
		orderService.update(order);
		map.put("status", "success");
		map.put("desc", "评价成功");
		return JSON.toJSONString(map);
	}
	/**
	 * 添加购物车
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = "/addCart", method=RequestMethod.POST)
	public String addCart(HttpServletRequest request,
			@RequestParam("id")String id){
		Map<String , Object> map = new HashMap<>();
		User user = getSessionUser(request);
		Item item = itemService.get(id);
		if (item==null) {
			map.put("status", "fail");
			map.put("desc", "此商品不存在");
			return JSON.toJSONString(map);
		}
		
		ShopCart shopCart = shopCartService.findByUserAndItem(user, item);
		if (shopCart==null) {
			shopCart = new ShopCart();
			shopCart.setCartStatus(CartStatus.NORMAL);
			shopCart.setItem(item);
			shopCart.setUser(user);
			shopCart.setNum(1);
			shopCartService.save(shopCart);
		}else {
			shopCart.setCartStatus(CartStatus.NORMAL);
			shopCart.setNum(1);
			shopCartService.update(shopCart);
		}
		
		map.put("status", "success");
		map.put("desc", "收藏成功");
		return JSON.toJSONString(map);
	}
	
	
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = "delCart", method=RequestMethod.POST)
	public String delCart(HttpServletRequest request,
			@RequestParam("id")String id){
		Map<String , Object> map = new HashMap<>();
		ShopCart shopCart = shopCartService.get(id);
		if (shopCart==null) {
			map.put("status", "fail");
			map.put("desc", "你在删除一个不存在的收藏");
			return JSON.toJSONString(map);
		}
		shopCart.setCartStatus(CartStatus.DELETE);
		shopCartService.update(shopCart);
		map.put("status", "success");
		map.put("desc", "删除成功");
		return JSON.toJSONString(map);
	}
	
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = "changeNum", method=RequestMethod.POST)
	public String changeNum(HttpServletRequest request,
			@RequestParam("id")String id,
			@RequestParam("num")int num,
			@RequestParam(value = "ids",required = false)String...ids){
		Map<String , Object> map = new HashMap<>();
		ShopCart shopCart = shopCartService.get(id);
		if (shopCart==null) {
			map.put("status", "fail");
			map.put("desc", "你在修改一个不存在的收藏");
			return JSON.toJSONString(map);
		}
		shopCart.setNum(num);
		shopCartService.update(shopCart);
		
		map.put("status", "success");
		map.put("desc", "删除成功");
		return JSON.toJSONString(map);
	}
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = "changeNumOrder", method=RequestMethod.POST)
	public String changeNumOrder(HttpServletRequest request,
			@RequestParam("id")String id,
			@RequestParam("num")int num,
			@RequestParam(value = "ids",required = false)String...ids){
		Map<String , Object> map = new HashMap<>();
		Order order = orderService.get(id);
		if (order==null) {
			map.put("status", "fail");
			map.put("desc", "你在修改一个不存在的订单");
			return JSON.toJSONString(map);
		}
		order.setNum(num);
		order.setRealPrice(order.getItem().getPrice().multiply(new BigDecimal(num)));
		orderService.update(order);
		
		map.put("status", "success");
		map.put("desc", "删除成功");
		return JSON.toJSONString(map);
	}
	
	/**
	 * 移到订单
	 * @param request
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = "moveToOrder", method=RequestMethod.POST)
	public String moveToOrder(HttpServletRequest request,
			@RequestParam(value = "ids",required = false)String...ids){
		Map<String , Object> map = new HashMap<>();
		if (StringUtils.isAnyBlank(ids)) {
			map.put("status", "fail");
			map.put("desc", "请选择一个收藏");
			return JSON.toJSONString(map);	
		}
		for (String str : ids) {
			ShopCart shopCart =shopCartService.get(str);
			Order order = new Order();
			order.setItem(shopCart.getItem());
			order.setNum(shopCart.getNum());
			order.setRealPrice(shopCart.getItem().getPrice().multiply(new BigDecimal(shopCart.getNum())));
			order.setUser(shopCart.getUser());
			order.setOrderStatus(OrderStatus.OBLIGATION);
			orderService.save(order);
			shopCart.setCartStatus(CartStatus.OVER);
			shopCartService.update(shopCart);
		}
		map.put("status", "success");
		map.put("desc", "结算成功");
		return JSON.toJSONString(map);
	}
	
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/safety" }, method=RequestMethod.GET)
	public String safety(HttpServletRequest request){
		return"/safety";
		
	}
	@ResponseBody
	@RequestMapping(produces = { "application/json;charset=UTF-8" }, value = { "/resetPassword" }, method=RequestMethod.POST)
	public String resetPassword(HttpServletRequest request,
			@RequestParam("password")String password,
			@RequestParam("newPassword")String newPassword){
		Map<String, Object> map = new HashMap<>();
		User user = getSessionUser(request);
		user = userService.find(user.getAccount(), password);
		
		if (user != null) {
			if (userService.changePassword(user, newPassword)) {
				map.put("status", "success");
				map.put("desc", "密码修改成功");
				return JSON.toJSONString(map);
			}
		}
		map.put("status", "fail");
		map.put("desc", "原密码不正确！");
		return JSON.toJSONString(map);
		
	}
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = "city", method=RequestMethod.POST)
	public String city(HttpServletRequest request,
			@RequestParam(value = "key",required = false)String key){
		Map<String , Object> map = new HashMap<>();
		map.put("status", "success");
		map.put("data", administrationService.getCity(key));
		return JSON.toJSONString(map);
	}
	
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = "county", method=RequestMethod.POST)
	public String county(HttpServletRequest request,
			@RequestParam(value = "key1",required = false)String key1,
			@RequestParam(value = "key2",required = false)String key2){
		Map<String , Object> map = new HashMap<>();
		map.put("status", "success");
		map.put("data", administrationService.getCounty(key1,key2));
		return JSON.toJSONString(map);
	}
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = "saveAddress", method=RequestMethod.POST)
	public String saveAddress(HttpServletRequest request,
			@RequestParam(value = "id",required = false)String id,
			@RequestParam(value = "address",required = false)String address,
			@RequestParam(value = "person",required = false)String person,
			@RequestParam(value = "phone",required = false)String phone){
		Map<String , Object> map = new HashMap<>();
		User user =getSessionUser(request);
		Administration administration = administrationService.get(id);
		Address add = new Address();
		add.setPerson(person);
		add.setAddress(address);
		add.setAdministration(administration);
		add.setPhone(phone);
		add.setUser(user);
		add.setSelected(false);
		addressService.save(add);
		map.put("status", "success");
		map.put("desc", "添加成功");
		return JSON.toJSONString(map);
	}
	
	
	
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = "deleteAddress", method=RequestMethod.POST)
	public String deleteAddress(HttpServletRequest request ,HttpServletResponse response,
			@RequestParam("id")String id){
		Map<String, Object> map = new HashMap<>();
		Address address = addressService.get(id);
		address.setValidStatus(ValidStatus.STOP);
		addressService.update(address);
		map.put("status", "success");
		map.put("desc", "删除成功");
		
		return JSON.toJSONString(map);
	}
	
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = "defultAddress", method=RequestMethod.POST)
	public String defultAddress(HttpServletRequest request ,HttpServletResponse response,
			@RequestParam("id")String id){
		Map<String, Object> map = new HashMap<>();
		Address address = addressService.get(id);
		address.setSelected(true);
		addressService.updateProperty("f_selected", getSessionUser(request));
		addressService.update(address);
		map.put("status", "success");
		map.put("desc", "设置成功");
		
		return JSON.toJSONString(map);
	}
	

	
}
