package com.shopping.base.service.impl;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.shopping.base.dao.AdminDao;
import com.shopping.base.dao.BaseDao;
import com.shopping.base.entity.Admin;
import com.shopping.base.service.AdminService;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
@Service
public class AdminServiceImpl extends AbstractBaseServiceImpl<Admin> implements AdminService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3402951120662722273L;
	@Resource
	private AdminDao adminDao;
	
	@Override
	public BaseDao<Admin> getBaseDao() {
		// TODO Auto-generated method stub
		return adminDao;
	}
	
	
	@Override
	public Admin find(String phone, String password) {
		Admin admin = adminDao.find("o.account=?or o.phone=?", phone,phone);
		if (null == admin) return null;
		String tmp = password + admin.getSalt();
		String md5 = DigestUtils.md5Hex(tmp);
		if (!admin.getPassword().equals(md5)) return null;
		return admin;
	}

}
