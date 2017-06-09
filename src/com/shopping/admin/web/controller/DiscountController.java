package com.shopping.admin.web.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.shopping.base.entity.Discount;
import com.shopping.base.entity.Item;
import com.shopping.base.enums.ValidStatus;
import com.shopping.base.web.controller.BaseController;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

/**
 * <p>商品折扣</p>
 * <p>创建时间: 2016年1月26日 下午5:10:42</p>
 * @author <a href="mailto:hgk@cndatacom.com">hgk</a>
 * @version V1.0
 * @since V1.0
 */
@Controller
@RequestMapping("/admin/discount")
public class DiscountController extends BaseController {
	
	
	
	public String list(HttpServletRequest request,HttpServletResponse response){
		return "/admin/discount/list";
	}
	
	
	public String doList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(required=false, value="keyword") String keyword, 
			@RequestParam(value="pagesize", defaultValue="10") Integer pagesize, 
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@DateTimeFormat(pattern = "yyyy/MM/dd") @RequestParam(value="startDate",required = false) Date startDate,
			@DateTimeFormat(pattern = "yyyy/MM/dd") @RequestParam(value="endDate",required = false) Date endDate){
		QueryResult<Discount> qrs = discountService.query(keyword, new Pager(offset, pagesize), startDate, endDate);
		return JSON.toJSONString(qrs);
		
	}
	
	public String save(HttpServletRequest request,HttpServletResponse response){
		return"/admin/discount/save";
	}
	
	
	public String doSave(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "discount",required = false) BigDecimal acount,
			@RequestParam(value = "iid",required = false)String iid,
			@DateTimeFormat(pattern = "yyyy/MM/dd") @RequestParam(value="startDate",required = false) Date startDate,
			@DateTimeFormat(pattern = "yyyy/MM/dd") @RequestParam(value="endDate",required = false) Date endDate){
		
		Map<String , Object> map =new HashMap<>();
		if (StringUtils.isBlank(iid)) {
			map.put("status", "fail");
			map.put("desc", "请选择打折商品！");
			return JSON.toJSONString(map);
		}
		Item item = itemService.get(iid);
		if (item==null) {
			map.put("status", "fail");
			map.put("desc", "此商品不存在！");
			return JSON.toJSONString(map);
		}
		Discount discount = new Discount();
		discount.setCreateTime(new Date());
		discount.setEndDate(endDate);
		discount.setStartDate(startDate);
		discount.setItem(item);
		discount.setValidStatus(ValidStatus.NORMAL);
		discountService.save(discount);
		
		map.put("status", "success");
		map.put("desc", "折扣设置成功");
		
		
		return JSON.toJSONString(map);
	}
	
	
	
	public String edit(HttpServletRequest request,HttpServletResponse response){
		return "/admin/discount/edit";
	}	
	
	
	public String doEdit(HttpServletRequest request,HttpServletResponse response,
			String id,
			@RequestParam(value = "discount",required = false) BigDecimal acount,
			@RequestParam(value = "iid",required = false)String iid,
			@DateTimeFormat(pattern = "yyyy/MM/dd") @RequestParam(value="startDate",required = false) Date startDate,
			@DateTimeFormat(pattern = "yyyy/MM/dd") @RequestParam(value="endDate",required = false) Date endDate){
		Map<String, Object> map = new HashMap<>();
		Item item = itemService.get(iid);
		if (item==null) {
			map.put("status", "fail");
			map.put("desc", "请关联一个商品");
			return JSON.toJSONString(map);
		}
		Discount discount = discountService.get(id);
		if (discount==null) {
			map.put("status", "fail");
			map.put("desc", "次折扣不存在");
			return JSON.toJSONString(map);
		}
		discount.setCreateTime(new Date());
		discount.setEndDate(endDate);
		discount.setStartDate(startDate);
		discount.setItem(item);
		discount.setValidStatus(ValidStatus.NORMAL);
		discountService.update(discount);
		map.put("status", "success");
		map.put("desc", "折扣修改成功");
		return"";
	}

}
