package com.shopping.admin.web.controller;

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
@RequestMapping("/admin/itemType")
public class ItemTypeController extends BaseController{
	
	@RequestMapping(produces = "text/html;charset=UTF-8", value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest  request){
		request.setAttribute("validStatus", getValidStatusList(ValidStatus.NORMAL));
		return"/admin/itemType/list";
	}
	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/doList", method=RequestMethod.POST)
	public String list(HttpServletRequest request,
			@RequestParam(required=false, value="keyword") String keyword, 
			@RequestParam(required=false, value="validStatus") ValidStatus validStatus, 
			@RequestParam(value="pagesize", defaultValue="10") Integer pagesize, 
			@RequestParam(value="offset", defaultValue="0") Integer offset){
		
		QueryResult<ItemType> itemTypes = itemTypeService.query(keyword, validStatus,new Pager(offset, pagesize));
		return JSON.toJSONString(itemTypes,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		
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
		ItemType item = itemTypeService.get(id);
		item.setValidStatus(ValidStatus.STOP);
		itemTypeService.update(item);
		
		map.put("status", "success");
		map.put("desc", "删除成功");
		return JSON.toJSONString(map);
	}
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/save" }, method=RequestMethod.GET)
	public String save(HttpServletRequest request,HttpServletResponse response){
		
		request.setAttribute("itemTypes", itemTypeService.listForFather());
		
		return"/admin/itemType/save";
	}
	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/doSave", method=RequestMethod.POST)
	public String doSave(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("name")String name,
			@RequestParam("pid")String pid){
		Map<String ,Object> map = new  HashMap<>();
		
		if (StringUtils.isBlank(name) ) {
			map.put("status", "fail");
			map.put("desc", "请上传完整的信息");
			return JSON.toJSONString(map);
		}
		
		ItemType father = itemTypeService.get(pid);
		
		ItemType itemType = new ItemType();
		itemType.setName(name);
		itemType.setItemType(father);
		itemType.setValidStatus(ValidStatus.NORMAL);
		itemTypeService.save(itemType);
		
		map.put("status", "success");
		map.put("desc", "新类目创建成功");
		return JSON.toJSONString(map);
	}
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/edit" }, method=RequestMethod.GET)
	public String edit(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("id")String id){
		request.setAttribute("validStatus", getValidStatusList(ValidStatus.NORMAL));
		ItemType item = itemTypeService.get(id);
		request.setAttribute("itemType",item);
		request.setAttribute("itemTypes", itemTypeService.listForFather());
		
		return"/admin/itemType/edit";
	}
	
	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/doEdit", method=RequestMethod.POST)
	public String doEdit(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("id")String id,
			@RequestParam("validStatus") ValidStatus validStatus,
			@RequestParam("name")String name,
			@RequestParam("pid")String pid){
		Map<String ,Object> map = new  HashMap<>();
		
		if (StringUtils.isAnyBlank(name,id)) {
			map.put("status", "fail");
			map.put("desc", "请上传完整的信息");
			return JSON.toJSONString(map);
		}
		
		ItemType father = itemTypeService.get(pid);
		

		ItemType itemType = itemTypeService.get(id);
		itemType.setItemType(father);
		itemType.setValidStatus(validStatus);
		itemType.setName(name);
		itemTypeService.update(itemType);
		
		map.put("status", "success");
		map.put("desc", "商品类型编辑成功");
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
