package com.shopping.member.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.shopping.base.constant.Conf;
import com.shopping.base.entity.User;
import com.shopping.base.web.controller.BaseController;
import com.shopping.user.dto.UserDto;



/**
 * 
 * 
 *
 */
@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/login" })
	public String login(HttpServletRequest request,HttpServletResponse response){
		return "/login";
	}
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/register" })
	public String register(HttpServletRequest request,HttpServletResponse response){
		return "/register";
	}
	
	
	
	@ResponseBody
	@RequestMapping(produces = { "application/json;charset=UTF-8" }, value = { "/doLogin" }, method=RequestMethod.POST)
	public String doLogin(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="accountOrPhone")String accountOrPhone,
			@RequestParam(value="password")String password){
		Map<String, Object> map = new HashMap<>();
		User user = userService.find(accountOrPhone, password);
		if (user==null) {
			map.put("status","fail");
			map.put("desc","用户或者密码错误");
			return JSON.toJSONString(map);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		request.getSession().setAttribute("brithday",user.getBrithDay()!=null?sdf.format(user.getBrithDay()):"" );
		request.getSession().setAttribute(Conf.SESSION_USER,user );

		map.put("status","success");
		map.put("desc","登陆成功");
		return JSON.toJSONString(map);
	}
	
	/**
	 * 注册
	 * @param account
	 * @param password
	 * @param password2
	 * @return
	 */
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/doRegister", method=RequestMethod.POST)
	public String doRegister(@RequestParam(value="account")String account,
			@RequestParam( value="password")String password,
			@RequestParam( value="password2")String password2) {
		
		
		Map<String ,Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(account)) {
			map.put("status", "fail");
			map.put("desc", "账号不能为空！");
			return JSON.toJSONString(map);
		}
		if (userService.isExist(account)) {
			map.put("status", "fail");
			map.put("desc", "账号已存在！");
			return JSON.toJSONString(map);
		}
		
		if (StringUtils.isBlank(password)) {
			map.put("status", "fail");
			map.put("desc", "密码不能为空！");
			return JSON.toJSONString(map);
		}
		if (!password.equals(password2)) {
			map.put("status", "fail");
			map.put("desc", "两次密码输入不同！");
			return JSON.toJSONString(map);
		}
		String salt = RandomStringUtils.randomAlphanumeric(5);
		User user = new User();
		user.setAccount(account);
		user.setSalt(salt);
		
		user.setPassword(DigestUtils.md5Hex(password+salt));
		user.setCreateTime(new Date());
		userService.save(user);
		map.put("status", "success");
		map.put("desc", "恭喜您，注册成功！");
		return JSON.toJSONString(map);
	}
	

	
	
	


	

}
