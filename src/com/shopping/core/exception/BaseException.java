package com.shopping.core.exception;

/**
 * 异常基类

 * @version V1.0
 * @since V1.0
 */
public class BaseException extends RuntimeException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8376555234967628678L;

	public BaseException() {
		super();
	}

	public BaseException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public BaseException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public BaseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public BaseException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
