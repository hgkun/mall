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
import com.shopping.base.entity.Message;
import com.shopping.base.entity.User;
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
@RequestMapping("/admin/message")
public class MessageController extends BaseController {
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/list" }, method=RequestMethod.GET)
	public String list(HttpServletRequest request){
		request.setAttribute("validStatus", getValidStatusList());
		return "/admin/message/list";
	}
	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/doList", method=RequestMethod.POST)
	public String doList(HttpServletRequest request,
			@RequestParam(required=false, value="keyword") String keyword, 
			@RequestParam(required=false, value="validStatus") ValidStatus validStatus, 
			@RequestParam(value="pagesize", defaultValue="10") Integer pagesize, 
			@RequestParam(value="offset", defaultValue="0") Integer offset){
		
		QueryResult<Message> qr = messageService.query(keyword, validStatus, new Pager(offset, pagesize));
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
		messageService.delete(id);
		
		map.put("status", "success");
		map.put("desc", "删除成功");
		return JSON.toJSONString(map);
		
	}
	
	
	
	
	@RequestMapping(produces = "text/html;charset=UTF-8", value = "/save", method=RequestMethod.GET)
	public String save(HttpServletRequest request){
		request.setAttribute("users", userService.all());
		return"/admin/message/save";
	}
	
	
	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/doSave", method=RequestMethod.POST)
	public String doSave(HttpServletRequest request,
			@RequestParam("title")String title,
			@RequestParam(required=false,value="uids")String[] uids,
			@RequestParam("context")String context){
		Map<String , Object> map = new HashMap<>();
		if (StringUtils.isAnyBlank(title,context)) {
			map.put("status", "fail");
			map.put("desc", "标题或内容不能为空！");
			return JSON.toJSONString(map);
		}
		if (uids==null || uids.length<=0) {
			List<User> users = userService.all();
			for (User user :users) {
				Message message = new Message();
				message.setContext(context);
				message.setTitle(title);
				message.setUser(user);
				message.setValidStatus(ValidStatus.NORMAL);
				messageService.save(message);
			}
		}else{
			
			for (int i = 0; i < uids.length; i++) {
				Message message = new Message();
				message.setContext(context);
				message.setTitle(title);
				message.setUser(userService.get(uids[i]));
				message.setValidStatus(ValidStatus.NORMAL);
				messageService.save(message);
			}
		}
		map.put("status", "success");
		map.put("desc", "添加公告成功！");
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
