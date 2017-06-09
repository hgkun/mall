/**
 * $Id: IdEntity.java Nov 18, 2014 10:49:51 AM hdp
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shopping.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.shopping.base.entity.User;


/**
 * 

 * @version V1.0
 * @since V1.0
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7282576412687617289L;
	private String id;

	/**
	 * 创建时间
	 */
	private Date createTime = new Date();
	
	/**
	 * 创建人
	 */
	private User createBy;
	
	/**
	 * 更改时间
	 */
	private Date updateTime;
	
	/**
	 * 更改人
	 */
	private User updateBy;
	// ------------------------------- mysql -------------------------------- //
	/**
	 * @return the id
	 */
	/*@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="F_ID")
	public Long getId() {
		return id;
	}*/
	
	
	// -------------------------- oracle -------------------------------- //
	/*@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "songSequenceGenerator")
	@SequenceGenerator(
	      name = "songSequenceGenerator",
	      sequenceName = "song_sequence",
	      initialValue = 2,
	      allocationSize = 20
	  )
	@Column(name="F_ID")
	public Long getId() {
		return id;
	}*/

	// --------------------------- UUID  ----------------------------------- //
	@Id
	@GeneratedValue(generator = "tableGenerator")
	@GenericGenerator(name = "tableGenerator", strategy = "uuid")
	@Column(name ="f_id",nullable=false,length=32,unique=true)
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="f_create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_create_by")
	public User getCreateBy() {
		return createBy;
	}
	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="f_update_time")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="f_update_by")
	public User getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}
	
	
}
