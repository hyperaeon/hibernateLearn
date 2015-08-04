package com.org.hibernate.learn.exception;

public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(String name) {
		super(name);
	}
}
