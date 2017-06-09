package com.shopping.base.dao.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.shopping.base.dao.ShopCartDao;
import com.shopping.base.entity.ShopCart;

@Repository
public class ShopCartDaoImpl extends AbstractBaseDaoImpl<ShopCart> implements ShopCartDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 793914126187592063L;
	
	@Override
	public Map< String, Object> queryCount(String[] ids){
		StringBuffer sql = new StringBuffer(" select ");
		sql.append(" sum(o.f_num) as num,");
		sql.append(" sum(o.f_num*i.f_price) as allprice ");
		sql.append(" from ");
		sql.append(" tb_shop_cart o ");
		sql.append(" left join tb_item i on o.f_item = i.id");
		sql.append(" where 1=1");
		if (!StringUtils.isAnyBlank(ids)) {
			sql.append(" and o.id in(");
			for (String string : ids) {
				sql.append("?,");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
		}
		
		return jdbcTemplate.queryForMap(sql.toString(),ids);
	}
	
	public static void main(String[] args) {
		String[] ids = {" s","s ","s "};
		StringBuffer sql = new StringBuffer(" select ");
		sql.append(" sum(o.f_num) as num,");
		sql.append(" sum(o.f_num*i.f_price) as allprice ");
		sql.append(" from ");
		sql.append(" tb_shop_cart o ");
		sql.append(" left join tb_item i on o.f_item = i.id");
		sql.append(" where 1=1");
		if (!StringUtils.isAnyBlank(ids)) {
			sql.append(" and o.id in(");
			for (String string : ids) {
				sql.append("?,");
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(")");
		}
		System.err.println(sql.toString());
	}

}
