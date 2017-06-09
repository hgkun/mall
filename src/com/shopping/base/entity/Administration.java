package com.shopping.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.shopping.core.entity.IdEntity;

/**
 * 行政区域
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_administration")
public class Administration extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 524543993877878043L;
	
	
	private String province;
	
	private String city;
	
	private String county;

	@Column(name="f_province")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	@Column(name="f_city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	@Column(name="f_county")
	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

}
