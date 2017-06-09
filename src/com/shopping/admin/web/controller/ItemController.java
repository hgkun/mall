package com.shopping.admin.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.shopping.base.entity.Item;
import com.shopping.base.entity.ItemType;
import com.shopping.base.enums.ValidStatus;
import com.shopping.base.web.controller.BaseController;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
@Controller
@RequestMapping("/admin/item")
public class ItemController  extends BaseController{
	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/list", method=RequestMethod.POST)
	public String list(HttpServletRequest request,
			@RequestParam(required=false, value="keyword") String keyword, 
			@RequestParam(required=false, value="validStatus") ValidStatus validStatus, 
			@RequestParam(value="pagesize", defaultValue="10") Integer pagesize, 
			@RequestParam(value="offset", defaultValue="0") Integer offset){
		
		QueryResult<Item> items = itemService.query(keyword,validStatus, new Pager(offset, pagesize));
		return JSON.toJSONString(items,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		
	}
	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/delete", method=RequestMethod.POST)
	public String delete(HttpServletRequest request, @RequestParam("id")String id){
		
		Map< String, Object> map = new HashMap<>();
		
		if (StringUtils.isBlank(id)) {
			map.put("status", "fail");
			map.put("desc", "id不能为空");
			return JSON.toJSONString(map);
		}
		Item item = itemService.get(id);
		item.setValidStatus(ValidStatus.STOP);
		itemService.update(item);
		
		map.put("status", "success");
		map.put("desc", "删除成功");
		return JSON.toJSONString(map);
	}
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/save" }, method=RequestMethod.GET)
	public String save(HttpServletRequest request,HttpServletResponse response){
		
		request.setAttribute("validStatus", getValidStatusList(ValidStatus.NORMAL));
		request.setAttribute("itemTypes", itemTypeService.all());
		
		return"/admin/item/save";
	}
	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/doSave", method=RequestMethod.POST)
	public String doSave(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("itemName")String itemName,
			@RequestParam("title")String title,
			@RequestParam("imgurl")String imgurl,
			@RequestParam("price")BigDecimal price,
			@RequestParam("itId")String itId){
		Map<String ,Object> map = new  HashMap<>();
		
		if (StringUtils.isAnyBlank(itemName,title,imgurl,itId) && price!=null) {
			map.put("status", "fail");
			map.put("desc", "请上传完整的信息");
			return JSON.toJSONString(map);
		}
		
		ItemType itemType = itemTypeService.get(itId);
		
		if (itemType==null) {
			map.put("status", "fail");
			map.put("desc", "商品类型不存在");
			return JSON.toJSONString(map);
		}
		Item item = new Item();
		item.setImgurl(imgurl);
		item.setItemName(itemName);
		item.setItemType(itemType);
		item.setPrice(price);
		item.setTitle(title);
		item.setCreateTime(new Date());
		item.setValidStatus(ValidStatus.NORMAL);
		itemService.save(item);
		
		map.put("status", "success");
		map.put("desc", "商品上传成功");
		return JSON.toJSONString(map);
	}
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/edit" }, method=RequestMethod.GET)
	public String edit(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("id")String id){
		request.setAttribute("validStatus", getValidStatusList(ValidStatus.NORMAL));
		Item item = itemService.get(id);
		request.setAttribute("item",item);
		request.setAttribute("itemTypes", itemTypeService.all());
		
		return"/admin/item/edit";
	}
	
	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/doEdit", method=RequestMethod.POST)
	public String doEdit(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("id")String id,
			@RequestParam("validStatus") ValidStatus validStatus,
			@RequestParam("itemName")String itemName,
			@RequestParam("title")String title,
			@RequestParam("imgurl")String imgurl,
			@RequestParam("price")BigDecimal price,
			@RequestParam("itId")String itId){
		Map<String ,Object> map = new  HashMap<>();
		
		if (StringUtils.isAnyBlank(itemName,title,imgurl,itId) && price!=null) {
			map.put("status", "fail");
			map.put("desc", "请上传完整的信息");
			return JSON.toJSONString(map);
		}
		
		ItemType itemType = itemTypeService.get(itId);
		
		if (itemType==null) {
			map.put("status", "fail");
			map.put("desc", "商品类型不存在");
			return JSON.toJSONString(map);
		}
		Item item = itemService.get(id);
		item.setImgurl(imgurl);
		item.setItemName(itemName);
		item.setItemType(itemType);
		item.setValidStatus(validStatus);
		item.setPrice(price);
		item.setTitle(title);
		item.setImgurl(imgurl);
		itemService.update(item);
		
		map.put("status", "success");
		map.put("desc", "商品编辑成功");
		return JSON.toJSONString(map);
	}
	
	private List<Map<String, String>> getValidStatusList(ValidStatus validStatus){
		
		List<Map<String, String>> list = new ArrayList<>();
		ValidStatus[] values = ValidStatus.values();
		for (int i = 0; i < values.length; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("value", values[i].getValue());
			map.put("name", values[i].name());
			list.add(map);
		}
		return list;
	}
	

}
