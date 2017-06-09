package com.shopping.base.service;

import java.util.List;
import com.shopping.base.entity.User;
import com.shopping.core.dto.Order;
import com.shopping.core.dto.Pager;
import com.shopping.core.dto.QueryResult;


public interface UserService extends BaseService<User> {
	/**
	 * 通过用户名和密码查找用户
	 * @param name
	 * @param password
	 * @return
	 */
	public User findUser(String name, String password);
	/**
	 * 通过账号密码找到用户
	 * @param phone 账号
	 * @param password 密码
	 * @return
	 */
	public User find(String phone, String password);
	/**
	 * 判断账户是否存在
	 * @param phone 
	 * @return
	 */
	public boolean isExist(String account);
	/**
	 * 判断手机是否存在
	 * @param phone 
	 * @return
	 */
	public boolean isPhoneExist(String phone);
	/**
	 * 判断账户是否存在
	 * @param account 
	 * @return
	 */
	public boolean isAccountExist(String account);
	/**
	 * 通过手机号码找用户
	 * @param phone
	 * @return
	 */
	public User find(String phone);
	
	/**
	 * 搜索用户
	 * @param keyword
	 * @param pager
	 * @return
	 */
	public QueryResult<User> query(String keyword, Pager pager);
	
	/**
	 * 修改密码
	 * @param user
	 * @param password
	 * @return
	 */
	public boolean changePassword(User user, String password);
	/**
	 * 通过分组 查找用户
	 * @param groupId
	 * @return
	 */
	public List<User> queryUserByGroup(String groupId);
	
	/**
	 * 通过分组，关键词 查找用户
	 * @param groupId
	 * @param keyword
	 * @param pager
	 * @return
	 */
	public QueryResult<User> queryUserByGroup(String groupId,String keyword, Pager pager);
	/**
	 * 排序查找用户
	 * @param order
	 * @param keyword
	 * @param pager
	 * @return
	 */
	public QueryResult<User> querySort(Order order, String keyword, Pager pager);
	
	
	
}
