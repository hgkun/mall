package com.shopping.base.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.shopping.base.constant.Conf;
import com.shopping.base.entity.Admin;
import com.shopping.base.entity.HeaderImg;
import com.shopping.base.entity.Item;
import com.shopping.base.entity.ItemType;
import com.shopping.base.entity.Message;
import com.shopping.base.entity.Review;
import com.shopping.base.entity.User;
import com.shopping.base.enums.ValidStatus;
import com.shopping.base.util.QiniuUtils;
import com.shopping.core.dto.Order;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

@Controller("public")
@RequestMapping({ "/" })
public class PublicController extends BaseController {
	
	private static List< String> doSortFields = Arrays.asList("price");
	
	
	/**
	 * 首页
	 * @param request
	 * @return
	 */
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/index" }, method = { RequestMethod.GET })
	public String index(HttpServletRequest request) {
		List<ItemType> itemTypes = itemTypeService.list(new Pager(1, 2));
		
		if (itemTypes!=null && !itemTypes.isEmpty()) {
			for (int i = 0; i < itemTypes.size(); i++) {
				
				List<Item> item = itemService.list(itemTypes.get(i), new Pager(0, 8));
				request.setAttribute("item"+i, item);
			}
		}
		
		List<HeaderImg> headerImgs = headerImgService.list(new Pager(0, 4));
		request.setAttribute("headerImgs", headerImgs);
		
		request.setAttribute("itemTypeList", gettypeList());
		
		return "/index";
	}
	@ResponseBody
	@RequestMapping(produces = { "application/json;charset=UTF-8" }, value = { "/getToken" }, method = { RequestMethod.GET })
	public String getToken(){
		Map<String, Object> map = new HashMap<>();
		map.put("uptoken", QiniuUtils.getToken(Conf.QINIU_BUCKET_NAME));
		return JSON.toJSONString(map);
	}
	
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/introduction" }, method = { RequestMethod.GET })
	public String introduction(HttpServletRequest request,
			@RequestParam("id")String id) {
		Item item = itemService.get(id);
		if (item==null) {
			return "";
		}
		request.setAttribute("item", item);
		
		return "/introduction";
	}
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = { "/reviewList" }, method = { RequestMethod.POST })
	public String reviewList(HttpServletRequest request,
			@RequestParam("id")String id){
		
		List<Review> list = reviewService.list(id,new Pager(-1, -1));
		Map<String, Object> map = new HashMap<>();
		map.put("status", "success");
		map.put("result", list);
		return JSON.toJSONString(map,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	/**
	 * 首页
	 * @param request
	 * @return
	 */
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/notfound" }, method = { RequestMethod.GET })
	public String page404(HttpServletRequest request) {
		return "/user/notfound";
	}
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/adminLogin" }, method = { RequestMethod.GET })
	public String adminLogin(HttpServletRequest request ,HttpServletResponse response){
		
		return "/admin/adminLogin";
	}
	
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value = { "/doAdminLogin" }, method = { RequestMethod.POST })
	public String doAdminLogin(HttpServletRequest request ,HttpServletResponse response,
			@RequestParam("account")String account,
			@RequestParam("password")String password){
		Map<String , Object> map = new HashMap<>();
		if (StringUtils.isBlank(account)) {
			map.put("status", "fail");
			map.put("desc", "账号不能为空");
			return JSON.toJSONString(map);
		}if (StringUtils.isBlank(password)) {
			map.put("status", "fail");
			map.put("desc", "密码不能为空");
			return JSON.toJSONString(map);
		}
		Admin admin = adminService.find(account, password);
		if (admin == null) {
			map.put("status", "fail");
			map.put("desc", "用户或密码错误");
			return JSON.toJSONString(map);
		}
		request.getSession().setAttribute(Conf.SESSION_ADMIN, admin);
		map.put("status", "success");
		map.put("desc", "登陆成功");
		return JSON.toJSONString(map);
		
	}
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value ="/doSearch", method = { RequestMethod.POST })
	public String doSearch(HttpServletRequest request,
			@RequestParam("keyword")String keyword,
			@RequestParam(value="itId",required=false)String itId,
			@RequestParam(value="field",required=false)String field,
			@RequestParam(value="sort",required=false)String sort,
			@RequestParam(value="pagesize", defaultValue="10") Integer pagesize, 
			@RequestParam(value="offset", defaultValue="0") Integer offset){
		Order order = Order.buildOrder(doSortFields, field, sort);
		ItemType itemType = itemTypeService.get(itId);
		QueryResult<Item> qr = itemService.query(keyword, order, itemType, new Pager(offset, pagesize));
		return JSON.toJSONString(qr,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/search" }, method = { RequestMethod.GET })
	public String search(HttpServletRequest request,
			@RequestParam(value="keyword",required=false)String keyword,
			@RequestParam(value="itId",required=false)String itId){
		request.setAttribute("keyword", keyword);
		request.setAttribute("itId", itemTypeService.get(itId));
		List<ItemType> itemTypes = itemTypeService.getTypeListByKeywordAndItid(keyword,itId);
		request.setAttribute("itemTypes", itemTypes);
		if (itemTypes!=null && !itemTypes.isEmpty()) {
			request.setAttribute("allType", itemTypes.get(0).getItemType());
		}else{
			request.setAttribute("allType", new ItemType());
			
		}
		return"/search";
	}
	
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value ="/getMsgCount", method = { RequestMethod.POST })
	public String getMsgCount(HttpServletRequest request,HttpServletResponse response){
		User user = getSessionUser(request);
		long count = messageService.count(user);
		Map< String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		
		return JSON.toJSONString(map);
	}
	
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value ="/getMsg", method = { RequestMethod.POST })
	public String getMsg(HttpServletRequest request,HttpServletResponse response){
		User user = getSessionUser(request);
		List<Message> count = messageService.list(user);
		Map< String, Object> map = new HashMap<String, Object>();
		map.put("results", count);
		return JSON.toJSONString(map);
	}
	
	@ResponseBody
	@RequestMapping(produces = "application/json;charset=UTF-8", value ="/msgOK", method = { RequestMethod.GET })
	public void msgOK(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("id")String id){
		Message msg = messageService.get(id);
		msg.setValidStatus(ValidStatus.STOP);
		messageService.update(msg);
	}
	
	
	
	public List<Object> gettypeList(){
		List<ItemType> itemTypes = itemTypeService.listForFather();
		List<Object> list = new ArrayList<>();
		if (itemTypes!=null && !itemTypes.isEmpty()) {
			for (int i=0;i<itemTypes.size()&&i<10;i++) {
				Map< String, Object> map = new HashMap<>();
				map.put("name", itemTypes.get(i).getName());
				map.put("child", itemTypeService.listForchild(itemTypes.get(i)));
				list.add(map);
			}
		}
		return list;
	}
	
}