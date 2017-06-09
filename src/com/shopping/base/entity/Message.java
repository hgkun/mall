package com.shopping.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shopping.base.enums.ValidStatus;
import com.shopping.core.entity.IdEntity;

/**
 * 

 * @version V1.0
 * @since V1.0
 */
@Entity
@Table(name="tb_message")
public class Message extends IdEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8223641944412053692L;

	private String title;
	
	private String context;
	
	private User user;
	
	
	private ValidStatus validStatus;

	@Column(name="f_title")
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="f_context")
	public String getContext() {
		return context;
	}


	public void setContext(String context) {
		this.context = context;
	}

	@ManyToOne
	@JoinColumn(name="f_user")
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name="f_valid_status")
	public ValidStatus getValidStatus() {
		return validStatus;
	}


	public void setValidStatus(ValidStatus validStatus) {
		this.validStatus = validStatus;
	}
	
	
	

}
