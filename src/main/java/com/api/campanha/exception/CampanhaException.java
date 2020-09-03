package com.api.campanha.exception;

import org.springframework.http.HttpStatus;

public class CampanhaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus;

	private Object obj;
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public Object getObj() {
		return obj;
	}

	public CampanhaException(String msg) {
		super(msg);
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
	
	public CampanhaException(String msg, HttpStatus status) {
		super(msg);
		this.httpStatus = status;
	}
	
	public CampanhaException(String msg, Throwable ex, HttpStatus status) {
		super(msg, ex);
		this.httpStatus = status;
	}

	public CampanhaException(Object o, String msg) {
		super(msg);
		this.httpStatus = HttpStatus.BAD_REQUEST;
		this.obj = o;
	}

	public CampanhaException(Object o, String msg, HttpStatus status) {
		super(msg);
		this.httpStatus = status;
		this.obj = o;
	}

	public CampanhaException(Object o, String msg, Throwable ex, HttpStatus status) {
		super(msg, ex);
		this.httpStatus = status;
		this.obj = o;
	}

}
