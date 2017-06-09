package com.shopping.admin.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.shopping.base.entity.Administration;
import com.shopping.base.web.controller.BaseController;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;
import com.shopping.user.util.ReaderExcelUtil;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

@Controller
@RequestMapping("/admin/administration")
public class AdministrationController extends BaseController{
	
	
	@RequestMapping(produces = { "text/html;charset=UTF-8" }, value = { "/list" }, method=RequestMethod.GET)
	public String list (){
		return"/admin/address/list";
	}
	
	
	@ResponseBody
	@RequestMapping(produces="application/json;charset=UTF-8", value="/doList", method=RequestMethod.POST)
	public String doList(HttpServletRequest request,
			@RequestParam(required=false, value="keyword") String keyword, 
			@RequestParam(value="pagesize", defaultValue="10") Integer pagesize, 
			@RequestParam(value="offset", defaultValue="0") Integer offset){
		QueryResult<Administration> qr = administrationService.query(keyword,  new Pager(offset, pagesize));
		return JSON.toJSONString(qr,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
	}
	
	
	@ResponseBody
	@RequestMapping(produces ="application/json;charset=UTF-8", value ="/save", method = RequestMethod.POST)
	public Object save(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("files")MultipartFile files){
		Map<String, Object> params = new HashMap<String, Object>();

		String originalFilename = null;
		if (null == files || files.isEmpty() ) {
			params.put("status", "fail");
			params.put("desc", "没有文件");
			return params;
		}else{
			originalFilename = files.getOriginalFilename();
			try {
				ByteInputStream byteInputStream = new ByteInputStream(files.getBytes(), (int) files.getSize());
				ReaderExcelUtil readerExcelUtil = new ReaderExcelUtil(byteInputStream, originalFilename);
				Integer rowNum = readerExcelUtil.getRowNum();
				List<Administration> list  = new ArrayList<>();
				for (int row = 1; row <= rowNum; row++) {
					Administration administration = new Administration();
					administration.setProvince(readerExcelUtil.getValue(row, 0));
					administration.setCity(readerExcelUtil.getValue(row, 1));
					administration.setCounty(readerExcelUtil.getValue(row, 2));
					administration.setCreateTime(new Date());
					list.add(administration);
				}
				administrationService.saveAll(list);
				params.put("status", "success");
				params.put("desc", "上传成功");
				return params;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				params.put("status", "fail");
				params.put("desc", "系统异常，请检查文件格式是否正确！");
				return params;
			}
		}
		
	}
	
	

}
