package com.shopping.admin.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shopping.base.web.controller.BaseController;

/**
 * 
 * 
 * @version V1.0
 * @since V1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/index" }, method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response){
		return"/admin/item/list";
	}

}
