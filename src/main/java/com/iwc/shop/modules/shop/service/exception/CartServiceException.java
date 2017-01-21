package com.iwc.shop.modules.shop.service.exception;

/**
 * @author Tony Wong
 *
 */
public class CartServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CartServiceException() {
		super();
	}
	
	public CartServiceException(String message) {
		super(message);
	}
}
