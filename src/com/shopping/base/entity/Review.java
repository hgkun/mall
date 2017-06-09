package com.shopping.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.shopping.base.enums.Comment;
import com.shopping.core.entity.IdEntity;

@Entity
@Table(name="tb_review")
public class Review extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7353810660541997467L;
	
	private User user;
	
	private String content;
	
	private Item item;
	
	private Comment comment;
	
	
	@ManyToOne
	@JoinColumn(name="f_user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@Column(name="f_content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ManyToOne
	@JoinColumn(name="f_item")
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	@Enumerated(EnumType.ORDINAL)
	@Column(name="f_comment")
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
	

}
