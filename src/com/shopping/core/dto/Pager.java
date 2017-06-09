package com.shopping.core.dto;

import java.io.Serializable;

/**
 * <p>分页dto</p>
 * <p>默认索引是0，每页显示记录数为20</p>

 * @version V1.0
 * @since V1.0
 */
public class Pager implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4088710529034297033L;
	/**
	 * 索引
	 */
	private int offset;
	/**
	 * 每页显示最大记录数
	 */
	private int size;
	/**
	 * 默认是0，20
	 */
	public Pager() {
		this(0, 20);
	}
	public Pager(int offset, int size) {
		this.offset = offset;
		this.size = size;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + offset;
		result = prime * result + size;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pager other = (Pager) obj;
		if (offset != other.offset)
			return false;
		if (size != other.size)
			return false;
		return true;
	}
}
