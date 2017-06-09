package com.shopping.core.qiniu.common;

import java.io.IOException;

import com.shopping.core.qiniu.http.Response;


public class QiniuException extends IOException {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3198091246655055366L;
	public final Response response;


    public QiniuException(Response response) {
        this.response = response;
    }

    public QiniuException(Exception e) {
        super(e);
        this.response = null;
    }

    public String url() {
        return response.url();
    }

    public int code() {
        return response == null ? -1 : response.statusCode;
    }
}
