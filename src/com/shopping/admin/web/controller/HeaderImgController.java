package com.shopping.admin.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.shopping.base.entity.HeaderImg;
import com.shopping.base.entity.Item;
import com.shopping.base.enums.ValidStatus;
import com.shopping.base.web.controller.BaseController;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;

/**
 * 
 * 轮播controller 控制层
 * @author wangyihui
 */
@Controller
@RequestMapping("/admin/headerimg")
public class HeaderImgController extends BaseController{
	
	/**
	 * 轮播图片管理列表页
	 * @param request
	 * @return
	 */
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/list" }, method=RequestMethod.GET)
	public String list(HttpServletRequest request){
		request.setAttribute("validStatus", getValidStatusList());
		return "/admin/headerimg/list";
	}
	
	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/doList", method=RequestMethod.POST)
	public String doList(HttpServletRequest request,
			@RequestParam(required=false, value="keyword") String keyword, 
			@RequestParam(required=false, value="validStatus") ValidStatus validStatus, 
			@RequestParam(value="pagesize", defaultValue="10") Integer pagesize, 
			@RequestParam(value="offset", defaultValue="0") Integer offset){
		QueryResult<HeaderImg> qr = headerImgService.query(keyword, validStatus, new Pager(offset, pagesize));
		return JSON.toJSONString(qr,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
	}
	

	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/delete", method=RequestMethod.POST)
	public String delete(HttpServletRequest request,
			@RequestParam( value="id") String id){
		Map< String, Object> map = new HashMap<>();
		
		if (StringUtils.isBlank(id)) {
			map.put("status", "fail");
			map.put("desc", "id不能为空");
			return JSON.toJSONString(map);
		}
		HeaderImg headerImg = headerImgService.get(id);
		if (headerImg==null) {
			map.put("status", "fail");
			map.put("desc", "改轮播图不存在");
			return JSON.toJSONString(map);
		}
		headerImg.setValidStatus(ValidStatus.STOP);
		headerImgService.update(headerImg);
		
		
		map.put("status", "success");
		map.put("desc", "删除成功");
		return JSON.toJSONString(map);
		
	}
	
	
	
	
	@RequestMapping(produces = "text/html;charset=UTF-8", value = "/save", method=RequestMethod.GET)
	public String save(HttpServletRequest request){
		request.setAttribute("items", itemService.all());
		return"/admin/headerimg/save";
	}
	
	
	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/doSave", method=RequestMethod.POST)
	public String doSave(HttpServletRequest request,
			@RequestParam("url")String url,
			@RequestParam(required=false,value="iid")String iid){
		Map<String , Object> map = new HashMap<>();
		if (StringUtils.isAnyBlank(url,iid)) {
			map.put("status", "fail");
			map.put("desc", "请提交完整的信息！");
			return JSON.toJSONString(map);
		}
		if (StringUtils.isNoneBlank(iid)) {
				Item item = itemService.get(iid);
				HeaderImg headerImg = new HeaderImg();
				headerImg.setUrl(url);
				headerImg.setItem(item);
				headerImg.setValidStatus(ValidStatus.NORMAL);
				headerImgService.save(headerImg);
			}
		

		map.put("status", "success");
		map.put("desc", "添加图片成功！");   
		return JSON.toJSONString(map);
	}
	
	
	
	
	private List<Map<String, String>> getValidStatusList(){
		
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
