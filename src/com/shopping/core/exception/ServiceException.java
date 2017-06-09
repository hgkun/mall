package com.shopping.core.exception;

/**
 * 业务逻辑层异常，遇到该异常才会事务回滚

 * @version V1.0
 * @since V1.0
 */
public class ServiceException extends BaseException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5734547828828393256L;

	public ServiceException() {
		super();
	}

	public ServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
