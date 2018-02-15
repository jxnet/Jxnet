package com.ardikars.jxnet.exception;

public class PropertyException extends RuntimeException {

	public PropertyException() {
		super();
	}

	public PropertyException(String message) {
		super(message);
	}

	public PropertyException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertyException(Throwable cause) {
		super(cause);
	}

	protected PropertyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
