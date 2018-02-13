package com.ardikars.jxnet.exception;

public class PropertyOrderException extends RuntimeException {

	public PropertyOrderException() {
		super();
	}

	public PropertyOrderException(String message) {
		super(message);
	}

	public PropertyOrderException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertyOrderException(Throwable cause) {
		super(cause);
	}

	protected PropertyOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
