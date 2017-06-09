package com.shopping.base.service;

import com.shopping.base.entity.Admin;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
public interface AdminService extends BaseService<Admin> {

	Admin find(String phone, String password);

}
